package material.tree.iterators;

import java.util.Iterator;
import java.util.Stack;

import material.tree.Position;
import material.tree.Tree;

public class PreorderIterator<E> implements Iterator<Position<E>> {

	private Stack<Position<E>> nodeStack;
	private Tree<E> tree;
	
	@Override
	public boolean hasNext() {
		 return (nodeStack.size() != 0);
	}

	@Override
	public Position<E> next() {
		Stack<Position<E>> auxStack = new Stack<>();
		Position<E> aux = nodeStack.pop();
	    for (Position<E> node : tree.children(aux)) {
	    	auxStack.push(node);
	    }
	    while (!auxStack.isEmpty()){
	    	nodeStack.push(auxStack.pop());
	    }
		return aux;
	}
	public PreorderIterator(Tree<E> tree) {
		this.tree = tree;
		nodeStack = new Stack<>();
		if (!tree.isEmpty()) {
			nodeStack.add(tree.root());
		}
	}

    public PreorderIterator(Tree<E> tree, Position<E> root) {
    	this.tree = tree;
    	nodeStack = new Stack<>();
    	nodeStack.add(root);
    }

}
