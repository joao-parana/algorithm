package br.com.joaoparana.algorithm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import dsaj.sorting.MergeSort;
import net.datastructures.LinkedPositionalList;
import net.datastructures.TreeMap;

// tentativa de resolver este problema https://www.damninteresting.com/the-birthday-paradox/

public class App {
	static int samplesQty = 20000;
	static int personsQty = 49;
	static int counter = 0;

	public static int countElements(Iterator<Double> iter) {

		if (iter.hasNext()) {
			iter.next();
			countElements(iter);
		}
		return counter++;
	}

	public static void doCaseOne(Order order) {
		MyRandom r = new MyRandom();
		MySearchTree st = new MySearchTree();
		st.insert("Lina", r.getRandom());
		st.insert("Ana", r.getRandom());
		st.insert("Lia", r.getRandom());
		st.insert("Ada", r.getRandom());
		st.insert("Lua", r.getRandom());
		st.insert("Sol", r.getRandom());
		st.insert("Cris", r.getRandom());
		st.insert("Bia", r.getRandom());
		st.insert("Rita", r.getRandom());
		st.insert("Mel", r.getRandom());
		st.insert("Rosa", r.getRandom());
		st.insert("Val", r.getRandom());
		System.out.println("••• DUMP");
		st.dump();
		System.out.println("••• inOrderTransverse");
		st.inOrderTransverse();
		System.out.println("••• preOrderTransverse");
		st.preOrderTransverse();
		System.out.println("••• postOrderTransverse");
		st.postOrderTransverse();
	}

	public static MySearchTree randomTree(int elemQty, long seed) {
		return randomTree(DisplayScope.DISPLAY_ALL, elemQty, seed);
	}

	public static MySearchTree randomTree(DisplayScope ds, int elemQty, long seed) {
		MyRandom r = new MyRandom();
		r.setSeed(seed);
		MySearchTree st = new MySearchTree();

		st.setOrder(Order.ASC);
		for (int i = 0; i < elemQty; i++) {
			Integer j = r.getRandom();
			// System.out.println("valor: " + j);
			st.insert(j.toString(), j);
		}
		System.out.println("••• DUMP");
		st.dump(ds);
		System.out.println("••• inOrderTransverse");
		st.inOrderTransverse();
		System.out.println("••• preOrderTransverse");
		st.preOrderTransverse();
		System.out.println("••• postOrderTransverse");
		st.postOrderTransverse();
		return st;
	}

	public static void main(String[] args) {
		Random r = new Random(11870);
		Comparator<String> comparator = null;
		String[] myArray = { "Ana", "Zuleika", "Maria", "Luana", "Yuri" };
		SortValues<String> processor = new SortValues<>(SortMethod.MergeSort, myArray, comparator);
		processor.sort();
		processor.dump();
		if (true) {
			return;
		}
		MySearchTree st = new MySearchTree();
		st.insert("43", 43);
		st.insert("70", 70);
		st.insert("23", 23);
		st.insert("20", 20);
		st.insert("56", 56);
		st.insert("26", 26);
		st.insert("47", 47);
		System.out.println("••• DUMP");
		st.dump();
		System.out.println("••• inOrderTransverse");
		st.inOrderTransverse();
		System.out.println("••• preOrderTransverse");
		st.preOrderTransverse();
		System.out.println("••• postOrderTransverse");
		st.postOrderTransverse();
		randomTree(10, 1380);
		randomTree(10, 4200);
		randomTree(10, 1810);
		randomTree(10, 1957);
		st = randomTree(DisplayScope.DISPLAY_ALL, 2000, 23257);
		System.out.println("Tree maxDepth: " + st.getMaxDepth());
		doCaseOne(Order.ASC);

		TreeMap<Integer, Integer> t = new TreeMap<>();
		t.put(5, 5);
		for (int i = 0; i < 7; i++) {
			int j = r.nextInt(10);
			System.out.println("valor: " + j);
			t.put(j, j);
		}
		t.dump();

		final int N = 1000000;
		int[] data = new int[N];

		for (int j = 0; j < N; j++) {
			data[j] = r.nextInt(N * 10);
		}
		Arrays.sort(data);
		float avg = 0;
		int operationsCounter = 0;
		int ITERACTIONS = 5;
		int SAMPLES = 0;
		float avgOperations = 0;
		float foundCounter = 0;
		for (int k = 0; k < ITERACTIONS; k++) {
			foundCounter = 0;
			avgOperations = 0;
			SAMPLES = 5 * N;
			for (int j = 0; j < 5 * N; j++) {
				int value = r.nextInt(N * 5);
				if (binarySearch(data, value)) {
					// Element Found
					avgOperations += qtdOperations;
					operationsCounter++;
					foundCounter++;
					// System.out.println("Value: " + (value));
				} else {
					// Element Not Found !
					avgOperations += qtdOperations;
					operationsCounter++;
				}
			}
			// System.out.println("counter: " + counter + " for (data.length): "
			// + (data.length));
			avgOperations = avgOperations / (SAMPLES);
			// System.out.println("avgOperations: " + avgOperations + " for
			// operationsCounter: " + operationsCounter);
			float percentage = (foundCounter / (SAMPLES) * 100);
			// System.out.println("Percentage of found elements: " + percentage
			// + " %");
			avg += percentage;
		}
		avg = avg / ITERACTIONS;
		System.out.println("Percentage of found elements: " + Math.round(avg) + " %");
		System.out.println(
				"average of qtdOperations is " + Math.round(avgOperations) + " for N " + N + " elements in array: ");
		System.out.println("foundCounter: " + Math.round(foundCounter) + " of operationsCounter: " + operationsCounter);

		LinkedPositionalList<Double> l = new LinkedPositionalList<>();
		l.addFirst(2.37);
		l.addFirst(3.14);
		l.addFirst(20.11);
		l.addFirst(3.27);
		l.addFirst(1.39);
		l.addFirst(7.61);
		Iterator<Double> iter = l.iterator();
		System.out.println(countElements(iter));
		System.out.println("Calculando a probabilidade com " + samplesQty + " pessoas");
		float total = 0;
		for (int i = 0; i < samplesQty; i++) {
			total += doIt();
		}

		System.out.println("com " + personsQty + " pessoas temos probabilidade -> " + (total / samplesQty));
	}

	public static float doIt() {

		float qtdMatch = 0;
		for (int i = 0; i < personsQty; i++) {
			if (checkMatch(i)) {
				qtdMatch++;
			}
		}
		return qtdMatch / personsQty;
	}

	static boolean checkMatch(int n) {
		Boolean[] match = new Boolean[365];
		// Inicializa o vetor
		for (int i = 0; i < match.length; i++) {
			match[i] = false;
		}

		for (int i = 0; i < n; i++) {
			int wd = new Random().nextInt(365);
			if (match[wd]) {
				return true;
			} else {
				match[wd] = true;
			}
		}

		return false;
	}

	/**
	 * Returns true if the target value is found in the indicated portion of the
	 * data array. This search only considers the array portion from data[low]
	 * to data[high] inclusive.
	 */
	public static boolean binarySearch(int[] data, int target, int low, int high) {
		qtdOperations++;
		if (low > high) {
			return false; // interval empty; no match
		} else {
			int mid = (low + high) / 2;
			if (target == data[mid]) {
				return true; // found a match
			} else if (target < data[mid]) {
				// recur left of the middle
				return binarySearch(data, target, low, mid - 1);
			} else {
				// recur right of the middle
				return binarySearch(data, target, mid + 1, high);
			}
		}
	}

	static int qtdOperations = 0;

	// Demonstration of a public wrapper function with cleaner signature
	/** Returns true if the target value is found in the data array. */
	public static boolean binarySearch(int[] data, int target) {
		// use parameterized version
		qtdOperations = 0;
		boolean ret = binarySearch(data, target, 0, data.length - 1);

		return ret;
	}

	/** Returns true if the target value is found in the data array. */
	public static boolean binarySearchIterative(int[] data, int target) {
		int low = 0;
		int high = data.length - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (target == data[mid]) // found a match
				return true;
			else if (target < data[mid])
				high = mid - 1; // only consider values left of mid
			else
				low = mid + 1; // only consider values right of mid
		}
		return false; // loop ended without success
	}

}

interface Tree {
	// isInternal(p): Returns true if node n has at least one child.
	boolean isInternal(MyNode n);

	// isExternal(p): Returns true if node n does not have any children.
	boolean isExternal(MyNode n);

	// isRoot(p): Returns true if node n is the root of the tree.
	boolean isRoot(MyNode n);

	// size():Returns the number of elements that are contained in the tree.
	int size();

	// isEmpty(): Returns true if the tree does not contain any elements
	boolean isEmpty();
}

class MySearchTree implements Tree {
	private MyNode root = null;
	private Order order = Order.ASC;
	private int size = 0;
	private int maxDepth = 0;

	/**
	 * @return the maxDepth
	 */
	public int getMaxDepth() {
		return this.maxDepth;
	}

	/**
	 * @param order
	 *            the Order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	public void insert(String key, Object value) {
		insert(this.order, key, value);
	}

	public void insert(Order order, String key, Object value) {
		if (this.root == null) {
			this.root = new MyNode(key, value);
			return;
		}
		insert(order, this.root, key, value);
	}

	private void insert(Order order, MyNode root, String key, Object value) {

		if (root == null) {
			throw new RuntimeException("Raiz da subarvore não pode ser nula");
		} else {
			boolean condition = false;
			if (order.equals(Order.ASC)) {
				condition = key.toString().compareTo(root.key) < 0;
			} else {
				condition = key.toString().compareTo(root.key) > 0;
			}
			if (condition) { // Depende da Ordem de classificação
				if (root.left == null) {
					root.left = new MyNode(key, value);
					// System.out.println("eu " + root.value + " left -> " +
					// value);
					size++;
					return;
				}
				insert(order, root.left, key, value);
			} else { // key >= root->key
				if (root.right == null) {
					root.right = new MyNode(key, value);
					// System.out.println("eu " + root.value + " rigth -> "
					// +
					// value);
					size++;
					return;
				}
				insert(order, root.right, key, value);
			}
		}
	}

	public void dump() {
		dump(DisplayScope.DISPLAY_ALL);
	}

	public void dump(DisplayScope ds) {
		if (this.root != null) {
			updateDepth();
			if (ds.equals(DisplayScope.DISPLAY_ALL) || ds.equals(DisplayScope.DISPLAY_DUMP)) {
				dumpRecurse(this.root, 0);
			}
			if (ds.equals(DisplayScope.DISPLAY_ALL) || ds.equals(DisplayScope.DISPLAY_MAX_DEPTH)) {
				System.out.println("Tree maxDepth: " + this.maxDepth);
			}
		}
	}

	/** This exists for debugging only */
	private void dumpRecurse(MyNode p, int depth) {
		String indent = (depth == 0 ? "" : String.format("%" + (4 * depth) + "s", ""));
		if (p == null) {
			return;
		}
		if (p.left == null && p.right == null) {
			System.out.println(p.depth + "\t" + indent + p.key + ": " + p.value + " (is leaf)");
		} else {
			System.out.println(p.depth + "\t" + indent + p.key + ": " + p.value);
			dumpRecurse(p.left, depth + 1);
			dumpRecurse(p.right, depth + 1);
		}
	}

	public void updateDepth() {
		if (this.root != null) {
			updateDepth(this.root, 0);
		}
	}

	private void updateDepth(MyNode p, int depth) {
		if (p == null) {
			return;
		}
		p.depth = depth;
		this.maxDepth = Math.max(this.maxDepth, depth);
		updateDepth(p.left, depth + 1);
		updateDepth(p.right, depth + 1);
	}

	public void preOrderTransverse() {
		preOrderTransverse(this.root);
		System.out.println("");
	}

	private void preOrderTransverse(MyNode p) {
		if (p == null) {
			return;
		}
		System.out.print(p.key + ": " + p.value + "  ");
		preOrderTransverse(p.left);
		preOrderTransverse(p.right);
	}

	public void inOrderTransverse() {
		inOrderTransverse(this.root);
		System.out.println("");
	}

	private void inOrderTransverse(MyNode p) {
		if (p == null) {
			return;
		}
		inOrderTransverse(p.left);
		System.out.print(p.key + ": " + p.value + "  ");
		inOrderTransverse(p.right);
	}

	public void postOrderTransverse() {
		postOrderTransverse(this.root);
		System.out.println("");
	}

	private void postOrderTransverse(MyNode p) {
		if (p == null) {
			return;
		}
		postOrderTransverse(p.left);
		postOrderTransverse(p.right);
		System.out.print(p.key + ": " + p.value + "  ");
	}

	@Override
	public boolean isInternal(MyNode n) {
		return n.left == null || n.right == null;
	}

	@Override
	public boolean isExternal(MyNode n) {
		return n.left == null && n.right == null;
	}

	@Override
	public boolean isRoot(MyNode n) {
		return this.root == n;
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

}

class MyRandom {
	Long seed = 1170L;
	Random r;

	public MyRandom() {
		this.r = new Random(this.seed);
	}

	public void setSeed(Long seed) {
		this.seed = seed;
		this.r = new Random(this.seed);
	}

	Integer getRandom() {
		// Valores randomicos entre 1001 e 9999
		return this.r.nextInt(8900) + 1001;
	}
}

class MyNode {
	public MyNode left;
	public MyNode right;
	int depth = 0;
	int height = 0;
	String key;
	Object value;

	public MyNode(String key, Object value) {
		this.key = key.toString();
		this.value = value;
	}
}

enum SortMethod {
	QuickSort, MergeSort
}

class NumberComparator<K> implements Comparator<K> {
	@Override
	public int compare(K a, K b) {
		// ascending order
		return new BigDecimal(a.toString()).compareTo(new BigDecimal(b.toString()));
	}
}

class StringComparator<K> implements Comparator<K> {
	@Override
	public int compare(K a, K b) {
		// ascending order
		return a.toString().compareTo(b.toString());
	}
};

class SortValues<K> {
	SortMethod sortMethod;
	K[] array;
	Comparator<K> comparator = null;

	public SortValues(SortMethod sortMethod, K[] array, Comparator<K> comparator) {
		this.sortMethod = sortMethod;
		this.array = array;

		if (this.array.length < 1) {
			throw new RuntimeException("Array não pode ser vazio");
		}
		if (this.array[0].getClass().equals(Number.class)) {
			this.comparator = new NumberComparator<K>();
		} else {
			this.comparator = new StringComparator<>();
		}
	}

	public void dump() {
		for (K k : array) {
			System.out.println(k.toString() + " ");
		}
	}

	public K[] sort() {
		switch (sortMethod) {
		case QuickSort:

			break;
		case MergeSort:
			MergeSort.mergeSort(array, comparator);
			break;
		default:
			break;
		}
		return array;
	}
}

enum Order {
	ASC, DESC
}

enum DisplayScope {
	DISPLAY_ALL, DISPLAY_DUMP, DISPLAY_MAX_DEPTH
}
