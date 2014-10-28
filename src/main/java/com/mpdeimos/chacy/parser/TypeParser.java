package com.mpdeimos.chacy.parser;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.ChacyException;
import com.mpdeimos.chacy.model.EModifier;
import com.mpdeimos.chacy.model.ETypeKind;
import com.mpdeimos.chacy.model.EVisibility;
import com.mpdeimos.chacy.model.ModifierCollection;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.util.AssertUtil;
import com.mpdeimos.chacy.util.JavaUtil;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/** Parses Java types from the javax implementation to Chacy model objects. */
public interface TypeParser extends Parser<TypeElement, Type>
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
		private static Type parse(TypeElement element) throws ChacyException
		{
			String name = element.getSimpleName().toString();

			Type type = new Type(
					parsePackageName(element),
					name,
					getTypeKind(element),
					createModifierCollection(element));

			parseSupportedLanguages(element, type);
			parseRenameRules(element, type);
			parseChildElements(element, type);

			return type;
		}

		/** Parses the child elements from the type. */
		private static void parseChildElements(Element element, Type type)
		{
			for (Element child : element.getEnclosedElements())
			{
				switch (child.getKind())
				{
				case METHOD:
					parseMethod(AssertUtil.checkedCast(
							child,
							ExecutableElement.class), type);
					break;
				default:
					// TODO warning if everything is implemented
				}
			}
		}

		/** Parses the method from the type. */
		private static void parseMethod(ExecutableElement method, Type type)
		{
			type.addMethod(method.getSimpleName().toString());
		}

		/** @return The package name of the element. */
		private static String parsePackageName(Element element)
		{
			String namespace = JavaUtil.getPackage(element).toString();

			if (namespace.isEmpty() || namespace.equals("unnamed package"))
			{
				namespace = null;
			}
			return namespace;
		}

		/** Creates a modifier collection from the given element. */
		private static ModifierCollection createModifierCollection(
				Element element)
		{
			ModifierCollection collection = new ModifierCollection(
					EVisibility.fromModifiers(element.getModifiers()),
					EModifier.fromModifiers(element));

			Chacy.Modifier modifierAnnotation = element.getAnnotation(Chacy.Modifier.class);
			if (modifierAnnotation != null)
			{
				collection.addModifiers(modifierAnnotation.value());
			}

			return collection;
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
				type.getSupportedLanguages().removeLanguages(ignoreAnnotation);
			}
		}

		/** Parses the rename rules for the type. */
		private static void parseRenameRules(Element element, Type type)
		{
			Chacy.Name nameAnnotation = element.getAnnotation(Chacy.Name.class);
			if (nameAnnotation != null)
			{
				type.getTypeNameRules().set(nameAnnotation.value());
			}

			nameAnnotation = JavaUtil.getPackage(element).getAnnotation(
					Chacy.Name.class);
			if (nameAnnotation != null)
			{
				type.getPackageNameRules().set(nameAnnotation.value());
			}
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
