package ru.Snake.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ru.Snake.SnakeMain;
import ru.Snake.game.components.Direction;
import ru.Snake.game.components.Field;
import ru.Snake.game.components.Rect;
import ru.Snake.game.components.SnakeType;
import ru.Snake.game.components.Type;


public class MainLoop extends Thread{
	
	private int width, height;
	
	public URL musPickUp = getClass().getResource("/sounds/PickUp.wav");
	public URL musPowerup = getClass().getResource("/sounds/Powerup.wav");
	public URL musMove = getClass().getResource("/sounds/Move.wav");
	
	@SuppressWarnings("deprecation")
	public MainLoop(int x, int y){
		
		width = 16 * (x + 1) + 6;
		height = 16 * (y + 1) + 9;
		
		startGame();
		init();
		SnakeMain.frame.show();
		SnakeMain.menu.hide();
		JOptionPane.showMessageDialog(SnakeMain.frame, "Press \" OK \" to start, after " + References.secondsToStart + " seconds!");
		this.start();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		// start
		try {
			this.sleep(References.secondsToStart * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
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
		SnakeMain.frame.setBounds(100, 100, width, height);
		SnakeMain.frame.setLocationRelativeTo(null);
		SnakeMain.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeMain.frame.setBackground(Color.BLACK);
		SnakeMain.frame.setResizable(false);
		SnakeMain.frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		SnakeMain.imageField = new Field();
		SnakeMain.imageField.setBackground(Color.BLACK);
		SnakeMain.imageField.setDoubleBuffered(true);
		SnakeMain.frame.getContentPane().add(SnakeMain.imageField, BorderLayout.CENTER);
		
		draw();
	}
	
	private void draw(){
		SnakeMain.imageField.update(SnakeMain.imageField.getGraphics());
	}
	
	private void startGame(){
		for(int x = 0; x < References.x; x++){
			for(int y = 0; y < References.y; y++){
				SnakeMain.field[x][y] = new Rect(null, 0, Type.Void, Direction.Null, SnakeType.Null, x, y);
			}
		}
		
		References.snake.add(new Rect(Pathes.Snake_Right, 0, Type.Snake, Direction.Right, SnakeType.Tail, 0, 0)); // Tail
		References.snake.add(new Rect(Pathes.Snake_Horisontal, 0, Type.Snake, Direction.Right, SnakeType.Body, 1, 0)); // Body
		References.snake.add(new Rect(Pathes.Snake_Left, 0, Type.Snake, Direction.Right, SnakeType.Head, 2, 0)); // Head
		
		for(int i = 0; i < References.snake.size(); i++){
			SnakeMain.field[References.snake.get(i).getX()][References.snake.get(i).getY()] = References.snake.get(i);
		}
		
		addApple();
	}
	
	private void gameLogic(){
		snakeLogic();
		appleLogic();
	}
	
	private void appleLogic(){
		if(References.apples.size() != 0){
			for(int i = 0; i < References.apples.size(); i++){
				if(References.apples.get(i).getX() == References.snake.get(0).getX() &&
				   References.apples.get(i).getY() == References.snake.get(0).getY()){
					References.apples.remove(i);
					addApple();
					References.grow += References.appleIncBy;
					References.score++;
					References.gameTick -= References.decrGameTick;
					new SoundSystem().playSound(musPickUp);
				}
			}
		}
	}
	
	private void addApple(){
		Random rand = new Random();
		int appleX = rand.nextInt(References.x - 1);
		int appleY = rand.nextInt(References.y - 1);
		
		References.apples.add(new Rect(Pathes.apple, 0, Type.Apple, Direction.Null, SnakeType.Null, appleX, appleY));
	}
	
	private void snakeLogic(){
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
				if(References.grow > 0){
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
						} else if(direction == Direction.Up){
							img = Pathes.Snake_Vertical;
						}
					} else if(prevRect == Direction.Left){
						if(direction == Direction.Left){
							img = Pathes.Snake_Horisontal;
						} else if(direction == Direction.Down){
							img = Pathes.Snake_Angel_2;
						} else if(direction == Direction.Up){
							img = Pathes.Snake_Angel_1;
						} else if(direction == Direction.Right){
							img = Pathes.Snake_Horisontal;
						}
					} else if(prevRect == Direction.Right){
						if(direction == Direction.Down){
							img = Pathes.Snake_Angel_3;
						} else if(direction == Direction.Right){
							img = Pathes.Snake_Horisontal;
						} else if(direction == Direction.Up){
							img = Pathes.Snake_Angel_4;
						} else if(direction == Direction.Left){
							img = Pathes.Snake_Horisontal;
						}
					} else if(prevRect == Direction.Up){
						if(direction == Direction.Left){
							img = Pathes.Snake_Angel_3;
						} else if(direction == Direction.Right){
							img = Pathes.Snake_Angel_2;
						} else if(direction == Direction.Up){
							img = Pathes.Snake_Vertical;
						} else if(direction == Direction.Down){
							img = Pathes.Snake_Vertical;
						}
					}
					
					References.grow--;
					snake.add(new Rect(img, speed, type, direction, snakeType, oldX, oldY));
					new SoundSystem().playSound(musPowerup);
				}
				
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
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Vertical;
					}
				} else if(prevRect == Direction.Left){
					if(direction == Direction.Left){
						img = Pathes.Snake_Horisontal;
					} else if(direction == Direction.Down){
						img = Pathes.Snake_Angel_2;
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Angel_1;
					} else if(direction == Direction.Right){
						img = Pathes.Snake_Horisontal;
					}
				} else if(prevRect == Direction.Right){
					if(direction == Direction.Down){
						img = Pathes.Snake_Angel_3;
					} else if(direction == Direction.Right){
						img = Pathes.Snake_Horisontal;
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Angel_4;
					} else if(direction == Direction.Left){
						img = Pathes.Snake_Horisontal;
					}
				} else if(prevRect == Direction.Up){
					if(direction == Direction.Left){
						img = Pathes.Snake_Angel_3;
					} else if(direction == Direction.Right){
						img = Pathes.Snake_Angel_2;
					} else if(direction == Direction.Up){
						img = Pathes.Snake_Vertical;
					} else if(direction == Direction.Down){
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
					gameOver(true);
				}
				
				snake.add(new Rect(img, speed, type, direction, snakeType, newX, newY));
			}
		}
		
		References.snake = snake;
		
		for(int i = 0; i < snake.size(); i++){
			newField[snake.get(i).getX()][snake.get(i).getY()] = snake.get(i);
		}
		
		SnakeMain.field = newField;
		
		
		for(int i = 0; i < References.snake.size() - 1; i++){
			int x = References.snake.get(i).getX();
			int y = References.snake.get(i).getY();
			
			if(x == References.snake.get(References.snake.size() - 1).getX() && y == References.snake.get(References.snake.size() - 1).getY()){
				gameOver(true);
			}
		}
		
		new SoundSystem().playSound(musMove);
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
	
	@SuppressWarnings("deprecation")
	public static void gameOver(boolean b){
		if(b){
			JOptionPane.showMessageDialog(null, "Game Over! Your score: " + References.score);
		}
		SnakeMain.frame.dispose();
		SnakeMain.menu.show();
		
		SnakeMain.game.stop();
	}
}
