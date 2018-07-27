package embgine.core;

import java.io.File;
import java.util.ArrayList;

public class Utils {

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
	
	public static Class<?>[] getClasses(String packageName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        File directory = new File(classLoader.getResource(packageName).getFile());
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                try {
					classes.add(Class.forName(packageName.replace('/', '.') + '.' + file.getName().substring(0, file.getName().length() - 6)));
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
            }
        }
        
        return classes.toArray(new Class[classes.size()]);
    }
	
	public static String getHashName(Class<?> cl) {
		String nam = cl.getSimpleName();
		StringBuilder build = new StringBuilder(32);
		int len = nam.length();
		boolean found = false;
		for(int i = 0; i < len; ++i) {
			char c = nam.charAt(i);
			if(found) {
				build.append(c);
			}else {
				found = (c == '_');
			}
		}
		return build.toString();
	}
}
