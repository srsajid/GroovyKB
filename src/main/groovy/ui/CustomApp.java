package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by sajedur on 8/13/2015.
 */

public class CustomApp {

    public static void main(String[] args) throws IOException {

        // load the texture resource image
        System.out.println("Please wait, Loading Texture : http://www.brewsterwallcovering.com/data/default/images/catalog/original/289-5757.jpg");
        MainContainer.textureImg = ImageIO.read(new URL("http://www.brewsterwallcovering.com/data/default/images/catalog/original/289-5757.jpg"));
        System.out.println("Loading finished. Starting the Demo!");

        MainContainer.textureImg = MainContainer.textureImg.getSubimage(0, 0, 200, 200);

        // Starting the Swing GUI in the EDT, learn about it
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Demo: LogIn Dialogue");

                // set frame size as Demo perposes, otherwise not recommended
                frame.setSize(new Dimension(500, 300));
                MainContainer container = new MainContainer();

                frame.add(new MainContainer());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

            }
        });
    }
}