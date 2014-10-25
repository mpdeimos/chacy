package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageValue;
import com.mpdeimos.chacy.util.JavaUtil;

/** Represents a type extracted from Java, e.g. a class, interface, etc. */
public class Type extends Element
{
	/** The namespace of the type. */
	protected String namespace;

	/** The name of the type. */
	protected String name;

	/** The kind of this type. */
	protected ETypeKind kind;

	/** Map of type rename rules. */
	protected LanguageValue typeNameRules = new LanguageValue();

	/** Map of package rename rules. */
	protected LanguageValue packageNameRules = new LanguageValue();

	/** The visibility of the type. */
	protected EVisibility visibility;

	/** Modifiers of the type. */
	protected final ModifierCollection modifiers;

	/** Constructor. */
	public Type(
			String namespace,
			String name,
			ETypeKind kind,
			ModifierCollection modifiers)
	{
		this.namespace = namespace;
		this.name = name;
		this.kind = kind;
		this.modifiers = modifiers;
	}

	/** Copy constructor. */
	public Type(Type origin)
	{
		this.namespace = origin.namespace;
		this.name = origin.name;
		this.kind = origin.kind;
		this.typeNameRules = new LanguageValue(origin.typeNameRules);
		this.packageNameRules = new LanguageValue(origin.packageNameRules);
		this.visibility = origin.visibility;
		this.modifiers = new ModifierCollection(origin.modifiers);
	}

	/** @see #namespace */
	public String getNamespace()
	{
		return this.namespace;
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

	/** @see #typeNameRules */
	public LanguageValue getTypeNameRules()
	{
		return this.typeNameRules;
	}

	/** @see #packageNameRules */
	public LanguageValue getPackageNameRules()
	{
		return this.packageNameRules;
	}
}
