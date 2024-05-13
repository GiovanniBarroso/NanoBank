package states;

import javax.swing.*;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.sql.SQLException;
import bgimg.ImagenFondo;
import javax.swing.border.LineBorder;

public class Transferencia extends ImagenFondo {


	private static final long serialVersionUID = 1L;

	private JTextField txtcuentaDestino;
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

	private JButton btnEnviarTransferencia;
	private JButton btnVolver;


	public Transferencia(int id_usuario, String nombreUsuario, double saldo, String dni, double porcentajeRF, double porcentajeRV, double cantidadInvertida) {

		//Llamada para panel de fondo
		super("/img/bg_img2.png");


		//Contructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		this.cantidadInvertida = cantidadInvertida;


		setLayout(null);



		// Panel para el fondo gris
		JPanel panelFondo = new JPanel();
		panelFondo.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelFondo.setBounds(61, 164, 316, 500);
		panelFondo.setBackground(Color.LIGHT_GRAY);


		// Logo
		ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_7.png"));
		JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
		lblLogo.setBounds(145, 10, 150, 150);



		JLabel lblTransferencia = new JLabel("TRANSFERENCIA");
		lblTransferencia.setFont(new Font("Impact", Font.BOLD, 38));
		lblTransferencia.setBounds(32, 29, 262, 36);



		JLabel lblcuentaDestino = new JLabel("IBAN");
		lblcuentaDestino.setHorizontalAlignment(SwingConstants.CENTER);
		lblcuentaDestino.setBounds(56, 92, 199, 25);
		lblcuentaDestino.setFont(new Font("Impact", Font.PLAIN, 20));
		txtcuentaDestino = new JTextField(20);
		txtcuentaDestino.setBounds(22, 127, 272, 25);
		txtcuentaDestino.setHorizontalAlignment(JTextField.CENTER);
		txtcuentaDestino.setFont(new Font("Arial", Font.PLAIN, 16));



		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setBounds(56, 162, 199, 25);
		lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));
		txtNombre = new JTextField(20);
		txtNombre.setBounds(56, 189, 199, 25);
		txtNombre.setHorizontalAlignment(JTextField.CENTER);
		txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));



		JLabel lblCantidad = new JLabel("Cantidad (Sin límite)");
		lblCantidad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCantidad.setBounds(56, 224, 199, 26);
		lblCantidad.setFont(new Font("Impact", Font.PLAIN, 20));
		txtCantidad = new JTextField(20);
		txtCantidad.setBounds(56, 261, 199, 25);
		txtCantidad.setHorizontalAlignment(JTextField.CENTER);
		txtCantidad.setFont(new Font("Arial", Font.PLAIN, 16));



		JLabel lblConcepto = new JLabel("Concepto");
		lblConcepto.setHorizontalAlignment(SwingConstants.CENTER);
		lblConcepto.setBounds(56, 288, 199, 26);
		lblConcepto.setFont(new Font("Impact", Font.PLAIN, 20));
		txtConcepto = new JTextField(20);
		txtConcepto.setBounds(56, 314, 199, 73);
		txtConcepto.setHorizontalAlignment(JTextField.CENTER);
		txtConcepto.setFont(new Font("Arial", Font.PLAIN, 16));


		btnEnviarTransferencia = new JButton("Transferir\r\n");
		btnEnviarTransferencia.setBounds(90, 404, 133, 25);
		btnVolver = new JButton("Volver atrás");
		btnVolver.setBounds(90, 447, 133, 25);
		panelFondo.setLayout(null);


		//Logo
		add(lblLogo);
		panelFondo.add(lblTransferencia);
		panelFondo.add(lblcuentaDestino);
		panelFondo.add(txtcuentaDestino);
		panelFondo.add(lblNombre);
		panelFondo.add(txtNombre);
		panelFondo.add(lblCantidad);
		panelFondo.add(txtCantidad);
		panelFondo.add(lblConcepto);
		panelFondo.add(txtConcepto);
		panelFondo.add(btnEnviarTransferencia);
		panelFondo.add(btnVolver);
		add(panelFondo);




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


	//Metodo para validad y guardar la transferencia
	private void saveTransferencia() {

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

			limpiarCampos();
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
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
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

			// Crear una nueva instancia con el saldo actualizado
			Menu menu = new Menu(id_usuario, nombreUsuario, nuevoSaldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
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