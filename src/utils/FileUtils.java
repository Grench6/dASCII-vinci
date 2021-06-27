package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils
{

	public static void writeStringToFile(File file, String string)
	{
		if (!file.exists())
		{
			try
			{
				file.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
		}

		try (FileWriter myWriter = new FileWriter(file, false))
		{
			myWriter.write(string);
		} catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
	}

	public static void deleteDir(File file)
	{
		File[] contents = file.listFiles();
		if (contents != null)
		{
			for (File f : contents)
			{
				deleteDir(f);
			}
		}
		file.delete();
	}

	public static void cleanDir(File file)
	{
		if (!file.exists())
			return;
		File[] contents = file.listFiles();
		if (contents != null)
		{
			for (File f : contents)
			{
				deleteDir(f);
			}
		}
	}

	public static File createTempFile(File parentDir, String suffix, String prefix)
	{
		File result = null;
		try
		{
			result = File.createTempFile(suffix, prefix, parentDir);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static File createTempDir()
	{
		File result = null;
		try
		{
			result = Files.createTempDirectory(null).toFile();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
}
