package embgine.core.components;

import embgine.graphics.Transform;
import embgine.graphics.infos.Info;
import embgine.graphics.shapes.Shape;

abstract public class Renderer<I extends Info> extends Component{
	
	private float[] packet;
	protected I info;
	protected Shape shape;
	protected Transform transform;
	protected boolean tSync;
	
	public Renderer(I i, Shape s, boolean t) {
		info = i;
		shape = s;
		tSync = t;
		packet = new float[info.getNumParams()];
		transform = new Transform();
	}
	
	public void setTransform(Transform t) {
		transform = t;
	}
	
	protected void setParams(float... p) {
		packet = p;
	}
	
	protected void setParam(float p, int i) {
		packet[i] = p;
	}
	
	public void setTSync(boolean t) {
		tSync = t;
	}
	
	abstract void preRender();
	
	public void update() { }
	
	public void render() {
		preRender();
		info.setPacket(packet);
		info.setShape(shape);
		if(tSync) {
			info.setTransform(parent.getTransform());
		}else {
			info.setTransform(transform);
		}
		info.render();
	}

}
