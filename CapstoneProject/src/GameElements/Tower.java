package GameElements;

public class Tower extends GameElement{
	int price,attackDamage;
	double attackSpeed;
	
	public Tower(int x, int y) {
		super(x,y);
		price = 100;
		attackDamage = 2;
		attackSpeed = 1;
	}
	
	public void draw() {
		
	}
	
	public void act() {
		attack();
	}
	
	private void attack() {
		
	}
}
