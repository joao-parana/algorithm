package br.com.joaoparana.algorithm.avl.generic;

// AvlTree class
// 
// CONSTRUCTION: with no initializer
// 
// ******************  PUBLIC OPERATIONS  *********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************  ERRORS  ********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree. Note that all "matching" is based on the compareTo
 * method.
 * 
 * @author Mark Allen Weiss
 */

public class AvlTree<E extends Comparable<? super E>> {
	/**
	 * Construct the tree.
	 */
	public AvlTree() {
		root = null;
	}

	public void dump() {
		if (this.root != null) {
			dumpRecurse(this.root, 0);
		}
	}

	/** This exists for debugging only */
	int qtyToPrintLimit = 40;
	int printed = 0;

	private void dumpRecurse(AvlNode<E> p, int depth) {

		String indent = (depth == 0 ? "" : String.format("%" + (4 * depth) + "s", ""));
		if (p == null) {
			return;
		}
		if (p.left == null && p.right == null) {
			if (printed++ < qtyToPrintLimit) {
				System.out.println(p.height + "\t" + indent + p.element + ": " + p.element + " (is leaf)");
			}
		} else {
			if (printed++ < qtyToPrintLimit) {
				System.out.println(p.height + "\t" + indent + p.element + ": " + p.element);
			}
			dumpRecurse(p.left, depth + 1);
			dumpRecurse(p.right, depth + 1);
		}
	}

	int inserted = 0;

	/**
	 * Insert into the tree; duplicates are ignored.
	 * 
	 * @param x
	 *            the item to insert.
	 */
	public void insert(E x) {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree. Nothing is done if x is not found.
	 * 
	 * @param x
	 *            the item to remove.
	 */
	public void remove(E x) {
		root = remove(x, root);
	}

	/**
	 * Internal method to remove from a subtree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<E> remove(E x, AvlNode<E> t) {
		if (t == null)
			return t; // Item not found; do nothing

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = remove(x, t.left);
		else if (compareResult > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return balance(t);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item or null if empty.
	 */
	public E findMin() {
		if (isEmpty())
			throw new UnderflowException();
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item of null if empty.
	 */
	public E findMax() {
		if (isEmpty())
			throw new UnderflowException();
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return true if x is found.
	 */
	public boolean contains(E x) {
		return contains(x, root);
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	/**
	 * @return the rotateCounters
	 */
	public int[] getRotateCounters() {
		return rotateCounters;
	}

	private static final int ALLOWED_IMBALANCE = 1;

	// Assume t is either balanced or within one of being balanced
	private AvlNode<E> balance(AvlNode<E> t) {
		if (t == null)
			return t;

		if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
			if (height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild(t);
			else
				t = doubleWithLeftChild(t);
		else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE)
			if (height(t.right.right) >= height(t.right.left))
				t = rotateWithRightChild(t);
			else
				t = doubleWithRightChild(t);

		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}

	public void checkBalance() {
		checkBalance(root);
	}

	private int checkBalance(AvlNode<E> t) {
		if (t == null)
			return -1;

		if (t != null) {
			int hl = checkBalance(t.left);
			int hr = checkBalance(t.right);
			if (Math.abs(height(t.left) - height(t.right)) > 1 || height(t.left) != hl || height(t.right) != hr)
				System.out.println("OOPS!!");
		}

		return height(t);
	}

	/**
	 * Internal method to insert into a subtree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private AvlNode<E> insert(E x, AvlNode<E> t) {
		inserted++;
		if (inserted < qtyToPrintLimit) {
			System.out.println("rotateConters = [ " + this.getRotateCounters()[0] + ", " + this.getRotateCounters()[1]
					+ ", " + this.getRotateCounters()[2] + ", " + this.getRotateCounters()[3] + " ]");
		}
		if (t == null)
			return new AvlNode<>(x, null, null);

		int compareResult = x.compareTo(t.element);

		if (compareResult < 0)
			t.left = insert(x, t.left);
		else if (compareResult > 0)
			t.right = insert(x, t.right);
		else
			; // Duplicate; do nothing
		return balance(t);
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the smallest item.
	 */
	private AvlNode<E> findMin(AvlNode<E> t) {
		if (t == null)
			return t;

		while (t.left != null)
			t = t.left;
		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the largest item.
	 */
	private AvlNode<E> findMax(AvlNode<E> t) {
		if (t == null)
			return t;

		while (t.right != null)
			t = t.right;
		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the tree.
	 * @return true if x is found in subtree.
	 */
	private boolean contains(E x, AvlNode<E> t) {
		while (t != null) {
			int compareResult = x.compareTo(t.element);

			if (compareResult < 0)
				t = t.left;
			else if (compareResult > 0)
				t = t.right;
			else
				return true; // Match
		}

		return false; // No match
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 */
	private void printTree(AvlNode<E> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	/**
	 * Return the height of node t, or -1, if null.
	 */
	private int height(AvlNode<E> t) {
		return t == null ? -1 : t.height;
	}

	private int rotateCounters[] = { 0, 0, 0, 0 };

	/**
	 * Rotate binary tree node with left child. For AVL trees, this is a single
	 * rotation for case 1. Update heights, then return new root.
	 */
	private AvlNode<E> rotateWithLeftChild(AvlNode<E> k2) {
		rotateCounters[0]++;
		AvlNode<E> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child. For AVL trees, this is a single
	 * rotation for case 4. Update heights, then return new root.
	 */
	private AvlNode<E> rotateWithRightChild(AvlNode<E> k1) {
		rotateCounters[1]++;
		AvlNode<E> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.right), k1.height) + 1;
		return k2;
	}

	/**
	 * Double rotate binary tree node: first left child with its right child;
	 * then node k3 with new left child. For AVL trees, this is a double
	 * rotation for case 2. Update heights, then return new root.
	 */
	private AvlNode<E> doubleWithLeftChild(AvlNode<E> k3) {
		rotateCounters[2]++;
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	/**
	 * Double rotate binary tree node: first right child with its left child;
	 * then node k1 with new right child. For AVL trees, this is a double
	 * rotation for case 3. Update heights, then return new root.
	 */
	private AvlNode<E> doubleWithRightChild(AvlNode<E> k1) {
		rotateCounters[3]++;
		k1.right = rotateWithLeftChild(k1.right);
		return rotateWithRightChild(k1);
	}

	private static class AvlNode<E> {
		// Constructors
		// AvlNode(E theElement) {
		// this(theElement, null, null);
		// }

		AvlNode(E theElement, AvlNode<E> lt, AvlNode<E> rt) {
			element = theElement;
			left = lt;
			right = rt;
			height = 0;
		}

		E element; // The data in the node
		AvlNode<E> left; // Left child
		AvlNode<E> right; // Right child
		int height; // Height
	}

	/** The tree root. */
	private AvlNode<E> root;

	// Test program
	public static void main(String[] args) {
		AvlTree<Integer> t = new AvlTree<>();
		final int SMALL = 400;
		final int NUMS = 1000000; // must be even
		final int GAP = 37;
		final int FACTOR_1 = 7;
		final int FACTOR_2 = 13;

		System.out.println("One milion records. Checking... (no more output means success)");

		for (int i = GAP; i != 0; i = (i + GAP) % NUMS) {
			// System.out.println( "INSERT: " + i );
			if (i % FACTOR_2 != 0) {
				t.insert(i);
			}
			if (NUMS < SMALL)
				t.checkBalance();
		}

		for (int i = FACTOR_1; i < NUMS; i += FACTOR_1) {
			// System.out.println( "REMOVE: " + i );
			t.remove(i);
			if (NUMS < SMALL)
				t.checkBalance();
		}
		if (NUMS < SMALL)
			t.printTree();

		if (t.findMin() != 1 || t.findMax() != NUMS - 2)
			System.out.println("FindMin or FindMax error!");

		for (int i = 1; i < NUMS; i++) {
			if (!(i % FACTOR_1 == 0 || i % FACTOR_2 == 0)) {
				if (!t.contains(i)) {
					System.out.println("Find error1!");
				}
			}
		}
		// for (int i = 1; i < NUMS; i += 2) {
		// if (t.contains(i))
		// System.out.println("Find error2!");
		// }
		t.dump();
		System.out.println("rotateConters = [ " + t.getRotateCounters()[0] + ", " + t.getRotateCounters()[1] + ", "
				+ t.getRotateCounters()[2] + ", " + t.getRotateCounters()[3] + " ]");

	}
}
