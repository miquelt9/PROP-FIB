package src.main.presentation.classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.main.presentation.controllers.*;

/**
 * Dialog box for adding a new query to the application.
 * 
 * @author Alex H
 */
public class AddQueryDialog extends AbsDialog {

    /**
     * Constructs a new AddQueryDialog.
     *
     * @param fr        The parent frame of the dialog bo.
     * @param opt_names An array of strings representing the default values for the
     *                  text fields (it can be null if you don't want to put
     *                  anything).
     * @param model     The table model for the table of queries.
     */
    public AddQueryDialog(JFrame fr, String[] opt_names, DefaultTableModel model) {
        String[] s = { "Query", "Query name" };
        this.fr = fr;
        this.model = model;
        // Initialize the dialog box with the given parameters
        initialize(fr, s, 2, opt_names, "Add", "Create your query");
    }

    /**
     * Adds the new query to the application and updates the table of queries.
     */
    @Override
    public void setAction() {

        String[] s = getTextFromDialog();
        PresentationController pc = PresentationController.get_instance();

        if (s[0].length() != 0 && s[1].length() != 0) {

            if (pc.createQuery(s[1], s[0])) {
                model.addRow(new Object[] { s[1], s[0] });
                close();

            }
        }

    }

    private JFrame fr;
    private DefaultTableModel model;
}
