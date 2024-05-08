package states;

import javax.swing.*;
import javax.swing.border.Border;

import sqlconnect.Conexion;
import states.IniciarSesion.RoundedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Bizum extends JPanel {

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
	private int porcentajeRF; 
	private int porcentajeRV;

	private JButton btnEnviarBizum;
	private JButton btnVolver;



	public Bizum(int idUsuario, String nombreUsuario, double saldo, String dni) {

		//Constructores
		this.id_usuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;

		setLayout(new BorderLayout());

		JPanel panelFondo = new JPanel();
		panelFondo.setBackground(Color.GRAY);
		panelFondo.setLayout(new GridBagLayout());

		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panelFondo.setBorder(BorderFactory.createCompoundBorder(border, new RoundedBorder(10)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel lblTelefono = new JLabel("Nº Teléfono");
		lblTelefono.setFont(new Font("Impact", Font.PLAIN, 20));
		txtTelefono = new JTextField(20);
		txtTelefono.setHorizontalAlignment(JTextField.CENTER);
		txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNombre = new JLabel("Nombre Destinatario");
		lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));
		txtNombre = new JTextField(20);
		txtNombre.setHorizontalAlignment(JTextField.CENTER);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblCantidad = new JLabel("Cantidad a enviar (500.00€)");
		lblCantidad.setFont(new Font("Impact", Font.PLAIN, 20));
		txtCantidad = new JTextField(20);
		txtCantidad.setHorizontalAlignment(JTextField.CENTER);
		txtCantidad.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblConcepto = new JLabel("Concepto");
		lblConcepto.setFont(new Font("Impact", Font.PLAIN, 20));
		txtConcepto = new JTextField(20);
		txtConcepto.setHorizontalAlignment(JTextField.CENTER);
		txtConcepto.setFont(new Font("Arial", Font.PLAIN, 16));

		btnEnviarBizum = new JButton("Realizar Bizum");
		btnVolver = new JButton("Volver atrás");



		//Distribución del grid de botones y textfield
		gbc.gridy++;
		panelFondo.add(lblTelefono, gbc);
		gbc.gridy++;
		panelFondo.add(txtTelefono, gbc);
		gbc.gridy++;
		panelFondo.add(lblNombre, gbc);
		gbc.gridy++;
		panelFondo.add(txtNombre, gbc);
		gbc.gridy++;
		panelFondo.add(lblCantidad, gbc);
		gbc.gridy++;
		panelFondo.add(txtCantidad, gbc);
		gbc.gridy++;
		panelFondo.add(lblConcepto, gbc);
		gbc.gridy++;
		panelFondo.add(txtConcepto, gbc);
		gbc.gridy++;
		panelFondo.add(btnEnviarBizum, gbc);
		gbc.gridy++;
		panelFondo.add(btnVolver, gbc);

		add(panelFondo, BorderLayout.CENTER);



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
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV);
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
			Menu menu = new Menu(id_usuario, nombreUsuario, nuevoSaldo, dni, porcentajeRF, porcentajeRV);
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