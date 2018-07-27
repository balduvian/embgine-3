package embgine.core;

public abstract class MapScript {
	
	protected Scene scene;
	
	public MapScript(Scene s) {
		scene = s;
	}
	
	abstract public void start();
	
}