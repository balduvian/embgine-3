package embgine.core.loaders;

import embgine.core.EID;
import embgine.graphics.Sound;

public abstract class SoundLoader implements EID{
	
	private String path;
	
	public SoundLoader(String p) {
		path = p;
	}
	
	public Sound create() {
		return new Sound(path);
	}
}
