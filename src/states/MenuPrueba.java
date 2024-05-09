package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPrueba extends JPanel {


	//Atributos
	private static final long serialVersionUID = 1L;
	private int id_usuario;
	private double saldo;
	private String nombreUsuario;
	private String dni;
	private double porcentajeRF; 
	private double porcentajeRV;


	public MenuPrueba(int id_usuario, String nombreUsuario, double saldo, String dni, double porcentajeRV, double porcentajeRF) {
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		setBackground(new Color(64, 224, 208));

		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(63, 100, 317, 213);
		add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido + usuario");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(72, 42, 156, 50);
		panel.add(lblNewLabel);
		
		JLabel lblSaldo = new JLabel("Saldo : + saldo + €");
		lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaldo.setBounds(72, 127, 156, 50);
		panel.add(lblSaldo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBounds(63, 340, 317, 371);
		add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(34, 38, 252, 53);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(34, 102, 252, 53);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(34, 166, 252, 53);
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setBounds(34, 230, 252, 53);
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setBounds(34, 294, 252, 53);
		panel_1.add(btnNewButton_4);
		
		JLabel lblNewLabel_1_1 = new JLabel("MENÚ");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 45));
		lblNewLabel_1_1.setBackground(Color.WHITE);
		lblNewLabel_1_1.setBounds(0, 24, 450, 49);
		add(lblNewLabel_1_1);







	}
}





