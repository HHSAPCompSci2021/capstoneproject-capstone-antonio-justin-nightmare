package gameElements;

import java.awt.Color;
import java.awt.Point;
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
	private int attackDamage;
	private double attackSpeed,attackRange;
	// It stands for Time Between Attacks (in 60ths of a second)
	private int TBA;
	private int attackCooldown;
	private boolean isSelected;
	private Color highlightColor;
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
		highlightColor = new Color(0, 255, 0);
	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		surface.fill(color.getRed(), color.getGreen(), color.getBlue());
		if (isSelected) {
			surface.stroke(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getBlue());
			surface.strokeWeight(2);
		} else {
			surface.stroke(0);
			surface.strokeWeight(1);
		}
		surface.rectMode(PConstants.CENTER);
		surface.rect(posX, posY, width, width);
		surface.noFill();
		surface.circle(posX, posY, (float)(2*attackRange));
		surface.fill(0);
		surface.pop();
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
	
	/**
	 * returns status of the specified position being in the tower
	 * @param p position
	 * @return true if point is in tower, false otherwise
	 */
	public boolean isPointInTower(Point p) {
		boolean inX = p.x > posX - width/2 && p.x < posX + width/2;
		boolean inY = p.y > posY - width/2 && p.y < posY + width/2;
		return inX && inY;
	}
	
	/**
	 * toggles selecting the tower
	 */
	public void toggleSelect() {
		isSelected = !isSelected;
	}
	
	/**
	 * returns status of tower being selected
	 * @return true if the tower is selected, false otherwise
	 */
	public boolean getIsSelected() {
		return isSelected;
	}
	
	public void upgradeTower() {
		attackDamage++;
	}
}
