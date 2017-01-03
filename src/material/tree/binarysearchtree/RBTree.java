package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import material.tree.Position;

/**
 *
 * Realization of a red-black Tree by extending a binary search tree.
 *
 * @author A. Duarte, J. Vélez
 */
/**
 * Realization of a dictionary by means of a red-black tree.
 */
public class RBTree<E> implements BinarySearchTree<E> {

//Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
    private class RBInfo<T> implements Comparable<RBInfo<T>>, Position<T> {

        private boolean isRed; // we add a color field to a BTNode
        private T element;
        private Position<RBInfo<T>> pos;

        RBInfo(T element) {
            this.element = element;
        }

        public void setTreePosition(Position<RBInfo<T>> pos) {
            this.pos = pos;
        }

        public Position<RBInfo<T>> getTreePosition() {
            return this.pos;
        }

        public boolean isRed() {
            return isRed;
        }

        public void setRed(boolean color) {
            isRed = color;
        }

        public T getElement() {
            return element;
        }

        @Override
        public boolean equals(Object obj) {
            RBInfo<T> info = (RBInfo<T>) obj;
            return element.equals(info.getElement());
        }

        @Override
        public int compareTo(RBInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable) {
                Comparable<T> c1 = (Comparable<T>) element;
                return c1.compareTo(o.element);

            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }
    }

    private class RBTreeIterator<T> implements Iterator<Position<T>> {

        private Iterator<Position<RBInfo<T>>> it;

        public RBTreeIterator(Iterator<Position<RBInfo<T>>> iterator) {
            this.it = iterator;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<T> next() {
            Position<RBInfo<T>> aux = it.next();
            return aux.getElement();
        }

        @Override
        public void remove() {
            it.remove();
        }
    }

    private LinkedBinarySearchTree<RBInfo<E>> bst = new LinkedBinarySearchTree<>();
    private ReestructurableBinaryTree<RBInfo<E>> resBT = new ReestructurableBinaryTree<>();

    public RBTree() {
        bst.binTree = resBT;
    }

    @Override
    public Position<E> find(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value);
        Position<RBInfo<E>> output = bst.find(searchedValue);

        if (output == null) {
            return null;
        }
        return output.getElement();
    }

    @Override
    public Iterable<Position<E>> findAll(E value) {
        RBInfo<E> searchedValue = new RBInfo<>(value);
        List<Position<E>> aux = new ArrayList<>();
        for (Position<RBInfo<E>> n : bst.findAll(searchedValue)) {
            aux.add(n.getElement());
        }
        return aux;
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    @Override
    public int size() {
        return bst.size();
    }

    /**
     * Inserts an element into the RBTree and returns the newly created postion.
     */
    public Position<E> insert(E e) {
        RBInfo<E> aux = new RBInfo<>(e);

        Position<RBInfo<E>> posZ = bst.insert(aux);
        aux.setTreePosition(posZ);
        aux.setRed(true);

        if (this.bst.binTree.isRoot(posZ)) {
            aux.setRed(false);
        } else {
            remedyDoubleRed(aux); // fix a double-red color violation
        }
        return aux;
    }

    /**
     * Remedies a double red violation at a given node caused by insertion.
     */
    protected void remedyDoubleRed(RBInfo<E> nodeZ) {
        Position<RBInfo<E>> posV = this.bst.binTree.parent(nodeZ.getTreePosition());
        RBInfo<E> nodeV = posV.getElement();

        if (!nodeV.isRed()) {
            return;
        }
        // we have a double red: posZ and posV
        Position<RBInfo<E>> grandParent = this.bst.binTree.parent(posV);
        boolean hasSibling = this.bst.binTree.hasLeft(grandParent) && this.bst.binTree.hasRight(grandParent);
        boolean blackUncle = true;
        if (hasSibling) {
            Position<RBInfo<E>> uncleZ = this.bst.binTree.sibling(posV);
            blackUncle = !uncleZ.getElement().isRed;
        }
        if (blackUncle) { // Case 1: trinode restructuring
            posV = this.resBT.restructure(nodeZ.getTreePosition(), this.bst);
            posV.getElement().setRed(false);
            this.bst.binTree.left(posV).getElement().setRed(true);
            this.bst.binTree.right(posV).getElement().setRed(true);
        } else { // Case 2: recoloring
            nodeV.setRed(false);
            if (hasSibling) {
                this.bst.binTree.sibling(posV).getElement().setRed(false);
            }
            Position<RBInfo<E>> posU = this.bst.binTree.parent(posV);
            if (this.bst.binTree.isRoot(posU)) {
                return;
            }
            RBInfo<E> nodeU = posU.getElement();
            nodeU.setRed(true);
            remedyDoubleRed(nodeU);
        }
    }

    @Override
    public void remove(Position<E> pos) throws IllegalStateException {
        RBInfo<E> rbInfo = (RBInfo<E>) pos;
        Position<RBInfo<E>> treePos = rbInfo.getTreePosition();

        Position<RBInfo<E>> parent = null;

        if (resBT.isLeaf(treePos) || !resBT.hasLeft(treePos) || !resBT.hasRight(treePos)) {
            if (resBT.root() != treePos) {
                parent = resBT.parent(treePos);
            }
            resBT.remove(treePos);
        } else {
            Position<RBInfo<E>> sucessor = bst.sucessor(treePos);
            resBT.swap(sucessor, treePos);
            final boolean colorSuccesor = sucessor.getElement().isRed;
            sucessor.getElement().setRed(treePos.getElement().isRed());
            treePos.getElement().setRed(colorSuccesor);
            if (resBT.root() != treePos) {
                parent = resBT.parent(treePos);
            }
            resBT.remove(treePos);
        }

        Position<RBInfo<E>> nodeR = null;
        if (resBT.hasLeft(treePos))
            nodeR = resBT.left(treePos);
        else if (resBT.hasRight(treePos))
            nodeR = resBT.right(treePos);
        
        if ((nodeR != null) && (nodeR.getElement().isRed)) {
            nodeR.getElement().setRed(false);
            return;
        }
        
        if (treePos.getElement().isRed) {
            return;
        }
        
        if (resBT.isEmpty()) {
            return;
        }
        
        remedyDoubleBlack(parent, nodeR);
    }

    /**
     * Remedies a double black violation at a given node caused by removal.
     */
    protected void remedyDoubleBlack(Position<RBInfo<E>> doubleBlackParent, Position<RBInfo<E>> doubleBlack) {
        Position<RBInfo<E>> posX = doubleBlackParent;
        Position<RBInfo<E>> posZ = null;
        Position<RBInfo<E>> posY = null;
        boolean YisLeftChildOfX;
        
        if (this.bst.binTree.hasLeft(posX) && this.bst.binTree.left(posX) != doubleBlack) {
            posY = this.bst.binTree.left(posX);
            YisLeftChildOfX = true;
        } else {
            posY = this.bst.binTree.right(posX);
            YisLeftChildOfX = false;
        }

        RBInfo<E> nodeX = doubleBlackParent.getElement();
        RBInfo<E> nodeY = posY.getElement();

        if (!nodeY.isRed()) {
            posZ = hasRedChild(posY); //posZ != null means that it at least one red childpren
            if (posZ != null) { // Case 1: trinode restructuring
                boolean oldColor = nodeX.isRed();
                Position<RBInfo<E>> posB = this.resBT.restructure(posZ, this.bst); //After restrusturing posZ gets the value of midKey
                posB.getElement().setRed(oldColor);
                this.bst.binTree.left(posB).getElement().setRed(false);
                this.bst.binTree.right(posB).getElement().setRed(false);
                return;
            } else { // Case 2: recoloring
                nodeY.setRed(true);
                if (!nodeX.isRed()) { //If X is black we propagate the doble black problem to the root
                    if (!this.bst.binTree.isRoot(posX)) { //If X is root the problem is solved
                        remedyDoubleBlack(this.bst.binTree.parent(posX),posX);  //In other case propagate
                    }
                    return;
                }
                nodeX.setRed(false);
                return;
            }
        } // Case 3: adjustment
        
        if (YisLeftChildOfX && this.bst.binTree.hasLeft(posY)) {
            posZ = this.bst.binTree.left(posY);
        } else if (this.bst.binTree.hasRight(posY)) {
            posZ = this.bst.binTree.right(posY);
        }
        this.resBT.restructure(posZ, this.bst);
        nodeY.setRed(false);
        nodeX.setRed(true);
        remedyDoubleBlack(doubleBlackParent, doubleBlack);
    }

    /**
     * Returns a red child of a node.
     */
    protected Position<RBInfo<E>> hasRedChild(Position<RBInfo<E>> pos) {
        Position<RBInfo<E>> child;
        if (this.bst.binTree.hasLeft(pos)) {
            child = this.bst.binTree.left(pos);
            final boolean redLeftChild = child.getElement() != null && child.getElement().isRed();
            if (redLeftChild) {
                return child;
            }
        }

        if (this.bst.binTree.hasRight(pos)) {
            child = this.bst.binTree.right(pos);
            final boolean redRightChild = child.getElement() != null && child.getElement().isRed();
            if (redRightChild) {
                return child;
            }
        }
        return null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<RBInfo<E>>> bstIt = bst.iterator();
        RBTreeIterator<E> it = new RBTreeIterator<>(bstIt);
        return it;
    }

    /**
     * Returns the position with the smallest value in the tree.
     */
    //PRACTICA 5 ejercicio 2, metodo first para el AVLTree
    //*********************************************************************
    //En los RBTree, es un caso parecido a los AVL
    //Funcionan de otra manera, porque en este caso tienen un "color" , pero al final el funcionamiento 
    //es el mismo, el valor mas bajo se queda a la izquierda, ya que se basa en BST
    //por lo que si nos piden el mas bajo, solo tenemos que hacer un hasLeft, hasta que ya
    //no existan mas nodos a la izquierda y devolver la hoja que este más a la izquierda
    public Position<E> first() {
        //private LinkedBinarySearchTree<RBInfo<E>> bst = new LinkedBinarySearchTree<>();
        //Tenemos bst, que utiliza LinkedBinarySearchTree, por lo que podemos llamar a la funcion
        //first() que hemos creado primero en la clase linkedBinarySearchTree

        //Se guarda en el position
        Position<E> nodo = bst.first().getElement();
        //Se devuelve
        return nodo;
    }

    //**Returns the position with the smallest value in the tree. */
    //PRACTICA 5 ejercicio 2, metodo last para el LinkedBinarySearchoTree
    //*********************************************************************
    //En los RBTree, es un caso parecido a los AVL
    //Funcionan de otra manera, porque en este caso tienen un "color" , pero al final el funcionamiento 
    //es el mismo, el valor mas alto se queda a la derecha, ya que se basa en BST
    //por lo que si nos piden el mas alto, solo tenemos que hacer un hasRight, hasta que ya
    //no existan mas nodos a la derecha y devolver la hoja que este más a la derecha
    public Position<E> last() {
        //private LinkedBinarySearchTree<RBInfo<E>> bst = new LinkedBinarySearchTree<>();
        //Tenemos bst, que utiliza LinkedBinarySearchTree, por lo que podemos llamar a la funcion
        //last() que hemos creado primero en la clase linkedBinarySearchTree
        //Se guarda en el position
        Position<E> nodo = bst.last().getElement();
        //Se devuelve
        return nodo;
    }

	@Override
	public Iterable<Position<E>> successors(Position<E> pos) {
		LinkedBinarySearchTree<RBInfo<E>> copyBst=this.bst;
		ArrayList<Position<E>> l = new ArrayList<>();
		if (checkPosition(pos)){
			while(copyBst.last()!=pos){
				l.add((Position<E>) copyBst.last());
				copyBst.remove(copyBst.last());
			}
		}
		return l;
	}

	@Override
	public Iterable<Position<E>> predecessors(Position<E> pos) {
		Iterator<Position<RBTree<E>.RBInfo<E>>> it = bst.iterator();
		ArrayList<Position<E>> l = new ArrayList<>();
		checkPosition(pos);		
		while(it.next()!=pos && it.hasNext()){
			Position<E> p = (Position<E>) it.next();
			l.add(p);
		}
		return l;
	}
	
	private boolean checkPosition(Position<E> p) throws IllegalStateException{
		if (p == null || !(p instanceof RBInfo))
			throw new IllegalStateException("The position is invalid");
		return true;
	}

}
