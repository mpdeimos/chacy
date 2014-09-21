package com.mpdeimos.chacy;

import java.io.IOException;

import org.junit.Test;

public class CompleteHarnessTest extends HarnessTestBase
{
	private static final String SIMPLE = "simple";

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
