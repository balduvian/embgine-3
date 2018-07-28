package embgine.core.renderers;

import embgine.graphics.Packet;
import embgine.graphics.Shape;
import embgine.graphics.shaders.Shader;

public class ColorRenderer extends Renderer{
	
	public Renderer clone() {
		return new ColorRenderer(shape);
	}
	
	public ColorRenderer(Shape sp) {
		super(sp, Shader.COL2DSHADER, new Packet(1, 1, 1, 1));
	}
	
	public void setColor(float r, float g, float b, float a) {
		packet.setParams(r, g, b, a);
	}
	
	protected void preRender() {
	}
	
	protected void postRender() {
	}

	public ColorRenderer(Object[] o) {
		super( (Shape)o[1], Shader.COL2DSHADER, new Packet(1, 1, 1, 1));
	}

}