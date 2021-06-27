# dASCII-vinci
This is a java library which can be used by other programs to generate ASCII-art.

There are 3 dependencies:

- A [python script](https://github.com/gomesGabriel/ascii_art) (thus python): Used to process the input image and generate ASCII-art.
- [FFmpeg](https://ffmpeg.org/): Used to break videos into multiple images and joining multiple images into a GIF.
- [wkhtmltoimage](https://wkhtmltopdf.org/): Used to generate images based on HTML.

The install location of this dependencies must be announced to the library. This is done when constructing a class, like this:
```Java

  File pythonScript = new File("C:\\path\\To\\script.py");
  File ffmpegExecutable = File("C:\\path\\To\\wkhtmltoimage.exe");
  File wkhtmltoimageExecutable = new File("C:\\path\\To\\ffmpeg.exe");
  
  DASCIIvinci dasciivinci = new DASCIIvinci(pythonScript, ffmpegExecutable, wkhtmltoimageExecutable);
  
```

# API

The API is exposed in the class `DASCIIvinci`.

- ```Java
  public String generateString(File inputImage);
  ```
  Returns a string that contains the ASCII version of the given image.
  
- ```Java
  public void generateImage(File inputImage, File outputImage)
  ```
  Renders the ascii version of an image. The resulting image is saved to a file.
  
- ```Java
  public void generateGIF(File inputDir, String pattern, File outputGIF)
  ```
  Renders a GIF, based on the ascii version of all files that follow the given pattern inside a certain folder. The resulting GIF is saved to a file.
  \*The pattern is expected to be given in ffmpeg format.
  
- ```Java
  public void breakVideoIntoImages(File inputVideo, int fps, File outputDir, String pattern)
  ```
  Breaks a video into multiple images, following a certain pattern to save them in a certain folder. One must specify the amount of images.
  \*The pattern is expected to be given in ffmpeg format.
