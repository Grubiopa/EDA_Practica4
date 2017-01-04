package material.maps;

public class HashTableMapDH<K,V> extends AbstractHashTableMap<K, V> {
	
	private int prime;
	
	protected int offset(K key, int i) {
		
		return i*(prime-(key.hashCode()%prime)) ;
	}

	public HashTableMapDH(int prime) {
		super();
		this.prime = prime;
	};
	
	
}
