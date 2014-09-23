package com.mpdeimos.chacy.view;

import java.util.Locale;

import org.stringtemplate.v4.StringRenderer;

public class EnumRenderer extends StringRenderer
{
	@Override
	public String toString(Object o, String formatString, Locale locale)
	{
		if (o instanceof Enum)
		{
			o = ((Enum<?>) o).name().toLowerCase();
		}
		return super.toString(o, formatString, locale);
	}
}
