package states;

import javax.swing.*;
import javax.swing.border.Border;

import sqlconnect.Conexion;
import states.IniciarSesion.RoundedBorder;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.sql.SQLException;

public class Transferencia extends JPanel {


	private static final long serialVersionUID = 1L;

	private JTextField txtNombre;
	private JTextField txtConcepto;
	private JTextField txtIBAN;
	private String nombreUsuario;
	private JButton btnEnviarTransferencia;
	private JButton btnVolver;


	public Transferencia(String nombreUsuario) {
		
		this.nombreUsuario = nombreUsuario;
		setLayout(new BorderLayout());

		// Panel para el fondo gris
		JPanel panelFondo = new JPanel();
		panelFondo.setBackground(Color.GRAY);
		panelFondo.setLayout(new GridBagLayout());


		// Establecer el borde redondeado
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		panelFondo.setBorder(BorderFactory.createCompoundBorder(border, new RoundedBorder(10)));


		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(10, 10, 10, 10);

		
		JLabel lblIBAN = new JLabel("IBAN:");
		lblIBAN.setFont(new Font("Impact", Font.PLAIN, 20));
		txtIBAN = new JTextField(20);
		txtIBAN.setHorizontalAlignment(JTextField.CENTER);
		txtIBAN.setFont(new Font("Arial", Font.PLAIN, 16));
		
		
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));
		txtNombre = new JTextField(20);
		txtNombre.setHorizontalAlignment(JTextField.CENTER);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));
		
		
		
		JLabel lblConcepto = new JLabel("CuentaDestino:");
		lblConcepto.setFont(new Font("Impact", Font.PLAIN, 20));
		txtConcepto = new JTextField(20);
		txtConcepto.setHorizontalAlignment(JTextField.CENTER);
		txtConcepto.setFont(new Font("Arial", Font.PLAIN, 16));
		
		
		

		btnEnviarTransferencia = new JButton("Realizar Transferencia");
		btnVolver = new JButton("Volver atrás");



		// Nombre
		gbc.gridy++;
		panelFondo.add(lblNombre, gbc);

		gbc.gridy++;
		panelFondo.add(txtNombre, gbc);



		// IBAN
		gbc.gridy++;
		panelFondo.add(lblIBAN, gbc);

		gbc.gridy++;
		panelFondo.add(txtIBAN, gbc);



		// nombreDestino
		gbc.gridy++;
		panelFondo.add(lblConcepto, gbc);

		gbc.gridy++;
		panelFondo.add(txtConcepto, gbc);




		// Botón Registrar
		gbc.gridy++;
		panelFondo.add(btnEnviarTransferencia, gbc);

		// Botón Volver a IniciarSesion
		gbc.gridy++;
		panelFondo.add(btnVolver, gbc);

		add(panelFondo, BorderLayout.CENTER);



		// Acción del botón Registrar
		btnEnviarTransferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTransferencia();
			}
		});




		// Acción del botón Volver a IniciarSesion
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volveraMenu();
			}
		});
	}



	private void saveTransferencia() {
		// Obtener los datos de los campos de entrada
		String nombre = txtNombre.getText();
		String cuentaDestino = txtConcepto.getText();
		String iban = txtIBAN.getText();

		// Validar que todos los campos estén llenos
		if (nombre.isEmpty() || cuentaDestino.isEmpty() || iban.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Conexion conexionDB = new Conexion();
		try {
			
			
			conexionDB.sendTransferencia(nombre, cuentaDestino, iban);
			JOptionPane.showMessageDialog(this, "¡ Cuenta creada correctamente !");

			
			System.out.println("¡Enhorabuena, la transferencia se ha realizado correctamente!\n");
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
	private void volveraMenu() {
		
		// Cerrar la ventana actual de registro
		SwingUtilities.getWindowAncestor(this).dispose();



		
		// Abrir una nueva instancia de la clase Menu
		SwingUtilities.invokeLater(() -> new Menu(nombreUsuario));
	}




	// Método para limpiar los campos de entrada
	public void limpiarCampos() {
		txtNombre.setText("");
		txtConcepto.setText("");
		txtIBAN.setText("");
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

			// Comparamos los caracteres 2 y 3 de la cuenta (dígito de control IBAN) con cadenaDc
			if(cuenta.substring(2,4).equals(cadenaDc)) {
				esValido = true;
			} else {
				esValido = false;
			}
		}

		return esValido;
	}

}