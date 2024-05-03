package states;

import javax.swing.*;
import javax.swing.border.Border;

import sqlconnect.Conexion;
import states.IniciarSesion.RoundedBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class RegistroUsuario extends JPanel {

    private JTextField txtDNI;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtIBAN;
    private JPasswordField txtContraseña;
    private JButton btnRegistrar;
    private JButton btnMostrarContraseña;
    private JButton btnVolver;
    private boolean mostrarContraseña = false;

    public RegistroUsuario() {
        setLayout(new BorderLayout());

        // Panel para el fondo gris
        JPanel panelFondo = new JPanel();
        panelFondo.setBackground(Color.GRAY);
        panelFondo.setLayout(new GridBagLayout());
        
     // Establecer el borde redondeado
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        panelFondo.setBorder(BorderFactory.createCompoundBorder(border, new RoundedBorder(10)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setFont(new Font("Impact", Font.PLAIN, 20));
        txtDNI = new JTextField(20);
        txtDNI.setHorizontalAlignment(JTextField.CENTER);
        txtDNI.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Impact", Font.PLAIN, 20));
        txtNombre = new JTextField(20);
        txtNombre.setHorizontalAlignment(JTextField.CENTER);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblTelefono = new JLabel("Número de Teléfono:");
        lblTelefono.setFont(new Font("Impact", Font.PLAIN, 20));
        txtTelefono = new JTextField(20);
        txtTelefono.setHorizontalAlignment(JTextField.CENTER);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Impact", Font.PLAIN, 20));
        txtEmail = new JTextField(20);
        txtEmail.setHorizontalAlignment(JTextField.CENTER);
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblIBAN = new JLabel("IBAN:");
        lblIBAN.setFont(new Font("Impact", Font.PLAIN, 20));
        txtIBAN = new JTextField(20);
        txtIBAN.setHorizontalAlignment(JTextField.CENTER);
        txtIBAN.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setFont(new Font("Impact", Font.PLAIN, 20));
        txtContraseña = new JPasswordField(20);
        txtContraseña.setHorizontalAlignment(JTextField.CENTER);
        txtContraseña.setFont(new Font("Arial", Font.PLAIN, 16));

        btnRegistrar = new JButton("Registrarse");
        btnMostrarContraseña = new JButton("Mostrar Contraseña");
        btnVolver = new JButton("Volver atrás");

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFondo.add(lblDNI, gbc);

        gbc.gridy++;
        panelFondo.add(txtDNI, gbc);

        // Nombre
        gbc.gridy++;
        panelFondo.add(lblNombre, gbc);

        gbc.gridy++;
        panelFondo.add(txtNombre, gbc);

        // Teléfono
        gbc.gridy++;
        panelFondo.add(lblTelefono, gbc);

        gbc.gridy++;
        panelFondo.add(txtTelefono, gbc);

        // Email
        gbc.gridy++;
        panelFondo.add(lblEmail, gbc);

        gbc.gridy++;
        panelFondo.add(txtEmail, gbc);

        // IBAN
        gbc.gridy++;
        panelFondo.add(lblIBAN, gbc);

        gbc.gridy++;
        panelFondo.add(txtIBAN, gbc);

        // Contraseña
        gbc.gridy++;
        panelFondo.add(lblContraseña, gbc);

        gbc.gridy++;
        panelFondo.add(txtContraseña, gbc);

        // Botón Registrar
        gbc.gridy++;
        panelFondo.add(btnRegistrar, gbc);

        // Botón Mostrar Contraseña
        gbc.gridy++;
        panelFondo.add(btnMostrarContraseña, gbc);

        // Botón Volver a IniciarSesion
        gbc.gridy++;
        panelFondo.add(btnVolver, gbc);

        add(panelFondo, BorderLayout.CENTER);

        // Acción del botón Registrar
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });

        // Acción del botón Mostrar Contraseña
        btnMostrarContraseña.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarContraseña = !mostrarContraseña;
                if (mostrarContraseña) {
                    txtContraseña.setEchoChar((char) 0);
                } else {
                    txtContraseña.setEchoChar('*');
                }
            }
        });

        // Acción del botón Volver a IniciarSesion
        btnVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverAIniciarSesion();
            }
        });
    }

    private void saveUser() {
        // Obtener los datos de los campos de entrada
        String DNI = txtDNI.getText();
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String email = txtEmail.getText();
        String iban = txtIBAN.getText();
        String contraseña = new String(txtContraseña.getPassword());

        // Validar que todos los campos estén llenos
        if (DNI.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || iban.isEmpty() || contraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar el formato del DNI (debe ser numérico)
        try {
            Integer.parseInt(DNI);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar el formato del teléfono (debe ser numérico)
        try {
            Integer.parseInt(telefono);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El número de teléfono debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar el formato del email (básicamente si contiene un @)
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "El email no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Conexion conexionDB = new Conexion();
        try {
            // Llamar al método addUser de la clase Conexion para agregar un nuevo usuario
            conexionDB.addUser(Integer.parseInt(DNI), contraseña, nombre, Integer.parseInt(telefono), email, iban);
            JOptionPane.showMessageDialog(this, "Usuario añadido con éxito.");

            // Mostrar por consola el resultado del registro para verificar los datos
            System.out.println("¡Enhorabuena, tu perfil con nombre " + nombre + " ha sido registrado!\n");
            limpiarCampos();

            // Cerrar la ventana actual de registro
            SwingUtilities.getWindowAncestor(this).dispose();

            // Abrir una nueva instancia de la clase IniciarSesion
            SwingUtilities.invokeLater(() -> new IniciarSesion());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para volver a IniciarSesion
    private void volverAIniciarSesion() {
        // Cerrar la ventana actual de registro
        SwingUtilities.getWindowAncestor(this).dispose();

        // Abrir una nueva instancia de la clase IniciarSesion
        SwingUtilities.invokeLater(() -> new IniciarSesion());
    }

    // Método para limpiar los campos de entrada
    public void limpiarCampos() {
        txtDNI.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtIBAN.setText("");
        txtContraseña.setText("");
    }
}
