package src.main.presentation.classes;

import java.util.ArrayList;

import src.main.presentation.controllers.PresentationController;

/**
 * Dialog box for creating the document.
 * 
 * @author Alex H
 * @author Lluc Clavera
 */
public class CreateDocumentDialog extends AbsDialog {

    /**
     * Constructs a new CreateDocumentDialog.
     * 
     * @param mv the MainView associated with this dialog.
     */
    public CreateDocumentDialog(MainView mv) {

        this.mv = mv;
        String[] s = {"Title" , "Author"};
        initialize(mv.getFrame(), s , 2, null, "Create", "Creation Dialog");
    }

    /**
     * Sets the action to be performed when the "Create Dialog" button is clicked.
     * 
     * The action involves creating a new document with the specified title and
     * author,
     * and opening an EditView for the new document.
     */
    @Override
    public void setAction() {

        String[] s = getTextFromDialog();
        if (PresentationController.get_instance().createDocument(s[0].trim(), s[1].trim())) {
            new EditView(s[0], s[1], "", mv, new ArrayList<ArrayList<String>>());
            close();
        }
    }
}
