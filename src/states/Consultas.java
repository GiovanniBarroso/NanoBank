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
	private JTextField txtQuConsultaDeseas;

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
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(21, 75, 399, 132);
		add(panel);
		panel.setLayout(null);
		
		txtQuConsultaDeseas = new JTextField();
		txtQuConsultaDeseas.setFont(new Font("Impact", Font.BOLD, 23));
		txtQuConsultaDeseas.setHorizontalAlignment(SwingConstants.CENTER);
		txtQuConsultaDeseas.setText("¿QUÉ CONSULTA DESEAS REALIZAR?");
		txtQuConsultaDeseas.setBounds(22, 21, 353, 86);
		panel.add(txtQuConsultaDeseas);
		txtQuConsultaDeseas.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(21, 233, 399, 405);
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("CONSULTAR ENTRADA Y SALIDA DE BIZUM\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(47, 57, 306, 60);
		panel_1.add(btnNewButton);
		
		JButton btnGestionarTransferencias = new JButton("CONSULTAR ENTRADA Y SALIDA DE TRANSFERENCIAS");
		btnGestionarTransferencias.setBounds(47, 176, 306, 60);
		panel_1.add(btnGestionarTransferencias);
		
		JButton btnNewButton_1_1 = new JButton("CONSULTAR Y GESTIONAR CARTERAS");
		btnNewButton_1_1.setBounds(47, 296, 306, 60);
		panel_1.add(btnNewButton_1_1);
		
		
		
		
		
		
		
		
	}
}