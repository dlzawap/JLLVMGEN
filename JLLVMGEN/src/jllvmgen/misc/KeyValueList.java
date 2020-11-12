package jllvmgen.misc;

import java.util.ArrayList;

public class KeyValueList<K, V>{
	private ArrayList<K> keys;
	private ArrayList<V> values;
	private int count = 0;
	
	public KeyValueList()
	{
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}
	
	public KeyValueList(int initialCapacity)
	{
		keys = new ArrayList<K>(initialCapacity);
		values = new ArrayList<V>(initialCapacity);
	}
	
	public boolean add(K key, V value)
	{
		if (key == null || value == null)
			return false;
		
		if (!keys.add(key))
			return false;
		
		if (!values.add(value))
			return false;
		
		// increase count value.
		++count;
		
		return true;
	}
	
	public boolean removeAt(int index)
	{
		if (index < 0 || index > count)
			return false;
		
		if (keys.remove(index) == null)
			return false;
		if (values.remove(index) == null)
			return false;
		
		return true;
	}

	public int getCount()
	{
		return count;
	}
	
	public K keyAt(int index)
	{
		return keys.get(index);
	}
	
	public V valueAt(int index)
	{
		return values.get(index);
	}
}
