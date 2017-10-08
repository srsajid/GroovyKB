package ui

import javax.imageio.ImageIO
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class SwingProgressBarExample extends JPanel {

    JProgressBar pbar;

    static final int MY_MINIMUM = 0;

    static final int MY_MAXIMUM = 100;

    public SwingProgressBarExample() {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        add(pbar);
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }

    public static void main(String[] args) {
        System.out.println("Please wait, Loading Texture : http://www.brewsterwallcovering.com/data/default/images/catalog/original/289-5757.jpg");
        MainContainer.textureImg = ImageIO.read(new URL("http://www.brewsterwallcovering.com/data/default/images/catalog/original/289-5757.jpg"));
        System.out.println("Loading finished. Starting the Demo!");

        MainContainer.textureImg = MainContainer.textureImg.getSubimage(0, 0, 200, 200);
        final SwingProgressBarExample it = new SwingProgressBarExample();

        JFrame frame = new JFrame("Progress Bar Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        def loginpanel = new MainContainer()
        frame.add(loginpanel)
        frame.pack();
        frame.setVisible(true);
        Thread.sleep(10000);
        loginpanel.setVisible(false)
        frame.add(it);
        it.setVisible(true)
        for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        it.updateBar(percent);
                    }
                });
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }

    }
}
