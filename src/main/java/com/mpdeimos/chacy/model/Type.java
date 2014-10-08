package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageValue;
import com.mpdeimos.chacy.util.JavaUtil;

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
	protected LanguageValue renameRules = new LanguageValue();

	/** The visibility of the type. */
	protected EVisibility visibility;

	/** Constructor. */
	public Type(
			String namespace,
			String name,
			ETypeKind kind,
			EVisibility visibility)
	{
		this.namespaceParts = JavaUtil.splitNamespace(namespace);
		this.name = name;
		this.kind = kind;
		this.visibility = visibility;
	}

	/** Copy constructor. */
	public Type(Type origin)
	{
		this.namespaceParts = origin.namespaceParts;
		this.name = origin.name;
		this.kind = origin.kind;
		this.renameRules = origin.renameRules;
		this.visibility = origin.visibility;
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

	/** @see #renameRules */
	public LanguageValue getRenameRules()
	{
		return this.renameRules;
	}
}
