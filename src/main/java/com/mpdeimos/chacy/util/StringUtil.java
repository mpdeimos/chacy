package com.mpdeimos.chacy.util;

/** String utilities. */
public class StringUtil
{
	/** The empty string. */
	public static final String EMPTY = "";

	/** Joins the parts of the given string with a separator. */
	public static String join(String separator, Object... parts)
	{
		StringBuilder sb = new StringBuilder();
		for (Object part : parts)
		{
			if (part == null)
			{
				continue;
			}

			String partString = part.toString();
			if (partString.isEmpty())
			{
				continue;
			}

			if (sb.length() > 0)
			{
				sb.append(separator);
			}
			sb.append(partString);
		}

		return sb.toString();
	}

	/** @return <code>null</code> if the string is <code>null</code> or empty. */
	public static boolean isNullOrEmpty(String s)
	{
		return s == null || s.isEmpty();
	}
}
