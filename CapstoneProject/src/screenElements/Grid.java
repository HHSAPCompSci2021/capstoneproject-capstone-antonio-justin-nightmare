package screenElements;

import java.util.ArrayList;

import core.DrawingSurface;
import gameElements.*;

public class Grid extends ScreenElement{
	private int cols;
	private int rows;
	private static final int CELL_WIDTH = 10;
	private ArrayList<Enemy> enemies;
	private int[] upperGoal,lowerGoal;
	public Grid(int x, int y, int width, int height) {
		super(x,y,width,height);
		cols = width/CELL_WIDTH;
		rows = height/CELL_WIDTH;
		enemies = new ArrayList<Enemy>();
		upperGoal = new int[] {x+width,y+height/2-40};
		lowerGoal = new int[] {x+width,y+height/2+40};

	}
	
	public void draw(DrawingSurface surface) {
		surface.push();
		// Sets the background
		surface.noFill();
		surface.rect(x, y, width, height);
		
		// Draws all the enemies
		for (Enemy e:enemies) {
			e.draw(surface,this);
		}
		surface.pop();
	}

	public void next() {
		for (Enemy e:enemies) {
			e.act(this);
		}
	}
	
	public void addToGrid(Enemy e) {
		enemies.add(e);
	}
	
	public void removeFromGrid(Enemy e) {
		enemies.remove(e);
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public int getCellWidth() {
		return CELL_WIDTH;
	}
	
	public int[] getUpperGoal() {
		return upperGoal;
	}
	
	public int[] getLowerGoal() {
		return lowerGoal;
	}

}
