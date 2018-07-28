package embgine.graphics.shaders;

import embgine.graphics.Packet;

public class Tex2DShader extends Shader {
	
	protected void subRoutine(Packet p) {
		
	}
	
	public Tex2DShader() {
		super("embgine/shaders/tex2d.vs", "embgine/shaders/tex2d.fs");
		
	}
	
	protected void getUniforms() {
		
	}
	
}
