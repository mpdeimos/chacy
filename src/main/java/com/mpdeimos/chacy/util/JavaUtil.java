package com.mpdeimos.chacy.util;

/** Utilities for Java code. */
public class JavaUtil
{
	/** Separator for namespace parts. */
	public static String NAMESPACE_SEPARATOR = "."; //$NON-NLS-1$

	/**
	 * Creates a Java namespace from the single namespace parts. If the parts
	 * are empty <code>null</code> is returned.
	 */
	public static String getNamespace(String... parts)
	{
		// TODO (MP) Test

		if (parts.length == 0)
		{
			return null;
		}

		return StringUtil.join(NAMESPACE_SEPARATOR, parts);
	}

	/** Splits a java namespace into its parts. */
	public static String[] splitNamespace(String namespace)
	{
		// TODO (MP) Test

		if (namespace == null)
		{
			return new String[] {};
		}

		return namespace.split(NAMESPACE_SEPARATOR);
	}
}
