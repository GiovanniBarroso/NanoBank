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




	//Metodo para conectar con la BD
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




	//Metodo para desconectar de la BD
	public void cerrarConexion(Connection conection){
		try {

			conection.close();
		} catch (SQLException e) {

			System.err.println("Se ha producido un error al cerrar la conexión");
		}
	}




	//Metodo para añadir usuarios a la BD
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




	//Metodo para realizar la transferencia en la BD
	public void sendTransferencia(int id_usuario, String nombreUsuario, String cuentaDestino, String nombreDestino, String concepto, double cantidad) throws SQLException {
		try (Connection conexion = conectar()) {
			String sql = "INSERT INTO Transferencia (cuentaOrigen, cuentaDestino, nombreDestino, cantidad, concepto, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
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




	//Metodo para registrar el Bizum en la BD
	public void sendBizum(int id_usuario, String nombreUsuario, int telefono, String nombreDestino, String concepto, double cantidad) throws SQLException {
		try (Connection conexion = conectar()) {
			String sql = "INSERT INTO Bizum (cuentaOrigen, telefono, nombreDestino, cantidad, concepto, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setString(1, nombreUsuario);
				statement.setInt(2, telefono);
				statement.setString(3, nombreDestino);
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
			sql = "UPDATE Usuario SET saldo = saldo + ? WHERE telefono = ?";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setDouble(1, cantidad);
				statement.setInt(2, telefono);
				statement.executeUpdate();
			}

			System.out.println("Bizum realizada con éxito.");
		}
	}







	//Metodo para validar si el usuario está registrado en la BD
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





	//Metodo para obtener el nombre a través del DNI 
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





	//Metodo para obtener el saldo del usuario a través del DNI
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





	//Metodo para obtener la ID a través del DNI
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




	//Metodo para comprobar si el Telefono coincide con el nombre en la BD
	public boolean coincideNombreTelefono(String telefono, String nombre) throws SQLException {
		boolean coincide = false;
		String query = "SELECT COUNT(*) AS count FROM Usuario WHERE telefono = ? AND nombre = ?";

		try (Connection connection = conectar();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, telefono);
			statement.setString(2, nombre);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt("count");
					coincide = (count > 0);
				}
			}
		}

		return coincide;
	}





	//Metodo para verificar si el telefono existe en la BD
	public boolean existeTelefono(String telefono) throws SQLException {
		boolean existe = false;
		String query = "SELECT COUNT(*) AS count FROM Usuario WHERE telefono = ?";

		try (Connection connection = conectar();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, telefono);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int count = resultSet.getInt("count");
					existe = (count > 0);
				}
			}
		}

		return existe;
	}




	//Metodo para verificar si existe el IBAN en la BD
	public boolean existeIBAN(String iban) throws SQLException {
		Connection conexion = conectar();
		boolean ibanExistente = false;

		try {
			String consulta = "SELECT COUNT(*) AS count FROM Usuario WHERE iban = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, iban);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				int count = resultado.getInt("count");
				ibanExistente = count > 0;
			}
		} finally {
			cerrarConexion(conexion);
		}

		return ibanExistente;
	}




	//Metodo para verificar si el nombre del usuario coincide con el IBAN de destino
	public boolean coincideNombreIBAN(String nombreUsuario, String iban) throws SQLException {
		boolean coincide = false;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT * FROM Usuario WHERE nombre = ? AND iban = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setString(1, nombreUsuario);
			statement.setString(2, iban);
			ResultSet resultado = statement.executeQuery();
			coincide = resultado.next();
		} finally {
			cerrarConexion(conexion);
		}
		return coincide;
	}




	//Método para guardar el formulario de carteras en la BD
	public void saveFormularioCarteras(double porcentajeRF, double porcentajeRV, double cantidadInvertida, int id_usuario) throws SQLException {
		Connection conexion = conectar();
		try {
			String sql = "INSERT INTO FormularioCarteras (porcentajeRF, porcentajeRV, cantidadInvertida, id_usuario) VALUES (?, ?, ?,?)";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setDouble(1, porcentajeRF);
				statement.setDouble(2, porcentajeRV);
				statement.setDouble(3, cantidadInvertida);
				statement.setInt(4, id_usuario);
				statement.executeUpdate();
			}
		} finally {
			cerrarConexion(conexion);
		}
	}



	// Método para obtener el porcentaje de Renta Fija por ID de usuario
	public double obtenerRFporIdUsuario(int idUsuario) throws SQLException {
		double porcentajeRF = 0.0;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT porcentajeRF FROM FormularioCarteras WHERE id_usuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setInt(1, idUsuario);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				porcentajeRF = resultado.getDouble("porcentajeRF");
			}
		} finally {
			cerrarConexion(conexion);
		}
		return porcentajeRF;
	}




	// Método para obtener el porcentaje de Renta Variable por ID de usuario
	public double obtenerRVporIdUsuario(int idUsuario) throws SQLException {
		double porcentajeRV = 0.0;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT porcentajeRV FROM FormularioCarteras WHERE id_usuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setInt(1, idUsuario);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				porcentajeRV = resultado.getDouble("porcentajeRV");
			}
		} finally {
			cerrarConexion(conexion);
		}
		return porcentajeRV;
	}




	// Método para actualizar el saldo del usuario en la base de datos
	public void actualizarSaldoUsuario(int id_usuario, double nuevoSaldo) throws SQLException {
		Connection conexion = conectar();
		try {
			String sql = "UPDATE Usuario SET saldo = ? WHERE id_usuario = ?";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setDouble(1, nuevoSaldo);
				statement.setInt(2, id_usuario);
				statement.executeUpdate();
			}
		} finally {
			cerrarConexion(conexion);
		}
	}




	// Método para obtener el porcentaje de Renta Variable por ID de usuario
	public double obtenercantidadInvertidaporIdUsuario(int idUsuario) throws SQLException {
		double cantidadInvertida = 0.0;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT cantidadInvertida FROM FormularioCarteras WHERE id_usuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setInt(1, idUsuario);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				cantidadInvertida = resultado.getDouble("cantidadInvertida");
			}
		} finally {
			cerrarConexion(conexion);
		}
		return cantidadInvertida;
	}



	//Metodo para eliminar la cartera de la BD
	public void eliminarCarteraPorIdUsuario(int idUsuario) throws SQLException {
		Connection conexion = conectar();
		try {
			// Construir la consulta SQL para eliminar la cartera del usuario
			String sql = "DELETE FROM FormularioCarteras WHERE id_usuario = ?";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				// Establecer el ID del usuario como parámetro en la consulta
				statement.setInt(1, idUsuario);
				// Ejecutar la consulta para eliminar la cartera del usuario
				statement.executeUpdate();
			}
		} finally {
			cerrarConexion(conexion);
		}
	}



	//Metodo para registrar la fecha_liquidacion en la BD
	public void registrarFechaLiquidacion(int idUsuario, Date fecha) throws SQLException {
		Connection conexion = conectar();
		try {
			String sql = "UPDATE Usuario SET fecha_liquidacion = ? WHERE id_usuario = ?";
			try (PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setDate(1, fecha);
				statement.setInt(2, idUsuario);
				statement.executeUpdate();
			}
		} finally {
			cerrarConexion(conexion);
		}
	}


	//Metodo para recibir la fecha_liquidacion en la BD
	public Date obtenerFechaLiquidacion(int idUsuario) throws SQLException {
		Date fechaLiquidacion = null;
		Connection conexion = conectar();
		try {
			String consulta = "SELECT fecha_liquidacion FROM Usuario WHERE id_usuario = ?";
			PreparedStatement statement = conexion.prepareStatement(consulta);
			statement.setInt(1, idUsuario);
			ResultSet resultado = statement.executeQuery();
			if (resultado.next()) {
				fechaLiquidacion = resultado.getDate("fecha_liquidacion");
			}
		} finally {
			cerrarConexion(conexion);
		}
		return fechaLiquidacion;
	}



}