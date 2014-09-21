package com.mpdeimos.chacy.config;

/** The Chacy configuration. */
public class Config
{
	/** Languages that will be included in conversion. */
	private final LanguageSupport languages = new LanguageSupport();

	/** @see #languages */
	public LanguageSupport getSupportedLanguages()
	{
		return this.languages;
	}
}
