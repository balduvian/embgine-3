package embgine.core;

public class Variable {
	
	private double value;
	
	public Variable(int v) {
		value = v;
	}
	
	public Variable(float v) {
		value = v;
	}
	
	public Variable(double v) {
		value = v;
	}
	
	public Variable(boolean v) {
		if(v) {
			value = 1;
		}else {
			value = 0;
		}
	}
	
	public boolean getBool() {
		return (value == 1);
	}
	
	public int getInt() {
		return (int)value;
	}
	
	public float getFloat() {
		return (float)value;
	}
	
	public double getDouble() {
		return value;
	}
	
	public void setBool(boolean v) {
		if(v) {
			value = 1;
		}else {
			value = 0;
		}
	}
	
	public void setInt(int v) {
		value = v;
	}
	
	public void setFloat(float v) {
		value = v;
	}
	
	public void setFloat(double v) {
		value = v;
	}
}
