package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {


	//Atributos
	private static final long serialVersionUID = 1L;
	private int id_usuario;
	private double saldo;
	private String nombreUsuario;
	private String dni;
	private double porcentajeRF; 
	private double porcentajeRV;
	private double cantidadInvertida;


	public Menu(int id_usuario, String nombreUsuario, double saldo, String dni, double porcentajeRF, double porcentajeRV, double cantidadInvertida) {

		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		setBackground(new Color(64, 224, 208));

		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		this.cantidadInvertida = cantidadInvertida;
		setLayout(null);


		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(63, 100, 317, 213);
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("¡Bienvenido " + nombreUsuario + "!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Impact", Font.PLAIN, 25));
		lblNewLabel.setBounds(22, 42, 270, 50);
		panel.add(lblNewLabel);

		JLabel lblSaldo = new JLabel("Saldo: " + saldo + " €");
		lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaldo.setFont(new Font("Impact", Font.PLAIN, 25));
		lblSaldo.setBounds(22, 127, 270, 50);
		panel.add(lblSaldo);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(63, 340, 317, 371);
		add(panel_1);
		panel_1.setLayout(null);

		JButton btnTransferencia = new JButton("TRANSFERENCIA");
		btnTransferencia.setBounds(34, 38, 252, 53);
		panel_1.add(btnTransferencia);

		JButton btnBizum = new JButton("BIZUM");
		btnBizum.setBounds(34, 102, 252, 53);
		panel_1.add(btnBizum);

		JButton btnCarteras = new JButton("CARTERAS");
		btnCarteras.setBounds(34, 166, 252, 53);
		panel_1.add(btnCarteras);

		JButton btnFormularioCarteras = new JButton("FORMULARIO CARTERAS");
		btnFormularioCarteras.setBounds(34, 230, 252, 53);
		panel_1.add(btnFormularioCarteras);

		JButton btnConsultas = new JButton("CONSULTAS");
		btnConsultas.setBounds(34, 294, 252, 53);
		panel_1.add(btnConsultas);





		JLabel lblNewLabel_1_1 = new JLabel("NANO BANK");
		lblNewLabel_1_1.setBounds(0, 24, 450, 49);
		add(lblNewLabel_1_1);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 45));
		lblNewLabel_1_1.setBackground(Color.WHITE);




		// Eventos para los botones
		btnTransferencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarTransferencia();
			}
		});

		btnBizum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarBizum();
			}
		});

		btnCarteras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionarCarteras();
			}
		});

		btnFormularioCarteras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarTestIdoneidad();
			}
		});

		btnConsultas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				realizarConsultas();
			}
		});


	}

	//Metodo para entrar en la clase Transferencia
	private void realizarTransferencia() {
		Container parent = getParent();

		if (parent != null) {
			parent.removeAll();

			parent.add(new Transferencia(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida)); 
			parent.revalidate();
			parent.repaint();
		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}

	//Metodo para entrar en la clase Bizum
	private void realizarBizum() {
		Container parent = getParent();

		if (parent != null) {
			parent.removeAll();

			parent.add(new Bizum(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida)); 
			parent.revalidate();
			parent.repaint();
		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}


	//Metodo para entrar en la clase Carteras
	private void gestionarCarteras() {
		Container parent = getParent();
		if (parent != null) {
			parent.removeAll();

			parent.add(new GestionarCarteras(id_usuario, nombreUsuario, dni, porcentajeRF, porcentajeRV, cantidadInvertida)); 
			parent.revalidate();
			parent.repaint();
		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}



	private void realizarTestIdoneidad() {
		Container parent = getParent();

		if (parent != null) {
			parent.removeAll();

			parent.add(new FormularioInversion(id_usuario, nombreUsuario, saldo, dni,  porcentajeRF, porcentajeRV, cantidadInvertida)); 
			parent.revalidate();
			parent.repaint();
		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}



	private void realizarConsultas() {

	}


}