package src.main.presentation.classes;


/**
 * Dialog box for searching for authors by prefix.
 */
public class PrefSearchDialog extends AbsDialog {

    /**
     * Constructs a new PrefSearchDialog.
     *
     * @param mv        The main view of the application
     * @param opt_names An array of strings representing the default values for the
     *                  text fields (it can be null if you don't want to put
     *                  anything)
     */
    public PrefSearchDialog(MainView mv, String[] opt_names) {
        this.mv = mv;
        String[] s = { "Prefix" };
        initialize(mv.getFrame(), s, 1, opt_names, "Search", "Prefix options");
    }

    /**
     * Searches for authors by prefix and displays the results.
     */
    @Override
    public void setAction() {
        String[] s = getTextFromDialog();
        PrefixView pv = new PrefixView(pc.getAuthorsByPrefix(s[0].trim()), mv);
        mv.changePanel(pv.getPanel());
        close();
    }
}
