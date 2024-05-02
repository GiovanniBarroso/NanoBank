package sqlconnect;

public class Usuario {

	//Declaramos atributos
	private int id;
	private int dni;
	private String Contraseña;
	private String Nombre;
	private int numTelefono;
	private String email;
	private String iban;



	//Constructor
	public Usuario(int id, int dni, String contraseña, String nombre, int numTelefono, String email, String iban) {
		this.id = id;
		this.dni = dni;
		this.Contraseña = contraseña;
		this.Nombre = nombre;
		this.numTelefono = numTelefono;
		this.email = email;
		this.iban = iban;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getDni() {
		return dni;
	}


	public void setDni(int dni) {
		this.dni = dni;
	}


	public String getContraseña() {
		return Contraseña;
	}


	public void setContraseña(String contraseña) {
		Contraseña = contraseña;
	}


	public String getNombre() {
		return Nombre;
	}


	public void setNombre(String nombre) {
		Nombre = nombre;
	}


	public int getNumTelefono() {
		return numTelefono;
	}


	public void setNumTelefono(int numTelefono) {
		this.numTelefono = numTelefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getIban() {
		return iban;
	}


	public void setIban(String iban) {
		this.iban = iban;
	}

	
	
	@Override
	public String toString() {
		return "Usuario{" +
				"id=" + id +
				", dni=" + dni +
				", Contraseña='" + Contraseña + '\'' +
				", Nombre='" + Nombre + '\'' +
				", numTelefono=" + numTelefono +
				", email='" + email + '\'' +
				", iban='" + iban + '\'' +
				'}';
		
	}
}