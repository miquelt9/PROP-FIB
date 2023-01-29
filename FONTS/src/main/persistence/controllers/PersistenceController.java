package src.main.persistence.controllers;


import src.main.persistence.classes.ConfigurationManager;
import src.main.persistence.classes.ContentManager;
import src.main.persistence.classes.DocumentManager;
import src.main.persistence.classes.IndexManager;
import src.main.persistence.classes.QueryManager;

/**
 * This class represents the persistence controller, it is responsible of
 * downloading and uploading all the files that can be saved.
 */
public class PersistenceController {

    private DocumentManager doc;
    private IndexManager index;
    private QueryManager query;
    private ContentManager content;
    private ConfigurationManager config;
    private static PersistenceController singleInstance;

    /**
     * Constructor of the persistence controller.
     */
    private PersistenceController() {
        doc = new DocumentManager();
        index = new IndexManager();
        query = new QueryManager();
        content = new ContentManager();
        config = new ConfigurationManager();
    }

    /**
     * Singleton getter
     * @return Reference to the persistence controller itself
     */
    public static PersistenceController getInstance() {
        if (singleInstance == null)
            singleInstance = new PersistenceController();
        return singleInstance;
    }

    /**
     * Saves the current Author Index (Trie) in a specific hard-coded path.
     * 
     * @param Trie The Author Index itself with type Object
     */
    public void saveTrie(Object Trie) {
        try {
            index.saveTrie(Trie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * This function returns the last saved Author Index (Trie).
     * 
     * @return The last saved Author index is returned.
     */
    public Object loadTrie() {
        Object aindex = new Object();
        try {
            aindex = index.loadTrie();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aindex;
    }

    /**
     * Saves the current Vectorial Index in a specific hard-coded path.
     * 
     * @param Trie The Vectorial Index itself with type Object.
     */
    public void saveVectorialIndex(Object VectorialIndex) {
        try {
            index.saveVectorialIndex(VectorialIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * This function returns the last saved Vectorial Index.
     * 
     * @return The last saved Vectorial index is returned.
     */
    public Object loadVectorialIndex() {
        Object vindex = new Object();
        try {
            vindex = index.loadVectorialIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vindex;
    }

    /**
     * Saves the current Inverted Index in a specific hard-coded path.
     * 
     * @param invertedIndex The Inverted Index itself with type Object.
     */
    public void saveInvertedIndex(Object invertedIndex) {
        try {
            index.saveInvertedIndex(invertedIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * This function returns the last saved Inverted Index.
     * 
     * @return The last saved Inverted index is returned.
     */
    public Object loadInvertedIndex() {
        Object index = new Object();
        try {
            index = this.index.loadInvertedIndex();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }

    /**
     * Saves the global configuration of the program.
     * 
     * @param configuration This object is the configuration the program is using.
     */
    public void saveConfiguration(Object configuration) {
        try {
            config.saveConfiguration(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the last saved configuration.
     * 
     * @return The last saved value of the program configuration.
     */
    public Object loadConfiguration() {
        Object configuration = new Object();
        try {
            configuration = config.loadConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return configuration;
    }

    /**
     * This functions either saves the Content or modifies it if its already been
     * saved previously.
     * 
     * @param Content Content to be saved.
     * @param name    The name of the binary that stores the Content (by
     *                precondition the name will always be the concatenation of its
     *                Document's title and author).
     */
    public void saveContent(Object Content, String name) {
        try {
            content.saveContent(Content, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function returns the Content located in the path ending with name name.
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the Content exists then its location ends with the
     *             concatenation of its Document's title and author).
     * @return The Content is returned as an Object type.
     */
    public Object loadContent(String name) {
        Object con = new Object();
        try {
            con = content.loadContent(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Funciton to erase a the content of a Document.
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the Content exists then its location ends with the
     *             concatenation of its Document's title and author).
     */
    public void deleteContent(String name) {
        try {
            content.deleteContent(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function either saves the document or modifies it if its already been
     * saved previously.
     * 
     * @param document Document to be saved.
     * @param name     The name of the binary that stores the document (by
     *                 precondition the name will always be the concatenation of its
     *                 title and author).
     */
    public void saveDocument(Object document, String name) {
        try {
            doc.saveDocument(document, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function returns the document located in the path ending with name name.
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the document exists then its location ends with the
     *             concatenation of its title and author).
     * @return The document is returned with an Object type.
     */
    public Object loadDocument(String name) {
        Object document = new Object();
        try {
            document = doc.loadDocument(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Funciton to erase a document
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the document exists then its location ends with the
     *             concatenation of its title and author).
     */
    public void deleteDocument(String name) {
        try {
            doc.deleteDocument(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the query with a specific name, if the name is already taken then it
     * will be overwritten.
     * 
     * @param queries Queries to be saved
     */
    public void saveQueries(Object queries) {
        try {
            query.saveQueries(queries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all the queries in Object class which can be casted to HashMap<String,Query>, which contains all the associations "name" -> Query 
     * @return HashMap<String,Query> as an instance of the class Object 
     */
    public Object loadQueries() {
        Object quer = new Object();
        try {
            quer = query.loadQueries();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quer;
    }
}
