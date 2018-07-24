package embgine.core;

public class Global<Type>{
	
	private Type value;
	
	public Global(Type v) {
		value = v;
	}
	
	public Type get() {
		return value;
	}
	
	public void set(Type v) {
		value = v;
	}
}
