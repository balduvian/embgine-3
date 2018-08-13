package embgine.graphics;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_DST_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.opengl.GL30;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.openal.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPop;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.libc.LibCStdlib.free;

public class Window {
	
	private long window;
	
	private int width;
	private int height;
	
	private boolean resized;
	
	private long cursorID;
	
	private GLFWVidMode vidMode;
	
	private int fixedFrameRate;
	
	public Window(boolean fu, String t, boolean r) {
		
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		if(!glfwInit()) {
			System.err.println("GLFW Failed to Initialize");
			System.exit(-1);
		}
		
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_RESIZABLE, r ? GLFW_TRUE: GLFW_FALSE);
		//glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
		
		vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());	

		fixedFrameRate = vidMode.refreshRate();
		
		if(fu) {
			width = vidMode.width();
			height = vidMode.height();
			window = glfwCreateWindow(width, height, t, glfwGetPrimaryMonitor(), 0);
		}else {
			width = vidMode.width()/2;
			height = vidMode.height()/2;
			window = glfwCreateWindow(width, height, t, 0, 0);
		}
		
		glfwMakeContextCurrent(window);
		createCapabilities();
		
		glfwSwapInterval(1);
		
		glfwSetWindowSizeCallback(window, (long window, int ww, int hh) -> {
			resized = true;
			width = ww;
			height = hh;
			glViewport(0, 0, width, height);
		} 	);
		
		glClearColor(0.1f,0.25f,0.85f,1f);
		
		glEnable(GL30.GL_CLIP_DISTANCE0);
		glEnable(GL_TEXTURE_2D);
		//glEnable(GL_DEPTH_TEST);
		//glDepthFunc(GL_LEQUAL);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//GL_ONE_MINUS_CONSTANT_COLOR //GL_ONE_MINUS_SRC_ALPHA
		
		//initCursor();
	}
	
	public void resetCursor() {
		glfwSetCursorPos(window, vidMode.width(), vidMode.height());
	}
	
	public void fullscreen(boolean full) {
		if(full) {
			if(fixedFrameRate == -1) {
				glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, vidMode.width(), vidMode.height(), vidMode.refreshRate());
			}else {
				glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, vidMode.width(), vidMode.height(), fixedFrameRate);
			}
		}else {
			if(fixedFrameRate == -1) {
				glfwSetWindowMonitor(window, 0, vidMode.width()/4, vidMode.height()/4, vidMode.width()/2, vidMode.height()/2, vidMode.refreshRate());
			}else {
				glfwSetWindowMonitor(window, 0, vidMode.width()/4, vidMode.height()/4, vidMode.width()/2, vidMode.height()/2, fixedFrameRate);
			}
		}
	}
	
	public int getRefreshRate() {
		return vidMode.refreshRate();
	}
	
	public void showCursor() {
		glfwSetCursor(window, cursorID);
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}
	
	public void hideCursor() {
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	}
	
	private void initCursor() {
		try {
			InputStream stream = new FileInputStream("res/textures/cursor.png");
		    BufferedImage image = ImageIO.read(stream);
	
		    int width = image.getWidth();
		    int height = image.getHeight();
	
		    int[] pixels = new int[width * height];
		    image.getRGB(0, 0, width, height, pixels, 0, width);
	
		    // convert image to RGBA format
		    ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
	
		    for (int y = 0; y < height; y++)
		    {
		        for (int x = 0; x < width; x++)
		        {
		            int pixel = pixels[y * width + x];
	
		            buffer.put((byte) ((pixel >> 16) & 0xFF));  // red
		            buffer.put((byte) ((pixel >> 8) & 0xFF));   // green
		            buffer.put((byte) (pixel & 0xFF));          // blue
		            buffer.put((byte) ((pixel >> 24) & 0xFF));  // alpha
		        }
		    }
		    buffer.flip(); // this will flip the cursor image vertically
	
		    // create a GLFWImage
		    GLFWImage cursorImg= GLFWImage.create();
		    cursorImg.width(width);     // setup the images' width
		    cursorImg.height(height);   // setup the images' height
		    cursorImg.pixels(buffer);   // pass image data
	
		    // create custom cursor and store its ID
		    int hotspotX = 0;
		    int hotspotY = 0;
		    cursorID = GLFW.glfwCreateCursor(cursorImg, hotspotX , hotspotY);
	
		    // set current cursor
		    glfwSetCursor(window, cursorID);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	public Vector3f getMouseCoords(Camera c) {
		double[] x = new double[1];
		double[] y = new double[1];
		glfwGetCursorPos(window, x, y);
		Vector3f ret = new Vector3f((float)x[0], (float)y[0], 0);
		Transform t = c.getTransform();
		ret.mul(t.getWidth()/width, t.getHeight()/height, 1);
		ret.sub(t.getWidth()/2, t.getHeight()/2, 0);
		return ret;
	}
	
	public boolean getResized() {
		return resized;
	}
	
	public boolean mousePressed(int button) {
		return glfwGetMouseButton(window, button) == GLFW_PRESS;
	}
	
	public boolean keyPressed(int keyCode) {
		return glfwGetKey(window, keyCode) == GLFW_PRESS;
	}
	
	public void update() {
		resized = false;
		glfwPollEvents();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void swap() {
		glfwSwapBuffers(window);
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, true);
		glfwTerminate();
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setSize(int w, int h) {
		glfwSetWindowSize(window, w, h);
		glViewport(0,0,w,h);
		width = w;
		height = h;
	}
	
	public long get() {
		return window;
	}
}
