package embgine.core.loaders;

import embgine.core.Block;
import embgine.graphics.Texture;

public class BlockLoader {
	
	
	private boolean   solid;
	private Texture texture;
	private int[][][][][][][][] tileGrid;
	private boolean tileable;
	
	public BlockLoader(boolean s, Texture t, boolean a) {
		solid   = s;
		texture = t;
		tileable = a;
		if(a) {
			tileGrid = new int[2][2][2][2][2][2][2][2];
		}
	}
	
	public Block create(){
		return new Block(solid, texture);
	}
	
	public int getTile(int l, int lu, int u, int ur, int r, int rd, int d, int dl){
		return tileGrid[l][lu][u][ur][r][rd][d][dl];
	}
	
	public int setTile(int v, int l, int lu, int u, int ur, int r, int rd, int d, int dl) {
		return tileGrid[l][lu][u][ur][r][rd][d][dl] = v;
	}
	
	public boolean getTileable() {
		return tileable;
	}
}
