package com.mpdeimos.chacy;

import java.util.function.Predicate;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/*** Matcher for asserting predicates. */
public class PredicateMatcher<T> extends TypeSafeMatcher<T>
{
	/** The predicate to match against. */
	private final Predicate<T> predicate;

	/** The description text. */
	private final String text;

	/** Constructor. */
	public PredicateMatcher(String text, Predicate<T> predicate)
	{
		this.predicate = predicate;
		this.text = text;
	}

	/** {@inheritDoc} */
	@Override
	protected boolean matchesSafely(T item)
	{
		return this.predicate.test(item);
	}

	/** {@inheritDoc} */
	@Override
	public void describeTo(Description description)
	{
		description.appendText(this.text);
	}

	/** {@inheritDoc} */
	@Override
	protected void describeMismatchSafely(
			T item,
			Description mismatchDescription)
	{
		mismatchDescription.appendText("was not "); //$NON-NLS-1$
		describeTo(mismatchDescription);
		mismatchDescription.appendText(" for "); //$NON-NLS-1$
		mismatchDescription.appendValue(item);
	}
}
