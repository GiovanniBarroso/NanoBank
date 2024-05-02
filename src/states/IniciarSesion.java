package states;

import javax.swing.*;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class IniciarSesion extends JFrame {

    private static final long serialVersionUID = 1L;

    public IniciarSesion() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.ORANGE);
        setLayout(new GridBagLayout());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo
        ImageIcon logoIcon = new ImageIcon(IniciarSesion.class.getResource("/img/foto_6.png"));
        JLabel lblLogo = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH)));

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Impact", Font.PLAIN, 20)); // Cambiar la fuente y el tamaño
        JTextField txtUsuario = new JTextField(20);
        txtUsuario.setHorizontalAlignment(JTextField.CENTER);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 16)); // Cambiar la fuente y el tamaño

        // Contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setFont(new Font("Impact", Font.PLAIN, 20)); // Cambiar la fuente y el tamaño
        JPasswordField txtContraseña = new JPasswordField(20);
        txtContraseña.setHorizontalAlignment(JPasswordField.CENTER);
        txtContraseña.setFont(new Font("Arial", Font.PLAIN, 16)); // Cambiar la fuente y el tamaño

        // Botón de iniciar sesión
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setHorizontalAlignment(JButton.CENTER);
        btnIniciarSesion.setFont(new Font("Arial Black", Font.PLAIN, 16)); // Cambiar la fuente y el tamaño

        // Botón de registrarse
        JButton btnRegistrarse = new JButton("¿No estás registrado? Regístrate aquí");
        btnRegistrarse.setHorizontalAlignment(JButton.CENTER);
        btnRegistrarse.setFont(new Font("Arial Black", Font.PLAIN, 16)); // Cambiar la fuente y el tamaño

     // Añadir el logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span 2 columnas
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblLogo, gbc);
        gbc.gridwidth = 1; // Restaurar el valor original

        // Usuario
        gbc.gridy = 1;
        add(lblUsuario, gbc);

        gbc.gridy = 2;
        add(txtUsuario, gbc);

        // Contraseña
        gbc.gridy = 3;
        add(lblContraseña, gbc);

        gbc.gridy = 4;
        add(txtContraseña, gbc);

        // Botón de iniciar sesión
        gbc.gridy = 5;
        add(btnIniciarSesion, gbc);

        // Botón de registrarse
        gbc.gridy = 6;
        add(btnRegistrarse, gbc);
        
        
        // Eventos de los botones
        btnIniciarSesion.addActionListener(e -> iniciarSesion(txtUsuario.getText(), new String(txtContraseña.getPassword())));
        btnRegistrarse.addActionListener(e -> mostrarRegistroUsuario());

        // Escuchar evento de presionar "Enter" en el campo de contraseña
        txtContraseña.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion(txtUsuario.getText(), new String(txtContraseña.getPassword()));
            }
        });
    }

    private void iniciarSesion(String usuario, String contraseña) {
        try {
            Conexion conexion = new Conexion();
            if (conexion.validarCredenciales(usuario, contraseña)) {
                getContentPane().removeAll();
                getContentPane().add(new Menu());
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error de conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarRegistroUsuario() {
        getContentPane().removeAll();
        getContentPane().add(new RegistroUsuario());
        revalidate();
        repaint();
    }
}
