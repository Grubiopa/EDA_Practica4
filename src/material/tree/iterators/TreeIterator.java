package material.tree.iterators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import material.tree.Position;
import material.tree.Tree;

public class TreeIterator<E> implements Iterator<Position<E>> {

	private Queue<Position<E>> nodeQueue;
	private Tree<E> tree;
	
	public TreeIterator(Tree<E> tree) {
		this.tree = tree;
		nodeQueue = new ArrayDeque<>();
		if (!tree.isEmpty()) {
			nodeQueue.add(tree.root());
		}
	}

    public TreeIterator(Tree<E> tree, Position<E> root) {
    	this.tree = tree;
		nodeQueue = new ArrayDeque<>();
        nodeQueue.add(root);
    }

    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    /**
     * This method visits the nodes of a tree by following a breath-first
     * search
     */
    @Override
    public Position<E> next() {
        Position<E> aux = nodeQueue.remove();
        for (Position<E> node : tree.children(aux)) {
            nodeQueue.add(node);
        }
        return aux;
    }
}
