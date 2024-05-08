package states;

import javax.swing.*;
import javax.swing.border.Border;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioInversion extends JPanel {


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


	private String nombreUsuario;
	private int id_usuario;
	private double saldo;
	private String dni;

	public FormularioInversion(int id_usuario, String nombreUsuario, double saldo, String dni) {
		setupUI(id_usuario, nombreUsuario, saldo, dni);
	}

	private void setupUI(int id_usuario, String nombreUsuario, double saldo, String dni) {

		this.id_usuario = id_usuario;
		this.nombreUsuario = nombreUsuario;
		this.saldo = saldo;
		this.dni = dni;


		setPreferredSize(new Dimension(450, 600));
		setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(0, 1, 10, 20)); 
		contentPanel.setBackground(Color.GRAY);

		// Crear un borde compuesto con un borde negro
		Border border = BorderFactory.createLineBorder(Color.BLACK,3);

		// Establecer el borde al panel principal
		contentPanel.setBorder(border);


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

		// Botones
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton confirmButton = new JButton("Confirmar Formulario");
		JButton btnVolver = new JButton("Volver Atrás");
		buttonPanel.add(confirmButton);
		buttonPanel.add(btnVolver);

		// Agregar componentes al panel principal
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);




		// Agregar eventos a los botones
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "Formulario confirmado");
			}
		});

		// Acción del botón Volver a menu
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				volveraMenu();
			}
		});
	}



	//Panel de las preguntas
	private JPanel createQuestionPanel(String question, JComboBox<String> comboBox) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(question);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Impact", Font.PLAIN, 18)); // Establecer la fuente y el tamaño
		panel.setBackground(Color.GRAY);
		panel.add(label, BorderLayout.NORTH);
		panel.add(comboBox, BorderLayout.CENTER);
		return panel;
	}

	// Métodos para obtener los valores seleccionados en los JComboBox
	public String getPerfilRiesgo() {
		return (String) perfilRiesgoComboBox.getSelectedItem();
	}

	public String getAccionesPerdida() {
		return (String) accionesPerdidaComboBox.getSelectedItem();
	}

	public String getPlazoInversion() {
		return (String) plazoInversionComboBox.getSelectedItem();
	}

	public String getPorcentajePatrimonio() {
		return (String) porcentajePatrimonioComboBox.getSelectedItem();
	}

	public String getPorcentajeGastos() {
		return (String) porcentajeGastosComboBox.getSelectedItem();
	}

	public String getEstabilidadIngresos() {
		return (String) estabilidadIngresosComboBox.getSelectedItem();
	}

	public String getGestionCartera() {
		return (String) gestionCarteraComboBox.getSelectedItem();
	}

	public String getConsiderarESG() {
		return (String) considerarESGComboBox.getSelectedItem();
	}

	public String getInvertirRentaVariable() {
		return (String) invertirRentaVariableComboBox.getSelectedItem();
	}







	// Método para volver a IniciarSesion
	private void volveraMenu() {
		Container parent = getParent();
		if (parent != null) {
			// Eliminar todos los componentes del padre
			parent.removeAll();

			// Crear una nueva instancia de Menu y agregarla al padre
			Menu menu = new Menu(id_usuario, nombreUsuario, saldo, dni);
			parent.add(menu);

			// Actualizar la interfaz de usuario
			parent.revalidate();
			parent.repaint();
		} else {
			// Manejar la situación donde getParent() devuelve null
			System.out.println("El componente no tiene un padre [🆎].");
		}
	}

}
