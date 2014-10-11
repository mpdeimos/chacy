package com.mpdeimos.chacy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Map of lists with convenient add method.
 */
public class ListMap<K, V> extends HashMap<K, List<V>>
{
	/**
	 * Adds a value to the list for the given key. The list is created if it
	 * does not exist.
	 */
	public void addToList(K key, V value)
	{
		List<V> list = get(key);
		if (list == null)
		{
			list = new ArrayList<>();
			put(key, list);
		}

		list.add(value);
	}

	/**
	 * removes a value from the list for the given key.
	 */
	public void removeFromList(K key, V value)
	{
		List<V> list = get(key);
		if (list != null)
		{
			list.remove(value);
		}
	}
}
