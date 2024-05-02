package states;

import javax.swing.*;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class IniciarSesion extends JFrame {

	private static final long serialVersionUID = 1L;


	public IniciarSesion() {

		setTitle("Iniciar Sesión");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 720);
		setLocationRelativeTo(null);
		getContentPane().setBackground(Color.ORANGE);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);

		JLabel lblUsuario = new JLabel("Usuario:");
		JTextField txtUsuario = new JTextField(20);
		txtUsuario.setHorizontalAlignment(JTextField.CENTER);

		JLabel lblContraseña = new JLabel("Contraseña:");
		JPasswordField txtContraseña = new JPasswordField(20);
		txtContraseña.setHorizontalAlignment(JPasswordField.CENTER);

		JButton btnIniciarSesion = new JButton("Iniciar Sesión");
		btnIniciarSesion.setHorizontalAlignment(JButton.CENTER);

		JButton btnRegistrarse = new JButton("¿No estás registrado? Regístrate aquí");
		btnRegistrarse.setHorizontalAlignment(JButton.CENTER);

		// Usuario
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		add(lblUsuario, gbc);

		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.CENTER;
		add(txtUsuario, gbc);

		// Contraseña
		gbc.gridy = 2;
		add(lblContraseña, gbc);

		gbc.gridy = 3;
		add(txtContraseña, gbc);

		// Botón de iniciar sesión
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.CENTER;
		add(btnIniciarSesion, gbc);

		// Botón de registrarse
		gbc.gridy = 5;
		add(btnRegistrarse, gbc);


		

		setVisible(true);



		//Evento al pulsar el boton de Iniciar Sesion
		btnIniciarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = txtUsuario.getText();
				String contraseña = new String(txtContraseña.getPassword());

				try {
					Conexion conexion = new Conexion();
					if (conexion.validarCredenciales(usuario, contraseña)) {
						getContentPane().removeAll();
						Menu menu = new Menu(); 
						getContentPane().add(menu);
						revalidate();
						repaint();
					} else {
						JOptionPane.showMessageDialog(IniciarSesion.this, "Usuario o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(IniciarSesion.this, "Error de conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});



		//Evento al pulsa el boton de Registrarse
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().removeAll();
				RegistroUsuario registroUsuario = new RegistroUsuario();
				getContentPane().add(registroUsuario);
				revalidate();
				repaint();
			}
		});
	}

}
