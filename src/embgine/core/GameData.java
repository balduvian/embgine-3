package embgine.core;

import embgine.core.scripts.Master;

public abstract class GameData {
	
	public Class<? extends Master> master;
	public float width;
	public float height;
	public String gameName;
	public String firstScene;
	public boolean debugMode;
	public boolean fullScreen;
	
	public GameData(Class<? extends Master> ma, float w, float h, String gn, String fs, boolean dm, boolean fu) {
		master = ma;
		width = w;
		height = h;
		gameName = gn;
		firstScene = fs;
		debugMode = dm;
		fullScreen = fu;
	}
	
}