package com.mpdeimos.chacy;

import com.mpdeimos.chacy.util.FileUtil;

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
import java.util.stream.Stream;

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

/** Base class for Harness tests. */
public abstract class HarnessTestBase
{
	/** The path where the harness source data is stored. */
	private static final String HARNESS_DATA = "src/test/resources/harness";

	/** Rule to catch the JUnit test name. */
	@Rule
	public TestName testName = new TestName();

	/** Cleans the test output directory for this method. */
	@Before
	public void cleanTestTemp() throws IOException
	{
		FileUtil.removeRecursive(getTestOutput());
	}

	/** @return the output directory of the test. */
	private Path getTestOutput()
	{
		return Paths.get("test-tmp",
				this.getClass().getCanonicalName(),
				this.testName.getMethodName());
	}

	/** @return a harness for the given name that allows compilations. */
	protected Harness getHarness(String harness)
	{
		return new Harness(getTestOutput(), harness);
	}

	/** @return The available harnesses. */
	protected static Stream<Path> listHarnesses() throws IOException
	{
		return Files.list(Paths.get(HARNESS_DATA));
	}

	/** Allows access and compilation of harness data. */
	protected class Harness
	{
		/** The output directory for the harness. */
		private final Path output;

		/** The name of the harness. */
		private final String harness;

		/**
		 * The annotation processor for compilation. Default is
		 * {@link ChacyProcessor}.
		 */
		private Processor processor = new ChacyProcessor();

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

			List<String> options = null;
			if (Boolean.parseBoolean(System.getProperty(ChacyProcessor.DEBUG_TEMPLATE)))
			{
				options = Arrays.asList(String.format("-A%s=true",
						ChacyProcessor.DEBUG_TEMPLATE));
			}

			CompilationTask task = compiler.getTask(
					null,
					fileManager,
					null,
					options,
					null,
					compilationUnits);

			task.setProcessors(Arrays.asList(this.processor));

			return new CompilationResult(task.call(), Paths.get(HARNESS_DATA,
					this.harness, "fixture"), outputDir);
		}

		/** @return the compilation units (source files) found in a folder. */
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

			Path binDir = outputDir.resolve("bin");
			Files.createDirectories(binDir);
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
					Arrays.asList(binDir.toFile()));

			Path srcDir = outputDir.resolve("src");
			Files.createDirectories(srcDir);
			fileManager.setLocation(StandardLocation.SOURCE_OUTPUT,
					Arrays.asList(srcDir.toFile()));

			fileManager.setLocation(StandardLocation.CLASS_PATH,
					Arrays.asList(new File("bin/ifc/java")));

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
				path = path.resolveSibling(name + "_" + i);
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
			Assert.assertTrue("Compilation was not successful", this.success);
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
					Assert.fail("File " + actual + " has not been written");
				}

				Assert.assertEquals(
						"File " + actual + " has not the expected content",
						FileUtil.readAllText(expected),
						FileUtil.readAllText(actual));

				actualFiles.remove(actual);
			}

			Assert.assertEquals("Encountered unexpected generated files.",
					Collections.<Path> emptySet(),
					actualFiles);
		}
	}
}
