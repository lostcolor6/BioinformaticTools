// Assignment 06 - Task 03

import java.util.Locale;

/**
 * BinaryTree class represents a binary tree with a label and two children. The class also implements a method to convert the tree to a Newick string.
 */
class BinaryTree {

    /**
     * The left child of the tree.
     */
    BinaryTree left;
    /**
     * The right child of the tree.
     */
    BinaryTree right;
    /**
     * The branch weight of the left child.
     */
    double left_distance;
    /**
     * The branch weight of the right child.
     */
    double right_distance;
    /**
     * The name of this tree.
     */
    String label;

    /**
     * Constructs a {@link BinaryTree} with no children.
     *
     * @param label The label of this tree.
     */
    public BinaryTree(String label) {
        this.label = label;
        this.left = null;
        this.right = null;
    }

    /**
     * Constructs a {@link BinaryTree} with two children and edge weights.
     *
     * @param left           A BinaryTree stored as the left children of this tree.
     * @param right          A BinaryTree stored as the right children of this tree.
     * @param left_distance  The distance/edge weight to the left children.
     * @param right_distance The distance/edge weight to the right children.
     */
    public BinaryTree(BinaryTree left, BinaryTree right, double left_distance, double right_distance) {
        this.left = left;
        this.right = right;
        this.left_distance = left_distance;
        this.right_distance = right_distance;
        this.label = null;
    }

    /**
     * Generates a Newick string representation of this tree.
     *
     * @return {@link String}; Newick string representation of this instance.
     */
    public String toNewick() {
        if (right == null && left == null)
            return label;
        String output = "(";
        if (right != null)
            output += right.toNewick() + ":" + String.format(Locale.US, "%.1f", right_distance);
        if (right != null && left != null)
            output += ",";
        if (left != null)
            output += left.toNewick() + ":" + String.format(Locale.US, "%.1f", left_distance);
        output += ")";
        return output;
    }
}
