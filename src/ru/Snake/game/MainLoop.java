package ru.Snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;

import javax.swing.JFrame;

import ru.Snake.SnakeMain;
import ru.Snake.game.components.Direction;
import ru.Snake.game.components.Field;
import ru.Snake.game.components.Rect;
import ru.Snake.game.components.SnakeType;
import ru.Snake.game.components.Type;


public class MainLoop extends Thread{
	
	private int width = 16 * (References.x - 0) + 6, height = 16 * (References.y - 0) + 6;
	
	public MainLoop(){
		startGame();
		init();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// start
		System.out.println("3 seconds to start!");
		try {
			this.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("Game started!");
		Input keyboard = new Input(); // Keyboard
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
		//frame = new JFrame();
		SnakeMain.frame.setBounds(100, 100, width, height);
		SnakeMain.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeMain.frame.setBackground(Color.GRAY);
		SnakeMain.frame.setResizable(false);
		SnakeMain.frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		SnakeMain.imageField = new Field();
		SnakeMain.frame.getContentPane().add(SnakeMain.imageField, BorderLayout.CENTER);
		
		draw();
	}
	
	private void draw(){
		SnakeMain.imageField.update(SnakeMain.imageField.getGraphics());
	}
	
	private void startGame(){
		for(int i = 0; i < References.x; i++){
			for(int j = 0; j < References.y; j++){
				SnakeMain.field[i][j] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null);
			}
		}
		
		// Head
		SnakeMain.field[4][0].setSnakeType(SnakeType.Head);
		SnakeMain.field[4][0].setDirection(Direction.Right);
		SnakeMain.field[4][0].setType(Type.Snake);
		SnakeMain.field[4][0].setImg(Pathes.Snake_Left);
		References.setCoord(4, 0, Direction.Right);
		
		// Body
		SnakeMain.field[3][0].setSnakeType(SnakeType.Body);
		SnakeMain.field[3][0].setDirection(Direction.Right);
		SnakeMain.field[3][0].setType(Type.Snake);
		SnakeMain.field[3][0].setImg(Pathes.Snake_Horisontal);
		
		// Body2
		SnakeMain.field[2][0].setSnakeType(SnakeType.Body);
		SnakeMain.field[2][0].setDirection(Direction.Right);
		SnakeMain.field[2][0].setType(Type.Snake);
		SnakeMain.field[2][0].setImg(Pathes.Snake_Horisontal);
		
		// Body3
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
		Rect[][] newField = new Rect[References.x][References.y];
		
		for(int i = 0; i < References.x; i++){
			for(int j = 0; j < References.y; j++){
				newField[i][j] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null);
			}
		}
		
		Rect[][] oldField = new Rect[References.x][References.y];
		oldField = SnakeMain.field;
		
		for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				// Head -> Right
				if(oldField[x][y].getSnakeType() == SnakeType.Head && oldField[x][y].getDirection() == Direction.Right){
					if(x + 1 > References.x - 1){
						gameOver();
					} else {
						newField[x+1][y].setDirection(Direction.Right);
						newField[x+1][y].setImg(Pathes.Snake_Left);
						newField[x+1][y].setSnakeType(SnakeType.Head);
						newField[x+1][y].setSpeed(oldField[x][y].getSpeed());
						newField[x+1][y].setType(Type.Snake);
						
						
						if(oldField[x][y].getDirection() == Direction.Up){ // ����� ������
							newField[x][y].setImg(Pathes.Snake_Angel_2);
						} else if(oldField[x][y].getDirection() == Direction.Down){ // ������ ������
							newField[x][y].setImg(Pathes.Snake_Angel_1);
						} else if(oldField[x][y].getDirection() == Direction.Right){ // ������
							newField[x][y].setImg(Pathes.Snake_Horisontal);
						}
						
						newField[x][y].setDirection(Direction.Right);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
						References.setCoord(x + 1, y, Direction.Right);
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
						
						
						if(oldField[x][y].getDirection() == Direction.Up){ // ����� �����
							newField[x][y].setImg(Pathes.Snake_Angel_3);
						} else if(oldField[x][y].getDirection() == Direction.Down){ // ������ �����
							newField[x][y].setImg(Pathes.Snake_Angel_4);
						} else if(oldField[x][y].getDirection() == Direction.Left){ // �����
							newField[x][y].setImg(Pathes.Snake_Horisontal);
						}
						
						newField[x][y].setDirection(Direction.Left);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
						References.setCoord(x - 1, y, Direction.Left);
						
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
						
						if(oldField[x][y].getDirection() == Direction.Up){ // �����
							newField[x][y].setImg(Pathes.Snake_Vertical);
						} else if(oldField[x][y].getDirection() == Direction.Right){ // ����� ������
							newField[x][y].setImg(Pathes.Snake_Angel_2);
						} else if(oldField[x][y].getDirection() == Direction.Left){ // ����� �����
							newField[x][y].setImg(Pathes.Snake_Angel_3);
						}
						
						newField[x][y].setDirection(Direction.Up);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
						References.setCoord(x, y - 1, Direction.Up);
						
					} // Head -> Down
				} else if(oldField[x][y].getSnakeType() == SnakeType.Head && oldField[x][y].getDirection() == Direction.Down){
					if(y + 1 > References.y - 1){
						gameOver();
					} else {
						newField[x][y + 1].setDirection(Direction.Down);
						newField[x][y + 1].setImg(Pathes.Snake_Up);
						newField[x][y + 1].setSnakeType(SnakeType.Head);
						newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
						newField[x][y + 1].setType(Type.Snake);
						
						if(oldField[x][y].getDirection() == Direction.Down){ // ����
							newField[x][y].setImg(Pathes.Snake_Vertical);
						} else if(oldField[x][y].getDirection() == Direction.Right){ // ������ ������
							newField[x][y].setImg(Pathes.Snake_Angel_1);
						} else if(oldField[x][y].getDirection() == Direction.Left){ // ������ �����
							newField[x][y].setImg(Pathes.Snake_Angel_4);
						}
						
						newField[x][y].setDirection(Direction.Down);
						newField[x][y].setSnakeType(SnakeType.Body);
						newField[x][y].setSpeed(oldField[x][y].getSpeed());
						newField[x][y].setType(Type.Snake);
						
						References.setCoord(x, y + 1, Direction.Down);
					} // Tail
				} else if(oldField[x][y].getSnakeType() == SnakeType.Tail){ // Tail
					
					Direction tailDirect = oldField[x][y].getDirection();
					if(tailDirect == Direction.Left){ // Tail -> Left
						Direction oldDirect = oldField[x - 1][y].getDirection();
						if(oldDirect == Direction.Down){
							newField[x][y + 1].setSnakeType(SnakeType.Tail);
							newField[x][y + 1].setImg(Pathes.Snake_Down);
							newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y + 1].setType(Type.Snake);
							newField[x][y + 1].setDirection(oldDirect);
						} else if(oldDirect == Direction.Left){
							newField[x - 1][y].setSnakeType(SnakeType.Tail);
							newField[x - 1][y].setImg(Pathes.Snake_Left);
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							newField[x - 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Up){
							newField[x][y - 1].setSnakeType(SnakeType.Tail);
							newField[x][y - 1].setImg(Pathes.Snake_Up);
							newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y - 1].setType(Type.Snake);
							newField[x][y - 1].setDirection(oldDirect);
						}
					} else if(tailDirect == Direction.Down){ // Tail -> down
						Direction oldDirect = oldField[x][y + 1].getDirection();
						if(oldDirect == Direction.Down){
							newField[x][y + 1].setSnakeType(SnakeType.Tail);
							newField[x][y + 1].setImg(Pathes.Snake_Down);
							newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y + 1].setType(Type.Snake);
							newField[x][y + 1].setDirection(oldDirect);
						} else if(oldDirect == Direction.Left){
							newField[x - 1][y].setSnakeType(SnakeType.Tail);
							newField[x - 1][y].setImg(Pathes.Snake_Left);
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							newField[x - 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Right){
							newField[x + 1][y].setSnakeType(SnakeType.Tail);
							newField[x + 1][y].setImg(Pathes.Snake_Right);
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							newField[x + 1][y].setDirection(oldDirect);
						}
					} else if(tailDirect == Direction.Right){ // Tail -> Right
						Direction oldDirect = oldField[x + 1][y].getDirection();
						if(oldDirect == Direction.Down){
							newField[x][y + 1].setSnakeType(SnakeType.Tail);
							newField[x][y + 1].setImg(Pathes.Snake_Down);
							newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y + 1].setType(Type.Snake);
							newField[x][y + 1].setDirection(oldDirect);
						} else if(oldDirect == Direction.Right){
							newField[x + 1][y].setSnakeType(SnakeType.Tail);
							newField[x + 1][y].setImg(Pathes.Snake_Right);
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							newField[x + 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Up){
							newField[x][y - 1].setSnakeType(SnakeType.Tail);
							newField[x][y - 1].setImg(Pathes.Snake_Up);
							newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y - 1].setType(Type.Snake);
							newField[x][y - 1].setDirection(oldDirect);
						}
					} else if(tailDirect == Direction.Up){ // Tail -> Up
						Direction oldDirect = oldField[x][y - 1].getDirection();
						if(oldDirect == Direction.Right){
							newField[x + 1][y].setSnakeType(SnakeType.Tail);
							newField[x + 1][y].setImg(Pathes.Snake_Right);
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							newField[x + 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Left){
							newField[x - 1][y].setSnakeType(SnakeType.Tail);
							newField[x - 1][y].setImg(Pathes.Snake_Left);
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							newField[x - 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Up){
							newField[x][y - 1].setSnakeType(SnakeType.Tail);
							newField[x][y - 1].setImg(Pathes.Snake_Up);
							newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y - 1].setType(Type.Snake);
							newField[x][y - 1].setDirection(oldDirect);
						}
					}
					
					oldField[x][y].setDirection(Direction.Null);
					oldField[x][y].setImg(null);
					oldField[x][y].setSnakeType(SnakeType.Null);
					oldField[x][y].setSpeed(0);
					oldField[x][y].setType(Type.Void);
				} else if(oldField[x][y].getSnakeType() == SnakeType.Body){
					
					Direction bodyDir = oldField[x][y].getDirection(); 
					
					if(bodyDir == Direction.Down){ // ���� ����
						if(!isBorder(x, y, Direction.Down)){
							if(oldField[x][y - 1].getType() == Type.Snake){ // ������ ��� ����
								if(oldField[x][y + 1].getType() == Type.Snake){ // ����� ����
									newField[x][y + 1].setDirection(Direction.Down);
									newField[x][y + 1].setImg(Pathes.Snake_Vertical);
								} else if(oldField[x - 1][y].getType() == Type.Snake){ // ����� ����
									newField[x][y + 1].setDirection(Direction.Left);
									newField[x][y + 1].setImg(Pathes.Snake_Angel_4);
								} else if(oldField[x + 1][y].getType() == Type.Snake){ // ������ ����
									newField[x][y + 1].setDirection(Direction.Right);
									newField[x][y + 1].setImg(Pathes.Snake_Angel_1);
								}
								
								newField[x][y + 1].setSnakeType(oldField[x][y].getSnakeType());
								newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
								newField[x][y + 1].setType(Type.Snake);
								
							}
						}
					}
				}
			}
		}
		SnakeMain.field = newField;
	}
	
	public static Rect changeDirection(Rect rect, Direction direct){
		Rect newRect = rect;
		newRect.setDirection(direct);
		
		return newRect;
	}
	
	public boolean isBorder(int oldX, int oldY, Direction dir){
		if(dir == Direction.Down){
			if(oldY + 1 > References.y - 1){
				return true;
			} else return false;
		} else if(dir == Direction.Left){
			if(oldX - 1 < 0){
				return true;
			} else return false;
		} else if(dir == Direction.Right){
			if(oldX + 1 > References.x - 1){
				return true;
			} else return false;
		} else if(dir == Direction.Up){
			if(oldY - 1 < 0){
				return true;
			} else return false;
		}
		
		return false;
	}
	
	private void gameOver(){
		System.out.println("Game Over!");
		System.exit(0);
	}
}
