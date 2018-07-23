package embgine.core.loaders;

abstract public class Loader<T> {
	
	public Loader() { }
	
	public abstract T create(Object... objects);
	
	public abstract int getID();
	
}
