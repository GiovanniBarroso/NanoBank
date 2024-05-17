package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import bgimg.ImagenFondo;


public class Menu extends ImagenFondo {


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

		super("/img/bg_img2.png");

		setLayout(null);


		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		this.cantidadInvertida = cantidadInvertida;



		//Panel info Usuario
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(63, 57, 317, 213);
		panel.setBackground(Color.WHITE);
		add(panel);
		panel.setLayout(null);

		JLabel lblnombreUsuario = new JLabel("¡Bienvenido " + nombreUsuario + "!");
		lblnombreUsuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblnombreUsuario.setFont(new Font("Impact", Font.PLAIN, 25));
		lblnombreUsuario.setBounds(22, 42, 270, 50);
		panel.add(lblnombreUsuario);

		JLabel lblSaldo = new JLabel("Saldo: " + saldo + " €");
		lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaldo.setFont(new Font("Impact", Font.PLAIN, 25));
		lblSaldo.setBounds(22, 127, 270, 50);
		panel.add(lblSaldo);


		//Panel botones
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(63, 292, 317, 371);
		panel_1.setBackground(Color.WHITE);
		add(panel_1);
		panel_1.setLayout(null);

		JButton btnTransferencia = new JButton();
		ImageIcon FondobtnTransferencia = new ImageIcon(getClass().getResource("/img/transferencia_button.png"));
		btnTransferencia.setIcon(FondobtnTransferencia);
		btnTransferencia.setBounds(34, 38, 252, 53);
		panel_1.add(btnTransferencia);

		JButton btnBizum = new JButton();
		ImageIcon FondobtnBizum = new ImageIcon(getClass().getResource("/img/bizum_button.png"));
		btnBizum.setIcon(FondobtnBizum);
		btnBizum.setBounds(34, 102, 252, 53);
		panel_1.add(btnBizum);

		JButton btnCarteras = new JButton();
		ImageIcon FondobtnCarteras = new ImageIcon(getClass().getResource("/img/carteras_button.png"));
		btnCarteras.setIcon(FondobtnCarteras);
		btnCarteras.setBounds(34, 166, 252, 53);
		panel_1.add(btnCarteras);

		JButton btnFormularioCarteras = new JButton();
		ImageIcon FondobtnformCarteras = new ImageIcon(getClass().getResource("/img/formularioCarteras_button.png"));
		btnFormularioCarteras.setIcon(FondobtnformCarteras);
		btnFormularioCarteras.setBounds(34, 230, 252, 53);
		panel_1.add(btnFormularioCarteras);

		JButton btnConsultas = new JButton();
		ImageIcon FondobtnConsultas = new ImageIcon(getClass().getResource("/img/Consultas_button.png"));
		btnConsultas.setIcon(FondobtnConsultas);
		btnConsultas.setBounds(34, 294, 252, 53);
		panel_1.add(btnConsultas);

		JButton btnCerrarSesion = new JButton();
		btnCerrarSesion.setBounds(312, 11, 111, 32);
		ImageIcon iconoLogout = new ImageIcon(getClass().getResource("/img/logout_button.png"));
		btnCerrarSesion.setIcon(iconoLogout);
		add(btnCerrarSesion);



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

		btnCerrarSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volverAIniciarSesion();
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

		Container parent = getParent();

		if (parent != null) {
			parent.removeAll();

			parent.add(new Consultas(id_usuario, nombreUsuario, saldo, dni,  porcentajeRF, porcentajeRV, cantidadInvertida)); 
			parent.revalidate();
			parent.repaint();
		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}




	private void volverAIniciarSesion() {
		// Cerrar la ventana actual de registro
		SwingUtilities.getWindowAncestor(this).dispose();

		// Abrir una nueva instancia de la clase IniciarSesion
		SwingUtilities.invokeLater(() -> new IniciarSesion());

	}





}