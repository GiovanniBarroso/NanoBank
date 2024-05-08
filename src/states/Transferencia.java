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
	private int porcentajeRF; 
	private int porcentajeRV;

	private String nombreUsuario;
	private int id_usuario;
	private double saldo;
	private String dni;

	private JButton btnEnviarTransferencia;
	private JButton btnVolver;


	public Transferencia(int id_usuario, String nombreUsuario, double saldo, String dni) {

		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		

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





		// Acción del botón Realizar Transferencia
		btnEnviarTransferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveTransferencia();
			}
		});




		// Acción del botón Volver a menu
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
		String Concepto  = txtConcepto.getText();
		String Cantidad = txtCantidad.getText();
		
		Cantidad = Cantidad.replaceAll(",", ".");

		// Validar que todos los campos estén llenos
		if (Nombre.isEmpty() || cuentaDestino.isEmpty() || Concepto.isEmpty() || Cantidad.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		double cantidad = Double.parseDouble(Cantidad);

		// Verificar que la cantidad sea mayor que cero
		if (cantidad <= 0) {
			JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Conexion conexionDB = new Conexion();
		try {
			// Verificar que el IBAN existe en la base de datos
			if (!conexionDB.existeIBAN(cuentaDestino)) {
				JOptionPane.showMessageDialog(this, "El IBAN especificado no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Verificar que el nombre de destino coincide con el IBAN especificado
			if (!conexionDB.coincideNombreIBAN(Nombre, cuentaDestino)) {
				JOptionPane.showMessageDialog(this, "El nombre de destino no coincide con el IBAN especificado.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Verificar que el usuario tiene saldo suficiente para la transferencia
			double saldoActual = conexionDB.obtenerSaldoPorDNI(dni);
			if (saldoActual < cantidad) {
				JOptionPane.showMessageDialog(this, "Saldo insuficiente para realizar la transferencia.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Realizar la transferencia
			conexionDB.sendTransferencia(id_usuario, nombreUsuario, Nombre, cuentaDestino, Concepto, Double.parseDouble(Cantidad));
			JOptionPane.showMessageDialog(this, "¡Transferencia realizada!");

			// Obtener el saldo actualizado del usuario desde la base de datos
			double nuevoSaldo = conexionDB.obtenerSaldoPorDNI(dni);
			System.out.println("Nuevo saldo: " + nuevoSaldo);

			// Limpiar los campos
			limpiarCampos();

			// Volver al menú con el nuevo saldo
			volveraMenu2(nuevoSaldo);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Error al realizar la transferencia: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}





	// Método para volver a IniciarSesion
	private void volveraMenu() {
		Container parent = getParent();
		if (parent != null) {
			// Eliminar todos los componentes del padre
			parent.removeAll();

			// Crear una nueva instancia de Menu y agregarla al padre
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV);
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
			Menu menu = new Menu(id_usuario, nombreUsuario, nuevoSaldo, dni, porcentajeRF, porcentajeRV);
			parent.add(menu);

			// Actualizar la interfaz de usuario
			parent.revalidate();
			parent.repaint();
		} else {
			// Manejar la situación donde getParent() devuelve null
			System.out.println("El componente no tiene un padre.");
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