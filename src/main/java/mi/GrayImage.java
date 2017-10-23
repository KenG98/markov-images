package mi;

import java.util.Collection;
import java.util.Properties;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

// Don't need a lot of the above imports...
// remove the unused ones

public class GrayImage {

  byte[][] pixels;

  public GrayImage() {
    // Does nothing
  }

  public static void displayGrayImage(GrayImage gi) {
    // Does nothing yet
  }

  // expects a file with pixel values in it
  public static GrayImage getGrayImage(File grayImagePath) {
    return new GrayImage();
  }

  // expects a file with an image
  public static boolean makeGrayImage(File imgPath) {
    try {
      // the image we're working with
      BufferedImage currentImg = ImageIO.read(imgPath);
      // write the gray image to this file
      BufferedWriter grayWriter = new BufferedWriter(
        new FileWriter("./gray/" + imgPath.getName() + ".gray"));
      // get the sizes
      int imgWidth = currentImg.getWidth();
      int imgHeight = currentImg.getHeight();
      // go through x (width) and y (height)
      for(int x = 0; x < imgWidth; x++){
        for(int y = 0; y < imgHeight; y++){
          // get the color of the pixel
          Color c = new Color(currentImg.getRGB(x, y));
          // change it to a grayscale value between 0 and 255
          // the algorithm is described here:
          // https://www.johndcook.com/blog/2009/08/24/algorithms-convert-color-grayscale/
          int grayVal = (int) Math.floor(0.21 * c.getRed() + 0.72 * c.getGreen() + 0.07 * c.getBlue());
          grayWriter.write(grayVal + ",");
        }
        grayWriter.write("\n");
      }
      grayWriter.close();
      return true;
    }
    catch (IOException e) {
      System.out.println("IO Exception.");
      return false;
    }
  }

}
