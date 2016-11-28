package material.tree.binarytree;

import material.tree.*;

/**
 * An interface for a binary tree, where each node can have zero, one,
 * or two children.
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 */
public interface BinaryTree<E> extends Tree<E>, Iterable <Position<E>>{
	/** Returns the left child of a node. */
	public Position<E> left(Position<E> v);

	/** Returns the right child of a node. */
	public Position<E> right(Position<E> v);

	/** Returns whether a node has a left child. */
	public boolean hasLeft(Position<E> v);

	/** Returns whether a node has a right child. */
	public boolean hasRight(Position<E> v);

	/** Return the sibling of a node */
	public Position<E> sibling(Position<E> p);

	/** Inserts a left child at a given node. */
	public Position<E> insertLeft(Position<E> p, E e);

	/** Inserts a right child at a given node. */
	public Position<E> insertRight(Position<E> p, E e);

	/** Attaches two trees to be subtrees of a leaf node. */
	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2);

}
