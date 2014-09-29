package com.mpdeimos.chacy.view;

import java.util.Locale;

import org.stringtemplate.v4.StringRenderer;

/**
 * Renderer for enum objects.
 * <p>
 * Uses the {@link Enum#name()} representation as default.
 */
public class EnumRenderer extends StringRenderer
{
	/** {@inheritDoc} */
	@Override
	public String toString(Object o, String formatString, Locale locale)
	{
		if (o instanceof Enum)
		{
			o = ((Enum<?>) o).name();
		}
		return super.toString(o, formatString, locale);
	}
}
