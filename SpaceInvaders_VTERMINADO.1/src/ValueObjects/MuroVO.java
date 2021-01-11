package ValueObjects;

public class MuroVO {
	
	private int x,y,energia;

	public MuroVO(){
	}
	
	public MuroVO(int x, int y) {
		this.x = x;
		this.y = y;
		this.energia=0;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getEnergia() {
		return energia;
	}

	public void setEnergia(int energia) {
		this.energia = energia;
	}
	
	
}
