package embgine.graphics.shaders;

public class Tex2DShader extends Shader {
	
	protected void sendUniforms(Object... params) {
		
	}
	
	public Tex2DShader() {
		super("embgine/shaders/tex2d.vs", "embgine/shaders/tex2d.fs");
	}
	
	protected void getUniforms() {
		
	}
	
}