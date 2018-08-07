package embgine.graphics.shapes;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import embgine.graphics.Camera;
import embgine.graphics.Transform;
import embgine.graphics.VAO;

abstract public class Shape {
	
	public static RectShape RECT;
	public static ArrowShape ARROW;
	
	private static Camera camera;
	
	private VAO vao;
	
	private Transform transform;
	
	public Shape(float vertices[], int[] indices, float[] texCoords) {
		transform = new Transform();
		vao = new VAO(vertices, indices);
		vao.addAttrib(texCoords, 2);
	}
	
	public static void init(Camera c) {
		camera = c;
		
		RECT = new RectShape();
		ARROW = new ArrowShape();
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
