package com.mpdeimos.chacy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** Class containing all Chacy annotations. */
public final class Chacy
{
	/**
	 * Annotates a type to be converted by Chacy.
	 */
	@Target(ElementType.TYPE)
	public @interface Type
	{
		/**
		 * The languages the type should be converted to. Leave empty to convert
		 * to all configured types.
		 */
		Language[] lang() default {};
	}

	/** Allows to ignore an element for the given languages. */
	@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
			ElementType.METHOD })
	public @interface Ignore
	{
		/**
		 * The languages the ignore applies to. Leave empty for all configured
		 * languages.
		 */
		Language[] lang() default {};
	}

	/** Allows to rename a type, method, field, etc. */
	@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
			ElementType.METHOD, ElementType.LOCAL_VARIABLE })
	public @interface Name
	{
		/** Reference to a language bound value. */
		Value[] value();
	}

	/** Allows to add modifiers to a type, method, field, etc. */
	@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.FIELD,
			ElementType.METHOD, ElementType.LOCAL_VARIABLE })
	public @interface Modifier
	{
		/** Reference to a language bound value. */
		Value[] value();
	}

	/** A language bound value. */
	@Target(ElementType.ANNOTATION_TYPE)
	public @interface Value
	{
		/**
		 * The languages the value applies to. Leave empty for all configured
		 * languages.
		 */
		Language[] lang() default {};

		/** The value bound for the language. */
		String[] value();
	}

	/** Constants. */
	public static class Const
	{
		/** Prefix that indicates to remove something. */
		public static final String REMOVE = "-";
	}
}
