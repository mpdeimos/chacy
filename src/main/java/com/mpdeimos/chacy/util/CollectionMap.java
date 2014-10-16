package com.mpdeimos.chacy.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A Map of lists with convenient add method.
 */
public class CollectionMap<K, V> implements Map<K, Collection<V>>
{
	/** The backend hash map. */
	private final HashMap<K, List<V>> backend = new HashMap<>();

	/** Constructor. */
	public CollectionMap()
	{
		// does nothing
	}

	/** Copy constructor. */
	public CollectionMap(CollectionMap<K, V> original)
	{
		for (K key : original.keySet())
		{
			this.put(key, new ArrayList<V>(original.get(key)));
		}
	}

	/**
	 * Adds a value to the list for the given key. The list is created if it
	 * does not exist.
	 */
	public void addToList(K key, V value)
	{
		List<V> list = this.backend.get(key);
		if (list == null)
		{
			list = new ArrayList<>();
			this.backend.put(key, list);
		}

		list.add(value);
	}

	/**
	 * removes a value from the list for the given key.
	 */
	public void removeFromList(K key, V value)
	{
		List<V> list = this.backend.get(key);
		if (list != null)
		{
			list.remove(value);
		}
	}

	/**
	 * Gets the key that corresponds to the list containing the given values.
	 */
	private K getKeyForList(Collection<V> comparee)
	{
		for (K key : keySet())
		{
			List<V> list = get(key);
			if (list.size() == comparee.size())
			{
				Iterator<V> iterator = comparee.iterator();
				if (list.stream().allMatch(
						value -> Objects.equals(value, iterator.next())))
				{
					return key;
				}
			}
		}

		return null;
	}

	// Implementation of map interface //

	/** {@inheritDoc} */
	@Override
	public int size()
	{
		return this.backend.size();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEmpty()
	{
		return this.backend.isEmpty();
	}

	/** {@inheritDoc} */
	@Override
	public boolean containsKey(Object key)
	{
		return this.backend.containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean containsValue(Object value)
	{
		if (value instanceof List)
		{
			return getKeyForList((List<V>) value) != null;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * The returned list is unmodifiable
	 */
	@Override
	public List<V> get(Object key)
	{
		List<V> list = this.backend.get(key);
		if (list == null)
		{
			return null;
		}
		return Collections.unmodifiableList(list);
	}

	/** {@inheritDoc} */
	@Override
	public List<V> put(K key, Collection<V> value)
	{
		return this.backend.put(key, new ArrayList<>(value));
	}

	/** {@inheritDoc} */
	@Override
	public List<V> remove(Object key)
	{
		return this.backend.remove(key);
	}

	/** {@inheritDoc} */
	@Override
	public void putAll(Map<? extends K, ? extends Collection<V>> m)
	{
		for (K key : m.keySet())
		{
			// this delegates to the put method of THIS class
			this.put(key, m.get(key));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void clear()
	{
		this.backend.clear();
	}

	/** {@inheritDoc} */
	@Override
	public Set<K> keySet()
	{
		return this.backend.keySet();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<Collection<V>> values()
	{
		ArrayList<Collection<V>> result = new ArrayList<>();
		for (List<V> list : this.backend.values())
		{
			result.add(Collections.unmodifiableList(list));
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public Set<java.util.Map.Entry<K, Collection<V>>> entrySet()
	{
		Set<java.util.Map.Entry<K, Collection<V>>> result = new HashSet<>();
		for (K key : this.backend.keySet())
		{
			result.add(new Entry<K, Collection<V>>()
			{
				@Override
				public K getKey()
				{
					return key;
				}

				@Override
				public Collection<V> getValue()
				{
					return get(key);
				}

				@Override
				public Collection<V> setValue(Collection<V> value)
				{
					return put(key, value);
				}
			});
		}

		return result;
	}
}
