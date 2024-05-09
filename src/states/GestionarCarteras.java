package states;

import javax.swing.*;
import javax.swing.border.Border;

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

		initUI();
	}

	private void initUI() {

		setBackground(Color.ORANGE);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		// Panel de Carteras
		JPanel panelCarteras = crearPanelCarteras();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.5;
		gbc.fill = GridBagConstraints.BOTH;
		add(panelCarteras, gbc);

		// Título
		JLabel lblTitulo = new JLabel("Gestionar Carteras");
		lblTitulo.setFont(new Font("Impact", Font.PLAIN, 20));
		lblTitulo.setHorizontalAlignment(JLabel.CENTER);
		gbc.gridy++;
		gbc.weighty = 0.1;
		add(lblTitulo, gbc);

		// Botón para añadir nueva cartera
		JButton btnNuevaCartera = new JButton("Añadir Nueva Cartera");
		btnNuevaCartera.setFont(new Font("Arial", Font.PLAIN, 16));
		gbc.gridy++;
		gbc.weighty = 0.1;
		add(btnNuevaCartera, gbc);
	}

	private JPanel crearPanelCarteras() {
		
		int porcentajeRF = 30;
		int porcentajeRV = 70;
		
		
		
		JPanel panelCarteras = new JPanel(new BorderLayout());
		panelCarteras.setPreferredSize(new Dimension(300, 200));
		panelCarteras.setBackground(Color.GRAY);

		// Crear un borde compuesto con un borde negro
		Border border = BorderFactory.createLineBorder(Color.BLACK,3);

		// Establecer el borde al panel principal
		panelCarteras.setBorder(border);

		// Etiqueta de Carteras
		JLabel lblCarteras = new JLabel("CARTERA INDEXADA");
		lblCarteras.setFont(new Font("Arial", Font.BOLD, 18));
		lblCarteras.setHorizontalAlignment(JLabel.CENTER);
		lblCarteras.setForeground(Color.WHITE);
		lblCarteras.setOpaque(true);
		lblCarteras.setBackground(Color.DARK_GRAY);
		panelCarteras.add(lblCarteras, BorderLayout.NORTH);

		// Panel de información de la cartera
		JPanel panelInfoCartera = new JPanel();
		panelInfoCartera.setLayout(new GridLayout(3, 2, 10, 10));
		panelInfoCartera.setBackground(Color.LIGHT_GRAY);
		panelInfoCartera.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Componentes de información de la cartera
		JLabel lblBeneficios = new JLabel("Beneficios: +96,04€");
		JLabel lblInversion = new JLabel("Inversión: 1.041,00€");
		JLabel lblValorMercado = new JLabel("Valor Mercado: 1.139.52€");
		JLabel lblRentabilidad = new JLabel("Rentabilidad: 9,23%");
		JLabel lblRiesgo = new JLabel("Riesgo: X%");
		JLabel lblComposicion = new JLabel("Composición de la cartera");

		// Añadir componentes al panel de información
		panelInfoCartera.add(lblBeneficios);
		panelInfoCartera.add(lblInversion);
		panelInfoCartera.add(lblValorMercado);
		panelInfoCartera.add(lblRentabilidad);
		panelInfoCartera.add(lblRiesgo);
		panelInfoCartera.add(lblComposicion);

		panelCarteras.add(panelInfoCartera, BorderLayout.CENTER);


		// Panel para el gráfico de pastel y las etiquetas
		JPanel panelGraficoYEtiquetas = new JPanel(new BorderLayout());
		panelGraficoYEtiquetas.setBackground(Color.LIGHT_GRAY);
		panelGraficoYEtiquetas.setPreferredSize(new Dimension(290, 190));

		// Panel para las etiquetas
		JPanel panelEtiquetas = new JPanel(new GridLayout(2, 1));
		panelEtiquetas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelEtiquetas.setBackground(Color.LIGHT_GRAY);
		JLabel lblRentaVariable = new JLabel(porcentajeRV + "% RENTA VARIABLE");
		lblRentaVariable.setFont(new Font("Arial", Font.BOLD, 12));
		JLabel lblRentaFija = new JLabel(porcentajeRF + "% RENTA FIJA");
		lblRentaFija.setFont(new Font("Arial", Font.BOLD, 12));
		panelEtiquetas.add(lblRentaVariable);
		panelEtiquetas.add(lblRentaFija);

		// Añadir el panel de etiquetas a la izquierda
		panelGraficoYEtiquetas.add(panelEtiquetas, BorderLayout.WEST);


		// Panel de gráfico de pastel
		JPanel panelGraficoPastel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				 // Calcula el diámetro como el mínimo entre la anchura y la altura del panel
		        int diametro = Math.min(getWidth(), getHeight()) - 60; // 60 para el padding
		        // Calcula las coordenadas x e y para centrar el círculo en el panel
		        int x = (getWidth() - diametro) / 2;
		        int y = (getHeight() - diametro) / 2;

		        // Dibuja la porción de renta fija
		        g.setColor(Color.GREEN);
		        g.fillArc(x, y, diametro, diametro, 0, (int) (360 * porcentajeRF / 100.0));

		        // Dibuja la porción de renta variable
		        g.setColor(Color.RED);
		        g.fillArc(x, y, diametro, diametro, (int) (360 * porcentajeRF / 100.0), (int) (360 * porcentajeRV / 100.0));

		        // Dibuja el borde del círculo y las líneas divisorias
		        Graphics2D g2d = (Graphics2D) g;
		        g2d.setStroke(new BasicStroke(2));
		        g2d.setColor(Color.BLACK);
		        g2d.drawOval(x, y, diametro, diametro);

				// Línea para renta fija
				double anguloRentaFija = 360 * porcentajeRF;
				double radianesRentaFija = Math.toRadians(anguloRentaFija);
				int xRentaFija = x + (int) (diametro / 2 * (1 + Math.cos(radianesRentaFija)));
				int yRentaFija = y + (int) (diametro / 2 * (1 - Math.sin(radianesRentaFija)));
				g2d.drawLine(x + diametro / 2, y + diametro / 2, xRentaFija, yRentaFija);
			}
		};



		panelGraficoPastel.setPreferredSize(new Dimension(290, 190));
		panelGraficoPastel.setBackground(Color.LIGHT_GRAY);
		// Añadir el panel del gráfico a la derecha
		panelGraficoYEtiquetas.add(panelGraficoPastel, BorderLayout.CENTER);

		// Añadir el panel combinado al panel de Carteras
		panelCarteras.add(panelGraficoYEtiquetas, BorderLayout.SOUTH);

		return panelCarteras;

	}
}