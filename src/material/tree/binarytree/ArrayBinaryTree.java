package material.tree.binarytree;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import material.tree.Position;
import material.tree.binarytree.LinkedBinaryTree.BTNode;
import material.tree.iterators.TreeIterator;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
	
	protected class BTNode<T> implements Position<T> {
	    private T element;
	    //private BTNode<T> parent;
	    private BTNode<T>[] myTree;
	    private int posicion,parent;

	    /** Main constructor */
	    public BTNode(T element, int parent, BTNode<T>[] myTree,int pos) {
	        setElement(element);
	        setParent(parent);
	        setMyTree(myTree);
	        setPosicion(pos);
	    }
	    
	    public void setParent(int parent) {
			this.parent = parent;
		}

		public int getPosicion() {
			return posicion;
		}

		public void setPosicion(int posicion) {
			this.posicion = posicion;
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
	    	if (getMyTree().length > 2*posicion+1)
	    		return getMyTree()[2*posicion+1];
	    	return null;
	    }

	    /** Returns the right child of this position */
	    public BTNode<T> getRight() {
	    	if (getMyTree().length > 2*posicion+2)
	    		return getMyTree()[2*posicion+2];
	    	return null;
	    }

	    /** Returns the parent of this position */
	    public BTNode<T> getParent() {
	    	if (parent != -1)
	    		return getMyTree()[parent];
	    	return null;
	    }
	    public int getParentPosition() {
	        return parent;
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
		l[0]= new BTNode<E>(value,-1,l,0);
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
		int posicion = checkPosition(v);
		return l[l[posicion].parent];
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v) throws IllegalStateException {
		int posicion = checkPosition(v);
		List<Position<E>> child = new ArrayList<>();
		child.add(l[posicion].getRight());
		child.add(l[posicion].getLeft());		
		return child;
	}

	@Override
	public boolean isLeaf(Position<E> v) throws IllegalStateException {
		// TODO Auto-generated method stub
		return !isInternal(v);
	}

	@Override
	public boolean isRoot(Position<E> v) throws IllegalStateException {
		int posicion = checkPosition(v);
		return posicion==0;
	}

	@Override
	public Position<E> addRoot(E e) throws IllegalStateException {
		l[0]= new BTNode<E>(e,-1,l,0);
		return l[0];
	}

	@Override
	public void removeSubtree(Position<E> p) throws IllegalStateException {
		int posicion = checkPosition(p);
		BTNode<E> node = l[posicion]; 
		if(node.getParentPosition()!=-1){
			TreeIterator<E> it = new TreeIterator<>(this,node);
			 int cont = 0;
			 while(it.hasNext()){
				 posicion = checkPosition(it.next());
				 BTNode<E> nNode = l[posicion];
				 nNode.setMyTree(null);
				 l[posicion] = null;
				 cont++;
			 }
			 size = size - cont;
		}else{			
			this.size = 0;
		}
		node.setMyTree(null);
		l[node.posicion]=null;		
	}

	@Override
	public E remove(Position<E> p) throws IllegalStateException {
		int posicion = checkPosition(p);
		BTNode nodeRemove = l[posicion];
		if(l[posicion].getRight()!= null && l[posicion].getLeft()!=null){
			throw new IllegalStateException("Cannot remove node with two children");
		}else{
			if (l[posicion].getRight()!= null){
				TreeIterator<E> it = new TreeIterator<>(this,l[posicion]);
				ArrayList<BTNode<E>> s = new ArrayList<>(); 
				while(it.hasNext()){
					 posicion = checkPosition(it.next());
					 BTNode<E> nNode = l[posicion];
					 s.add(nNode);
				}
				l[s.get(0).getParentPosition()]=s.get(0);
				s.get(0).parent= l[s.get(0).parent].parent;
				s.remove(0);
				while(s.size()>0){
					if(s.get(0).posicion!=0){
						
					}else{
						
					}
				}
			}
		}
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
	private int checkPosition(Position<E> p)
			{
		if (p == null || !(p instanceof BTNode))
			throw new IllegalStateException("The position is invalid");
		BTNode<E> node = (BTNode<E>) p;
		if(node.myTree != l){
			throw new IllegalStateException("The position is invalid");
		}
		return ((BTNode<E>) p).getPosicion();
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
