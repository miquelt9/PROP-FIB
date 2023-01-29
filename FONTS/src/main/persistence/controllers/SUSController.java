package src.main.persistence.controllers;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.main.persistence.classes.exceptions.*;

public class SUSController extends FileController {

    /**
     * Reference to the SUSController singleton
     */
    private static SUSController singleInstance;

    /**
     * Singleton SUSController getter
     * 
     * @return Reference to the controller itself
     */
    public static SUSController getInstance() {
        if (singleInstance == null)
            singleInstance = new SUSController();
        return singleInstance;
    }

    /**
     * To download documents from the running program into SUS format
     * In order for it to work, the extension must be SUS and
     * the overwritting permission can be set. Normally will be 0, but can be 1 if
     * the user gives permission to overwrite a file (in the case that there exists
     * a file in the same folder with same name and extension).
     * 
     * 
     * This is the SUS extension downloader.
     * 
     * @param extension format of the document
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
    @Override
    public void downloadDocument(String extension, String path, String title, String author, String content, boolean ow)
            throws FileAlreadyExists, ForbiddenExtension {

        try {
            File fitxer = new File(path);
            if (ow) {
                if (fitxer.exists()) {
                    if (!fitxer.delete())
                        throw new IOException();
                    // DELETE AND RE-CREATE THE FILE
                }
            } else {
                if (!fitxer.createNewFile())
                    throw new FileAlreadyExists(path, title + author);
            }

            FileWriter filewriter = new FileWriter(fitxer);
            String ResultContent = "Title ==> " + secretEncryptationSHHH(title) + '\n' + "Author ==> "
                    + secretEncryptationSHHH(author) + '\n' + secretEncryptationSHHH(content);

            filewriter.write(ResultContent);
            filewriter.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

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
    @Override
    public ArrayList<String> uploadDocument(String extension, String path, boolean ow)
            throws ForbiddenExtension, UnrecognisableFormatting, FileDoesntExists {
                
        File f = new File(path);
        if (f.exists() && f.isDirectory())
            throw new FileDoesntExists(path);

        ArrayList<ArrayList<String>> documents = new ArrayList<>();
        // Assuming that the path looks like: "/users/docs/myfile.sus";
        try {
            Scanner scanner = new Scanner(new File(path));


            if (!scanner.hasNextLine())
                throw new UnrecognisableFormatting(extension);
                
            String nl = scanner.nextLine();
            if (!(nl.substring(0, 10).equals("Title ==> "))) {
                throw new UnrecognisableFormatting(extension);
            }
            String title = secretDesencryptationSHHH(nl.substring(10));

            if (!scanner.hasNextLine()) {
                throw new UnrecognisableFormatting(extension);
            }
            
            nl = scanner.nextLine();
            if (!((nl).substring(0, 11).equals("Author ==> "))) {
                throw new UnrecognisableFormatting(extension);
            }
            String author = secretDesencryptationSHHH(scanner.nextLine().substring(11));

            String content = "";
            while (scanner.hasNextLine()) {
                content += secretDesencryptationSHHH(scanner.nextLine()) + '\n';
            }

            ArrayList<String> document = new ArrayList<>(List.of(title, author, content));
            documents.add(document);

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (documents.isEmpty())
            throw new UnrecognisableFormatting(extension);
        return documents.get(0);
    }

    /**
     * Process to encrypt a text back to plain text using meta quantum physics
     * encryptation method with probabilistic algorithms and machine learnign
     * heuristics
     * 
     * @param input The text to be encrypted
     * @return encrypted text
     */
    private String secretEncryptationSHHH(String input) {
        int shift = 5; // 5 modulo 26
        String result = "";
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (Character.isUpperCase(c))
                c = (char) (((int) c + shift - 65) % 26 + 65);
            else if (Character.isLowerCase(c))
                c = (char) (((int) c + shift - 97) % 26 + 97);

            result = result + c;
        }
        return result;
    }

    /**
     * Process to desencrypt a text back to plain text using meta quantum physics
     * encryptation method with probabilistic algorithms and machine learnign
     * heuristics
     * 
     * @param input The text to be desencrypted
     * @return desencrypted text
     */
    private String secretDesencryptationSHHH(String input) {
        int shift = 21; // actually -5 modulo 26
        String result = "";
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (Character.isUpperCase(c))
                c = (char) (((int) c + shift - 65) % 26 + 65);
            else if (Character.isLowerCase(c))
                c = (char) (((int) c + shift - 97) % 26 + 97);

            result = result + c;
        }
        return result;
    }

}
