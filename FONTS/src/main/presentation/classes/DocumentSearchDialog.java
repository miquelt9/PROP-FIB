package src.main.presentation.classes;

import java.util.ArrayList;
import src.main.presentation.controllers.*;

/**
 * Dialog box for searching for documents by title or author.
 * 
 * @author Alex H
 */
public class DocumentSearchDialog extends AbsDialog {

    /**
     * Constructs a new DocumentSearchDialog.
     *
     * @param mv        The main view of the application.
     * @param opt_names An array of strings representing the default values for the
     *                  text fields (it can be null if you don't want to put default
     *                  values).
     */
    public DocumentSearchDialog(MainView mv, String[] opt_names) {
        this.mv = mv;
        String[] s = { "Title", "Author" };
        // Initialize the dialog box with the given parameters
        initialize(mv.getFrame(), s, 2, opt_names, "Search", "Document Search Options");
    }

    /**
     * Searches for documents by the given title or author and displays the results.
     */
    @Override
    public void setAction() {
        String[] s = getTextFromDialog();
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        PresentationController pc = PresentationController.get_instance();

        if (s[1].length() == 0 && s[0].length() != 0) {
            // Search for documents by the given title
            res = pc.getDocumentsByTitle(s[0].trim());
        } else if (s[1].length() != 0 && s[0].length() == 0) {
            // Search for documents by the given author
            res = pc.getDocumentsByAuthor(s[1].trim());
        }
        // This is for throwing exception
        else if (s[0].isEmpty() && s[1].isEmpty())
            res = pc.getDocumentsByTitle(s[0].trim());

        else {
            new ContentView(s[0].trim(), s[1].trim(), mv, new ArrayList<ArrayList<String>>());
            res = null;
            close();
        }

        if (res != null) {

            ResultView rv = new ResultView(res, mv);
            mv.changePanel(rv.getPanel());
            close();
        }
    }
}