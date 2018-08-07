package embgine.core.renderers;

import embgine.graphics.Packet;
import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class TextureRenderer extends Renderer{
	
	private Texture texture;
	
	public Texture getTexture() {
		return texture;
	}
	
	public TextureRenderer(Shape sp, Texture t) {
		super(sp, Shader.TEX2DSHADER, new Packet());
		texture = t;
	}
	
	protected void preRender() {
		texture.bind();
	}
	
	protected void postRender() {
		texture.unbind();
	}

	public Renderer clone() {
		return new TextureRenderer(shape, texture);
	}

	public TextureRenderer (Object[] o) {
		super((Shape)o[1], Shader.TEX2DSHADER, new Packet());
		texture = (Texture)o[2];
	}

}
