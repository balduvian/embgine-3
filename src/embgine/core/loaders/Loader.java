package embgine.core.loaders;

abstract public class Loader<T> {
	
	public abstract T create(Object... objects);
	
	public abstract void sets(Object... objects);
	
	public abstract void setup();
}
