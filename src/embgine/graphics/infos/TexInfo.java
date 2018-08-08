package embgine.graphics.infos;

import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;

public class TexInfo extends Info{
	
	private Texture texture;
	
	public TexInfo() {
		super(Shader.TEX2DSHADER);
	}
	
	public void setTexture(Texture t) {
		texture = t;
	}
	
	protected void preRender() {
		texture.bind();
	}
	
	protected void postRender() {
		texture.unbind();
	}

}
