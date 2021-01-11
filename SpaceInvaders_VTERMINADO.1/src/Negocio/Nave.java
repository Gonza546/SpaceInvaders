package Negocio;
import ValueObjects.DisparoVO;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Nave.java
//  @ Date : 05/11/2018
//  @ Author : 
//
//




public class Nave {

	private int vida;
	private int x;
	private int y;
	private Disparo disparo;
	
	public Nave(int vida, int x, int y) {
		this.vida = vida;
		this.x = x;
		this.y = y;
	}
	
	public int moverDerecha(int ancho) {
		
		if(this.x+1<ancho-70)
			this.x+=10;
		return this.x;
	}
	
	public int moverIzq(int ancho) {
		
		if(this.x-1>ancho-ancho+19)
			this.x-=10;
		return this.x;
	}
	
	public DisparoVO disparar(DisparoVO dis) {
		
		disparo = new Disparo(this.x+25,this.y-40);
		dis.setX(disparo.getX());
		dis.setY(disparo.getY());
		return dis;
	}
	
	
	public int getVida() {
		return vida;
	}
	public void setVida(int x){
		this.vida=x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public Disparo getDisparo(){
		return disparo;
	}
	
	public void setDisparo(){
		disparo=null;
	}
	public boolean soyLaNave(DisparoVO d){
		if(d.getY()>=this.y&&d.getY()<=this.y+64){
			if(d.getX()>=this.x&&d.getX()<=this.x+64){
				return true;
			}
		}
		return false;
	}
}
