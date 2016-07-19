package br.com.joaoparana.algorithm.avl.generic;

public interface Element<E> extends Comparable<E> {
	E getValue();
	void setValue(E elem);
}
