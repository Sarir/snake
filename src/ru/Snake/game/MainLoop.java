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
			this.sleep(References.secondsToStart * 1000);
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
				this.sleep(References.gameTick);
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
		SnakeMain.field[4][3].setSnakeType(SnakeType.Head);
		SnakeMain.field[4][3].setDirection(Direction.Right);
		SnakeMain.field[4][3].setType(Type.Snake);
		SnakeMain.field[4][3].setImg(Pathes.Snake_Left);
		References.setCoord(4, 3, Direction.Right);
		
		// Body
		SnakeMain.field[3][3].setSnakeType(SnakeType.Body);
		SnakeMain.field[3][3].setDirection(Direction.Right);
		SnakeMain.field[3][3].setType(Type.Snake);
		SnakeMain.field[3][3].setImg(Pathes.Snake_Horisontal);
		
		// Body2
		SnakeMain.field[2][3].setSnakeType(SnakeType.Body);
		SnakeMain.field[2][3].setDirection(Direction.Right);
		SnakeMain.field[2][3].setType(Type.Snake);
		SnakeMain.field[2][3].setImg(Pathes.Snake_Angel_1);
		
		// Body3
		SnakeMain.field[2][2].setSnakeType(SnakeType.Body);
		SnakeMain.field[2][2].setDirection(Direction.Right);
		SnakeMain.field[2][2].setType(Type.Snake);
		SnakeMain.field[2][2].setImg(Pathes.Snake_Vertical);
		
		// Body4
		SnakeMain.field[2][1].setSnakeType(SnakeType.Body);
		SnakeMain.field[2][1].setDirection(Direction.Down);
		SnakeMain.field[2][1].setType(Type.Snake);
		SnakeMain.field[2][1].setImg(Pathes.Snake_Angel_3);
		
		// Tail
		SnakeMain.field[1][1].setSnakeType(SnakeType.Tail);
		SnakeMain.field[1][1].setDirection(Direction.Right);
		SnakeMain.field[1][1].setType(Type.Snake);
		SnakeMain.field[1][1].setImg(Pathes.Snake_Right);
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
		
		
		// Head
		for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				if(oldField[x][y].getSnakeType() == SnakeType.Head){
					if(oldField[x][y].getDirection() == Direction.Right){ // Head to Right
						if(x + 1 > References.x - 1){
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
							
							/*if(oldField[x][y].getDirection() == Direction.Right){ // Едем вправо
								if(!isBorder(x, y, Direction.Up) && oldField[x][y - 1].getType() == Type.Snake){ // Сверху змея
									newField[x + 1][y].setDirection(Direction.Up);
									newField[x + 1][y].setImg(Pathes.Snake_Angel_4);
								} else if(!isBorder(x, y, Direction.Down) && oldField[x][y + 1].getType() == Type.Snake){ // Снизу змея
									newField[x + 1][y].setDirection(Direction.Down);
									newField[x + 1][y].setImg(Pathes.Snake_Angel_3);
								} else if(!isBorder(x, y, Direction.Right) && oldField[x + 1][y].getType() == Type.Snake){ // Справо змея
									newField[x + 1][y].setDirection(Direction.Right);
									newField[x + 1][y].setImg(Pathes.Snake_Horisontal);
								}
								
								newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
								newField[x + 1][y].setType(Type.Snake);
								newField[x + 1][y].setSnakeType(SnakeType.Body);
							}*/
							
							References.setCoord(x + 1, y, Direction.Right);
						}
					} else if(oldField[x][y].getDirection() == Direction.Left){ // Head to Left
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
							
							References.setCoord(x - 1, y, Direction.Left);
							
						}
					} else if(oldField[x][y].getDirection() == Direction.Up){ // Head to Up
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
							
							References.setCoord(x, y - 1, Direction.Up);
							
						}
					} else if(oldField[x][y].getDirection() == Direction.Down){ // Head to Down
						if(y + 1 > References.y - 1){
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
							
							References.setCoord(x, y + 1, Direction.Down);
						}
					}
					break;
				}
			}
		}
		
		// Body
		for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				if(oldField[x][y].getSnakeType() == SnakeType.Body){ // Body
					
					newField[x][y] = oldField[x][y];
					
					/*Direction bodyDir = oldField[x][y].getDirection(); 
					
					if(bodyDir == Direction.Down){ // Едем вниз
						if(!isBorder(x, y, Direction.Up) && oldField[x][y - 1].getType() == Type.Snake){ // Сверху нас змея
							if(!isBorder(x, y, Direction.Down) && oldField[x][y + 1].getType() == Type.Snake){ // Снизу змея
								newField[x][y + 1].setDirection(Direction.Down);
								newField[x][y + 1].setImg(Pathes.Snake_Vertical);
							} else if(!isBorder(x, y, Direction.Left) && oldField[x - 1][y].getType() == Type.Snake){ // Слево змея
								newField[x][y + 1].setDirection(Direction.Left);
								newField[x][y + 1].setImg(Pathes.Snake_Angel_4);
							} else if(!isBorder(x, y, Direction.Right) && oldField[x + 1][y].getType() == Type.Snake){ // Справо змея
								newField[x][y + 1].setDirection(Direction.Right);
								newField[x][y + 1].setImg(Pathes.Snake_Angel_1);
							}
							
							newField[x][y + 1].setSnakeType(oldField[x][y].getSnakeType());
							newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y + 1].setType(Type.Snake);
							
						}
					} else if(bodyDir == Direction.Up){ // Едем вверх
						if(!isBorder(x, y, Direction.Down) && oldField[x][y + 1].getType() == Type.Snake){ // Снизу нас змея
							if(!isBorder(x, y, Direction.Up) && oldField[x][y - 1].getType() == Type.Snake){ // Сверху змея
								newField[x][y - 1].setDirection(Direction.Up);
								newField[x][y - 1].setImg(Pathes.Snake_Vertical);
							} else if(!isBorder(x, y, Direction.Left) && oldField[x - 1][y].getType() == Type.Snake){ // Слево змея
								newField[x][y - 1].setDirection(Direction.Left);
								newField[x][y - 1].setImg(Pathes.Snake_Angel_3);
							} else if(!isBorder(x, y, Direction.Right) && oldField[x + 1][y].getType() == Type.Snake){ // Справо змея
								newField[x][y - 1].setDirection(Direction.Right);
								newField[x][y - 1].setImg(Pathes.Snake_Angel_2);
							}
							
							newField[x][y - 1].setSnakeType(oldField[x][y].getSnakeType());
							newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y - 1].setType(Type.Snake);
							
						}
					} else if(bodyDir == Direction.Left){ // Едем влево
						if(!isBorder(x, y, Direction.Left) && oldField[x + 1][y].getType() == Type.Snake){ // Справа нас змея
							if(!isBorder(x, y, Direction.Up) && oldField[x][y - 1].getType() == Type.Snake){ // Сверху змея
								newField[x - 1][y].setDirection(Direction.Up);
								newField[x - 1][y].setImg(Pathes.Snake_Angel_1);
							} else if(!isBorder(x, y, Direction.Left) && oldField[x - 1][y].getType() == Type.Snake){ // Слево змея
								newField[x - 1][y].setDirection(Direction.Left);
								newField[x - 1][y].setImg(Pathes.Snake_Horisontal);
							}else if(!isBorder(x, y, Direction.Down) && oldField[x][y + 1].getType() == Type.Snake){ // Снизу змея
								newField[x - 1][y].setDirection(Direction.Down);
								newField[x - 1][y].setImg(Pathes.Snake_Angel_2);
							}
							
							newField[x - 1][y].setSnakeType(oldField[x][y].getSnakeType());
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							
						}
					} else if(bodyDir == Direction.Right){ // Едем вправо
						if(!isBorder(x, y, Direction.Right) && oldField[x + 1][y].getType() == Type.Snake){ // Слева от нас змея
							if(!isBorder(x, y, Direction.Up) && oldField[x][y - 1].getType() == Type.Snake){ // Сверху змея
								newField[x + 1][y].setDirection(Direction.Up);
								newField[x + 1][y].setImg(Pathes.Snake_Angel_4);
							} else if(!isBorder(x, y, Direction.Down) && oldField[x][y + 1].getType() == Type.Snake){ // Снизу змея
								newField[x + 1][y].setDirection(Direction.Down);
								newField[x + 1][y].setImg(Pathes.Snake_Angel_3);
							} else if(!isBorder(x, y, Direction.Right) && oldField[x + 1][y].getType() == Type.Snake){ // Справо змея
								newField[x + 1][y].setDirection(Direction.Right);
								newField[x + 1][y].setImg(Pathes.Snake_Horisontal);
							}
							
							newField[x + 1][y].setSnakeType(oldField[x][y].getSnakeType());
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							
						}
					}*/
					
				}
			}
		}
		
		// Tail
		for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				if(oldField[x][y].getSnakeType() == SnakeType.Tail){ // Tail
					
					Direction tailDirect = oldField[x][y].getDirection();
					
					System.out.println(tailDirect);
					
					if(tailDirect == Direction.Left){ // Tail -> Left
						Direction oldDirect = oldField[x - 1][y].getDirection();
						if(oldDirect == Direction.Down){
							newField[x - 1][y].setSnakeType(SnakeType.Tail);
							newField[x - 1][y].setImg(Pathes.Snake_Down);
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							newField[x - 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Left){
							newField[x - 1][y].setSnakeType(SnakeType.Tail);
							newField[x - 1][y].setImg(Pathes.Snake_Left);
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							newField[x - 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Up){
							newField[x - 1][y].setSnakeType(SnakeType.Tail);
							newField[x - 1][y].setImg(Pathes.Snake_Up);
							newField[x - 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x - 1][y].setType(Type.Snake);
							newField[x - 1][y].setDirection(oldDirect);
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
							newField[x][y + 1].setSnakeType(SnakeType.Tail);
							newField[x][y + 1].setImg(Pathes.Snake_Left);
							newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y + 1].setType(Type.Snake);
							newField[x][y + 1].setDirection(oldDirect);
						} else if(oldDirect == Direction.Right){
							newField[x][y + 1].setSnakeType(SnakeType.Tail);
							newField[x][y + 1].setImg(Pathes.Snake_Right);
							newField[x][y + 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y + 1].setType(Type.Snake);
							newField[x][y + 1].setDirection(oldDirect);
						}
					} else if(tailDirect == Direction.Right){ // Tail -> Right
						Direction oldDirect = oldField[x + 1][y].getDirection();
						
						if(oldDirect == Direction.Down){
							newField[x + 1][y].setSnakeType(SnakeType.Tail);
							newField[x + 1][y].setImg(Pathes.Snake_Down);
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							newField[x + 1][y].setDirection(oldDirect);
							
						} else if(oldDirect == Direction.Right){
							newField[x + 1][y].setSnakeType(SnakeType.Tail);
							newField[x + 1][y].setImg(Pathes.Snake_Right);
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							newField[x + 1][y].setDirection(oldDirect);
						} else if(oldDirect == Direction.Up){
							newField[x + 1][y].setSnakeType(SnakeType.Tail);
							newField[x + 1][y].setImg(Pathes.Snake_Up);
							newField[x + 1][y].setSpeed(oldField[x][y].getSpeed());
							newField[x + 1][y].setType(Type.Snake);
							newField[x + 1][y].setDirection(oldDirect);
						}
					} else if(tailDirect == Direction.Up){ // Tail -> Up
						Direction oldDirect = oldField[x][y - 1].getDirection();
						if(oldDirect == Direction.Right){
							newField[x][y - 1].setSnakeType(SnakeType.Tail);
							newField[x][y - 1].setImg(Pathes.Snake_Right);
							newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y - 1].setType(Type.Snake);
							newField[x][y - 1].setDirection(oldDirect);
						} else if(oldDirect == Direction.Left){
							newField[x][y - 1].setSnakeType(SnakeType.Tail);
							newField[x][y - 1].setImg(Pathes.Snake_Left);
							newField[x][y - 1].setSpeed(oldField[x][y].getSpeed());
							newField[x][y - 1].setType(Type.Snake);
							newField[x][y - 1].setDirection(oldDirect);
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
					break;
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
