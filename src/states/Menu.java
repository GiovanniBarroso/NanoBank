package states;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

public class Menu extends JPanel {

    public Menu() {
        setLayout(new GridLayout(3, 1)); // Organización de los componentes en 3 filas

        JButton loginButton = new JButton("Iniciar Sesión");
        JButton checkBalanceButton = new JButton("Ver Saldo");
        JButton transferButton = new JButton("Realizar Transferencia");

        add(loginButton);
        add(checkBalanceButton);
        add(transferButton);
    }
}
