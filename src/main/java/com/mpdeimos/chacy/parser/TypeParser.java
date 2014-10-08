package com.mpdeimos.chacy.parser;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.ChacyException;
import com.mpdeimos.chacy.model.ETypeKind;
import com.mpdeimos.chacy.model.EVisibility;
import com.mpdeimos.chacy.model.Type;

import javax.lang.model.element.Element;

/** Parses Java types from the javax implementation to Chacy model objects. */
public interface TypeParser extends Parser<Element, Type>
{
	/** @return A type parser instance. */
	public static TypeParser get()
	{
		return Implementation::parse;
	}

	/** Actual implementation of a type parser. */
	public static class Implementation
	{
		/** Static implementation of {@link TypeParser} signature. */
		private static Type parse(Element element) throws ChacyException
		{
			String namespace = element.getEnclosingElement().getSimpleName()
					.toString();
			if (namespace.isEmpty())
			{
				namespace = null;
			}

			String name = element.getSimpleName().toString();

			Type type = new Type(
					namespace,
					name,
					getTypeKind(element),
					EVisibility.fromModifier(element.getModifiers()));

			parseSupportedLanguages(element, type);
			parseRenameRules(element, type);

			return type;
		}

		/** Parses the supported languages for the type. */
		private static void parseSupportedLanguages(Element element, Type type)
		{
			Chacy.Type typeAnnotation = element.getAnnotation(Chacy.Type.class);
			type.getSupportedLanguages().setLanguages(typeAnnotation.lang());

			Chacy.Ignore ignoreAnnotation = element
					.getAnnotation(Chacy.Ignore.class);
			if (ignoreAnnotation != null)
			{
				type.getSupportedLanguages().removeLanguages(
						ignoreAnnotation.lang());
			}
		}

		/** Parses the rename rules for the type. */
		private static void parseRenameRules(Element element, Type type)
		{
			Chacy.Name nameAnnotation = element.getAnnotation(Chacy.Name.class);
			if (nameAnnotation == null)
			{
				return;
			}

			type.getRenameRules().set(nameAnnotation.value());
		}

		/** @return the {@link ETypeKind} of the element. */
		private static ETypeKind getTypeKind(Element element)
				throws ChacyException
		{
			switch (element.getKind())
			{
			case CLASS:
				return ETypeKind.CLASS;
			case INTERFACE:
				return ETypeKind.INTERFACE;
			case ENUM:
				return ETypeKind.ENUM;
			default:
				throw new ChacyException("Unsupported element kind "
						+ element.getKind() + " for element "
						+ element.getSimpleName());
			}
		}
	}
}
