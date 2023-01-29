package src.main.domain.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import src.main.domain.classes.Document;
import src.main.domain.classes.Result;
import src.main.domain.classes.Trie;
import src.main.domain.classes.exceptions.*;
import src.main.persistence.controllers.PersistenceController;
import src.main.domain.classes.Query;
import src.main.domain.classes.Configuration;

/**
 * The SearchController class allows searching for documents in the system by
 * different criteria such as author, relevance,
 * similitude, boolean expression, or title. It also allows creating, modifying,
 * and deleting queries.
 * 
 * @author Alex H
 * @author Lluc Clavera
 */
public class SearchController {

    private static SearchController single_instance;
    private Result result;
    private HashMap<String, Query> queries;

    // --------------------- Constructors --------------------------------

    /**
     * Default constructor.
     */
    private SearchController() {
        queries = new HashMap<String, Query>();
        result = new Result();
    }

    /**
     * Returns the single instance of the SearchController.
     * 
     * @return the single instance of the SearchController
     */
    public static SearchController get_instance() {
        if (single_instance == null)
            single_instance = new SearchController();
        return single_instance;
    }

    /**
     * Creates a new query with the given name and query string.
     * 
     * @param name  the name of the query
     * @param query the query string
     * @throws queryNotCorrect    if the query string is not correct
     * @throws alreadyExistsQuery if a query with the given name already exists
     */
    public void createQuery(String name, String query) throws queryNotCorrect, alreadyExistsQuery {
        Query q = new Query(name, query);
        if (queries.containsKey(name))
            throw new alreadyExistsQuery(name);
        else {
            queries.put(name, q);
        }

    }

    /**
     * Returns a list of all the queries in the system.
     * 
     * @return a list of all the queries in the system
     */
    public ArrayList<ArrayList<String>> getQueries() {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for (java.util.Map.Entry<String, Query> query : queries.entrySet()) {
            ArrayList<String> act = new ArrayList<String>();
            act.add(query.getKey());
            act.add(query.getValue().getQuery());
            result.add(act);
        }
        return result;
    }

    /**
     * Returns a specific query in the system
     * 
     * @param name the name of the query to return
     * @return a query of the system with the format [name, content]
     */
    public ArrayList<String> getQuery(String name) {
        try {
            ArrayList<String> result = new ArrayList<String>();
            Query q = queries.get(name);
            result.add(q.getName());
            result.add(q.getQuery());
            return result;
        } catch (Exception e) {
            System.err.println("No s'ha pogut trobar la query.");
            return null;
        }
    }

    /**
     * Deletes the query with the given name.
     * 
     * @param name the name of the query to be deleted
     */
    public void deleteQuery(String name) {
        queries.remove(name);
    }

    /**
     * Modifies the query with the given name to have the new query string.
     * 
     * @param name  the name of the query to be modified
     * @param query the new query string
     * @throws queryNotCorrect if the new query string is not correct
     * @throws notFoundQuery   if a query with the given name does not exist
     */
    public void modifyQuery(String name, String query) throws queryNotCorrect, notFoundQuery {
        if (!queries.containsKey(name))
            throw new notFoundQuery(name);
        queries.get(name).modifyQuery(query);
    }

    /**
     * Returns a list of all documents written by the given author.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param author the author of the documents to be returned
     * @return a list of all documents written by the given author
     * @throws emptyAuthor       if the author is an empty string
     * @throws authorWithoutDocs if the author has not written any documents
     * @throws notFoundAuthor    if the author does not exist in the system
     */
    public ArrayList<ArrayList<String>> getDocumentsByAuthor(String author)
            throws emptyAuthor, authorWithoutDocs, notFoundAuthor {
        author = author.toLowerCase();
        result = new Result();
        try {
            result.searchDocuments(author);
            return result.getResult();
        } catch (emptyAuthor e) {
            throw new emptyAuthor();
        }

        catch (Exception e) {
            return new ArrayList<ArrayList<String>>();
        }

    }

    /**
     * Returns a list of the k documents most similar to the document with the given
     * query.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param query the string to compare to
     * @param k     the number of similar documents to return
     * @return a list of the k documents most relevant to the given query
     * @throws notEnoughDocuments if there are not enough documents in the system to
     *                            return k relevant documents
     */
    public ArrayList<ArrayList<String>> getDocumentByRelevance(String query, int k) throws notEnoughDocuments {
        result = new Result();
        result.searchByRelevance(query, k);
        return result.getResult();
    }

    /**
     * Returns a list of the k documents most similar to the document with the given
     * title and author.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param title  the title of the document to compare to
     * @param author the author of the document to compare to
     * @param k      the number of similar documents to return
     * @return a list of the k documents most similar to the document with the given
     *         title and author
     * @throws notFoundDocument   if the document with the given title and author
     *                            does not exist
     * @throws notFoundAuthor     if the author does not exist in the system
     * @throws notEnoughDocuments if there are not enough documents in the system to
     *                            return k similar documents
     * @throws emptyTitle         if the title is an empty string
     * @throws emptyAuthor        if the author is an empty string
     */
    public ArrayList<ArrayList<String>> getDocumentBySimilitude(String title, String author, int k)
            throws notFoundDocument, notFoundAuthor, notEnoughDocuments, emptyTitle, emptyAuthor {
        result = new Result();
        Document doc = Trie.get_instance().getDoc(title, author);
        result.searchKSimilars(doc, k);
        return result.getResult();

    }

    /**
     * Returns a list of documents that match the given boolean expression query.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param query the boolean expression query to match documents to
     * @return a list of documents that match the given boolean expression query
     * @throws queryNotCorrect if the query is not a valid boolean expression query
     */
    public ArrayList<ArrayList<String>> getDocumentsByBooleanExpression(String query) throws queryNotCorrect {
        result = new Result();
        result.searchByQuery(query);
        return result.getResult();
    }

    // Unused
    public ArrayList<ArrayList<String>> getDocumentsByBooleanExpression(Query query) throws queryNotCorrect {
        result = new Result();
        result.searchByQuery(query);
        return result.getResult();
    }

    /**
     * Returns a list of documents with the given title.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param title the title of the documents to be returned
     * @return a list of documents with the given title
     * @throws emptyTitle if the title is an empty string
     */
    public ArrayList<ArrayList<String>> getDocumentsByTitle(String title) throws emptyTitle {
        title = title.toLowerCase();
        result = new Result();
        result.searchByTitle(title);
        return result.getResult();
    }

    /**
     * Returns a list of authors whose name starts with the given prefix.
     * 
     * @param prefix the prefix to search for in the authors' names
     * @return a list of authors whose name starts with the given prefix
     */
    public ArrayList<String> getAuthorsByPrefix(String prefix) {

        return Trie.get_instance().getAuthPref(prefix);

    }

    /**
     * Sets the ordering criteria for search results.
     * 
     * @param mode the mode for ordering the results (by title, author, acces
     *             date...)
     * @param asc  whether the results should be ordered in ascending or descending
     *             order
     * @return the search results with the new ordering criteria applied
     */
    public ArrayList<ArrayList<String>> setOrderingCriteria(String mode, boolean asc) {
        try {
            Configuration.get_instance().setConfigurationMode(mode);
            Configuration.get_instance().setAscendent(asc);
            return result.reSort();
        } catch (Exception e) {
            System.err.println("No s'ha pogut canviar el mode.");
            return new ArrayList<ArrayList<String>>();
        }

    }

    /**
     * Returns a specific document
     * Format: [[Title, Author]] (to be consistent with other results)
     * 
     * @param title  the title of the document to return
     * @param author the author of the document to return
     * @throws notFoundDocument if the document with the given title and author does
     *                          not exist
     * @throws notFoundAuthor   if the author does not exist in the system
     * @throws emptyTitle       if the title is an empty string
     * @throws emptyAuthor      if the author is an empty string
     */
    public ArrayList<ArrayList<String>> getDocument(String title, String author)
            throws notFoundDocument, notFoundAuthor, emptyTitle, emptyAuthor {
        title = title.toLowerCase();
        author = author.toLowerCase();
        if (title.equals(" "))
            throw new emptyTitle();
        if (author.equals(" "))
            throw new emptyAuthor();
        result = new Result();
        result.searchDocument(title, author);
        return result.getResult();
    }

    /**
     * Loads the saved queries from the persistence layer
     */
    public void openAndLoad() {
        PersistenceController PersCtrl = PersistenceController.getInstance();
        queries = (HashMap<String, Query>) PersCtrl.loadQueries();
    }

    /**
     * Saves the current queries to the persistence layer
     */
    public void saveAndClose() {
        PersistenceController.getInstance().saveQueries(queries);
    }
}
