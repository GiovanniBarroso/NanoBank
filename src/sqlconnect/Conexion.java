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
			//Cierre de la conexión
			conection.close();
		} catch (SQLException e) {
			System.err.println("Se ha producido un error al cerrar la conexión");

		}
	}
	
	
	
	// Método para registrar un usuario en la base de datos
	public void addUser(int dni, String contraseña, String nombre, int telefono, String email, String iban) throws SQLException {
	    Connection conexion = conectar();
	    Statement statement = conexion.createStatement();
	    String sql = "INSERT INTO Usuario (dni, contraseña, nombre, telefono, email, iban) VALUES (" + dni + ", '" + contraseña + "', '" + nombre + "', " + telefono + ", '" + email + "', '" + iban + "')";
	    statement.executeUpdate(sql);
	    statement.close();
	}





	public void insertData() throws SQLException{
		Connection conexion = conectar();

		try {

			//Datos a insertar
			String consultasInserccion = "INSERT INTO Usuario VALUES(11, 'Martin');";
			System.out.println(consultasInserccion);
			//Creación del Statement para poder reqalizar la consulta
			Statement consul = conexion.createStatement();
			//Ejecución de la consulta
			consul.executeUpdate(consultasInserccion);
			System.out.println("Datos insertados correctamente");
			//Cierre del Statement
			consul.close();
		}finally {
			//Cierre de la conexión
			cerrarConexion(conexion);
		}
	}




	public void getData() throws SQLException{
		Connection conexion = conectar();

		if(conexion!=null) {
			try {

				//Datos a consultar
				String consultasSeleccion = "SELECT * FROM producto";
				System.out.println(consultasSeleccion);
				Statement consul = conexion.createStatement();


				//Ejecución de la consulta
				if(consul.execute(consultasSeleccion)) {
					ResultSet resultset = consul.getResultSet();
					while(resultset.next()) {
						Usuario Usuario = new Usuario (resultset.getInt("dni"),
								resultset.getString("Contraseña"), resultset.getString("Nombre"), resultset.getInt("numTelefono"),
								resultset.getString("email"),resultset.getString("iban"));
						System.out.println(Usuario.toString());

					}

					System.out.println("Datos recuperados correctamente");
				}
				//Cierre del Statement
				consul.close();

			}finally {
				//Cierre de la conexión
				cerrarConexion(conexion);
			}
		}
	}
}