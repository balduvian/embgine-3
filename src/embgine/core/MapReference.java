package embgine.core;

import embgine.core.loaders.BlockLoader;

public abstract class MapReference {
	
	private int[] blockKeys;
	private String[] blockNames;
	private BlockLoader[] blockRefs;
	private int[][] blockValues;
	
	public MapReference(int[] bk, String[] br, int[][] bv) {
		blockKeys = bk;
		
		blockNames = br;
		
		blockValues = bv;
	}
	
	public void init(Index index) {
		int len = blockNames.length;
		blockRefs = new BlockLoader[len];
		for(int i = 0; i < len; ++i) {
			blockRefs[i] = index.getBlockLoader(blockNames[i]);
		}
	}
	
	public int[] getBlockKeys() {
		return blockKeys;
	}
	
	public BlockLoader[] getBlockRefs() {
		return blockRefs;
	}
	
	public int[][] getBlockValues(){
		return blockValues;
	}
	
}
