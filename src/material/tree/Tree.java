package material.tree;


/**
 * An interface for a tree where nodes can have an arbitrary number of children.
 * 
 * @author A. Duarte, J. Velez, J. Sanchez-Oro
 */
public interface Tree<E> extends Iterable <Position<E>>{
	/** Returns the number of nodes in the tree. */
	public int size();

	/** Returns whether the tree is empty. */
	public boolean isEmpty();

	/** Returns the root of the tree. */
	public Position<E> root() throws IllegalStateException;

	/** Returns the parent of a given node. */
	public Position<E> parent(Position<E> v) throws IllegalStateException;

	/** Returns an iterable collection of the children of a given node. */
	public Iterable<? extends Position<E>> children(Position<E> v) throws IllegalStateException;

	/** Returns whether a given node is external. */
	public boolean isLeaf(Position<E> v) throws IllegalStateException;

	/** Returns whether a given node is the root of the tree. */
	public boolean isRoot(Position<E> v) throws IllegalStateException;
	
	/** Sets the root of the tree */
	public Position<E> addRoot(E e);
	
	/** Remove a node and its corresponding subtree rooted at node. */
    public void removeSubtree(Position<E> p) throws IllegalStateException;
    
    public E remove(Position<E> p) throws IllegalStateException;
    
    /** Swap the elements at two nodes */
    public void swapElements(Position<E> p1, Position<E> p2) throws IllegalStateException;
    
    /** Replaces the element at a node. */
    public E replace(Position<E> p, E e) throws IllegalStateException;
}
