package ru.Snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import ru.Snake.SnakeMain;
import ru.Snake.game.components.Direction;
import ru.Snake.game.components.Field;
import ru.Snake.game.components.Rect;
import ru.Snake.game.components.SnakeType;
import ru.Snake.game.components.Type;

public class MainLoop extends Thread{
	
	private JFrame frame;
	
	private int width = 16 * SnakeMain.x, height = 16 * SnakeMain.y;
	
	public MainLoop(){
		startGame();
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
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// start
		
		// Loop
		while(true){
			draw();
			try {
				this.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void init(){
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.GRAY);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		SnakeMain.imageField = new Field();
		frame.getContentPane().add(SnakeMain.imageField, BorderLayout.CENTER);
	}
	
	private void draw(){
		SnakeMain.imageField.update(SnakeMain.imageField.getGraphics());
	}
	
	private void startGame(){
		for(int i = 0; i < SnakeMain.x; i++){
			for(int j = 0; j < SnakeMain.y; j++){
				SnakeMain.field[i][j] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null);
			}
		}
		
		SnakeMain.field[2][0].setSnakeType(SnakeType.Head);
		SnakeMain.field[2][0].setDirection(Direction.Right);
		SnakeMain.field[2][0].setType(Type.Snake_Left);
		SnakeMain.field[2][0].setImg(Pathes.Snake_Left);
	}
}
