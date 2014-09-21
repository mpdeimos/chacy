package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.util.JavaUtil;

/** Represents a type extracted from Java, e.g. a class, interface, etc. */
public class Type extends Element
{
	/** The namespace parts of the type. */
	protected String[] namespaceParts;

	/** The name of the type. */
	protected String name;

	/** Constructor. */
	public Type(String namespace, String name)
	{
		this.namespaceParts = JavaUtil.splitNamespace(namespace);
		this.name = name;
	}

	/** Copy constructor. */
	public Type(Type origin)
	{
		this.namespaceParts = origin.namespaceParts;
		this.name = origin.name;
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
}
