package ReadWriteTools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Read {

    /**
     * Function that reads a file, ignores the header (first line) and
     * stores the cells in an array of array where u can access data[column][rows]
     *
     * @param filePath
     * @param splitat
     * @return array: data[column][rows]
     */
    public static String[][] readandSplitatEach(String filePath, String splitat) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true; //ignore the first line (header)
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header line
                }
                String[] parts = line.split(splitat);
                rows.add(parts);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Convert List<String[]> to String[][]
        String[][] data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }
        return data;
    }
}
