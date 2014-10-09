package com.mpdeimos.chacy.model.deviant;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.model.EModifier;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.util.FileUtil;
import com.mpdeimos.chacy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/** Deviant of a {@link Type} for a given language. */
public class TypeDeviant extends Type implements Deviant<Type>
{
	/** @see DeviantInfo */
	private final DeviantInfo<Type> deviantInfo;

	/** Constructor. */
	public TypeDeviant(Type origin, Language language)
	{
		super(origin);
		this.deviantInfo = new DeviantInfo<Type>(origin, language);
	}

	/** {@inheritDoc} */
	@Override
	public DeviantInfo<Type> getDeviantInfo()
	{
		return this.deviantInfo;
	}

	/**
	 * @return The filename (including extension) of the file, the type is
	 *         written
	 */
	public String getFilename()
	{
		return StringUtil.join(
				FileUtil.EXTENSION_SEPARATOR,
				getName(),
				getFileExtension());
	}

	/** @return The extension of the file of this type. */
	protected String getFileExtension()
	{
		return this.deviantInfo.getLanguage().getExtension();
	}

	/** {@inheritDoc} */
	@Override
	public String getName()
	{
		return this.renameRules.get(this.deviantInfo.getLanguage()).orElse(
				super.getName());
	}

	/** Returns a string describing the visibility of the type. */
	public String getVisibility()
	{
		return this.visibility.toString();
	}

	/** Returns the modifiers of the deviant type. */
	public List<String> getModifiers()
	{
		List<String> modifiers = new ArrayList<>();

		String visibility = this.getVisibility();
		if (!StringUtil.isNullOrEmpty(visibility))
		{
			modifiers.add(visibility);
		}

		for (EModifier modifier : this.modifiers)
		{
			modifiers.add(modifier.toString());
		}

		return modifiers;
	}
}
