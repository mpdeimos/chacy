package com.mpdeimos.chacy.transform.csharp;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.model.EVisibility;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.transform.Transformator;

/**
 * Interface for transforming a {@link Type} into a {@link Language#CSHARP}.
 */
public class CSharpTransformator implements Transformator
{
	/** Transforms the type with the given configuration. */
	@Override
	public TypeDeviant[] transform(Type type, Config config)
	{
		return new TypeDeviant[] { new TypeDeviant(type, Language.CSHARP)
		{
			@Override
			public String getVisibility()
			{
				if (this.visibility == EVisibility.PACKAGE_PRIVATE)
				{
					return "internal"; // TODO define constant
				}

				return super.getVisibility();
			}
		} };
	}
}
