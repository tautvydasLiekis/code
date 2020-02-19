package override.swing;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * JPanel draw a image and can to use as background. Also JPanel are opaque by default what means a transparent.
 */
public class JPanel2 extends JPanel {

	private Image backgroundImage;

	  // Some code to initialize the background image.
	  // Here, we use the constructor to load the image. This
	  // can vary depending on the use case of the panel.
	  public JPanel2(String fileName) {
		  this.setOpaque(false);
		  try {
			  backgroundImage = ImageIO.read(new File(fileName));
		  } catch (Exception e) {
			  // Gerai butu exception'a rodyti runtime o ne design time, reikes pasidometi kaip ta padaryti
			 // JOptionPane.showMessageDialog(null, "Error: \n" + e.getClass().getName() + ": " + e.getMessage());
		  }
	  }

	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }
}
