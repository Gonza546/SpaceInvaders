package Negocio;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Muro.java
//  @ Date : 05/11/2018
//  @ Author : 
//
//

public class Muro {
	
	private int energia;
	private int y;
	private int x;
	private boolean estado;
	
	public Muro(int energia, int x, int y) {
		this.energia = energia;
		this.y = y;
		this.x = x;
		this.estado=true;
	}
	
	public void debilitar(int disparo) {
		this.energia=(this.energia-disparo);
	}
	
	public int getEnergia() {
		return energia;
	}

	public void setEnergia() {
		this.energia = 100;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
}
