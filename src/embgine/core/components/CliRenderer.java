package embgine.core.components;

import org.joml.Matrix4f;

import embgine.graphics.Texture;
import embgine.graphics.infos.CliInfo;
import embgine.graphics.infos.Info;
import embgine.graphics.shapes.Shape;

public class CliRenderer extends Renderer<CliInfo>{

	private Texture texture;
	private int frameX;
	private int frameY;
	private float r, g, b, a;
	private float x, y, z, w; 
	private Matrix4f view;
	
	public CliRenderer(Shape s, boolean synced, Texture t) {
		super(Info.CLIINFO, s, synced);
		texture = t;
		frameX = 0;
		frameY = 0;
		r = 1;
		g = 1;
		b = 1;
		a = 1;
		x = 0;
		y = 0;
		z = 0;
		w = 0;
		view = new Matrix4f();
	}
	
	public CliRenderer clone() {
		return new CliRenderer(shape, tSync, texture);
	}
	
	public void setFrame(int x, int y) {
		frameX = x;
		frameY = y;
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}
	
	public void setPlane(float xx, float yy, float zz, float ww) {
		x = xx;
		y = yy;
		z = zz;
		w = ww;
	}
	
	public void setView(Matrix4f v) {
		view = v;
	}
	
	@Override
	void preRender() {
		info.setColor(r, g, b, a);
		info.setTexture(texture);
		info.giveFrame(frameX, frameY);
		info.setPlane(x, y, z, w);
		info.setView(view);
	}

}
