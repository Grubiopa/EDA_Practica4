package material.tree.binarytree;

import java.util.Iterator;

import material.tree.Position;

public class ArrayBinaryTree<E> implements BinaryTree<E> {

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position<E> root() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> parent(Position<E> v) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v) throws IllegalStateException {
		// TODO Auto-generated method stub
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

}
