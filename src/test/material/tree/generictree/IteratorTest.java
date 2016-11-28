package test.material.tree.generictree;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import material.tree.Position;
import material.tree.generic.LinkedTree;
import material.tree.generic.LCRSTree;
import material.tree.iterators.FrontIterator;
import material.tree.iterators.PreorderIterator;

public class IteratorTest {
	
	private LCRSTree<String> tree;
	
	public void setTree() {
		tree = new LCRSTree<>();
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.add("B", a);
		Position<String> c = tree.add("C", a);
		Position<String> d = tree.add("D", a);
		Position<String> e = tree.add("E", b);
		Position<String> f = tree.add("F", c);
		Position<String> g = tree.add("G", c);
	}

	@Test
	public void testPreorder() {
		setTree();
		Iterator<Position<String>> it = new PreorderIterator<>(tree);
		String result = "";
		while (it.hasNext()) {
			result += it.next().getElement();
		}
		assertEquals("ABECFGD",result);
	}
	
	@Test
	public void testFront() {
		setTree();
		Iterator<Position<String>> it = new FrontIterator<>(tree);
		String result = "";
		while (it.hasNext()) {
			result += it.next().getElement();
		}
		assertEquals("DEFG",result);
	}

}
