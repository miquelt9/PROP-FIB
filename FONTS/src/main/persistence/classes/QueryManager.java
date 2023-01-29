package src.main.persistence.classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is the Query Manager, it is responsible for loading and
 * saving the queries.
 * 
 * @author Walter J.T.V
 */

public class QueryManager {

    public QueryManager() {
    }

    /**
     * Save all the queries in its specific folder.
     * 
     * @param queries this structure contains all the queries in the program, each
     *                query associated with its name , as the key of the hashmap,
     *                and the value, the query on a Object format
     * 
     * @throws IOException If any Unexpected IO error is thrown
     */
    public void saveQueries(Object queries) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/Queries";
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(queries);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnaf) {
            fnaf.printStackTrace();
        }
    }

    /**
     * Load the queries from its specific folder.
     * 
     * @return The object with of the queries file.
     * @throws IOException If the file is not opened correctly.
     */
    public Object loadQueries() throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/Queries";
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
