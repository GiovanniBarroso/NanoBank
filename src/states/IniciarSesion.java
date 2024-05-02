package states;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IniciarSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	

	public IniciarSesion() {
		
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.ORANGE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(15);

        JLabel lblContraseña = new JLabel("Contraseña:");
        JPasswordField txtContraseña = new JPasswordField(15);

        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        JButton btnRegistrarse = new JButton("¿No estás registrado? Regístrate aquí");

        
        
        
        btnIniciarSesion.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		getContentPane().removeAll();
                Menu menu = new Menu(); 
                getContentPane().add(menu);
                revalidate();
                repaint();
        	}
        });
        
        
        
        btnRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                RegistroUsuario registroUsuario = new RegistroUsuario();
                getContentPane().add(registroUsuario);
                revalidate();
                repaint();
            }
        });

        
        
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblUsuario, gbc);

        gbc.gridx = 1;
        add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblContraseña, gbc);

        gbc.gridx = 1;
        add(txtContraseña, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnIniciarSesion, gbc);

        gbc.gridy = 3;
        add(btnRegistrarse, gbc);

        setVisible(true);
    }

}
