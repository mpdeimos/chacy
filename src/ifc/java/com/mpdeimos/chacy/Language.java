package com.mpdeimos.chacy;


/** Enumeration of all languages Java code can be converted to. */
public enum Language
{
	/** The C# programming language */
	CSHARP("C#", "cs"),

	/** The CoffeeScript language which can be compiled to JavaScript. */
	VALA("Vala", "vala"),

	;

	/** The spoken name of the language. */
	private final String spokenName;

	/** The file extension of the language. */
	private String extension;

	/** Constructor. */
	private Language(String spokenName, String extension)
	{
		this.spokenName = spokenName;
		this.extension = extension;
	}

	/** @see #spokenName */
	public String getSpokenName()
	{
		return this.spokenName;
	}

	/** @see #extension */
	public String getExtension()
	{
		return this.extension;
	}
}
