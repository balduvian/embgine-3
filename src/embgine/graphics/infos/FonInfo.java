package embgine.graphics.infos;

import java.util.ArrayList;

import org.joml.Vector4f;

import embgine.core.Font;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;

public class FonInfo extends Info {
	
	private char[][] characters;
	private boolean centeredW;
	private boolean centeredH;
	private boolean centeredT;
	private float width, height;
	private Font font;
	
	public FonInfo() {
		super(Shader.TIL2DSHADER);
	}
	
	public void setColor(float r, float g, float b, float a) {
		setPacket(4, r);
		setPacket(5, g);
		setPacket(6, b);
		setPacket(7, a);
	}

	public void setText(char[][] c) {
		characters = c;
	}
	
	public void setParams(Font f, float s, boolean cw, boolean ch, boolean ct) {
		font = f;
		width = s;
		height = s;
		centeredW = cw;
		centeredH = ch;
		centeredT = ct;
	}
	
	public char[][] getText() {
		return characters;
	}
	
	public void setCentered(boolean w, boolean h) {
		centeredW = w;
		centeredH = h;
	}
	
	public void setDims(float w, float h) {
		width = w;
		height = h;
	}
	
	public void render() {
		if(characters != null) {
			
			float gutterWidth = font.getGutter() * width;
			
			float advanceWidth = font.getWidth() * width;
			
			float startX;
			float startY;
			
			Transform tp = new Transform(shape.getTransform());
			
			shape.getTransform().setSize(width, height);
			
			startX = tp.getX();
			startY = tp.getY();
			
			if(!centeredW) {
				if(!centeredH) {
					startX = tp.getX()-tp.getWidth()/2+width/2;
					startY = tp.getY()-tp.getHeight()/2+height/2;
				}else {
					startX = tp.getX()-tp.getWidth()/2+width/2;
					startY = tp.getY();
				}
			}else {
				if(!centeredH) {
					startX = tp.getX();
					startY = tp.getY()-tp.getHeight()/2+height/2;
				}else {
					startX = tp.getX();
					startY = tp.getY();
				}
			}
			
			preRender();
			
			int length = characters.length;
			
			for(int i = 0; i < length; ++i) {
				
				int lineLength = characters[i].length;
				
				float x;
				
				if(centeredT) {
					x = -( (lineLength * advanceWidth / 2) - (width / 2) - (gutterWidth / 2) );
				}else {
					x = 0;
				}
				
				for(int j = 0; j < lineLength; ++j) {
					
					char c = characters[i][j];
					
					Vector4f frame = font.getTexture().getFrame(c%16, c/16);

					setPacket(0, frame.x);
					setPacket(1, frame.y);
					setPacket(2, frame.z);
					setPacket(3, frame.w);
					
					shape.getTransform().setPosition(startX+x, startY+height*i);
					
					renderRoutine(shape.getMatrix());
					
					x += advanceWidth;
					
				}
			}
			
			postRender();
		}
	}
	
	protected void preRender() {
		font.getTexture().bind();
	}

	protected void postRender() {
		font.getTexture().unbind();
	}
	
}
