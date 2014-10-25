package com.mpdeimos.chacy.model.deviant;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.model.ModifierCollection;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.util.FileUtil;
import com.mpdeimos.chacy.util.StringUtil;

import java.util.Arrays;
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
		setUp();
	}

	/** Template method that can be overridden in implementing classes. */
	protected void setUp()
	{
		// template
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
		return this.typeNameRules.get(this.deviantInfo.getLanguage()).orElse(
				Arrays.asList(super.getName())).iterator().next();
	}

	/** {@inheritDoc} */
	@Override
	public String getNamespace()
	{
		return this.packageNameRules.get(this.deviantInfo.getLanguage()).orElse(
				Arrays.asList(super.getNamespace())).iterator().next();
	}

	/** @return the modifiers as strings of the deviant language. */
	public List<String> getModifiers()
	{
		return this.modifiers.getModifiers(this.deviantInfo.getLanguage());
	}

	/** @return the modifier collection. */
	public ModifierCollection getModifierCollection()
	{
		return this.modifiers;
	}
}
