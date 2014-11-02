package com.mpdeimos.chacy.model.deviant;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.LanguageValue;
import com.mpdeimos.chacy.model.Method;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.util.Composition;
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
		super(origin, toMethodDeviants(origin, language), Composition.from(
				LanguageValue::get).swap().apply(language)::apply);
		this.deviantInfo = new DeviantInfo<Type>(origin, language);
		setUp();
	}

	/** Converts the methods to method deviants. */
	private static List<Method> toMethodDeviants(Type origin, Language language)
	{
		List<Method> methods = new ArrayList<>();
		for (Method method : origin.getMethods())
		{
			methods.add(new MethodDeviant(method, language));
		}
		return methods;
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

	/** @return the modifiers as strings of the deviant language. */
	public List<String> getModifiers()
	{
		return this.modifiers.getModifiers(this.deviantInfo.getLanguage());
	}
}
