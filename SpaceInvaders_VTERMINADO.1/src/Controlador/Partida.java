package Controlador;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

import ColaPrioridad.ColaPrioridadLD;
import Negocio.ConjuntoInvasor;
import Negocio.Enemigo;
import Negocio.Jugador;
import Negocio.Muro;
import Negocio.Nave;
import ValueObjects.DisparoVO;
import ValueObjects.EnemigoVO;
import ValueObjects.JugadorVO;
import ValueObjects.MuroVO;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Partida.java
//  @ Date : 05/11/2018
//  @ Author : 
//
//




public class Partida {
	
	private ColaPrioridadLD jugadores = new ColaPrioridadLD();
	private int puntaje, puntajeMax, i, j,pos=0,posMover=0;
	private Nave n ;
	private ConjuntoInvasor ci ;
	private Muro[] m = new Muro[4];
	private static Partida instancia;
	//private Jugador jugador;
	Dimension s = new Dimension();
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	private Partida(){
		
		this.s = tk.getScreenSize();
		crearNave();
		crearEnemigos();
		crearMuros();
	}
	
	public static Partida getInstancia(){
		if(instancia==null)
		{
			instancia= new Partida();
		}
		return instancia;
	}
	
	public void actualizarVida(boolean b) {
		
		if(b)
			n.setVida(n.getVida()+1);
		else
			n.setVida(n.getVida()-1);
	
	}
	public boolean vidaLlena(){
		if(n.getVida()!=3){
			return false;
		}
		return true;
	}
	
	
	public void borrarDisparoEnemigo(DisparoVO dis){
		for(i=0;i<15;i++){
			if(dis.getX()==ci.getCi()[i].getX()&&dis.getY()==ci.getCi()[i].getY())
				System.out.println("borre");
				ci.getCi()[i].setDisparo();
		}
	}
	
	public void clearEnemigos() {
		
		ci=null;
		
	}
	public void borrarDisparoNave(){
	
		n.setDisparo();
	
	}

	public void clearNave(){
		n=null;
	}
	public void restartNave(){
		n.setX(150);
	}
	
	public void crearMuros(){
		int sep=125;
		for(i=0;i<4;i++){
			if(m[i]==null)
			m[i]= new Muro(100,sep,s.height-250);
			sep+=325;
		}
	}
	
	public void clearMuros(){
		for(i=0;i<4;i++){
			m[i]=null;
		}
	}

	public JugadorVO[] topJgs(){
		JugadorVO[] jgs = new JugadorVO[5];
		ColaPrioridadLD colaAux = new ColaPrioridadLD();
		for(i=0;i<5;i++){
			if(!jugadores.ColaVacia()){
				Jugador aux = new Jugador(jugadores.Primero(),jugadores.Prioridad());
				jgs[i]= new JugadorVO(aux.getNombre(),aux.getPuntaje());
				colaAux.AcolarPrioridad(jugadores.Primero(), jugadores.Prioridad());
				jugadores.Desacolar();
			}
			else
				jgs[i]= new JugadorVO();
		}
		
		while(!colaAux.ColaVacia()){
			jugadores.AcolarPrioridad(colaAux.Primero(), colaAux.Prioridad());
			colaAux.Desacolar();
			
		}
		
		return jgs;
	}
	
	public void crearEnemigos() {
		
		ci = new ConjuntoInvasor(20,64);
		
	}
	
	public void crearNave() {
		
		n = new Nave(3,150,s.height-150);
		
	}
	public EnemigoVO tocoEnemigo(){
		EnemigoVO e = null;
		for(i=0;i<15;i++){
			if(n.getDisparo().detectarColisiones(ci.getCi()[i].getX(),ci.getCi()[i].getY(),ci.getCi()[i].isEstado())){
				ci.getCi()[i].setEstado(false);
				e = new EnemigoVO(ci.getCi()[i].getX(),ci.getCi()[i].getY());
			}
		}
		return e;
	}
	public boolean tocoNave(DisparoVO d){
		
		boolean toco=false;
		toco = n.soyLaNave(d);
		return toco;
	}
	
	public MuroVO tocoMuro(DisparoVO d){
		
		
		MuroVO muro = null, muroDead = null;
		
		for(i=0;i<4;i++){
			if(d.getY()>=m[i].getY()&&d.getY()<=m[i].getY()+64&&m[i].isEstado()){
				if(d.getX()>=m[i].getX()&&d.getX()<=m[i].getX()+128){
					muro = new MuroVO(m[i].getX(),m[i].getY());
					if(d.getY()>=m[i].getY()+32&&d.getY()<=m[i].getY()+64&&d.getX()>=m[i].getX()&&d.getX()<=m[i].getX()+128)
						m[i].debilitar(10);
					else
						m[i].debilitar(5);
					if(m[i].getEnergia()>0)
						muro.setEnergia(m[i].getEnergia());
					else{
						m[i].setEstado(false);
						muroDead = new MuroVO(m[i].getX(),m[i].getY()); 
					}
				}
			}
		}
		if(muroDead!=null)
			return muroDead;
		return muro;
			
	}
	
	public EnemigoVO[] moverEnemigos() {
		
		EnemigoVO[] e = new EnemigoVO[15];
		
		if(ci.isSentido() && puedoMoverDer()){
			e = this.ci.moverDer();
		}
		else
		{
			if(ci.isSentido()&&!puedoMoverDer())
			{
				ci.setSentido(false);
				e=this.ci.bajar();
			}
			if(!ci.isEmpty() && puedoMoverIzq()){
				e = this.ci.moverIzq();
			}
			else{
				if(!ci.isSentido() &&!puedoMoverIzq())
				{
					ci.setSentido(true);
					e = this.ci.bajar();
				}
			}
		}
		
		return e;
	}
	
	public int moverNave(int cod) {
		
		if(cod == 39)
			return n.moverDerecha(s.width);
		else 
			if(cod==37)
				return n.moverIzq(s.width);
		return 0;
	}			
		
	public EnemigoVO[] getEnemigos() {
		
		EnemigoVO[] e = new EnemigoVO[15];
		int i;
		
		for(i=0;i<15;i++) {
			
			e[i] = new EnemigoVO();
			if(ci.getCi()[i].isEstado()){
			e[i].setX(ci.getCi()[i].getX());
			e[i].setY(ci.getCi()[i].getY());
			}
		}
		
		return e;
	}
	
	public boolean ultimoEnemigo() {
		for(i=0;i<15;i++){
			if(ci.getCi()[i].isEstado())
				return false;
		}
		return true;
	}
	
	private boolean puedoMoverDer(){
			
			int pos=4, pos2=14;
			boolean resultado = true;;
			
			if(!this.ci.isEmpty()){
				while(pos!=-1&&pos2!=-1) {
					if(ci.getCi()[pos].isEstado()) {
						if(ci.getCi()[pos].getX()+75 < s.width-80)
							pos=-1; 
						else 
							pos2=-1;
					}
					else {
						if(pos!=pos2)
							pos+=5;
						else {
							pos-=11;
							pos2--;
						}
					}
				}
				
				if(pos==-1)
					resultado = true;
				else 
					if(pos2==-1)
						resultado = false;
				
			}
			return resultado;
		}

	public void guardarJugador(String nombre){
		
		
		esPuntajeMax(this.puntaje);
		
		this.jugadores.AcolarPrioridad(nombre, this.puntaje);
		
	}
	private void esPuntajeMax(int p){
		if(p>this.puntajeMax)
			puntajeMax=p;
	}
	
	public int getPuntajeMax() {
		return puntajeMax;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	private boolean puedoMoverIzq(){
		
		int pos=0, pos2=10;
		boolean resultado = true;;
		
		if(!this.ci.isEmpty()){
			while(pos!=-1&&pos2!=-1) {
				if(ci.getCi()[pos].isEstado()) {
					if(ci.getCi()[pos].getX() > 20)
						pos=-1; 
					else 
						pos2=-1;
				}
				else {
					if(pos!=pos2)
						pos+=5;
					else {
						pos-=9;
						pos2++;
					}
				}
			}
			
			if(pos==-1)
				resultado = true;
			else 
				if(pos2==-1)
					resultado = false;
			
		}
		return resultado;
	}
	
	public int disparoMovimientoNave(){
		n.getDisparo().setY(n.getDisparo().getY()-20);
		return n.getDisparo().getY();
	}
	
	public int disparoMovimientoEnemigo(){
		ci.getCi()[posMover].getDisparo().setY(ci.getCi()[posMover].getDisparo().getY()+20);
		return ci.getCi()[posMover].getDisparo().getY();
	}
	
	public boolean existeDisparoNave(){
		
		if(n.getDisparo()==null){
			return false;
		}
		return true; 
	}
	public boolean existeDisparoEnemigo(EnemigoVO e){
		
		boolean existe=false;
		for(i=0;i<15;i++){
			if(e.getX()==ci.getCi()[i].getX()&&e.getY()==ci.getCi()[i].getY()){
				if(ci.getCi()[i].getDisparo()!=null)
					existe=true;
			}
		}
		return existe;
	}
	
	public DisparoVO disparoNave(){
		DisparoVO disparo=new DisparoVO();
		disparo=n.disparar(disparo);
		
		return disparo;
	}
	public DisparoVO disparoEnemigo(){
		
		DisparoVO dis;
		Enemigo[] aux = new Enemigo[5];
		for(i=0;i<15;i++){
			if(puedoDisparar(i)){
				//System.out.println("entre"+j);
				aux[j]=null;
				aux[j] = new Enemigo(ci.getCi()[i].getX(),ci.getCi()[i].getY());
				j++;
			}
		}
		do{
		Random aleatorio = new Random(System.currentTimeMillis());
		// Producir nuevo int aleatorio entre 0 y 4
		pos = aleatorio.nextInt(5);
		}while(aux[pos]==null);
		dis = new DisparoVO();
		for(i=0;i<15;i++){
			if(aux[pos].getX()==ci.getCi()[i].getX()&&aux[pos].getY()==ci.getCi()[i].getY()){
				dis = ci.getCi()[i].disparar(dis);
				posMover=i;
			}
		}
		j=0;
		return dis;
	}
	
	private boolean puedoDisparar(int i){
		
		boolean puedo=false;
		
		if(i>=10){
			if(ci.getCi()[i].isEstado()){
				if(ci.getCi()[i].getDisparo()==null){//Fila de abajo
					return puedo=true;
				}
			}
		}
		else if(i>=5){
			if(ci.getCi()[i].isEstado()){
				if(ci.getCi()[i].getDisparo()==null){//Fila del medio
					if(!ci.getCi()[i+5].isEstado()){
						return puedo=true;
					}
				}
			}
		}
		else{
			if(i<5){
				if(ci.getCi()[i].isEstado()){
					if(ci.getCi()[i].getDisparo()==null){
						if(!ci.getCi()[i+5].isEstado()&&!ci.getCi()[i+10].isEstado()){
							return puedo=true;
						}
					}
				}
			}
		}
		return puedo;
	}
		/*if(i<5){//Fila 1
			if(ci.getCi()[i].getEstado()){
				if(!ci.getCi()[i+5].getEstado()&&!ci.getCi()[i+10].getEstado()&&ci.getCi()[i].getDisparo()==null){
					System.out.println("fila 1");
					band=0;
				}
			}
		
		}
		else if(i<10){
			if(ci.getCi()[i].getEstado()){//Fila 2
				if(!ci.getCi()[i+5].getEstado()&&ci.getCi()[i].getDisparo()==null){
					System.out.println("fila 2");
					band=0;
				}
			}
		}
		else if(i<15){
			if(ci.getCi()[i].getDisparo()==null){//Fila 3
				System.out.println("fila 3");
				band=0;
			}
			
		}

		if(band==0){
			System.out.println("devuelvo true");
			return true;
		}
		System.out.println("devuelvo false");
		return false;*/

	public boolean ultimaVida() {
		if(n.getVida()==0)
			return true;
		return false;
	}
	}