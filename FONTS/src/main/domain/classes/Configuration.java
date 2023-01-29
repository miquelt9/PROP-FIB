package src.main.domain.classes;

import java.io.Serializable;

/**
 * This class represents the configuration.
 */
public class Configuration implements Serializable {

    /**
     * Default constructor for Configuration.
     */
    private Configuration() {
        configurationMode = ConfigurationModes.AUTHOR;
        ascendant = true;
    }

    /**
     * Instance getter of configuration.
     */
    public static Configuration get_instance() {
        if (single_instance == null)
            single_instance = new Configuration();
        return single_instance;
    }

    /**
     * Funciton to check the ascendent boolean status.
     * 
     * @return True if the sorting criteria used to compare on the configuration
     *         is set to ascendent.
     */
    public boolean isAscendent() {
        return ascendant;
    }

    /**
     * Set the configuration to a specific mode.
     * 
     * @param mode Mode which will be set as the one to compare different documents.
     */
    public void setConfigurationMode(String mode) {
        // if (!EnumUtils.isValidEnum(ConfigurationModes, mode))
        // throw new modeNotFound(mode);
        configurationMode = ConfigurationModes.valueOf(mode);
    }

    /**
     * Sets the comparation criteria to ascendent if b == true, descendent
     * otherwise.
     * 
     * @param b boolean to indicate if the sorting will be done in ascending or
     *          descending order.
     */
    public void setAscendent(boolean b) {
        ascendant = b;
    }

    /**
     * Used to compare two documents by their characteristics (previously set in the
     * setconfigurationMode method).
     * 
     * @param a Document which will be compared against b.
     * @param b Document which will be compared against a.
     * @return True if the a < b in ascendent mode or b < a in descendent mode given
     *         one of the configuration modes, false otherwise.
     */
    public boolean cmp(Document a, Document b) {

        switch (configurationMode) {
            case CREATION_DATE:
                boolean b2 = a.getCreationDate().before(b.getCreationDate());
                return (ascendant) ? b2 : !b2;

            case MODIFIED_DATE:
                boolean b3 = a.getModificationDate().before(b.getModificationDate());
                return (ascendant) ? b3 : !b3;

            case ACCES_DATE:
                boolean b4 = a.getAccesDate().before(b.getAccesDate());
                return (ascendant) ? b4 : !b4;

            case TITLE:
                int comp = a.getTitle().compareTo(b.getTitle());
                return (ascendant) ? comp > 0 : comp <= 0;

            case AUTHOR:
                int comp2 = a.getAuthor().compareTo(b.getAuthor());
                return (ascendant) ? comp2 > 0 : comp2 <= 0;

            case NUM_LINES:
                if (a.getNumLines() == b.getNumLines()) {
                    int comp3 = a.getTitle().compareTo(b.getTitle());
                    return (ascendant) ? comp3 <= 0 : comp3 > 0;
                }
                boolean b5 = a.getNumLines() < b.getNumLines();
                return (ascendant) ? b5 : !b5;

            case NUM_WORDS:
                if (a.getNumWord() == b.getNumWord()) {
                    int comp4 = a.getTitle().compareTo(b.getTitle());
                    return (ascendant) ? comp4 <= 0 : comp4 > 0;
                }

                boolean b6 = a.getNumWord() < b.getNumWord();
                return (ascendant) ? b6 : !b6;

            default:
                System.err.println("Error in cmp function using configurationMode: " + configurationMode);
                return false;
        }
    }

    // Macros for set sorting mode
    public static String NUMBER_OF_WORDS = "NUM_WORDS";
    public static String NUMBER_OF_LINES = "NUM_LINES";
    public static String AUTHOR = "AUTHOR";
    public static String TITLE = "TITLE";
    public static String ACCES_DATE = "ACCES_DATE";
    public static String CREATION_DATE = "CREATION_DATE";
    public static String MODIFIED_DATE = "MODIFIED_DATE";

    private ConfigurationModes configurationMode;

    private boolean ascendant;

    private static Configuration single_instance;

    private enum ConfigurationModes {
        NUM_LINES,
        NUM_WORDS,
        AUTHOR,
        TITLE,
        ACCES_DATE,
        CREATION_DATE,
        MODIFIED_DATE
    }

}
