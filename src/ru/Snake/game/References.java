package ru.Snake.game;

import java.util.ArrayList;

import ru.Snake.game.components.Direction;
import ru.Snake.game.components.Rect;

public class References {

	public static boolean started = false;
	
	public static int preX = 1, preY = 1;
	
	public static int x = 10 + preX, y = 10 + preY;
	
	public static int headX, headY;
	
	public static Direction headDirection;
	
	public static int secondsToStart = 3;
	
	public static final int defaultGameTick = 200; // Not changeable, this need for restart. Default value = 500
	
	public static int gameTick = References.defaultGameTick;
	
	public static int decrGameTick = 5;
	
	public static ArrayList<Rect> snake = new ArrayList<Rect>();
	
	public static ArrayList<Rect> apples = new ArrayList<Rect>();
	
	public static int tailX = 0, tailY = 0;
	
	public static int grow = 0;
	
	public static int score = 0;
	
	public static boolean music = true;
	
	public static int appleIncBy = 1;
	
	public static void setCoord(int x, int y, Direction direct){
		References.headX = x;
		References.headY = y;
		References.headDirection = direct;	
	}
}
