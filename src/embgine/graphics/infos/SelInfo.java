package embgine.graphics.infos;

import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;

public class SelInfo extends Info{
	
	private Texture texture;
	
	public SelInfo() {
		super(Shader.SEL2DSHADER);
	}
	
	public void setRand(float r) {
		setPacket(0, r);
	}
	
	public void setEnabled(float e) {
		setPacket(1, e);
	}
	
	@Override
	protected void preRender() {
		texture.bind();
	}
	
	@Override
	protected void postRender() {
		texture.unbind();
	}

	public void setTexture(Texture t) {
		texture = t;
	}

}
