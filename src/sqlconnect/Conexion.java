package sqlconnect;

import java.sql.*;

public class Conexion {


	//driver JDBC
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

	//dirección de la BBDD MySQL
	private static final String URL = "jdbc:mysql://localhost:3306/NanoBank";

	//usuario y contraseña de acceso a la BD
	private static final String USUARIO = "root";
	private static final String PASSWORD = "";



	public Connection conectar() {
		Connection conexion = null;

		try {
			Class.forName(DRIVER);
			conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
			System.out.println("Conexión con la base de datos establecida");

		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Error en la conexión con la base de datos");
			e.printStackTrace();
		}

		return conexion;
	}




	public void cerrarConexion(Connection conection){
		try {

			conection.close();
		} catch (SQLException e) {

			System.err.println("Se ha producido un error al cerrar la conexión");
		}
	}



	public void addUser(String dni, String contraseña, String nombre, int telefono, String email, String iban) throws SQLException {
		Connection conexion = conectar();
		String sql = "INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement statement = conexion.prepareStatement(sql)) {
			statement.setString(1, dni);
			statement.setString(2, contraseña);
			statement.setString(3, nombre);
			statement.setInt(4, telefono);
			statement.setString(5, email);
			statement.setString(6, iban);
			statement.executeUpdate();
		}
	}



	public void sendTransferencia(int id_usuario, String nombreUsuario, String cuentaDestino, String nombreDestino, String concepto, double cantidad) throws SQLException {
		try (Connection conexion = conectar()) {
			String sql = "INSERT INTO Transacciones (cuentaOrigen, cuentaDestino, nombreDestino, cantidad, concepto, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setString(1, nombreUsuario);
				statement.setString(2, nombreDestino);
				statement.setString(3, cuentaDestino);
				statement.setDouble(4, cantidad);
				statement.setString(5, concepto);
				statement.setInt(6, id_usuario); 
				statement.executeUpdate();
			}

			// Actualizar el saldo del usuario en la tabla Usuario
			sql = "UPDATE Usuario SET saldo = saldo - ? WHERE id_usuario = ?";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setDouble(1, cantidad);
				statement.setInt(2, id_usuario);
				statement.executeUpdate();
			}

			// Actualizar el saldo del destinatario en la tabla Usuario
			sql = "UPDATE Usuario SET saldo = saldo + ? WHERE iban = ?";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setDouble(1, cantidad);
				statement.setString(2, nombreDestino);
				statement.executeUpdate();
			}

			System.out.println("Transferencia realizada con éxito.");
		}
	}









	public boolean validarCredenciales(String usuario, String contraseña) throws SQLException {
		Connection conexion = conectar();
		boolean usuarioValido = false;

		try {
			String consultaLogin = "SELECT * FROM Usuario WHERE dni = ? AND contraseña = ?";
			PreparedStatement statement = conexion.prepareStatement(consultaLogin);
			statement.setString(1, usuario);
			statement.setString(2, contraseña);

			ResultSet resultado = statement.executeQuery();
			usuarioValido = resultado.next();

			// Cerrar recursos
			resultado.close();
			statement.close();
		} finally {
			cerrarConexion(conexion);
		}

		return usuarioValido;
	}





	public String obtenerNombrePorDNI(String dni) throws SQLException {
		String nombre = null;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT nombre FROM Usuario WHERE dni = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, dni);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				nombre = resultado.getString("nombre");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cerrarConexion(conexion);
		}
		return nombre;
	}





	public double obtenerSaldoPorDNI (String dni) throws SQLException {
		double saldo = 0;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT saldo FROM Usuario WHERE dni = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, dni);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				saldo = resultado.getDouble("saldo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cerrarConexion(conexion);
		}
		return saldo;
	}





	public int obtenerIdPorDNI(String dni) throws SQLException {
		int id_usuario = -1; // Valor por defecto en caso de que no se encuentre el usuario
		Connection conexion = conectar();
		try {
			String consulta = "SELECT id_usuario FROM Usuario WHERE dni = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, dni);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				id_usuario = resultado.getInt("id_usuario");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			cerrarConexion(conexion);
		}
		return id_usuario;
	}
}