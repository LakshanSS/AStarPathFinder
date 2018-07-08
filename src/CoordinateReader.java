import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Lakshan on 4/10/18.
 */

//Class CoordinateReader
public class CoordinateReader {

    //Method to mark the blocked cells
    public static void blockCells(Cell[][] grid){
        try {
            //Read from blockedCells.txt
            Scanner sc = new Scanner(new BufferedReader(new FileReader("blockedCells.txt")));
            while(sc.hasNextLine()){
                String coordinates = sc.nextLine();
                //Split the line by ','
                String[] arr=coordinates.split(",");
                //Get the coordinates of the blocked cell
                int i=Integer.parseInt(arr[0]);
                int j=Integer.parseInt(arr[1]);
                //Set the cell as blocked
                grid[i][j].setTraversable(false);
                //Set the weight as 0
                grid[i][j].setWeight(0);
            }
            //Close the Scanner
            sc.close();
        }
        catch (IOException e){
            System.out.println("Error "+e);
        }
    }


    //Method to add weights for the cells
    public static void addCellWeights(Cell[][] grid){
        try {
            //Read from cellWights.txt
            Scanner sc = new Scanner(new BufferedReader(new FileReader("cellWeights.txt")));
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                //Split the line by ','
                String[] arr=line.split(",");
                //Get coordinates and weight of the cell
                int i=Integer.parseInt(arr[0]);
                int j=Integer.parseInt(arr[1]);
                int weight=Integer.parseInt(arr[2]);
                //Set weight of the Cell
                grid[i][j].setWeight(weight);
            }
            //Close the Scanner
            sc.close();
        }
        catch (IOException e){
            System.out.println("Error "+e);
        }
    }



}
