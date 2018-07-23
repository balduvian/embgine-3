package embgine.core;

public abstract class MapReference {
	
	private int len;
	private int[] blockKeys;
	private Class<? extends Block>[] blockRefs;
	
	public MapReference(String mp, int[] bk, Class<? extends Block>[] br) {
		blockKeys = bk;
		blockRefs = br;
		len = blockKeys.length;
	}
	
	public int[] getBlockKeys() {
		return blockKeys;
	}
	
	public int[] getBlockRefs() {
		return blockKeys;
	}
	
	public Class<? extends Block> getBlock(int color) {
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
