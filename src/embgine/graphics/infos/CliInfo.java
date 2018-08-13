package embgine.graphics.infos;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import embgine.graphics.Texture;
import embgine.graphics.shaders.Shader;

public class CliInfo extends Info{
	
	private Texture texture;
	
	public CliInfo() {
		super(Shader.CLI2DSHADER);
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
	
	public void setPlane(float x, float y, float z, float w) {
		setPacket(8, x);
		setPacket(9, y);
		setPacket(10, z);
		setPacket(11, w);
	}
	
	public void setView(Matrix4f v) {
		float[] array = v.get(new float[16]);
		for(int i = 0; i < 16; ++i) {
			setPacket(12 + i, array[i]);
		}
	}
	
	@Override
	protected void preRender() {
		texture.bind();
		//glEnable(GL30.GL_CLIP_DISTANCE0);
		
	}
	
	@Override
	protected void postRender() {
		texture.unbind();
		
		//glDisable(GL30.GL_CLIP_DISTANCE0);
	}

}
