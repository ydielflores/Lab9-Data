package priorityQueue;
import java.util.Comparator;

import priorityQueueInterfaces.*; 

/**
 * 
 * @author pedroirivera-vega
 *
 * @param <K> Type for the keys of the entries that will be stored
 * @param <V> Type for the values of the entries that will be stored
 */

public abstract class AbstractPriorityQueue<K,V> 
	implements DisplayablePriorityQueue<K,V>
{
	
	protected EntryComparator<K,V> entryCmp;  // the comparator object
	/**
	 * Notice that here we use a comparator of entries.
	 * Such a comparator is constructed based on a key comparator that is
	 * received as the parameter of one of the constructors, 
	 * or based on the default comparator of keys if the default constructor
	 * is used to instantiate the priority queue object. This provides
	 * flexibility in determining what constitutes "high priority", and
	 * allows to implement max-heaps or min-heaps without the need of
	 * making changes to the code (see class HeapPriorityQueue).
	 */
	
	/**
	 * This constructor receives a comparator that is capable of comparing keys. 
	 * Based on that comparator of keys, it construct the entry comparator that
	 * will be used in different operations of the class. 
	 * 
	 * @param cmp the key comparator that will be used
	 */
	protected AbstractPriorityQueue(Comparator<K> cmp) { 
		this.entryCmp = new EntryComparator<>(cmp); 
	}
	
	/**
	 * Default constructor. Under this constructor, the comparator object to
	 * compare keys is the default comparator (of type DefaultComparator<E>). 
	 * See that here the EntryComparator object is created using that default
	 * comparator (class included as an internal class at the end of this class)
	 */
	protected AbstractPriorityQueue() { 
		this(new DefaultComparator<K>()); 
	}
	
	/**
	 * Returns true if the queue is empty; otherwise, returns false. 
	 */
	public boolean isEmpty() { 
		return this.size() == 0; 
	}
	
	/**
	 * Internal method used to compare entries. 
	 * @param e1
	 * @param e2
	 * @return
	 */
	protected int compare(Entry<K, V> e1, Entry<K, V> e2) { 
		return entryCmp.compare(e1, e2);
	}

	protected boolean validate(K key) throws IllegalArgumentException { 
		if (key == null) throw new IllegalArgumentException("Key is null.");
		try { 
			return entryCmp.compare(new PQEntry<K,V>(key, null), new PQEntry<K,V>(key, null))==0; 
		}
		catch (ClassCastException e) { 
			throw new IllegalArgumentException("Key does not match comparator requirements.");
		}
	}
	
	///////////////////////////  Internal Classes /////////////////////////////
	/**
	 * Entry data type. Objects of this type hold a pair of values: key-value
	 * pair. 
	 *
	 * @param <K> data type of the key
	 * @param <V> data type of the value
	 */
	protected static class PQEntry<K, V> implements Entry<K, V> {

		private K key; 
		private V value; 
		
		public PQEntry(K key, V value) { 
			this.key = key; 
			this.value = value; 
		}
		
		@Override
		public K getKey() {
			// TODO Auto-generated method stub
			return key;
		}

		@Override
		public V getValue() {
			// TODO Auto-generated method stub
			return value;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public void setValue(V value) {
			this.value = value;
		} 
		
		public String toString() { 
			return "[" + key + ", " + value + "]"; 
		}
	}

	/**
	 * Default comparator. Presumes that objects to be compared are of type
	 * Comparator<E>. Notice that the compare method will throw an exception
	 * if the objects to compare are not Comparable. 
	 * 
	 * An object of this type is used to implement the EntryComparator
	 * object in the cases when the pq instance is created using the
	 * default constructor. ... 
	 *
	 * @param <E> The data type of objects to be compared. 
	 */
	private static class DefaultComparator<E> implements Comparator<E> {

		@SuppressWarnings("unchecked")
		@Override
		public int compare(E o1, E o2) throws ClassCastException {
			return ((Comparable<E>) o1).compareTo(o2);
		} 		
	}
	

	/**
	 * Implementation of comparator of entries. The compare method bases its results
	 * on how do the keys in both entries given compare. 
	 * 
	 * @author pedroirivera-vega
	 *
	 * @param <K> Data type of Key
	 * @param <V> Data type of value
	 */
	private static class EntryComparator<K, V> implements Comparator<Entry<K, V>> {
		private Comparator<K> cmp; 
		public EntryComparator(Comparator<K> cmp) { 
			this.cmp = cmp; 
		}
		
		public int compare(Entry<K, V> e1, Entry<K, V> e2) {
			return cmp.compare(e1.getKey(), e2.getKey());
		} 
		
	}

}