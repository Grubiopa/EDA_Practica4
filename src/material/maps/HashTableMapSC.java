package material.maps;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Random;







/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 */
public class HashTableMapSC<K, V> implements Map<K, V> {
	
	protected int nOcupados;
	protected int prime, capacity;
	// MAD ((Scale*key + shift) map prime) mod capacity
	protected long scale,shift;
	private final Entry<K,V> AVAILABLE = new HashEntry<>(null,null);
	protected ArrayList<HashEntry<K,V>>[] map;

    private class HashEntry<T, U> implements Entry<T, U> {
		private T key;
		private U value;
		
		public T getKey() {
			return key;
		}
		public U getValue() {
			return value;
		}
		public HashEntry(T key, U value) {
			this.key = key;
			this.value = value;
		}
		
		public boolean equals(Object O){
			if (O.getClass()!=this.getClass()){
				return false;
			}else{
				HashEntry<K,V> ent = (HashEntry<K,V>) O;
				return (this.getKey().equals(ent.getKey()) && this.getValue().equals(ent.getValue()));
			}
		}
        
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
    	
    	private ArrayList<HashEntry<T, U>>[] map;
    	private int numElems;
    	private Queue<HashEntry<T, U>> q;
    	private int index;
        //Ejercicio 2.2
        public HashTableMapIterator(ArrayList<HashEntry<T, U>>[] map, int numElems) {
            this.map = map;
            this.numElems= numElems;
            q = new ArrayDeque<>();
            int i=0;
            boolean first=false;
            while(!first && numElems!=0 && i<map.length){
            	if(map[i]!=null){
            		for(Entry<T, U> e: map[i])
            			q.add((HashTableMapSC<K, V>.HashEntry<T, U>) e);
            		first=true;
            	}
            	i++;
            }
            index=i-1;
        }

        private void goToNextElement() {
            index++;
        }

        @Override
        public boolean hasNext() {
        	boolean hasNext = q.size()>0;
        	if(index<map.length-1 && !hasNext && numElems>0){
        		while(index < map.length && !hasNext){
        			index++;
        			hasNext=map[index]!=null;
        		}        			
        	}
        	return hasNext;
        }

        @Override
        public Entry<T, U> next() {
           Entry<T, U> e = q.poll();
           if(e==null){
         		for(Entry<T, U> e2: map[index])
        			q.add((HashTableMapSC<K, V>.HashEntry<T, U>) e2);
         		e= q.poll();
           }
           numElems--;
           return e;
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {

    	private HashTableMapIterator<T,U> it;
    	
        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
        	this.it=it;
        }

        @Override
        public T next() {
            HashEntry<T, U> he = (HashEntry<T, U>) it.next();
            return he.getKey();
        }

        @Override
        public boolean hasNext() {
           return it.hasNext();
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {
    	
    	private HashTableMapIterator<T,U> it;
    	
        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
        	this.it=it;
        }

        @Override
        public U next() {
            HashEntry<T, U> he = (HashEntry<T, U>) it.next();
            return he.getValue();
        }

        @Override
        public boolean hasNext() {
           return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    /**
     * Creates a hash table
     */
    public HashTableMapSC() {
    	this.nOcupados = 0;
    	this.prime = 1;
    	this.capacity = 10;
    	Random rand = new Random();
    	this.scale = rand.nextInt(this.prime-1)+1;
    	this.shift = rand.nextInt(this.prime);
    	this.map = (ArrayList<HashTableMapSC<K, V>.HashEntry<K, V>>[]) new Object [capacity];
    }

    /**
     * Creates a hash table.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
     	this.nOcupados = 0;
    	this.prime = 1;
    	this.capacity = cap;    
    	Random rand = new Random();
    	this.scale = rand.nextInt(this.prime-1)+1;
    	this.shift = rand.nextInt(this.prime);
    	this.map = (ArrayList<HashTableMapSC<K, V>.HashEntry<K, V>>[]) new Object [capacity];
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
    	this.nOcupados = 0;
		this.prime = p;
		this.capacity = cap;
		Random rand = new Random();
		this.scale = rand.nextInt(this.prime-1)+1;
		this.shift = rand.nextInt(this.prime);
		this.map = (ArrayList<HashTableMapSC<K, V>.HashEntry<K, V>>[]) new Object [capacity];
    }

    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {
    	return (int)((scale*key.hashCode()+shift)% prime)%capacity;
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
       return nOcupados;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
        return nOcupados==0;
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) throws IllegalStateException {
    	HashEntryIndex i= findEntry(key);
		if (!i.isFound()) {
			return null;
		}
		for(Entry<K,V> e : map[i.getIndex()]){
			if(e.getKey().equals(key)){
				return e.getValue();
			}
		}
		return null;
    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws IllegalStateException {
        if(nOcupados>=capacity/2)
        	rehash(capacity*2);
        int code = hashValue(key);
        ArrayList<HashTableMapSC<K, V>.HashEntry<K, V>> l = map[code];
        map[code].add(new HashEntry<K, V>(key, value));
        return value;
    }

    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws IllegalStateException {       
    	checkKey(key);
        int code = hashValue(key);
        ArrayList<HashTableMapSC<K, V>.HashEntry<K, V>> l = map[code];
        int i=0;
        for(Entry<K, V> e : l){
        	if(e.getKey().equals(key)){
        		V value =  e.getValue();
        		map[code].remove(i);
        		return value;
        	}
        	i++;	
        }
        throw new IllegalStateException();
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<K, V>(map,map.length);
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
     	ArrayList<K> l = new ArrayList<>();
    	for(int i = 0;i<this.capacity;i++)
    		if(map[i]!=null)
		    	for(HashEntry<K,V> e : map[i]){
		    		l.add(e.getKey());
		    	}
    	return l;
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
     	ArrayList<V> l = new ArrayList<>();
    	for(int i = 0;i<this.capacity;i++)
    		if(map[i]!=null)
		    	for(HashEntry<K,V> e : map[i]){
		    		l.add(e.getValue());
		    	}
    	return l;
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
    	ArrayList<Entry<K, V>> l = new ArrayList<>();
    	for(int i = 0;i<this.capacity;i++)
    		if(map[i]!=null)
		    	for(HashEntry<K,V> e : map[i]){
		    		l.add(e);
		    	}
    	return l;
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
    	if(k ==null)
    		throw new IllegalStateException();
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     */
    protected void rehash(int newCap) {
    	HashTableMapSC<K, V> map2 =  new HashTableMapSC<K, V>();
    	
    	for(int i = 0;i<this.capacity;i++)
    		if(map[i]!=null)
		    	for(HashEntry<K,V> e : map[i]){
		    		map2.put(e.getKey(),e.getValue());
		    	}
    	capacity=newCap;
    }
    
    private class HashEntryIndex{
		private int index;
		private boolean found;
		public HashEntryIndex(int i, boolean b) {
			index = i;
			found = b;  
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public boolean isFound() {
			return found;
		}
		public void setFound(boolean found) {
			this.found = found;
		}
		
	}
	
    private HashEntryIndex findEntry(K key) throws IllegalStateException{
		checkKey(key);
		int i = hashValue(key);
		int avail = -1;
		final int j = i;
		Entry<K, V> entry = new HashEntry<K, V>(key, null);
		do{
			List<HashTableMapSC<K, V>.HashEntry<K, V>> e = this.map[i];
			if(e==null){
				if(avail == -1){
					avail = i;
				}
				break;
			}else if (e.contains(entry.getKey())){
				return new HashEntryIndex(i,true);
			}else if(e == this.AVAILABLE){
				if (avail == -1){
					avail = i;
				}
			}else{
				i = (i+1)%capacity;
			}			
			
		}while(i!=j);
		return new HashEntryIndex(avail,false);
	}
}
