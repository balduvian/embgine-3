package embgine.core;

public abstract class GameData {
	
	public float width;
	public float height;
	public String gameName;
	public String firstScene;
	public boolean debugMode;
	public boolean fullScreen;
	
	public GameData(float w, float h, String gn, String fs, boolean dm, boolean fu) {
		width = w;
		height = h;
		gameName = gn;
		firstScene = fs;
		debugMode = dm;
		fullScreen = fu;
	}
	
}