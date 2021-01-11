package ColaPrioridad;

public interface ColaPrioridadTDA {
	
	void InicializarCola();
	void AcolarPrioridad(String x, int puntaje);//predecesor va en el metodo seleccion.
	boolean ColaVacia();
	int Prioridad();
	void Desacolar();
	String Primero();
}