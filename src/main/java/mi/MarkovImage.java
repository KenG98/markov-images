package mi;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MarkovImage {

  Properties props = null;
  String action = null;

  public MarkovImage(Properties p) {
    this.props = p;
  }

  private void execute() {
    action = props.getProperty("action");
    switch (action) {
      case "convert to grayscale":
        System.out.println("Converting images to grayscale...");
        convertToGrayscale();
        break;
      case "create markov chain": 
        System.out.println("creating the markov chain...");
        createMarkovChain();
        break;
      case "generate new image":
        System.out.println("Generating a new image...");
        generateNewImage();
        break;
      default:
        System.out.println("Unrecognized \"action\" in config file.");
        break;
    }
  }

  private void convertToGrayscale() {
    ConvertGrayscale cg = new ConvertGrayscale(props);
    cg.convert();
  }

  private void createMarkovChain() {
    
  }

  private void generateNewImage() {
    
  }

  public static void main(String[] args) {
    Properties prop = new Properties();
    InputStream input = null;

    if (args.length < 1) {
      System.out.println("Please pass MarkovImage a configuration file");
    } 

    else {
      try {
        input = new FileInputStream(args[0]);
        prop.load(input);
        MarkovImage mi = new MarkovImage(prop);
        mi.execute();
      } 

      catch (IOException ex) {
        ex.printStackTrace();
      } 

      finally {
        if (input != null) {
          try {
            input.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
}
