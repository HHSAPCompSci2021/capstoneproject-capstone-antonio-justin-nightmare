package screens;
import core.DrawingSurface;

public class GameScreen extends Screen{
	private DrawingSurface surface;
	public GameScreen(DrawingSurface surface) {
		super(1280, 720);
		this.surface = surface;
	}

	public void draw() {
		surface.background(0,100,100);
		surface.fill(100,0,100);
		surface.rect(20, 20, 200, 200);
	}
}
