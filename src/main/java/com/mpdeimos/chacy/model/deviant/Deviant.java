package com.mpdeimos.chacy.model.deviant;

import com.mpdeimos.chacy.Language;

/** A deviant of an language element in another language. */
public interface Deviant<T>
{
	/** @returns information about this deviant. */
	public DeviantInfo<T> getDeviantInfo();

	/**
	 * Information about a deviant element like the language and the original
	 * element.
	 */
	public class DeviantInfo<T>
	{
		/** @see Deviant#getOrigin() */
		private final T origin;

		/** @see Deviant#getLanguage() */
		private final Language language;

		/** Constructor. */
		public DeviantInfo(T origin, Language language)
		{
			this.language = language;
			this.origin = origin;

		}

		/** @return the Language of the deviant. */
		public Language getLanguage()
		{
			return this.language;
		}

		/** @return the origin of this deviant. */
		public T getOrigin()
		{
			return this.origin;
		}
	}
}
