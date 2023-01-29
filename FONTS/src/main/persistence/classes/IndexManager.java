package src.main.persistence.classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is the Index Manager, it is responsible for loading and
 * saving the indexs (Trie, Inverted Index and Vectorial Index).
 * 
 * @author Walter J.T.V.
 */

public class IndexManager {

    /**
     * Constructor of the Index Manager.
     */
    public IndexManager() {
    }

    /**
     * Saves the current Author Index (Trie) in a specific hard-coded path.
     * 
     * @param Trie The Author Index itself with type Object.
     * @throws IOException if the Index can't be saved, this exception is thrown.
     */
    public void saveTrie(Object Trie) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/AI";
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(Trie);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnne) {
            fnne.printStackTrace();
        }
    }

    /**
     * This function returns the last saved Author Index (Trie).
     * 
     * @return The last saved Author index is returned.
     * @throws IOException if the object can't be found, this excepction is thrown.
     */
    public Object loadTrie() throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/AI";
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
     * Saves the current Vectorial Index in a specific hard-coded path.
     * 
     * @param Trie The Vectorial Index itself as type Object.
     * @throws IOException if the Index can't be saved, this exception is thrown.
     */
    public void saveVectorialIndex(Object VectorialIndex) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/VI";
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(VectorialIndex);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnne) {
            fnne.printStackTrace();
        }
    }

    /**
     * This function returns the last saved Vectorial Index.
     * 
     * @return The last saved Vectorial index is returned.
     * @throws IOException If the object can't be found, this excepction is thrown.
     */
    public Object loadVectorialIndex() throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/VI";
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
     * Saves the current Inverted Index in a specific hard-coded path.
     * 
     * @param Trie The Inverted Index itself as type Object.
     * @throws IOException If the Index can't be saved, this exception is thrown.
     */
    public void saveInvertedIndex(Object InvertedIndex) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/II";
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(InvertedIndex);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnne) {
            fnne.printStackTrace();
        }
    }

    /**
     * This function returns the last saved Inverted Index.
     * 
     * @return The last saved Inverted index is returned.
     * @throws IOException If the object can't be found, this excepction is thrown.
     */
    public Object loadInvertedIndex() throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/II";
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
}
