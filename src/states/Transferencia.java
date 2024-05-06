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

	private JTextField txtcuentaDestino;
	private JTextField txtNombre;
	private JTextField txtCantidad;
	private JTextField txtConcepto;

	private String nombreUsuario;
	private int id_usuario;
	private double saldo;
	private JButton btnEnviarTransferencia;
	private JButton btnVolver;


	public Transferencia(int id_usuario, String nombreUsuario, double saldo) {

		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;

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


		JLabel lblcuentaDestino = new JLabel("IBAN");
		lblcuentaDestino.setFont(new Font("Impact", Font.PLAIN, 20));
		txtcuentaDestino = new JTextField(20);
		txtcuentaDestino.setHorizontalAlignment(JTextField.CENTER);
		txtcuentaDestino.setFont(new Font("Arial", Font.PLAIN, 16));



		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));
		txtNombre = new JTextField(20);
		txtNombre.setHorizontalAlignment(JTextField.CENTER);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));



		JLabel lblCantidad = new JLabel("Cantidad a enviar (Sin límites)");
		lblCantidad.setFont(new Font("Impact", Font.PLAIN, 20));
		txtCantidad = new JTextField(20);
		txtCantidad.setHorizontalAlignment(JTextField.CENTER);
		txtCantidad.setFont(new Font("Arial", Font.PLAIN, 16));



		JLabel lblConcepto = new JLabel("Concepto");
		lblConcepto.setFont(new Font("Impact", Font.PLAIN, 20));
		txtConcepto = new JTextField(20);
		txtConcepto.setHorizontalAlignment(JTextField.CENTER);
		txtConcepto.setFont(new Font("Arial", Font.PLAIN, 16));


		btnEnviarTransferencia = new JButton("Realizar Transferencia");
		btnVolver = new JButton("Volver atrás");



		// IBAN
		gbc.gridy++;
		panelFondo.add(lblcuentaDestino, gbc);

		gbc.gridy++;
		panelFondo.add(txtcuentaDestino, gbc);


		// Nombre
		gbc.gridy++;
		panelFondo.add(lblNombre, gbc);

		gbc.gridy++;
		panelFondo.add(txtNombre, gbc);


		// Cantidad
		gbc.gridy++;
		panelFondo.add(lblCantidad, gbc);

		gbc.gridy++;
		panelFondo.add(txtCantidad, gbc);


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
		String Nombre = txtNombre.getText();
		String cuentaDestino = txtcuentaDestino.getText();
		String Concepto = txtConcepto.getText();
		String Cantidad = txtCantidad.getText();

		// Validar que todos los campos estén llenos
		if (Nombre.isEmpty() || cuentaDestino.isEmpty() || Cantidad.isEmpty() || Concepto.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Conexion conexionDB = new Conexion();
		try {


			conexionDB.sendTransferencia(id_usuario, nombreUsuario, Nombre, cuentaDestino, Concepto, Double.parseDouble(Cantidad));
			JOptionPane.showMessageDialog(this, "¡ Transferencia realizada !");


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
		Container parent = getParent();
		if (parent != null) {
			// Eliminar todos los componentes del padre
			parent.removeAll();

			// Crear una nueva instancia de Menu y agregarla al padre
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo);
			parent.add(menu);

			// Actualizar la interfaz de usuario
			parent.revalidate();
			parent.repaint();
		} else {
			// Manejar la situación donde getParent() devuelve null
			System.out.println("El componente no tiene un padre [🆎].");
		}
	}




	// Método para limpiar los campos de entrada
	public void limpiarCampos() {
		txtcuentaDestino.setText("");
		txtNombre.setText("");
		txtCantidad.setText("");
		txtConcepto.setText("");
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