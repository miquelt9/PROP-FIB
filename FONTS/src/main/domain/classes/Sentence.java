package src.main.domain.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;
import java.lang.String;

/**
 * This class represents a Sentence.
 */
public class Sentence implements Serializable {

    private ArrayList<String> sentence;

    /**
     * Default constructor.
     */
    public Sentence() {
        this.sentence = new ArrayList<>();
    }

    /**
     * Default copier.
     * 
     * @param sentence ArrayList of values to initialize the sentence with.
     */
    public Sentence(ArrayList<String> sentence) {
        this.sentence = sentence;
    }

    /**
     * Contructor by string.
     * 
     * @param sentence String to initialize the sentence with.
     */
    public Sentence(String sentence) {
        this.sentence = new ArrayList<String>(Arrays.asList(sentence.trim().split("\\s+")));
    }

    /**
     * Get number of words in the sentence.
     * 
     * @return The number of words in the sentence.
     */
    public int getNumberOfWords() {
        return sentence.size();
    }

    /**
     * Get the k-th word of the sentence.
     * 
     * @param k Position of the word will be returned.
     * @return The word on the k-th position in the sentence.
     */
    public String getKthWord(int k) {
        if (sentence.size() == 0 || k >= sentence.size() || k < 0) {
            System.err.println("Exception catch: Error in getting the " + k + " word of the sentence " + sentence);
            return "ERROR IN GET KWORD";
        }
        return sentence.get(k);
    }

    /**
     * Get the sentence as splitted strings in a format of a list.
     * 
     * @return The content of the sentence splitted by words.
     */
    public ArrayList<String> getSplittedSentence() {
        return sentence;
    }

    /**
     * Get the sentence in one string with the words concatenated by a white space.
     * 
     * @return The sentence as a string.
     */
    public String getWholeSentence() {
        String s = "";
        for (int i = 0; i < getNumberOfWords(); ++i) {
            if (i != 0)
                s += " ";
            s += sentence.get(i);
        }
        return s;
    }

    /**
     * Get the sentence in a form of a list made by its words.
     * 
     * @return The content of the sentence splitted by words.
     */
    public ArrayList<String> getSentence() {
        return sentence;
    }

    /**
     * Set the sentence given a string.
     * 
     * @param sentence New sentence to update with.
     */
    public void setSentence(String sentence) {
        this.sentence = new ArrayList<String>(Arrays.asList(sentence.trim().split("\\s+")));
    }

    /**
     * Set the sentence given a list of words.
     * 
     * @param sentence New sentence to update with.
     */
    public void setSentence(ArrayList<String> sentence) {
        this.sentence = sentence;
    }
}
