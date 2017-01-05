package material.exercises.netflix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import material.exercises.netflix.Movie.String;


public class Netflix {
	
	Map<String, Movie> films;
	public Netflix(String data) throws IOException {
		String film;
		films = new HashMap<>();
		data.replace('\\','/');
		File file = new File(data);
		FileReader f = new FileReader(file);
		BufferedReader b = new BufferedReader(f);
		while((film = b.readLine())!=null){
			String[] filmDivide = film.split(" - ");
			String[] StringDivde= filmDivide[3].split(",");
			List<String> Strings = new ArrayList<>();
			for(int i=0;i<StringDivde.length;i++){
				String splitTitle = String.valueOf(StringDivde[i]).replace(" ", "");
				splitTitle= splitTitle.replace("[", "");
				splitTitle=splitTitle.replace("]", "");
				Strings.add(String.valueOf(splitTitle));
			}
			Double points = (Double.parseDouble(filmDivide[2]));
			Movie movie = new Movie(filmDivide[0], Integer.parseInt(filmDivide[1]), points, Strings);
			films.put(movie.getTitle(), movie);
		}
		b.close();
	}
	
	public Movie findTitle(String title) {
		return films.get(title);
	}
	
	public Set<Movie> findYear(int year) {
		Set<Movie> sol = new HashSet<>();
		for(Map.Entry<String,Movie> m:films.entrySet()){
			if(m.getValue().getYear()==year)
				sol.add(m.getValue());
		}
		return sol;
	}
	
	public Set<Movie> findScore(float minScore, float maxScore) {
		Set<Movie> sol = new HashSet<>();
		for(Map.Entry<String,Movie> m:films.entrySet()){
			if( m.getValue().getScore()>=minScore &&
					m.getValue().getScore()<=maxScore)
				sol.add(m.getValue());
		}
		return sol;
	}
	
	public Set<Movie> findType(String type) {
		Set<Movie> sol = new HashSet<>();
		for(Map.Entry<String,Movie> m:films.entrySet()){
			List<String> l = m.getValue().getGender();
			if(l.contains(type))
				sol.add(m.getValue());
		}
		return sol;
	}
	
	public Set<Movie> findType(List<String> type) {
		Set<Movie> sol = new HashSet<>();
		for(Map.Entry<String,Movie> m:films.entrySet()){
			List<String> l = m.getValue().getGender();
			boolean add = false;
			for(String g :type ){				
				if(l.contains(g)&&!add){
					sol.add(m.getValue());
					add = true;
				}
			}
		}
		return sol;
	}
	
	public void addScore(String title, float score) {
		Movie m = findTitle(title);
		double media = (m.getScore() + (double) score)/2; 
		m.setPoints(media);
		
	}
	
}
