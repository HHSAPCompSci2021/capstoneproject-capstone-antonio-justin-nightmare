package screens;
import core.DrawingSurface;

public class GameScreen extends Screen{
	private DrawingSurface surface;
	public GameScreen(DrawingSurface surface) {
		super(1280, 720);
		this.surface = surface;
	}

}
