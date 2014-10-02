package com.mpdeimos.chacy.parser;

import com.mpdeimos.chacy.ChacyException;

/**
 * Functional interface describing a parser of an element.
 */
@FunctionalInterface
public interface Parser<Element, Result>
{
	/** Parses the element to a result. */
	public Result parse(Element element) throws ChacyException;
}
