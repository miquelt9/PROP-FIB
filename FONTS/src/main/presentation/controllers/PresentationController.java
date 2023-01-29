package src.main.presentation.controllers;

import src.main.domain.controllers.*;
import src.main.persistence.classes.exceptions.FileAlreadyExists;
import java.util.ArrayList;
import javax.swing.*;

import src.main.domain.classes.exceptions.*;

/**
 * This class represents the Presentation Controller, it is in charge of
 * controlling all the graphical interface of the program.
 * 
 * @author Lluc Clavera
 * @author Alex H
 */
public class PresentationController {

    private static PresentationController single_instance;

    /**
     * Default constructor.
     */
    private PresentationController() {
    }

    /**
     * Returns the single instance of the PresentationController.
     * 
     * @return the single instance of the PresentationController
     */
    public static PresentationController get_instance() {
        if (single_instance == null) {
            single_instance = new PresentationController();
        }
        return single_instance;
    }

    /**
     * Returns a list of authors whose name starts with the given prefix.
     * 
     * @param prefix the prefix to search for in the authors' names
     * @return a list of authors whose name starts with the given prefix
     */
    public ArrayList<String> getAuthorsByPrefix(String author) {
        return SearchController.get_instance().getAuthorsByPrefix(author);
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
        return SearchController.get_instance().setOrderingCriteria(mode, asc);
    }

    /**
     * Deletes the query with the given name.
     * 
     * @param name the name of the query to be deleted
     */
    public void deleteDocument(String title, String author) {

        try {
            DocumentController.getInstance().deleteDocument(title, author);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Returns a list of documents with the given title.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param title the title of the documents to be returned
     * @return a list of documents with the given title
     */
    public ArrayList<ArrayList<String>> getDocumentsByTitle(String title) {
        try {
            return SearchController.get_instance().getDocumentsByTitle(title);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     * Returns a list of documents that match the given boolean expression query.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param query the boolean expression query to match documents to
     * @return a list of documents that match the given boolean expression query
     */
    public ArrayList<ArrayList<String>> getDocumentsByBooleanExpression(String query) {
        try {
            return SearchController.get_instance().getDocumentsByBooleanExpression(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
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
     */
    public ArrayList<ArrayList<String>> getDocumentBySimilitude(String title, String author, int k) {
        try {
            return SearchController.get_instance().getDocumentBySimilitude(title, author, k);

        } catch (notEnoughDocuments e) {

            String[] options = new String[] { "Yes", "No" };
            String message = "You only have " + Integer.toString(e.getMaxDocs())
                    + " documents. Do you want to display the similitude with all the documents?";
            int response = JOptionPane.showOptionDialog(null, message, "Error with the number",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (response == 0) {
                return getDocumentBySimilitude(title, author, e.getMaxDocs());
            }

            else
                return null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
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
     */
    public ArrayList<ArrayList<String>> getDocumentByRelevance(String query, int k) {
        try {
            return SearchController.get_instance().getDocumentByRelevance(query, k);

        } catch (notEnoughDocuments e) {

            String[] options = new String[] { "Yes", "No" };
            String message = "You only have " + Integer.toString(e.getMaxDocs())
                    + " documents. Do you want to display the relevance with all the documents?";
            int response = JOptionPane.showOptionDialog(null, message, "Error with the number",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (response == 0) {
                return getDocumentByRelevance(query, e.getMaxDocs());
            }

            else
                return null;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     * Returns a list of all documents written by the given author.
     * Format: [[Title1, Author1], [Title2, Author2], ...]
     * 
     * @param author the author of the documents to be returned
     * @return a list of all documents written by the given author
     */
    public ArrayList<ArrayList<String>> getDocumentsByAuthor(String Author) {
        try {
            return SearchController.get_instance().getDocumentsByAuthor(Author);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    /**
     * Returns the content of a specific document
     * Format: [[Title, Author]] (to be consistent with other results)
     * 
     * @param title  the title of the document to return
     * @param author the author of the document to return
     */
    public String getDocument(String title, String Author) {
        try {
            return DocumentController.getInstance().getContent(title, Author);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }

    }

    /**
     * Creates a new query with the given name and query string.
     * 
     * @param name  the name of the query
     * @param query the query string
     * @return false if the query can not be created, true otherwise
     */
    public boolean createQuery(String name, String query) {
        try {
            SearchController.get_instance().createQuery(name, query);
            return true;
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return false;

        }

    }

    /**
     * Modifies a query
     * 
     * @param name  the name of the query
     * @param query the query string
     * @return false if the query can not be modified, true otherwise
     */
    public boolean modifyQuery(String name, String newQuery) {
        try {
            SearchController.get_instance().modifyQuery(name, newQuery);
            return true;
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    /**
     * Renames a query
     * 
     * @param name  the name of the query
     * @param query the query string
     */
    public void renameQuery(String name, String newName) {
        String content = getQuery(name).get(1);
        createQuery(newName, content);
        deleteQuery(name);
    }

    /**
     * Deletes a query
     * 
     * @param name the name of the query
     */
    public void deleteQuery(String name) {
        SearchController.get_instance().deleteQuery(name);
    }

    /**
     * returns a query in the format [name, query]
     * 
     * @param name the name of the query
     * @return the requested query
     */
    public ArrayList<String> getQuery(String name) {
        return SearchController.get_instance().getQuery(name);
    }

    /**
     * Returns all queries
     * 
     * @return the list of all queries
     */
    public ArrayList<ArrayList<String>> getQueries() {
        return SearchController.get_instance().getQueries();
    }

    /**
     * Loads the indices and querys from the disk
     */
    public void openAndLoad() {
        SearchController.get_instance().openAndLoad();
        DocumentController.getInstance().openAndLoad();
    }

    /**
     * Saves everything in the disk
     */
    public void saveAndClose() {
        SearchController.get_instance().saveAndClose();
        DocumentController.getInstance().saveAndClose();
    }

    /**
     * Uploads a document from the given file path.
     * 
     * @param path the file path of the document to be uploaded
     */
    public void uploadDocument(String path) {
        try {
            DocumentController.getInstance().uploadDocument(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Downloads the document with the given title and author to the specified file
     * path.
     * 
     * @param title  the title of the document to be downloaded
     * @param author the author of the document to be downloaded
     * @param path   the file path to save the downloaded document
     */
    public void downloadDocument(String title, String author, String path) {
        try {
            DocumentController.getInstance().downloadDocument(title, author, path);
        } catch (FileAlreadyExists e) {
            JOptionPane.showMessageDialog(null, "This document already exists!!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Creates a new empty document with the given title and author.
     * 
     * @param title  the title of the new document
     * @param author the author of the new document
     */
    public boolean createDocument(String title, String author) {

        try {
            DocumentController.getInstance().createDocument(title, author);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    /**
     * Modifies the content of the document with the given title and author.
     * 
     * @param title   the title of the document to be modified
     * @param author  the author of the document to be modified
     * @param content the new content of the document
     */
    public void modifyDocument(String title, String author, String content) {
        DocumentController.getInstance().modifyDocument(title, author, content);
    }

}
