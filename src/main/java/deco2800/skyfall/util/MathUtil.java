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

	/**
	 * Simple clamping function return value, bounded by min and max
	 * Generic implementation, for all possible implementations of comparable
	 * @param value value to compare
	 * @param min minimum attainable by value
	 * @param max maximum attainable by value
	 * @param <T> Needs to be comparable type, such as int, float, double, etc
	 * @return the clamped value
	 */
	public static <T extends Comparable<? super T>> T clamp(T value, T min, T max) {
		if (value.compareTo(min) < 0) {
			return min;
		}
		else if (value.compareTo(max) > 0) {
			return max;
		}
		else {
			return value;
		}
	}

}
