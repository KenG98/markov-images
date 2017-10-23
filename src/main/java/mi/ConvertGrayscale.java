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
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ConvertGrayscale {

  Properties props = null;
  File[] allImages = null;

  public ConvertGrayscale(Properties p) {
    this.props = p;
    this.allImages = getAllImages(p.getProperty("stanfordDogs"));
  }

  public void convert() {
    int maxSize = Integer.parseInt(props.getProperty("images.maxSize"));
    for(File img : allImages) {
      try {
        // the image we're working with
        BufferedImage currentImg = ImageIO.read(img);
        int imgWidth = currentImg.getWidth();
        int imgHeight = currentImg.getHeight();
        // make sure this image is small enough
        if (imgWidth <= maxSize || imgHeight <= maxSize) {
          GrayImage.makeGrayImage(img);
          // limit it to just one image for now.
          break;
        }
      }
      catch (IOException e) {
        System.out.println("IO Error while reading image");
      }
    }
  }

  public void analyze() {
    System.out.println(allImages.length + " images");
    for(File img : allImages) {
      String imgPath = img.getAbsolutePath();
      if (!imgPath.contains(".jpg")) {
        System.out.println("One of the images is not a .jpg! " + imgPath);
      }
    }
    // write all image widths and heights to a file to analyze in excel
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("ImageWidthHeight.csv"));
      for(int i = 0; i < allImages.length; i++){
        if (i % 2000 == 0) System.out.println("On image " + i + "...");
        BufferedImage thisImage = ImageIO.read(allImages[i]);
        int thisWidth = thisImage.getWidth();
        int thisHeight = thisImage.getHeight();
        writer.write(thisWidth + "," + thisHeight + "\n");
      }
      writer.close();
    } 
    catch (IOException ex) {
      System.out.println("IO Error.");
    } 
  }

  private File[] getAllImages(String folder) {
    // the directory with your data
    File dir = new File(folder);
    // a filter which will return all images of interest
    IOFileFilter jpgFilter = FileFilterUtils.suffixFileFilter(".jpg");
    IOFileFilter dirFilter = TrueFileFilter.INSTANCE;
    // a collection of those files
    Collection<File> imgs = FileUtils.listFiles(dir, jpgFilter, dirFilter);
    return imgs.toArray(new File[imgs.size()]);
  }

}
