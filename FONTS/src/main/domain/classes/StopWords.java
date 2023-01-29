package src.main.domain.classes;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * This class represents the StopWords.
 */
public class StopWords implements Serializable {

    private HashSet<String> english;
    private HashSet<String> catalan;
    private HashSet<String> spanish;
    private static StopWords single_instance = null;

    /**
     * Getter of the StopWords singleton
     * @return Reference to the StopWords manager itself
     */
    public static StopWords getInstance() {
        if (single_instance == null)
            single_instance = new StopWords();
        return single_instance;
    }

    // We have to specify the path for every single file,
    // and then read line by line to create each set.
    private StopWords() {
        spanish = readStopWords("SpanishStopWords");
        catalan = readStopWords("CatalanStopWords");
        english = readStopWords("EnglishStopWords");
    }

    /**
     * Reads the words in a file and returns them in a HashSet.
     * 
     * @param diccionary Name of the diccionary to be read.
     * @return HashSet of the read word from the diccionary.
     */
    public HashSet<String> readStopWords(String diccionary) {
        HashSet<String> resultSet = new HashSet<>();

        String path = System.getProperty("user.dir") + "/../FONTS/src/main/domain/classes/StopWord/" + diccionary
                + ".txt";
        try {
            File sp = new File(path);
            Scanner rrr = new Scanner(sp);
            while (rrr.hasNextLine()) {
                String data = rrr.nextLine();
                resultSet.add(data);
            }

            rrr.close();

        } catch (FileNotFoundException e) {
            System.err.println("Exception catch: Failed loading " + diccionary + ".");
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Checks wether a word is an English stopword.
     * 
     * @param s Word to be checked.
     * @return True if the word is an English stopword, False otherwise.
     */
    public boolean isEnglishStopWord(String s) {
        return english.contains(s);
    }

    /**
     * Checks wether a word is an Spanish stopword.
     * 
     * @param s Word to be checked.
     * @return True if the word is an Spanish stopword, False otherwise.
     */
    public boolean isSpanishStopWord(String s) {
        return spanish.contains(s);
    }

    /**
     * Checks wether a word is an Catalan stopword.
     * 
     * @param s Word to be checked.
     * @return True if the word is an Catalan stopword, False otherwise.
     */
    public boolean isCatalanStopWord(String s) {
        return catalan.contains(s);
    }

    /**
     * Checks wether a word is a stopword.
     * 
     * @param s Word to be checked.
     * @return True if the word is a stopword, False otherwise.
     */
    public boolean isStopWord(String s) {
        return (isEnglishStopWord(s) || isCatalanStopWord(s) || isSpanishStopWord(s));
    }

    /**
     * Checks wether a word is a stopword.
     * 
     * @param s        Word to be checked.
     * @param language Language of the s String.
     * @return True if the word is a stopwordin the language, False otherwise.
     *         Return isStopWord if language is not detected.
     */
    public boolean isStopWord(String s, String language) {
        if (language == "ca")
            return isCatalanStopWord(s);
        if (language == "es")
            return isSpanishStopWord(s);
        if (language == "en")
            return isEnglishStopWord(s);
        return (isEnglishStopWord(s) || isCatalanStopWord(s) || isSpanishStopWord(s));
    }
}
