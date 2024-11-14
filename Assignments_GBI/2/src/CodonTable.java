// Assignment 02 - Pre-implemented code. You do not need to modify this file.
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The CodonTable class represents a codon table used for genetic translation.
 * It provides methods to initialize the codon table, read the codon table,
 * and retrieve the corresponding aminoacid for a given codon.
 */
public class CodonTable {
    private Map<String, String> codonTable;

    /**
     * Constructs a CodonTable object and initializes the codon table.
     *
     * @param codonTablePath The path to the file containing the codon table.
     */
    public CodonTable(String codonTablePath) {
        this.codonTable = readCodonTable(codonTablePath);
    }

    /**
     * Initializes the codon table by reading the codon table from a file.
     * 
     * @param path the path to the file containing the codon table
     * @return the initialized codon table
     */
    private Map<String, String> readCodonTable(String path) {
        Map<String, String> codonTable = new HashMap<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            // Skip the header lines
            for (int i = 0; i < 2; i++) {
                scanner.nextLine();
            }
        
            // Read codon table
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t");
                if (parts.length >= 2) {
                    String codon = parts[0];
                    String aminoAcid = parts[2];
                    codonTable.put(codon, aminoAcid);
                }
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return codonTable;
    }

    /**
     * Sets the codon table.
     *
     * @param codonTable the codon table to set
     */
    public void setCodonTable(Map<String, String> codonTable) {
        this.codonTable = codonTable;
    }

    /**
     * Retrieves the codon table.
     *
     * @return The codon table
     */
    public Map<String, String> getCodonTable() {
        return this.codonTable;
    }

    /**
     * Retrieves the aminoacid corresponding to the given codon.
     *
     * @param codon the codon for which to retrieve the aminoacid.
     * @return The aminoacid corresponding to the given codon, or null if the codon is not found in the table.
     */
    public String getAminoacid(String codon) {
        return this.codonTable.get(codon);
    }

    /**
     * Prints the codon table to stdout.
     */
    public void printCodonTable() {
        for (Map.Entry<String, String> entry : this.codonTable.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
