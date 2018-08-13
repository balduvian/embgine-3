package embgine.core.components;

import java.util.ArrayList;

import embgine.core.Font;
import embgine.graphics.Texture;
import embgine.graphics.infos.FonInfo;
import embgine.graphics.infos.Info;
import embgine.graphics.infos.TilInfo;
import embgine.graphics.shapes.Shape;

public class FonRenderer extends Renderer<FonInfo>{

	private Font font;
	private float r, g, b, a;
	private char[][] text;
	
	public FonRenderer(Shape s, boolean synced, Font t) {
		super(Info.FONINFO, s, synced);
		font = t;
		r = 1;
		g = 1;
		b = 1;
		a = 1;
	}
	
	public FonRenderer clone() {
		return new FonRenderer(shape, tSync, font);
	}
	
	public void setText(String s) {
		text = new char[1][11];
		for(int i = 0; i < 11; ++i) {
			text[0][i] = s.charAt(i);
		}
	}
	
	public void setColor(float red, float green, float blue, float alpha) {
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}
	
	@Override
	void preRender() {
		info.setText(text);
		info.setParams(font, 18, true, true, true);
		info.setColor(r, g, b, a);
	}

}
