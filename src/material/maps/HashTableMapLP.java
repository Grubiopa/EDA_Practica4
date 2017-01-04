package material.maps;

public class HashTableMapLP<K,V> extends AbstractHashTableMap<K, V> {

	protected int offset(K key, int i) {
		return 1;
	};
}
