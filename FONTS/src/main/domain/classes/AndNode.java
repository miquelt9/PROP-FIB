package src.main.domain.classes;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This class represents and AndNode in the queries.
 * 
 * @author Lluc Clavera
 */
public class AndNode extends QueryNode {

    /**
     * Default constructor of an AndNode.
     */
    public AndNode() {
        this.negated = false;
        this.right = null;
        this.left = null;
    }

    /**
     * (The intersection of all documents from the left child and the right child).
     * 
     * @return a collection of documents satisfying the condition of the node
     */
    public HashMap<Document, HashSet<Integer>> search() {
        HashMap<Document, HashSet<Integer>> leftNode = left.search();
        HashMap<Document, HashSet<Integer>> rightNode = right.search();
        if (left.negated && right.negated) {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : rightNode.entrySet()) {
                if (!leftNode.containsKey(doc.getKey()))
                    leftNode.put(doc.getKey(), doc.getValue());
                else
                    leftNode.get(doc.getKey()).addAll(doc.getValue());
            }
            negated = true;
            return leftNode;
        }

        else if (left.negated) {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : leftNode.entrySet()) {
                if (rightNode.containsKey(doc.getKey())) {
                    rightNode.get(doc.getKey()).removeAll(doc.getValue());
                    if (rightNode.get(doc.getKey()).size() == 0)
                        rightNode.remove(doc.getKey());
                }
            }
            negated = false;
            return rightNode;
        }

        else if (right.negated) {
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : rightNode.entrySet()) {
                if (leftNode.containsKey(doc.getKey())) {
                    leftNode.get(doc.getKey()).removeAll(doc.getValue());
                    if (leftNode.get(doc.getKey()).size() == 0)
                        leftNode.remove(doc.getKey());
                }
            }
            negated = false;
            return leftNode;
        }

        else {
            HashSet<Document> toRemove = new HashSet<Document>();
            for (java.util.Map.Entry<Document, HashSet<Integer>> doc : leftNode.entrySet()) {
                if (!(rightNode.containsKey(doc.getKey())))
                    toRemove.add(doc.getKey());
                else {
                    doc.getValue().retainAll(rightNode.get(doc.getKey()));
                    if (leftNode.get(doc.getKey()).size() == 0)
                        toRemove.add(doc.getKey());
                }
            }
            leftNode.keySet().removeAll(toRemove);
            negated = false;
            return leftNode;
        }
    }
}
