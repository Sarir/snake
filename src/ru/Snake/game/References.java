package ru.Snake.game;

import java.util.ArrayList;

import ru.Snake.game.components.Direction;
import ru.Snake.game.components.Rect;

public class References {

	public static boolean started = false;
	
	public static final int x = 10 + 1, y = 10 + 1;
	
	public static int headX, headY;
	
	public static Direction headDirection;
	
	public static int secondsToStart = 3;
	
	public static int gameTick = 300;
	
	public static ArrayList<Rect> snake = new ArrayList<Rect>();
	
	public static ArrayList<Rect> apples = new ArrayList<Rect>();
	
	public static int grow = 0;
	
	public static void setCoord(int x, int y, Direction direct){
		References.headX = x;
		References.headY = y;
		References.headDirection = direct;
		
	}
}
