package embgine.graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

public class Texture {
	
	private int id;
	
	private float frameWidth;
	private float frameHeight;
	
	private int width;
	private int height;
	
	public Texture(String path, boolean n, boolean c) {
		init(path, n, c);
		frameWidth = 1;
		frameHeight = 1;
	}
	
	public Texture(String path) {
		init(path, true, false);
		frameWidth = 1;
		frameHeight = 1;
	}
	
	public Texture(String path, int fw, int ft){
		init(path, true, false);
		frameWidth = 1f/fw;
		frameHeight = 1f/ft;
	}
	
	public Texture(String path, int fw){
		init(path, true, false);
		frameWidth = 1f/fw;
		frameHeight = 1f;
	}
	
	public Vector4f getFrame(int x, int y) {
		return new Vector4f(frameWidth, frameHeight, x*frameWidth, y*frameHeight);
	}
	
	public Vector4f getFrame(int x) {
		return new Vector4f(frameWidth, 1, x*frameWidth, 1);
	}
	
	public Texture(int w, int h) {
        width = w;
        height = h;
        id = glGenTextures();
        bind();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        unbind();
    }
	
	private void init(String path, boolean nearest, boolean clamp) {
		try {
			BufferedImage b = ImageIO.read(this.getClass().getClassLoader().getResource(path).openStream());
			width = b.getWidth();
			height = b.getHeight();
			int[] pixels = b.getRGB(0, 0, width, height, null, 0, width);
			ByteBuffer buffer = BufferUtils.createByteBuffer(width*height*4);
			for(int i = 0; i < height; ++i) {
				for(int j = 0; j < width; ++j) {
					int pixel = pixels[i*width+j];
					buffer.put((byte)((pixel >> 16) & 0xff));
					buffer.put((byte)((pixel >>  8) & 0xff));
					buffer.put((byte)((pixel      ) & 0xff));
					buffer.put((byte)((pixel >> 24) & 0xff));
				}
			}
			buffer.flip();
			id = glGenTextures();
			bind();
			if(clamp) {
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			}
			if(nearest) {
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			}else {
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			}
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			unbind();
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void destroy() {
		glDeleteTextures(id);
	}
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
