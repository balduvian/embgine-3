package embgine.core.renderers;

import embgine.core.Renderer;
import embgine.graphics.Shader;
import embgine.graphics.Shape;

public class ColorRenderer extends Renderer{
	
	public Renderer clone() {
		return new ColorRenderer(shape);
	}
	
	public ColorRenderer(Shape sp) {
		super(sp, Shader.COL2DSHADER, 0);
	}
	
	public void setColor(float r, float g, float b, float a) {
		packet.giveColor(r, g, b, a);
	}
	
	protected void preRender() {
		
	}
	
	protected void postRender() {
	}

	public ColorRenderer(Object[] o) {
		super( (Shape)o[1], Shader.COL2DSHADER, 0);
	}

}
