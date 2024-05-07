package states;

import javax.swing.*;
import javax.swing.border.Border;
import sqlconnect.Conexion;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class IniciarSesion extends JFrame {
	private static final long serialVersionUID = 1L;


	public IniciarSesion() {

		//Propiedades del JFrame
		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 750);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.ORANGE);
		setLayout(new GridBagLayout());

		initComponents();
	}


	private void initComponents() {

		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			//Diseño del JFrame
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				int radius = 10;
				int width = getWidth();
				int height = getHeight();
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setColor(getBackground());
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.fillRoundRect(0, 0, width - 1, height - 1, radius, radius);
				g2d.dispose();
			}
		};


		//Dimensiones del panel de datos de login
		panel.setPreferredSize(new Dimension(300, 500)); 
		panel.setBackground(Color.GRAY);

		// Establecer el borde redondeado
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		panel.setBorder(BorderFactory.createCompoundBorder(border, new RoundedBorder(10)));

		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);



		// Logo
		ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_6.png"));
		JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));



		// Usuario
		JLabel lblUsuario = new JLabel("DNI:");
		lblUsuario.setFont(new Font("Impact", Font.PLAIN, 20));
		JTextField txtUsuario = new JTextField(15);
		txtUsuario.setHorizontalAlignment(JTextField.CENTER);
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16));


		// Contraseña
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setFont(new Font("Impact", Font.PLAIN, 20));
		JPasswordField txtContraseña = new JPasswordField(15);
		txtContraseña.setHorizontalAlignment(JPasswordField.CENTER);
		txtContraseña.setFont(new Font("Arial", Font.PLAIN, 16));


		// Botón de iniciar sesión
		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setHorizontalAlignment(JButton.CENTER);
		btnIniciarSesion.setFont(new Font("Arial Black", Font.PLAIN, 16));


		// Botón de registrarse
		JButton btnRegistrarse = new JButton("¿No estás registrado?");
		btnRegistrarse.setHorizontalAlignment(JButton.CENTER);
		btnRegistrarse.setFont(new Font("Arial Black", Font.PLAIN, 16));



		// Añadir el logo
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(lblLogo, gbc);
		gbc.gridwidth = 1;


		// Usuario
		gbc.gridy = 1;
		panel.add(lblUsuario, gbc);

		gbc.gridy = 2;
		panel.add(txtUsuario, gbc);


		// Contraseña
		gbc.gridy = 3;
		panel.add(lblContraseña, gbc);

		gbc.gridy = 4;
		panel.add(txtContraseña, gbc);


		// Botón de iniciar sesión
		gbc.gridy = 5;
		panel.add(btnIniciarSesion, gbc);


		// Botón de registrarse
		gbc.gridy = 6;
		panel.add(btnRegistrarse, gbc);


		// Agregar el panel al contenido del JFrame
		getContentPane().add(panel);



		// Eventos de los botones
		btnIniciarSesion.addActionListener(e -> iniciarSesion(txtUsuario.getText(), new String(txtContraseña.getPassword())));
		btnRegistrarse.addActionListener(e -> mostrarRegistroUsuario());


		// Evento de presionar "Enter" en el campo de contraseña
		txtContraseña.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iniciarSesion(txtUsuario.getText(), new String(txtContraseña.getPassword()));
			}
		});



		setVisible(true);
	}



	//Metodo para validad credenciales y entrar en la app
	private void iniciarSesion(String dni, String contraseña) {
		try {
			Conexion conexion = new Conexion();

			if (conexion.validarCredenciales(dni, contraseña)) {

				int id_usuario = conexion.obtenerIdPorDNI(dni); 
				String nombreUsuario = conexion.obtenerNombrePorDNI(dni);
				double saldo = conexion.obtenerSaldoPorDNI(dni);

				getContentPane().removeAll();
				getContentPane().add(new Menu(id_usuario, nombreUsuario, saldo, dni));
				revalidate();
				repaint();

			} else {
				JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error de conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}



	//Metodo para cambiar a la clase RegistroUsuario
	private void mostrarRegistroUsuario() {
		getContentPane().removeAll();
		getContentPane().add(new RegistroUsuario());
		revalidate();
		repaint();
	}



	//Metodo del diseño del JFrame
	static class RoundedBorder implements Border {
		private int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}

		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
		}

		public boolean isBorderOpaque() {
			return true;
		}

		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.setColor(Color.BLACK);
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
		}
	}
}
