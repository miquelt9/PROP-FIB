package src.main.presentation.classes;

import java.awt.*;

import java.util.ArrayList;

import javax.swing.*;

import src.main.presentation.controllers.PresentationController;

/**
 * Dialog box for editing a document.
 * 
 * @author Lluc Clavera
 * 
 */

public class EditView {

  /**
   * 
   * Main constructor of the class
   * 
   * @param title   the title of the document to edit
   * 
   * @param author  author of the document
   * 
   * @param content content to edit
   * 
   * @param mv      MainView of the program
   * 
   */

  public EditView(String title, String author, String content, MainView mv, ArrayList<ArrayList<String>> results) {

    this.title = title;

    this.author = author;

    this.mv = mv;

    this.lastResults = results;

    // create the main window

    panel = new JPanel();

    // create the text area and add it to the main window

    textArea = new JTextArea(content);

    JScrollPane scrollPane = new JScrollPane(textArea);

    panel.add(scrollPane, BorderLayout.CENTER);

    // create the buttons and add them to the main window

    buttonPanel = new JPanel();

    panel.add(buttonPanel, BorderLayout.SOUTH);

    // add the buttons to the button panel

    saveButton = new JButton("Save");

    saveButton.addActionListener(e -> saveAction());

    saveButton.setMnemonic('S');

    buttonPanel.add(saveButton);

    exitButton = new JButton("Exit");

    exitButton.addActionListener(e -> exitAction());

    buttonPanel.add(exitButton);
	  buttonPanel.setBackground(new Color(54,57,63));

    scrollPane.setBounds(450, 80, 1000, 720);

    buttonPanel.setBounds(620, 860, 300, 120);

    panel.setLayout(null);

    if (content != null) {

      mv.changePanel(this.panel);

    }

  }

  /**
   * 
   * Saves the document
   * 
   */

  public void saveAction() {

    PresentationController.get_instance().modifyDocument(title, author, textArea.getText());

    JOptionPane.showMessageDialog(null, "The text has been saved", "Saved!", JOptionPane.PLAIN_MESSAGE);

  }

  /**
   * 
   * Exits edit view and goes back to the main menu
   * 
   */

  public void exitAction() {

    if (lastResults.size() > 0) {

      ResultView lastResult = new ResultView(lastResults, mv);

      mv.changePanel(lastResult.getPanel());

    }

    else
      mv.initializeMain();

  }

  private ArrayList<ArrayList<String>> lastResults;

  private String title;

  private MainView mv;

  private String author;

  private JPanel panel;

  private JTextArea textArea;

  private JPanel buttonPanel;

  private JButton saveButton;

  private JButton exitButton;

}
