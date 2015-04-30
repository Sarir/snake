package ru.Snake;

import ru.Snake.game.MainLoop;
import ru.Snake.game.components.Field;
import ru.Snake.game.components.Rect;

public class SnakeMain {

	public static Field imageField;
	
	public static boolean started = false;
	
	public static final int x = 10, y = 10;
	
	public static Rect[][] field = new Rect[x][y];
	
	public static void main(String[] args) {
		System.out.println("Game started!");
		MainLoop loop = new MainLoop();
		loop.start();
	}

}
