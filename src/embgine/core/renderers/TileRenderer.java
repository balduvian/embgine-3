package embgine.core.renderers;

import org.joml.Vector4f;

import embgine.core.Renderer;
import embgine.graphics.Shader;
import embgine.graphics.Shape;
import embgine.graphics.Texture;

public class TileRenderer extends Renderer{
	
	private Texture texture;
	
	public Texture getTexture() {
		return texture;
	}
	
	public TileRenderer(Shape sp, Texture t) {
		super(sp, Shader.TIL2DSHADER, 0);
		texture = t;
	}
	
	protected void preRender() {
		
		Vector4f frame = texture.getFrame(parent.getFrame());
		packet.giveFrame(frame.x, frame.y, frame.z, frame.w);
		
		texture.bind();
	}
	
	protected void postRender() {
		texture.unbind();
	}

	public Renderer clone() {
		return new TileRenderer(shape, texture);
	}

	public TileRenderer (Object[] o) {
		super((Shape)o[1], Shader.TIL2DSHADER, 0);
		texture = (Texture)o[2];
	}

}
