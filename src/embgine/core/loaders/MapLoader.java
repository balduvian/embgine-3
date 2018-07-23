package embgine.core.loaders;

import embgine.core.Block;
import embgine.core.EID;
import embgine.core.Map;
import embgine.core.MapReference;
import embgine.core.Scene;
import embgine.core.Script;

public abstract class MapLoader implements EID{
	
	private String mapPath;
	private MapReference mapRef;
	private boolean edgeMode;
	private Class<? extends Block> edgeTile;
	private Class<? extends Script> script;
	
	public MapLoader() {
		
	}
	
	public MapLoader(String mp, MapReference mr, boolean em, Class<? extends Block> et, Class<? extends Script> sc) {
		mapPath = mp;
		mapRef = mr;
		edgeMode = em;
		edgeTile = et;
		script = sc;
	}
	
	public Map create() {
		return new Map(mapPath, mapRef, edgeMode, edgeTile, script);
	}
	
}
