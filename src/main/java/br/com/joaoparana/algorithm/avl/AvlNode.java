package br.com.joaoparana.algorithm.avl;

public class AvlNode {
	// Constructors
	AvlNode(Comparable theElement) {
		this(theElement, null, null);
	}

	AvlNode(Comparable theElement, AvlNode lt, AvlNode rt) {
		element = theElement;
		left = lt;
		right = rt;
		height = 0;
	}

	// Friendly data; accessible by other package routines
	Comparable element; // The data in the node
	AvlNode left; // Left child
	AvlNode right; // Right child
	int height; // Height
}
