package GameElements;

public class Weapon {
	int attackDamage;
	double attackSpeed;
	
	public Weapon() {
		attackDamage = 3;
		attackSpeed = 1;
	}
	
	public int getAttackDamage() {
		return attackDamage;
	}
	
	public double getAttackSpeed() {
		return attackSpeed;
	}
}
