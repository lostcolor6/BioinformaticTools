package ReadWriteTools;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Write {

    /**
     * convert a data[][] to a file (.txt) with give seperation and a first header line
     * and save it to a new path
     * @param data
     * @param outputPath
     * @param firstline
     * @param seperationSymbol
     *
     */
    public static void write(String[][] data, String outputPath, String firstline, String seperationSymbol) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            //add the original frist (header) Line to the new file
            writer.write(firstline);
            writer.newLine();

            for (String[] row : data) {
                writer.write(String.join(seperationSymbol, row));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Modified data saved to: " + outputPath);
    }
}
