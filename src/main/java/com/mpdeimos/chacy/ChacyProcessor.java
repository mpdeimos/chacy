package com.mpdeimos.chacy;

import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.parser.TypeParser;
import com.mpdeimos.chacy.transform.Transformator;
import com.mpdeimos.chacy.util.JavaUtil;
import com.mpdeimos.chacy.view.FileWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Annotation processor that processes all types annotated with
 * {@link Chacy.Type}.
 */
@SupportedAnnotationTypes("com.mpdeimos.chacy.Chacy.Type")
@SupportedOptions(ChacyProcessor.DEBUG_TEMPLATE)
public class ChacyProcessor extends AbstractProcessor
{
	/** Config flag to enable debug output for ST4. */
	public static final String DEBUG_TEMPLATE = "chacy.debug.tpl";

	/** The global configuration. */
	private final Config config = new Config();

	/** {@inheritDoc} */
	@Override
	public SourceVersion getSupportedSourceVersion()
	{
		return SourceVersion.latest();
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);

		if (Boolean.parseBoolean(processingEnv.getOptions().get(DEBUG_TEMPLATE)))
		{
			FileWriter.enableDebugOutput();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv)
	{
		for (Element element : roundEnv
				.getElementsAnnotatedWith(Chacy.Type.class))
		{
			try
			{
				// TODO (MP) Check whether the parent is a package and not
				// another type.
				Type type = parseType(element);
				Map<Language, TypeDeviant[]> convertedTypes = transformType(type);
				writeTypes(convertedTypes);
			}
			catch (ChacyException e)
			{
				printError(e.getMessage());
			}
		}
		return true;
	}

	/** Parses the Java Type from the type element. */
	private Type parseType(Element element) throws ChacyException
	{
		return TypeParser.get().parse(element);
	}

	/**
	 * Transforms the {@link Type} to one or more {@link TypeDeviant}s.
	 */
	private Map<Language, TypeDeviant[]> transformType(Type type)
	{
		Map<Language, TypeDeviant[]> deviantTypes = new HashMap<Language, TypeDeviant[]>();
		for (Language language : this.config.getSupportedLanguages()
				.getLanguages())
		{
			if (!type.getSupportedLanguages().isSupported(language))
			{
				continue;
			}

			Transformator transformator = Transformator.Factory.get(language);
			TypeDeviant[] types = transformator.transform(type, this.config);

			deviantTypes.put(language, types);
		}

		return deviantTypes;
	}

	/** Writes the transformed types to files. */
	private void writeTypes(Map<Language, TypeDeviant[]> transformedTypes)
	{
		for (TypeDeviant[] types : transformedTypes.values())
		{
			for (TypeDeviant type : types)
			{
				writeType(type);
			}
		}
	}

	/** Writes the transformed type to a file. */
	private void writeType(TypeDeviant type)
	{
		try
		{
			FileWriter writer = new FileWriter(this.processingEnv.getFiler(),
					type);
			writer.write();
		}
		catch (IOException e)
		{
			printError("Could not convert class "
					+ JavaUtil.getNamespace(type.getName(),
							type.getNamespace())
					+ " to language "
					+ type.getDeviantInfo().getLanguage()
							.getSpokenName() + ": "
					+ e.getMessage());
		}
	}

	/** Prints the given error message to the processing environment. */
	private void printError(String message)
	{
		this.processingEnv.getMessager().printMessage(
				Diagnostic.Kind.ERROR,
				message);
	}
}
