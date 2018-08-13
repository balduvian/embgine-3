package embgine.core.components;

import embgine.graphics.infos.Info;
import embgine.graphics.infos.ColInfo;
import embgine.graphics.shapes.Shape;

public class ColRenderer extends Renderer<ColInfo>{

	private float r, g, b, a;
	
	public ColRenderer(Shape s, boolean synced) {
		super(Info.COLINFO, s, synced);
		r = 1;
		g = 1;
		b = 1;
		a = 1;
	}
	
	public ColRenderer clone() {
		return new ColRenderer(shape, tSync);
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
	}

}
