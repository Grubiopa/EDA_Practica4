package material.tree.binarytree;


import java.nio.channels.IllegalSelectorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import javax.security.auth.callback.ChoiceCallback;

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
	    
	    public void setLeft(Position<E> pos){
	    	if (getMyTree().length < 2*posicion+1){
	    		overSize();
	    	}
	    	l[2*posicion+1]= new BTNode<E>(pos.getElement(),posicion,l , 2*posicion+1);	    	
	    }
	   
	}
	
	BTNode<E>[] l;
	int size;
	int arraysize=10;
	
	public ArrayBinaryTree() {
		l = new BTNode[arraysize];
		size = 0;
	}
	public ArrayBinaryTree(E value) {
		l = new BTNode[arraysize];
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
		if(l[0]==null)
			throw new IllegalStateException();
		return l[0];
	}

	@Override
	public Position<E> parent(Position<E> v) throws IllegalStateException {
		int posicion = checkPosition(v);
		if(posicion == 0)
			throw new IllegalStateException("is the root");
		return l[l[posicion].parent];
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v)  {
		int posicion = checkPosition(v);
		List<Position<E>> child = new ArrayList<>();
		if(l[posicion].getLeft()!=null && (posicion*2+2<arraysize))
			child.add(l[posicion].getLeft());
		if(l[posicion].getRight()!=null && (posicion*2+1<arraysize))
			child.add(l[posicion].getRight());
	
		return child;
	}

	@Override
	public boolean isLeaf(Position<E> v) throws IllegalStateException {
		
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
		size++;
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
	
	private void recolocar(BTNode<E> p,int padrePos){
		int posIzq=padrePos*2+1;
		if(p.getLeft()!=null){
			l[posIzq]=p.getLeft();
			l[posIzq].setParent(padrePos);
			l[posIzq].setPosicion(posIzq);
			recolocar(l[posIzq],posIzq);
		}
		if(p.getRight()!=null){
			l[posIzq + 1]=p.getRight();
			l[posIzq + 1].setParent(padrePos);
			l[posIzq + 1].setPosicion(posIzq + 1);
			recolocar(l[posIzq+1],posIzq + 1);
		}
	}
	@Override
	public E remove(Position<E> p) throws IllegalStateException {
		int posicion = checkPosition(p);
		BTNode<E> nodeRemove = l[posicion];
		BTNode<E> changeNode;
		if(l[posicion].getRight()!= null && l[posicion].getLeft()!=null){
			throw new IllegalStateException("Cannot remove node with two children");
			
		}else if(l[posicion].getRight()!= null || l[posicion].getLeft()!=null){
			if (l[posicion].getRight()!= null){
				changeNode = l[posicion].getRight();
				recolocar(l[posicion].getRight(),posicion);
				
			}else{
				changeNode = l[posicion].getLeft();
				recolocar(l[posicion].getLeft(),posicion);
			}
				l[posicion] = changeNode;
				l[posicion].setParent(nodeRemove.parent);
				l[posicion].setPosicion(posicion);
			
		}else{
			l[posicion]=null;
		}
		
		return nodeRemove.getElement();
	}
	
	
	@Override
	public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException {
		int pos1 = checkPosition(p1);
		int pos2 = checkPosition(p2);
		E temp = l[pos1].getElement();
		l[pos1].setElement(l[pos2].getElement());
		l[pos2].setElement(temp);
		
	}

	@Override
	public E replace(Position<E> p, E e) throws IllegalStateException {
		int posicion =  checkPosition(p);
		E ele= l[posicion].getElement();
		l[posicion].setElement(e);
		return ele;
	}

	@Override
	public Iterator<Position<E>> iterator() {
		return new TreeIterator<>(this);
	}

	@Override
	public Position<E> left(Position<E> v) throws IllegalStateException{
		int posicion = checkPosition(v);
		if (l[posicion].getLeft()== null)
			throw new IllegalStateException("no left child");
		return l[posicion].getLeft();
	}

	@Override
	public Position<E> right(Position<E> v) {
		int posicion = checkPosition(v);
		if(l[posicion].getRight()==null)
			throw new IllegalStateException("no right child");
		return l[posicion].getRight();
	}

	@Override
	public boolean hasLeft(Position<E> v) {
		int posicion = checkPosition(v);
		return l[posicion].getLeft()!=null;
	}

	@Override
	public boolean hasRight(Position<E> v) {
		int posicion = checkPosition(v);
		return l[posicion].getRight()!=null;
	}

	@Override
	public Position<E> sibling(Position<E> p) {
		int posicion = checkPosition(p);
		BTNode<E> node = l[posicion];
		BTNode<E> parentPos = node.getParent();
		if (parentPos != null) {
			BTNode<E> sibPos;
			BTNode<E> leftPos = parentPos.getLeft();
			if (leftPos == node)
				sibPos = parentPos.getRight();
			else
				sibPos = parentPos.getLeft();
			if (sibPos != null)
				return sibPos;
		}
		throw new IllegalStateException("No sibling");
	}
	
	private void overSize(){
		BTNode<E>[] newArray = new BTNode[arraysize*2+2];
		for(int i=0;i<arraysize;i++){
			newArray[i]=l[i];
		}
		l=newArray;
		arraysize=arraysize*2;
	}
	@Override
	public Position<E> insertLeft(Position<E> p, E e) {
		int posicion = checkPosition(p);
		if(posicion *2 +1 >arraysize){
			overSize();
		}
		l[posicion*2+1]= new BTNode<E>(e, posicion, l, posicion*2+1);
		size++;
		return l[posicion*2+1];
	}

	@Override
	public Position<E> insertRight(Position<E> p, E e) {
		int posicion = checkPosition(p);
		if(posicion *2 +1 >arraysize){
			overSize();
		}
		l[posicion*2+2]= new BTNode<E>(e, posicion, l, posicion*2+2);
		size++;
		return l[posicion*2+2];
	}
	
	
	@Override
	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) {
		checkPosition(p);
		if(isInternal(p))
			throw new IllegalStateException("Cannot attach from internal node");
		int newSize = size + t1.size() + t2.size();
		if (!t1.isEmpty()) {
			Position<E> r1 = t1.root();
			Position<E> node = insertLeft(p,r1.getElement());
			recolocateNodes(t1.left(t1.root()), t1.right(t1.root()), t1, node);
			remove(t1.root());
		}
		if (!t2.isEmpty()) {
			Position<E> r2 = t2.root();
			Position<E> node = this.insertRight(p, r2.getElement());
			recolocateNodes(t2.left(t2.root()), t2.right(t2.root()), t2, node);
			remove(t2.root());
		}
		this.size = newSize;
	}
	private void recolocateNodes(Position<E> lChild, Position<E> rChild,BinaryTree<E> t, Position<E> node){
		if(lChild!=null){
			Position<E> pos = insertLeft(node, lChild.getElement());
			recolocateNodes(t.left(lChild), t.right(lChild), t, pos);
			remove(lChild);
		}
		if(rChild != null){
			Position<E> pos = insertRight(node, rChild.getElement());
			recolocateNodes(t.left(rChild), t.right(rChild), t, pos);
			remove(rChild);
		}
	}
	public boolean isInternal(Position<E> v) {
		int posicion = checkPosition(v);
		if(posicion*2+1<arraysize){
			return l[posicion*2+1] !=null || l[posicion*2+2]!=null;
		}
		return false;
	}
	
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
		int posicion = checkPosition(v);
		size = calculateSize(l[posicion]);
	}

	public void swap(Position<E> p1, Position<E> p2) {
        int pos1 = checkPosition(p1);
        int pos2 = checkPosition(p2);
        E aux = l[pos1].getElement();
        l[pos1].setElement(l[pos2].getElement());
        l[pos2].setElement(aux);
    }
}
