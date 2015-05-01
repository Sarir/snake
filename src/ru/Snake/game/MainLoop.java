package ru.Snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

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
		@SuppressWarnings("unused")
		Input keyboard = new Input(); // Keyboard
		// Loop
		while(true){
			draw();
			gameLogic();
			System.out.println("============================");
			System.out.println("ArraySize: " + References.snake.size());
			for(int i = 0; i < References.snake.size(); i++){
				System.out.println("SnType: " + References.snake.get(i).getSnakeType());
				System.out.println("X: " + References.snake.get(i).getX());
				System.out.println("Y: " + References.snake.get(i).getY());
			}
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
		//SnakeMain.imageField.update(SnakeMain.imageField.getGraphics());
		SnakeMain.frame.update(SnakeMain.frame.getGraphics());
	}
	
	private void startGame(){
		for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				SnakeMain.field[x][y] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null, x, y);
			}
		}
		
		References.snake.add(new Rect(Pathes.Snake_Right, 0, Type.Snake, Direction.Right, SnakeType.Tail, 0, 0)); // Tail
		References.snake.add(new Rect(Pathes.Snake_Horisontal, 0, Type.Snake, Direction.Right, SnakeType.Body, 1, 0));
		References.snake.add(new Rect(Pathes.Snake_Horisontal, 0, Type.Snake, Direction.Right, SnakeType.Body, 2, 0));
		References.snake.add(new Rect(Pathes.Snake_Left, 0, Type.Snake, Direction.Right, SnakeType.Head, 3, 0)); // Head
		
		for(int i = 0; i < References.snake.size(); i++){
			SnakeMain.field[References.snake.get(i).getX()][References.snake.get(i).getY()] = References.snake.get(i);
		}
		/*// Head
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
		SnakeMain.field[1][1].setImg(Pathes.Snake_Right);*/
	}
	
	private void gameLogic(){
		Rect[][] newField = new Rect[References.x][References.y];
		
		for(int i = 0; i < References.x; i++){
			for(int j = 0; j < References.y; j++){
				newField[i][j] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null, 0, 0);
			}
		}
		
		ArrayList<Rect> snake = new ArrayList<Rect>();
		
		for(int i = 0; i < References.snake.size(); i++){
			if(References.snake.get(i).getSnakeType() == SnakeType.Tail && i == 0){
				String img = null;
				int speed = References.snake.get(i + 1).getSpeed();
				Type type = References.snake.get(i + 1).getType();
				Direction direction = References.snake.get(i + 1).getDirection();
				SnakeType snakeType = SnakeType.Tail;
				int x = References.snake.get(i + 1).getX();
				int y = References.snake.get(i + 1).getY();
				
				if(!isBorder(x, y, Direction.Up) && direction == Direction.Up){
					img = Pathes.Snake_Up;
				} else if(!isBorder(x, y, Direction.Down) && direction == Direction.Down){
					img = Pathes.Snake_Down;
				} else if(!isBorder(x, y, Direction.Right) && direction == Direction.Right){
					img = Pathes.Snake_Right;
				} else if(!isBorder(x, y, Direction.Left) && direction == Direction.Left){
					img = Pathes.Snake_Left;
				}
				
				snake.add(i, new Rect(img, speed, type, direction, snakeType, x, y));
			}
			
			if(References.snake.get(i).getSnakeType() == SnakeType.Body){
				String img = null;
				int speed = References.snake.get(i + 1).getSpeed();
				Type type = References.snake.get(i + 1).getType();
				Direction direction = References.snake.get(i + 1).getDirection();
				SnakeType snakeType = SnakeType.Body;
				int oldX = References.snake.get(i + 1).getX();
				int oldY = References.snake.get(i + 1).getY();
				
				Direction prevRect = References.snake.get(i - 1).getDirection();
				if(prevRect == Direction.Down){
					if(direction == Direction.Down){
						img = Pathes.Snake_Vertical;
					} else if(direction == Direction.Left){
						img = Pathes.Snake_Angel_4;
					} else if(direction == Direction.Right){
						img = Pathes.Snake_Angel_1;
					}
				} else if(prevRect == Direction.Left){
					if(direction == Direction.Left){
						img = Pathes.Snake_Horisontal;
					} else if(direction == Direction.Down){
						img = Pathes.Snake_Angel_3;
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Angel_4;
					}
				} else if(prevRect == Direction.Right){
					if(direction == Direction.Down){
						img = Pathes.Snake_Angel_3;
					} else if(direction == Direction.Right){
						img = Pathes.Snake_Horisontal;
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Angel_1;
					}
				} else if(prevRect == Direction.Up){
					if(direction == Direction.Left){
						img = Pathes.Snake_Angel_3;
					} else if(direction == Direction.Right){
						img = Pathes.Snake_Angel_2;
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Vertical;
					}
				}
				
				snake.add(new Rect(img, speed, type, direction, snakeType, oldX, oldY));
			}
			
			if(References.snake.get(i).getSnakeType() == SnakeType.Head && i == References.snake.size() - 1){
				String img = null;
				int speed = References.snake.get(i).getSpeed();
				Type type = References.snake.get(i).getType();
				Direction direction = References.snake.get(i).getDirection();
				SnakeType snakeType = SnakeType.Head;
				int oldX = References.snake.get(i).getX();
				int oldY = References.snake.get(i).getY();
				int newX = 0;
				int newY = 0;
				
				if(!isBorder(oldX, oldY, Direction.Up) && direction == Direction.Up){
					newX = oldX;
					newY = oldY - 1;
					img = Pathes.Snake_Down;
				} else if(!isBorder(oldX, oldY, Direction.Down) && direction == Direction.Down){
					newX = oldX;
					newY = oldY + 1;
					img = Pathes.Snake_Up;
				} else if(!isBorder(oldX, oldY, Direction.Right) && direction == Direction.Right){
					newX = oldX + 1;
					newY = oldY;
					img = Pathes.Snake_Left;
				} else if(!isBorder(oldX, oldY, Direction.Left) && direction == Direction.Left){
					newX = oldX - 1;
					newY = oldY;
					img = Pathes.Snake_Right;
				} else if(isBorder(oldX, oldY, Direction.Up) || isBorder(oldX, oldY, Direction.Down) || isBorder(oldX, oldY, Direction.Right) || isBorder(oldX, oldY, Direction.Left)){
					gameOver();
				}
				
				snake.add(new Rect(img, speed, type, direction, snakeType, newX, newY));
			}
		}
		
		References.snake = snake;
		
		for(int i = 0; i < snake.size(); i++){
			newField[snake.get(i).getX()][snake.get(i).getY()] = snake.get(i);
		}
		
		SnakeMain.field = newField;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Head
		/*for(int x = 0; x < References.x; x++){
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
							
							if(oldField[x][y].getDirection() == Direction.Right){ // Едем вправо
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
							}
							
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
					
					//newField[x][y] = oldField[x][y];
					
					Direction bodyDir = oldField[x][y].getDirection(); 
					
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
					}
					
				}
			}
		}
		
		// Tail
		/*for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				if(oldField[x][y].getSnakeType() == SnakeType.Tail){ // Tail
					
					Direction tailDirect = oldField[x][y].getDirection();
					
					System.out.println("TailDirect: " + tailDirect);
					
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
						
						Direction oldDirect = oldField[x][y].getDirection();
						
						System.out.println("OldDirect_Down: " + oldDirect);
						
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
						
						System.out.println("OldDirect_Right: " + oldDirect);
						
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
		}*/
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
