package material.exercises.netflix;

import java.io.BufferedReader;
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
		FileReader f = new FileReader(data);
		BufferedReader b = new BufferedReader(f);
		while((film = b.readLine())!=null){
			String[] filmDivide = film.split(";");
			String[] StringDivde= filmDivide[3].split(",");
			List<String> Strings = new ArrayList<>();
			for(int i=0;i<StringDivde.length;i++){
				Strings.add(String.valueOf(StringDivde[i]));
			}
			Puntuacion points = new Puntuacion(Float.parseFloat(filmDivide[2]));
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
			if(m.getValue().getPoints().getValue()>=minScore &&
					m.getValue().getPoints().getValue()<=maxScore)
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
			for(String g :type){
				boolean add = false;
				if(l.contains(g)&&!add){
					sol.add(m.getValue());
					add = true;
				}
			}
		}
		return sol;
	}
	
	public void addScore(String title, float score) {
		for(Map.Entry<String,Movie> m:films.entrySet()){
			if(m.getValue().getTitle()==title)
				m.getValue().setPoints(new Puntuacion(score));
		}
	}
	
}
