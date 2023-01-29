package src.main.persistence.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is the Content Manager, it is responsible for loading and
 * saving the content.
 * 
 * @author Walter J.T.V.
 */

public class ContentManager {

    /**
     * Default constructor of the Content Manager.
     */
    public ContentManager() {
    }

    /**
     * This functions either saves the Content or modifies it if its already been
     * saved previously.
     * 
     * @param Content Content to be saved.
     * @param name    The name of the binary that stores the Content (by
     *                precondition the name will always be the concatenation of its
     *                Document's.
     *                title and author).
     * @throws IOException If the Content can't be saved, this exception is thrown.
     */
    public void saveContent(Object Content, String name) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/contents/" + name;
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(Content);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnne) {
            fnne.printStackTrace();
        }
    }

    /**
     * This function returns the Content located in the path ending with name name.
     * 
     * @param name The name will be used in order to find the path where its located
     *             (if the Content exists then its location ends with the
     *             concatenation of its Document's title and author).
     * @return The Content is returned with an Object type.
     * @throws IOException If the Content can't be found, this excepction is thrown.
     */
    public Object loadContent(String name) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/contents/" + name;
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
     * Fucntion to erase a the content of a Document.
     * 
     * @param name the name will be used in order to find the path where its located
     *             (if the Content exists then its location ends with the
     *             concatenation of its Document's title and author).
     */
    public void deleteContent(String name) {
        try {
            String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/contingut" + name;
            File f = new File(path);
            f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}