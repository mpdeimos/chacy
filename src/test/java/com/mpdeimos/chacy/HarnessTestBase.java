package com.mpdeimos.chacy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import com.mpdeimos.chacy.processor.TypeProcessor;
import com.mpdeimos.chacy.util.FileUtil;

/** Base class for Harness tests. */
public abstract class HarnessTestBase
{
	/** Rule to catch the JUnit test name. */
	@Rule
	public TestName testName = new TestName();

	/** Cleans the test output directory for this method. */
	@Before
	public void cleanTestTemp() throws IOException
	{
		FileUtil.removeRecursive(getTestOutput());
	}

	/** @return the ouput directory of the test. */
	private Path getTestOutput()
	{
		return Paths.get(
				"test-tmp", //$NON-NLS-1$
				this.getClass().getCanonicalName(),
				this.testName.getMethodName());
	}

	/** @return a harness for the given name that allows compilations. */
	protected Harness getHarness(String harness)
	{
		return new Harness(getTestOutput(), harness);
	}

	/** Allows access and compilation of harness data. */
	protected class Harness
	{
		/** The path where the harjness source data is stored. */
		private static final String HARNESS_DATA = "src/test/resources/harness"; //$NON-NLS-1$

		/** The output directory for the harness. */
		private final Path output;

		/** The name of the harness. */
		private final String harness;

		/**
		 * The annotation processor for compilation. Default is
		 * {@link TypeProcessor}.
		 */
		private Processor processor = new TypeProcessor();

		/** Constructor. */
		public Harness(Path output, String harness)
		{
			this.output = output.resolve(harness);
			this.harness = harness;
		}

		/** @see #processor */
		public void setProcessor(Processor processor)
		{
			this.processor = processor;
		}

		/** Compiles the given files with the specified {@link Processor}. */
		public CompilationResult compileFiles(String... filenames)
				throws IOException
		{
			JavaCompiler compiler = javax.tools.ToolProvider
					.getSystemJavaCompiler();

			StandardJavaFileManager fileManager = compiler
					.getStandardFileManager(null, null, null);

			Path outputDir = initEnvironment(fileManager);

			Iterable<? extends JavaFileObject> compilationUnits = getCompilationUnits(
					fileManager, filenames);

			CompilationTask task = compiler.getTask(null, fileManager, null,
					null, null, compilationUnits);

			task.setProcessors(Arrays.asList(this.processor));

			return new CompilationResult(task.call(), Paths.get(HARNESS_DATA,
					this.harness, "fixture"), outputDir); //$NON-NLS-1$
		}

		private Iterable<? extends JavaFileObject> getCompilationUnits(
				StandardJavaFileManager fileManager, String... filenames)
				throws IOException
		{
			if (filenames.length == 0)
			{
				filenames = new String[] { "**" };
			}

			List<Path> paths = FileUtil.listFiles(
					Paths.get(HARNESS_DATA, this.harness, "src"), filenames);

			List<JavaFileObject> compilationUnits = new ArrayList<JavaFileObject>();
			for (Path path : paths)
			{
				for (JavaFileObject file : fileManager.getJavaFileObjects(path
						.toFile()))
				{
					compilationUnits.add(file);
				}
			}

			return compilationUnits;
		}

		/** Initializes the output environment. */
		private Path initEnvironment(StandardJavaFileManager fileManager)
				throws IOException
		{
			Path outputDir = createUniquePath(this.output);

			Path binDir = outputDir.resolve("bin"); //$NON-NLS-1$
			Files.createDirectories(binDir);
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
					Arrays.asList(binDir.toFile()));

			Path srcDir = outputDir.resolve("src"); //$NON-NLS-1$
			Files.createDirectories(srcDir);
			fileManager.setLocation(StandardLocation.SOURCE_OUTPUT,
					Arrays.asList(srcDir.toFile()));

			fileManager.setLocation(StandardLocation.CLASS_PATH,
					Arrays.asList(new File("bin/main/java")));

			return srcDir;
		}

		/**
		 * Creates a unique path. If the passed path exists, it is suffixed by a
		 * number.
		 */
		private Path createUniquePath(Path path) throws IOException
		{
			int i = 1;
			String name = path.getFileName().toString();
			while (Files.exists(path))
			{
				path = path.resolveSibling(name + "_" + i); //$NON-NLS-1$
			}

			Files.createDirectories(path);

			return path;
		}
	}

	/** Result of the Java compilation. */
	public class CompilationResult
	{
		/** Whether the compilation was successful. */
		private final boolean success;

		/** The path where fixture data is stored. */
		private final Path fixture;

		/** The path to the output directory. */
		private final Path output;

		/** Constructor. */
		public CompilationResult(boolean success, Path fixture, Path output)
		{
			this.success = success;
			this.fixture = fixture;
			this.output = output;
		}

		/** @see #success */
		public boolean isSuccess()
		{
			return this.success;
		}

		/** Asserts that the compilation was successful. */
		public void assertSuccess()
		{
			Assert.assertTrue("Compilation was not successful", this.success); //$NON-NLS-1$
		}

		/**
		 * Asserts that the output matches the fixture files. Fails if
		 * unexpected generated files occur or files are missing.
		 */
		public void assertOutput() throws IOException
		{
			Set<Path> actualFiles = new HashSet<Path>(
					FileUtil.listFiles(this.output));

			for (Path expected : FileUtil.listFiles(this.fixture))
			{
				Path relative = expected.subpath(this.fixture.getNameCount(),
						expected.getNameCount());
				Path actual = this.output.resolve(relative);

				if (!Files.exists(actual))
				{
					Assert.fail("File " + actual + " has not been written"); //$NON-NLS-1$ //$NON-NLS-2$
				}

				Assert.assertEquals(
						"File " + actual + " has not the expected content", FileUtil.readAllText(expected), FileUtil.readAllText(actual)); //$NON-NLS-1$ //$NON-NLS-2$

				actualFiles.remove(actual);
			}

			Assert.assertEquals("Encountered unespected generated files.", //$NON-NLS-1$
					Collections.<Path> emptySet(), actualFiles);
		}
	}
}
