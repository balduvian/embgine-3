package embgine.core;

public class  Utils {

	public static final float PI = 3.1415927f;
	
	public static float rand(float min, float max) {
		return (float)(min+Math.random()*(max-min));
	}
	
	public static int rand(int min, int max) {
		return (int)(min+Math.random()*(min-max+1));
	}
	
	public static float random() {
		return (float) Math.random();
	}
}
