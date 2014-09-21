package com.mpdeimos.chacy;

import com.mpdeimos.chacy.transform.Transformator;
import com.mpdeimos.chacy.transform.csharp.CSharpTransformator;
import com.mpdeimos.chacy.transform.vala.ValaTransformator;

/** Enumeration of all languages Java code can be converted to. */
public enum Language
{
	/** The C# programming language */
	CSHARP("C#", "cs", new CSharpTransformator()), //$NON-NLS-1$ //$NON-NLS-2$

	/** The CoffeeScript language which can be compiled to JavaScript. */
	VALA("Vala", "vala", new ValaTransformator()), //$NON-NLS-1$ //$NON-NLS-2$

	;

	/** The spoken name of the language. */
	private final String spokenName;

	/** The file extension of the language. */
	private String extension;

	/**
	 * The {@link Transformator} to convert Java types to types of other
	 * languages.
	 */
	private final Transformator transformator;

	/** Constructor. */
	private Language(String spokenName, String extension,
			Transformator transformator)
	{
		this.spokenName = spokenName;
		this.extension = extension;
		this.transformator = transformator;
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

	/** @see #transformator */
	public Transformator getTransformator()
	{
		return this.transformator;
	}
}
