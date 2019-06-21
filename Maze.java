 /*
  * Maze Loader/Solver
  * by Chait Sayani
  * Constructs a maze and demonstrates its solution based on a text full input
  */

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.*;

public class Maze{
  //an ArrayList of ArrayList of MazeSquares that stores every square in the maze
  private ArrayList<ArrayList<MazeSquare>> rowList;

  //these hold coordinates of start/endSquares and dimensions
  private String[] dimensions, startCoords, endCoords;

  //holds values that will be used in Load
  private int startRow, startCol, endRow, endCol, numRows, numCols;

  /**
   * Maze Constructor
   */
  public Maze(){
    rowList = new ArrayList<ArrayList<MazeSquare>>();
  }//end of constructor method

  /**
  * @param inputFile the name of the text file to be loaded in
  * creates RowList
  */
  public void load(String inputFile){

    //creates a file and scanner associated with that file
    File file = new File(inputFile);
    Scanner scanner = null;

    try{
      scanner = new Scanner(file);
    } catch(FileNotFoundException e){
      System.err.println(e);
      System.exit(1);
    }

    //splits first line and parses for dimensions
    //adds it to the string array initialized above the Constructor
    dimensions = scanner.nextLine().split(" ");
    this.numCols = Integer.parseInt(dimensions[0]);
    this.numRows = Integer.parseInt(dimensions[1]);

    //splits second line and parses for start coords
    startCoords = scanner.nextLine().split(" ");
    this.startCol = Integer.parseInt(startCoords[0]);
    this.startRow = Integer.parseInt(startCoords[1]);

    //splits third line and parses for end coords
    endCoords = scanner.nextLine().split(" ");
    this.endCol = Integer.parseInt(endCoords[0]);
    this.endRow = Integer.parseInt(endCoords[1]);

    //initialize instance variables that will be passed through the
    //constructor of the MazeSquare
    boolean initTop, initBot=false, initLeft=false, initRight=false;
    int row=0;
    boolean start, fin;
    boolean isVis;

    //starts parsing every following line
    while (scanner.hasNextLine()){
      //splits line of text into individual MazeSquare shapes
      String[] shapeString = scanner.nextLine().split("");

      //adds a sublist to row list representing that row
      rowList.add(new ArrayList<MazeSquare>());

      //cycles through every shape in the shapeString array
      for (int i = 0; i<shapeString.length; i++) {

        //checks if first row then sets top as true
        //else top is the same value of the square above's botWall
        if (row == 0){
          initTop = true;
        }else{
          initTop = rowList.get(row-1).get(i).hasBot();
        }

        //checks if square is NOT in the last column
        if (i<shapeString.length-1){
          //if not, assigns right wall based on whether the next shape hasRight()
          String nextShape = shapeString[i+1];
          if (nextShape.equals("L") || nextShape.equals("|")){
            initRight = true;
          }else{
            initRight = false;
          }
        //all squares in the last col have Right walls
        }else{
          initRight = true;
        }

        //sets a curShape
        String curShape = shapeString[i];

        //following if statements set Left and Bot walls based on Rules
        if (curShape.equals("L")){
          initLeft = true;
          initBot = true;
        }

        if(curShape.equals("|")){
          initLeft = true;
          initBot = false;
        }

        if (curShape.equals("-")){
          initLeft = false;
          initBot = false;
        }

        if (curShape.equals("_")){
          initLeft = false;
          initBot = true;
        }

        //every square starts as not Visited
        isVis = false;

        //checks if startSquare
        if (row == this.startRow && i == this.startCol){
          start = true;
        } else{
          start = false;
        }

        //checks if endSquare
        if (row == this.endRow && i == this.endCol){
          fin = true;
        } else{
          fin = false;
        }

        //adds each maze square to rowList
        rowList.get(row).add(new MazeSquare(initTop,initBot,initLeft,initRight,isVis,start,fin,row,i,curShape));
      }
      //moves to the next row
      row++;
    }
  }//end of load method

  /**
  * prints out the maze without solution
  * @param none
  */
  public void print(){
    //starts printing the top wall i.e. the very first line of text
    for (int i=0; i<rowList.size();i++){
      ArrayList<MazeSquare> curRow = rowList.get(i);
      for(int a = 0; a<curRow.size();a++){
        MazeSquare curSquare = curRow.get(a);
        System.out.print("+");
        //prints dash if has top wall, blanks if not
        if (curSquare.hasTop()){
          System.out.print("-----");
        }else{
          System.out.print("     ");
        }
      }System.out.print("+");
      System.out.println("");

      //prints second line of walls
      //prints | if left wall, and spaces if not
      for (int b = 0; b<curRow.size();b++){
        MazeSquare curSquare = curRow.get(b);
        if (curSquare.hasLeft()){
          System.out.print("|     ");
        }else{
          System.out.print("      ");
        }
      }System.out.print("|");
      System.out.println("");

      //prints the first half of the third line of walls in each square
      for (int c = 0; c<curRow.size();c++){
        MazeSquare curSquare = curRow.get(c);
        if (curSquare.hasLeft()){
          System.out.print("|  ");
        }else{
          System.out.print("   ");
        }
        //adds an S or F for the finish and start squares
        if (curSquare.isStart()){
          System.out.print("S");
        }else if (curSquare.isFin()){
            System.out.print("F");
        } else{
            System.out.print(" ");
        }
        //prints out the rest of the square
        System.out.print("  ");
      }System.out.print("|");
      System.out.println("");

      //prints next line using same method as second line of walls
      for (int d = 0; d <curRow.size();d++){
        MazeSquare curSquare = curRow.get(d);
        if (curSquare.hasLeft()){
          System.out.print("|     ");
        }else{
          System.out.print("      ");
        }
      }System.out.print("|");
      System.out.println("");
    }

    //prints last bottom wall of maze
    for (int e=0; e < rowList.size()+1;e++){
      System.out.print("+-----");
    }System.out.print("+");
    System.out.println("");
  }//end of print method

  /**
  * prints maze with solution
  * @param none
  */
  public void printSolution(){
    //gets a stack with all solution squares
    Stack<MazeSquare> solution = getSolution();

    //same method as above
    for (int i=0; i<rowList.size();i++){
      ArrayList<MazeSquare> curRow = rowList.get(i);
      //prints out top walls
      for(int a = 0; a<curRow.size();a++){
        MazeSquare curSquare = curRow.get(a);
        System.out.print("+");
        if (curSquare.hasTop()){
          System.out.print("-----");
        }else{
          System.out.print("     ");
        }
      }System.out.print("+");
      System.out.println("");

      //prints out next line of walls
      for (int b = 0; b<curRow.size();b++){
        MazeSquare curSquare = curRow.get(b);
        if (curSquare.hasLeft()){
          System.out.print("|     ");
        }else{
          System.out.print("      ");
        }
      }System.out.print("|");
      System.out.println("");

      //prints out half of third line of walls
      for (int c = 0; c<curRow.size();c++){
        MazeSquare curSquare = curRow.get(c);
        if (curSquare.hasLeft()){
          System.out.print("|  ");
        }else{
          System.out.print("   ");
        }

        //prints same as print method, except if the stack contains the square
        //it prints an *
        if (curSquare.isStart()){
          System.out.print("S");
        }else if (curSquare.isFin()){
            System.out.print("F");
        }else if (solution.contains(curSquare)){
            System.out.print("*");
        }else{
            System.out.print(" ");
        }

        System.out.print("  ");
      }System.out.print("|");
      System.out.println("");

      for (int d = 0; d <curRow.size();d++){
        MazeSquare curSquare = curRow.get(d);
        if (curSquare.hasLeft()){
          System.out.print("|     ");
        }else{
          System.out.print("      ");
        }
      }System.out.print("|");
      System.out.println("");
    }

    //prints bottom wall
    for (int e=0; e < rowList.size()+1;e++){
      System.out.print("+-----");
    }System.out.print("+");
    System.out.println("");

  }//end of print solution method

  /**
   * generates a stack of solution mazeSquares
   * @param none
   * @return Stack of all MazeSquares part of the solution
   */
  public Stack<MazeSquare> getSolution(){
    //creates a stack
    Stack<MazeSquare> solution = new Stack<MazeSquare>();
    //sets a variable that is the square that you now
    MazeSquare startSquare = rowList.get(startRow).get(startCol);
    //sets startSquare as visited
    startSquare.setVisited();
    //pushes Start Square
    solution.push(startSquare);

    //keeps pushing unto the stack until maze is solved or all squares have been pushed
    while (!solution.isEmpty()){
      //if top square is the finish square, maze is solved
      //returns the stack
      if (solution.peek().isFin()==true){
        return solution;
      } //looks at the next movable left square and pushes it + marks visited
      else if (look(solution.peek(),"left") != null){
        solution.push(look(solution.peek(),"left"));
        solution.peek().setVisited();
      } //pushes next available right square and marks visited
      else if (look(solution.peek(),"right") != null){
        solution.push(look(solution.peek(),"right"));
        solution.peek().setVisited();
      } //pushes next available above square and marks visited
      else if (look(solution.peek(),"up")!= null){
        solution.push(look(solution.peek(),"up"));
        solution.peek().setVisited();
      } //pushes next available down square and marks visited
      else if (look(solution.peek(),"down")!=null){
        solution.push(look(solution.peek(),"down"));
        solution.peek().setVisited();
      } //pops square if dead end
      else{
        solution.pop();
      }
    }

    //if stack is empty at the end, its unsolvable
    if (solution.isEmpty()){
      System.out.println("Maze Is Unsolvable");
    }
    return solution;

  }//end of get Solution method

  /**
   *Returns a non visited adjacent square in a specified direction if possible
   *@param square a square to use as a base
   *@param direction a direction to look in
   *@return MazeSquare in the given direction that is not visited
  */
  public MazeSquare look(MazeSquare square, String direction){
    //checks if a square is not visited in a given direction and returns
    //that square
    if (direction.equals("left") && square.hasLeft()==false){
      if (rowList.get(square.getRow()).get(square.getCol()-1).isVisited()==false){
        return rowList.get(square.getRow()).get(square.getCol()-1);
      }
    }else if (direction.equals("right")&& square.hasRight()==false){
      if (rowList.get(square.getRow()).get(square.getCol()+1).isVisited()==false){
        return rowList.get(square.getRow()).get(square.getCol()+1);
      }
    }else if (direction.equals("up") && square.hasTop()==false){
      if (rowList.get(square.getRow()-1).get(square.getCol()).isVisited()==false){
        return rowList.get(square.getRow()-1).get(square.getCol());
      }
    }else if (direction.equals("down") && square.hasBot()==false){
      if (rowList.get(square.getRow()+1).get(square.getCol()).isVisited()==false){
        return rowList.get(square.getRow()+1).get(square.getCol());
      }
    }

    //returns null if dead end
    return null;
  }//end of look Solution method

  public static void main(String[] args) {
    Maze maze = new Maze();
    maze.load(args[0]);
    //shows solution if args[1] is show solution
    if (args.length == 2 && args[1].equals("--showsolution")){
      maze.printSolution();
    }//does regular print if one argument
    else if (args.length==1){
      maze.print();
    }//else prints an argument error
    else{
      System.err.print("please check your arguments");
    }
  }//end of main method

}//end of Maze Class
