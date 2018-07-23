package embgine.core;

import org.joml.Matrix4f;

import embgine.graphics.Camera;
import embgine.graphics.Packet;
import embgine.graphics.Shader;
import embgine.graphics.Shape;
import embgine.graphics.Transform;

abstract public class Renderer {
	
	protected GameObject parent;
	protected Packet packet;
	
	protected Shape shape;
	private Shader shader;
	
	protected boolean enabled;
	
	public void setParent(GameObject o) {
		parent = o;
	}
	
	public Renderer(Shape sp, Shader sd, int i) {
		shape = sp;
		shader = sd;
		packet = new Packet(i);
		enabled = true;
	}
	
	public void setEnabled(boolean b) {
		enabled = b;
	}
	
	public void render() {
		if(enabled) {
			
			shape.getTransform().set(parent.getTransform());
			
			preRender();
			
			if(parent.getGui()) {
				renderRoutine(shape.getGuiMatrix());
			}else {
				renderRoutine(shape.getMatrix());
			}
			
			postRender();
		}
	}
	
	abstract public Renderer clone();
	
	public Shape getShape() {
		return shape;
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
