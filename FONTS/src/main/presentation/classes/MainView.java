package src.main.presentation.classes;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;

import src.main.presentation.controllers.*;

/**
 * Main frame of the program.
 *
 * @author Alex H
 */

public class MainView {


    /**
    * Contruct a new Main View
    */
    public MainView() {
    
        fr = new JFrame("Falcon Explorer");
    
        ImageIcon icon = new ImageIcon(getClass().getResource("faclonExplorer.png"));
        Image image = icon.getImage();
        fr.setIconImage(image);
    
    
        initializeMain();
    
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        fr.setLocationRelativeTo(null);
        fr.setResizable(false);
        fr.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
    
    }
    
    /**
    * Getter for get the frame
    */
    public JFrame getFrame() {
    
        return fr;
    
    }
    
    /**
    * Initialize the components for the main view (the first that the user sees
    */
    public void initializeMain() {
    
            
        img_crash = new ImageIcon(getClass().getResource("talonflame.png"));
    
        lbl_crash = new JLabel();
    
        lbl_crash.setText("Welcome to Falcon Explorer!");
		lbl_crash.setForeground(Color.WHITE);
        lbl_crash.setIcon(img_crash);
        lbl_crash.setBounds(700,-10,1300,1000);
        lbl_crash.setFont(new Font("Arial", Font.PLAIN, 48));
        lbl_crash.setHorizontalTextPosition(JLabel.CENTER);
        lbl_crash.setVerticalTextPosition(JLabel.TOP);
    
        int x = 35;
        int y = -100;
        but_author = new JButton("Author search");
		but_author.setFocusable(false);
		but_author.setBackground(new Color(0xFF3632));
        but_author.setBounds(x, 200+y , 300, 50);
        but_author.addActionListener(e -> new AuthSearchDialog(this, null));
    
        but_pref = new JButton("Prefix search");
        but_pref.setBounds(x, 275+y, 300, 50);
		but_pref.setBackground(new Color(0xFF3632));
		but_pref.setFocusable(false);
        but_pref.addActionListener(e -> new PrefSearchDialog(this, null));
    
        but_query = new JButton("Query search");
        but_query.setBounds(x, 350+y, 300, 50);
		but_query.setBackground(new Color(0xFF3632));
		but_query.setFocusable(false);
        but_query.addActionListener(e -> new QuerySearchDialog(this, null));
    
        but_ksimilar = new JButton("Similitude search");
        but_ksimilar.setBounds(x, 425+y, 300, 50);
		but_ksimilar.setFocusable(false);
		but_ksimilar.setBackground(new Color(0xFF3632));
        but_ksimilar.addActionListener(e -> new KSimilarSearchDialog(this, null));
    
        but_krelevant= new JButton("Relevance search");
        but_krelevant.setBounds(x, 500+y, 300, 50);
		but_krelevant.setFocusable(false);
		but_krelevant.setBackground(new Color(0xFF3632));
        but_krelevant.addActionListener(e -> new KRelevantSearchDialog(this, null));
    
        but_doc= new JButton("Document search");
        but_doc.setBounds(x, 575+y, 300, 50);
		but_doc.setFocusable(false);
		but_doc.setBackground(new Color(0xFF3632));
        but_doc.addActionListener(e -> new DocumentSearchDialog(this, null));
    
        but_manage = new JButton("Your Queries");
        but_manage.setBounds(x, 650 + y, 300, 50);
		but_manage.setBackground(new Color(0xFF3632));
		but_manage.setFocusable(false);
        but_manage.addActionListener(e -> new SavedQueriesView(this));
    
        but_new = new JButton("New Document");
        but_new.setBounds(x, 925 + y, 300, 50);
		but_new.setBackground(new Color(0xFF3632));
		but_new.setFocusable(false);
        but_new.addActionListener(e -> new CreateDocumentDialog(this));
    
        but_upl = new JButton("Upload Document");
        but_upl.setBounds(x, 850 + y, 300, 50);
		but_upl.setBackground(new Color(0xFF3632));
		but_upl.setFocusable(false);
        but_upl.addActionListener(e -> uploadDocumentAction());


        lbl_search = new JLabel("Search Options");
		lbl_search.setFont(new Font("Serif", Font.PLAIN, 24));
		Dimension size = lbl_search.getPreferredSize();
        lbl_search.setBounds(100, 50, size.width , size.height);
		lbl_search.setForeground(Color.WHITE);
    
        lbl_doc= new JLabel("Document Options");
		lbl_doc.setFont(new Font("Serif", Font.PLAIN, 24));
        lbl_doc.setBounds(75, 700, size.width + 50 , size.height); 
		lbl_doc.setForeground(Color.WHITE);

        fr.getContentPane().removeAll();
    
        main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.add(but_author);
        main_panel.add(but_pref);
        main_panel.add(but_query);
        main_panel.add(but_ksimilar);
        main_panel.add(but_krelevant);
        main_panel.add(but_doc);
        main_panel.add(but_new);
        main_panel.add(but_upl);
        main_panel.add(but_manage);
        main_panel.add(lbl_crash);
        main_panel.add(lbl_search);
        main_panel.add(lbl_doc);
    
        fr.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                PresentationController.get_instance().saveAndClose();
            }
        });
        
        fr.setContentPane(main_panel);
		fr.getContentPane().setBackground(new Color(54,57,63));
        fr.revalidate();
        fr.repaint();

    }

	/**
	 * Change the panel from the main frame
	 *
	 * @param panel the panel witch the user want's to put for
	 *
	 */
    public void changePanel(JPanel panel) {
        fr.getContentPane().removeAll();
        fr.setContentPane(panel);
		fr.getContentPane().setBackground(new Color(54,57,63));
        fr.revalidate();
        fr.repaint();


    }


	
    /**
    * The code for upload a new document
    */
    public void uploadDocumentAction() {
        JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fc.setMultiSelectionEnabled(true);
        int returnVal = fc.showOpenDialog(null);
    
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            for (File file: files) {
                String path = file.getAbsolutePath();
                PresentationController.get_instance().uploadDocument(path);
            }
        }
    }
    
    
    
        private JFrame fr;
        private JLabel lbl_crash;
        private JLabel lbl_search;
        private JLabel lbl_doc;
        private ImageIcon img_crash;
        private JButton but_author;
        private JButton but_doc;
        private JButton but_pref;
        private JButton but_query;
        private JButton but_manage;
        private JButton but_ksimilar;
        private JButton but_krelevant;
        private JButton but_new;
        private JButton but_upl;
        private JPanel main_panel;
    
    
    }
