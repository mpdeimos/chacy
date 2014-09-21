package com.mpdeimos.chacy.view;

import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.util.JavaUtil;

import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STRawGroupDir;

/** Writes a type to a {@link ST} template. */
public class TypeWriter
{
	/** Encoding for template files. */
	private static final String ST_ENCODING = "UTF-8"; //$NON-NLS-1$

	/** Char used by String Template to delimit expressions. */
	private static final char ST_DELIMITER_CHAR = '#';

	/** The filer for creation of files. */
	private final Filer filer;

	/** The type to be written. */
	private final TypeDeviant type;

	/** Constructor. */
	public TypeWriter(Filer filer, TypeDeviant type)
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

		String outputNamespace = JavaUtil.getNamespace(
				language.name().toLowerCase(),
				this.type.getNamespace());

		FileObject resource = this.filer.createResource(
				StandardLocation.SOURCE_OUTPUT,
				outputNamespace,
				this.type.getFilename());

		URL tplDir = this.getClass().getResource(language.name().toLowerCase());

		STGroupDir tplGroup = new STRawGroupDir(tplDir, ST_ENCODING,
				ST_DELIMITER_CHAR, ST_DELIMITER_CHAR);
		ST tpl = tplGroup.getInstanceOf("Type"); //$NON-NLS-1$
		tpl.add("type", this.type); //$NON-NLS-1$

		Writer writer = resource.openWriter();
		tpl.write(new AutoIndentWriter(writer));
		writer.close();
	}
}
