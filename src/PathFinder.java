import java.util.*;


/**
 * Created by Lakshan on 4/10/18.
 */


//Class PathFinder
public class PathFinder {

    private double euclideanTotCost;
    private double manhattanTotCost;
    private double chebyshevTotCost;

    public double getEuclideanTotCost() {
        return euclideanTotCost;
    }

    public void setEuclideanTotCost(double euclideanTotCost) {
        this.euclideanTotCost = euclideanTotCost;
    }

    public double getManhattanTotCost() {
        return manhattanTotCost;
    }

    public void setManhattanTotCost(double manhattanTotCost) {
        this.manhattanTotCost = manhattanTotCost;
    }

    public double getChebyshevTotCost() {
        return chebyshevTotCost;
    }

    public void setChebyshevTotCost(double chebyshevTotCost) {
        this.chebyshevTotCost = chebyshevTotCost;
    }


    //Method to backtrace the path
    public static void backtracePath(Cell startCell, Cell endCell, int sizeOfGrid, int penColour) {
        //Create an arrayList
        List<Cell> path = new ArrayList<>();

        //Set currentCell as the endCell
        Cell currentCell = endCell;

        // add Cells to the path arrayList until the start cell is found
        while (!currentCell.equals(startCell)) {
            path.add(currentCell);
            currentCell = currentCell.getParent();
        }


        // Draw paths on the grid
        switch (penColour) {
            case 0:// set green for manhattan
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.setPenRadius(.01);
                break;
            case 1: // set blue for chebyshev
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(.009);
                break;
            case 2: // set red for euclidean
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(.005);
                break;
        }

        Cell previousCell = endCell;
        for (int i = 1; i < path.size(); i++) {
            Cell tempCell = path.get(i);
            StdDraw.line(previousCell.getY(), sizeOfGrid - previousCell.getX() - 1, tempCell.getY(), sizeOfGrid - tempCell.getX() - 1);
            previousCell = tempCell;
        }

        StdDraw.line(previousCell.getY(), sizeOfGrid - previousCell.getX() - 1, startCell.getY(), sizeOfGrid - startCell.getX() - 1);


    }

    //Method to get Manhattan distance between two cells
    public static int getManhattanDistance(Cell c1, Cell c2) {
        int xDistance = Math.abs(c1.getX() - c2.getX());
        int yDistance = Math.abs(c1.getY() - c2.getY());

        if (xDistance > yDistance) {
            return 2 * yDistance + (xDistance - yDistance);
        }
        return 2 * xDistance + (yDistance - xDistance);
    }

    //Method to get Chebysev distance between two cells
    public static int getChebyshevDistance(Cell c1, Cell c2) {
        int xDistance = Math.abs(c1.getX() - c2.getX());
        int yDistance = Math.abs(c1.getY() - c2.getY());

        if (xDistance > yDistance) {
            return yDistance + (xDistance - yDistance);
        }
        return xDistance + (yDistance - xDistance);
    }

    //Method to get Euclidean distance between two cells
    public static double getEuclideanDistance(Cell c1, Cell c2) {
        int xDistance = Math.abs(c1.getX() - c2.getX());
        int yDistance = Math.abs(c1.getY() - c2.getY());

        if (xDistance > yDistance) {
            return Math.sqrt(2) * yDistance + (xDistance - yDistance);
        }
        return Math.sqrt(2) * xDistance + (yDistance - xDistance);
    }

    //Find a Cell by giving it's x and y coordinates
    public static Cell getCell(int x, int y, Cell[][] grid) {
        for (Cell[] column : grid) { // looping columns
            for (Cell row : column) { // looping grids
                if (row.getX() == x && row.getY() == y) { // position found
                    Cell c = row;
                    return c;
                }
            }
        }
        return null; // Cell not found
    }

    //Get neighour cells of a cell
    public static List<Cell> getNeighbours(Cell cell, Cell[][] grid, char distanceMetric) {
        int gridSize = grid.length;

        //Create an arrayList
        List<Cell> neighbours = new ArrayList<Cell>();

        // searching neighbours the given cell
        for (int x = -1; x <= 1; x++) { // looping columns
            for (int y = -1; y <= 1; y++) { // looping rows
                switch (distanceMetric) { // filter neighbours accordingly
                    case 'M':
                        if ((x == 0 && y == 0)
                                || (x == -1 && y == -1)
                                || (x == 1 && y == 1)
                                || (x == -1 && y == 1)
                                || (x == 1 && y == -1)) { // skip the position of the given cell
                            continue;
                        }
                        break;
                    case 'E':
                        if (x == 0 && y == 0) { // skip the position of the given cell
                            continue;
                        }
                        break;
                    case 'C':
                        if (x == 0 && y == 0) { // skip the position of the given cell
                            continue;
                        }
                        break;
                    default:
                        break;
                }

                // to check whether the actual position is in range of the grid by adding the hypothetical positin it
                int checkX = cell.getX() + x;
                int checkY = cell.getY() + y;

                // check if the cell lies within the range of the grid
                if (checkX >= 0 && checkX < gridSize && checkY >= 0 && checkY < gridSize) {
                    // find the position of the neighbour from the grid
                    neighbours.add(getCell(checkX, checkY, grid));
                }
            }
        }
        return neighbours;
    }

    //Method to find path by Manhattan
    public  void findPathByManhattan(Cell startCell, Cell endCell, Cell[][] grid) {
        int gridSize = grid.length;

        // this queue will prioritize cells based on the lowest fCost
        PriorityQueue<Cell> openSet = new PriorityQueue<>(8, new CellComparator());
        HashSet<Cell> closedSet = new HashSet<>(); // cells that are already evaluated

        openSet.add(startCell); // initially the open set will only have the startCell

        while (!openSet.isEmpty()) { // evaluating each cell in open set
            Cell currentCel = openSet.poll(); // the cell with lowest fCost, initially the first element


            closedSet.add(currentCel);

            if (currentCel == endCell) { // destination cell is found
                manhattanTotCost = currentCel.getgCost();
                backtracePath(startCell, endCell, gridSize, 0); // trace the path
                return; // exit the while loop
            }

            for (Cell neighbour : getNeighbours(currentCel, grid, 'M')) {

                // skip the iteration if the neighbor is a blocked cell or already traversed one
                if (!neighbour.isTraversable() || closedSet.contains(neighbour)) {
                    continue;
                }

                double newMovementCostToNeighbour = currentCel.getgCost() + neighbour.getWeight()+getManhattanDistance(currentCel, neighbour);
                if (newMovementCostToNeighbour < neighbour.getgCost() || !openSet.contains(neighbour)) {
                    neighbour.setgCost(newMovementCostToNeighbour);
                    neighbour.sethCost(getManhattanDistance(neighbour, endCell));
                    neighbour.setParent(currentCel);

                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }
    }


    //Method to find path by Chebyshev
    public  void findPathByChebyshev(Cell startCell, Cell endCell, Cell[][] grid) {
        int gridSize = grid.length;

        // this queue will prioritize nodes based on the lowest f_cost
        PriorityQueue<Cell> openSet = new PriorityQueue<>(8, new CellComparator());
        HashSet<Cell> closedSet = new HashSet<>(); // nodes that are already evaluated

        openSet.add(startCell); // initially the open set will only have the startCell

        while (!openSet.isEmpty()) { // evaluating each cell in open set
            Cell currentCell = openSet.poll(); // the cell with lowest fCost, initially the first element
            closedSet.add(currentCell);

            if (currentCell == endCell) { // destination cell is found
                chebyshevTotCost = currentCell.getgCost();
                backtracePath(startCell, endCell, gridSize, 1); // trace the path
                return; // exit the while loop
            }

            for (Cell neighbour : getNeighbours(currentCell, grid, 'C')) {
                // skip thw iteration if the neigbor is a blocked cell or already traversed one
                if (!neighbour.isTraversable() || closedSet.contains(neighbour)) {
                    continue;
                }

                double newMovementCostToNeighbour = currentCell.getgCost() +neighbour.getWeight()+ getChebyshevDistance(currentCell, neighbour);
                if (newMovementCostToNeighbour < neighbour.getgCost() || !openSet.contains(neighbour)) {
                    neighbour.setgCost(newMovementCostToNeighbour);
                    neighbour.sethCost(getChebyshevDistance(neighbour, endCell));
                    neighbour.setParent(currentCell);

                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }
    }

    //Method to find path by Euclidean
    public  void findPathByEuclidean(Cell startCell, Cell endCell, Cell[][] grid) {
        int gridSize = grid.length;

        // this queue will prioritize nodes based on the lowest fCost
        PriorityQueue<Cell> openSet = new PriorityQueue<>(8, new CellComparator());
        HashSet<Cell> closedSet = new HashSet<>(); // coells that are already evaluated

        openSet.add(startCell); // initially the open set will only have the startCell

        while (!openSet.isEmpty()) { // evaluating each cell in open set
            Cell currentCell = openSet.poll(); // the cell with lowest fCost, initially the first element
            closedSet.add(currentCell);

            if (currentCell == endCell) { // destination cell is found
                euclideanTotCost = currentCell.getgCost();
                backtracePath(startCell, endCell, gridSize, 2); // trace the path
                return; // exit the while loop
            }

            for (Cell neighbour : getNeighbours(currentCell, grid, 'E')) {
                // skip thw iteration if the neigbor is a blocked cell or already traversed one
                if (!neighbour.isTraversable() || closedSet.contains(neighbour)) {
                    continue;
                }

                double newMovementCostToNeighbour = currentCell.getgCost() + neighbour.getWeight()+getEuclideanDistance(currentCell, neighbour);
                if (newMovementCostToNeighbour < neighbour.getgCost() || !openSet.contains(neighbour)) {
                    neighbour.setgCost(newMovementCostToNeighbour);
                    neighbour.sethCost(getEuclideanDistance(neighbour, endCell));
                    neighbour.setParent(currentCell);

                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }

    }

    //Method to mark Start and End Cells on the GUI
    public static void markStartAndEndCells(Cell[][] a, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                    StdDraw.setPenColor((i == x1 && j == y1) ? StdDraw.ORANGE : StdDraw.MAGENTA);
                    StdDraw.filledCircle(j, N - i - 1, .5);
                }
            }
        }
    }

    //Method to display the Grid
    public static void displayGrid(Cell[][] a) {
        int N = a.length;
        StdDraw.setXscale(-1, N);;
        StdDraw.setYscale(-1, N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //if the cell is traversable
                if (a[i][j].isTraversable()) {
                    //color = light gray if the weight is 2
                    if(a[i][j].getWeight()==2){
                        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                        StdDraw.filledSquare(j, N - i - 1, .5);
                    }
                    //color = gray if the weight is 3
                    else if(a[i][j].getWeight()==3){
                        StdDraw.setPenColor(StdDraw.GRAY);
                        StdDraw.filledSquare(j, N - i - 1, .5);
                    }
                    //color = dark gray if the weight is 4
                    else if(a[i][j].getWeight()==4){
                        StdDraw.setPenColor(StdDraw.DARK_GRAY);
                        StdDraw.filledSquare(j, N - i - 1, .5);
                    }

                }
                //If the cell is blocked
                else {
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                    StdDraw.filledSquare(j, N - i - 1, .5);
                }

                StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                StdDraw.square(j, N - i - 1, .5);
            }
        }
    }

    /**
     * Print the M-by-N array of Cells to standard output.
     */
    public static void printGrid(Cell[][] a) {
        int M = a.length;
        int N = a[0].length;
        StdOut.println(M + " " + N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                //if the cell is traversable
                if (a[i][j].isTraversable()) {
                    StdOut.print(a[i][j].getWeight()+" ");
                }
                //If the cell is blocked
                else {
                    StdOut.print("- ");
                }
            }
            StdOut.println();
        }
    }

    //Create a grid with random blocks
    public static Cell[][] createRandomGrid(int N, double p) {
        Cell[][] a = new Cell[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = new Cell( i, j,StdRandom.bernoulli(p));
            }
        }
        return a;
    }

    //Create a grid without any blocks
    public static Cell[][] createGrid(int N) {
        Cell[][] a = new Cell[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = new Cell( i, j);
            }
        }
        return a;
    }
}
