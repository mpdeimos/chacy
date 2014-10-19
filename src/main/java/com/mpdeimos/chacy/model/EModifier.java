package com.mpdeimos.chacy.model;

import java.util.EnumSet;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

/**
 * Defines modifiers (but visibility) of an element.
 */
public enum EModifier
{
	/** The abstract modifier. */
	ABSTRACT(Modifier.ABSTRACT, "abstract"),

	/** The final modifier. */
	FINAL(Modifier.FINAL, "final");

	/** The corresponding Java modifier. */
	private final Modifier modifier;

	/** The display name of the modifier. */
	private final String name;

	/** Constructor. */
	private EModifier(Modifier modifier, String name)
	{
		this.modifier = modifier;
		this.name = name;
	}

	/**
	 * @return The set of modifiers (excluding visibility) for the given
	 *         element.
	 */
	public static EnumSet<EModifier> fromModifiers(Element element)
	{
		Set<Modifier> modifiers = element.getModifiers();

		EnumSet<EModifier> result = EnumSet.noneOf(EModifier.class);

		for (EModifier modifier : values())
		{
			// interfaces are marked as abstract, but this information is rather
			// confusing.
			if (element.getKind() == ElementKind.INTERFACE
					&& modifier == EModifier.ABSTRACT)
			{
				continue;
			}

			if (modifiers.contains(modifier.modifier))
			{
				result.add(modifier);
			}
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public String toString()
	{
		return this.name;
	}
}
