package embgine.graphics.infos;

import org.joml.Matrix4f;

import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

abstract public class Info {
	
	public static ColInfo COLINFO;
	public static FonInfo FONINFO;
	public static TexInfo TEXINFO;
	public static TilInfo TILINFO;
	public static SelInfo SELINFO;
	public static CliInfo CLIINFO;
	
	protected float[] packet;
	
	protected Shape shape;
	private Shader shader;

	public Info(Shader sd) {
		shader = sd;
		packet = new float[sd.getNumParams()];
	}
	
	public static void init() {
		COLINFO = new ColInfo();
		FONINFO = new FonInfo();
		TEXINFO = new TexInfo();
		TILINFO = new TilInfo();
		SELINFO = new SelInfo();
		CLIINFO = new CliInfo();
	}
	
	public void setPacket(float... p) {
		packet = p;
	}
	
	public void setPacket(int s, float p) {
		packet[s] = p;
	}
	
	public void setShape(Shape s) {
		shape = s;
	}

	public void setTransform(Transform t) {
		shape.getTransform().set(t);
	}
	
	public int getNumParams() {
		return packet.length;
	}
	
	public void render() {
		preRender();
		
		renderRoutine(shape.getMatrix());
		
		postRender();
	}
	
	protected abstract void preRender();
	
	protected abstract void postRender();
	
	protected void renderRoutine(Matrix4f trans) {
		shader.enable(packet);
		shader.setMvp(trans);
		shape.getVAO().render();
		shader.disable();
	}
}
