package src.main.presentation.classes;


import java.util.ArrayList;

/**
 * Dialog box for searching for documents using a boolean query.
 *
 * @author Alex H
 */
public class QuerySearchDialog extends AbsDialog {

    /**
     * Constructs a new QuerySearchDialog.
     *
     * @param mv        the main view of the application
     * @param opt_names an array of strings representing the default values for the
     *                  text fields (it can be null if you don't want to put
     *                  anything by default)
     */
    public QuerySearchDialog(MainView mv, String[] opt_names) {
        this.mv = mv;
        String[] s = { "Boolean query" };
        initialize(mv.getFrame(), s, 1, opt_names, "Search", "Query search options");
    }

    /**
     * Searches for documents using a boolean query and displays the results.
     */
    @Override
    public void setAction() {
        ArrayList<ArrayList<String>> res = pc.getDocumentsByBooleanExpression(getTextFromDialog()[0]);
        if (res != null) {

            ResultView rv = new ResultView(res, mv);
            mv.changePanel(rv.getPanel());
            close();

        }
    }
}
