package embgine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Shape {
	
	private Camera camera;
	
	private VAO vao;
	
	private Transform transform;
	
	public Shape(Camera c, float vertices[], int[] indices, float[] texCoords) {
		camera = c;
		transform = new Transform();
		vao = new VAO(vertices, indices);
		vao.addAttrib(texCoords, 2);
	}
	
	public Matrix4f getMatrix() {
		Vector3f position = transform.getPosition();
		float rotation = transform.getRotation();
		float width = transform.getWidth();
		float height = transform.getHeight();
		
		return camera.getProjview().mul( new Matrix4f().translate(position).rotateZ(rotation).scale(width,height,1) , new Matrix4f());
		
	}
	
	public Matrix4f getGuiMatrix() {
		Vector3f position = transform.getPosition();
		float rotation = transform.getRotation();
		float width = transform.getWidth();
		float height = transform.getHeight();
		
		return camera.getProjection().translate(position).rotateZ(rotation).scale(width,height,1);
	}
	
	public VAO getVAO() {
		return vao;
	}
	
	public Transform getTransform() {
		return transform;
	}
}
