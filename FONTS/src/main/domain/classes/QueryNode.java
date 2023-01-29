package src.main.domain.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents a QueryNode.
 * 
 * @author Lluc Clavera
 */
public abstract class QueryNode implements Serializable {

    protected boolean negated;

    protected QueryNode left;
    protected QueryNode right;

    /**
     * Default constructor.
     */
    public QueryNode() {
        this.negated = false;

        left = null;
        right = null;
    }

    /**
     * Adds the given QueryNode as the left child.
     * 
     * @param leftNode Node in which the new node will be added to.
     */
    public void addLeft(QueryNode leftNode) {
        left = leftNode;
    }

    /**
     * Adds the given QueryNode as the left child.
     * 
     * @param rightNode Node in which the new node will be added to.
     */
    public void addRight(QueryNode rightNode) {
        left = rightNode;
    }

    /**
     * Adds both the left and the right node as children.
     * 
     * @param leftNode Node in which the new nodes will be added to.
     */
    public void addChildren(QueryNode leftNode, QueryNode rightNode) {
        left = leftNode;
        right = rightNode;
    }

    /**
     * Get the left child.
     * 
     * @return The left child of the node.
     */
    public QueryNode getLeft() {
        return left;
    }

    /**
     * Get the right child.
     * 
     * @return The right child of the node.
     */
    public QueryNode getRight() {
        return right;
    }

    /**
     * Returns a collection of documents satisfying the condition of the node
     * (The implementation depends on the subclasses).
     * 
     * @return A HashMap with the documents that satifies the condition of the node.
     */
    abstract public HashMap<Document, HashSet<Integer>> search();

}
