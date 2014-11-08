package com.mpdeimos.chacy.model.deviant;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.LanguageValue;
import com.mpdeimos.chacy.model.Method;
import com.mpdeimos.chacy.util.Composition;

import java.util.List;

/** Deviant of a {@link Method} for a given language. */
public class MethodDeviant extends Method implements Deviant<Method>
{
	/** @see DeviantInfo */
	private final DeviantInfo<Method> deviantInfo;

	/** Constructor. */
	public MethodDeviant(Method origin, Language language)
	{
		super(origin, Composition.from(
				LanguageValue::get).swap().apply(language)::apply);
		this.deviantInfo = new DeviantInfo<Method>(origin, language);
		setUp();
	}

	/** Template method that can be overridden in implementing classes. */
	protected void setUp()
	{
		// template
	}

	/** {@inheritDoc} */
	@Override
	public DeviantInfo<Method> getDeviantInfo()
	{
		return this.deviantInfo;
	}

	/** @return the modifiers as strings of the deviant language. */
	public List<String> getModifiers()
	{
		return this.modifiers.getModifiers(this.deviantInfo.getLanguage());
	}
}
