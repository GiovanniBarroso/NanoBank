package bgimg;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagenFondo extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image backgroundImage;

	public ImagenFondo(String imagePath) {
		backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}