package material.tree.generic;

import java.util.*;

import material.tree.Position;
import material.tree.Tree;
import material.tree.iterators.TreeIterator;



public class LCRSTree<E> implements Tree<E> {
	
	private class LRSCNode<T> implements Position<T>{
		private T element;
	    private LRSCNode<T> parent;
	    private LRSCNode<T> child;
	    private LRSCNode<T> sibling;
	    private LCRSTree<T> myTree;
	    
		public LRSCNode(T element, LRSCNode<T> parent, LRSCNode<T> children,LRSCNode<T> sibling, LCRSTree<T> myTree) {
			
			this.element = element;
			this.parent = parent;
			this.child = children;
			this.sibling = sibling;
			this.myTree = myTree;
		}
		public T getElement() {
			return element;
		}
		public void setElement(T element) {
			this.element = element;
		}
		public LRSCNode<T> getParent() {
			return parent;
		}
		public void setParent(LRSCNode<T> parent) {
			this.parent = parent;
		}
		public LRSCNode<T> getChild() {
			return child;
		}
		public void setChild(LRSCNode<T> children) {
			this.child = children;
		}
		public LRSCNode<T> getSibling() {
			return sibling;
		}
		public void setSibling(LRSCNode<T> sibling) {
			this.sibling = sibling;
		}
		public LCRSTree<T> getMyTree() {
			return myTree;
		}
		public void setMyTree(LCRSTree<T> myTree) {
			this.myTree = myTree;
		}
	}
	
    private LRSCNode<E> root;
    private int size;
    
	@Override
	public Iterator<Position<E>> iterator() {
		return new TreeIterator<>(this);
	}
	public LCRSTree(){
		root = null;
		size = 0;
	}
	
	@Override
	public int size() {		
		return size;
	}

	@Override
	public boolean isEmpty() {
		return (size == 0);
	}

	@Override
	public Position<E> root() throws IllegalStateException {
        if (root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        return root;
	}

	@Override
	public Position<E> parent(Position<E> v) throws IllegalStateException {
		LRSCNode<E> node = checkPosition(v);
        Position<E> parentPos = (Position<E>) node.getParent();
        if (parentPos == null) {
            throw new IllegalStateException("No parent");
        }
        return parentPos;
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v) throws IllegalStateException {
		LRSCNode<E> node = checkPosition(v);
		List<LRSCNode<E>> list = new ArrayList<>();
		LRSCNode<E> aux = node.getChild();
		
		while(aux!= null){
			list.add(aux);
			aux=aux.getSibling();
		}
		return list;
	}

	@Override
	public boolean isLeaf(Position<E> v) throws IllegalStateException {
		LRSCNode<E> node = checkPosition(v);
        return node.getChild() == null;
	}

    private LRSCNode<E> checkPosition(Position<E> p) throws IllegalStateException {
        if (p == null || !(p instanceof LRSCNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        LRSCNode<E> aux = (LRSCNode<E>) p;

        if (aux.getMyTree() != this) {
            throw new IllegalStateException("The node is not from this tree");
        }
        return aux;
    }
    
	@Override
	public boolean isRoot(Position<E> v) throws IllegalStateException {
		LRSCNode<E> node = checkPosition(v);
        return (node == this.root());
	}

	@Override
	public Position<E> addRoot(E e) throws IllegalStateException {
		if(!isEmpty()){
			throw new IllegalStateException("Tree already has a root");
		}
		size = 1;
		root = new LRSCNode<E>(e, null, null, null, this);
		return root;
	}

	@Override
	public void removeSubtree(Position<E> p) throws IllegalStateException {
		 LRSCNode<E> node = checkPosition(p);
	        if (node.getParent() != null) {
	            TreeIterator<E> it = new TreeIterator<>(this,node);
	            int cont = 0;
	            while (it.hasNext()) {
	                it.next();
	                cont++;
	            }
	            size = size - cont;
	            LRSCNode<E> parent = node.getParent();
	            if(parent.getChild()==node){
	            	parent.setChild(node.getSibling());	            	
	            }else{
	            	BeforeSibling(node).setSibling(node.getSibling());	            	
	            }
	            node.setSibling(null);
            	node.setParent(null);
	        } else {
	            this.root = null;
	            this.size = 0;
	        }
	        node.setMyTree(null);
		
	}

	@Override
	public E remove(Position<E> p) throws IllegalStateException {
		LRSCNode<E> node = checkPosition(p);
    	LRSCNode<E>child = node.child;
    	if (child == null){
    		if (isRoot(p)) {
    			root = null;    			
    		} else {
    			if(node.getParent().getChild()==node){
    				node.getParent().setChild(node.getSibling());
    				node.setSibling(null);
    			}else{
    				BeforeSibling(node).setSibling(node.getSibling());
    			}    			
    		}
    		node.setMyTree(null);
    		return node.getElement();
    	}
    	else if (child.getSibling() == null) {    		
    		if (isRoot(p)) {
    			child.setParent(null);
    			root = child;
    		} else {
    			child.setParent(node.parent);
    			child.setSibling(node.getSibling());
    			node.sibling=null;
    			if(node.getParent().getChild()==node){
    				node.getParent().setChild(child);
    			}else{
    				BeforeSibling(node).setSibling(child);
    			}    			
    		}
    		node.setMyTree(null);
    		return node.getElement();
    	} else {
    		// Throw exception
    		new IllegalStateException("More than 1 child");
    		return null; // The node cannot be removed
    	}
	}

	@Override
	public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException {
		LRSCNode<E> node1 = checkPosition(p1);
        LRSCNode<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
		
	}

	@Override
	public E replace(Position<E> p, E e) throws IllegalStateException {
		  LRSCNode<E> node = checkPosition(p);
	      E temp = p.getElement();
	      node.setElement(e);
	      return temp;
	}
	
    public Position<E> moveSubtree(Position<E> pOrig, Position<E> pDest) {
        LRSCNode<E> pOrigen = checkPosition(pOrig);
        LRSCNode<E> pDestino = checkPosition(pDest);
        LRSCNode<E> father = pOrigen.getParent();
        LRSCNode<E> lastSibling = pDestino.getChild();
        
        if (!isAChild(pOrigen,pDestino)){
        	//Unique child
        	if(father.getChild().getSibling()==null){
        		father.setChild(null);
        		//First Child
        	}else if(father.getChild()==pOrigen){
        		father.setChild(pOrigen.getSibling());
        		pOrigen.setSibling(null);
        		//otherwise
        	}else{
        		BeforeSibling(pOrigen).setSibling(pOrigen.getSibling());
        		pOrigen.setSibling(null);
        	}
    		//Mix in the new place
        	pOrigen.setParent(pDestino);
        	        	
        	if (lastSibling!=null){
        		while (lastSibling.getSibling()!= null){
            		lastSibling = lastSibling.getSibling();
            	}
        		lastSibling.setSibling(pOrigen);
        	}	
        	else{
        		pDestino.setChild(pOrigen);
        	}
        	return pOrigen;
        }else{
        	throw new IllegalStateException("Destino is a child of Origen");
        }
    }
    
    private boolean isAChild(LRSCNode<E> pO,LRSCNode<E> pD){
    	boolean Child = false;
    	while ((pD.getParent()!=null)&& !Child){
    		Child= (pO==pD)?true:false;
    		pD = pD.getParent();
    	}
    	return Child;
    }
    
    private LRSCNode<E> BeforeSibling(LRSCNode<E> t){
    	LRSCNode<E> firstSibling = t.getParent().getChild();
    	while(firstSibling != null && firstSibling.getSibling()!=t){
    		firstSibling = firstSibling.getSibling();
    	}
    	return firstSibling;
    }
    
    /**
     * Add a new node whose parent is pointed by a given position.
     *
     * @param p The position of the parent, e the element stored in the new
     * created node.
     * @throws InvalidPositionException
     */
    public Position<E> add(E element, Position<E> p) throws IllegalStateException {
    	LRSCNode<E> parent = checkPosition(p);
        LRSCNode<E> newNode = new LRSCNode<E>(element, parent, null, null, parent.getMyTree());
        LRSCNode<E> child = parent.getChild();
        if (child==null){
        	parent.setChild(newNode);
        }else{
        	while(child.sibling!=null){
        		child=child.sibling;
        	}
        	child.setSibling(newNode);
        }
        size++;
        return newNode;
    }

    /**
     * Add a new node whose parent is pointed by a given position, 
     * and set the child at the position n if possible.
     *
     * @param p The position of the parent, e the element stored in the new
     * created node.
     * @throws InvalidPositionException
     */
    public Position<E> add(E element, Position<E> p, final int n) throws IllegalStateException {

    	LRSCNode<E> parent = checkPosition(p);
        LRSCNode<E> newNode = new LRSCNode<E>(element, parent, null, null, parent.getMyTree());
        LRSCNode<E> child = parent.getChild();
        LRSCNode<E> sibling = child.getSibling();
        int contador=0;
        if(n==0){
             newNode.sibling= child;
             child = newNode;            	  	
        }else{
        	 while(contador!=(n-1) && child!=null){
         		contador++;
         		child= child.sibling;
         		sibling= child.sibling;
         	}        	 
        	if(contador == (n-1)){
        	 child.setSibling(newNode);
        	 newNode.setSibling(sibling);
        	}else{
        		throw new IllegalStateException("The element can't be inserted at specified position.");  
        	}
        }        
        size++;
        return newNode;
    }
}
