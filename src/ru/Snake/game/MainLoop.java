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
			gameLogic();
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
		//SnakeMain.imageField.update(SnakeMain.imageField.getGraphics());
	}
	
	private void startGame(){
		for(int i = 0; i < SnakeMain.x; i++){
			for(int j = 0; j < SnakeMain.y; j++){
				SnakeMain.field[i][j] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null);
			}
		}
		
		// Head
		SnakeMain.field[2][0].setSnakeType(SnakeType.Head);
		SnakeMain.field[2][0].setDirection(Direction.Right);
		SnakeMain.field[2][0].setType(Type.Snake);
		SnakeMain.field[2][0].setImg(Pathes.Snake_Left);
		
		// Body
		SnakeMain.field[1][0].setSnakeType(SnakeType.Body);
		SnakeMain.field[1][0].setDirection(Direction.Right);
		SnakeMain.field[1][0].setType(Type.Snake);
		SnakeMain.field[1][0].setImg(Pathes.Snake_Horisontal);
		
		// Tail
		SnakeMain.field[0][0].setSnakeType(SnakeType.Tail);
		SnakeMain.field[0][0].setDirection(Direction.Right);
		SnakeMain.field[0][0].setType(Type.Snake);
		SnakeMain.field[0][0].setImg(Pathes.Snake_Right);
	}
	
	private void gameLogic(){
		Rect[][] newField = SnakeMain.field;
		Rect[][] oldField = SnakeMain.field;
		for(int x = 0; x < SnakeMain.x; x++){
			for(int y = 0; y < SnakeMain.y; y++){
				// Head -> Right
				if(oldField[x][y].getSnakeType() == SnakeType.Head && oldField[x][y].getDirection() == Direction.Right){
					if(x + 1 > SnakeMain.x){
						gameOver();
					} else {
						newField[x+1][y].setDirection(Direction.Right);
						newField[x+1][y].setImg(Pathes.Snake_Left);
						newField[x+1][y].setSnakeType(SnakeType.Head);
						newField[x+1][y].setSpeed(oldField[x][y].getSpeed());
						newField[x+1][y].setType(Type.Snake);
						
						
						if(oldField[x][y].getDirection() == Direction.Up){ // Снизу вправо
							newField[x][y].setImg(Pathes.Snake_Angel_2);
						} else if(oldField[x][y].getDirection() == Direction.Down){ // Сверху вправо
							newField[x][y].setImg(Pathes.Snake_Angel_1);
						} else if(oldField[x][y].getDirection() == Direction.Right){ // Вправо
							newField[x][y].setImg(Pathes.Snake_Horisontal);
						}
						
						newField[x][y].setDirection(Direction.Right);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
					} // Head -> Left
				} else if(oldField[x][y].getSnakeType() == SnakeType.Head && oldField[x][y].getDirection() == Direction.Left){ 
					if(x - 1 == -1){
						gameOver();
					} else {
						newField[x-1][y].setDirection(Direction.Left);
						newField[x-1][y].setImg(Pathes.Snake_Right);
						newField[x-1][y].setSnakeType(SnakeType.Head);
						newField[x-1][y].setSpeed(oldField[x][y].getSpeed());
						newField[x-1][y].setType(Type.Snake);
						
						
						if(oldField[x][y].getDirection() == Direction.Up){ // Снизу влево
							newField[x][y].setImg(Pathes.Snake_Angel_3);
						} else if(oldField[x][y].getDirection() == Direction.Down){ // Сверху влево
							newField[x][y].setImg(Pathes.Snake_Angel_4);
						} else if(oldField[x][y].getDirection() == Direction.Left){ // Влево
							newField[x][y].setImg(Pathes.Snake_Horisontal);
						}
						
						newField[x][y].setDirection(Direction.Left);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
					} // Head -> Up
				} else if(oldField[x][y].getSnakeType() == SnakeType.Head && oldField[x][y].getDirection() == Direction.Up){
					if(y - 1 == -1){
						gameOver();
					} else {
						newField[x][y - 1].setDirection(Direction.Up);
						newField[x][y - 1].setImg(Pathes.Snake_Down);
						newField[x][y - 1].setSnakeType(SnakeType.Head);
						newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
						newField[x][y - 1].setType(Type.Snake);
						
						if(oldField[x][y].getDirection() == Direction.Up){ // Вверх
							newField[x][y].setImg(Pathes.Snake_Vertical);
						} else if(oldField[x][y].getDirection() == Direction.Right){ // Снизу вправо
							newField[x][y].setImg(Pathes.Snake_Angel_2);
						} else if(oldField[x][y].getDirection() == Direction.Left){ // Снизу влево
							newField[x][y].setImg(Pathes.Snake_Angel_3);
						}
						
						newField[x][y].setDirection(Direction.Up);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
					} // Head -> Down
				} else if(oldField[x][y].getSnakeType() == SnakeType.Head && oldField[x][y].getDirection() == Direction.Down){
					if(y + 1 > SnakeMain.y){
						gameOver();
					} else {
						newField[x][y + 1].setDirection(Direction.Down);
						newField[x][y + 1].setImg(Pathes.Snake_Up);
						newField[x][y + 1].setSnakeType(SnakeType.Head);
						newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
						newField[x][y + 1].setType(Type.Snake);
						
						if(oldField[x][y].getDirection() == Direction.Down){ // Вниз
							newField[x][y].setImg(Pathes.Snake_Vertical);
						} else if(oldField[x][y].getDirection() == Direction.Right){ // Сверху вправо
							newField[x][y].setImg(Pathes.Snake_Angel_1);
						} else if(oldField[x][y].getDirection() == Direction.Left){ // Сверху влево
							newField[x][y].setImg(Pathes.Snake_Angel_4);
						}
						
						newField[x][y].setDirection(Direction.Down);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
					}
				}
			}
		}
	}
	
	private void gameOver(){
		System.out.println("Game Over!");
		System.exit(0);
	}
}
