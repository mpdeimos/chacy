package com.mpdeimos.chacy.util;

/**
 * Execution time assertions that rise an {@link AssertionError}.
 */
public final class AssertUtil
{
	/**
	 * Performs a cast from the given object to type T. If the cast cannot be
	 * performed an assertion error is thrown.
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T checkedCast(Object o, Class<T> clazz)
	{
		if (clazz.isInstance(o))
		{
			return (T) o;
		}

		throw new AssertionError("Failed asserting that an object of type "
				+ o.getClass().getSimpleName() + " can be cast to "
				+ clazz.getSimpleName());
	}
}
