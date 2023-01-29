package src.main.persistence.classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class is the Configuration Manager, it is responsible for loading and
 * saving the configuration.
 * 
 * @author Walter J.T.V.
 */

public class ConfigurationManager {

    public ConfigurationManager() {
    }

    /**
     * Saves the global configuration of the program.
     * 
     * @param configuration This object is the configuration of the whole program.
     * @throws IOException If the Configuration can't be saved, this exception is
     *                     thrown.
     */
    public void saveConfiguration(Object configuration) throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/Configuration";
        try {
            FileOutputStream fileout = new FileOutputStream(path);
            ObjectOutputStream objectout = new ObjectOutputStream(fileout);
            objectout.writeObject(configuration);
            objectout.flush();
            objectout.close();
        } catch (FileNotFoundException fnaf) {
            fnaf.printStackTrace();
        }
    }

    /**
     * Returns the last saved configuration.
     * 
     * @return The last saved value of the program configuration.
     * @throws IOException If the Configuration can't be located, this exception is
     *                     thrown.
     */
    public Object loadConfiguration() throws IOException {
        String path = System.getProperty("user.dir") + "/../FONTS/src/main/persistence/bin/Configuration";
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
