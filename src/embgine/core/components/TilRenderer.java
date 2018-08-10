package embgine.core.components;

import embgine.graphics.Texture;
import embgine.graphics.infos.Info;
import embgine.graphics.infos.TilInfo;
import embgine.graphics.shapes.Shape;

public class TilRenderer extends Renderer<TilInfo>{

	private Texture texture;
	private int frame;
	private float r, g, b, a;
	
	public TilRenderer(Shape s, boolean synced, Texture t) {
		super(Info.TILINFO, s, synced);
		texture = t;
		frame = 0;
		r = 1;
		g = 1;
		b = 1;
		a = 1;
	}
	
	public TilRenderer clone() {
		return new TilRenderer(shape, tSync, texture);
	}
	
	public void setFrame(int f) {
		frame = f;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}
	
	@Override
	void preRender() {
		info.setColor(r, g, b, a);
		info.setTexture(texture);
		info.giveFrame(frame);
	}

}
