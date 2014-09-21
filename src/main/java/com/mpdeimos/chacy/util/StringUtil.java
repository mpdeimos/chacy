package com.mpdeimos.chacy.util;

/** String utilities. */
public class StringUtil
{
	/** The empty string. */
	public static final String EMPTY = "";

	/** Joins the parts of the given string with a separator. */
	public static String join(String separator, String... parts)
	{
		StringBuilder sb = new StringBuilder();
		for (String part : parts)
		{
			if (part == null || part.isEmpty())
			{
				continue;
			}

			if (sb.length() > 0)
			{
				sb.append(separator);
			}
			sb.append(part);
		}

		return sb.toString();
	}
}
