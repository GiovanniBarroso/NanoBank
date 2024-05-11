package states;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;

    public BackgroundPanel() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/img/bg_img2.png")).getImage(); // Ruta de la imagen de fondo
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}