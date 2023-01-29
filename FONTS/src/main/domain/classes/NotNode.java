package src.main.domain.classes;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents the NotNode in the queries.
 * 
 * @author Lluc Clavera
 */
public class NotNode extends QueryNode {

    /**
     * Default constructor for NotNode.
     */
    public NotNode() {
        this.negated = true;
        this.right = null;
        this.left = null;
    }

    /**
     * Returns a collection of documents satisfying the condition of the node
     * (The negation of all documents from the left child).
     * 
     * @return A HashMap of the documents that the condition of the node.
     */
    public HashMap<Document, HashSet<Integer>> search() {
        negated = true;
        return left.search();
    }
}
