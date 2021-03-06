package material.exercises;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import material.tree.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;
import material.tree.iterators.FrontIterator;
import material.tree.iterators.TreeIterator;

public class Huffman {
	
	
	private class Element{
		private char letra;
		private int frec;
		public Element(char letra, int frec) {
			this.letra = letra;
			this.frec = frec;
		}
		public Element(int frec) {
			this.frec = frec;
		}
		public char getLetra() {
			return letra;
		}
		public void setLetra(char letra) {
			this.letra = letra;
		}
		public int getFrec() {
			return frec;
		}
		public void setFrec(int frec) {
			this.frec = frec;
		}
		
	}
	ArrayList<BinaryTree<Element>> listaElement;
	CompararFrecuencia c = new CompararFrecuencia();
	
	public Huffman(String sentence) {
		listaElement = new ArrayList<>(); //SOLO PUEDE HABER 27 Elementos diferentes
		int j;
		boolean encontrado = false;
		for(int i=0;i<sentence.length();i++){
			j = 0;
			while(!encontrado||j<listaElement.size()){
				BinaryTree<Element> ar = listaElement.get(j);
				Element e = ar.root().getElement();
				if(e.getLetra()==sentence.charAt(i)){
					e.setFrec(e.getFrec() + 1);
					encontrado= true;
				}
				j++;
			}
			if (!encontrado){
				BinaryTree<Element> binaryTree = new LinkedBinaryTree<>();
				binaryTree.addRoot(new Element(sentence.charAt(i),1));
				listaElement.add(binaryTree);
			}
		}
		ordenar();
		analysis();
	}
	
	class CompararFrecuencia implements Comparator{

		@Override
		public int compare(Object arg0, Object arg1) {
			int frecu1 = ((BinaryTree<Element>)arg0).root().getElement().getFrec();
			int frecu2 = ((BinaryTree<Element>)arg1).root().getElement().getFrec();
			return frecu1 > frecu2 ?-1:1;
		}
		
	}
	
	private void ordenar(){
		listaElement.sort(c);
	}
	
	private void analysis() {
		while(listaElement.size() >1){
			LinkedBinaryTree<Element> newTree = new LinkedBinaryTree<>();
			int newFrec = listaElement.get(0).root().getElement().getFrec() 
					+ listaElement.get(1).root().getElement().getFrec();
			newTree.addRoot(new Element(newFrec));
			newTree.attach(newTree.root(), listaElement.get(0), listaElement.get(1));
			listaElement.remove(0);
			listaElement.remove(1);
			listaElement.add(newTree);
			ordenar();
		}
	}
	
	public String encode(char c) {
		String sol = "";
		FrontIterator<Element> it = new FrontIterator<>(listaElement.get(0));
		while(it.hasNext()|| sol==""){
			Position<Element> p = it.next();
			sol = advanceEnconde(c, p);
		}
		if(sol=="")
			throw new IllegalStateException();
		else
			return sol;
	}
	
	private String advanceEnconde(char c,Position<Element> p){
		Position<Element> parent = listaElement.get(0).parent(p);
		String auxsol="";
		if(parent!=null){
			if(listaElement.get(0).right(parent)==p){
				if (p.getElement().getLetra()== c)
					return "1";
				else{
					auxsol= advanceEnconde(c, parent);
					if(auxsol=="")
						return "";
					else
						return "1" + auxsol;
				}
			}else{
				if (p.getElement().getLetra()== c)
					return "0";
				else{
					auxsol= advanceEnconde(c, parent);
					if(auxsol=="")
						return "";
					else
						return "0" + auxsol;
				}
			}
		}else{
			return "";
		}		
	}
	
}
