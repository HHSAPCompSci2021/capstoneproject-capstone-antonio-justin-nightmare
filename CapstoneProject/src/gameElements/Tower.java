package gameElements;

import java.awt.Color;
import java.util.ArrayList;

import core.DrawingSurface;
import processing.core.PConstants;
import screenElements.Store;

/**
 * This class represents a tower.
 * @author Antonio Cuan and Justin Yen
 *
 */
public class Tower extends GameElement{
	private int width;
	private Color color;
	private final int attackDamage;
	private final double attackSpeed,attackRange;
	// It stands for Time Between Attacks (in 60ths of a second)
	private final int TBA;
	private int attackCooldown;
	ArrayList<Projectile> projectiles;
	public Tower(int x, int y, int w, Store st) {
		super(x,y);
		width = w;
		color = st.getItemColor();
		attackDamage = 2;
		attackSpeed = 2;
		attackRange = 100;
		TBA = (int) (60/attackSpeed);
		attackCooldown = TBA;
		projectiles = new ArrayList<Projectile>();
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(color.getRed(), color.getGreen(), color.getBlue());
		surface.stroke(0);
		surface.rectMode(PConstants.CENTER);
		surface.rect(posX, posY, width, width);
		surface.noFill();
		surface.circle(posX, posY, (float)(2*attackRange));
		surface.fill(0);
		for (Projectile p:projectiles) {
			p.draw(surface);
		}
	}
	
	public void act(ArrayList<Enemy> enemies) {
		if (attackCooldown == TBA) {
			attack(enemies);
			attackCooldown--;
		} else if (attackCooldown > 0){
			attackCooldown--;
		} else {
			attackCooldown = TBA;
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (!projectiles.get(i).act()) {
				projectiles.remove(i);
				i--;
			}
		}
	}
	
	private void attack(ArrayList<Enemy> enemies) {
		if (enemies.size() > 0) {
			double shortestDist = attackRange;
			Enemy closestEnemy= null;
			for (Enemy e:enemies) {
				if (this.distanceTo(e) < shortestDist) {
					shortestDist = this.distanceTo(e);
					closestEnemy = e;
				}
			}
			if (closestEnemy != null) {
				projectiles.add(new Projectile(attackDamage,posX,posY,closestEnemy));
			}
		}
	}
}
