package src.main.persistence.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import src.main.persistence.classes.exceptions.*;

/**
 * This class represents the Controller in charge of the downloading and
 * uploading the .xml files.
 */
public class XMLController extends FileController {

    /**
     * Reference to the XMLController itself
     */
    private static XMLController singleInstance;

    /**
     * Singleton XMLController getter
     * @return Reference to the controller itself
     */
    public static XMLController getInstance() {
        if (singleInstance == null)
            singleInstance = new XMLController();
        return singleInstance;
    }
    /**
     * To download documents from the running program into XML format
     * In order for it to work, the extension must be XML and
     * the overwritting permission can be set. Normally will be 0, but can be 1 if
     * the user gives permission to overwrite a file (in the case that there exists
     * a file in the same folder with same name and extension).
     * 
     * 
     * This is the XML extension downloader.
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
            String ResultContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + '\n'
                    + "<documents>" + '\n'
                    + '\t' + "<document>" + '\n'
                    + '\t' + '\t' + "<title>" + title + "</title>" + '\n'
                    + '\t' + '\t' + "<author>" + author + "</author>" + '\n'
                    + '\t' + '\t' + "<content>" + content + "</content>" + '\n'
                    + '\t' + "</document>" + '\n'
                    + "</documents>";

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
        // Assuming that the path looks like: "/users/docs/myfile.xml"
        try {
            
            // Initiate the factory.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Process the XML Securely.
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // Parse XML file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Notice this document is from org.w3c.dom.
            Document doc = db.parse(new File(path));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("documents");
            
            for (int temp = 0; temp < 1; temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    // Get the document
                    Element element = (Element) node;

                    // Get author, title and content.
                    String author = element.getElementsByTagName("author").item(0).getTextContent();
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String content = element.getElementsByTagName("content").item(0).getTextContent();

                    ArrayList<String> document = new ArrayList<>(List.of(title, author, content));
                    documents.add(document);

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            
            if ((e.getClass().getName()).equals("ParserConfigurationException"))
                throw new UnrecognisableFormatting(extension);
            else
                e.printStackTrace();
        }
        if (documents.isEmpty())
            throw new UnrecognisableFormatting(extension);
        return documents.get(0);
    }
}
