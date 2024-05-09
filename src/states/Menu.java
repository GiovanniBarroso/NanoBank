package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
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


	public Menu(int id_usuario, String nombreUsuario, double saldo, String dni, double porcentajeRV, double porcentajeRF) {

		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;

		// Espacio entre el panel de saldo y los botones
		add(Box.createRigidArea(new Dimension(0, 50)));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setBackground(new Color(64, 224, 208));


		// Panel superior para dar la bienvenida al usuario
		add(PanelUsuario(nombreUsuario, saldo));


		// Espacio entre el panel de saldo y los botones
		add(Box.createRigidArea(new Dimension(0, 50)));


		// Creamos los botones y los agregamos directamente al JPanel principal
		add(createButton("TRANSFERENCIA", e -> realizarTransferencia()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("BIZUM", e -> realizarBizum()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("CARTERAS", e -> gestionarCarteras()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("FORMULARIO CARTERAS", e -> realizarTestIdoneidad()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("CONSULTAS", e -> realizarConsultas()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("¿NECESITAS AYUDA?", e -> ofrecerAyuda()));
		add(Box.createRigidArea(new Dimension (0,20)));
	}


	private JPanel PanelUsuario(String nombreUsuario, double saldo) {
	    JPanel panel = new JPanel();
	    panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
	

	    panel.setBackground(Color.GRAY);
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    
	    // Componente de relleno en la parte superior para centrar verticalmente
	    panel.add(Box.createVerticalGlue());

	    JLabel welcomeLabel = new JLabel("¡Bienvenido " + nombreUsuario + "!");
	    welcomeLabel.setForeground(Color.BLACK);
	    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
	    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(welcomeLabel);

	 // Espacio vertical de 10 píxeles
	    panel.add(Box.createRigidArea(new Dimension(0, 10)));

	    JLabel saldoLabel = new JLabel("Saldo: " + saldo + "€");
	    saldoLabel.setForeground(Color.BLACK);
	    saldoLabel.setFont(new Font("Arial", Font.PLAIN, 24)); 
	    saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel.add(saldoLabel);
	    
	    // Componente de relleno en la parte inferior para centrar verticalmente
	    panel.add(Box.createVerticalGlue());

	    return panel;
	}



	

	//Metodo para crear botones.
	private JButton createButton(String text, ActionListener action) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(300, 40)); 
		button.setMinimumSize(new Dimension(300, 40)); 
		button.setPreferredSize(new Dimension(300, 40)); 
		button.addActionListener(action);
		return button;
	}



	//Metodo para entrar en la clase Transferencia
	private void realizarTransferencia() {
		Container parent = getParent();

		if (parent != null) {
			parent.removeAll();

			parent.add(new Transferencia(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV)); 
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

			parent.add(new Bizum(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV)); 
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

			parent.add(new GestionarCarteras(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV)); 
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

			parent.add(new FormularioInversion(id_usuario, nombreUsuario, saldo, dni,  porcentajeRF, porcentajeRV)); 
			parent.revalidate();
			parent.repaint();
		} else {
			System.out.println("El componente no tiene un padre.");
		}
	}



	private void realizarConsultas() {

	}



	private void ofrecerAyuda() {

	}




}