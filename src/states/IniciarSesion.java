package states;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import sqlconnect.Conexion;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.border.LineBorder;

public class IniciarSesion extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnMostrarContraseña;
	private boolean mostrarContraseña = false;


	public IniciarSesion() {
		setTitle("NanoBank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 800);
		setResizable(false);
		setLocationRelativeTo(null);


		// Cargar la imagen como fondo
		try {
			BufferedImage backgroundImage = ImageIO.read(getClass().getResource("/img/bg_img2.png")); 

			JPanel contentPanel = new JPanel() {
				private static final long serialVersionUID = 1L;

				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				}
			};

			contentPanel.setLayout(new GridBagLayout()); 
			setContentPane(contentPanel);
			initComponents();


		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar la imagen de fondo", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	private void initComponents() {

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));


		// Dimensiones del panel de datos de login
		panel.setPreferredSize(new Dimension(300, 500)); 
		panel.setBackground(Color.LIGHT_GRAY);

		// Logo
		ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_6.png"));
		JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
		lblLogo.setBounds(59, 25, 188, 178);



		// Usuario
		JLabel lblUsuario = new JLabel("DNI:");
		lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuario.setBounds(46, 214, 198, 26);
		lblUsuario.setFont(new Font("Impact", Font.PLAIN, 20));

		JTextField txtUsuario = new JTextField(15);
		txtUsuario.setBounds(46, 251, 201, 26);
		txtUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		txtUsuario.setFont(new Font("Arial", Font.PLAIN, 15));


		// Contraseña
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(46, 288, 198, 26);
		lblContraseña.setFont(new Font("Impact", Font.PLAIN, 20));

		JPasswordField txtContraseña = new JPasswordField(15);
		txtContraseña.setBounds(46, 324, 201, 26);
		txtContraseña.setHorizontalAlignment(JPasswordField.CENTER);
		txtContraseña.setFont(new Font("Arial", Font.PLAIN, 15));


		// Botón de iniciar sesión
		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setBounds(59, 376, 178, 31);
		btnIniciarSesion.setHorizontalAlignment(JButton.CENTER);
		btnIniciarSesion.setFont(new Font("Arial Black", Font.PLAIN, 16));


		// Botón de registrarse
		JButton btnRegistrarse = new JButton("¿No estás registrado?");
		btnRegistrarse.setBounds(22, 432, 255, 31);
		btnRegistrarse.setHorizontalAlignment(JButton.CENTER);
		btnRegistrarse.setFont(new Font("Arial Black", Font.PLAIN, 16));


		// Botón de mostrarContraseña
		btnMostrarContraseña = new JButton();
		ImageIcon iconoMostrarContraseña = new ImageIcon(getClass().getResource("/img/show_pw.png"));
		btnMostrarContraseña.setIcon(iconoMostrarContraseña);
		btnMostrarContraseña.setBounds(257, 324, 26, 26);



		panel.setLayout(null);


		panel.add(btnMostrarContraseña);
		panel.add(lblLogo);
		panel.add(lblUsuario);
		panel.add(txtUsuario);
		panel.add(lblContraseña);
		panel.add(txtContraseña);
		panel.add(btnIniciarSesion);
		panel.add(btnRegistrarse);


		// Agregar el panel al contenido del JFrame
		getContentPane().add(panel);



		// Eventos de los botones
		btnIniciarSesion.addActionListener(e -> iniciarSesion(txtUsuario.getText(), new String(txtContraseña.getPassword())));
		btnRegistrarse.addActionListener(e -> mostrarRegistroUsuario());

		

		// Acción del botón Mostrar Contraseña
		btnMostrarContraseña.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarContraseña = !mostrarContraseña;
				if (mostrarContraseña) {
					txtContraseña.setEchoChar((char) 0);
				} else {
					txtContraseña.setEchoChar('*');
				}
			}
		});

		

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
				double porcentajeRF  = conexion.obtenerRFporIdUsuario(id_usuario);
				double porcentajeRV= conexion.obtenerRVporIdUsuario(id_usuario);
				double cantidadInvertida = conexion.obtenercantidadInvertidaporIdUsuario(id_usuario);

				getContentPane().removeAll();
				getContentPane().setLayout(new BorderLayout());
				getContentPane().add(new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida));
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
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new RegistroUsuario());
		revalidate();
		repaint();
	}
}