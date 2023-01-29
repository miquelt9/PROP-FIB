package src.main.domain.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import src.main.domain.classes.Configuration;
import src.main.domain.classes.Contents;
import src.main.domain.classes.Document;
import src.main.domain.classes.Trie;
import src.main.domain.classes.InvertedIndex;
import src.main.domain.classes.VectorialIndex;
import src.main.domain.classes.exceptions.*;
import src.main.persistence.classes.exceptions.FileAlreadyExists;
import src.main.persistence.classes.exceptions.FileDoesntExists;
import src.main.persistence.classes.exceptions.ForbiddenExtension;
import src.main.persistence.classes.exceptions.UnrecognisableFormatting;
import src.main.persistence.controllers.*;

/**
 * DocumentController class is responsible for handling document-related actions
 * such as creation, uploading, and deletion.
 * 
 * It also implements the Singleton design pattern to ensure that only one
 * instance of the controller exists at any given time.
 * 
 * @author Walter J.T.V
 * @author Lluc Clavera
 */
public class DocumentController {
    private static DocumentController single_instance = null;

    /**
     * Default constructor.
     * This constructor is private to prevent the creation of additional instances
     * of the controller.
     */
    private DocumentController() {
    }

    /**
     * Returns the single instance of the DocumentController.
     * If the instance has not been created yet, it creates a new instance before
     * returning it.
     * 
     * @return The single instance of the DocumentController.
     */
    public static DocumentController getInstance() {
        if (single_instance == null) {
            single_instance = new DocumentController();
        }
        return single_instance;
    }

    /*------------------ Document creator------------------*/

    /**
     * Creates a new empty document with the given title and author.
     * 
     * @param title  The title of the new document.
     * @param author The author of the new document.
     * @throws emptyAuthor      If the author is empty.
     * @throws alreadyExistsDoc If a document with the same title and author already
     *                          exists.
     */
    public void createDocument(String title, String author) throws emptyAuthor, alreadyExistsDoc {
        title = title.toLowerCase();
        author = author.toLowerCase();
        Document d = new Document(title, author);
        Trie.get_instance().setAuthorDoc(author, d);
        VectorialIndex.get_instance().InsertDocument(d);
        String name = title + "_" + author;
        Contents con = d.getContents();
        PersistenceController.getInstance().saveDocument(d, name);
        PersistenceController.getInstance().saveContent(con, name);
        PersistenceController.getInstance().saveInvertedIndex(InvertedIndex.getInstance());
        PersistenceController.getInstance().saveTrie(Trie.get_instance());
        PersistenceController.getInstance().saveVectorialIndex(VectorialIndex.get_instance());
        d.setContent("");
    }

    /**
     * Creates a new empty document with the given title and author.
     * 
     * @param title  The title of the new document.
     * @param author The author of the new document.
     * @throws emptyAuthor      If the author is empty.
     * @throws alreadyExistsDoc If a document with the same title and author already
     *                          exists.
     */
    public void createDocument(String title, String author, String content)
            throws emptyTitle, emptyAuthor, alreadyExistsDoc {
        title = title.toLowerCase();
        author = author.toLowerCase();
        Document d = new Document(title, author, content);
        VectorialIndex.get_instance().InsertDocument(d);
        Trie.get_instance().setAuthorDoc(author, d);
        String name = title + "_" + author;
        Contents conts = d.getContents();
        PersistenceController.getInstance().saveDocument(d, name);
        PersistenceController.getInstance().saveContent(conts, name);
        PersistenceController.getInstance().saveInvertedIndex(InvertedIndex.getInstance());
        PersistenceController.getInstance().saveTrie(Trie.get_instance());
        PersistenceController.getInstance().saveVectorialIndex(VectorialIndex.get_instance());
        d.setContent("");
    }

    /*------------------ Document Uploader------------------*/

    /**
     * Uploads a document from the given file path.
     * 
     * @param path the file path of the document to be uploaded.
     * @throws ForbiddenExtension       If the file extension is not recognized.
     * @throws UnrecognisableFormatting If the formatting of the file is not
     *                                  recognized.
     * @throws FileDoesntExists         If the file does not exist at the given
     *                                  path.
     * @throws emptyAuthor              If the author of the document is empty.
     * @throws alreadyExistsDoc         If a document with the same title and author
     *                                  already exists.
     * @throws emptyTitle
     */
    public void uploadDocument(String path)
            throws ForbiddenExtension, UnrecognisableFormatting, FileDoesntExists, emptyAuthor, alreadyExistsDoc, emptyTitle {
        String extension = Optional.ofNullable(path).filter(f -> f.contains("."))
                .map(f -> f.substring(path.lastIndexOf(".") + 1)).orElse(null);
        extension = extension.toLowerCase();
        ArrayList<String> result;
        switch (extension) {
            case "xml":
                result = XMLController.getInstance().uploadDocument(extension, path, false);
                break;

            case "txt":
                result = TXTController.getInstance().uploadDocument(extension, path, false);
                break;

            case "sus":
                result = SUSController.getInstance().uploadDocument(extension, path, false);
                break;

            default:
                throw new ForbiddenExtension(extension);
        }

        String title = result.get(0);
        String author = result.get(1);
        String content = result.get(2);
        createDocument(title, author, content);
        // VectorialIndex.get_instance().InsertDocument(d);
        // Trie.get_instance().setAuthorDoc(getAuthor(), d);
    }

    /*------------------ Document eraser ------------------*/

    /**
     * Deletes the given document.
     * 
     * @param d The document to be deleted.
     */
    public void deleteDocument(Document d) {
        try {
            VectorialIndex.get_instance().EraseDocument(d);
            InvertedIndex.getInstance().deleteDocument(d);
            Trie.get_instance().deleteDoc(d.getAuthor().toLowerCase(), d.getTitle().toLowerCase());
            String name = d.getTitle().toLowerCase() + "_" + d.getAuthor().toLowerCase();
            PersistenceController.getInstance().deleteDocument(name);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Deletes the document with the given title and author.
     * 
     * @param title  The title of the document to be deleted.
     * @param author The author of the document to be deleted.
     * @throws notFoundDocument If the document with the given title and author does
     *                          not exist.
     * @throws notFoundAuthor   If the author does not exist.
     * @throws emptyTitle       If the title is empty.
     * @throws emptyAuthor      If the author is empty.
     */
    public void deleteDocument(String title, String author)
            throws notFoundDocument, notFoundAuthor, emptyTitle, emptyAuthor {
        title = title.toLowerCase();
        author = author.toLowerCase();
        Document d = Trie.get_instance().getDoc(title, author);
        VectorialIndex.get_instance().EraseDocument(d);
        InvertedIndex.getInstance().deleteDocument(d);
        Trie.get_instance().deleteDoc(title, author);
        String name = title + "_" + author;
        PersistenceController.getInstance().deleteDocument(name);
    }

    /*------------------ Document modifier ------------------*/

    /**
     * Modifies the content of the document with the given title and author.
     * 
     * @param title   The title of the document to be modified.
     * @param author  The author of the document to be modified.
     * @param content The new content of the document.
     */
    public void modifyDocument(String title, String author, String content) {
        title = title.toLowerCase();
        author = author.toLowerCase();
        try {
            Document d = Trie.get_instance().getDoc(title, author);
            InvertedIndex.getInstance().deleteDocument(d);
            d.setContent(content);
            Contents conts = d.getContents();
            String name = title + "_" + author;
            PersistenceController.getInstance().saveDocument(d, name);
            PersistenceController.getInstance().saveContent(conts, name);
            PersistenceController.getInstance().saveInvertedIndex(InvertedIndex.getInstance());
            d.setContent("");
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("No s'ha pogut modificar el document.");
        }
    }

    /*------------------ Document downloader(getter)------------------*/

    /**
     * Downloads the document with the given title and author to the specified file
     * path.
     * 
     * @param title  The title of the document to be downloaded.
     * @param author The author of the document to be downloaded.
     * @param path   The file path to save the downloaded document.
     * @throws FileAlreadyExists  If a file already exists at the given path.
     * @throws ForbiddenExtension If the file extension is not recognized.
     */
    public void downloadDocument(String title, String author, String path)
            throws FileAlreadyExists, ForbiddenExtension {
        String extension = Optional.ofNullable(path).filter(f -> f.contains("."))
                .map(f -> f.substring(path.lastIndexOf(".") + 1)).orElse(null);
        String name = title + "_" + author;
        Contents aux = (Contents) PersistenceController.getInstance().loadContent(name);
        String contents = aux.getRawContents();
        switch (extension) {
            case "xml":
                XMLController.getInstance().downloadDocument(extension, path, title, author, contents, false);
                break;

            case "txt":
                TXTController.getInstance().downloadDocument(extension, path, title, author, contents, false);
                break;

            case "sus":
                SUSController.getInstance().downloadDocument(extension, path, title, author, contents, false);
                break;

            default:
                throw new ForbiddenExtension(extension);
        }
    }

    /**
     * Returns the document with the given title and author.
     * 
     * @param title  The title of the document to be returned.
     * @param author The author of the document to be returned.
     * @return The document with the given title and author.
     */
    public Document getDocument(String title, String author) {
        title = title.toLowerCase();
        author = author.toLowerCase();

        try {
            Document d = Trie.get_instance().getDoc(title, author);
            return d;
        } catch (Exception e) {
            System.err.println(e);
            System.err.println("No s'ha pogut obtenir el document.");
            return null;
        }
    }

    /**
     * Opens and loads the saved configuration, trie, vectorial index, and inverted
     * index.
     */
    public void openAndLoad() {

        PersistenceController PersCtrl = PersistenceController.getInstance();
        Configuration conf = (Configuration) PersCtrl.loadConfiguration();
        Trie tr = (Trie) PersCtrl.loadTrie();
        VectorialIndex VecInd = (VectorialIndex) PersCtrl.loadVectorialIndex();
        InvertedIndex InvInd = (InvertedIndex) PersCtrl.loadInvertedIndex();
        InvInd.set_instance();
        VecInd.set_instance();
        tr.set_instance();

    }

    /**
     * Saves the configuration, trie, vectorial index, and inverted index befor
     * closing the program.
     */
    public void saveAndClose() {
        PersistenceController PersCtrl = PersistenceController.getInstance();
        PersCtrl.saveConfiguration(Configuration.get_instance());
        PersCtrl.saveTrie(Trie.get_instance());
        PersCtrl.saveVectorialIndex(VectorialIndex.get_instance());
        PersCtrl.saveInvertedIndex(InvertedIndex.getInstance());
    }

    /**
     * Assings a document its content.
     * 
     * @param d The document whose content will be set.
     */
    public void setContent(Document d) {
        String name = d.getTitle().toLowerCase() + "_" + d.getAuthor().toLowerCase();
        Contents DocContent = (Contents) PersistenceController.getInstance().loadContent(name);
        d.setContent(DocContent);
    }

    /**
     * Returns the content of the document with the given title and author.
     * 
     * @param title  The title of the document whose content will be returned.
     * @param author The author of the document whose content will be returned.
     * @return The content of the document with the given title and author.
     * @throws notFoundDocument If the document with the given title and author does
     *                          not exist.
     * @throws notFoundAuthor   If the author does not exist in the system.
     * @throws emptyTitle       If the title is an empty string
     * @throws emptyAuthor      If the author is an empty string.
     */
    public String getContent(String title, String author)
            throws notFoundDocument, notFoundAuthor, emptyTitle, emptyAuthor {
        String name = title + "_" + author;
        Document d = Trie.get_instance().getDoc(title, author);
        d.setModificationDate(new Date());
        Contents con = (Contents) PersistenceController.getInstance().loadContent(name);
        return con.getRawContents();
    }
}
