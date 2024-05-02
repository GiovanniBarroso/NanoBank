package states;

import javax.swing.*;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class RegistroUsuario extends JPanel {


    //Declaramos atributos
    private static final long serialVersionUID = 1L;
    private JTextField txtDNI;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtIBAN;
    private JPasswordField txtContraseña;
    private JButton btnRegistrar;
    private JButton btnMostrarContraseña;
    private boolean mostrarContraseña = false;


    public RegistroUsuario() {
        setSize(400, 720);
        setBackground(Color.ORANGE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblDNI = new JLabel("DNI:");
        txtDNI = new JTextField(20);
        txtDNI.setHorizontalAlignment(JTextField.CENTER);

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField(20);
        txtNombre.setHorizontalAlignment(JTextField.CENTER);

        JLabel lblTelefono = new JLabel("Número de Teléfono:");
        txtTelefono = new JTextField(20);
        txtTelefono.setHorizontalAlignment(JTextField.CENTER);

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);
        txtEmail.setHorizontalAlignment(JTextField.CENTER);

        JLabel lblIBAN = new JLabel("IBAN:");
        txtIBAN = new JTextField(20);
        txtIBAN.setHorizontalAlignment(JTextField.CENTER);

        JLabel lblContraseña = new JLabel("Contraseña:");
        txtContraseña = new JPasswordField(20);
        txtContraseña.setHorizontalAlignment(JTextField.CENTER);

        btnRegistrar = new JButton("Registrarse");
        btnMostrarContraseña = new JButton("Mostrar Contraseña");

        // DNI
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblDNI, gbc);

        gbc.gridy++;
        add(txtDNI, gbc);

        // Nombre
        gbc.gridy++;
        add(lblNombre, gbc);

        gbc.gridy++;
        add(txtNombre, gbc);

        // Teléfono
        gbc.gridy++;
        add(lblTelefono, gbc);

        gbc.gridy++;
        add(txtTelefono, gbc);

        // Email
        gbc.gridy++;
        add(lblEmail, gbc);

        gbc.gridy++;
        add(txtEmail, gbc);

        // IBAN
        gbc.gridy++;
        add(lblIBAN, gbc);

        gbc.gridy++;
        add(txtIBAN, gbc);

        // Contraseña
        gbc.gridy++;
        add(lblContraseña, gbc);

        gbc.gridy++;
        add(txtContraseña, gbc);

        // Botón Registrar
        gbc.gridy++;
        add(btnRegistrar, gbc);

        // Botón Mostrar Contraseña
        gbc.gridy++;
        add(btnMostrarContraseña, gbc);

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
            System.out.println("¡Enhorabuena, tu perfil con nombre " + nombre + " ha sido registrado!");
            limpiarCampos();

            // Cerrar la ventana actual de registro
            SwingUtilities.getWindowAncestor(this).dispose();

            // Abrir una nueva instancia de la clase IniciarSesion
            SwingUtilities.invokeLater(() -> new IniciarSesion());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } 
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
