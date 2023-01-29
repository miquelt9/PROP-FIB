package src.main.domain.classes;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents the LeafNode in the queries.
 * 
 * @author Lluc Clavera
 */
public class LeafNode extends QueryNode {
    private String literal;

    /**
     * Constructor of LeadNode.
     * 
     * @param lit word or sequence corresponding to the leaf
     */
    public LeafNode(String lit) {
        this.literal = lit.toLowerCase();
        this.negated = false;
        this.right = null;
        this.left = null;
    }

    /**
     * Returns a collection of documents satisfying the condition of the node
     * (All documents that contain the specific word or sequence).
     * 
     * @return A HashMap of the documents that the condition of the node.
     */
    public HashMap<Document, HashSet<Integer>> search() {
        if (literal.charAt(0) == '"') {
            HashMap<Document, HashSet<Integer>> map = InvertedIndex.getInstance().getDocumentsFromSequence(literal);
            HashMap<Document, HashSet<Integer>> copy = new HashMap<Document, HashSet<Integer>>();
            for (java.util.HashMap.Entry<Document, HashSet<Integer>> entry : map.entrySet()) {
                copy.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
            }

            return copy;
        } else {
            HashMap<Document, HashSet<Integer>> map = InvertedIndex.getInstance()
                    .getDocumentList(literal);
            HashMap<Document, HashSet<Integer>> copy = new HashMap<Document, HashSet<Integer>>();
            for (java.util.HashMap.Entry<Document, HashSet<Integer>> entry : map.entrySet()) {
                copy.put(entry.getKey(), new HashSet<Integer>(entry.getValue()));
            }
            return copy;
        }
    }
}
