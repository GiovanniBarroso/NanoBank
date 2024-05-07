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

	private static final long serialVersionUID = 1L;

	private JTextField txtTelefono;
	private JTextField txtNombre;
	private JTextField txtCantidad;
	private JTextField txtConcepto;

	private String nombreUsuario;
	private int id_usuario;
	private double saldo;
	private String dni;

	private JButton btnEnviarBizum;
	private JButton btnVolver;

	public Bizum(int idUsuario, String nombreUsuario, double saldo, String dni) {
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

		btnEnviarBizum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveBizum();
			}
		});

		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volveraMenu();
			}
		});
	}

	private void saveBizum() {
		String nombre = txtNombre.getText();
		String telefono = txtTelefono.getText();
		String concepto = txtConcepto.getText();
		String cantidad = txtCantidad.getText();

		if (nombre.isEmpty() || telefono.isEmpty() || cantidad.isEmpty() || concepto.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Conexion conexionDB = new Conexion();
		try {
			conexionDB.sendBizum(id_usuario, nombreUsuario, Integer.parseInt(telefono), nombre, concepto, Double.parseDouble(cantidad));
			JOptionPane.showMessageDialog(this, "¡Bizum realizado con éxito!");

			double nuevoSaldo = conexionDB.obtenerSaldoPorDNI(dni);
			limpiarCampos();
			volveraMenu2(nuevoSaldo);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al realizar Bizum: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Método para volver a IniciarSesion
	private void volveraMenu() {
		Container parent = getParent();
		if (parent != null) {
			// Eliminar todos los componentes del padre
			parent.removeAll();

			// Crear una nueva instancia de Menu y agregarla al padre
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni);
			parent.add(menu);

			// Actualizar la interfaz de usuario
			parent.revalidate();
			parent.repaint();
		} else {
			// Manejar la situación donde getParent() devuelve null
			System.out.println("El componente no tiene un padre [🆎].");
		}
	}

	private void volveraMenu2(double nuevoSaldo) {
		Container parent = getParent();
		if (parent != null) {
			// Eliminar todos los componentes del padre
			parent.removeAll();

			// Crear una nueva instancia de Menu y agregarla al padre con el nuevo saldo
			Menu menu = new Menu(id_usuario, nombreUsuario, nuevoSaldo, dni);
			parent.add(menu);

			// Actualizar la interfaz de usuario
			parent.revalidate();
			parent.repaint();
		} else {
			// Manejar la situación donde getParent() devuelve null
			System.out.println("El componente no tiene un padre.");
		}
	}

	public void limpiarCampos() {
		txtTelefono.setText("");
		txtNombre.setText("");
		txtCantidad.setText("");
		txtConcepto.setText("");
	}


}
