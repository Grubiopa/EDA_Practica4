package test.material.tree.generictree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import material.tree.Position;
import material.tree.generic.LCRSTree;
import material.tree.generic.LinkedTree;


public class LinkedTreeTest {
	
	private LCRSTree<String> tree = new LCRSTree<>();
	
	public void setTree() {

		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);

		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);

		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);

		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRootExc() {
		tree.root();
	}

	@Test
	public void testSize() {
		Position<String> p = this.tree.addRoot("+");
		this.tree.add("2", p);
		Position<String> h = this.tree.add("*", p);
		this.tree.add("3", h);
		this.tree.add("5", h);
		assertEquals(this.tree.size(), 5);
	}
	
	@Test
	public void testSize2() {
		this.setTree();
		assertEquals(this.tree.size(), 12);
	}

	@Test
	public void testIsEmpty() {
		assertEquals(this.tree.isEmpty(), true);
	}
	
	@Test
	public void testIsEmpty2() {
		Position<String> p = this.tree.addRoot("B");
		this.tree.add("C", p);
		assertEquals(this.tree.isEmpty(), false);
	}

	@Test
	public void testRoot() {
		this.setTree();
		assertEquals(this.tree.root().getElement(), "A");
	}

	@Test
	public void testParent() {
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);
		assertEquals(p2, tree.parent(p3));
	}
	
	@Test
	public void testPositions() {
		Position<String> p = this.tree.addRoot("+");
		this.tree.add("2", p);
		this.tree.add("3", p);
		String salida = "";
		for (Position<String> e : this.tree) {
			salida += e.getElement();
		}
		assertEquals(salida, "+23");
	}

	@Test
	public void testAddN() {
		Position<String> p = this.tree.addRoot("R");
		this.tree.add("A", p);
		this.tree.add("C", p);
		this.tree.add("B", p, 1);
		Iterable<? extends Position<String>> children = this.tree.children(p);
		String output = "";
		for (Position<String> child : children)
			output += child.getElement();
		assertEquals(output, "ABC");

	}
	
	@Test
	public void testRemove() {
		LinkedTree<String> t = new LinkedTree<>();
		Position<String> a = t.addRoot("A");
		Position<String> b = t.add("B", a);
		Position<String> c = t.add("C", a);
		Position<String> d = t.add("D", b);
		Position<String> e = t.add("E", d);
		Position<String> f = t.add("F", d);
		Position<String> g = t.add("G", c);
		Position<String> h = t.add("H", c);
		
		t.remove(b);
		assertEquals(7, t.size());
		int count = 0;
		for (Position<String> p : t.children(a)) {
			String elem = p.getElement();
			assertTrue(elem.equals("D") || elem.equals("C"));
			count++;
		}		
		assertEquals(count, 2);
		assertEquals(t.parent(d), a);
	}
	
	@Test
	public void testRemoveRoot() {
		LinkedTree<String> t = new LinkedTree<>();
		Position<String> a = t.addRoot("A");
		Position<String> b = t.add("B", a);
		Position<String> d = t.add("D", b);
		Position<String> e = t.add("E", d);
		Position<String> f = t.add("F", d);
		
		t.remove(a);
		assertEquals(4, t.size());
		assertEquals(t.root(), b);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testRemoveExcept() {
		LinkedTree<String> t = new LinkedTree<>();
		Position<String> a = t.addRoot("A");
		Position<String> b = t.add("B", a);
		Position<String> c = t.add("C", a);
		Position<String> d = t.add("D", b);
		Position<String> e = t.add("E", d);
		Position<String> f = t.add("F", d);
		Position<String> g = t.add("G", c);
		Position<String> h = t.add("H", c);
		t.remove(c);
	}

	@Test
	public void testRemove2() {
		this.setTree();
		this.tree.removeSubtree(this.tree.root());
		assertEquals(this.tree.size(), 0);
	}

	@Test
	public void testRemove3() {
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);

		this.tree.removeSubtree(p2);

		StringBuilder s = new StringBuilder();
		for (Position<String> pos : this.tree) {
			s.append(pos.getElement());
		}
		assertEquals(s.toString(), "ABCDE");
	}

	@Test
	public void testGetUnmodifiableChildren() {
		Position<String> p = this.tree.addRoot("+");
		this.tree.add("2", p);
		this.tree.add("3", p);
		Iterable<? extends Position<String>> l = this.tree.children(p);
		try {
			l.iterator().remove();
			fail("The children collection has been modified");
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetChildren() {
		Position<String> p = this.tree.addRoot("+");
		this.tree.add("2", p);
		this.tree.add("3", p);

		String salida = "";
		for (Position<String> e : this.tree.children(p)) {
			salida += e.getElement();
		}
		assertEquals(salida, "23");
	}

	@Test
	public void testGetChildren2() {
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);

		String salida = "";
		for (Position<String> e : this.tree.children(p3)) {
			salida += e.getElement();
		}
		assertEquals(salida, "IJKL");
	}

	@Test
	public void testIterator() {
		this.setTree();

		StringBuilder s = new StringBuilder();
		for (Position<String> pos : this.tree) {
			s.append(pos.getElement());
		}
		assertEquals(s.toString(), "ABCDEFGHIJKL");
	}
	
	@Test
	public void testIsRoot() {
		this.setTree();
		assertEquals(this.tree.root().getElement(), "A");

	}

	@Test
	public void testIsRoot2() {
		try {
			this.tree.isRoot(null);
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSwapElements() {
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);

		this.tree.swapElements(p, p1);
		this.tree.swapElements(p2, p3);

		String salida = "";
		for (Position<String> e : this.tree) {
			salida += e.getElement();
		}
		assertEquals(salida, "CBADEHGFIJKL");
	}
	
	@Test
	public void testReplace() {		
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);

		this.tree.replace(p, "X");
		this.tree.replace(p1, "Y");
		this.tree.replace(p2, "Z");
		this.tree.replace(p3, "W");

		String salida = "";
		for (Position<String> e : this.tree) {
			salida += e.getElement();
		}
		assertEquals(salida, "XBYDEZGWIJKL");
	}
	

	public void testParent2() {
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);
		assertEquals(p2, tree.parent(p3));
	}

	public void testParent3() {
		this.setTree();

		try {
			this.tree.parent(null);
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}


	
	
	public void testAddN2() {
		Position<String> p = this.tree.addRoot("R");
		this.tree.add("A", p);
		this.tree.add("D", p);
		this.tree.add("F", p);
		this.tree.add("B", p, 1);
		this.tree.add("H", p);
		this.tree.add("E", p, 3);
		this.tree.add("I", p);
		this.tree.add("G", p, 5);
		this.tree.add("C", p, 2);
		Iterable<? extends Position<String>> children = this.tree.children(p);
		String output = "";
		for (Position<String> child : children)
			output += child.getElement();
		assertEquals(output, "ABCDEFGHI");

	}
	

	public void testFrontIterator() {
		this.setTree();
		StringBuilder s = new StringBuilder();
		for (Position<String> pos : this.tree) {
			s.append(pos.getElement());
		}
		assertEquals(s.toString(), "BEG");
	}

		
	@Test(expected=IllegalStateException.class)
	public void testMove(){
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		Position<String> p4 = tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);
		tree.moveSubtree(p3, p4);
		
	}

	
	public void testMove2(){
		Position<String> p = tree.addRoot("A");
		tree.add("B", p);
		Position<String> p1 = tree.add("C", p);
		tree.add("D", p);
		tree.add("E", p1);
		Position<String> p2 = tree.add("F", p1);
		tree.add("G", p2);
		Position<String> p3 = tree.add("H", p2);
		tree.add("I", p3);
		tree.add("J", p3);
		tree.add("K", p3);
		tree.add("L", p3);
		tree.moveSubtree(p3, p);
		String salida2 = "";
		for (Position<String> e : this.tree) {
			salida2 += e.getElement();
		}
		assertEquals(salida2, "ABCDHEFIJKLG");
	}


	

}
