/**
 * Created by Lakshan on 4/10/18.
 */

//Class Cell
public class Cell {
    private int x;//x coordinate of the Cell
    private int y;//y coordinate of the Cell
    private int weight=1;//weight of the Cell
    private double gCost;//cost of the path from start Cell to this Cell
    private double hCost;//estimated cost of the path from this Cell to the end Cell
    private boolean isTraversable=true;//To show a cell is not blocked
    private Cell parent;//Parent of the Cell

    //Constructor with x and y
    public Cell(int x, int y){
        this.x=x;
        this.y=y;
    }

    //Constructor with x,y and isTraversable
    public Cell(int x,int y,boolean isTraversable){
        this.x=x;
        this.y=y;
        this.isTraversable=isTraversable;
    }

    //Constructor with x,y,weight and isTraversable
    public Cell(int x,int y,int weight,boolean isTraversable){
        this.x=x;
        this.y=y;
        this.weight=weight;
        this.isTraversable=isTraversable;
    }

    //Method to get fCost
    public double getFCost(){
        return this.gCost+this.hCost;
    }

    //Getter for X
    public int getX() {
        return x;
    }

    //Setter for X
    public void setX(int x) {
        this.x = x;
    }

    //Getter for Y
    public int getY() {
        return y;
    }

    //Setter for Y
    public void setY(int y) {
        this.y = y;
    }

    //Getter for weight
    public int getWeight() {
        return weight;
    }

    //Setter for weight
    public void setWeight(int weight) {
        this.weight = weight;
    }

    //Getter for gCost
    public double getgCost() {
        return gCost;
    }

    //Setter for gCost
    public void setgCost(double gCost) {
        this.gCost = gCost;
    }

    //Getter for hCost
    public double gethCost() {
        return hCost;
    }

    //Setter fot hCost
    public void sethCost(double hCost) {
        this.hCost = hCost;
    }

    //Getter for isTraversable
    public boolean isTraversable() {
        return isTraversable;
    }

    //Setter for isTraversable
    public void setTraversable(boolean traversable) {
        isTraversable = traversable;
    }

    //Getter for parent
    public Cell getParent() {
        return parent;
    }

    //Setter for parent
    public void setParent(Cell parent) {
        this.parent = parent;
    }
}
