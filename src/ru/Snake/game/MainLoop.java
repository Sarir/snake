package ru.Snake.game;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import ru.Snake.SnakeMain;
import ru.Snake.game.components.Direction;
import ru.Snake.game.components.SnakeType;
import ru.Snake.game.components.Type;

public class MainLoop extends Thread{
	
	private JFrame frame;
	
	private int width = 16 * SnakeMain.x, height = 16 * SnakeMain.y;
	
	public MainLoop(){
		init();
		
		if(!SnakeMain.started){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						MainLoop loop = new MainLoop();
						loop.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
			SnakeMain.started = true;
		}
	}
	
	@Override
	public void run() {
		// start
		
		// Fill field
		for(int i = 0; i < SnakeMain.x; i++){
			for(int j = 0; j < SnakeMain.y; j++){
				SnakeMain.field[i][j].setImg(null);
				SnakeMain.field[i][j].setDirection(Direction.Null);
				SnakeMain.field[i][j].setSpeed(0);
				SnakeMain.field[i][j].setType(Type.Void);
			}
		}
		
		SnakeMain.field[2][0].setSnakeType(SnakeType.Head);
		SnakeMain.field[2][0].setDirection(Direction.Right);
		SnakeMain.field[2][0].setType(Type.Snake_Left);
		try {
			SnakeMain.field[2][0].setImg(new File(getClass().getResource(Pathes.Snake_Left).toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		// Loop
		while(true){
			
		}
	}
	
	private void init(){
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.GRAY);
		frame.setResizable(false);
	}
}
