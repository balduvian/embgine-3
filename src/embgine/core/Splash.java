package embgine.core;

import embgine.graphics.Camera;
import embgine.graphics.Texture;
import embgine.graphics.Transform;
import embgine.graphics.Window;
import embgine.graphics.infos.ColInfo;
import embgine.graphics.infos.FonInfo;
import embgine.graphics.infos.Info;
import embgine.graphics.infos.TexInfo;
import embgine.graphics.shapes.Shape;

public class Splash {
	
	private static final double SHOW_TIME = 3;
	
	private double timer;
	private Camera camera;
	
	private Transform gt;
	private ColInfo white;
	private TexInfo logo;
	private FonInfo text;
	
	private Texture logoTex;
	private Font logoFont;
	
	private Shape rect;
	
	public Splash(Camera c) {
		camera = c;
		
		rect = Shape.RECT;
		
		gt = new Transform(0, 0, 0, 0, 0);
		
		white = Info.COLINFO;
		logo = Info.TEXINFO;
		text = Info.FONINFO;
		logoTex = new Texture("embgine/standard/logo.png");
		logoFont = new Font(new Texture("embgine/standard/text.png", 16, 8), 8, 6);
		text.setText(new char[][] {{'M','a','d','e',' ','W','i','t','h',' ','E','m','b','g','i','n','e'}});
		text.setParams(logoFont, 9, true, true, true);
		text.setColor(0, 0, 0, 1);
		
		timer = SHOW_TIME;
	}
	
	public boolean update(Window w) {
		camera.update();
		timer -= Base.time;
		if(w.keyPressed(81)) {
			return(true);
		}else {
			return (timer <= 0);
		}
	}
	
	public void render() {
		
		gt.set(0, 0, camera.getTransform().getWidth(), camera.getTransform().getHeight());
		white.setColor(1, 1, 1, 1);
		white.setShape(rect);
		white.setTransform(gt);
		white.render();
		
		gt.set(96, 40, 64, 64);
		logo.setTexture(logoTex);
		logo.setShape(rect);
		logo.setTransform(gt);
		logo.render();
		
		gt.setPosition(128, 112);
		text.setShape(rect);
		text.setTransform(gt);
		text.render();
		
	}
}