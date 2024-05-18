package states;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sqlconnect.Conexion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class FormularioInversion extends JPanel {

	//Atributos
	private static final long serialVersionUID = 1L;
	private JComboBox<String> perfilRiesgoComboBox;
	private JComboBox<String> accionesPerdidaComboBox;
	private JComboBox<String> plazoInversionComboBox;
	private JComboBox<String> porcentajePatrimonioComboBox;
	private JComboBox<String> porcentajeGastosComboBox;
	private JComboBox<String> estabilidadIngresosComboBox;
	private JComboBox<String> gestionCarteraComboBox;
	private JComboBox<String> considerarESGComboBox;
	private JComboBox<String> invertirRentaVariableComboBox;

	private JTextField cantidadInversionTextField; 
	private JLabel cantidadInversionLabel; 

	private String nombreUsuario;
	private int id_usuario;
	private double saldo;
	private String dni;
	private double porcentajeRF; 
	private double porcentajeRV;
	private double cantidadInvertida;


	//Metodo para iniciar la clase
	public FormularioInversion(int id_usuario, String nombreUsuario, double saldo, String dni, double porcentajeRF, double porcentajeRV, double cantidadInvertida) {

		//Constructores
		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;
		this.porcentajeRF = porcentajeRF;
		this.porcentajeRV = porcentajeRV;
		this.cantidadInvertida = cantidadInvertida;

		setupUI();
	}

	private void setupUI() {


		setPreferredSize(new Dimension(450, 600));
		setLayout(new BorderLayout());


		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(0, 1, 10, 20)); 
		contentPanel.setBackground(Color.GRAY);
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));



		// Preguntas
		String[] perfilesRiesgo = {"Conservador", "Moderado", "Agresivo"};
		String[] accionesPerdida = {"Mantener la inversión", "Vender parte de la inversión", "Vender toda la inversión"};
		String[] plazoInversion = {"Corto plazo", "Mediano plazo", "Largo plazo"};
		String[] porcentajePatrimonio = {"Menos del 25%", "Entre 25% y 50%", "Más del 50%"};
		String[] porcentajeGastos = {"Menos del 25%", "Entre 25% y 50%", "Más del 50%"};
		String[] estabilidadIngresos = {"Sí", "No"};
		String[] gestionCartera = {"Conservadora", "Moderada", "Agresiva"};
		String[] considerarESG = {"Sí", "No"};
		String[] invertirRentaVariable = {"Sí", "No"};


		// Componentes JComboBox
		perfilRiesgoComboBox = new JComboBox<>(perfilesRiesgo);
		accionesPerdidaComboBox = new JComboBox<>(accionesPerdida);
		plazoInversionComboBox = new JComboBox<>(plazoInversion);
		porcentajePatrimonioComboBox = new JComboBox<>(porcentajePatrimonio);
		porcentajeGastosComboBox = new JComboBox<>(porcentajeGastos);
		estabilidadIngresosComboBox = new JComboBox<>(estabilidadIngresos);
		gestionCarteraComboBox = new JComboBox<>(gestionCartera);
		considerarESGComboBox = new JComboBox<>(considerarESG);
		invertirRentaVariableComboBox = new JComboBox<>(invertirRentaVariable);



		// Nuevo JTextField con tamaño preferido y JLabel ajustado
		cantidadInversionLabel = new JLabel("Cantidad a invertir:");
		cantidadInversionLabel.setFont(new Font("Impact", Font.PLAIN, 18));
		cantidadInversionLabel.setHorizontalAlignment(SwingConstants.CENTER); 
		cantidadInversionLabel.setPreferredSize(new Dimension(20, 20));

		cantidadInversionTextField = new JTextField(10); 


		// Agregar componentes al panel
		contentPanel.add(createQuestionPanel("Perfil de Riesgo:", perfilRiesgoComboBox));
		contentPanel.add(createQuestionPanel("Acciones a tomar ante una pérdida del 5%:", accionesPerdidaComboBox));
		contentPanel.add(createQuestionPanel("Plazo de Inversión:", plazoInversionComboBox));
		contentPanel.add(createQuestionPanel("Porcentaje de Patrimonio a Invertir:", porcentajePatrimonioComboBox));
		contentPanel.add(createQuestionPanel("Porcentaje de Ingresos destinados a gastos:", porcentajeGastosComboBox));
		contentPanel.add(createQuestionPanel("Estabilidad de Ingresos:", estabilidadIngresosComboBox));
		contentPanel.add(createQuestionPanel("Gestión de Cartera:", gestionCarteraComboBox));
		contentPanel.add(createQuestionPanel("¿Considerar factores ESG en la cartera?:", considerarESGComboBox));
		contentPanel.add(createQuestionPanel("¿Invertir al 100% en renta variable?:", invertirRentaVariableComboBox));

		// Agregar JTextField y JLabel al panel
		contentPanel.add(cantidadInversionLabel);
		contentPanel.add(cantidadInversionTextField);

		// Botones
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton confirmButton = new JButton();
		ImageIcon FondoconfirmButton = new ImageIcon(getClass().getResource("/img/confirm_btn.png"));
		confirmButton.setPreferredSize(new Dimension(160,30));
		confirmButton.setIcon(FondoconfirmButton);

		JButton btnVolver = new JButton();
		ImageIcon FondobtnBack = new ImageIcon(getClass().getResource("/img/backform_btn.png"));
		btnVolver.setPreferredSize(new Dimension(160,30));
		btnVolver.setIcon(FondobtnBack);


		buttonPanel.add(confirmButton);
		buttonPanel.add(btnVolver);


		// Agregar componentes al panel principal
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);



		// Agregar eventos a los botones
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				validarFormulario();

			}
		});



		// Acción del botón Volver atrás
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volveraMenu(porcentajeRF, porcentajeRV, cantidadInvertida);
			}
		});

	}



	//Panel de las preguntas
	private JPanel createQuestionPanel(String question, JComboBox<String> comboBox) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(question);

		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Impact", Font.PLAIN, 18));

		panel.setBackground(Color.GRAY);
		panel.add(label, BorderLayout.NORTH);
		panel.add(comboBox, BorderLayout.CENTER);
		return panel;
	}




	private void sendFormulario(double porcentajeRF, double porcentajeRV, double cantidadInvertida) {

		String perfilRiesgo = (String) perfilRiesgoComboBox.getSelectedItem();
		String accionesPerdida = (String) accionesPerdidaComboBox.getSelectedItem();
		String plazoInversion = (String) plazoInversionComboBox.getSelectedItem();
		String porcentajePatrimonio = (String) porcentajePatrimonioComboBox.getSelectedItem();
		String porcentajeGastos = (String) porcentajeGastosComboBox.getSelectedItem();
		String estabilidadIngresos = (String) estabilidadIngresosComboBox.getSelectedItem();
		String gestionCartera = (String) gestionCarteraComboBox.getSelectedItem();
		String considerarESG = (String) considerarESGComboBox.getSelectedItem();
		String invertirRentaVariable = (String) invertirRentaVariableComboBox.getSelectedItem();


		cantidadInvertida = Double.parseDouble(cantidadInversionTextField.getText());


		double nuevoSaldo = saldo - cantidadInvertida;

		if (perfilRiesgo.equals("Conservador")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		} else if (perfilRiesgo.equals("Moderado")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (perfilRiesgo.equals("Agresivo")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		}



		if (accionesPerdida.equals("Mantener la inversión")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		} else if (accionesPerdida.equals("Vender parte de la inversión")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (accionesPerdida.equals("Vender toda la inversión")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		}



		if (plazoInversion.equals("Corto plazo")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (plazoInversion.equals("Mediano plazo")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (plazoInversion.equals("Largo plazo")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		}



		if (porcentajePatrimonio.equals("Menos del 25%")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		} else if (porcentajePatrimonio.equals("Entre 25% y 50%")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		} else if (porcentajePatrimonio.equals("Más del 50%")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		}



		if (porcentajeGastos.equals("Menos del 25%")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (porcentajeGastos.equals("Entre 25% y 50%")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		} else if (porcentajeGastos.equals("Más del 50%")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		}



		if (estabilidadIngresos.equals("Sí")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (estabilidadIngresos.equals("No")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		}



		if (gestionCartera.equals("Conservadora")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (gestionCartera.equals("Moderada")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		} else if (gestionCartera.equals("Agresiva")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		}



		if (considerarESG.equals("Sí")) {
			porcentajeRF += 10.0;
			porcentajeRV += 0.0;
		} else if (considerarESG.equals("No")) {
			porcentajeRF += 0.0;
			porcentajeRV += 10.0;
		}



		if (invertirRentaVariable.equals("Sí")) {
			porcentajeRF = 0.0;
			porcentajeRV = 100.0;
		} else if (invertirRentaVariable.equals("No")) {
			porcentajeRF += 0.0;
			porcentajeRV += 20.0;
		}



		// Calcular el porcentaje restante para que la suma sea 100%
		double porcentajeRestante = 100.0 - (porcentajeRF + porcentajeRV);


		// Asignar el porcentaje restante al porcentaje más bajo entre porcentajeRF y porcentajeRV
		if (porcentajeRestante > 0) {
			if (porcentajeRF <= porcentajeRV) {
				porcentajeRF += porcentajeRestante;
			} else {
				porcentajeRV += porcentajeRestante;
			}
		}



		try {
			Conexion conexion = new Conexion();

			java.sql.Date fechaLiquidacion = conexion.obtenerFechaLiquidacion(id_usuario);

			// Verificar si han pasado al menos 30 días
			if (fechaLiquidacion != null) {
				java.util.Date fechaActual = new java.util.Date();
				long diferencia = fechaActual.getTime() - fechaLiquidacion.getTime();
				long diasPasados = diferencia / (1000 * 60 * 60 * 24); 

				if (diasPasados < 30) {
					JOptionPane.showMessageDialog(this, "Lo siento, existe una penalización de 30 días desde que borras tu última cartera.");
					return; 
				}
			}

			conexion.saveFormularioCarteras(porcentajeRF, porcentajeRV, cantidadInvertida, id_usuario);

			conexion.actualizarSaldoUsuario(id_usuario, nuevoSaldo);

			JOptionPane.showMessageDialog(null, "Formulario de Idoneidad registrado correctamente.");
			System.out.println("\nFormulario guardado en la BD");

			volveraMenu2(nuevoSaldo, porcentajeRF, porcentajeRV, cantidadInvertida);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al insertar datos en la base de datos: " + e.getMessage());
		}
	}





	// Método para validar el formulario antes de enviarlo
	private void validarFormulario() {
		try {
			double cantidadInversion = Double.parseDouble(cantidadInversionTextField.getText());
			if (cantidadInversion > saldo) {
				JOptionPane.showMessageDialog(null,
						"La cantidad a invertir supera el saldo disponible. Por favor, ingrese una cantidad válida.",
						"Error de validación", JOptionPane.ERROR_MESSAGE);
			} else {
				sendFormulario(porcentajeRF, porcentajeRV, cantidadInvertida);
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Por favor, ingrese una cantidad válida.", "Error de validación", JOptionPane.ERROR_MESSAGE);
		}
	}





	// Método para volver a Menu
	private void volveraMenu(double porcentajeRF, double porcentajeRV, double cantidadInvertida) {
		Container parent = getParent();
		if (parent != null) {

			parent.removeAll();

			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
			parent.add(menu);
			parent.revalidate();
			parent.repaint();

		} else {
			System.out.println("El componente no tiene un padre [🆎].");
		}
	}



	// Método para volver a Menu
	private void volveraMenu2(double nuevoSaldo, double porcentajeRF, double porcentajeRV, double cantidadInvertida) {
		Container parent = getParent();
		if (parent != null) {

			parent.removeAll();
			Menu menu = new Menu(id_usuario, nombreUsuario, nuevoSaldo, dni, porcentajeRF, porcentajeRV, cantidadInvertida);
			parent.add(menu);

			parent.revalidate();
			parent.repaint();

		} else {
			System.out.println("El componente no tiene un padre [🆎].");
		}
	}



}