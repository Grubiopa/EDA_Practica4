package material.maps;

public class HashTableMapQP <K,V> extends AbstractHashTableMap<K, V> {
	
	private int c1,c2;
	
	protected int offset(K key, int i) {
		
		return i*(key.hashCode()+ c1*i + c2*(i*i)) ;
	}

	public HashTableMapQP(int c1, int c2) {
		super();
		this.c1 = c1;
		this.c2= c1 !=0 ? c2:1;
	};
}
