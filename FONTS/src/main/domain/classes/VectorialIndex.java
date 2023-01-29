package src.main.domain.classes;

import src.main.domain.classes.exceptions.notEnoughDocuments;
import src.main.domain.classes.exceptions.notFoundDocument;
import src.main.domain.controllers.DocumentController;

//import src.test.domain.classes.Stubs_Mocks.Document;
import java.util.*;
import java.io.Serializable;
import java.lang.Math;

/**
 * This class represents a comparator for document objects.
 *
 * @author Alex H
 */
class comp implements Comparator<Document>, Serializable {

    /**
     * Compares two Document objects based on their title and author.
     *
     * @param documentA the first Document to compare
     * @param documentB the second Document to compare
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second
     */
    @Override
    public int compare(Document documentA, Document documentB) {

        int compr = documentA.getTitle().compareTo(documentB.getTitle());

        if (compr > 0)
            return 1;
        if (compr < 0)
            return -1;

        compr = documentA.getAuthor().compareTo(documentB.getAuthor());

        if (compr > 0)
            return 1;
        if (compr < 0)
            return -1;
        else
            return 0;
    }
}

/**
 * This class represents a pair of a Document object and a Double value.
 *
 * @version 1.0
 */
class MyPair {

    /**
     * A private field that stores the Document object.
     */
    private Document document;

    /**
     * A private field that stores the Double value.
     */
    private Double cos;

    /**
     * A constructor that creates a new MyPair with the given Document object and
     * Double value.
     *
     * @param document the Document object
     * @param cos      the Double value
     */
    public MyPair(Document document, Double cos) {
        this.document = document;
        this.cos = cos;
    }

    /**
     * A getter method that returns the Document object.
     *
     * @return the Document object
     */
    public Document first() {
        return document;
    }

    /**
     * A getter method that returns the Double value.
     *
     * @return the Double value
     */
    public Double second() {
        return cos;
    }

}

/**
 * This class represents a Vectorial Index.
 */
public class VectorialIndex implements Serializable {

    private static VectorialIndex single_instance;
    private TreeSet<Document> documentTreeSet;

    /**
     * Default constructor.
     */
    private VectorialIndex() {
        documentTreeSet = new TreeSet<Document>(new comp());
    }

    /**
     * Get a single instance of the vectorial index.
     * 
     * @return the single instance of the VectorialIndex
     */
    public static VectorialIndex get_instance() {
        if (single_instance == null)
            single_instance = new VectorialIndex();
        return single_instance;
    }

    /**
     * Sets the single instance to "this"
     */
    public void set_instance() {
        single_instance = this;
    }

    /**
     * Insert a document into the document tree set.
     * 
     * @param document Document to be inserted into the Vectorial Index.
     */
    public void InsertDocument(Document document) {
        documentTreeSet.add(document);
    }

    /**
     * Erase a document from the document tree set.
     * 
     * @param document Document to be erased from the Vectorial Index.
     * @throws notFoundDocument If the document doesn't exist.
     */
    public void EraseDocument(Document document) throws notFoundDocument {
        if (!documentTreeSet.remove(document))
            throw new notFoundDocument(document.getTitle(), document.getAuthor());
    }

    /**
     * Returns the document tree set.
     * 
     * @return A TreeSet of document from the Vectorial Index.
     */
    public TreeSet<Document> getAllDocs() {
        return documentTreeSet;
    }

    /**
     * Returns the number of documents in the document tree set.
     * 
     * @return The number of documents in the Vectorial Index.
     */
    public int numDocs() {
        return documentTreeSet.size();
    }

    /**
     * Returns a list up to the k-th most similar document.
     * 
     * @param document Document from which the similar documents will be searched
     *                 from.
     * @param k        Number of the most similar documents to be returned.
     * @return An ArrayList of the k-th most similar documents.
     * @throws notEnoughDocuments If the are less than k similar documents.
     */
    public ArrayList<Document> getKSimilar(Document document, int k) throws notEnoughDocuments {
        if (k > documentTreeSet.size())
            throw new notEnoughDocuments(documentTreeSet.size());

        PriorityQueue<MyPair> pq = new PriorityQueue<MyPair>(documentTreeSet.size(),
                (x, y) -> ((x.second() <= y.second()) ? 1 : -1));

        DocumentController.getInstance().setContent(document);
        TreeMap<String, Double> documentWeight = document.getWeightVector(documentTreeSet.size());

        for (Document potentialDocument : documentTreeSet) {

            DocumentController.getInstance().setContent(potentialDocument);

            TreeMap<String, Double> map1 = new TreeMap<String, Double>(documentWeight);
            TreeMap<String, Double> map2 = potentialDocument.getWeightVector(documentTreeSet.size());

            map1.keySet().retainAll(map2.keySet());
            map2.keySet().retainAll(map1.keySet());

            ArrayList<Double> v1 = new ArrayList<Double>(map1.values());
            ArrayList<Double> v2 = new ArrayList<Double>(map2.values());

            normalize(v1);
            normalize(v2);
            double cos_similarity = scalarProduct(v1, v2);
            pq.add(new MyPair(potentialDocument, cos_similarity));
            potentialDocument.setContent("");
        }

        ArrayList<Document> vd = new ArrayList<Document>();

        for (int i = 0; i < k; ++i)
            vd.add(pq.poll().first());

        document.setContent("");

        return vd;
    }

    /**
     * Returns a list up to the k-th most relevant document.
     * 
     * @param query Query to be use to find the docuemnts.
     * @param k     Number of the most similar documents to be returned.
     * @return An ArrayList of the k-th most relevant documents.
     * @throws notEnoughDocuments If the are less than k similar documents.
     */
    public ArrayList<Document> getKRelevant(String query, int k) throws notEnoughDocuments {
        if (k > documentTreeSet.size())
            throw new notEnoughDocuments(documentTreeSet.size());

        String[] splt = query.split(" ");
        query = query.toLowerCase();

        TreeMap<String, Double> qWeight = new TreeMap<String, Double>();

        for (String s : splt) {

            if (!qWeight.containsKey(s))
                qWeight.put(s, 1.0);
            else {

                Double x = qWeight.get(s);
                ++x;
            }
        }

        for (Double document : qWeight.values()) {
            document = 1.0 + Math.log(document);
        }

        PriorityQueue<MyPair> pq = new PriorityQueue<MyPair>(documentTreeSet.size(),
                (x, y) -> ((x.second() <= y.second()) ? 1 : -1));

        for (Document potentialDocument : documentTreeSet) {
            DocumentController.getInstance().setContent(potentialDocument);
            TreeMap<String, Double> map2 = potentialDocument.getWeightVector2();
            TreeMap<String, Double> map1 = new TreeMap<String, Double>(qWeight);
            map1.keySet().retainAll(map2.keySet());
            map2.keySet().retainAll(map1.keySet());
            ArrayList<Double> v1 = new ArrayList<Double>(map1.values());
            ArrayList<Double> v2 = new ArrayList<Double>(map2.values());

            normalize(v1);
            normalize(v2);
            double cos_similarity = scalarProduct(v1, v2);
            pq.add(new MyPair(potentialDocument, cos_similarity));
            potentialDocument.setContent("");
            ;
        }

        ArrayList<Document> vd = new ArrayList<Document>();
        for (int i = 0; i < k; ++i)
            vd.add(pq.poll().first());
        return vd;
    }

    /**
     * Returns the scalar product of two list of doubles.
     */
    private double scalarProduct(ArrayList<Double> v, ArrayList<Double> v2) {
        int n = v.size();
        int m = v2.size();

        int length = (n < m) ? n : m;

        double scalar = 0;

        for (int i = 0; i < length; ++i)
            scalar += v.get(i) * v2.get(i);

        return scalar;
    }

    /**
     * Normalizes the given list of doubles.
     */
    private void normalize(ArrayList<Double> v) {

        double modul = Math.sqrt(scalarProduct(v, v));

        for (int i = 0; i < v.size(); ++i) {
            Double document = v.get(i);
            document = document * 1.0 / modul;
        }
    }
}
