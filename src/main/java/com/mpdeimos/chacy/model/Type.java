package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageValue;
import com.mpdeimos.chacy.util.JavaUtil;

import java.util.EnumSet;

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

	/** Additonal modifiers of the type. */
	protected final EnumSet<EModifier> modifiers;

	/** Constructor. */
	public Type(
			String namespace,
			String name,
			ETypeKind kind,
			EVisibility visibility, EnumSet<EModifier> modifiers)
	{
		this.namespaceParts = JavaUtil.splitNamespace(namespace);
		this.name = name;
		this.kind = kind;
		this.visibility = visibility;
		this.modifiers = modifiers;

		// interfaces are marked as abstract, but this information is rather
		// confusing.
		if (this.kind == ETypeKind.INTERFACE)
		{
			this.modifiers.remove(EModifier.ABSTRACT);
		}
	}

	/** Copy constructor. */
	public Type(Type origin)
	{
		this.namespaceParts = origin.namespaceParts;
		this.name = origin.name;
		this.kind = origin.kind;
		this.renameRules = new LanguageValue(origin.renameRules);
		this.visibility = origin.visibility;
		this.modifiers = EnumSet.copyOf(origin.modifiers);
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
