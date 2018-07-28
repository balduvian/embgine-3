package embgine.core.renderers;

import java.util.ArrayList;

import org.joml.Vector4f;

import embgine.core.Font;
import embgine.graphics.Packet;
import embgine.graphics.Shape;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;

public class FontRenderer extends Renderer {
	
	private char[][] characters;
	private boolean centeredW;
	private boolean centeredH;
	private boolean centeredT;
	private float width, height;
	private Font font;
	
	public Renderer clone() {
		return new FontRenderer(shape, font, width, centeredW, centeredH, centeredT);
	}
	
	public FontRenderer(Shape sp, Font f, float s, boolean cw, boolean ch, boolean ct) {
		super(sp, Shader.TIL2DSHADER, new Packet(1, 1, 1, 1));
		font = f;
		width = s;
		height = s;
		centeredW = cw;
		centeredH = ch;
		centeredT = ct;
	}
	
	public FontRenderer(Object[] o) {
		super((Shape)o[1], Shader.TIL2DSHADER, new Packet(1, 1, 1, 1));
		font = (Font)o[2];
		width = (float)o[3];
		height = (float)o[3];
		centeredW = (boolean)o[4];
		centeredH = (boolean)o[5];
		centeredT = (boolean)o[6];
	}
	
	public void setText(String s) {
		char[] initial = s.toCharArray();
		ArrayList<ArrayList<Character>> temp = new ArrayList<ArrayList<Character>>();
		int length = initial.length;
		
		int line = 0;
		temp.add(new ArrayList<Character>());
		for(int i = 0; i < length; ++i) {
			char c = initial[i];
			if(c == '\n') {
				temp.add(new ArrayList<Character>());
				++line;
			}else {
				temp.get(line).add(c);
			}
		}
		++line;
		
		characters = new char[line][];
		for(int i = 0; i < line; ++i) {
			ArrayList<Character> ac = temp.get(0);
			int size = ac.size();
			characters[i] = new char[size];
			for(int j = 0; j < size; ++j) {
				characters[i][j] = ac.get(j);
			}
		}
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
			float startX;
			float startY;
			
			Transform tp = new Transform(shape.getTransform());
			
			shape.getTransform().setSize(width, height);
			
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
					x = -((lineLength*width)/2f-width/2);
				}else {
					x = 0;
				}
				
				for(int j = 0; j < lineLength; ++j) {
					
					char c = characters[i][j];
					
					Vector4f frame = font.getTexture().getFrame(c%16, c/16);

					packet.giveFrame(frame.x, frame.y, frame.z, frame.w);
					
					shape.getTransform().setPosition(startX+x, startY+height*i);
					
					if(gui) {
						renderRoutine(shape.getMatrix());
					}else {
						renderRoutine(shape.getGuiMatrix());
					}
					
					x += width*font.advance(c);
					
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
