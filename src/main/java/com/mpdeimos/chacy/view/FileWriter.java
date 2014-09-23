package com.mpdeimos.chacy.view;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.util.JavaUtil;
import com.mpdeimos.chacy.util.StringUtil;

/** Writes a type to a {@link ST} template. */
public class FileWriter
{
	/** Encoding for template files. */
	private static final String ST_ENCODING = "UTF-8"; //$NON-NLS-1$

	/** Char used by String Template to start expressions. */
	private static final char ST_DELIMITER_START_CHAR = '<';
	/** Char used by String Template to stop expressions. */
	private static final char ST_DELIMITER_STOP_CHAR = '>';

	/** The filer for creation of files. */
	private final Filer filer;

	/** The type to be written. */
	private final TypeDeviant type;

	/** Map for caching template groups per language. */
	private final Map<Language, STGroup> groupCache = new HashMap<>();

	/** Constructor. */
	public FileWriter(Filer filer, TypeDeviant type)
	{
		this.filer = filer;
		this.type = type;
	}

	/**
	 * Uses the {@link Filer} to write the type to a file in the
	 * {@link StandardLocation#SOURCE_OUTPUT} directory.
	 */
	public void write() throws IOException
	{
		Language language = this.type.getDeviantInfo().getLanguage();

		ErrorBuffer listener = new ErrorBuffer();
		STGroup tplGroup = getTemplateGroup(language, listener);

		try (Writer writer = openOutputWriter(language))
		{
			ST tpl = tplGroup.getInstanceOf("file"); //$NON-NLS-1$
			if (tpl == null)
			{
				throw new IOException(
						"Template 'file' does not exist for language "
								+ language);
			}

			tpl.add("type", this.type); //$NON-NLS-1$
			tpl.write(new AutoIndentWriter(writer));
		}
		catch (Exception e)
		{
			// Seems like STG has some NPEs dangling if something does not
			// compile, catch Exception.

			// If we have something in the error log, throw this
			throwIfErrors(listener);

			// otherwise throw the exception
			throw new IOException("Template Error: " + e.getMessage());
		}

		throwIfErrors(listener);
	}

	/** Opens the output writer for the file. */
	private Writer openOutputWriter(Language language) throws IOException
	{
		String outputNamespace = JavaUtil.getNamespace(language.name()
				.toLowerCase(), this.type.getNamespace());

		FileObject file = this.filer.createResource(
				StandardLocation.SOURCE_OUTPUT, outputNamespace,
				this.type.getFilename());

		return file.openWriter();
	}

	/**
	 * @return The template group for the given language. The group may be
	 *         cached.
	 */
	private STGroup getTemplateGroup(Language language, ErrorBuffer listener)
			throws IOException
	{
		URL tplDir = this.getClass().getResource(
				language.name().toLowerCase() + ".stg");

		STGroup tplGroup = new STGroupFile(tplDir, ST_ENCODING,
				ST_DELIMITER_START_CHAR, ST_DELIMITER_STOP_CHAR);

		tplGroup.registerRenderer(Enum.class, new EnumRenderer());
		tplGroup.setListener(listener);

		groupCache.put(language, tplGroup);

		return tplGroup;
	}

	/** Throws an IOException if template was not written. */
	private void throwIfErrors(ErrorBuffer listener) throws IOException
	{
		if (!listener.errors.isEmpty())
		{
			String message = "Template Error:\n"
					+ StringUtil.join("\n", listener.errors);
			listener.errors.clear();

			throw new IOException(message);
		}
	}
}
