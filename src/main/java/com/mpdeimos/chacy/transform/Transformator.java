package com.mpdeimos.chacy.transform;

import java.util.HashMap;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.transform.csharp.CSharpTransformator;
import com.mpdeimos.chacy.transform.vala.ValaTransformator;

/**
 * Interface for transforming a type into a type of another programming
 * language.
 */
public interface Transformator
{
	/** Transforms the type with the given configuration. */
	public TypeDeviant[] transform(Type type, Config config);

	/** Factory for transformators. */
	public class Factory
	{
		// TODO (MP) Test
		// TODO (MP) Use dependency injection?

		/** Cache of transformators for languages. */
		private static HashMap<Language, Transformator> transformators = new HashMap<Language, Transformator>();

		/** Static Constructor. */
		static
		{
			for (Language lang : Language.values())
			{
				transformators.put(lang, create(lang));
			}
		}

		/** @return A new transformator instance for the language. */
		private static Transformator create(Language lang)
		{
			switch (lang)
			{
			case CSHARP:
				return new CSharpTransformator();
			case VALA:
				return new ValaTransformator();
			}

			throw new IllegalStateException(
					"No transformator provided for language "
							+ lang.getSpokenName());
		}

		/** @return The transformator from the language from the factory. */
		public static Transformator get(Language lang)
		{
			return transformators.get(lang);
		}
	}
}
