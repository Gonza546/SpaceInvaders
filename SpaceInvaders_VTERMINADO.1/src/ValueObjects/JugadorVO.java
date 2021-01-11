package ValueObjects;

public class JugadorVO {

	private int puntaje;
	private String nombre;
	
	
	public JugadorVO() {
		this.puntaje = 0;
		this.nombre = "AAAA";
	}
	
	public JugadorVO(String nombre, int puntaje) {
		this.puntaje = puntaje;
		this.nombre = nombre;
	}

	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
