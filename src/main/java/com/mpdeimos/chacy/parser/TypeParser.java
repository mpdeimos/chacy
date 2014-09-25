package com.mpdeimos.chacy.parser;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.EType;

public interface TypeParser extends Parser<Element, Type>
{
	public static TypeParser get()
	{
		return Implementation::parse;
	}

	public static class Implementation
	{
		private static Type parse(Element element)
		{
			String namespace = element.getEnclosingElement().getSimpleName()
					.toString();
			if (namespace.isEmpty())
			{
				namespace = null;
			}

			String name = element.getSimpleName().toString();
			Type type = new Type(namespace, name,
					element.getKind() == ElementKind.CLASS ? EType.CLASS
							: EType.INTERFACE);

			Chacy.Type typeAnnotation = element.getAnnotation(Chacy.Type.class);
			type.getSupportedLanguages().setLanguages(typeAnnotation.lang());

			Chacy.Ignore ignoreAnnotation = element
					.getAnnotation(Chacy.Ignore.class);
			if (ignoreAnnotation != null)
			{
				type.getSupportedLanguages().removeLanguages(
						ignoreAnnotation.lang());
			}

			return type;
		}
	}
}
