/**
 * Assignment 01 - Task 02
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//new imports


public class FastaReader {
    /**
     * Adjust your names here:
     */
    private static final String studentNameA = "Polina Novikova";
    private static final String studentNameB = "Marius Maier";

    /**
     * Method to read in a fasta file. It generates a new fasta objects for each entry.
     *
     * @param filepath The path to the fasta file
     * @return A list of Fasta objects
     */
    public static List<Fasta> readInFasta(String filepath) {

        List<Fasta> fastaEntries = new ArrayList<>();


        try (BufferedReader buffreader = new BufferedReader(new FileReader(filepath))) {
            String header = null;
            StringBuilder sequence = new StringBuilder();

            String line;
            //while loop that reads file line by line
            while ((line = buffreader.readLine()) != null) {
                //if line starts with ">" it indicates new entry
                if (line.startsWith(">")) {
                    //if header is not null, it means we have encountered a new entry
                    if (header != null) {

                        //create a Fasta object with the collected header and sequence
                        fastaEntries.add(new Fasta(header, sequence.toString()));
                        //reset sequence StringBuilder
                        sequence = new StringBuilder();
                    }
                    //update header to the new header line
                    header = line;
                } else {
                    //append the line to the sequence StringBuilder
                    sequence.append(line);
                }
            }
            //add the last entry after reaching the end of the file
            if (header != null) {
                fastaEntries.add(new Fasta(header, sequence.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fastaEntries;
    }

    /**
     * Get the sequence length from a Fasta instance.
     *
     * @param fasta A Fasta object
     * @return The length of the sequence
     */
    public static int calculateSequenceLength(Fasta fasta) {
        int length = 0;

        String sequence = fasta.getSequence();
        length = sequence.length();

        return length;
    }

    /**
     * Calculates base frequency from a Fasta instance.
     *
     * @param fasta A Fasta object
     * @return A map of base frequencies
     */
    public static Map<Character, Integer> calculateBaseFrequency(Fasta fasta) {
        Map<Character, Integer> baseFrequencies = new HashMap<>();


        String sequence = fasta.getSequence();

        //initalize Base Frequency to 0 for all at the beginning
       /*baseFrequencies.put('A', 0);
        baseFrequencies.put('T', 0);
        baseFrequencies.put('C', 0);
        baseFrequencies.put('G', 0);*/
        //not even needed anymore because of the else statement below

        // go through every Base in the sequence and update Frequency
        for (char base : sequence.toCharArray()) {
            if (baseFrequencies.containsKey(base)) {
                // if the base is already in the map increase frequency by one
                baseFrequencies.put(base, baseFrequencies.get(base) + 1);
            } else {
                // if not add the base and put the frequency to one
                baseFrequencies.put(base, 1);
            }
        }
        return baseFrequencies;


    }

    /**
     * Converts the sequence stored in a Fasta instance into its corresponding RY sequence and returns it.
     *
     * @param fasta A Fasta object
     * @return The RY sequence
     */
    public static String calculateRYSequence(Fasta fasta) {
        StringBuilder rySequenceBuilder = new StringBuilder();

        String sequence = fasta.getSequence();
        //converting String sequence to char array and for each char run helpermethod convertRYbase
        for (char base : sequence.toCharArray()) {
            char ryBase = convertRYbase(base);
            //append the new base RY to the sequence
            rySequenceBuilder.append(ryBase);
        }
        //convert back to String
        return rySequenceBuilder.toString();
    }

    /**
     * returns the corresponding RY base for a given base.
     *
     * @param base The input base
     * @return corresponding RY base
     */
    private static char convertRYbase(char base) {

        // R = purine (A or G)
        //Y = Pyrimidine (T or C)

        /*
        //Ursprüngliche Version
        if(base =='A' ){
            return 'R';
        } else if (base =='T' ) {
           return  'Y';
        } else if (base =='C') {
            return  'Y';
        }else if (base == 'G'){
            return  'R';
        }else{
            //fehler
            return 'X';
        }

         */

        //new version with switch cases and more readability
        switch (base) {
            case 'A':
                return 'R';
            case 'T':
                return 'Y';
            case 'C':
                return 'Y';
            case 'G':
                return 'R';
            default:
                // return the original base if none match the cases above
                return base;
        }


    }


    /**
     * Helper function for displaying analysis/ stats of a Fasta file as String
     *
     * @param filepath
     * @return Output of the Fasta instance read in console
     */
    public static void FastaReaderDisplay(String filepath) {
        List<Fasta> fastaobjects = readInFasta(filepath);
        for (Fasta fasta : fastaobjects) {
            System.out.println("Sequence Header: " + fasta.getHeader());

            System.out.println("Length: " + calculateSequenceLength(fasta));

            Map<Character, Integer> baseFrequenciesresult = calculateBaseFrequency(fasta);


            int sumFrequency = baseFrequenciesresult.get('A') + baseFrequenciesresult.get('T') + baseFrequenciesresult.get('C') + baseFrequenciesresult.get('G');
            //calculate percentages and round to nearest integer
            int percentA = Math.round((float) baseFrequenciesresult.get('A') / sumFrequency * 100);
            int percentT = Math.round((float) baseFrequenciesresult.get('T') / sumFrequency * 100);
            int percentC = Math.round((float) baseFrequenciesresult.get('C') / sumFrequency * 100);
            int percentG = Math.round((float) baseFrequenciesresult.get('G') / sumFrequency * 100);

            //from number to percentage the way its also displayed in the workflow tests
            System.out.println("Base Frequency: "
                    + "A= " + percentA + " "
                    + "T= " + percentT + " "
                    + "C= " + percentC + " "
                    + "G= " + percentG
            );


            System.out.println("RY Sequence:" + calculateRYSequence(fasta));

            System.out.println("Sequence: " + fasta.getSequence());

            System.out.println(); //add a newline for better readability
        }
    }

    /**
     * main with Tests
     *
     * IMPORTANT!:
     * currently the code can run in Git bash terminal using:
     * Make sure you are in the correct folders otherwise navigate to them with "cd" command or right click and "open git bash here" on Win10
     *
     * javac FastaReader.java
     * java FastaReader filepath
     *
     * For example:
     *
     * javac FastaReader.java
     *
     * java FastaReader D:/GIT/assignment-1-maier-novikova/data/single-sequence.fasta
     *
     *
     *
     *
     * I dont know how to make it run with "FastaReader.java file.fasta".....
     *
     *
     * these Test inputs below will run before the given terminal command (but you can comment them out)
     *
     * @param args
     */

    public static void main(String[] args) throws IOException {
        System.out.println("GBI - Assignment 1 Task 2 - " + studentNameA + ", " + studentNameB);





        System.out.println("Output for single sequence:");
        FastaReaderDisplay("D:\\GIT\\assignment-1-maier-novikova\\data\\single-sequence.fasta");

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        System.out.println("Output for multiple sequences:");
        FastaReaderDisplay("D:\\GIT\\assignment-1-maier-novikova\\data\\multi-sequence.fasta");



        /**
         * because of the functionality to run from console with "FastaReader.java file.fasta"
         */
        //check if the correct number of arguments is provided (in this case 1)
        if (args.length != 1) {
            System.out.println("Usage: java FastaReader <file_path>");
            return;
        }

        //call method to read and display fasta file information

        FastaReaderDisplay(args[0]);


    }
}