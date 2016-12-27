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
		for (Position<E> childR1 : this.tree) {
			if(e==childR1.getElement())
				return true;
		}
		return false;
	}
	
	
	/**
	* Determines the level of a node in the tree
	*/
	public int level(Position<E> p) {
		if (p!= this.tree.root())
			return 1 + level(this.tree.parent(p));
		return 0;
	}
	
}
