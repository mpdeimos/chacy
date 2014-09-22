package com.mpdeimos.chacy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.mpdeimos.chacy.Chacy;
import com.mpdeimos.chacy.Language;
import com.mpdeimos.chacy.config.Config;
import com.mpdeimos.chacy.model.Type;
import com.mpdeimos.chacy.model.deviant.TypeDeviant;
import com.mpdeimos.chacy.transform.Transformator;
import com.mpdeimos.chacy.util.JavaUtil;
import com.mpdeimos.chacy.view.TypeWriter;

/**
 * Annotation processor that processes all types annotated with
 * {@link Chacy.Type}.
 */
@SupportedAnnotationTypes("com.mpdeimos.chacy.Chacy.Type")
public class ChacyProcessor extends AbstractProcessor
{
	/** The global configuration. */
	private final Config config = new Config();

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
	}

	/** {@inheritDoc} */
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv)
	{
		for (Element element : roundEnv
				.getElementsAnnotatedWith(Chacy.Type.class))
		{
			// TODO (MP) Check whether the parent is a package and not another
			// type.
			Type type = parseType(element);
			Map<Language, TypeDeviant[]> convertedTypes = transformType(type);
			writeTypes(convertedTypes);
		}
		return true;
	}

	/** Parses the Java Type from the type element. */
	private Type parseType(Element element)
	{
		String namespace = element.getEnclosingElement().getSimpleName()
				.toString();
		if (namespace.isEmpty())
		{
			namespace = null;
		}

		String name = element.getSimpleName().toString();
		Type type = new Type(namespace, name);

		Chacy.Type typeAnnotation = element.getAnnotation(Chacy.Type.class);
		type.getSupportedLanguages().setLanguages(typeAnnotation.lang());

		Chacy.Ignore ignoreAnnotation = element
				.getAnnotation(Chacy.Ignore.class);
		if (ignoreAnnotation != null)
		{
			type.getSupportedLanguages().removeLanguages(
					ignoreAnnotation.lang());
		}

		return type;
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
			TypeWriter writer = new TypeWriter(this.processingEnv.getFiler(),
					type);
			writer.write();
		}
		catch (IOException e)
		{
			this.processingEnv.getMessager().printMessage(
					Diagnostic.Kind.ERROR,
					"Could not convert class " //$NON-NLS-1$
							+ JavaUtil.getNamespace(type.getName(),
									type.getNamespace())
							+ " to language " //$NON-NLS-1$
							+ type.getDeviantInfo().getLanguage()
									.getSpokenName() + ": " //$NON-NLS-1$
							+ e.getMessage());
		}
	}
}