package embgine.core.renderers;

import org.joml.Matrix4f;

import embgine.graphics.Packet;
import embgine.graphics.Shape;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;

abstract public class Renderer {
	
	protected Packet packet;
	
	protected Shape shape;
	private Shader shader;
	
	protected boolean gui;
	
	public Renderer(Shape sp, Shader sd, Packet p) {
		shape = sp;
		shader = sd;
		packet = p;
	}
	
	//this one can also be called
	public void setGui(boolean g) {
		gui = g;
	}
	
	//this should be the first method called for render
	public void setTransform(Transform t) {
		shape.getTransform().set(t);
	}
	
	//this should be the second method called
	public void render() {
		preRender();
		
		if(gui) {
			renderRoutine(shape.getGuiMatrix());
		}else {
			renderRoutine(shape.getMatrix());
		}
		
		postRender();
	}
	
	abstract public Renderer clone();
	
	protected abstract void preRender();
	
	protected abstract void postRender();
	
	protected void renderRoutine(Matrix4f trans) {
		shader.enable(packet);
		shader.setMvp(trans);
		shape.getVAO().render();
		shader.disable();
	}
}
