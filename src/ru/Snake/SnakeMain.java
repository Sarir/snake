package ru.Snake;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ru.Snake.game.Input;
import ru.Snake.game.MainLoop;
import ru.Snake.game.components.Field;
import ru.Snake.game.components.Rect;
import ru.Snake.game.frames.Menu;

public class SnakeMain {

	public static Field imageField;
	
	public static Rect[][] field;
	
	public static JFrame frame = new JFrame();
	
	public static Menu menu;
	
	public static Input keyboard = new Input();
	
	public static MainLoop game;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					menu = new Menu();
					SnakeMain.menu.setVisible(true);
					menu.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
