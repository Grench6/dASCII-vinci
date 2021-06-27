package base;

import java.io.File;
import java.util.LinkedList;

import utils.FileUtils;
import utils.ParseUtils;
import utils.Shell;

public class DASCIIvinci
{

	private final File pythonScript;
	private final File ffmpegExecutable;
	private final File wkhtmltoimageExecutable;

	private File tempDir;
	private Shell shell;

	public DASCIIvinci(File pythonScript, File ffmpegExecutable, File wkhtmltoimageExecutable)
	{
		this.pythonScript = pythonScript;
		this.ffmpegExecutable = ffmpegExecutable;
		this.wkhtmltoimageExecutable = wkhtmltoimageExecutable;
		shell = new Shell(true);
		tempDir = FileUtils.createTempDir();
	}

	public String generateString(File inputImage)
	{
		shell.smartExec("python " + pythonScript.getAbsolutePath() + " " + inputImage.getAbsolutePath());
		return ParseUtils.linkedStringsToString(shell.stdout, false);
	}

	public void generateImage(File inputImage, File outputImage)
	{
		shell.smartExec("python " + pythonScript.getAbsolutePath() + " " + inputImage.getAbsolutePath());
		shell.smartExec(wkhtmltoimageExecutable.getAbsolutePath() + " --width 0 " + generateHTMLTemplate(shell.stdout).getAbsolutePath() + " " + outputImage.getAbsolutePath());
		FileUtils.cleanDir(tempDir);
	}

	public void generateGIF(File inputDir, String pattern, File outputGIF)
	{
		shell.smartExec(ffmpegExecutable.getAbsolutePath() + " -f image2 -i " + inputDir.getAbsolutePath() + "\\" + pattern + " " + outputGIF.getAbsolutePath());
	}

	public void breakVideoIntoImages(File inputVideo, int fps, File outputDir, String pattern)
	{
		shell.smartExec(ffmpegExecutable.getAbsolutePath() + " -i " + inputVideo.getAbsolutePath() + " -vf fps=" + fps + " " + outputDir.getAbsolutePath() + "\\" + pattern);
	}

	private File generateHTMLTemplate(LinkedList<String> rawLines)
	{
		String content = "";
		content += ("<!DOCTYPE html>") + '\n';
		content += ("<html>") + '\n';
		content += ("<body>") + '\n';
		content += ("<p style=\"font-family:Lucida Console;font-size:10px; color:#FFFFFF; background-color:#000000;text-align:center;\">") + '\n';
		for (String currentline : rawLines)
			content += "<nobr>" + currentline + "</nobr><br>\n";
		content += ("</p>") + '\n';
		content += ("</body>") + '\n';
		content += ("</html>") + '\n';

		File result = FileUtils.createTempFile(tempDir, "tmp", ".html");
		FileUtils.writeStringToFile(result, content);
		return result;
	}
}
