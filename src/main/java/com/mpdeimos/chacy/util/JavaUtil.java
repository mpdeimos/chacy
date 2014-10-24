package com.mpdeimos.chacy.util;

import java.util.regex.Pattern;

/** Utilities for Java code. */
public class JavaUtil
{
	/** Separator for namespace parts. */
	public static String NAMESPACE_SEPARATOR = ".";

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

		return StringUtil.join(NAMESPACE_SEPARATOR, (Object[]) parts);
	}

	/** Splits a java namespace into its parts. */
	public static String[] splitNamespace(String namespace)
	{
		// TODO (MP) Test

		if (namespace == null)
		{
			return new String[] {};
		}

		return namespace.split(Pattern.quote(NAMESPACE_SEPARATOR));
	}
}
