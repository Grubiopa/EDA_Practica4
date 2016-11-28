package material.tree.generic;

import java.util.*;

import material.tree.Position;
import material.tree.Tree;
import material.tree.iterators.TreeIterator;

/**
 * A linked class for a tree where nodes havpre an arbitrary number of children.
 *
 * @author R. Cabido, A. Duarte, J. Velez, J. Sanchez-Oro
 */
public class LinkedTree<E> implements Tree<E> {

    private class TreeNode<T> implements Position<T> {

        private T element;
        private TreeNode<T> parent;
        private List<TreeNode<T>> children;
        private LinkedTree<T> myTree;

        /**
         * Main constructor
         */
        public TreeNode(LinkedTree<T> t, T e, TreeNode<T> p, List<TreeNode<T>> c) {
            this.element = e;
            this.parent = p;
            this.children = c;
            this.myTree = t;
        }

        /**
         * Returns the element stored at this position
         */
        @Override
        public T getElement() {
            return element;
        }

        /**
         * Sets the element stored at this position
         */
        public final void setElement(T o) {
            element = o;
        }

        /**
         * Returns the children of this position
         */
        public List<TreeNode<T>> getChildren() {
            return children;
        }

        /**
         * Returns the parent of this position
         */
        public TreeNode<T> getParent() {
            return parent;
        }
        
        /**
         * Sets the parent of this position
         */
        public void setParent(TreeNode<T> parent) {
			this.parent = parent;
		}

        /**
         * @return the myTree
         */
        public LinkedTree<T> getMyTree() {
            return myTree;
        }

        /**
         * @param myTree the myTree to set
         */
        public void setMyTree(LinkedTree<T> myTree) {
            this.myTree = myTree;
        }
    }

    private TreeNode<E> root;
    private int size;

    /**
     * Creates an empty tree.
     */
    public LinkedTree() {
        root = null;
        size = 0;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return The size.
     *
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns whether the tree is empty.
     *
     * @return True if is empty.
     *
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Returns whether a node is external.
     */
    @Override
    public boolean isLeaf(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        return (node.getChildren() == null) || (node.getChildren().isEmpty());
    }

    /**
     * Returns whether a node is the root.
     */
    @Override
    public boolean isRoot(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        return (node == this.root());
    }

    /**
     * Returns the root of the tree.
     */
    @Override
    public Position<E> root() throws IllegalStateException {
        if (root == null) {
            throw new IllegalStateException("The tree is empty");
        }
        return root;
    }

    /**
     * Returns the parent of a node.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        Position<E> parentPos = (Position<E>) node.getParent();
        if (parentPos == null) {
            throw new IllegalStateException("No parent");
        }
        return parentPos;
    }

    /**
     * Returns an iterable collection of the children of a node.
     */
    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        return node.getChildren();
    }

    /**
     * Returns an iterator of the elements stored at the nodes. The nodes are
     * visited according to a breath-first search
     */
    @Override
    public Iterator<Position<E>> iterator() {
        return new TreeIterator<>(this); // An iterator of elements
    }

 
    /**
     * Replaces the element at a node.
     */
    public E replace(Position<E> p, E e) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    /**
     * Adds a root node to an empty tree
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root");
        }
        size = 1;
        root = new TreeNode<>(this, e, null, new ArrayList<TreeNode<E>>());
        return root;
    }

    /**
     * Swap the elements at two nodes
     */
    public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException {
        TreeNode<E> node1 = checkPosition(p1);
        TreeNode<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    /**
     * If v is a good tree node, cast to TreePosition, else throw exception
     */
    private TreeNode<E> checkPosition(Position<E> p) throws IllegalStateException {
        if (p == null || !(p instanceof TreeNode)) {
            throw new IllegalStateException("The position is invalid");
        }
        TreeNode<E> aux = (TreeNode<E>) p;

        if (aux.getMyTree() != this) {
            throw new IllegalStateException("The node is not from this tree");
        }
        return aux;
    }

    /**
     * Add a new node whose parent is pointed by a given position.
     *
     * @param p The position of the parent, e the element stored in the new
     * created node.
     * @throws InvalidPositionException
     */
    public Position<E> add(E element, Position<E> p) throws IllegalStateException {
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newNode = new TreeNode<>(this, element, parent, new ArrayList<TreeNode<E>>());
        List<TreeNode<E>> l = parent.getChildren();
        l.add(newNode);
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
        TreeNode<E> parent = checkPosition(p);
        TreeNode<E> newNode = new TreeNode<>(this, element, parent, new ArrayList<TreeNode<E>>());
        List<LinkedTree<E>.TreeNode<E>> l = parent.getChildren();
        if (n > l.size())
            throw new IllegalStateException("The element can't be inserted at specified position.");
        l.add(n, newNode);
        size++;
        return newNode;
    }
    
    
    
    /**
     * Remove a node and its corresponding subtree rooted at node.
     *
     * @param p The position of the node to be removed.
     * @throws InvalidPositionException
     */
    public void removeSubtree(Position<E> p) throws IllegalStateException {
        TreeNode<E> node = checkPosition(p);
        if (node.getParent() != null) {
            TreeIterator<E> it = new TreeIterator<>(this,node);
            int cont = 0;
            while (it.hasNext()) {
                TreeNode<E> tNode = checkPosition(it.next());
                tNode.setMyTree(null);
                cont++;
            }
            size = size - cont;
            TreeNode<E> parent = node.getParent();
            parent.getChildren().remove(node);
        } else {
            this.root = null;
            this.size = 0;
        }
        node.setMyTree(null);
    }
    
    @Override
	public E remove(Position<E> p) throws IllegalStateException {
    	TreeNode<E> node = checkPosition(p);
    	List<TreeNode<E>> children = node.children;
    	if (children.size() == 1) {
    		TreeNode<E> child = children.get(0);
    		child.setParent(node.parent);
    		if (isRoot(p)) {
    			root = child;
    		} else {
    			TreeNode<E> parent = node.parent;
    			parent.getChildren().remove(node);
    			parent.getChildren().add(child);    			
    		}
    		size--;
    		node.setMyTree(null);
    		node.children = null;
    		return node.getElement();
    	} else {
    		throw new IllegalStateException("This node cannot be removed");
    	}
	}
    
    public Position<E> moveSubtree(Position<E> pOrig, Position<E> pDest) throws IllegalStateException {
        TreeNode<E> orig= checkPosition(pOrig);
        TreeNode<E> dest= checkPosition(pDest);
        
        if(isDescendiente(dest,orig)){
              throw new IllegalStateException("The destiny is descendant or the destiny is the same than the origin");
        }else{
            dest.getChildren().add(orig);
            List<TreeNode<E>> children = orig.getParent().getChildren();
            children.remove(orig);
            orig.setParent(dest);
        }
        return dest;
    }
    public boolean isDescendiente(Position<E> dest,Position<E> orig){
        boolean descendiente= false;
        TreeNode<E> aux=(TreeNode<E>)dest;
        
        while((!descendiente) && (aux!=null)){
            descendiente= (aux== orig);
            aux=aux.getParent();
        }
        return descendiente;
    }

	            
}
