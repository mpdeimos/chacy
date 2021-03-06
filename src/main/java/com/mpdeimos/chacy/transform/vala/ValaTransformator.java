package com.mpdeimos.chacy.transform.vala;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.model.EModifier;
import com.mpdeimos.chacy.model.EVisibility;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.transform.Transformator;

/**
 * Interface for transforming a {@link Type} to a {@link Language#VALA}.
 */
public class ValaTransformator implements Transformator
{
	/** Transforms the type with the given configuration. */
	@Override
	public TypeDeviant[] transform(Type type, Config config)
	{
		return new TypeDeviant[] { new TypeDeviant(type, Language.VALA)
		{
			@Override
			protected void setUp()
			{
				this.modifiers.replaceVisibility(
						Language.VALA,
						EVisibility.PACKAGE_PRIVATE,
						EVisibility.PUBLIC.toString());
				this.modifiers.addModifiers(
						Language.VALA,
						Chacy.Const.REMOVE
								+ EModifier.FINAL.toString());
			}
		} };
	}
}
