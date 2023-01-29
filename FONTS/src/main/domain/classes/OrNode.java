package src.main.domain.classes;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents the OrNode in the queries.
 * 
 * @author Lluc Clavera
 */
public class OrNode extends QueryNode {

    /**
     * Default constructor for OrNode.
     */
    public OrNode() {
        this.negated = false;
        this.right = null;
        this.left = null;
    }

    /**
     * Returns a collection of documents satisfying the condition of the node
     * (The union of all documents from the left child and the right child).
     * 
     * @return A HashMap of the documents that the condition of the node.
     */
    public HashMap<Document, HashSet<Integer>> search() {
        HashMap<Document, HashSet<Integer>> leftNode = left.search();
        HashMap<Document, HashSet<Integer>> rightNode = right.search();

        if (left.negated && right.negated) {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : leftNode.entrySet()) {
                if (!rightNode.containsKey(doc.getKey()))
                    leftNode.remove(doc.getKey());
                else {
                    doc.getValue().retainAll(rightNode.get(doc.getKey()));
                    if (leftNode.get(doc.getKey()).size() == 0)
                        leftNode.remove(doc.getKey());
                }
            }
            negated = true;
            return leftNode;
        }

        else if (left.negated) {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : rightNode.entrySet()) {
                if (leftNode.containsKey(doc.getKey())) {
                    leftNode.get(doc.getKey()).removeAll(doc.getValue());
                    if (leftNode.get(doc.getKey()).size() == 0)
                        leftNode.remove(doc.getKey());
                }
            }
            negated = true;
            return leftNode;
        }

        else if (right.negated) {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : leftNode.entrySet()) {
                if (rightNode.containsKey(doc.getKey())) {
                    rightNode.get(doc.getKey()).removeAll(doc.getValue());
                    if (rightNode.get(doc.getKey()).size() == 0)
                        rightNode.remove(doc.getKey());
                }
            }
            negated = true;
            return rightNode;
        }

        else {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : leftNode.entrySet()) {
                if (!rightNode.containsKey(doc.getKey()))
                    rightNode.put(doc.getKey(), doc.getValue());
                else
                    rightNode.get(doc.getKey()).addAll(doc.getValue());
            }
            negated = false;
            return rightNode;
        }
    }

}
