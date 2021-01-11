package ColaPrioridad;

import ColaPrioridad.NodoPrioridad;
import ColaPrioridad.ColaPrioridadTDA;

public class ColaPrioridadLD implements ColaPrioridadTDA {


			NodoPrioridad mayorPrioridad ;
			public void InicializarCola () {
			mayorPrioridad = null ;
			}
			public void AcolarPrioridad ( String x, int puntaje){
				NodoPrioridad nuevo = new NodoPrioridad ();
				nuevo. nombre = x;
				nuevo. prioridad = puntaje;
	
				if ( mayorPrioridad == null ||puntaje> mayorPrioridad. prioridad) {
					nuevo. sig = mayorPrioridad ;
					mayorPrioridad = nuevo;
				}
				else {
					NodoPrioridad aux = mayorPrioridad;
					while( aux.sig != null && aux . sig . prioridad >= puntaje) {
						aux = aux . sig ;
					}
					nuevo. sig = aux . sig ;
					aux . sig = nuevo;
				}
			}
			public void Desacolar() {
				mayorPrioridad = mayorPrioridad. sig ;
			}
			public String Primero() {
				return mayorPrioridad.nombre ;
			}
			public boolean ColaVacia (){
				return ( mayorPrioridad == null ) ;
			}
			public int Prioridad() {
				return mayorPrioridad.prioridad;
			}
	}
