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
	private int level;
	private static final int MAX_LEVEL = 4;
	ArrayList<Projectile> projectiles;
	
	/**
	 * Creates a tower with given coordinates and width
	 * @param x The x-coordinate of the center of the tower
	 * @param y The y-coordinate of the center of the tower
	 * @param w The width of the tower
	 * @param st
	 */
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
		level = 1;
	}
	
	@Override
	public void draw(DrawingSurface surface) {
		surface.push();
		if (isSelected) {
			surface.stroke(highlightColor.getRed(), highlightColor.getGreen(), highlightColor.getBlue());
			surface.strokeWeight(2);
			surface.noFill();
			surface.circle(posX, posY, (float)(2*attackRange));
		} else {
			surface.stroke(0);
			surface.strokeWeight(1);
		}
		surface.fill(color.getRed(), color.getGreen(), color.getBlue());
		surface.rectMode(PConstants.CENTER);
		surface.rect(posX, posY, width, width);
		surface.pop();
		for (Projectile p:projectiles) {
			p.draw(surface);
		}
	}
	
	/**
	 * Moves the state of the tower forward 1 step (Also all projectiles being fired by it)
	 * @param enemies The ArrayList of all enemies in the grid
	 */
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
	
	/**
	 * Creates a projectile targeting the nearest enemy
	 * @param enemies The ArrayList of all enemies in the grid
	 */
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
	
	/**
	 * upgrades the tower
	 */
	public void upgradeTower() {
		attackDamage++;
		level++;
		switch (level) {
		case 2:
			color = new Color(200, 0, 255);
			break;
		case 3:
			color = new Color(255, 220, 0);
			break;
		case 4:
			color = new Color(255, 0, 0);
			break;
		}
	}
	
	/**
	 * returns tower level
	 * @return level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * returns max tower level
	 * @return max level
	 */
	public int getMaxLevel() {
		return MAX_LEVEL;
	}
}
