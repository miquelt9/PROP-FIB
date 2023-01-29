package src.main.persistence.classes.exceptions;

/**
 * Exception raised whenever the uploaded documents are not in the correct
 * format even though the extension is fine (sus,txt,xml), so its unreadable by
 * the parsing program
 * The format in all three extensions is:
 * -sus: Title ==> title
 * Author ==> author
 * content
 * -txt: title
 * author
 * content
 * -xml: <?xml version=\"1.0\" encoding=\"UTF-8\"?>
 * <document>
 * <title> ... </title>
 * <author>... </author>
 * <content>...</content>
 * </document>
 * 
 * There is no need to respect identation, a document could have many
 * <documents> </documents> roots , the first one found is the one actually used
 * thou.
 * 
 * @author Walter J.T.V
 */
public class UnrecognisableFormatting extends Exception {

    private static String ExtensionDetector(String extension) {
        String result;
        extension = extension.toLowerCase();
        if (extension == "xml")
            result = "The .xml file couldn't be uploaded due to bad formatting, try using the format: <?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + '\n' + "<documents>" + '\n' + "<document>" + '\n' + "<title> ... </title>" + '\n'
                    + "<author>... </author>" + '\n' + "<content>...</content>" + '\n' + "</document>" + '\n'
                    + "</documents>";
        else if (extension == "txt")
            result = "The .txt file couldn't be uploaded due to bad formatting, try using the format:" + '\n'
                    + "title..." + '\n' + "author..." + '\n' + "...content...";
        else if (extension == "sus")
            result = "The .sus file couldn't be uploaded due to bad formatting, try using the format:" + '\n'
                    + "Title ==> " + '\n' + "Author ==> " + '\n' + "...content..." + "\n\n"
                    + "Beware of both 2 whitespaces between the arrow, also keep in mind that using this format is useless if you don't grasp how this super encryptation system works";
        else
            result = "Unknown bad formatting error";
        return result;
    }

    public UnrecognisableFormatting(String extension) {
        super(ExtensionDetector(extension));
    }
}
