// Assignment 02
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implements methods to read and write fasta format files.
 */
public class FastaIO {
    /**
     * Method to read in a fasta file. Generates a {@link Fasta} object for each entry.
     *
     * Pre-implemented code. You do not need to modify this method.
     * 
     * @param filepath {@link String} specifying the path to the fasta file.
     * @return List of {@link Fasta} objects.
     */
    public static List<Fasta> readInFasta(String filepath) {
        List<Fasta> fastaEntries = new ArrayList<>();
        StringBuilder header = new StringBuilder();
        StringBuilder sequence = new StringBuilder();
        Runnable dump = () -> {
            fastaEntries.add(new Fasta(header.toString(), sequence.toString()));
            header.setLength(0);
            sequence.setLength(0);
        };
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filepath))) {
            Iterator<String> lineIterator = fileReader.lines().iterator();
            String line;
            do {
                line = lineIterator.next();
                if (line.startsWith(">")) {
                    if (!header.isEmpty())
                        dump.run();
                    header.append(line.strip());
                } else {
                    sequence.append(line.strip());
                }
            } while (lineIterator.hasNext());
            if (!header.isEmpty())
                dump.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fastaEntries;
    }

    /**
     * Method to write out a list of {@link Fasta} objects to a fasta file.
     * 
     * @TODO Implement this method.
     * 
     * @param fasta
     * @param filepath
     */

    public static void writeOutFasta(List<Fasta> fasta, String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (Fasta entry : fasta) {
                writer.write(entry.getHeader());
                writer.newLine();
                writer.write(entry.getSequence());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}