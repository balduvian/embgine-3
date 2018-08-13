package embgine.graphics;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
	
	private Transform transform;
	
	private Matrix4f projection, projView;
	
	public Camera(float w, float h) {
		transform = new Transform();
		setDims(w, h);
		projView = new Matrix4f();
	}
	
	public void setDims(float w, float h) {
		transform.setSize(w, h);
		projection = new Matrix4f().setOrtho(0, w, h, 0, 1, -1);
	}
	
	public void update() {
		projection.translate(transform.getPosition().negate(new Vector3f()), projView);
		projView.rotateZ(-transform.getRotation());
	}
	
	public Matrix4f getParallaxView(float p) {
		projection.translate(transform.getPosition().mul(p).negate(new Vector3f()), projView);
		projView.rotateZ(-transform.getRotation());
		return new Matrix4f(projView);
	}
	
	public Matrix4f getProjview() {
		return new Matrix4f(projView);
	}
	
	public Matrix4f getProjection() {
		return new Matrix4f(projection);
	}
	
	public Transform getTransform() {
		return transform;
	}
}
