package ru.Snake;

import java.awt.EventQueue;

import javax.swing.JFrame;

import ru.Snake.game.MainLoop;
import ru.Snake.game.References;
import ru.Snake.game.components.Field;
import ru.Snake.game.components.Rect;

public class SnakeMain {

	public static Field imageField;
	
	public static Rect[][] field = new Rect[References.x][References.y];
	
	public static SnakeMain loop;
	
	public static JFrame frame = new JFrame();
	
	public static void main(String[] args) {
		
		if(!References.started){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						//loop = new SnakeMain();
						SnakeMain.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
			References.started = true;
		}
		
		MainLoop loop = new MainLoop();
		loop.start();
	}

}
