package com.mpdeimos.chacy;

import java.io.IOException;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Tests the harness directory.
 */
@RunWith(Parameterized.class)
public class CompleteHarnessSuite extends HarnessTestBase
{
	/** The harness to test. */
	private final String harness;

	/** @return the test harnesses. */
	@Parameters(name = "{0}")
	public static Iterable<String[]> getHarnesses() throws IOException
	{
		return listHarnesses().filter(
				path -> !path.getFileName().toString().startsWith(".")).map(
				harness -> new String[] { harness.getFileName().toString() }).collect(
				Collectors.toList());
	}

	/** Constructor. */
	public CompleteHarnessSuite(String harness)
	{
		this.harness = harness;
	}

	/** Tests the simple harness. */
	@Test
	public void testHarness() throws IOException
	{
		assertHarness(this.harness);
	}

	/** tests the given harness. */
	private void assertHarness(String harness) throws IOException
	{
		CompilationResult result = getHarness(harness).compileFiles();

		result.assertSuccess();
		result.assertOutput();
	}
}
