package states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistroUsuario extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtCorreo;
	private JPasswordField txtContraseña;
	private JButton btnRegistrar;




	public RegistroUsuario() {
		setBackground(Color.ORANGE);
		setLayout(new GridLayout(6, 2, 10, 10));

		JLabel lblNombre = new JLabel("Nombre:");
		txtNombre = new JTextField();

		JLabel lblApellido = new JLabel("Apellido:");
		txtApellido = new JTextField();

		JLabel lblCorreo = new JLabel("Correo:");
		txtCorreo = new JTextField();

		JLabel lblContraseña = new JLabel("Contraseña:");
		txtContraseña = new JPasswordField();

		btnRegistrar = new JButton("Registrar");



		// Action listener para el botón de registro
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarUsuario();
				limpiarCampos();
			}
		});




		// Añadir componentes al JPanel
		add(lblNombre);
		add(txtNombre);
		add(lblApellido);
		add(txtApellido);
		add(lblCorreo);
		add(txtCorreo);
		add(lblContraseña);
		add(txtContraseña);
		add(new JLabel()); // Espacio en blanco para alinear el botón de registro
		add(btnRegistrar);
	}




	// Método para registrar un nuevo usuario
	private void registrarUsuario() {

		String nombre = txtNombre.getText();
		String apellido = txtApellido.getText();
		String correo = txtCorreo.getText();
		String contraseña = new String(txtContraseña.getPassword());


		//Mostramos por consola resultado del registro para verificar los datos
		System.out.println("Nuevo usuario registrado:");
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellido: " + apellido);
		System.out.println("Correo: " + correo);
		System.out.println("Contraseña: " + contraseña + "\n");


		// Cerrar la ventana actual de registro
		SwingUtilities.getWindowAncestor(this).dispose();

		// Abrir una nueva instancia de la clase IniciarSesion
		SwingUtilities.invokeLater(() -> new IniciarSesion());
	}

	// Método para limpiar los campos de entrada
	public void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtCorreo.setText("");
		txtContraseña.setText("");
	}
}
