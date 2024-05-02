package sqlconnect;

public class Usuario {


	//Declaramos atributos
	private int dni;
	private String Contraseña;
	private String Nombre;
	private int telefono;
	private String email;
	private String iban;


	//Constructor
	public Usuario(int dni, String contraseña, String nombre, int numTelefono, String email, String iban) {

		this.dni = dni;
		this.Contraseña = contraseña;
		this.Nombre = nombre;
		this.telefono = numTelefono;
		this.email = email;
		this.iban = iban;
	}



	//Metodos getter and setter
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
		return telefono;
	}


	public void setNumTelefono(int numTelefono) {
		this.telefono = numTelefono;
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
				", dni=" + dni +
				", Contraseña='" + Contraseña + '\'' +
				", Nombre='" + Nombre + '\'' +
				", numTelefono=" + telefono +
				", email='" + email + '\'' +
				", iban='" + iban + '\'' +
				'}';

	}
}