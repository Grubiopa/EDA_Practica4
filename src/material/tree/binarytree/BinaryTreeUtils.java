package material.tree.binarytree;

import java.util.Iterator;

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
		BinaryTree<E> newTree = new LinkedBinaryTree<>();
		newTree.addRoot(this.tree.root().getElement());		
		newTree= recolocateMirror(newTree, this.tree.root());
		return newTree;
	}
	
	private BinaryTree<E> recolocateMirror(BinaryTree<E> ntree,Position<E> pos){
		BinaryTree<E> nbTree = ntree;
		if(this.tree.right(pos)!=null){
			nbTree.insertLeft(pos, this.tree.right(pos).getElement());
			recolocateMirror(ntree, this.tree.right(pos));		
		}
		if(this.tree.hasLeft(pos)){
			nbTree.insertRight(pos, this.tree.left(pos).getElement());
			recolocateMirror(ntree, this.tree.left(pos));
		}
		return nbTree;
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
