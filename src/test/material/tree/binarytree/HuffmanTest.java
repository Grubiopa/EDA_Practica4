package test.material.tree.binarytree;

import static org.junit.Assert.*;

import org.junit.Test;

import material.exercises.Huffman;

public class HuffmanTest {

	@Test
	public void testEncode() {
		Huffman huffman = new Huffman("SUSIE SAYS IT EASY");
		assertEquals("010", huffman.encode('A'));
		assertEquals("1111", huffman.encode('E'));
	}

}
