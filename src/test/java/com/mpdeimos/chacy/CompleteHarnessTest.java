package com.mpdeimos.chacy;

import java.io.IOException;

import org.junit.Test;

/**
 * Tests the harness directory. TODO change this to be a test suite.
 */
public class CompleteHarnessTest extends HarnessTestBase
{
	/** Name of the simple test harness. */
	private static final String SIMPLE = "simple";

	/** Tests the simple harness. */
	@Test
	public void testSimpleHarness() throws IOException
	{
		assertHarness(SIMPLE);
	}

	/** tests the given harness. */
	private void assertHarness(String harness) throws IOException
	{
		CompilationResult result = getHarness(harness).compileFiles();

		result.assertSuccess();
		result.assertOutput();
	}
}
