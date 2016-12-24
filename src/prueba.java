

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import material.exercises.TreeDirectory;

public class prueba {

	public static void main(String[] args) {
		ArrayList l = new ArrayList<>();
		for (int i = 0; i<5;i++){
			l.add(i);
		}
		while(l.size()>0){
			System.out.println(l.get(0));
			l.remove(0);
		}
	}

}
