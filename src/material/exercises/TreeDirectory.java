package material.exercises;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import material.tree.Position;
import material.tree.Tree;
import material.tree.generic.LinkedTree;
import material.tree.iterators.TreeIterator;

public class TreeDirectory {

	private LinkedTree<String> tree;
	
	public TreeDirectory(String s){
		s.replace('\\','/');
		tree = new LinkedTree<String>();
		tree.addRoot(s);
		getChild(s);
	}
	
	private void getChild(String s){
		File archivo = new File (s);
		Position<String> pos = findString(s);
		for (String elem : archivo.list()){
			String completo = s +"/"+elem;
			File f = new File(completo);
			tree.add(completo, pos);
			if (f.isDirectory())
				getChild(completo);		
		}
	}
	public String getTreeDirectory() {
		//Se me ocurre utilizar una pila para ir almacenando pero no consigo saber exactamente por el nivel x el que voy
		String solution=((tree.root()).getElement())+"\n";
		solution= recursiveDirectory(1,tree.root(),tree.root().getElement()+"\n");
		return solution;
	}
	
	private String recursiveDirectory(int cont, Position<String> p, String solucion){
		Iterable<? extends Position<String>> children = this.tree.children(p);
		for (Position<String> child : children){
			for(int i=0;i<cont;i++){
				solucion+="\t";
			}
			solucion+=child.getElement()+"\n";
 			solucion=recursiveDirectory(cont + 1, child, solucion);
 			//recursiveDirectory(cont + 1, child, solucion);
		}
		return solucion;
	}
	
	public double getSize(String s) {
		Position<String>stringPosition = findString(s);
		File fichero = new File(stringPosition.getElement());
		double value = 0;
		//Iterable<? extends Position<String>> children = this.tree.children(p);
		//Iterador y recorrer con next sumar las que sean hojas
		Iterator<Position<String>> it = new TreeIterator<String>(tree,stringPosition);
		while (it.hasNext()){
			Position<String> position = it.next();
			File f = new File(position.getElement());
			if(tree.isLeaf(position)){
				value+= f.getTotalSpace();
			}
			
		}
		return value;
	}
		
	private Position<String> findString(String s)throws IllegalStateException{
		Position<String> solucion=null;
		boolean found=false;
		Iterator<Position<String>> it = new TreeIterator<String>(tree);
		while (it.hasNext()&& !found){
			Position<String> position = it.next();
			found= position.getElement().equals(s);	
			solucion = (found)?position:null;
		}
		if(solucion==null){
			throw new IllegalStateException("No se encuentra");
		}else{
			return solucion;
		}
	}
	
	public List<String> findExtension(String ext) {
		List<String> l = new ArrayList<>();
		Pattern pat = Pattern.compile(".*."+ext);
		Iterator<Position<String>> it = new TreeIterator<String>(tree);
		while (it.hasNext()){
			Position<String> position = it.next();
			Matcher mat = pat.matcher(position.getElement());
			 if (mat.matches()) {
				 l.add(position.getElement());
			 }
		}
		return l;
	}
	
	
	
	
	
}
