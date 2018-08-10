package embgine.core;

import embgine.core.scripts.Master;

public abstract class GameData {
	
	public Class<? extends Master> master;
	public int tileSize;
	public float width;
	public float height;
	public String gameName;
	public boolean debugMode;
	public boolean fullScreen;
	public Class<? extends Scene>[] sceneList;
	
	public GameData(Class<? extends Master> ma, int ts, float w, float h, String gn, boolean dm, boolean fu, Class<? extends Scene>[] sl) {
		master = ma;
		tileSize = ts;
		width = w;
		height = h;
		gameName = gn;
		debugMode = dm;
		fullScreen = fu;
		sceneList = sl;
	}
	
}