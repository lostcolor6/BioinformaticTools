// Assignment 02 - Pre-implemented code. You do not need to modify this file.

/**
 * Stores the content of a single FASTA entry.
 */
public class Fasta {
    private String header;
    private String sequence;

    public Fasta(String header, String sequence){
        this.header = header;
        this.sequence = sequence;
    }

    public String getSequence(){
        return this.sequence;
    }

    public String getHeader(){
        return this.header;
    }
}