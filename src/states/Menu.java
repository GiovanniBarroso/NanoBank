package states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Menu extends JPanel {

	private double saldo;
	private String nombreUsuario;
	
	private static final long serialVersionUID = 1L;

	public Menu(String nombreUsuario, double saldo) {

		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
		setBackground(Color.ORANGE); 

		// Panel superior para dar la bienvenida al usuario
		add(createTopPanel(nombreUsuario));

		// Panel para mostrar el saldo
		add(createSaldoPanel());

		// Espacio entre el panel de saldo y los botones
		add(Box.createRigidArea(new Dimension(0, 50)));



		// Creamos los botones y los agregamos directamente al JPanel principal
		add(createButton("TRANSFERENCIA", e -> realizarTransferencia()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("BIZUM", e -> realizarBizum()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("CARTERAS", e -> gestionarCarteras()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("PAGOS Y DEUDAS", e -> gestionarPagosDeudas()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("CONSULTAS", e -> realizarConsultas()));
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(createButton("¿NECESITAS AYUDA?", e -> ofrecerAyuda()));
		add(Box.createRigidArea(new Dimension (0,20)));
	}



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



	private JPanel createSaldoPanel() {
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



	private JButton createButton(String text, ActionListener action) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(300, 40)); 
		button.setMinimumSize(new Dimension(300, 40)); 
		button.setPreferredSize(new Dimension(300, 40)); 
		button.addActionListener(action);
		return button;
	}




	private void realizarTransferencia() {
		Container parent = getParent();
		if (parent != null) {
			parent.removeAll();
			parent.add(new Transferencia(nombreUsuario, saldo)); 
			parent.revalidate();
			parent.repaint();
		} else {
			// Manejar la situación donde getParent() devuelve null
			System.out.println("El componente no tiene un padre.");
		}
	}


	private void realizarBizum() {

	}



	private void gestionarCarteras() {

	}



	private void gestionarPagosDeudas() {

	}



	private void realizarConsultas() {

	}



	private void ofrecerAyuda() {

	}




}