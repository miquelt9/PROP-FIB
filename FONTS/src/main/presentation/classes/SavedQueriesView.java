package src.main.presentation.classes;

import src.main.presentation.controllers.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.ArrayList;

/**
 * This class represents a view for displaying and interacting with saved
 * queries.
 *
 * @author Alex H
 */
public class SavedQueriesView {

    /**
     * Constructs a new SavedQueriesView with the given main view.
     *
     * @param mv the main view
     */
    public SavedQueriesView(MainView mv) {

        this.mv = mv;
        initialize();

    }

    /**
     * Initializes the saved queries view.
     */
    private void initialize() {

        ArrayList<ArrayList<String>> queries = PresentationController.get_instance().getQueries();
        Object[][] docs = new Object[queries.size()][2];
        String[] names = { "Query Name", "Query" };

        for (int i = 0; i < queries.size(); ++i) {

            docs[i][0] = queries.get(i).get(0);
            docs[i][1] = queries.get(i).get(1);

        }

        model = new DefaultTableModel(docs, names);

        table = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        popupMenu = new JPopupMenu();
        JMenuItem search = new JMenuItem("Search");
        search.addActionListener(e -> searchQuery());
        JMenuItem delete = new JMenuItem("Delete");
        delete.addActionListener(e -> deleteQuery());
        JMenuItem change = new JMenuItem("Change Name");
        change.addActionListener(e -> changeQueryName());
        JMenuItem changeQuery = new JMenuItem("Change Query");
        changeQuery.addActionListener(e -> changeQuery());
        popupMenu.add(search);
        popupMenu.add(delete);
        popupMenu.add(change);
        popupMenu.add(changeQuery);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    int row = table.rowAtPoint(e.getPoint());
                    lastQueryClicked = String.valueOf(table.getValueAt(row, 1));
                    lastNameClicked = String.valueOf(table.getValueAt(row, 0));
                    lastRowClicked = row;

                }
            }
        });

        add_but = new JButton("Add");
        add_but.addActionListener(e -> new AddQueryDialog(fr, null, model));

        searcher = new JTextField();
        searcher.addActionListener(e -> setSearcher());

        fr = new JFrame();

        pane = new JScrollPane(table);
        fr.setVisible(true);
        fr.setSize(500, 500);
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.add(pane);
        fr.add(add_but, BorderLayout.SOUTH);
        fr.add(searcher, BorderLayout.NORTH);
        fr.setLocationRelativeTo(null);

    }

    /**
     * Sets the searcher text field.
     */
    private void setSearcher() {

        String result = searcher.getText();
        model.setRowCount(0);

        ArrayList<ArrayList<String>> queries = PresentationController.get_instance().getQueries();

        for (int i = 0; i < queries.size(); ++i) {

            String col1 = queries.get(i).get(0);
            String col2 = queries.get(i).get(1);

            if (col1.contains(result) || col2.contains(result))
                model.addRow(new Object[] { col1, col2 });

        }

    }

    /**
     * Searches for documents using the last query clicked by the user.
     */
    private void searchQuery() {

        PresentationController pc = PresentationController.get_instance();
        ArrayList<ArrayList<String>> res = pc.getDocumentsByBooleanExpression(lastQueryClicked);
        if (res != null) {
            ResultView rv = new ResultView(res, mv);
            fr.dispatchEvent(new WindowEvent(fr, WindowEvent.WINDOW_CLOSING));
            mv.changePanel(rv.getPanel());
        }

    }

    /**
     * Deletes the last query clicked by the user.
     */
    private void deleteQuery() {

        String[] options = new String[] { "Yes", "No" };
        int response = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this query?", "Delete",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (response == 0) {
            PresentationController.get_instance().deleteQuery(lastNameClicked);
            model.removeRow(lastRowClicked);
        }

    }

    /**
     * Changes the name of the last query clicked by the user.
     */
    private void changeQueryName() {

        String newQuery = JOptionPane.showInputDialog("Enter the new query name");

        if (newQuery != null) {
            PresentationController.get_instance().renameQuery(lastNameClicked, newQuery);
            model.setValueAt(newQuery, lastRowClicked, 0);
        }

    }

    /**
     * Changes the query of the last query clicked by the user.
     *
     */
    private void changeQuery() {

        String newQuery = JOptionPane.showInputDialog("Enter the new query name");

        if (newQuery != null) {
            if (PresentationController.get_instance().modifyQuery(lastNameClicked, newQuery))
                model.setValueAt(newQuery, lastRowClicked, 1);
        }

    }

    /**
     * The table model for the saved queries.
     */
    private DefaultTableModel model;

    /**
     * The table for displaying the saved queries.
     */
    private JTable table;
    /**
     * The frame for displaying the saved queries view.
     */
    private JFrame fr;
    /**
     * The scroll pane for the table.
     */

    private JScrollPane pane;
    /**
     * The popup menu for interacting with the saved queries.
     */
    private JPopupMenu popupMenu;
    /**
     * The button for adding a new query.
     */
    private JButton add_but;
    /**
     * The text field for searching the saved queries.
     */
    private JTextField searcher;
    /**
     * The last query clicked by the user.
     */
    private String lastQueryClicked;
    /**
     * The last name clicked by the user.
     */
    private String lastNameClicked;
    /**
     * The main view.
     */
    private MainView mv;
    /**
     * The last row clicked by the user.
     */
    private int lastRowClicked;

}
