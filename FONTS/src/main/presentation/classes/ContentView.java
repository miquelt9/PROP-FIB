package src.main.presentation.classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import src.main.presentation.controllers.PresentationController;

/**
 * Dialog box for the content view.
 * 
 * @author Lluc Clavera
 */
public class ContentView {

    /**
     * Main constructor
     * 
     * @param title  Title of the document to view.
     * @param author Author of the document.
     * @param mv     Main view of the program.
     */
    public ContentView(String title, String author, MainView mv, ArrayList<ArrayList<String>> results) {
        this.title = title;
        this.author = author;
        this.mv = mv;
        this.lastResults = results;

        String content = PresentationController.get_instance().getDocument(title, author);

        // Create the frame
        panel = new JPanel();
        panel.setBounds(500, 500, 720, 490);

        // Create the text area and add it to a scroll pane
        textArea = new JTextArea(content);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create the button panel and add the edit button
        buttonPanel = new JPanel();
        but_edit = new JButton("Edit");
        but_exit = new JButton("exit");
        buttonPanel.add(but_edit);
        buttonPanel.add(but_exit);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add an action listener to the edit button
        but_edit.addActionListener(e -> editAction());
        but_exit.addActionListener(e -> exitAction());

        scrollPane.setBounds(450, 80, 1000, 720);
        buttonPanel.setBounds(620, 860, 300, 120);
        buttonPanel.setBackground(new Color(54,57,63));

        panel.setLayout(null);

        // Display the frame
        if (content != null) {
            mv.changePanel(this.panel);
        }
    }

    /**
     * Exit the view and go back to the main menu.
     */
    private void exitAction() {
        if (lastResults.size() > 0) {
            ResultView lastResult = new ResultView(lastResults, mv);
            mv.changePanel(lastResult.getPanel());
        }
        else mv.initializeMain();
    }

    /**
     * Opens the edit view to edit the document.
     */
    private void editAction() {
        new EditView(title, author, textArea.getText(), mv, lastResults);
    }

    private ArrayList<ArrayList<String>> lastResults;
    private JTextArea textArea;
    private String title;
    private MainView mv;
    private String author;
    private JPanel panel;
    private JPanel buttonPanel;
    private JButton but_edit;
    private JButton but_exit;
    private JScrollPane scrollPane;
}
