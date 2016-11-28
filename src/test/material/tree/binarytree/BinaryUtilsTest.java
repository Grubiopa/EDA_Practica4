package test.material.tree.binarytree;

import static org.junit.Assert.*;

import org.junit.Test;

import material.tree.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.BinaryTreeUtils;
import material.tree.binarytree.LinkedBinaryTree;

public class BinaryUtilsTest {
	
	private LinkedBinaryTree<String> tree;
	private BinaryTreeUtils<String> utils;
	
	public void setUpTree() {
		tree = new LinkedBinaryTree<>();
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertRight(b, "E");
		Position<String> f = tree.insertLeft(c, "F");
		utils = new BinaryTreeUtils<>(tree);
	}

	@Test
	public void testMirror() {
		setUpTree();
		BinaryTree<String> mirrored = utils.mirror();
		String result = "";
		for (Position<String> p : mirrored) {
			result += p.getElement();
		}
		assertEquals("ACBFED", result);
	}

	@Test
	public void testContains() {
		setUpTree();
		assertTrue(utils.contains("C"));
		assertFalse(utils.contains("X"));
	}

	@Test
	public void testLevel() {
		tree = new LinkedBinaryTree<>();
		Position<String> a = tree.addRoot("A");
		Position<String> b = tree.insertLeft(a, "B");
		Position<String> c = tree.insertRight(a, "C");
		Position<String> d = tree.insertLeft(b, "D");
		Position<String> e = tree.insertRight(b, "E");
		Position<String> f = tree.insertLeft(c, "F");
		utils = new BinaryTreeUtils<>(tree);
		assertEquals(0, utils.level(a));
		assertEquals(1, utils.level(c));
		assertEquals(2, utils.level(e));
	}

}
