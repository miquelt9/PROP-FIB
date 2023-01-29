package src.main.presentation.classes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;
import java.util.ArrayList;
import src.main.presentation.controllers.*;

/**
 * Class for display the results.
 *
 * @author Alex H (mostly)
 * @author Lluc Clavera (a few additions)
 */
public class ResultView {

    /**
     * Constructor for the ResultView
     *
     * @param results the results that is going to be displayed
     * @param mv      the view witch is going to be displayed
     *
     */

    public ResultView(ArrayList<ArrayList<String>> results, MainView mv) {

        this.mv = mv;

        initialize(mv, results);

    }

    /**
     * Initialize the components from the result view
     *
     * @param mv      same explained in the constructor
     * @param results same explained in the constructor
     *
     */
    private void initialize(MainView mv, ArrayList<ArrayList<String>> results) {

        this.results = results;

        Object[][] docs = new Object[results.size()][2];

        for (int i = 0; i < results.size(); ++i) {

            docs[i][0] = results.get(i).get(0);
            docs[i][1] = results.get(i).get(1);

        }

        String[] names = { "Title", "Author" };

        model = new DefaultTableModel(docs, names);

        table = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        popupMenu = new JPopupMenu();
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem author = new JMenuItem("Search Author");
        JMenuItem KSimilar = new JMenuItem("Search by Similarity");
        JMenuItem Open = new JMenuItem("Open");
        // JMenuItem properties = new JMenuItem("Properties");
        JMenuItem download = new JMenuItem("Download");

        popupMenu.add(Open);
        popupMenu.add(delete);
        popupMenu.add(author);
        popupMenu.add(KSimilar);
        // popupMenu.add(properties);
        popupMenu.add(download);

        Open.addActionListener(e -> openDocument());
        delete.addActionListener(e -> deleteDocument());
        author.addActionListener(e -> new AuthSearchDialog(mv, new String[] { lastAuthorClicked }));
        KSimilar.addActionListener(
                e -> new KSimilarSearchDialog(mv, new String[] { lastTitleClicked, lastAuthorClicked, null }));
        download.addActionListener(e -> downloadDocument());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    int row = table.rowAtPoint(e.getPoint());
                    Object author = table.getValueAt(row, 1);
                    Object title = table.getValueAt(row, 0);
                    lastAuthorClicked = String.valueOf(author);
                    lastTitleClicked = String.valueOf(title);
                    lastRowClicked = row;

                }
            }
        });

        String[] s = { "Author Up", "Author Down", "Title Up", "Title Down", "Number of lines Up",
                "Number of lines Down", "Number of words Up", "Number of words Down", "Acces Date Up",
                "Acces Date Down", "Creation Date Up", "Creation Date Down", "Modified Date Up", "Modified Date Down" };
        sort_opt = new JComboBox<String>(s);
        sort_opt.addActionListener(e -> setSortingResult());

        img_home = new ImageIcon(getClass().getResource("home.png"));

        Image img = img_home.getImage();
        img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        img_home = new ImageIcon(img);

        home = new JButton(img_home);
        home.setBounds(1920 / 2 - 100, 0, 125, 50);
        home.addActionListener(e -> mv.initializeMain());

        lbl_sort = new JLabel("Sort options :");
	    lbl_sort.setForeground(Color.WHITE);
        lbl_sort.setBounds(1500,-10,1720-125,50);
        main_panel = new JPanel();
        main_panel.setLayout(null);
        pane = new JScrollPane(table);
        pane.setBounds(0, 50, 1920, 1080);
        sort_opt.setBounds(1600, 0, 245, 25);

        main_panel.add(pane);
        main_panel.add(lbl_sort);
        main_panel.add(sort_opt, BorderLayout.SOUTH);
        main_panel.add(home);

    }

    /**
     * Getter for the panel
     *
     * @return the panel
     *
     */

    public JPanel getPanel() {

        return main_panel;

    }

    /**
     * Action for delete the document
     */

    private void deleteDocument() {

        String[] options = new String[] { "Yes", "No" };
        int response = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this document?", "Delete",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (response == 0) {
            PresentationController.get_instance().deleteDocument(lastTitleClicked, lastAuthorClicked);
            model.removeRow(lastRowClicked);
        }

    }

    /**
     * Action for open a document
     */
    private void openDocument() {
        new ContentView(lastTitleClicked, lastAuthorClicked, mv, results);
    }

    /**
     * Action for download the document
     */
    private void downloadDocument() {
        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = fc.getSelectedFile().getAbsolutePath();
            PresentationController.get_instance().downloadDocument(lastTitleClicked, lastAuthorClicked, path);
        }
    }


    /**
     * Action for display the results sorted
     */
    private void setSortingResult() {

        String item_selected = String.valueOf(sort_opt.getSelectedItem());
        boolean asc = item_selected.charAt(item_selected.length() - 1) == 'p';
        item_selected = asc ? item_selected.substring(0, item_selected.length() - 3)
                : item_selected.substring(0, item_selected.length() - 5);

        switch (item_selected) {

            case "Author":
                results = PresentationController.get_instance().setOrderingCriteria(AUTHOR, asc);
                break;

            case "Title":
                results = PresentationController.get_instance().setOrderingCriteria(TITLE, asc);
                break;

            case "Number of lines":
                results = PresentationController.get_instance().setOrderingCriteria(NUMBER_OF_LINES, asc);
                break;

            case "Number of words":
                results = PresentationController.get_instance().setOrderingCriteria(NUMBER_OF_WORDS, asc);
                break;

            case "Acces Date":
                results = PresentationController.get_instance().setOrderingCriteria(ACCES_DATE, asc);
                break;

            case "Creation Date":
                results = PresentationController.get_instance().setOrderingCriteria(CREATION_DATE, asc);
                break;

            case "Modified Date":
                results = PresentationController.get_instance().setOrderingCriteria(MODIFIED_DATE, asc);
                break;

        }

        model.setRowCount(0);

        for (int i = 0; i < results.size(); ++i) {

            String col1 = results.get(i).get(0);
            String col2 = results.get(i).get(1);
            model.addRow(new Object[] { col1, col2 });

        }

    }

    // Macros for the configuration
    private String NUMBER_OF_WORDS = "NUM_WORDS";
    private String NUMBER_OF_LINES = "NUM_LINES";
    private String AUTHOR = "AUTHOR";
    private String TITLE = "TITLE";
    private String ACCES_DATE = "ACCES_DATE";
    private String CREATION_DATE = "CREATION_DATE";
    private String MODIFIED_DATE = "MODIFIED_DATE";

    private JTable table;
    private DefaultTableModel model;
    private JPanel main_panel;
    private JScrollPane pane;
    private JComboBox<String> sort_opt;
    private JButton home;
    private JLabel lbl_sort;
    private ImageIcon img_home;
    private JPopupMenu popupMenu;
    private String lastAuthorClicked;
    private String lastTitleClicked;
    private int lastRowClicked;
    private ArrayList<ArrayList<String>> results;
    private MainView mv;

}