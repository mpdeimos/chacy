package com.mpdeimos.chacy.model;

/**
 * Defines the kind of a type.
 */
public enum ETypeKind
{
	/** A normal class. */
	CLASS("class"), //$NON-NLS-1$

	/** An interface type. */
	INTERFACE("interface"), //$NON-NLS-1$

	/** An enumeration type. */
	ENUM("enum"); //$NON-NLS-1$

	/** The default display name for the type. */
	private final String name;

	/** Constructor. */
	private ETypeKind(String name)
	{
		this.name = name;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return this.name;
	}
}
