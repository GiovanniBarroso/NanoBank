package states;

import javax.swing.*;
import java.awt.*;


public class GestionarCarteras extends JPanel {
	private static final long serialVersionUID = 1L;
	private int id_usuario;
	private double saldo;
	private String nombreUsuario;
	private String dni;
	private int porcentajeRF; 
	private int porcentajeRV; 


	public GestionarCarteras(int id_usuario, String nombreUsuario, double saldo, String dni, int porcentajeRF, int porcentajeRV) {

		// Atributos
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;

		setLayout(new GridBagLayout()); 


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);



		// Panel principal con tamaño 300x200
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setPreferredSize(new Dimension(300, 200));
		panelPrincipal.setBackground(Color.GRAY); 
		panelPrincipal.setLayout(new BorderLayout()); 




		// Panel secundario con tamaño 290x190
		JPanel panelSecundario = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Dibuja el gráfico de pastel con los porcentajes proporcionados
				int diametro = Math.min(getWidth() / 3, getHeight());
				int x = getWidth() - diametro - 30;
				int y = (getHeight() - diametro) / 2;

				// Dibuja la porción de renta fija
				g.setColor(Color.GREEN);
				g.fillArc(x, y, diametro, diametro, 0, (int)(360 * porcentajeRF));


				// Dibuja la porción de renta variable
				g.setColor(Color.RED);
				g.fillArc(x, y, diametro, diametro, (int)(360 * porcentajeRF), (int)(360 * porcentajeRV));


				// Dibuja el borde del círculo y las líneas divisorias
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(2));
				g2d.setColor(Color.BLACK);
				g2d.drawArc(x, y, diametro, diametro, 0, 360);


				// Línea para renta fija
				double anguloRentaFija = 360 * porcentajeRF;
				double radianesRentaFija = Math.toRadians(anguloRentaFija);
				int xRentaFija = x + (int)(diametro / 2 * (1 + Math.cos(radianesRentaFija)));
				int yRentaFija = y + (int)(diametro / 2 * (1 - Math.sin(radianesRentaFija)));
				g2d.drawLine(x + diametro / 2, y + diametro / 2, xRentaFija, yRentaFija);
			}
		};


		panelSecundario.setPreferredSize(new Dimension(290, 190));
		panelSecundario.setBackground(Color.LIGHT_GRAY);
		panelSecundario.setLayout(new FlowLayout());


		panelPrincipal.add(panelSecundario, BorderLayout.CENTER);


		JLabel etiqueta = new JLabel("Contenido del Panel Secundario");
		panelSecundario.add(etiqueta);




		gbc.gridx = 0;
		gbc.gridy = 0;
		add(panelPrincipal, gbc);



		// Componentes como título y botones
		gbc.gridy++;
		JLabel lblTitulo = new JLabel("Gestionar Carteras");
		lblTitulo.setFont(new Font("Impact", Font.PLAIN, 20));
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lblTitulo, gbc);



		gbc.gridy++;
		JButton btnNuevaCartera = new JButton("Añadir Nueva Cartera");
		btnNuevaCartera.setFont(new Font("Arial", Font.PLAIN, 16));
		add(btnNuevaCartera, gbc);
	}
}
