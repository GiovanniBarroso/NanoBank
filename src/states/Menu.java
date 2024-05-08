package states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JPanel {


	//Atributos
	private static final long serialVersionUID = 1L;
	private int id_usuario;
	private double saldo;
	private String nombreUsuario;
	private String dni;
	private int porcentajeRF; 
	private int porcentajeRV;


	public Menu(int id_usuario, String nombreUsuario, double saldo, String dni) {

		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setBackground(Color.ORANGE); 


		// Panel superior para dar la bienvenida al usuario
		add(createTopPanel(nombreUsuario));

		// Panel para mostrar el saldo
		add(createSaldoPanel(saldo));

		// Espacio entre el panel de saldo y los botones
		add(Box.createRigidArea(new Dimension(0, 50)));



		// Creamos los botones y los agregamos directamente al JPanel principal
		add(createButton("TRANSFERENCIA", e -> realizarTransferencia()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("BIZUM", e -> realizarBizum()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("CARTERAS", e -> gestionarCarteras()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("PAGOS Y DEUDAS", e -> realizarTestIdoneidad()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("CONSULTAS", e -> realizarConsultas()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("¿NECESITAS AYUDA?", e -> ofrecerAyuda()));
		add(Box.createRigidArea(new Dimension (0,20)));
	}


	//Panel de Bienvenida al usuario
	private JPanel createTopPanel(String nombreUsuario) {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.setBackground(Color.CYAN);
		topPanel.setMaximumSize(new Dimension(300, 100));
		topPanel.setPreferredSize(new Dimension(300, 100)); 
		topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		topPanel.add(Box.createVerticalGlue());

		JLabel welcomeLabel = new JLabel("¡Bienvenido " + nombreUsuario + "!");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		topPanel.add(welcomeLabel);
		topPanel.add(Box.createVerticalGlue()); 
		return topPanel;
	}


	//Panel de saldo del usuario
	private JPanel createSaldoPanel(double saldo) {
		JPanel saldoPanel = new JPanel();
		saldoPanel.setLayout(new BoxLayout(saldoPanel, BoxLayout.Y_AXIS));
		saldoPanel.setBackground(Color.GREEN);
		saldoPanel.setMaximumSize(new Dimension(300, 100));
		saldoPanel.setPreferredSize(new Dimension(300, 100)); 
		saldoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		saldoPanel.add(Box.createVerticalGlue());

		JLabel saldoLabel = new JLabel("Saldo: €" + saldo);
		saldoLabel.setFont(new Font("Arial", Font.PLAIN, 24)); 
		saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		saldoPanel.add(saldoLabel);
		saldoPanel.add(Box.createVerticalGlue()); 
		return saldoPanel;
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
			parent.add(new Transferencia(id_usuario, nombreUsuario, saldo, dni)); 
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
			parent.add(new Bizum(id_usuario, nombreUsuario, saldo, dni)); 
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
			parent.add(new FormularioInversion(id_usuario, nombreUsuario, saldo, dni)); 
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