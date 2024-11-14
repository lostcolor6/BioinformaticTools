// Assignment 02 - Task 03

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class provides methods to translate nucleotide sequences to protein sequences using a codon table.
 */



    public class TranslateNucleotides {

        private static final String studentNameA = "Polina Novikova";
        private static final String studentNameB = "Marius Maier";

    /**
     * Translates the given nucleotide sequence to protein sequences using the provided codon table.
     *
     *
     *
     * @param fasta     the nucleotide sequence to be translated
     * @param codonTable the codon table used for translation
     * @return a list of translated protein sequences
     */

        public static List<Fasta> translateNucleotides(Fasta fasta, CodonTable codonTable) {
            List<Fasta> translatedProteins = new ArrayList<>();

            //get the seq. (sequence) from the fasta fromat as a string
            String nucleotideSequence = fasta.getSequence();

            //then get the reverse complement of that seq. string
            String complementSequence = new StringBuilder(getComplementSequence(nucleotideSequence)).reverse().toString();


            //translate in all possible reading frames from original sequence
            for (int frame = 0; frame < 3; frame++) {
                String proteinSequence = translateFrame(nucleotideSequence, frame, codonTable);
                String header = fasta.getHeader() + " Frame " + (frame + 1);
                translatedProteins.add(new Fasta(header, proteinSequence));
            }

            //translate frames for the complement sequence
            for (int frame = 0; frame < 3; frame++) {
                String proteinSequence = translateFrame(complementSequence, frame, codonTable);
                String header = fasta.getHeader() + " Complement Frame " + (frame + 1);
                translatedProteins.add(new Fasta(header, proteinSequence));
            }
            return translatedProteins;
        }


    /**
     * Converts the given sequence to its complement seq.
     * @param sequence seq
     * @return complement of seq as a string
     */
    private static String getComplementSequence(String sequence) {
            StringBuilder complementSequence = new StringBuilder();
        //iterate over the whole seq.
            for (int i = 0; i < sequence.length(); i++) {
                //read the given string and assign current nucleotide = position i of sequence string
                char nucleotide = sequence.charAt(i);
                //case does a char match? -> append the complement to the stringbuilder
                switch (nucleotide) {
                    case 'A':
                        complementSequence.append('T');
                        break;
                    case 'T':
                        complementSequence.append('A');
                        break;
                    case 'C':
                        complementSequence.append('G');
                        break;
                    case 'G':
                        complementSequence.append('C');
                        break;
                    default:
                        //for any other characters, treat as unknown
                        complementSequence.append('N');
                        break;
                }
            }
            //convert stringbuilder to string and return the complement
            return complementSequence.toString();
        }

    /**
     * Method to translate all 3 Frames of a given sequence with the provided codon Table
     *
     * @param sequence seq
     * @param frame frame 0 1 2
     * @param codonTable codon table
     * @return a translated seq (based on the codon Table)
     */
        private static String translateFrame(String sequence, int frame, CodonTable codonTable) {
            StringBuilder proteinSequence = new StringBuilder();
            int length = sequence.length();

            /*
             * Start translating from the given frame (frame 1 would be input 0, see below in main)
             * i < length - 2 This ensures that we have at least three nucleotides left to form a codon.
             * i += 3 This moves the loop to the next codon in the sequence.
             */

            for (int i = frame; i < length - 2; i += 3) {
                //extracts a substring of length 3 from the nucleotide sequence, starting at index i. This substring represents a codon.
                String codon = sequence.substring(i, i + 3);
                //Looks up the amino acid corresponding to the codon in the codon table.
                String aminoacid = codonTable.getAminoacid(codon);

                //check if its a valid codon
                if (aminoacid != null) {
                    proteinSequence.append(aminoacid);
                } else {
                    //if not found its unknown X
                    proteinSequence.append('X');
                }
            }

            //most of the lengths (minus the Stopcodon O) and translation it gets right compared to the ORF finder Site recommended in the Tutorium, yet some it doesnt (4 out of 6 right)
            //System.out.println(findLongestSubstring(proteinSequence.toString().toCharArray()).length());
            return findLongestSubstring(proteinSequence.toString().toCharArray());


        }


    /**
     * Method to determin the longest ORF from a proteinseq.
     * @param sequence Seq
     * @return longest ORF
     */
    public static String findLongestSubstring(char[] sequence) {
        List<String> substrings = new ArrayList<>();

        //iterate through the character array
        for (int i = 0; i < sequence.length; i++) {
            //check for the starting character "M"
            if (sequence[i] == 'M') {
                StringBuilder subSequence = new StringBuilder();
                int endIndex = -1;

                //find the end index of the substring
                for (int j = i; j < sequence.length; j++) {
                    // Check for the ending character "O"
                    if (sequence[j] == 'O') {
                        endIndex = j;
                        break;
                    }
                }

                //if "O" is found, construct the substring
                if (endIndex != -1) {
                    for (int k = i; k <= endIndex; k++) {
                        subSequence.append(sequence[k]);
                    }
                    //add the found substring to the list
                    substrings.add(subSequence.toString());
                }

                //move the starting index to the end of the found substring
                i = endIndex;
            }
        }

        //find the longest substring from the list
        String longestSubstring = "";
        for (String substring : substrings) {

            if (substring.length() > longestSubstring.length()) {
                longestSubstring = substring;
            }
        }
        return longestSubstring;
    }

    /**
     * Main to organize calling of functions
     *
     * @param args User input/ Arguments
     * user input 1: a file path to a fasta file
     * user input 2: a codon table for translating the seq to amino acids
     * user input 3: a file path where user wants to save the new file, how the file should be called and in what format
     *
     * example:
     *  D:\GIT\assignment-2-maier-novikova\data\ unknown.fasta    //fasta file with seq that wants to be translated
     *  D:\GIT\assignment-2-maier-novikova\data\codon_table.tsv  //codon table that should be used for translation
     *  D:\GIT\assignment-2-maier-novikova\data\known.fasta      //new file with all 6 frame translations of seq.
     *
     *
     * known.fasta consists the translated frames of unknown.fasta
     */
    public static void main(String[] args) {
            System.out.println("GBI - Assignment 02 Task 03 - " + studentNameA + ", " + studentNameB);

            if (args.length != 3) {
                System.out.println("Usage: TranslateNucleotides <input.fasta> <codon_table.txt> <output.fasta>");
                return;
            }
            //these are the user inputs from commandline
            String inputFilePath = args[0];
            String codonTablePath = args[1];
            String outputFilePath = args[2];

            //create new fasta IO for read and writing
            FastaIO fastaIO = new FastaIO();
            //create new codon table
            CodonTable codonTable = new CodonTable(codonTablePath);

            //read fasta file entries
            List<Fasta> inputSequences = FastaIO.readInFasta(inputFilePath);
            //create a list to store translated sequences
            List<Fasta> translatedSequences = new ArrayList<>();

            //translate each input seq and add the translated sequences to the list
            for (Fasta inputSequence : inputSequences) {
                //translate nucleotide sequence to protein sequences using the provided codon table
                List<Fasta> translated = translateNucleotides(inputSequence, codonTable);
                //add the translated sequences to the list
                translatedSequences.addAll(translated);

            }

            //write out the translated sequences to the output fasta file
            FastaIO.writeOutFasta(translatedSequences, outputFilePath);

        }
    }

