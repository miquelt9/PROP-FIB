package src.main.domain.classes;

import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/* Will not be used in the end because of problems importing the library with the .jar file itself. */
// import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
// import org.apache.tika.language.detect.LanguageDetector;

/**
 * This class represents the metadata and attributes of a document. The
 * combination of this object and a content form a whole document
 * 
 * @author Walter J.T.V
 * @author Miquel Torner
 */

public class Document implements Serializable {

    // DATES
    private Date creationDate;
    private Date modificationDate;
    private Date accesDate;

    // PRIMARY KEY
    private String title;
    private String author;
    private String language;

    // CONTENT
    private Contents contents;

    /**
     * Default contructor (needs the primary key of the document which is title +
     * author). The DocumentController will immediatly assign a content, so, if its
     * null after its creation in
     * the controller, then that doesn't imply that it doesn't have a content at
     * all, just that has been removed from
     * the main memory in order to free up space.
     * 
     * @param title  title of the document
     * @param author author of the document
     */
    public Document(String title, String author) {
        creationDate = new Date();
        modificationDate = new Date();
        accesDate = new Date();
        contents = new Contents("", this);

        // Primary key
        updateLanguage("");
        this.title = title;
        this.author = author;
    }

    /**
     * content constructor (needs the primary key of the document which is title +
     * author + content).
     * 
     * @param title   title of the document
     * @param author  author of the document
     * @param content content of the document
     */
    public Document(String title, String author, String content) {

        creationDate = new Date();
        modificationDate = new Date();
        accesDate = new Date();
        this.title = title;
        this.author = author;

        updateLanguage(content);
        contents = new Contents(content, this);
    }

    /**
     * @return The creation date.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @return The modification date.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the modification date.
     * 
     * @param modificationDate New modification date.
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * @return The last access date.
     */
    public Date getAccesDate() {
        return accesDate;
    }

    /**
     * Sets the last access date.
     * 
     * @param accesDate New acces date.
     */
    public void setAccesDate(Date accesDate) {
        this.accesDate = accesDate;
    }

    /**
     * Get the content of the document.
     * 
     * @return The raw content text.
     */
    public String getContent() {
        return contents.getRawContents();
    }

    /**
     * Get the content of the document (Similar to getContent, but returns the
     * object instead of the text)
     * 
     * @return The raw content text.
     */
    public Contents getContents() {
        return this.contents;
    }

    /**
     * Get the language of the text.
     * 
     * @return The language of the text.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Get the title of the document.
     * 
     * @return The title of the docuemnt.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the author of the document.
     * 
     * @return The author of the docuemnt.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Get the k-th sentence of the document.
     * 
     * @return The sentence in the k-th position of the docuemnt.
     */
    public Sentence getKthSentence(int k) {
        return contents.getKthSentence(k);
    }

    /**
     * Get the number of lines of the document.
     * 
     * @return The number of lines on the docuemnt.
     */
    public int getNumLines() {
        return contents.getNumLines();
    }

    /**
     * Get the number of wordss of the document.
     * 
     * @return The number of words on the docuemnt.
     */
    public int getNumWord() {
        return contents.getNumWords();
    }

    /**
     * Get the content of the document spiltted.
     * 
     * @return The content of the docuemnt splitted by Sentences in an ArrayList.
     */
    public ArrayList<Sentence> getSplittedContent() {
        return contents.getSplittedContent();
    }

    /**
     * Set the content of the document.
     * 
     * @param content New content (in raw string format)
     */
    public void setContent(String content) {
        this.contents = new Contents(content, this);
        this.setModificationDate(new Date());
        language = identifyLanguage(content);
    }

    /**
     * Set the content of the document.
     * 
     * @param content New content in content object format
     */
    public void setContent(Contents content) {
        this.contents = content;
        this.setModificationDate(new Date());
        language = identifyLanguage(content.getRawContents());
    }

    /**
     * This methods returns the vector with the weights of each word, following the
     * tf-idf rule for weigths.
     * The inverse document frequency is log (n of docs / n of docs which contain
     * "term"), for a word "term"
     * 
     * @param NumberOfDocuments Number of documents in the Vectorial Index TreeSet.
     * @return A TreeSet with the words as key and its weight as the value.
     */
    public TreeMap<String, Double> getWeightVector(int NumberOfDocuments) {
        return contents.getWeightVector(NumberOfDocuments);
    }

    /**
     * This methods, also returns the vector with the weights of each word,
     * following the logarithmic frequency weighting
     * 0 if www does not belong to D
     * where the weight of a word "www" is computed using a function f(www) = | 1 +
     * log(termfreq) if www belongs to D
     *
     * @return A TreeSet with the words as key and its weight as the value.
     */
    public TreeMap<String, Double> getWeightVector2() {
        return contents.getWeightVector2();
    }

    /**
     * To detect the language of the document whenever a content is set.
     * 
     * @param text content of the document
     * @return the language of the document
     */
    private String identifyLanguage(String text) {
        // LanguageDetector languageDetector = new OptimaizeLangDetector().loadModels();
        // languageDetector.addText(text);
        // return languageDetector.detect().getLanguage();
        return "null";
    }

    /**
     * To update the language of the document or either null it.
     * 
     * @param content Either the content of the document or the empty string, in
     *                order to remove the language
     */
    private void updateLanguage(String content) {
        if (content == "") {
            language = "null";
        } else {
            language = identifyLanguage(content);
        }
    }
}
