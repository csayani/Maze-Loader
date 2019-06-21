public class MazeSquare{
  //these booleans represent whether their respective walls exist
  private boolean top, bot, left, right, visited, isStart, isFin;
  //ints of the row and column of the square
  private int row, col;
  //shape is the string in the text file
  private String shape;

  public MazeSquare(boolean initTop, boolean initBot,
  boolean initLeft, boolean initRight, boolean initVis, boolean start, boolean fin,
  int row, int col, String shape){
    top = initTop;
    bot = initBot;
    left = initLeft;
    right = initRight;
    visited = initVis;
    isStart = start;
    isFin = fin;
    this.row = row;
    this.col = col;
    shape = shape;
  }

  /**
   * @return true if top wall exists
   */

  boolean hasTop(){
    return top;
  }

  /**
   * @return true if bot wall exists
   */
  boolean hasBot(){
    return bot;
  }

  /**
   * @return true if left wall exists
   */
  boolean hasLeft(){
    return left;
  }

  /**
   * @return true if right wall exists
   */
  boolean hasRight(){
    return right;
  }

  /**
    * @return true if square is visited
    */
  boolean isVisited(){
    return visited;
  }

  /**
   * @return true if start Square
   */
  boolean isStart(){
    return isStart;
  }

  /**
   * @return true if finish square
   */
  boolean isFin(){
    return isFin;
  }

  /**
   * @return shape of square
   */
  String getShape(){
    return shape;
  }

  //sets the square as visited
  void setVisited(){
    this.visited = true;
  }

  /**
   * @return row of square
   */
  int getRow(){
    return row;
  }

  /**
   * @return col of square
   */
  int getCol(){
    return col;
  }

  //makes the coordinates what the Maze Square prints
  public String toString(){
    return "("+ row + ", " + col + ")";
  }

  //sets left wall as true or false
  void setLeft(boolean ifLeft){
    this.left = ifLeft;
  }

  //sets right wall as true or false
  void setRight(boolean ifRight){
    this.right = ifRight;
  }

  //sets top wall as true or false
  void setTop(boolean ifTop){
    this.top = ifTop;
  }

  //sets bot wall as true or false
  void setBot(boolean ifBot){
    this.bot = ifBot;
  }

  //sets shape
  void setShape(String shape){
    this.shape = shape;
  }

  //sets finish square
  void setFin(boolean fin){
    this.isFin = fin;
  }

  //sets start square
  void setStart(boolean start){
    this.isStart = start;
  }

  //sets square row
  void setRow(int squareRow){
    this.row = squareRow;
  }

  //sets column of square
  void setCol(int squareCol){
    this.col = squareCol;
  }



}//end of Maze Square class
