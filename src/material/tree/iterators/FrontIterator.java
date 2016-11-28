package material.tree.iterators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import material.tree.Position;
import material.tree.Tree;

public class FrontIterator<E> implements Iterator<Position<E>> {

	private Queue<Position<E>> nodeQueue;
	private Tree<E> tree;
	
	@Override
	public boolean hasNext() {
		 return (nodeQueue.size() != 0);
	}

	@Override
	public Position<E> next() {
		   Position<E> aux = nodeQueue.remove();
	       while(!tree.isLeaf(aux)){
			   for (Position<E> node : tree.children(aux)) {	    
		        	nodeQueue.add(node);
		       }
			   aux = nodeQueue.remove();
	       }
			return aux;
	}
	public FrontIterator(Tree<E> tree) {
		this.tree = tree;
		nodeQueue = new ArrayDeque<>();
		if (!tree.isEmpty()) {
			nodeQueue.add(tree.root());
		}
	}

    public FrontIterator(Tree<E> tree, Position<E> root) {
    	this.tree = tree;
		nodeQueue = new ArrayDeque<>();
        nodeQueue.add(root);
    }


}
