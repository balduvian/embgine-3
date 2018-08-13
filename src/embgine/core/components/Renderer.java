package embgine.core.components;

import embgine.graphics.Transform;
import embgine.graphics.infos.Info;
import embgine.graphics.shapes.Shape;

abstract public class Renderer<I extends Info> extends Component{
	
	protected I info;
	protected Shape shape;
	
	public Renderer(I i, Shape s, boolean ts) {
		super(new Transform(), ts);
		info = i;
		shape = s;
	}
	
	public void setTransform(Transform t) {
		transform = t;
	}

	
	public void setTSync(boolean t) {
		tSync = t;
	}
	
	abstract void preRender();
	
	public void update() { }
	
	public void render() {
		preRender();
		info.setShape(shape);
		if(tSync) {
			Transform t = parent.getTransform();
			info.setTransform(new Transform(t.getX(), t.getY(), t.getWidth(), t.getHeight(), t.getRotation()));
		}else {
			info.setTransform(transform);
		}
		info.render();
	}

}
