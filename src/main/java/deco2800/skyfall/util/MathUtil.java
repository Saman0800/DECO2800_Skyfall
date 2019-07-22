package deco2800.skyfall.util;

public class MathUtil {
	
	public static final float EPSILON = 0.001f;
	
	
	private MathUtil(){
		//No one should call this constructor.
	}
	
	public static boolean floatEquality(float x, float y) {
		return Math.abs(x - y) < EPSILON;
	}
	public static boolean floatEquality(float x, float y, float e) {
		return Math.abs(x - y) < e;
	}

}
