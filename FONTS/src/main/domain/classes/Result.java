package src.main.domain.classes;

import src.main.domain.classes.exceptions.*;
import java.util.*;

/**
 * Comparator for using in a sort method from Java.
 */
class compr implements Comparator<Document> {

    @Override
    public int compare(Document a, Document b) {

        if (Configuration.get_instance().cmp(a, b))
            return 1;
        else
            return -1;
    }
}

/**
 * This class represents the Result of a search.
 */
public class Result {

    /**
     * Default constructor.
     */
    public Result() {
        documentList = new ArrayList<Document>();
    }

    /**
     * Update the document list with a document given its title and author.
     * 
     * @param Title  Title searched for.
     * @param Author Author searched for.
     * @throws notFoundDocument If the document is not found.
     * @throws notFoundAuthor   If the author of the document is not found.
     * @throws emptyTitle       If the title is empty
     * @throws emptyAuthor      If the author is empty
     */
    public void searchDocument(String Title, String Author)
            throws notFoundDocument, notFoundAuthor, emptyTitle, emptyAuthor {
        Document d = Trie.get_instance().getDoc(Title, Author);
        documentList.clear();
        documentList.add(d);
    }

    /**
     * Update the document list by searching of all the documents of a given author.
     * 
     * @param Author Author searched for.
     * @throws authorWithoutDocs If the given author doesn't have any documents.
     * @throws notFoundAuthor    If the author is not found.
     * @throws emptyAuthor       If the author is empty
     */
    public void searchDocuments(String Author) throws authorWithoutDocs, notFoundAuthor, emptyAuthor {
        ArrayList<Document> v = new ArrayList<Document>(Trie.get_instance().getDocs(Author));
        documentList = v;
        Collections.sort(documentList, new compr());
    }

    /**
     * Update the document list by searching the documents by relevance.
     * 
     * @param query Query which will be used for the search.
     * @param k     Indicated the number of documents to update the result list
     *              with.
     * @throws notEnoughDocuments If there's less than k documents that satisfies
     *                            the query.
     */
    public void searchByRelevance(String query, int k) throws notEnoughDocuments {
        documentList = VectorialIndex.get_instance().getKRelevant(query, k);
    }

    /**
     * Update the document list by searching up to the K-th most similar document.
     * 
     * @param doc Document from which similiars will be looked for.
     * @param k   Indicated the number of documents to update the result list with.
     * @throws notEnoughDocuments If there's less than k documents that satisfies
     *                            the query.
     */
    public void searchKSimilars(Document doc, int k) throws notEnoughDocuments {
        documentList = VectorialIndex.get_instance().getKSimilar(doc, k);
    }

    /**
     * Update the document list by searching by documents titles.
     * 
     * @param title Title which is searched for.
     * @throws emptyTitle If the title is empty.
     */
    public void searchByTitle(String title) throws emptyTitle {
        documentList = Trie.get_instance().getDocumentsByTitle(title);
        Collections.sort(documentList, new compr());
    }

    /**
     * Returns the document list.
     * 
     * @return An ArrayList of the documents (in a ArrayList of Strings (Set of
     *         sentences) format).
     */
    public ArrayList<ArrayList<String>> getResult() {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for (Document doc : documentList) {
            ArrayList<String> singleDoc = new ArrayList<String>();
            singleDoc.add(doc.getTitle());
            singleDoc.add(doc.getAuthor());
            result.add(singleDoc);
        }
        return result;
    }

    /**
     * Update the document list by searching by a given query in a string format.
     * 
     * @param query Query which will be used to look for the documents in a string
     *              format.
     * @throws queryNotCorrect If the query structure is not correct.
     */
    public void searchByQuery(String query) throws queryNotCorrect {
        Query q = new Query(query);
        documentList = q.search();
        Collections.sort(documentList, new compr());
    }

    /**
     * Update the document list by searching by a given query.
     * 
     * @param query Query which will be used to look for the documents.
     */
    public void searchByQuery(Query query) {
        documentList = query.search();
        Collections.sort(documentList, new compr());
    }

    /**
     * Sorts the document list and returns it
     * 
     * @return the new ordered list of documents
     */
    public ArrayList<ArrayList<String>> reSort() {
        Collections.sort(documentList, new compr());
        return getResult();
    }

    private ArrayList<Document> documentList;
}
