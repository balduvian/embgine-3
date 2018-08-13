package embgine.core.loaders;

import embgine.core.Block;
import embgine.graphics.Texture;

public class BlockLoader {
	
	private boolean   solid;
	private Texture texture;
	private int[][][][][][][][][] tileGrid;
	private boolean tileable;
	private int layer;
	private int type;
	
	public BlockLoader(boolean s, Texture t, int l, boolean a) {
		solid   = s;
		texture = t;
		layer = l;
		tileable = a;
		if(a) {
			tileGrid = new int[2][2][2][2][2][2][2][2][2];
		}
	}
	
	public Block create(){
		return new Block(solid, texture, layer, type);
	}
	
	public int[] getTile(int l, int lu, int u, int ur, int r, int rd, int d, int dl){
		return tileGrid[l][lu][u][ur][r][rd][d][dl];
	}
	
	public void setTile(int x, int y, int l, int lu, int u, int ur, int r, int rd, int d, int dl) {
		tileGrid[l][lu][u][ur][r][rd][d][dl][0] = x;
		tileGrid[l][lu][u][ur][r][rd][d][dl][1] = y;
	}
	
	public void setType(int t) {
		type = t;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean getTileable() {
		return tileable;
	}
	
	public int[][][][][][][][][] getTileGrid(){
		return tileGrid;
	}
}
