package embgine.core.components;

import embgine.graphics.Transform;
import embgine.graphics.infos.Info;
import embgine.graphics.shapes.Shape;

abstract public class Renderer<I extends Info> extends Component{
	
	private float[] packet;
	protected I info;
	protected Shape shape;
	
	public Renderer(I i, Shape s, boolean ts) {
		super(new Transform(), ts);
		info = i;
		shape = s;
		packet = new float[info.getNumParams()];
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
			Transform pt = parent.getTransform();
			float x = Math.round(pt.getX());
			float y = Math.round(pt.getY());
			info.setTransform(new Transform(x, y, pt.getWidth(), pt.getHeight()));
		}else {
			info.setTransform(transform);
		}
		info.render();
	}

}
