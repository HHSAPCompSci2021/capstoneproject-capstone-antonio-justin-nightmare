package gameElements;

public class Weapon extends GameElement{
	int attackDamage;
	double attackSpeed;
	
	public Weapon(int x, int y) {
		super(x,y);
		attackDamage = 3;
		attackSpeed = 1;
	}
	
	public void draw() {
		
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}
}
