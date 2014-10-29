package com.mpdeimos.chacy.util;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Wrapper for function compositions.
 * 
 * @author mpdeimos
 */
@FunctionalInterface
public interface Composition<T, U, R> extends Function<T, Function<U, R>>
{
	/** @returns the lambda from a {@link BiFunction}. */
	public static <T, U, R> Composition<T, U, R> from(
			final BiFunction<T, U, R> delegate)
	{
		return (T t) -> (U u) -> delegate.apply(t, u);
	}

	/** @returns the function with swapped arguments. */
	public default Composition<U, T, R> swap()
	{
		return (U u) -> (T t) -> this.apply(t).apply(u);
	}
}
