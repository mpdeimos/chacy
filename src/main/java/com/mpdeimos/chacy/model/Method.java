package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageValue;

/**
 * Represents a Java Method.
 * 
 * @author mpdeimos
 */
public class Method extends Element
{
	/** Constructor. */
	public Method(
			LanguageValue name,
			ModifierCollection modifiers)
	{
		super(name, modifiers, LanguageValue::getDefault);
	}

	/** Copy constructor. */
	public Method(Method origin, LanguageValueAccessor accessor)
	{
		super(origin.name, origin.modifiers, accessor);
	}
}
