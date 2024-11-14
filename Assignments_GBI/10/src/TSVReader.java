// Assignment 10 - Pre-implemented code. You do not need to modify this file.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Class to parse TSV format files.
 */
public class TSVReader {

    /**
     * Method to read in a tsv file. Generates an {@link ArrayList} of String Array objects representing the rows of the file.
     * <p>
     * The parameter skipRows can be used to skip the first x rows (e.g. to ignore header data).
     *
     * @param filepath {@link String} specifying the path to the TSV file.
     * @param skipRows {@link Integer} number of rows to skip.
     * @return List of String Array objects. The row data of the file.
     */
    public static List<String[]> readTSV(String filepath, int skipRows) {
        List<String[]> rowData = new ArrayList<>();
        Consumer<String> dump = (r) -> {
            r = r.strip();
            rowData.add(r.split("\\t"));
        };
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filepath))) {
            Iterator<String> lineIterator = fileReader.lines().iterator();
            String line;
            do {
                line = lineIterator.next();
                if (skipRows > 0) {
                    skipRows--;
                    continue;
                }
                dump.accept(line);
            } while (lineIterator.hasNext());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rowData;
    }

}
