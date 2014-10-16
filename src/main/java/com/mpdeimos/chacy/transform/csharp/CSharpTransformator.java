package com.mpdeimos.chacy.transform.csharp;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.lang.CSharp;
import com.mpdeimos.chacy.model.EModifier;
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
			protected void setUp()
			{
				this.modifiers.replaceVisibility(
						Language.CSHARP,
						EVisibility.PACKAGE_PRIVATE,
						CSharp.MODIFIER_INTERNAL);
				this.modifiers.replaceModifier(
						Language.CSHARP,
						EModifier.FINAL,
						CSharp.MODIFIER_SEALED);
			}
		} };
	}
}
