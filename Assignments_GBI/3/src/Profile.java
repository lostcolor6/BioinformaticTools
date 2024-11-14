// Assignment 03 - Pre-implemented code. You do not need to modify this file.

import java.util.ArrayList;

/**
 * Stores one unaligned or multiple aligned sequences.
 */
public class Profile {

    /**
     * The sequences currently stored in this profile.
     */
    private final ArrayList<String> sequences = new ArrayList<>();

    /**
     * Returns an empty sequence profile.
     */
    public Profile() {

    }

    /**
     * Adds a new sequence to the end of the sequence list.
     *
     * @param sequence The sequence to add.
     */
    public void addSequenceToProfile(String sequence) {
        this.sequences.add(sequence);
    }

    /**
     * Adds a new sequence to a specific index of the sequence list.
     *
     * @param sequence The sequence to add.
     * @param index    The position in the sequence list (0-based) to add the profile to.
     */
    public void addSequenceToProfile(String sequence, int index) {
        this.sequences.add(index, sequence);
    }

    /**
     * Removes the sequence stored at index from the sequence list (0-based).
     *
     * @param index The index of the sequence to remove.
     */
    public void removeSequenceFromProfile(int index) {
        this.sequences.remove(index);
    }

    /**
     * Returns the sequence stored at index from the sequence list (0-based).
     *
     * @param index The index of the sequence to return.
     */
    public String getSequenceFromProfile(int index) {
        return this.sequences.get(index);
    }

    /**
     * Returns the number of sequences stored in this profile.
     *
     * @return The number of stored sequences.
     */
    public int getNoSequences() {
        return this.sequences.size();
    }

    @SuppressWarnings("DuplicatedCode")
    public static Profile combineProfiles(Profile profile1, Profile profile2, ArrayList<Integer> gapsProfile1, ArrayList<Integer> gapsProfile2) {
        Profile combinedProfile = new Profile();
        // TODO Implement this method.
        return combinedProfile;
    }

}
