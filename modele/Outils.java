package application.modele;

public abstract class Outils {
	
	private static int idN = 0;
	private String id = "O" + idN;
	private int x, y;
	
	public void incremId() {
		this.idN++;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	

}
