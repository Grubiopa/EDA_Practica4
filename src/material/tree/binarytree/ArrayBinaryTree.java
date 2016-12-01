package material.tree.binarytree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import material.tree.Position;
import material.tree.binarytree.LinkedBinaryTree.BTNode;
import material.tree.iterators.TreeIterator;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
	
	protected class BTNode<T> implements Position<T> {
	    private T element;
	    private BTNode<T> left, right, parent;
	    private BTNode<T>[] myTree;

	    /** Main constructor */
	    public BTNode(T element, BTNode<T> parent, BTNode<T> left,
	            BTNode<T> right, BTNode<T>[] myTree) {
	        setElement(element);
	        setParent(parent);
	        setLeft(left);
	        setRight(right);
	        setMyTree(myTree);
	    }
	    
	    public BTNode<T>[] getMyTree() {
			return myTree;
		}
	    
	    public void setMyTree(BTNode<T>[] myTree) {
			this.myTree = myTree;
		}

	    /** Returns the element stored at this position */
	    public T getElement() {
	        return element;
	    }

	    /** Sets the element stored at this position */
	    public void setElement(T o) {
	        element = o;
	    }

	    /** Returns the left child of this position */
	    public BTNode<T> getLeft() {
	        return left;
	    }

	    /** Sets the left child of this position */
	    public void setLeft(BTNode<T> v) {
	        left = v;
	    }

	    /** Returns the right child of this position */
	    public BTNode<T> getRight() {
	        return right;
	    }

	    /** Sets the right child of this position */
	    public void setRight(BTNode<T> v) {
	        right = v;
	    }

	    /** Returns the parent of this position */
	    public BTNode<T> getParent() {
	        return parent;
	    }

	    /** Sets the parent of this position */
	    public void setParent(BTNode<T> v) {
	        parent = v;
	    }
	}
	
	BTNode<E>[] l;
	int size;
	
	public ArrayBinaryTree() {
		l = new BTNode[10];
		size = 0;
	}
	public ArrayBinaryTree(E value) {
		l = new BTNode[10];
		l[0]= new BTNode<E>(value,null,null,null,l);
		size = 1;
		
	}
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public Position<E> root() throws IllegalStateException {
		return l[0];
	}

	@Override
	public Position<E> parent(Position<E> v) throws IllegalStateException {
		int posicion = checkposition(v);
		return l[posicion].parent;
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v) throws IllegalStateException {
		BTNode posicion = checkposition(v);
		List child = new ArrayList<>();
		child.add();
		
		return null;
	}

	@Override
	public boolean isLeaf(Position<E> v) throws IllegalStateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRoot(Position<E> v) throws IllegalStateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position<E> addRoot(E e) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSubtree(Position<E> p) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove(Position<E> p) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E replace(Position<E> p, E e) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Position<E>> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> left(Position<E> v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> right(Position<E> v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasLeft(Position<E> v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasRight(Position<E> v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position<E> sibling(Position<E> p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> insertLeft(Position<E> p, E e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> insertRight(Position<E> p, E e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) {
		// TODO Auto-generated method stub
		
	}
	public boolean isInternal(Position<E> v) {}
	
		// Auxiliary methods
	/**
	 * If v is a good binary tree node, cast to BTPosition, else throw exception
	 */
	private BTNode<E> checkPosition(Position<E> p)
			{
		if (p == null || !(p instanceof BTNode))
			throw new IllegalStateException("The position is invalid");
		return (BTNode<E>) p;
	}

	/**
	 * Creates a list storing the the nodes in the subtree of a node, ordered
	 * according to the preorder traversal of the subtree.
	 */
	protected void preorderPositions(Position<E> p, List<Position<E>> pos)
			{
		pos.add(p);
		if (hasLeft(p))
			preorderPositions(left(p), pos); // recurse on left child
		if (hasRight(p))
			preorderPositions(right(p), pos); // recurse on right child
	}

	/**
	 * Creates a list storing the the nodes in the subtree of a node, ordered
	 * according to the inorder traversal of the subtree.
	 */
	protected void inorderPositions(Position<E> v, List<Position<E>> pos)
			{
		if (hasLeft(v))
			inorderPositions(left(v), pos); // recurse on left child
		pos.add(v);
		if (hasRight(v))
			inorderPositions(right(v), pos); // recurse on right child
	}

	/**
	 * @return The size above a node
	 */
	private int calculateSize(BTNode<E> n) {
		if (n != null)
			return 1 + calculateSize(n.getLeft()) + calculateSize(n.getRight());
		else
			return 0;
	}

	/**
	 * Convert the node passed by parameter in the new root.
	 * 
	 * @param v
	 *            new root node
	 */
	public void subTree(Position<E> v) {
		root = checkPosition(v);
		size = calculateSize(root);
	}

	public void swap(Position<E> p1, Position<E> p2) {
        BTNode<E> node1 = checkPosition(p1);
        BTNode<E> node2 = checkPosition(p2);
        
        BTNode<E> copyNode1 = new BTNode <>(node1.element,node1.parent, node1.left, node1.right, this);

        node1.parent = node2.parent == node1 ? node2 : node2.parent;
        node1.left = node2.left == node1 ? node2 : node2.left;
        node1.right = node2.right == node1 ? node2 : node2.right;
                       
        node2.parent = copyNode1.parent == node2 ? node1 : copyNode1.parent;
        node2.left = copyNode1.left == node2 ? node1 : copyNode1.left;
        node2.right = copyNode1.right == node2 ? node1 : copyNode1.right;

        if (node1.parent != null) {
            if (node1.parent.left == node2)
                node1.parent.left = node1;
            else
                node1.parent.right = node1;
        }

        if (node2.parent != null) {
            if (node2.parent.left == node1)
                node2.parent.left = node2;
            else
                node2.parent.right = node2;            
        }
    }
}
