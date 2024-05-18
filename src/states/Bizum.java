package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sqlconnect.Conexion;
import bgimg.ImagenFondo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Bizum extends ImagenFondo {

	//Atributos
	private static final long serialVersionUID = 1L;
	private JTextField txtTelefono;
	private JTextField txtNombre;
	private JTextField txtCantidad;
	private JTextField txtConcepto;

	private String nombreUsuario;
	private int id_usuario;
	private double saldo;
	private String dni;
	private double porcentajeRF; 
	private double porcentajeRV;
	private double cantidadInvertida;

	private JButton btnEnviarBizum;
	private JButton btnVolver;
	private JLabel lblNewLabel;



	public Bizum(int idUsuario, String nombreUsuario, double saldo, String dni, double porcentajeRF, double porcentajeRV, double cantidadInvertida) {

		super("/img/bg_img2.png");

		//Constructores
		this.id_usuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		this.cantidadInvertida = cantidadInvertida;

		setLayout(null);

		//Declaramos JPanel, JFrame & JTextField
		JPanel panelFondo = new JPanel();
		panelFondo.setBounds(61, 164, 321, 500);
		panelFondo.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelFondo.setBackground(Color.LIGHT_GRAY);

		// Logo
		ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_7.png"));


		JLabel lblTelefono = new JLabel("Nº Teléfono");
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setBounds(72, 89, 177, 26);
		lblTelefono.setFont(new Font("Impact", Font.PLAIN, 20));


		txtTelefono = new JTextField(20);
		txtTelefono.setBounds(56, 121, 211, 26);
		txtTelefono.setHorizontalAlignment(JTextField.CENTER);
		txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(72, 157, 182, 26);
		lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));


		txtNombre = new JTextField(20);
		txtNombre.setBounds(56, 186, 211, 26);
		txtNombre.setHorizontalAlignment(JTextField.CENTER);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblCantidad = new JLabel("Cantidad (500€ Max)");
		lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidad.setBounds(72, 222, 177, 26);
		lblCantidad.setFont(new Font("Impact", Font.PLAIN, 20));


		txtCantidad = new JTextField(20);
		txtCantidad.setBounds(56, 251, 211, 26);
		txtCantidad.setHorizontalAlignment(JTextField.CENTER);
		txtCantidad.setFont(new Font("Arial", Font.PLAIN, 16));


		JLabel lblConcepto = new JLabel("Concepto");
		lblConcepto.setHorizontalAlignment(SwingConstants.CENTER);
		lblConcepto.setBounds(72, 287, 177, 26);
		lblConcepto.setFont(new Font("Impact", Font.PLAIN, 20));


		txtConcepto = new JTextField(20);
		txtConcepto.setBounds(56, 316, 211, 67);
		txtConcepto.setHorizontalAlignment(JTextField.CENTER);
		txtConcepto.setFont(new Font("Arial", Font.PLAIN, 16));


		//Botones
		btnEnviarBizum = new JButton();
		ImageIcon FondobtnRealizarBizum = new ImageIcon(getClass().getResource("/img/bizum_btn.png"));
		btnEnviarBizum.setIcon(FondobtnRealizarBizum);
		btnEnviarBizum.setBounds(93, 398, 134, 26);


		btnVolver = new JButton();
		ImageIcon FondobtnBack2 = new ImageIcon(getClass().getResource("/img/back_btn2.png"));
		btnVolver.setIcon(FondobtnBack2);
		btnVolver.setBounds(93, 440, 134, 26);


		//Añadimos elementos al panel
		panelFondo.setLayout(null);
		panelFondo.add(lblTelefono);
		panelFondo.add(txtTelefono);
		panelFondo.add(lblNombre);
		panelFondo.add(txtNombre);
		panelFondo.add(lblCantidad);
		panelFondo.add(txtCantidad);
		panelFondo.add(lblConcepto);
		panelFondo.add(txtConcepto);
		panelFondo.add(btnEnviarBizum);
		panelFondo.add(btnVolver);

		add(panelFondo);

		lblNewLabel = new JLabel("BIZUM");
		lblNewLabel.setFont(new Font("Impact", Font.BOLD, 35));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 21, 300, 42);
		panelFondo.add(lblNewLabel);
		JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblLogo.setBounds(145, 10, 150, 150);
		add(lblLogo);



		//Evento al pulsar boton realizar bizum
		btnEnviarBizum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveBizum();
			}
		});



		//Evento al pulsar boton volver atras
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volveraMenu();
			}
		});
	}



	//Metodo que verifica y realiza el Bizum
	private void saveBizum() {
		String nombre = txtNombre.getText();
		String telefono = txtTelefono.getText();
		String concepto = txtConcepto.getText();
		String cantidad = txtCantidad.getText();

		//replaceall para eliminar error entre comas y puntos en la cantidad
		cantidad = cantidad.replaceAll(",", ".");


		// Valida que todos los campos estén llenos
		if (nombre.isEmpty() || telefono.isEmpty() || cantidad.isEmpty() || concepto.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		double cantidadEnviar;
		try {
			cantidadEnviar = Double.parseDouble(cantidad);
			if (cantidadEnviar <= 0 || cantidadEnviar > 500) {
				JOptionPane.showMessageDialog(this, "La cantidad a enviar debe ser mayor que 0 y menor que el límite (500€).", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La cantidad a enviar debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Valida que el usuario tenga saldo suficiente
		if (cantidadEnviar > saldo) {
			JOptionPane.showMessageDialog(this, "Saldo insuficiente para realizar el Bizum.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		// Valida que el número de teléfono exista en la base de datos
		Conexion conexionDB = new Conexion();
		try {
			if (!conexionDB.existeTelefono(telefono)) {
				JOptionPane.showMessageDialog(this, "El número de teléfono ingresado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al verificar el número de teléfono: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		// Valida que el nombre del destinatario coincida con el número de teléfono de destino
		try {
			if (!conexionDB.coincideNombreTelefono(telefono, nombre)) {
				JOptionPane.showMessageDialog(this, "El nombre ingresado no coincide con el número de teléfono de destino.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al verificar el nombre del destinatario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		// Realizar el Bizum
		try {
			conexionDB.sendBizum(id_usuario, nombreUsuario, Integer.parseInt(telefono), nombre, concepto, cantidadEnviar);
			JOptionPane.showMessageDialog(this, "¡Bizum realizado con éxito!");

			double nuevoSaldo = conexionDB.obtenerSaldoPorDNI(dni);
			System.out.println("Nuevo saldo: " + nuevoSaldo);
			limpiarCampos();
			volveraMenu2(nuevoSaldo);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al realizar el Bizum: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}



	//Metodo para volver al menu en caso de no realizar un bizum
	private void volveraMenu() {
		Container parent = getParent();
		if (parent != null) {
			parent.removeAll();
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
			parent.add(menu);
			parent.revalidate();
			parent.repaint();

		} else {
			System.out.println("El componente no tiene un padre [🆎].");
		}
	}


	//Metodo para volver al menu en caso de realizar un bizum
	private void volveraMenu2(double nuevoSaldo) {
		Container parent = getParent();
		if (parent != null) {
			parent.removeAll();
			Menu menu = new Menu(id_usuario, nombreUsuario, nuevoSaldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
			parent.add(menu);
			parent.revalidate();
			parent.repaint();

		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}


	//Metodo para limpiar los campos del formulario
	public void limpiarCampos() {
		txtTelefono.setText("");
		txtNombre.setText("");
		txtCantidad.setText("");
		txtConcepto.setText("");
	}
}