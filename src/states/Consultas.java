package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import bgimg.ImagenFondo;


public class Consultas extends ImagenFondo {


	//Atributos
	private static final long serialVersionUID = 1L;

	private int id_usuario;
	private double saldo;
	private String nombreUsuario;
	private String dni;
	private double porcentajeRF; 
	private double porcentajeRV;
	private double cantidadInvertida;

	public Consultas(int id_usuario, String nombreUsuario, double saldo, String dni, double porcentajeRF, double porcentajeRV, double cantidadInvertida) {

		super("/img/bg_img2.png");


		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		this.cantidadInvertida = cantidadInvertida;

		setLayout(null);



		JPanel panelFondo = new JPanel();
		panelFondo.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelFondo.setBounds(37, 128, 358, 132);
		add(panelFondo);
		panelFondo.setLayout(null);


		//Logo
		ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_7.png"));
		JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
		lblLogo.setBounds(154, 11, 127, 119);
		add(lblLogo);



		JLabel lblConsulta = new JLabel("<html><div style='text-align: center;'>¿QUÉ CONSULTA DESEAS REALIZAR?</div></html>");
		lblConsulta.setOpaque(true);
		lblConsulta.setForeground(Color.BLACK);
		lblConsulta.setHorizontalTextPosition(SwingConstants.CENTER);
		lblConsulta.setBackground(new Color(255, 255, 255));
		lblConsulta.setFont(new Font("Impact", Font.BOLD, 40));
		lblConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsulta.setBounds(10, 11, 338, 110);
		panelFondo.add(lblConsulta);



		JPanel panelBotones = new JPanel();
		panelBotones.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelBotones.setBounds(37, 295, 358, 369);
		add(panelBotones);
		panelBotones.setLayout(null);



		JButton btnBizum = new JButton();
		ImageIcon FondobtnBizum = new ImageIcon(getClass().getResource("/img/BizumRealizados_btn.png"));
		btnBizum.setIcon(FondobtnBizum);
		btnBizum.setBounds(25, 45, 306, 60);
		panelBotones.add(btnBizum);



		JButton btnTransferencias = new JButton();
		ImageIcon FondobtnTransferencias = new ImageIcon(getClass().getResource("/img/TransferenciasRealizadas_btn.png"));
		btnTransferencias.setIcon(FondobtnTransferencias);
		btnTransferencias.setBounds(25, 130, 306, 60);
		panelBotones.add(btnTransferencias);



		JButton btnCarteras = new JButton();
		ImageIcon FondobtnCarteras = new ImageIcon(getClass().getResource("/img/ConsultarCarteras_btn.png"));
		btnCarteras.setIcon(FondobtnCarteras);
		btnCarteras.setBounds(25, 213, 306, 60);
		panelBotones.add(btnCarteras);



		JButton btnVolverAtras = new JButton();
		ImageIcon FondobtnBack= new ImageIcon(getClass().getResource("/img/back_btn4.png"));
		btnVolverAtras.setIcon(FondobtnBack);
		btnVolverAtras.setBounds(106, 305, 140, 37);
		panelBotones.add(btnVolverAtras);




		btnBizum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarPDFBizum();
			}
		});



		btnTransferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarPDFTransferencias();
			}
		});



		btnCarteras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarPDFCarteras();
			}
		});



		btnVolverAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volveraMenu();
			}
		});



	}


	private void volveraMenu() {
		Container parent = getParent();
		if (parent != null) {
			parent.removeAll();
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
			parent.add(menu);
			parent.revalidate();
			parent.repaint();

		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}

	private void generarPDFBizum() {
		GenerarPDFBizum.generarPDFBizum(id_usuario, nombreUsuario);
	}

	private void generarPDFTransferencias() {
		GenerarPDFTransferencias.generarPDFTransferencias(id_usuario, nombreUsuario);
	}

	private void generarPDFCarteras() {
		GenerarPDFCarteras.generarPDFCartera(id_usuario, nombreUsuario);
	}

}