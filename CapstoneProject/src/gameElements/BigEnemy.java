package gameElements;

import core.DrawingSurface;
import screenElements.Grid;
import screens.GameScreen;

import java.awt.Point;

public class BigEnemy extends Enemy{
	private int health;
	private int diameter;
	private static final int goldValue = 20;
	private boolean hasReachedGoal;
	private Grid grid;
	private GameScreen gScreen;
	private int prevMoveX, prevMoveY;
	private boolean hasFoundNextPos;
	public BigEnemy(int x, int y, Grid g, GameScreen sc) {
		super(x, y, g, sc);
		health = 40;
		diameter = 36;
		hasReachedGoal = false;
		grid = g;
		gScreen = sc;
		hasFoundNextPos = false;
	}
	
	public void draw(DrawingSurface surface) {
		surface.fill(200,0,0);
		surface.stroke(100,0,0);
		surface.circle(posX, posY, diameter);
		surface.fill(0,0,0);
		surface.textSize(10);
		surface.text(health, posX, posY);
	}
}
