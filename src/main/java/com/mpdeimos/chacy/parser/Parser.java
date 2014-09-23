package com.mpdeimos.chacy.parser;

@FunctionalInterface
public interface Parser<Element, Result>
{
	public Result parse(Element element);
}
