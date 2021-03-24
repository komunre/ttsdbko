import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class DeckLoader {
    String directory;
    BufferedImage[] images = new BufferedImage[7 * 10];
    void load(String dir){
        directory = dir;
        File builder = new File(directory + "builder");
        int index = 0;
        try {
            Scanner scanner = new Scanner(builder);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] splitted = line.split(" ");
                int counter = -1;
                try {
                    counter = Integer.parseInt(splitted[0]);
                }
                catch (NumberFormatException e){
                    System.out.println("Syntax error");
                }
                File imageFile = new File(directory + splitted[1]);
                System.out.println("Adding " + splitted[1]);
                try {
                    BufferedImage image = ImageIO.read(imageFile);
                    if (index + counter - 1 > 68){
                        System.out.println("Maximum cards in deck 69");
                        return;
                    }
                    if (counter != 0) {
                        for (int x = 0; x < counter; x++) {
                            images[index] = image;
                            index++;
                        }
                    }
                    else {
                        images[69] = image;
                    }
                }
                catch (IOException e) {
                    System.out.println("Can't read file: " + directory + imageFile);
                }
            }
        }
        catch (IOException e){
            System.out.println("Can't read file: " + directory + "builder");
        }

        System.out.println("Exporting...");
        int chunkWidth = images[0].getWidth();
        int chunkHeight = images[0].getHeight();

        BufferedImage finalImg = new BufferedImage(chunkWidth * 10, chunkHeight * 7, images[0].getType());
        int num = 0;
        for (int y = 0; y < 7; y++){
            for (int x = 0; x < 10; x++){
                BufferedImage image;
                if (images[num] == null){
                    image = new BufferedImage(chunkWidth, chunkHeight, images[0].getType());
                    Graphics2D graphics = image.createGraphics();
                    graphics.setPaint(new Color(0, 0, 0));
                    graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
                }
                else{
                    image = images[num];
                }
                int finalX = chunkWidth * x;
                finalImg.createGraphics().drawImage(image, finalX, chunkHeight * y, null);
                num++;
            }
        }

        try {
            ImageIO.write(finalImg, "png", new File(directory + "deck.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Exported");
    }
}
