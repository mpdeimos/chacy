package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageSupport;

/** Defines a source code element that can be excluded or renamed. */
public class Element
{
	/** The languages this element supports. */
	private final LanguageSupport supportedLanguages = new LanguageSupport();

	/** @see #supportedLanguages */
	public LanguageSupport getSupportedLanguages()
	{
		return this.supportedLanguages;
	}
}
