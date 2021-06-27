package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Shell
{
	public boolean success;
	public int exitCode;
	public LinkedList<String> stdout;
	public LinkedList<String> stderr;
	private boolean panicOnError;

	public Shell(boolean _panicOnError)
	{
		exitCode = -1;
		success = false;
		stdout = new LinkedList<String>();
		stderr = new LinkedList<String>();
		panicOnError = _panicOnError;
	}

	public void printStdout()
	{
		for (int i = 0; i < stdout.size(); i++)
			System.out.println(stdout.get(i));
	}

	public void printStderr()
	{
		for (int i = 0; i < stderr.size(); i++)
			System.out.println(stderr.get(i));
	}

	protected void saveStdout(Process process)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null)
				stdout.add(line);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void saveStderr(Process process)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line = "";
			while ((line = reader.readLine()) != null)
				stderr.add(line);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected int waitProcess(Process process)
	{
		int result = 1;
		try
		{
			result = process.waitFor();
		} catch (Exception e)
		{
			e.printStackTrace();
			result = 1;
		}
		return result;
	}

	public void smartExec(String command)
	{
		stdout.clear();
		stderr.clear();
		Process process = exec(command, "");
		saveStdout(process);
		exitCode = waitProcess(process);

		if (exitCode == 0)
		{
			success = true;
		} else
		{
			success = false;
			saveStderr(process);
			if (panicOnError)
			{
				System.out.println("!Error while executing [" + command + "]!");
				System.out.println("!The program will abort after showing stderr!");
				printStderr();
				System.exit(1);
			}
		}
	}

	protected Process exec(String command, String dir)
	{
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd.exe", "/c", command);
		if (dir.compareTo("") != 0)
			processBuilder.directory(new File(dir));

		try
		{
			Process process = processBuilder.start();
			return process;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
