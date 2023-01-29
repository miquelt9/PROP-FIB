package src.main.presentation.classes;

import java.util.ArrayList;

/**
 * Dialog box for searching for documents by author.
 */
public class AuthSearchDialog extends AbsDialog {

    /**
     * Constructs a new AuthSearchDialog.
     *
     * @param mv        The main view of the application.
     * @param opt_names An array of strings representing the default values for the
     *                  text field.
     */
    public AuthSearchDialog(MainView mv, String[] opt_names) {
        String[] s = { "Author" };
        this.mv = mv;
        // Initialize the dialog box with the given parameters
        initialize(mv.getFrame(), s, 1, opt_names, "Search", "Author Search Options");
    }

    /**
     * Searches for documents by the given author and displays the results.
     */
    @Override
    public void setAction() {
        // Get the text from the text field of the dialog box
        String[] s = getTextFromDialog();
        // Search for documents by the given author
        ArrayList<ArrayList<String>> res = pc.getDocumentsByAuthor(s[0].trim());
        // Create a new result view with the search results and the main view
        if (res != null) {
            ResultView rv = new ResultView(res, mv);
            mv.changePanel(rv.getPanel());
            close();
        }
        // Close the dialog box
    }
}
