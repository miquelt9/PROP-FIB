package src.main.presentation.classes;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

/**
 * PrefixView is a class that displays the results of a prefix search
 * in a table. It also has a button that allows the user to go back
 * to the main view.
 *
 * @author Alex H
 */
public class PrefixView {
    private JTable table;
    private JPanel panel;
    private JButton home;
    private JScrollPane pane;
    private ImageIcon img_home;

    /**
     * Constructor for PrefixView. Creates the table with the search results,
     * the home button, and the panel that contains these components.
     * 
     * @param results The list of results to display in the table
     * @param mv      The main view to return to when the home button is clicked
     */
    public PrefixView(ArrayList<String> results, MainView mv) {
        Object[][] docs = new Object[results.size()][1];

        for (int i = 0; i < results.size(); ++i) {
            docs[i][0] = results.get(i);
        }

        String[] names = { "Authors" };

        table = new JTable(docs, names) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        img_home = new ImageIcon(getClass().getResource("home.png"));
        Image img = img_home.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        img_home = new ImageIcon(img);

        home = new JButton(img_home);
        home.setBounds(1920 / 2 - 100, 0, 125, 50);
        home.addActionListener(e -> mv.initializeMain());

        pane = new JScrollPane(table);
        pane.setBounds(0, 50, 1920, 1080);

        panel = new JPanel();
        panel.setLayout(null);

        panel.add(home);
        panel.add(pane);
    }

    /**
     * Returns the panel containing the table and home button.
     * 
     * @return The panel.
     */
    public JPanel getPanel() {
        return panel;
    }
}
