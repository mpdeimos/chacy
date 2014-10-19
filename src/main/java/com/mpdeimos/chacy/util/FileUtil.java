package com.mpdeimos.chacy.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/** File utility functions. */
public class FileUtil
{
	/** The separator between filename and extension. */
	public static final String EXTENSION_SEPARATOR = ".";

	/** Removes a directory (or file) including all its children. */
	public static void removeRecursive(Path path) throws IOException
	{
		Files.walkFileTree(path, new SimpleFileVisitor<Path>()
		{
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException
			{
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException e)
					throws IOException
			{
				// try to delete the file anyway, even if its attributes
				// could not be read, since delete-only access is
				// theoretically possible
				Files.deleteIfExists(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException e)
					throws IOException
			{
				if (e != null)
				{
					throw e;
				}

				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * Lists all files of a directory. Optionally takes a list of globs that
	 * must be met.
	 */
	public static List<Path> listFiles(Path path, String... globs)
			throws IOException
	{
		if (globs.length == 0)
		{
			globs = new String[] { "**" };
		}

		final List<PathMatcher> matchers = new ArrayList<PathMatcher>(
				globs.length);
		for (String glob : globs)
		{
			matchers.add(path.getFileSystem().getPathMatcher("glob:" + glob));
		}

		final List<Path> files = new ArrayList<Path>();
		Files.walkFileTree(path, new SimpleFileVisitor<Path>()
		{
			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException
			{
				for (PathMatcher matcher : matchers)
				{
					if (matcher.matches(file))
					{
						files.add(file);
						break;
					}
				}
				return FileVisitResult.CONTINUE;
			}
		});

		return files;
	}

	/** Reads all the text from the given file. */
	public static String readAllText(Path path) throws IOException
	{
		return new String(Files.readAllBytes(path));
	}
}
