package src.main.domain.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

//import src.main.domain.classes.Document;
//import src.test.domain.classes.Stubs_Mocks.Document;
//import src.test.domain.classes.Stubs_Mocks.InvertedIndex;
import src.main.domain.classes.exceptions.queryNotCorrect;

/**
 * This class represents the Query for the queries search.
 * 
 * @author Lluc Clavera
 */
public class Query implements Serializable {

	public enum type {
		OR,
		AND,
		NOT,
		LEAF
	}

	private String original;
	private String adapted;
	private String name;
	private QueryNode root;

	/**
	 * Default constructor.
	 */
	public Query() {
	}

	/**
	 * Copier constructor.
	 * 
	 * @param query Query to be copied into the new one.
	 */
	public Query(Query query) {
		root = query.root;
		original = query.original;
		name = query.name;
	}

	/**
	 * Constructor by string.
	 * 
	 * @param query Query in a string format to initialize the query object.
	 * @throws queryNotCorrect If the query structure is not correct.
	 */
	public Query(String query) throws queryNotCorrect {
		original = query;
		adapted = supressBrackets(original);
		root = constructTree(adapted);
		this.name = null;
	}

	/**
	 * Creates a query from a string and assigns it a name
	 * 
	 * @param name  To be assigned to identify the query itself easily.
	 * @param query Query in a string format to initialize the query object.
	 * @throws queryNotCorrect If the query structure is not correct.
	 */
	public Query(String name, String query) throws queryNotCorrect {
		original = query;
		adapted = supressBrackets(original);
		root = constructTree(adapted);
		this.name = name;

	}

	/**
	 * Get the string of a query.
	 * 
	 * @return The query in a string format.
	 */
	public String getQuery() {
		return original;
	}

	/**
	 * Get the name of a query.
	 * 
	 * @return The name of the query.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of a query.
	 * 
	 * @param name New name to identify the query.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the root of the tree of the expression.
	 * 
	 * @return The node of the query.
	 */
	public QueryNode getRoot() {
		return root;
	}

	/**
	 * Modify a query with a new one defined by a string.
	 * 
	 * @param query New query in a String format.
	 * @throws queryNotCorrect If the query structure is not correct.
	 */
	public void modifyQuery(String query) throws queryNotCorrect {
		original = query;
		adapted = supressBrackets(query);
		root = constructTree(adapted);
	}

	/**
	 * Chage all the expressions shaped like {p1 p2 p3} for (p1 & p2 & p3).
	 * 
	 * @param input String to be treated.
	 * @return String without brackets.
	 * @throws queryNotCorrect If the query structure is not correct.
	 */
	public String supressBrackets(String input) throws queryNotCorrect {
		int first = -1; // First { index
		String ret = input;
		int i = 0; // Current indec
		while (i < ret.length()) {
			if (ret.charAt(i) == '{') {
				if (first != -1)
					throw new queryNotCorrect();
				first = i;
			} else if (ret.charAt(i) == '}') {
				if (first == -1)
					throw new queryNotCorrect();
				else {
					String aux = ret.substring(first, i + 1);
					aux = aux.replace(" ", " & ");
					i = first;
					while (ret.charAt(i) != '}')
						++i;
					aux = aux.replace("{", "(");
					aux = aux.replace("}", ")");
					ret = ret.substring(0, first) + aux + ret.substring(i + 1, ret.length());
					first = -1;
				}
			}
			++i;
		}
		if (first != -1)
			throw new queryNotCorrect();
		return ret;
	}

	/**
	 * Returns the list of documents that satisfies the query.
	 * 
	 * @return An ArrayList of the documents that satifies the query.
	 */
	public ArrayList<Document> search() {
		if (root == null)
			return new ArrayList<Document>();
		HashMap<Document, HashSet<Integer>> search = root.search();
		ArrayList<Document> result = new ArrayList<Document>();
		result.addAll(search.keySet());
		if (root.negated) {
			ArrayList<Document> aux = new ArrayList<Document>(VectorialIndex.get_instance().getAllDocs());
			ArrayList<Document> toRemove = new ArrayList<Document>();
			for (Document d : aux) {
				if (search.containsKey(d) && d.getNumLines() <= search.get(d).size()) {
					toRemove.remove(d);
				}
			}
			aux.removeAll(toRemove);
			return aux;
		}
		return result;
	}

	/**
	 * Constructs and returns a tree that represents the query given as a string.
	 * The tree is made to simplify searches.
	 * 
	 * @param s String from which the tree will be build.
	 * @return The root node of the tree.
	 * @throws queryNotCorrect If the query structure is not correct.
	 */
	private QueryNode constructTree(String s) throws queryNotCorrect {
		int len = s.length();
		int depth = 0;
		QueryNode act;
		boolean parenth = false;
		int ands = -1;
		int nots = -1;
		for (int i = 0; i < len; ++i) {
			if (s.charAt(i) == '(') {
				++depth;
				parenth = true;
			} else if (s.charAt(i) == ')') {
				--depth;
				if (depth < 0)
					throw new queryNotCorrect();

			} else if (depth == 0 && s.charAt(i) == '!' && nots == -1) {
				nots = i;
			}

			else if (depth == 0 && s.charAt(i) == '&' && ands == -1) {
				ands = i;
			}

			else if (depth == 0 && s.charAt(i) == '|') {
				act = new OrNode();
				String lft = s.substring(0, i);
				String rgt = s.substring(i + 1, len);
				act.addChildren(constructTree(lft), constructTree(rgt));
				if (act.getLeft() == null || act.getRight() == null)
					throw new queryNotCorrect();
				return act;
			}
		}

		if (ands != -1) {
			act = new AndNode();
			String lft = s.substring(0, ands);
			String rgt = s.substring(ands + 1, len);
			act.addChildren(constructTree(lft), constructTree(rgt));
			if (act.getLeft() == null || act.getRight() == null)
				throw new queryNotCorrect();
			return act;
		}

		if (nots != -1) {
			act = new NotNode();
			String aux = s.substring(nots + 1, len);
			act.addLeft(constructTree(aux));
			if (act.getLeft() == null)
				throw new queryNotCorrect();
			return act;
		}

		if (depth != 0)
			return null;
		else if (parenth) {
			String aux = s.trim();
			if (!(aux.charAt(0) == '('))
				throw new queryNotCorrect();
			if (!(aux.charAt(aux.length() - 1) == ')'))
				throw new queryNotCorrect();
			return constructTree(aux.substring(1, aux.length() - 1));
		} else {
			String aux = s.trim();
			if (aux.split(" ").length > 1 && (aux.charAt(0) != '"' || aux.charAt(aux.length() - 1) != '"')) {
				throw new queryNotCorrect();
			}
			if (aux.length() == 0)
				throw new queryNotCorrect();

			act = new LeafNode(s.trim());
			return act;
		}
	}
}
