package com.electdead.newgame.physics;

/*
 * Класс вектора с двумя координатами типа float
 * и методами для обработки векторов.
 * Служит для указания позиции на экране и работы с ней.
 */
public class Vector2F {
	public float x;
	public float y;

	public static float worldXpos;
	public static float worldYpos;

	public Vector2F() {
		this.x = 0.0f;
		this.y = 0.0f;
	}

	public Vector2F(float xpos, float ypos) {
		this.x = xpos;
		this.y = ypos;
	}

	public static Vector2F zero() {
		return new Vector2F(0, 0);
	}

	public void normalize() {
		double length = length();

		if (length != 0.0) {
			float s = 1.0f / (float) length;
			x = x * s;
			y = y * s;
		}
	}
	
	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public Vector2F getScreenLocation() {
		return new Vector2F(x, y);
	}

	public Vector2F getWorldLocation() {
		return new Vector2F(x - worldXpos, y - worldYpos);
	}

	public boolean equals(Vector2F other) {
		return (x == other.x && y == other.y);
	}

	public Vector2F copy() {
		return new Vector2F(x, y);
	}

	public Vector2F add(Vector2F vec) {
		x = x + vec.x;
		y = y + vec.y;
		return new Vector2F(x, y);
	}
	
	public Vector2F sub(Vector2F vec) {
		x = x - vec.x;
		y = y - vec.y;
		return new Vector2F(x, y);
	}

	public static void setWorldVariables(float wx, float wy) {
		worldXpos = wx;
		worldYpos = wy;
	}

	public static double getDistanceOnScreen(Vector2F vec1, Vector2F vec2) {
		float v1 = vec1.x - vec2.x;
		float v2 = vec1.y - vec2.y;
		return Math.sqrt(v1 * v1 + v2 * v2);
	}

	public double getDistanceBetweenWorldVectors(Vector2F vec) {
		float dx = Math.abs(getWorldLocation().x - vec.getWorldLocation().x);
		float dy = Math.abs(getWorldLocation().y - vec.getWorldLocation().y);
		return Math.abs(dx * dx - dy * dy);
	}
	
	@Override
	public String toString() {
		return "Vector2F:[x:" + x + ",y:" + y + "]";
	}
}
