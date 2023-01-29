package src.main.domain.classes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import src.main.domain.controllers.DocumentController;

/**
 * This class represents the InvertedIndex.
 * 
 * @author Lluc Clavera
 */
public class InvertedIndex implements Serializable {
    private class Info implements Serializable {
        public HashMap<Document, HashSet<Integer>> docs;
        public HashMap<Document, Integer> localFreq;
        public int frequency;

        public Info() {
            docs = new HashMap<Document, HashSet<Integer>>();
            localFreq = new HashMap<Document, Integer>();
            frequency = 0;
        }

    }

    private static InvertedIndex single_instance = null;
    HashMap<String, Info> Index;

    /**
     * Default constructor.
     */
    private InvertedIndex() {
        Index = new HashMap<String, Info>();
    }

    /**
     * Gets the single instance of InvertedIndex.
     * 
     * @return the single instance of the index
     */
    public static InvertedIndex getInstance() {
        if (single_instance == null) {
            single_instance = new InvertedIndex();
        }

        return single_instance;
    }

    /**
     * Sets the single intsnce to "this"
     */
    public void set_instance() {
        single_instance = this;
    }

    /**
     * Get the total frquency of a word.
     * 
     * @param word Word to get the frequency of.
     * 
     * @return The total frequency (nº of appeareances) of a given word across the
     *         documents.
     */
    public int getTotalFrequency(String word) {
        word = word.toLowerCase();
        if (!(Index.containsKey(word)))
            return 0;
        else
            return Index.get(word).frequency;
    }

    /**
     * Get the frequency of a word given a document.
     * 
     * @param document Document to get the frequency of.
     * @param word     Word to get the frequency of.
     * 
     * @return The total frequency (nº of appeareances) of a given word in the given
     *         document.
     */
    public int getLocalFrequency(Document document, String word) {
        word = word.toLowerCase();
        if (!(Index.containsKey(word)))
            return 0;
        else if (!(Index.get(word).localFreq.containsKey(document)))
            return 0;
        return Index.get(word).localFreq.get(document);
    }

    /**
     * Get the number of documents where the given word appears.
     * 
     * @param word Word to look for.
     * @return The number of documents where the word appears.
     */
    public int getNumberOfDocuments(String word) {
        word = word.toLowerCase();
        if (!(Index.containsKey(word)))
            return 0;
        else
            return Index.get(word).docs.size();
    }

    /**
     * Get a list of documents given a word.
     * 
     * @param word Word to look for.
     * @return A HashMap with the documents where the given word appears.
     */
    public HashMap<Document, HashSet<Integer>> getDocumentList(String word) {
        word = word.toLowerCase();
        if (!(Index.containsKey(word)))
            return new HashMap<Document, HashSet<Integer>>();
        else
            return Index.get(word).docs;
    }

    /**
     * Get a list of documents given a sequence of words.
     * 
     * @param sequence Sequence to look for.
     * @return A HashMap with the documents where the given sequence appears.
     */
    public HashMap<Document, HashSet<Integer>> getDocumentsFromSequence(String sequence) {
        sequence = sequence.toLowerCase();
        String aux = sequence.replace("\"", "");
        String[] words = aux.split(" ");
        HashMap<Document, HashSet<Integer>> IntermediateResult = new HashMap<Document, HashSet<Integer>>();

        for (java.util.Map.Entry<Document, HashSet<Integer>> doc : getDocumentList(words[0]).entrySet()) {
            for (int i = 1; i < words.length; ++i) {
                if (getDocumentList(words[i]).containsKey(doc.getKey())) {
                    IntermediateResult.put(doc.getKey(), new HashSet<Integer>(doc.getValue()));
                    IntermediateResult.get(doc.getKey()).retainAll(getDocumentList(words[i]).get(doc.getKey()));
                    if (IntermediateResult.get(doc.getKey()).size() == 0) {
                        IntermediateResult.remove(doc.getKey());
                        break;
                    }
                } else
                    break;
            }
        }

        HashMap<Document, HashSet<Integer>> result = new HashMap<Document, HashSet<Integer>>(IntermediateResult);
        for (java.util.Map.Entry<Document, HashSet<Integer>> doc : IntermediateResult.entrySet()) {
            DocumentController.getInstance().setContent(doc.getKey());
            HashSet<Integer> toDelete = new HashSet<Integer>();
            for (Integer sent : doc.getValue()) {
                if (!doc.getKey().getKthSentence(sent).getWholeSentence().toLowerCase().contains(aux.toLowerCase())) {
                    toDelete.add(sent);
                }
            }
            result.get(doc.getKey()).removeAll(toDelete);
            if (result.get(doc.getKey()).size() == 0) {
                result.remove(doc.getKey());
            }
            doc.getKey().setContent("");
        }
        return result;
    }

    /**
     * Update the frequency of the given word.
     * 
     * @param word     Word which need to be updated the frequency of.
     * @param document Document that triggers the frquency update (by changing its
     *                 content).
     */
    public void updateWord(String word, Document document, int sent) {
        word = word.toLowerCase();
        Info temp = Index.get(word);
        if (temp == null)
            temp = new Info();

        HashSet<Integer> temp2 = temp.docs.get(document);

        if (temp2 == null) {
            temp2 = new HashSet<Integer>();
        }

        temp2.add(sent);

        Integer localF;

        if (!temp.localFreq.containsKey(document))
            localF = 0;
        else
            localF = temp.localFreq.get(document);

        temp.localFreq.put(document, localF + 1);
        temp.docs.put(document, temp2);
        temp.frequency += 1;
        Index.put(word, temp);
    }

    /**
     * Function to delete a document from the index.
     * 
     * @param d Document to be deleted from the data structures.
     */
    public void deleteDocument(Document d) {
        for (java.util.Map.Entry<String, Info> word : Index.entrySet()) {
            word.getValue().frequency = getTotalFrequency(word.getKey()) - getLocalFrequency(d, word.getKey());
            word.getValue().docs.remove(d);
            word.getValue().localFreq.remove(d);
        }
    }

}