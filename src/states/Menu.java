package states;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Menu extends JPanel {

   
	private static final long serialVersionUID = 1L;

	public Menu() {
        setLayout(new GridLayout(3, 1));

        JButton loginButton = new JButton("Iniciar Sesión");
        JButton checkBalanceButton = new JButton("Ver Saldo");
        JButton transferButton = new JButton("Realizar Transferencia");

        add(loginButton);
        add(checkBalanceButton);
        add(transferButton);
    }
}
