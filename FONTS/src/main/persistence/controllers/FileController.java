package src.main.persistence.controllers;

import java.util.ArrayList;
import src.main.persistence.classes.exceptions.FileDoesntExists;
import src.main.persistence.classes.exceptions.FileAlreadyExists;
import src.main.persistence.classes.exceptions.ForbiddenExtension;
import src.main.persistence.classes.exceptions.UnrecognisableFormatting;

/**
 * This class has the main purpose of carrying out all the import and export
 * funcionalities that this project offers, just to help the Domain Controller
 * in difficult tasks. Made to reduce the code in the Domain Controller and to
 * boost readibility.
 * 
 * @author Walter J.T.V
 */
public class FileController {

    /**
     * Constructor of the FileController.
     */
    protected FileController() {

    }

    /**
     * To download documents from the running program into a file in any of the
     * allowed file extensions.
     * In order for it to work, the extension must be supported (xml, txt, sus) and
     * the overwritting permission can be set. Normally will be 0, but can be 1 if
     * the user gives permission to overwrite a file (in the case that there exists
     * a file in the same folder with same name and extension).
     * 
     * 
     * This is the abstract hook function that is overriden in every subclass of
     * FileController
     * 
     * @param extension Desired format of the document.
     * @param content   Content of the Document to be saved.
     * @param path      Path where the file will be saved.
     * @param title     Title of the Document to be saved.
     * @param author    Author of the Document to be saved.
     * @param ow        Permission bit with value 0 if overwrite can't be done, 1
     *                  otherwise.
     * @throws FileAlreadyExists  If the file already exists, then this exception is
     *                            thrown in order to avoid deleting it without.
     *                            asking the user.
     * @throws ForbiddenExtension If the file extension chosen is not supported
     *                            then.
     *                            this exception is thrown to stop the execution.
     */
    public void downloadDocument(String extension, String path, String title, String author, String content, boolean ow)
            throws FileAlreadyExists, ForbiddenExtension {

    }

    // @throws DocumentAlreadyExists If the overwritte permission is 0, then if the
    // Document with same title and author as the one given, this exception is
    // thrown, preventing its execution
    // OJO QUE NO CREA RES, CAL QUE EL DOMINI O EL CONTROLADOR DE PERSISTENCIA !!

    /**
     * Checks if the file on path, can be uploaded (even if it overwrites another
     * document), considering that the extension type must be supported and the
     * format must be correct.
     * 
     * @param extension File extension of the uploaded file.
     * @param path      Path where the file to be uploaded is located.
     * @param ow        permission bit with value 0 if overwrite can't be done, 1
     *                  otherwise.
     * 
     * @throws ForbiddenExtension       If the extension of the file uploaded is not
     *                                  one
     *                                  of the allowed ones.
     * 
     * @throws UnrecognisableFormatting
     * 
     */
    public ArrayList<String> uploadDocument(String extension, String path, boolean ow)
            throws ForbiddenExtension, UnrecognisableFormatting, FileDoesntExists {

        return null;
    }
}
