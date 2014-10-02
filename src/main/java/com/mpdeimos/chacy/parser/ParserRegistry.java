package com.mpdeimos.chacy.parser;

import com.mpdeimos.chacy.model.Type;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Element;

/** Registry of language element parsers. */
public class ParserRegistry
{
	/** Map of parsers for a result type (key). */
	private final Map<Class<?>, Map<Class<?>, Parser<?, ?>>> parsers = new HashMap<>();

	/** Constructor. */
	public ParserRegistry()
	{
		add(Element.class, Type.class, TypeParser.get());
	}

	/** Adds a parser to the parser map. */
	private <E, R> void add(Class<E> element, Class<R> result,
			Parser<E, R> parser)
	{
		Map<Class<?>, Parser<?, ?>> parsersForResult = this.parsers.get(result);
		if (parsersForResult == null)
		{
			parsersForResult = new HashMap<>();
			this.parsers.put(result, parsersForResult);
		}

		parsersForResult.put(element, parser);
	}

	/** Gets a parser from the parser map. */
	@SuppressWarnings("unchecked")
	public <E, R> Parser<E, R> get(E element, Class<? extends R> result)
	{
		Map<Class<?>, Parser<?, ?>> parsersForResult = this.parsers.get(result);
		if (parsersForResult == null)
		{
			return null;
		}

		Class<?> elementClazz = element.getClass();
		Parser<?, ?> parser = parsersForResult.get(elementClazz);
		if (parser != null)
		{
			return (Parser<E, R>) parser;
		}

		return (Parser<E, R>) parsersForResult.keySet().stream()
				.filter(elementClazz::isAssignableFrom).findFirst()
				.map(parsersForResult::get).orElse(null);
	}
}
