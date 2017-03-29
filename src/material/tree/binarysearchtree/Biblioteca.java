package material.tree.binarysearchtree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import material.tree.Position;

public class Biblioteca {
	
	private LinkedBinarySearchTree<String> stopWords;
	private LinkedBinarySearchTree<String> library;
	
	public Biblioteca(String library, String stopWords) throws IOException{
		cargarStopWords(stopWords);
		cargarLibrary(library);
	}
	private void cargarStopWords(String stopWords) throws IOException{
		String word;
		stopWords=stopWords.replace('\\','/');
		File file = new File(stopWords);
		FileReader f = new FileReader(file);
		BufferedReader b = new BufferedReader(f);
		while((word = b.readLine())!=null){
			this.stopWords.insert(word);
		}
	}
	
	private void cargarLibrary(String text) throws IOException{
		String line;
		String[] wordLine;
		text=text.replace('\\','/');
		File file = new File(text);
		FileReader f = new FileReader(file);
		BufferedReader b = new BufferedReader(f);
		while((line = b.readLine())!=null){
			if(line!="--------"){
				wordLine= line.split(" ");
				for(int i=0;i<wordLine.length;i++){
					boolean introduce = false;
					int j = 0;
					while(!introduce && j <wordLine[i].length()){
						String l = wordLine[i].substring(j, j+1);
						if(l.matches("[A-Z]")){
							if(comprobarpalabra(wordLine[i])){
								library.insert(wordLine[i]);
							}
							introduce=true;
						}
					}
				}
			}
		}
	}
	
	private boolean comprobarpalabra(String word){
		Position<String> p = stopWords.find(word);
		return p == null;
	}

}
