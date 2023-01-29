package src.main.presentation.classes;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import src.main.presentation.controllers.*;

/**
 * Abstract class representing a dialog box in the user interface.
 * 
 * @author Alex H.
 */
abstract class AbsDialog {

    /**
     * Initializes a new dialog box with the given parameters.
     *
     * @param fr          The parent frame of the dialog box.
     * @param lbl_name    An array of strings representing the labels for the text
     *                    fields.
     * @param num_buttons The number of text fields in the dialog box.
     * @param opt_names   An array of strings representing the default values for
     *                    the text fields (it can be null if you don't want to put
     *                    anything).
     * @param but_name    The name of the button in the dialog box.
     * @param dialog_name The title of the dialog box.
     */
    public void initialize(JFrame fr, String[] lbl_name, int num_buttons, String[] opt_names, String but_name,
            String dialog_name) {

        this.pc = PresentationController.get_instance();
        this.num_buttons = num_buttons;

        lbl = new JLabel[num_buttons];

        img_mog = new ImageIcon(getClass().getResource("mog.png"));
        Image img = img_mog.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        lbl_mog = new JLabel();
        lbl_mog.setBounds(575, 50, 50, 50);
        lbl_mog.setIcon(new ImageIcon(img));
		
        dg = new JDialog(fr, dialog_name , true);
		dg.getContentPane().setBackground(new Color(54,57,63));
        dg.setResizable(false);
        dg.setSize(720, 200);

        txt = new JTextField[num_buttons];

        // Variable for put the positions of the TextBoxs
        int h = (num_buttons == 1) ? 200 : 200 / (num_buttons + 1);

        // loop across all the text boxes
        for (int i = 0; i < num_buttons; ++i) {

            txt[i] = opt_names == null || opt_names[i] == null ? new JTextField() : new JTextField(opt_names[i]);
            txt[i].setBounds(250, h / 3 + i * h, 300, 25);

            dg.add(txt[i]);

            lbl[i] = new JLabel("Introduce the " + lbl_name[i]);
            lbl[i].setBounds(25,h/3 + i*h,300,25);
			lbl[i].setForeground(Color.WHITE);
            lbl[i].setFont(new Font("Arial", Font.PLAIN, 14));

            dg.add(lbl[i]);

        }

        but_search = new JButton(but_name);
        but_search.setBounds(590, 120, 100, 35);
        but_search.addActionListener(e -> setAction());
        txt[num_buttons - 1].addActionListener(e -> setAction());

        dg.setLayout(null);
        dg.add(lbl_mog);
        dg.add(but_search);
        dg.setLocationRelativeTo(null);
        dg.setVisible(true);
    }

    /**
     * Closes the dialog box.
     */
    public void close() {
        dg.dispose();
    }

    /**
     * Returns an array of strings containing the text entered in the text fields of
     * the dialog box.
     *
     * @return an array of strings containing the text entered in the text fields of
     *         the dialog box.
     */
    public String[] getTextFromDialog() {
        String[] txt_dialog = new String[num_buttons];

        for (int i = 0; i < num_buttons; ++i)
            txt_dialog[i] = txt[i].getText();

        return txt_dialog;
    }

    /**
     * Abstract method to be implemented in subclasses to specify the action to be
     * performed when the button in the
     * dialog box is clicked.
     */
    public abstract void setAction();

    // Declare private variables for the dialog box, labels, button, text fields,
    // and number of buttons.
    private JDialog dg;
    private JLabel[] lbl;
    private JLabel lbl_mog;
    private JButton but_search;
    private JTextField[] txt;
    private ImageIcon img_mog;
    private int num_buttons;
    // Declare protected variables for the presentation controller and main view
    protected PresentationController pc;
    protected MainView mv;
}