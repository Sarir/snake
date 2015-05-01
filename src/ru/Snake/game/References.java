package ru.Snake.game;

import ru.Snake.game.components.Direction;

public class References {

	public static boolean started = false;
	
	public static final int x = 10 + 1, y = 10 + 1;
	
	public static int headX, headY;
	
	public static Direction headDirection;
	
	public static void setCoord(int x, int y, Direction direct){
		References.headX = x;
		References.headY = y;
		References.headDirection = direct;
		
	}
}
