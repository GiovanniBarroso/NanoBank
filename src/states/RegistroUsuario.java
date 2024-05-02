package states;

import javax.swing.*;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class RegistroUsuario extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtDNI;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextField txtIBAN;
	private JPasswordField txtContraseña;
	private JButton btnRegistrar;
	private JButton btnMostrarContraseña;
	private boolean mostrarContraseña = false;

	public RegistroUsuario() {
		setBackground(Color.ORANGE);
		setLayout(new GridLayout(9, 2, 10, 10));

		JLabel lblDNI = new JLabel("DNI:");
		txtDNI = new JTextField();

		JLabel lblNombre = new JLabel("Nombre:");
		txtNombre = new JTextField();

		JLabel lblTelefono = new JLabel("Número de Teléfono:");
		txtTelefono = new JTextField();

		JLabel lblEmail = new JLabel("Email:");
		txtEmail = new JTextField();

		JLabel lblIBAN = new JLabel("IBAN:");
		txtIBAN = new JTextField();

		JLabel lblContraseña = new JLabel("Contraseña:");
		txtContraseña = new JPasswordField();

		btnRegistrar = new JButton("Registrarse");
		btnMostrarContraseña = new JButton("Mostrar Contraseña");

		// Action listener para el botón de registro
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveUser();
				limpiarCampos();
			}
		});

		// Action listener para el botón de mostrar contraseña
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

		// Añadir componentes al JPanel
		add(lblDNI);
		add(txtDNI);
		add(lblNombre);
		add(txtNombre);
		add(lblTelefono);
		add(txtTelefono);
		add(lblEmail);
		add(txtEmail);
		add(lblIBAN);
		add(txtIBAN);
		add(lblContraseña);
		add(txtContraseña);
		add(btnMostrarContraseña);
		add(btnRegistrar);
	}
	
	private void saveUser() {
	    // Obtener los datos de los campos de entrada
	    String DNI = txtDNI.getText();
	    String nombre = txtNombre.getText();
	    String telefono = txtTelefono.getText();
	    String email = txtEmail.getText();
	    String iban = txtIBAN.getText();
	    String contraseña = new String(txtContraseña.getPassword());

	    // Crear una instancia de Conexion para realizar la inserción en la base de datos
	    Conexion conexionDB = new Conexion();
	    try {
	        // Llamar al método addUser de la clase Conexion para agregar un nuevo usuario
	        conexionDB.addUser(Integer.parseInt(DNI), contraseña, nombre, Integer.parseInt(telefono), email, iban);
	        JOptionPane.showMessageDialog(this, "Usuario añadido con éxito.");
	        
	     // Mostrar por consola el resultado del registro para verificar los datos
			System.out.println("¡Enhorabuena, tu perfil con nombre " + nombre + " ha sido registrado!");
			
			// Cerrar la ventana actual de registro
			SwingUtilities.getWindowAncestor(this).dispose();

			// Abrir una nueva instancia de la clase IniciarSesion
			SwingUtilities.invokeLater(() -> new IniciarSesion());
			
			
	    } catch (SQLException ex) {
	        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	    } 
	       
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
}
