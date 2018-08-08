package embgine.graphics.infos;

import embgine.graphics.shaders.Shader;

public class ColInfo extends Info{
	
	public ColInfo() {
		super(Shader.COL2DSHADER);
	}
	
	public void setColor(float r, float g, float b, float a) {
		setPacket(r, g, b, a);
	}
	
	protected void preRender() {
	}
	
	protected void postRender() {
	}

}