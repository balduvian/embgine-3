package embgine.graphics.infos;

import org.joml.Vector4f;

import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;

public class TilInfo extends Info{
	
	private Texture texture;
	
	public TilInfo() {
		super(Shader.TIL2DSHADER);
	}
	
	public void setTexture(Texture t) {
		texture = t;
	}
	
	public void setColor(float r, float g, float b, float a) {
		setPacket(4, r);
		setPacket(5, g);
		setPacket(6, b);
		setPacket(7, a);
	}
	
	public void giveFrame(int x, int y) {
		Vector4f frame = texture.getFrame(x, y);
		setPacket(0, frame.x);
		setPacket(1, frame.y);
		setPacket(2, frame.z);
		setPacket(3, frame.w);
	}
	
	@Override
	protected void preRender() {
		texture.bind();
	}
	
	@Override
	protected void postRender() {
		texture.unbind();
	}

}
