package com.mpdeimos.chacy.transform.csharp;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.lang.CSharp;
import com.mpdeimos.chacy.model.EModifier;
import com.mpdeimos.chacy.model.EVisibility;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.transform.Transformator;

import java.util.List;

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
					return CSharp.MODIFIER_INTERNAL;
				}

				return super.getVisibility();
			}

			@Override
			public List<String> getModifiers()
			{
				List<String> modifiers = super.getModifiers();
				int index = modifiers.indexOf(EModifier.FINAL.toString());
				if (index >= 0)
				{
					modifiers.set(index, CSharp.MODIFIER_SEALED);
				}
				return modifiers;
			}
		} };
	}
}
