package src.main.persistence.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is the Document Manager, it is responsible for loading and
 * saving the documents.
 * 
 * @author Walter J.T.V.
 */

public class DocumentManager {

    /**
     * Constructor of the Document Manager.
     */
    public DocumentManager() {
    }

    /**
     * This function either saves the document or modifies it if its already been
     * saved previously.
     * 
     * @param document Document to be saved.
     * @param name     The name of the binary that stores the document (by
     *                 precondition the name will always be the concatenation of its
     *                 title and author).
     * @throws IOException If the object can't be saved, this exception is thrown.
     */
    public void saveDocument(Object document, String name) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/documents/" + name;
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(document);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnne) {
            fnne.printStackTrace();
        }
    }

    /**
     * This function returns the document located in the path ending with name name.
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the document exists then its location ends with the
     *             concatenation of its title and author).
     * @return The document is returned as an Object type.
     * @throws IOException If the object can't be found, this excepction is thrown.
     */
    public Object loadDocument(String name) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/documents/" + name;
        Object obj = new Object();
        try {
            FileInputStream filein = new FileInputStream(path);
            ObjectInputStream objectin = new ObjectInputStream(filein);
            obj = objectin.readObject();
            objectin.close();

        } catch (FileNotFoundException ffne) {
            ffne.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

    /**
     * Function to erase a document.
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the document exists then its location ends with the
     *             concatenation of its title and author).
     */
    public void deleteDocument(String name) {
        try {
            String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/documents" + name;
            File f = new File(path);
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
