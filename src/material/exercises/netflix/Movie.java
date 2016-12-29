package material.exercises.netflix;

import java.util.List;

public class Movie {

	
	
	//public enum Gender {accion,cienciaficcion,comedia,documental,drama,española,europea,familiar,premiada,romantica,terror,thriller};
	
	private String title;
	private int year;
	private Puntuacion points;
	private List<String> gender;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Puntuacion getPoints() {
		return points;
	}
	public void setPoints(Puntuacion points) {
		this.points = points;
	}
	public List<String> getGender() {
		return gender;
	}
	public void setGender(List<String> gender) {
		this.gender = gender;
	}
	public Movie(String title, int year, Puntuacion points, List<String> gender) {
		this.title = title;
		this.year = year;
		this.points = points;
		this.gender = gender;
	}
	
	
}

