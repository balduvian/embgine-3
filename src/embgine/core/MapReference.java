package embgine.core;

import embgine.core.loaders.BlockLoader;

public abstract class MapReference {
	
	private int len;
	private int[] blockKeys;
	private String[] blockNames;
	private BlockLoader[] blockRefs;
	
	public MapReference(int[] bk, String[] br) {
		blockKeys = bk;
		
		blockNames = br;
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
	
	public BlockLoader getBlock(int color) {
		for(int i = 0; i < len; ++i) {
			if(blockKeys[i] == color) {
				try {
					return blockRefs[i];
				}catch(Exception ex) {}
			}
		}
		return null;
	}
}
