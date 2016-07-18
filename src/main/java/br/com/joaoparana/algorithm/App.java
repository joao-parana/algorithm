package br.com.joaoparana.algorithm;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

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

	public static void randomTree(int seed) {
		Random r = new Random(seed);
		MySearchTree st = new MySearchTree();
		for (int i = 0; i < 7; i++) {
			Integer j = r.nextInt(80) + 10;
			// System.out.println("valor: " + j);
			st.insert(j.toString(), j);
		}
		System.out.println("••• DUMP");
		st.dump();
		System.out.println("••• inOrderTransverse");
		st.inOrderTransverse();
		System.out.println("••• preOrderTransverse");
		st.preOrderTransverse();
		System.out.println("••• postOrderTransverse");
		st.postOrderTransverse();
	}

	public static void main(String[] args) {
		Random r = new Random(11870);
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
		randomTree(1380);
		randomTree(4200);
		randomTree(1810);
		randomTree(1957);
		randomTree(257);
		st = new MySearchTree();
		st.insert("Lina", r.nextInt(89) + 10);
		st.insert("Ana", r.nextInt(89) + 10);
		st.insert("Lia", r.nextInt(89) + 10);
		st.insert("Ada", r.nextInt(89) + 10);
		st.insert("Lua", r.nextInt(89) + 10);
		st.insert("Sol", r.nextInt(89) + 10);
		st.insert("Cris", r.nextInt(89) + 10);
		st.insert("Bia", r.nextInt(89) + 10);
		st.insert("Rita", r.nextInt(89) + 10);
		st.insert("Mel", r.nextInt(89) + 10);
		st.insert("Rosa", r.nextInt(89) + 10);
		st.insert("Val", r.nextInt(89) + 10);
		System.out.println("••• DUMP");
		st.dump();
		System.out.println("••• inOrderTransverse");
		st.inOrderTransverse();
		System.out.println("••• preOrderTransverse");
		st.preOrderTransverse();
		System.out.println("••• postOrderTransverse");
		st.postOrderTransverse();
		if (true) {
			return;
		}
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

class MySearchTree {
	MyNode root = null;

	void insert(String key, int value) {
		if (this.root == null) {
			this.root = new MyNode(key, value);
			return;
		}
		insert(this.root, key, value);
	}

	void insert(MyNode root, String key, int value) {
		if (root == null) {
			throw new RuntimeException("Raiz da subarvore não pode ser nula");
		} else if (key.toString().compareTo(root.key) < 0) {
			if (root.left == null) {
				root.left = new MyNode(key, value);
				// System.out.println("eu " + root.value + " left -> " + value);
				return;
			}
			insert(root.left, key, value);
		} else { // key >= root->key
			if (root.right == null) {
				root.right = new MyNode(key, value);
				// System.out.println("eu " + root.value + " rigth -> " +
				// value);
				return;
			}
			insert(root.right, key, value);
		}
	}

	public void dump() {
		if (this.root != null)
			dumpRecurse(this.root, 0);
	}

	/** This exists for debugging only */
	void dumpRecurse(MyNode p, int depth) {
		String indent = (depth == 0 ? "" : String.format("%" + (4 * depth) + "s", ""));
		if (p == null) {
			return;
		}
		if (p.left == null && p.right == null) {
			System.out.println(depth + "\t" + indent + p.key + ": " + p.value + " (is leaf)");
		} else {
			System.out.println(depth + "\t" + indent + p.key + ": " + p.value);
			dumpRecurse(p.left, depth + 1);
			dumpRecurse(p.right, depth + 1);
		}
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

}

class MyNode {
	public MyNode left;
	public MyNode right;
	String key;
	String value;

	public MyNode(String key, Integer value) {
		this.key = key.toString();
		this.value = value.toString();
	}
}
