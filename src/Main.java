import java.util.Scanner;

/**
 * Created by Lakshan on 4/10/18.
 */

//Class Main
public class Main {

    //Size of the grid
    private static final int gridSize=20;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
         System.out.println("---A Star Path Finder---");
         findPath();
    }

    //Method to find path
    public static void findPath(){
        //Create a PathFinder Object
        PathFinder pf = new PathFinder();

        //Create the grid
        Cell[][] myGrid = PathFinder.createGrid(gridSize);

        //Mark the blocked Cells
        CoordinateReader.blockCells(myGrid);

        //Add weights for Cells
        CoordinateReader.addCellWeights(myGrid);

        //Print the grid on the command line
        PathFinder.printGrid(myGrid);

        //Display the grid in GUI
        PathFinder.displayGrid(myGrid);

        //Create a Scanner Object
        Scanner in = new Scanner(System.in);

        //Get coordinates of StartCell and EndCell from the user
        System.out.println("Enter i for Start Cell:");
        int iStart = in.nextInt();

        System.out.println("Enter j for Start Cell:");
        int jStart = in.nextInt();

        System.out.println("Enter i for End Cell:");
        int iEnd = in.nextInt();

        System.out.println("Enter j for End Cell:");
        int jEnd = in.nextInt();

        //Mark the StartCell and EndCell on the GUI
        PathFinder.markStartAndEndCells(myGrid, iStart, jStart, iEnd, jEnd);

        //StartCell and EndCell
        Cell startCell = myGrid[iStart][jStart];
        Cell endCell = myGrid[iEnd][jEnd];

        //Print the coordinates of startCell and endCell
        StdOut.println("StartCell: ("+startCell.getX() + "," + startCell.getY()+")");
        StdOut.println("StartCell: ("+endCell.getX() + "," + endCell.getY()+")");

        //Create a stopWatch
        Stopwatch sw = new Stopwatch();

        //Find Paths and draw them
        pf.findPathByManhattan(startCell, endCell, myGrid);
        pf.findPathByChebyshev(startCell, endCell, myGrid);
        pf.findPathByEuclidean(startCell, endCell, myGrid);

        //Print the elapsed time
        StdOut.println("\nElapsed time : "+sw.elapsedTime()+"s");

        //Print the total costs for paths
        StdOut.println("Total Cost for Manhattan (green) : " + pf.getManhattanTotCost());
        StdOut.println("Total Cost for Chebyshev (blue) : " + pf.getChebyshevTotCost());
        StdOut.println("Total Cost for Euclidean (red) : " + pf.getEuclideanTotCost());
    }
}
