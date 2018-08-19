package embgine.core;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.shaders.Shader;
import embgine.graphics.shapes.Shape;

public class Font {
	
	//static rendering stuff
	private static Shape rect;
	private static Shader shader;
	private static Camera camera;
	
	/** the tilesheet that contains the characters */
	private Texture texture;
	
	/** the width in pixels of a tile in the tileSheet */
	private int pixelWidth;
	
	/** the percentage width of the tile that the character takes up */
	private float characterWidth;
	
	/** the distance between each character */
	private float gapWidth;
	
	/**
	 * constructs a new font instance
	 * 
	 * @param t - the tilesheet of the font
	 * @param pw - how wide a tile of the sheet is in pixels 
	 * @param cw - how wide a character in the tile is in pixels
	 * 
	 * @return the font instance
	 */
	public Font(Texture t, int pw, int cw) {
		texture = t;
		pixelWidth = pw;
		characterWidth = ((float)cw/pixelWidth);
		gapWidth = ((float)1/pixelWidth);
	}
	
	/**
	 * statically initializes the rendering components of the font for the render method in font
	 * 
	 * @param c - the game camera from base
	 */
	public static void init(Camera c) {
		rect = Shape.RECT;
		shader = Shader.TIL2DSHADER;
		camera = c;
	}
	
	/**
	 * renders the font, using the font's own transform.
	 * WARNING: this will fuc up the transform you pass into it, so make sure you set it b4 rendering again
	 * 
	 * @param characters - the characters to render
	 */
	public void render(Transform transform, boolean gui, char[][] characters, boolean centered, float r, float g, float b, float a) {
		if(characters != null) {
			
			// how wide each gap is that we're rendering
			float gapAdvance = gapWidth * transform.getHeight();
			
			// how wide each character and gap is that we're rendering
			float characterAdvance = (characterWidth + gapWidth) * transform.getHeight();
			
			texture.bind();
			
			float startX = transform.abcissa;
			
			// find how many lines tall the rendered text is
			int lines = characters.length;
			
			//the y position to draw at, advances with each line drawn
			//if we're centered, then we start rendering half way up
			float offsetY = transform.ordinate + ( (centered) ? -(lines * transform.getHeight() / 2) : 0 );
					
			//go through each of the lines
			//j represents the vertical position
			for(int j = 0; j < lines; ++j) {
				
				// find how many characters wide this line is
				int lineLength = characters[j].length;
				
				//the x position to draw at, advances with each character drawn, resets with new lines
				//if we're centered, then we start rendering half way back the line
				//this is resetting from our base non-moving transform
				float offsetX = startX + ( (centered) ? -( (lineLength * characterAdvance / 2) - (gapAdvance / 2) ) : 0 );
				
				//go through each of the characters in the line
				//i represents the horizontal position
				for(int i = 0; i < lineLength; ++i) {
					
					//get the character
					char c = characters[j][i];
					
					//get the frame of the character in the tileSheet
					Vector4f frame = texture.getFrame(c%16, c/16);

					transform.setTranslation(offsetX, offsetY);
					
					shader.enable(frame.x, frame.y, frame.z, frame.w, r, g, b, a);
					if(gui) {
						shader.setMvp(camera.getModelProjectionMatrix(camera.getModelMatrix(transform)));
					}else {
						shader.setMvp(camera.getModelViewProjectionMatrix(camera.getModelMatrix(transform)));
					}
					
					rect.render();
					
					shader.disable();
					
					//advance character 
					offsetX += characterAdvance;
					
				}
				
				//advance line
				offsetY += transform.getHeight();
			}
			
			texture.unbind();
		}
	}
	
}