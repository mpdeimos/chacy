package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.util.JavaUtil;

import java.util.HashMap;
import java.util.Map;

/** Represents a type extracted from Java, e.g. a class, interface, etc. */
public class Type extends Element
{
	/** The namespace parts of the type. */
	protected String[] namespaceParts;

	/** The name of the type. */
	protected String name;

	/** The kind of this type. */
	protected ETypeKind kind;

	/** Map of rename rules. */
	protected Map<Language, String> renameRules = new HashMap<>();

	/** Constructor. */
	public Type(String namespace, String name, ETypeKind kind)
	{
		this.namespaceParts = JavaUtil.splitNamespace(namespace);
		this.name = name;
		this.kind = kind;
	}

	/** Copy constructor. */
	public Type(Type origin)
	{
		this.namespaceParts = origin.namespaceParts;
		this.name = origin.name;
		this.kind = origin.kind;
		this.renameRules = origin.renameRules;
	}

	/** @see #namespace */
	public String getNamespace()
	{
		return JavaUtil.getNamespace(this.namespaceParts);
	}

	/** @see #name */
	public String getName()
	{
		return this.name;
	}

	/** @return the qualified name of the type in namespaced notation. */
	public String getQualifiedName()
	{
		return JavaUtil.getNamespace(getNamespace(), getName());
	}

	/** @see #kind */
	public ETypeKind getKind()
	{
		return this.kind;
	}

	/**
	 * Sets the name for the given languages. If no language is specified, all
	 * languages that have no explicit name set will be renamed to the given
	 * name.
	 */
	public void addRenameRule(String name, Language... languages)
	{
		if (languages.length == 0)
		{
			for (Language language : Language.values())
			{
				this.renameRules.putIfAbsent(language, name);
			}
			return;
		}

		for (Language language : languages)
		{
			this.renameRules.put(language, name);
		}
	}
}
