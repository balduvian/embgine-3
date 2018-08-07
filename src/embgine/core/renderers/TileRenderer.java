package embgine.core.renderers;

import org.joml.Vector4f;

import embgine.graphics.Packet;
import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class TileRenderer extends Renderer{
	
	private Texture texture;
	
	public Texture getTexture() {
		return texture;
	}
	
	public TileRenderer(Shape sp, Texture t) {
		super(sp, Shader.TIL2DSHADER, new Packet(1, 1, 1, 1));
		texture = t;
	}
	
	public void setColor(float r, float g, float b, float a) {
		packet.setParams(r, g, b, a);
	}
	
	public void giveFrame(int f) {
		Vector4f frame = texture.getFrame(f);
		packet.giveFrame(frame.x, frame.y, frame.z, frame.w);
	}
	
	protected void preRender() {
		texture.bind();
	}
	
	protected void postRender() {
		texture.unbind();
	}

	public Renderer clone() {
		return new TileRenderer(shape, texture);
	}

	public TileRenderer (Object[] o) {
		super((Shape)o[1], Shader.TIL2DSHADER, new Packet(1, 1, 1, 1));
		texture = (Texture)o[2];
	}

}
