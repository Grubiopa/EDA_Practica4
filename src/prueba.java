

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import material.exercises.TreeDirectory;

public class prueba {

	public static void main(String[] args) {
		String palabra = "hola";
		char[] palbraChar = new char[palabra.length()];
		palabra.getChars(0, palabra.length(), palbraChar, 0);
		for(char le : palbraChar){
			System.out.println(le);
		}
		
	}

}
