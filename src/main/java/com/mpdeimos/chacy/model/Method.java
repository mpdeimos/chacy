package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.LanguageValue;

import java.util.List;

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

	/** @return the modifiers as strings of the deviant language. */
	public List<String> getModifiers()
	{
		return this.modifiers.getModifiers(Language.CSHARP);
	}
}
