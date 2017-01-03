

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import material.exercises.TreeDirectory;
import material.exercises.netflix.Movie;

public class prueba {

	public static void main(String[] args) throws NumberFormatException, IOException {
		String film;
		Map<String, Movie> films = new HashMap<>();
		File file = new File("C:/Users/gabri/Documents/netflix.txt");	
		FileReader f = new FileReader(file);
		BufferedReader b = new BufferedReader(f);
		while((film = b.readLine())!=null){
			String[] filmDivide = film.split(" - ");
			String[] StringDivde= filmDivide[3].split(",");
			List<String> Strings = new ArrayList<>();
			for(int i=0;i<StringDivde.length;i++){
				String splitTitle = String.valueOf(StringDivde[i]).replace(" ", "");
				splitTitle = splitTitle.replace("[", "");
				splitTitle = splitTitle.replace("]", "");
				Strings.add(String.valueOf(splitTitle));
			}
			Double points = (Double.parseDouble(filmDivide[2]));
			Movie movie = new Movie(filmDivide[0], Integer.parseInt(filmDivide[1]), points, Strings);
			films.put(movie.getTitle(), movie);
		}
		b.close();
	}

}
