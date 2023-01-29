package src.main.presentation.classes;

import javax.swing.*;

import java.util.ArrayList;

/**
 * Dialog box for searching for documents by relevance to a set of words.
 *
 * @author Alex H
 */
public class KRelevantSearchDialog extends AbsDialog {

    /**
     * Constructs a new KRelevantSearchDialog.
     *
     * @param mv        The main view of the application.
     * @param opt_names An array of strings representing the default values for the
     *                  text fields (it can be null if you don't want to put any
     *                  value by default).
     */
    public KRelevantSearchDialog(MainView mv, String[] opt_names) {
        this.mv = mv;
        String[] s = { "Words", "Number of similars" };
        initialize(mv.getFrame(), s, 2, opt_names, "Search", "Relevance options");
    }

    /**
     * Searches for documents by relevance to the given words and displays the
     * results.
     */
    @Override
    public void setAction() {

        String[] s = getTextFromDialog();

        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();

        // Regular expresion that recognize numbers
        if (s[1].matches("[0-9]+"))
            res = pc.getDocumentByRelevance(s[0].trim(), Integer.parseInt(s[1].trim()));
        else {
            res = null;
            JOptionPane.showMessageDialog(null, "Please introduce a natural number in the second box", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (res != null) {

            ResultView rv = new ResultView(res, mv);
            mv.changePanel(rv.getPanel());
            close();

        }
    }
}
