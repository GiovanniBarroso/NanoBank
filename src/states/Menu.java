package states;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    private double saldo = 1000.0;
   
    private static final long serialVersionUID = 1L;

    public Menu(String nombreUsuario) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
        setBackground(Color.ORANGE); 

        // Panel superior para dar la bienvenida al usuario
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
        add(topPanel); 

        
        
        // Panel para mostrar el saldo
        JPanel saldoPanel = new JPanel();
        saldoPanel.setLayout(new BoxLayout(saldoPanel, BoxLayout.Y_AXIS));
        saldoPanel.setBackground(Color.green);
        saldoPanel.setMaximumSize(new Dimension(300, 100));
        saldoPanel.setPreferredSize(new Dimension(300, 100)); // Tamaño preferido para el panel de bienvenida
        saldoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saldoPanel.add(Box.createVerticalGlue()); 
        
        
        
        JLabel saldoLabel = new JLabel("Saldo: €" + saldo);
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 24)); 
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saldoPanel.add(saldoLabel);
        saldoPanel.add(Box.createVerticalGlue()); 
        add(saldoPanel); 

        
        
        // Espacio entre el panel de saldo y los botones
        add(Box.createRigidArea(new Dimension(0, 50)));

        
        
        // Creamos los botones y los agregamos directamente al JPanel principal
        add(createButton("TRANSFERENCIA"));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createButton("BIZUM"));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createButton("CARTERAS"));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createButton("PAGOS Y DEUDAS"));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createButton("CONSULTAS"));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createButton("¿NECESITAS AYUDA?"));
    }

    
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 40)); 
        button.setMinimumSize(new Dimension(300, 40)); 
        button.setPreferredSize(new Dimension(300, 40)); 
        return button;
    }
}
