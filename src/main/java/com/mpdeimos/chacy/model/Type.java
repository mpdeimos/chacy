package com.mpdeimos.chacy.model;

import com.mpdeimos.chacy.config.LanguageValue;
import com.mpdeimos.chacy.util.JavaUtil;

import java.util.ArrayList;
import java.util.List;

/** Represents a type extracted from Java, e.g. a class, interface, etc. */
public class Type extends Element
{
	/** The kind of this type. */
	protected ETypeKind kind;

	/** Map of package rename rules. */
	protected final LanguageValue packageName;

	private List<String> methods = new ArrayList<String>();

	/** Constructor. */
	public Type(
			LanguageValue packageName,
			LanguageValue typeName,
			ETypeKind kind,
			ModifierCollection modifiers)
	{
		super(typeName, modifiers, LanguageValue::getDefault);
		this.kind = kind;
		this.packageName = packageName;
	}

	/** Copy constructor. */
	public Type(Type origin, LanguageValueAccessor accessor)
	{
		super(origin.name, origin.modifiers, accessor);
		this.kind = origin.kind;
		this.packageName = new LanguageValue(origin.packageName);
		this.methods = new ArrayList<>(origin.methods);
	}

	/** @see #packageName */
	public String getNamespace()
	{
		return this.accessor.access(this.packageName).iterator().next();
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

	/** TODO */
	public void addMethod(String name)
	{
		this.methods.add(name);
	}

	public List<String> getMethods()
	{
		return this.methods;
	}
}
