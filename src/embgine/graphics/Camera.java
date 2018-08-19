package embgine.graphics;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
	
	private Transform transform;
	
	private Matrix4f projection, projectionView;
	
	public Camera(float w, float h) {
		transform = new Transform();
		setDims(w, h);
		projectionView = new Matrix4f();
	}
	
	public void setDims(float w, float h) {
		transform.setSize(w, h);
		projection = new Matrix4f().setOrtho(0, w, h, 0, 1, -1);
	}
	
	public void update() {
		projection.scale(transform.wScale, transform.hScale, 1, projectionView);
		projection.translate(-transform.abcissa, -transform.ordinate, 0, projectionView);
		projection.rotateZ(-transform.rotation, projectionView);
	}
	
	/**
	 * gets the model matrix from a certain transform
	 * 
	 * @param transform - the transform of the model
	 * 
	 * @return the model matrix in world coordiantes
	 */
	public Matrix4f getModelMatrix(Transform transform) {
		return new Matrix4f().translate(transform.abcissa, transform.ordinate, 1).rotateZ(transform.rotation).scale(transform.getWidth(), transform.getHeight(), 1);
	}
	
	/**
	 * gets the mvp matrix from a certain model matrix
	 * 
	 * @param model - the matrix of the model
	 * 
	 * @return the mvp matrix in ndc
	 */
	public Matrix4f getModelViewProjectionMatrix(Matrix4f model) {
		return new Matrix4f(projectionView).mul(model);	
	}
	
	/**
	 * gets the view matrix from a certain model matrix for gui models.
	 * Basically like the camera is at position 0,0
	 * 
	 * @param model - the matrix of the model
	 * 
	 * @return the model projection matrix in gui ndc
	 */
	public Matrix4f getModelProjectionMatrix(Matrix4f model) {
		return new Matrix4f(projection).mul(model);
	}
	
	/**
	 * gets the model matrix width a ceratin width and height centered on the screen
	 * 
	 * @param w - the percent width that the model takes up of the screen
	 * @param h - the percent height that the model takes up of the screen
	 * 
	 * @return the model matrix in ndc coordinates
	 */
	public Matrix4f ndcFullMatrix(float w, float h) {
		return new Matrix4f().translate(-w, -h, 0).scale(2 * w, 2 * h, 1);
	}
	
	/**
	 * gets the camera's transform
	 * 
	 * @return the camera's transform
	 */
	public Transform getTransform() {
		return transform;
	}
}
