/**
 * Class Fasta
 * Used to create a Fasta object
 * You don't need to change this class
 */
public class Fasta {
    private String header;
    private String sequence;

    /**
     * Constructs a new Fasta object with the given header and sequence.
     *
     * @param header   the header of the Fasta object
     * @param sequence the sequence of the Fasta object
     * @throws IllegalArgumentException if header or sequence is null or empty
     */
    public Fasta(String header, String sequence){
        if (header == null || header.isEmpty() || sequence == null || sequence.isEmpty()) {
            throw new IllegalArgumentException("Header and sequence must not be null or empty");
        }
        this.header = header;
        this.sequence = sequence;
    }

    /**
     * Returns the sequence of the Fasta object.
     *
     * @return the sequence
     */
    public String getSequence(){
        return this.sequence;
    }

    /**
     * Returns the header of the Fasta object.
     *
     * @return the header
     */
    public String getHeader(){
        return this.header;
    }
}