package embgine.graphics.shapes;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import embgine.graphics.Camera;
import embgine.graphics.Transform;
import embgine.graphics.VAO;

abstract public class Shape {
	
	public static final int TRIANGLES = GL11.GL_TRIANGLES;
	public static final int LINES = GL11.GL_LINES;
	public static final int POINTS = GL11.GL_POINTS;
	
	public static RectShape RECT;
	public static ArrowShape ARROW;
	
	private static Camera camera;
	
	private VAO vao;
	
	private Transform transform;
	
	public Shape(float vertices[], int[] indices, float[] texCoords, int drawMode) {
		transform = new Transform();
		vao = new VAO(vertices, indices, drawMode);
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
		//return camera.getProjview().mul( new Matrix4f().translate(position.add(width/2,height/2,0,new Vector3f())).rotateZ(rotation).scale(width,height,1).translate(-1f,-1f,0) );
		return camera.getProjview().mul( new Matrix4f().translate(position).rotateZ(rotation).scale(width,height,1) , new Matrix4f());
		
	}
	
	public VAO getVAO() {
		return vao;
	}
	
	public Transform getTransform() {
		return transform;
	}
}
