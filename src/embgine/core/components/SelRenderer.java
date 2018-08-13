package embgine.core.components;

import embgine.core.Utils;
import embgine.graphics.Texture;
import embgine.graphics.infos.Info;
import embgine.graphics.infos.SelInfo;
import embgine.graphics.infos.TilInfo;
import embgine.graphics.shapes.Shape;

public class SelRenderer extends Renderer<SelInfo>{

	private Texture texture;
	private float enabled;
	
	public SelRenderer(Shape s, boolean synced, Texture t) {
		super(Info.SELINFO, s, synced);
		texture = t;
		enabled = 0;
	}
	
	public SelRenderer clone() {
		return new SelRenderer(shape, tSync, texture);
	}
	
	public void setEnabled(float e) {
		enabled = e;
	}
	
	public void setTexture(Texture t) {
		texture = t;
	}
	
	@Override
	void preRender() {

		info.setTexture(texture);
		info.setEnabled(enabled);
		info.setRand(Utils.random());
		
		
	}

}
