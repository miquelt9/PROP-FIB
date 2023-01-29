package src.main.domain.classes;

import java.io.Serializable;
import java.util.*;
import src.main.domain.classes.exceptions.*;

/**
 * This class represents a Node used in the Trie Class.
 */
class Node implements Serializable {

    char info;
    Node left, right, center;
    Hashtable<String, Document> hash_docs;
    boolean end;

    /**
     * Default constructor.
     */
    public Node() {

        left = right = center = null;
        info = '?';
        end = false;
        hash_docs = new Hashtable<String, Document>();

    }

    /**
     * Creates the author in the Trie and return the last node of this.
     * 
     * @param p      Node from which the children will be created.
     * @param author Name of the author to be added.
     * @param i      I-th letter of the author (used to iterate).
     * @return The last node from the author in the Trie.
     * @throws emptyAuthor
     */
    public static Node createNode(Node p, String author, int i) throws emptyAuthor {

        author = author.toLowerCase();
        if (author.isEmpty())
            throw new emptyAuthor();

        if (i < author.length() && p.info != '?' && p.info > author.charAt(i)) {

            if (p.left == null)
                p.left = new Node();
            return createNode(p.left, author, i);
        }

        else if (i < author.length() && p.info != '?' && p.info < author.charAt(i)) {
            if (p.right == null)
                p.right = new Node();
            return createNode(p.right, author, i);

        }

        else {
            if (i == author.length()) {
                p.end = true;
                return p;
            } else
                p.info = author.charAt(i);
            if (p.center == null)
                p.center = new Node();
            return createNode(p.center, author, i + 1);

        }
    }

    /**
     * Looks for an author in a given node.
     * 
     * @param p      Node from it will look for.
     * @param author Name of the author searched for.
     * @param i      I-th letter of the author (used to iterate).
     * @return The node which is the end of the author's name, null if it doesn't
     *         exist.
     * @throws emptyAuthor
     */
    public static Node findNode(Node p, String author, int i) throws emptyAuthor {

        author = author.toLowerCase();
        if (author.isEmpty())
            throw new emptyAuthor();
        if (p == null)
            return null;

        if (i < author.length() && p.info != '?' && p.info > author.charAt(i))
            return findNode(p.left, author, i);
        else if (i < author.length() && p.info != '?' && p.info < author.charAt(i))
            return findNode(p.right, author, i);
        else {

            if (i == author.length())
                return p;
            else
                return findNode(p.center, author, i + 1);
        }
    }

    /**
     * Looks for an author in a given node but doesn't throw exception
     * 
     * @param p      Node from it will look for.
     * @param author Name of the author searched for.
     * @param i      I-th letter of the author (used to iterate).
     * @return The node which is the end of the author's name, null if it doesn't
     *         exist.
     */
    public static Node findNodeNotSecure(Node p, String author, int i) {

        author = author.toLowerCase();
        if (p == null)
            return null;

        if (i < author.length() && p.info != '?' && p.info > author.charAt(i))
            return findNodeNotSecure(p.left, author, i);
        else if (i < author.length() && p.info != '?' && p.info < author.charAt(i))
            return findNodeNotSecure(p.right, author, i);
        else {

            if (i == author.length())
                return p;
            else
                return findNodeNotSecure(p.center, author, i + 1);
        }
    }

}

/**
 * This class represents the Trie data Structure.
 */
public class Trie implements Serializable {

    private Node root;
    private static Trie single_instance;

    /**
     * Default constructor.
     */
    private Trie() {
        root = null;
    }

    /**
     * Get a single instance of Trie
     * 
     * @return The Trie instance.
     */
    public static Trie get_instance() {
        if (single_instance == null)
            single_instance = new Trie();
        return single_instance;
    }

    /**
     * Sets the single intsnce to "this"
     */
    public void set_instance() {
        single_instance = this;
    }

    /**
     * Create an author if it doesn't exists and then assign the given document to
     * them.
     * 
     * @param author New author to be created.
     * @param d      Document to which the author will be assigned.
     * @throws alreadyExistsDoc If the document already exists.
     * @throws emptyAuthor      If the author is empty
     */
    public void setAuthorDoc(String author, Document d) throws alreadyExistsDoc, emptyAuthor {
        author = author.toLowerCase();
        if (root == null)
            root = new Node();
        Node p = Node.createNode(root, author, 0);

        if (p.hash_docs.containsKey(d.getTitle().toLowerCase()))
            throw new alreadyExistsDoc(d.getTitle(), author);

        else
            p.hash_docs.put(d.getTitle().toLowerCase(), d);

    }

    /**
     * Modifies a document's content given its title and author.
     * 
     * @param title   Title of the document to be modified.
     * @param author  Author of the document to be modified.
     * @param content New content to be set on the document.
     * @throws notFoundAuthor   If the author doesn't exits.
     * @throws notFoundDocument If the Document doesn't exist.
     * @throws emptyAuthor      If the author is empty
     * @throws emptyTitle       If the title is empty
     */
    public void modifyDoc(String title, String author, String content)
            throws notFoundAuthor, notFoundDocument, emptyTitle, emptyAuthor {

        title = title.toLowerCase();
        if (title.isEmpty())
            throw new emptyTitle();

        author = author.toLowerCase();
        Node p = Node.findNode(root, author, 0);

        if (p == null || !p.end)
            throw new notFoundAuthor(author);

        if (!p.hash_docs.containsKey(title))
            throw new notFoundDocument(title, author);

        else
            p.hash_docs.get(title).setContent(content);
    }

    /**
     * Deletes a document given the name of it and its author.
     * 
     * @param docName Name of the document (title) to be deleted.
     * @param author  Name of the author of the document to be deleted.
     * @throws notFoundDocument If the document isn't found.
     * @throws notFoundAuthor   If the author doesn't exist.
     * @throws emptyAuthor      If the author is empty
     * @throws emptyTitle       If the title is empty
     */
    public void deleteDoc(String docName, String author)
            throws notFoundDocument, notFoundAuthor, emptyTitle, emptyAuthor {

        docName = docName.toLowerCase();
        if (docName.isEmpty())
            throw new emptyTitle();
        author = author.toLowerCase();
        Node p = Node.findNode(root, author, 0);

        if (p == null || !p.end)
            throw new notFoundAuthor(author);

        else {

            if (!p.hash_docs.containsKey(docName))
                throw new notFoundDocument(docName, author);
            else
                p.hash_docs.remove(docName);

        }
    }

    /**
     * Get the document of the title and the given author.
     * 
     * @param docName Name of the document (title) to get.
     * @param author  Name of the author of the document to get.
     * @return The document with the given title and author.
     * @throws notFoundDocument If the document is not found.
     * @throws notFoundAuthor   If the author doesn't exist.
     * @throws emptyAuthor      If the author is empty
     * @throws emptyTitle       If the title is empty
     */
    public Document getDoc(String docName, String author)
            throws notFoundDocument, notFoundAuthor, emptyTitle, emptyAuthor {

        docName = docName.toLowerCase();
        if (docName.isEmpty())
            throw new emptyTitle();
        author = author.toLowerCase();
        Node p = Node.findNode(root, author, 0);

        if (p == null || !p.end)
            throw new notFoundAuthor(author);

        else {

            if (!p.hash_docs.containsKey(docName))
                throw new notFoundDocument(docName, author);
            else
                return p.hash_docs.get(docName);

        }
    }

    /**
     * Get the list of document that have the given title.
     * 
     * @param title Title of the document searched for.
     * @throws emptyTitle If the title is empty
     * 
     * @return An ArrayList of Documents that have the given title.
     */

    public ArrayList<Document> getDocumentsByTitle(String title) throws emptyTitle {

        if (title.isEmpty())
            throw new emptyTitle();
        ArrayList<Document> docs = new ArrayList<Document>();
        i_getDocumentsByTitle(root, title, docs);

        return docs;
    }

    /**
     * Deletes a document given the name of it and its author.
     * 
     * @param title  Title of the document to be deleted.
     * @param author Name of the author of the document to be deleted.
     * @throws emptyAuthor If the author is empty
     * @throws emptyTitle  If the title is empty
     */
    public void deleteDocument(String title, String author) throws emptyTitle, emptyAuthor {

        if (title.isEmpty())
            throw new emptyTitle();
        Node p = Node.findNode(root, author, 0);

        p.hash_docs.remove(title);

    }

    private void i_getDocumentsByTitle(Node p, String title, ArrayList<Document> docs) {

        if (p != null) {
            if (p.end && p.hash_docs.containsKey(title))
                docs.add(p.hash_docs.get(title));

            i_getDocumentsByTitle(p.left, title, docs);
            i_getDocumentsByTitle(p.right, title, docs);
            i_getDocumentsByTitle(p.center, title, docs);
        }
    }

    /**
     * Get the documents of the given author.
     * 
     * @param author Name of the author searched for.
     * @return A collection of documents from the given author.
     * @throws authorWithoutDocs If the author has no documents assigned.
     * @throws notFoundAuthor    If the author doesn't exist.
     * @throws emptyAuthor       If the author is empty
     */
    public Collection<Document> getDocs(String author) throws authorWithoutDocs, notFoundAuthor, emptyAuthor {

        author = author.toLowerCase();
        Node p = Node.findNode(root, author, 0);

        if (p == null || !p.end)
            throw new notFoundAuthor(author);

        else {

            if (p.hash_docs.size() == 0)
                throw new authorWithoutDocs(author);

            else
                return p.hash_docs.values();
        }
    }

    /**
     * Get the list of the authors that has the prefix given.
     * 
     * @param pref The prefix given for search the authors.
     * @return An arraylist with all the author that has the prefix passed.
     */
    public ArrayList<String> getAuthPref(String pref) {

        pref = pref.toLowerCase();
        Node p = Node.findNodeNotSecure(root, pref, 0);

        ArrayList<String> v = new ArrayList<String>();

        if (p == null)
            return v;

        i_getNames(p, v, pref);

        return v;
    }

    /**
     * Internal version of getAuthPref method to be call recursively.
     */
    private void i_getNames(Node p, ArrayList<String> v, String name) {

        if (p != null) {

            if (p.end) {
                v.add(name);
            }
                

            if (p.info != '?') {
                i_getNames(p.left, v, name);
                i_getNames(p.right, v, name);

                name += p.info;
                i_getNames(p.center, v, name);

                name.substring(0, name.length() - 1);
            }
        }
    }
}
