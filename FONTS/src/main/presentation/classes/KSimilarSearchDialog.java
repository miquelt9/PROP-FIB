package src.main.presentation.classes;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Dialog box for searching for documents by similitude to a given document.
 *
 * @author Alex H
 */
public class KSimilarSearchDialog extends AbsDialog {

    /**
     * Constructs a new KSimilarSearchDialog.
     *
     * @param mv        The main view of the application.
     * @param opt_names An array of strings representing the default values for the
     *                  text fields (it can be null if you don't want to put
     *                  anything.
     */
    public KSimilarSearchDialog(MainView mv, String[] opt_names) {
        this.mv = mv;
        String[] s = { "Title", "Author", "Number of similar" };
        // Initialize the dialog box with the given parameters
        initialize(mv.getFrame(), s, 3, opt_names, "Search", "Similitude Search options");
    }

    /**
     * Searches for documents by similitude to the given document and displays the
     * results.
     */
    @Override
    public void setAction() {
        String[] s = getTextFromDialog();

        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();

        // Regular expression that only recgnize natural numbers
        if (s[2].matches("[0-9]+"))
            res = pc.getDocumentBySimilitude(s[0].trim(), s[1].trim(), Integer.parseInt(s[2]));
        else {
            res = null;
            JOptionPane.showMessageDialog(null, "Please introduce a natural number in the third box" , "Warning", JOptionPane.WARNING_MESSAGE);
        }
        if (res != null) {

            ResultView rv = new ResultView(res, mv);
            mv.changePanel(rv.getPanel());
            close();
        }
    }
}
