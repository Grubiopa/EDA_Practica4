package test.material.tree.generictree;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import material.exercises.TreeDirectory;

public class TreeDirectoryTest {
	
	private TreeDirectory treeDir;
	
	public void setDir() {
		treeDir = new TreeDirectory("C:/Users/gabri/Documents");
	}

	@Test
	public void testGetTreeDirectory() {
		setDir();
		String result = treeDir.getTreeDirectory();
		String expected = "direjemplo\n"+
				"\tnetflix\n" +
				"\t\tnetflix.txt\n" +
				"\t\tPractica 4 - Mapas.pdf\n" +
				"\tPractica 2 - Arboles n-arios.pdf\n" +
				"\tPractica 3 - Arboles binarios.pdf";
		assertEquals(expected, result);
	}
	
	@Test
	public void testGetSize() {
		/*
		 * En nuestro pc, los ficheros ocupan:
		 * Practica 3 - Arboles binarios -> 556 KB
		 * Practica 2 - Arboles n-arios -> 144 KB
		 * Practica 4 - Mapas.pdf -> 416 KB
		 * netflix.txt -> 3 KB
		 */
		//setDir();
		int kbytes = (int)treeDir.getSize("C:/Users/gabri/Documents");
		assertEquals(13, kbytes);
		//kbytes = (int)treeDir.getSize("./direjemplo");
		//assertEquals(1119, kbytes);
	}
	
	@Test
	public void testFindExtension() {
		setDir();
		List<String> files = treeDir.findExtension(".pdf");
		List<String> expected = Arrays.asList("Practica 2 - Arboles n-arios.pdf", "Practica 3 - Arboles binarios.pdf", "Practica 4 - Mapas.pdf");
		assertEquals(expected.size(), files.size());
		for (String file : files) {
			assertTrue(expected.contains(file));
		}
		
	}

}
