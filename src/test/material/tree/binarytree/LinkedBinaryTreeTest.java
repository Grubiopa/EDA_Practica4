package test.material.tree.binarytree;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import material.tree.Position;
import material.tree.binarytree.ArrayBinaryTree;
import material.tree.binarytree.LinkedBinaryTree;

public class LinkedBinaryTreeTest {
	
	private ArrayBinaryTree<String> tree = new ArrayBinaryTree<>();
	
	public void setTree() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
	}

	@Test
	public void testSize() {
		assertEquals(0, tree.size());
		setTree();
		assertEquals(7, tree.size());
	}

	@Test
	public void testIsEmpty() {
		assertTrue(tree.isEmpty());
		setTree();
		assertFalse(tree.isEmpty());
	}

	@Test
	public void testIsInternal() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertTrue(tree.isInternal(a));
		assertTrue(tree.isInternal(d));
		assertFalse(tree.isInternal(e));
	}

	@Test
	public void testIsLeaf() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertFalse(tree.isLeaf(a));
		assertFalse(tree.isLeaf(d));
		assertTrue(tree.isLeaf(e));
	}

	@Test
	public void testIsRoot() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertTrue(tree.isRoot(a));
		assertFalse(tree.isRoot(e));
	}

	@Test
	public void testHasLeft() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertTrue(tree.hasLeft(a));
		assertTrue(tree.hasLeft(d));
		assertFalse(tree.hasLeft(f));
	}

	@Test
	public void testHasRight() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertTrue(tree.hasRight(a));
		assertTrue(tree.hasRight(d));
		assertFalse(tree.hasRight(c));
	}

	@Test
	public void testRoot() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertEquals(a,tree.root());
		assertNotEquals(b, tree.root());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRootExc() {
		tree.root();
	}

	@Test
	public void testLeft() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertEquals(b, tree.left(a));
		assertNotEquals(f, tree.left(d));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testLeftExc() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.left(f);
	}

	@Test
	public void testRight() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertEquals(c, tree.right(a));
		assertNotEquals(b, tree.right(a));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRightExc() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.right(b);
	}

	@Test
	public void testParent() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertEquals(a, tree.parent(b));
		assertNotEquals(a, tree.parent(f));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testParentExc() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.parent(a);
	}

	@Test
	public void testChildren() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		Set<Position<String>> expected = new HashSet<>();
		expected.add(b);
		expected.add(c);
		for (Position<String> child : tree.children(a)) {
			assertTrue(expected.contains(child));
			expected.remove(child);
		}
		assertTrue(expected.isEmpty());
	}

	@Test
	public void testIterator() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		String expected = "ABCDGEF";
		String found = "";
		for (Position<String> p : tree) {
			found += p.getElement();
		}
		assertEquals(expected, found);
	}

	@Test
	public void testReplace() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.replace(b, "NEW");
		assertEquals("NEW", b.getElement());
	}

	@Test
	public void testSibling() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		assertEquals(b, tree.sibling(c));
		assertEquals(f, tree.sibling(e));
	}

	@Test
	public void testAddRoot() {
		Position<String> a = tree.addRoot("A");
		assertEquals(a, tree.root());
	}

	@Test
	public void testRemove() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.remove(b);
		assertEquals(d, tree.left(a));
	}

	@Test
	public void testSwapElements() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.swapElements(d, g);
		assertEquals(d.getElement(), "G");
		assertEquals(g.getElement(), "D");
	}


	@Test
	public void testRemoveSubtree() {
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertLeft(d, "E");
		Position<String> f = tree.insertRight(d, "F");
		Position<String> g = tree.insertLeft(c, "G");
		tree.removeSubtree(b);
		assertFalse(tree.hasLeft(a));
	}

}
