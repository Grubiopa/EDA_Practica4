package test.material.maps;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

import material.exercises.netflix.Movie;
import material.exercises.netflix.Netflix;

public class NetflixTest {
	
	private Netflix netflix;
	
	public void setUpNetflix() throws IOException {
		netflix = new Netflix("C:/Users/gabri/Documents/netflix.txt");
	}

	@Test
	public void testFindTitle() throws IOException {
		setUpNetflix();
		Movie movie = netflix.findTitle("Venganza");
		assertEquals("Venganza", movie.getTitle());
	}

	@Test
	public void testFindYear() throws IOException {
		setUpNetflix();
		Set<Movie> movies2013 = netflix.findYear(2013);
		assertEquals(3, movies2013.size());
		for (Movie m : movies2013) {
			assertTrue(m.getTitle().equals("Los juegos del hambre: En llamas") || m.getTitle().equals("La Gran Familia Española")
					|| m.getTitle().equals("El Hombre de Acero"));
		}
	}

	@Test
	public void testFindScore() throws IOException {
		setUpNetflix();
		Set<Movie> movies = netflix.findScore(3, 4);
		assertEquals(3, movies.size());
		for (Movie m : movies) {
			assertTrue(m.getTitle().equals("Postdata: te quiero") 
					||m.getTitle().equals("El Hombre de Acero")
					|| m.getTitle().equals("Pacific Rim"));
		}
	}

	@Test
	public void testFindTypeString() throws IOException {
		setUpNetflix();
		Set<Movie> movies = netflix.findType("accion");
		assertEquals(5, movies.size());
		for (Movie m : movies) {
			assertTrue(m.getTitle().equals("Los juegos del hambre: En llamas") 
					|| m.getTitle().equals("Venganza")
					|| m.getTitle().equals("Beasts of no nation")
					|| m.getTitle().equals("El Hombre de Acero")
					|| m.getTitle().equals("Pacific Rim"));
		}
	}

	@Test
	public void testFindTypeListOfString() throws IOException {
		setUpNetflix();
		Set<Movie> movies = netflix.findType(Arrays.asList("accion", "thriller"));
		assertEquals(3, movies.size());
		for (Movie m : movies) {
			assertTrue(m.getTitle().equals("Los juegos del hambre: En llamas") 
					|| m.getTitle().equals("Venganza")
					|| m.getTitle().equals("Pacific Rim"));
		}
	}

	@Test
	public void testAddScore() throws IOException {
		setUpNetflix();
		netflix.addScore("Venganza", 5);
		netflix.addScore("Venganza", 2);
		netflix.addScore("Venganza", 1);
		assertEquals(3.0, netflix.findTitle("Venganza").getScore(), 0.1);
	}

}
