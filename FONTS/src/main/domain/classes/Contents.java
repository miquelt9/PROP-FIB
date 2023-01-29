package src.main.domain.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * This class reprsents the content of a document including the term frequencies, the number of words ...
 * @author Walter J.T.V
 */
public class Contents implements Serializable {

    private String rawContent;
    private ArrayList<Sentence> splittedContent;
    private HashMap<String, Integer> termfrequency;
    private Document doc;

    private int numberOfWords = 0;

    private InvertedIndex index;
    private StopWords stop;

    /**
     * 
     * @param rawContent Plain text of the content, main attribute.
     * @param document   Instance of the document where this content belongs (Null
     *                   if it doesn't belong to any document)
     */
    public Contents(String rawContent, Document document) {
        this.doc = document;
        this.index = InvertedIndex.getInstance();
        this.stop = StopWords.getInstance();
        this.rawContent = rawContent;

        this.splittedContent = this.splitContentIntoSentences(rawContent);
        this.termfrequency = new HashMap<String, Integer>();
        setTermFrequencies();
        updateNumberOfWords();

    }

    /**
     * Splits a string into multiple sentences by punction.
     * 
     * @param text Raw string to be converted.
     * @return ArrayList of Sentences with the splitted content.
     */
    private ArrayList<Sentence> splitContentIntoSentences(String text) {
        ArrayList<Sentence> splitted = new ArrayList<Sentence>();
        String[] array = text.split("[.!?\n\r\f\t]+[ \n\r\f\t]+");
        for (String sent : array) {
            splitted.add(new Sentence(sent));
        }
        return splitted;
    }

    /**
     * @return The raw text content.
     */
    public String getRawContents() {
        return rawContent;
    }

    /**
     * @return The number of word in the content.
     */
    public int getNumWords() {
        return numberOfWords;
    }

    /**
     * @return The number of lines or sentences after spliting the raw content text.
     */
    public int getNumLines() {
        return splittedContent.size();
    }

    /**
     * Gets the k-th line of the splitted content.
     * 
     * @param k Number of the line to return.
     * @return The number of the line in the k-th position.
     */
    public Sentence getKthSentence(int k) {
        return splittedContent.get(k);
    }

    /**
     * @return The splitted content as an ArrayList of Sentence.
     */
    public ArrayList<Sentence> getSplittedContent() {
        return splittedContent;
    }

    /**
     * Updates the number of words so it doesn't have to compute it each time.
     * Called when setting the content.
     */
    private void updateNumberOfWords() {
        numberOfWords = 0;
        for (Sentence s : splittedContent)
            numberOfWords += s.getNumberOfWords();
    }

    /**
     * Set the frequency of each term in the document.
     */
    // FOR EACH WORD, THE FREQUENCY IS COUNTED, ALSO, THE INVERTED INDEX IS UPDATED
    // ALSO HAVE IN MIND THAT STOPWORDS ARE LOWERCASE AND ALL WORDS STORED SHOULD BE
    // REPRESENTED AS LOWERCASE FOR THE DATA STRUCTURES TO WORK JUST FINE
    private void setTermFrequencies() {

        for (int i = 0; i < splittedContent.size(); ++i) {
            Sentence s = splittedContent.get(i);

            for (String term : s.getSplittedSentence()) {

                term = term.toLowerCase();

                if (stop.isStopWord(term, doc.getLanguage())) {
                    index.updateWord(term, this.doc, i); // THE WORD IS ADDED, BUT NEVER USED IN THE MODEL
                } else {
                    index.updateWord(term, this.doc, i);
                    if (termfrequency.containsKey(term)) {
                        int freq = termfrequency.get(term);
                        termfrequency.replace(term, freq + 1);
                    } else {
                        termfrequency.put(term, 1);
                    }
                }
            }
        }
    }

    /**
     * This methods returns the vector with the weights of each word, following the
     * tf-idf rule for weigths.
     * The inverse document frequency is log (n of docs / n of docs which contain
     * "term"), for a word "term".
     * 
     * @param NumberOfDocuments Number of documents in the Vectorial Index TreeSet.
     * @return A TreeSet with the words as key and its weight as the value.
     */
    public TreeMap<String, Double> getWeightVector(int NumberOfDocuments) {

        TreeMap<String, Double> returnedTree = new TreeMap<String, Double>();

        for (HashMap.Entry<String, Integer> mapElement : termfrequency.entrySet()) {
            String key = mapElement.getKey().toLowerCase();
            double value = mapElement.getValue();
            double documentsContainingTerm = index.getNumberOfDocuments(key);

            // The formula to compute the weight of this term.
            returnedTree.put(key, value * Math.log(NumberOfDocuments / documentsContainingTerm));
        }
        return returnedTree;
    }

    /**
     * This methods, also returns the vector with the weights of each word,
     * following the logarithmic frequency weighting 0 if www does not belong to D
     * where the weight of a word "www" is computed using a function f(www) = | 1 +
     * log(termfreq) if www belongs to D.
     * 
     * @return A TreeSet with the words as key and its weight as the value.
     */
    public TreeMap<String, Double> getWeightVector2() {

        TreeMap<String, Double> returnedTree = new TreeMap<String, Double>();
        for (HashMap.Entry<String, Integer> mapElement : termfrequency.entrySet()) {
            String key = mapElement.getKey().toLowerCase();
            double value = mapElement.getValue();
            if (value == 0)
                returnedTree.put(key, 0.0);
            else
                returnedTree.put(key, 1.0 + Math.log(value));
        }
        return returnedTree;
    }

}
