import java.util.Comparator;

/**
 * Created by Lakshan on 4/10/18.
 */

//Class CellComparator

public class CellComparator implements Comparator<Cell>{

    @Override
    public int compare(Cell c1,Cell c2){

        //Get the cell with the lowest fCost
        if(c1.getFCost() < c2.getFCost()){
            return -1;
        }else if(c1.getFCost() > c2.getFCost()){
            return 1;
        }

        //If fCost is equal, get the cell with lowest hCost
        else if(c1.gethCost() < c2.gethCost()){
            return -1;
        }else if (c1.gethCost() > c2.gethCost()){
            return 1;
        }else{
            return 0;
        }
    }
}
