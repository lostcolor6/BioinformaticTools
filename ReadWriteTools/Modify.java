package ReadWriteTools;

public class Modify {

    /**
     * function takes an 2d array (matrix) and can change String contents of a specific cell
     * @param data
     * @param rowIndex
     * @param columnIndex
     * @param change
     * @return
     */
    public static String[][] overwriteCell(String[][] data,
                                           int rowIndex,
                                           int columnIndex,
                                           String change){

        String[][] modifiedData = data;
        data[rowIndex][columnIndex] = change;



        return modifiedData;

    }
}
