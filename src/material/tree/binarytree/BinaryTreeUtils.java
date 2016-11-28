package material.tree.binarytree;

import material.tree.Position;

public class BinaryTreeUtils<E> {
	
	private BinaryTree<E> tree;
	
	public BinaryTreeUtils(BinaryTree<E> tree) {
		this.tree = tree;
	}

	/**
	* Given a tree the method returns a new tree where all left children
	* become right children and vice versa
	*/
	public BinaryTree<E> mirror() {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	/**
	* Determines whether the element e is the tree or not
	*/
	public boolean contains (E e) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	
	/**
	* Determines the level of a node in the tree
	*/
	public int level(Position<E> p) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
}
