package base;

import java.io.File;
import utils.FileUtils;

/* This is an example on the usage of the library. 
 * It creates a GIF based on the output of rendering a short 3D-animation in Blender.
*/

public class Main
{

	final static File pythonScript = new File("C:\\Users\\David\\Desktop\\workspc\\deps\\ascii.py");
	final static File wkhtmltoimage = new File("C:\\Users\\David\\Desktop\\workspc\\deps\\wkhtmltox\\bin\\wkhtmltoimage.exe");
	final static File ffmpeg = new File("C:\\Users\\David\\Desktop\\workspc\\deps\\ffmpeg-4.4-full_build\\bin\\ffmpeg.exe");

	private static DASCIIvinci dasciivinci;

	public static void main(String[] args)
	{
		dasciivinci = new DASCIIvinci(pythonScript, ffmpeg, wkhtmltoimage);
		File inputDir = new File("C:\\Users\\David\\Desktop\\workspc\\input");
		File outputGIF = new File("C:\\Users\\David\\Desktop\\workspc\\output\\out.gif");
		animation3DToGIF(inputDir, 240, outputGIF);
	}

	public static void animation3DToGIF(File inputDir, int images, File outputGIF)
	{
		File tempDir = FileUtils.createTempDir();
		System.out.println("Generating images...");
		for (int i = 1; i <= images; i++)
		{
			dasciivinci.generateImage(new File(inputDir, formatIntLikeBlender(i, 4) + ".png"), new File(tempDir, i + ".png"));
			System.out.println(i + "/" + images);
		}
		dasciivinci.cleanTempDir();
		System.out.println("Creating gif...");
		dasciivinci.generateGIF(tempDir, "%d.png", outputGIF);
		System.out.println("Done!");
		FileUtils.cleanDir(tempDir);
		FileUtils.deleteDir(tempDir);
	}

	private static String formatIntLikeBlender(int number, int mindigits)
	{
		String result = "" + number;

		for (int i = 0; i < mindigits - digitsInNumber(number); i++)
			result = "0" + result;

		return result;
	}

	private static int digitsInNumber(int number)
	{
		int result = 0;

		do
		{
			number /= 10;
			result++;
		} while (number > 0);

		return result;
	}
}
