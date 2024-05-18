package states;

import javax.swing.*;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.sql.SQLException;
import javax.swing.border.LineBorder;
import bgimg.ImagenFondo;

public class RegistroUsuario extends ImagenFondo {

	//Atributos
	private static final long serialVersionUID = 1L;

	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtIBAN;

	private JPasswordField txtContraseña;
	private JButton btnRegistrar;
	private JButton btnMostrarContraseña;
	private JButton btnVolver;

	private boolean mostrarContraseña = false;


	public RegistroUsuario() {

		super("/img/bg_img2.png");
		setLayout(null);


		//Panel de fondo
		JPanel panelFondo = new JPanel();
		panelFondo.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelFondo.setBounds(34, 109, 372, 580);
		panelFondo.setBackground(Color.WHITE);


		//Logo
		ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_7.png"));
		JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
		lblLogo.setBounds(154, 0, 127, 119);
		add(lblLogo);


		//JLabel y JTextField
		JLabel lblDNI = new JLabel("DNI:");
		lblDNI.setBounds(168, 148, 42, 26);
		lblDNI.setFont(new Font("Impact", Font.PLAIN, 20));
		txtDNI = new JTextField(20);
		txtDNI.setBounds(61, 184, 250, 26);
		txtDNI.setHorizontalAlignment(JTextField.CENTER);
		txtDNI.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(151, 220, 76, 26);
		lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));
		txtNombre = new JTextField(20);
		txtNombre.setBounds(61, 256, 250, 26);
		txtNombre.setHorizontalAlignment(JTextField.CENTER);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblTelefono = new JLabel("Teléfono:");
		lblTelefono.setBounds(150, 299, 77, 26);
		lblTelefono.setFont(new Font("Impact", Font.PLAIN, 20));
		txtTelefono = new JTextField(20);
		txtTelefono.setBounds(61, 335, 250, 26);
		txtTelefono.setHorizontalAlignment(JTextField.CENTER);
		txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(161, 370, 49, 26);
		lblEmail.setFont(new Font("Impact", Font.PLAIN, 20));
		txtEmail = new JTextField(20);
		txtEmail.setBounds(61, 406, 250, 26);
		txtEmail.setHorizontalAlignment(JTextField.CENTER);
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblIBAN = new JLabel("IBAN:");
		lblIBAN.setBounds(161, 76, 49, 26);
		lblIBAN.setFont(new Font("Impact", Font.PLAIN, 20));
		txtIBAN = new JTextField(20);
		txtIBAN.setBounds(61, 112, 255, 26);
		txtIBAN.setHorizontalAlignment(JTextField.CENTER);
		txtIBAN.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(133, 441, 101, 26);
		lblContraseña.setFont(new Font("Impact", Font.PLAIN, 20));
		txtContraseña = new JPasswordField(20);
		txtContraseña.setBounds(61, 477, 250, 26);
		txtContraseña.setHorizontalAlignment(JTextField.CENTER);
		txtContraseña.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblRegistroUsuario = new JLabel("REGISTRO USUARIO");
		lblRegistroUsuario.setFont(new Font("Impact", Font.PLAIN, 35));
		lblRegistroUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistroUsuario.setBounds(0, 21, 372, 35);




		//Botones
		btnRegistrar = new JButton();
		ImageIcon FondobtnRegistrarse = new ImageIcon(getClass().getResource("/img/registrarse_btn.png"));
		btnRegistrar.setIcon(FondobtnRegistrarse);
		btnRegistrar.setBounds(61, 525, 108, 35);


		btnMostrarContraseña = new JButton();
		ImageIcon iconoMostrarContraseña = new ImageIcon(getClass().getResource("/img/show_pw.png"));
		btnMostrarContraseña.setIcon(iconoMostrarContraseña);
		btnMostrarContraseña.setBounds(320, 478, 26, 26);


		btnVolver = new JButton();
		ImageIcon FondobtnBack = new ImageIcon(getClass().getResource("/img/volverAtras_btn.png"));
		btnVolver.setIcon(FondobtnBack);
		btnVolver.setBounds(203, 525, 108, 35);
		panelFondo.setLayout(null);



		// RegistroUsuario
		panelFondo.add(lblRegistroUsuario);


		// DNI
		panelFondo.add(lblDNI);
		panelFondo.add(txtDNI);


		// Nombre
		panelFondo.add(lblNombre);
		panelFondo.add(txtNombre);


		// Teléfono
		panelFondo.add(lblTelefono);
		panelFondo.add(txtTelefono);


		// Email
		panelFondo.add(lblEmail);
		panelFondo.add(txtEmail);


		// IBAN
		panelFondo.add(lblIBAN);
		panelFondo.add(txtIBAN);


		// Contraseña
		panelFondo.add(lblContraseña);
		panelFondo.add(txtContraseña);


		// Botón Registrar
		panelFondo.add(btnRegistrar);


		// Botón Mostrar Contraseña
		panelFondo.add(btnMostrarContraseña);


		// Botón Volver a IniciarSesion
		panelFondo.add(btnVolver);


		add(panelFondo);




		// Acción del botón Registrarse
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveUser();
			}
		});



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



		// Acción del botón Volver atrás
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAIniciarSesion();
			}
		});



	}


	//Metodo para validad y registrar al usuario
	private void saveUser() {

		String DNI = txtDNI.getText();
		String nombre = txtNombre.getText();
		String telefono = txtTelefono.getText();
		String email = txtEmail.getText();
		String iban = txtIBAN.getText();
		String contraseña = new String(txtContraseña.getPassword());


		// Validar que todos los campos estén llenos
		if (DNI.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || iban.isEmpty() || contraseña.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!validarTelefono(telefono)) {
			JOptionPane.showMessageDialog(this, "El número de teléfono no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar el formato del correo electrónico
		if (!validarCorreoElectronico(email)) {
			JOptionPane.showMessageDialog(this, "El correo electrónico no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validar el formato del DNI
		if (!validarDNI(DNI)) {
			JOptionPane.showMessageDialog(this, "El DNI no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		// Validar el formato del IBAN
		if (!validarIBAN(iban)) {
			JOptionPane.showMessageDialog(this, "El IBAN no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		Conexion conexionDB = new Conexion();
		try {

			conexionDB.addUser(DNI, contraseña, nombre, Integer.parseInt(telefono), email, iban);
			JOptionPane.showMessageDialog(this, "¡ Cuenta creada correctamente !");

			System.out.println("¡Enhorabuena, tu perfil con nombre " + nombre + " ha sido registrado!\n");
			limpiarCampos();

			// Cerrar la ventana actual de registro
			SwingUtilities.getWindowAncestor(this).dispose();

			// Abrir una nueva instancia de la clase IniciarSesion
			SwingUtilities.invokeLater(() -> new IniciarSesion());

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al registrar usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}



	// Método para volver a IniciarSesion
	private void volverAIniciarSesion() {
		// Cerrar la ventana actual de registro
		SwingUtilities.getWindowAncestor(this).dispose();

		// Abrir una nueva instancia de la clase IniciarSesion
		SwingUtilities.invokeLater(() -> new IniciarSesion());
	}



	// Método para limpiar los campos de entrada
	public void limpiarCampos() {
		txtDNI.setText("");
		txtNombre.setText("");
		txtTelefono.setText("");
		txtEmail.setText("");
		txtIBAN.setText("");
		txtContraseña.setText("");
	}



	// Metodo para validar telefono
	private boolean validarTelefono(String telefono) {

		telefono = telefono.replaceAll("\\s", "").replaceAll("-", "");
		if (telefono.length() != 9) {
			return false;
		}


		for (char c : telefono.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}

		return true;
	}



	// Metodo para validar correo electrónico
	private boolean validarCorreoElectronico(String correo) {
		// El correo electrónico debe contener un "@" y al menos un carácter antes y después del "@"
		if (!correo.contains("@") || correo.indexOf("@") == 0 || correo.indexOf("@") == correo.length() - 1) {
			return false;
		}


		// El correo electrónico debe contener al menos un carácter antes del punto después del "@" y al menos dos caracteres después del punto
		String[] partes = correo.split("@");
		if (partes.length != 2 || partes[1].indexOf(".") == 0 || partes[1].indexOf(".") == partes[1].length() - 1) {
			return false;
		}

		return true;
	}



	//Metodo para validar DNI
	private boolean validarDNI(String dni) {

		if (dni.length() != 9) {
			return false;
		}

		for (int i = 0; i < 8; i++) {
			if (!Character.isDigit(dni.charAt(i))) {
				return false;
			}
		}

		char letra = Character.toUpperCase(dni.charAt(8));
		if (letra < 'A' || letra > 'Z') {
			return false;
		}

		int numero = Integer.parseInt(dni.substring(0, 8));
		char letraCalculada = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);


		return letra == letraCalculada;
	}




	//Metodo para validar IBAN
	public static boolean validarIBAN(String cuenta) {

		boolean esValido = false;
		int i = 2;
		int caracterASCII = 0; 
		int resto = 0;
		int dc = 0;
		String cadenaDc = "";
		BigInteger cuentaNumero = new BigInteger("0"); 
		BigInteger modo = new BigInteger("97");


		cuenta = cuenta.replaceAll(" ", "");


		if(cuenta.length() == 24 && cuenta.substring(0,1).toUpperCase().equals("E") 
				&& cuenta.substring(1,2).toUpperCase().equals("S")) {

			do {
				caracterASCII = cuenta.codePointAt(i);
				esValido = (caracterASCII > 47 && caracterASCII < 58);
				i++;
			}
			while(i < cuenta.length() && esValido); 


			if(esValido) {
				cuentaNumero = new BigInteger(cuenta.substring(4,24) + "142800");
				resto = cuentaNumero.mod(modo).intValue();
				dc = 98 - resto;
				cadenaDc = String.valueOf(dc);
			}	

			if(dc < 10) {
				cadenaDc = "0" + cadenaDc;
			} 


			if(cuenta.substring(2,4).equals(cadenaDc)) {
				esValido = true;
			} else {
				esValido = false;
			}
		}

		return esValido;
	}


}