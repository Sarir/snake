package ru.Snake.game.components;


public class Rect {
	private String img = null;
	private int speed = 0;
	private Type type = Type.Void;
	private Direction direction = Direction.Null;
	private SnakeType snakeType = SnakeType.Null;
	private int x = 0, y = 0;
	
	public Rect(String img, int speed, Type type, Direction direction, SnakeType snakeType, int x, int y){
		this.img = img;
		this.speed = speed;
		this.type = type;
		this.direction = direction;
		this.snakeType = snakeType;
		this.x = x;
		this.y = y;
	}
	
	public Rect(){}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public void setImg(String img){
		this.img = img;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public int getSpeed(){
		return this.speed;
	}
	
	public String getImg(){
		return this.img;
	}
	
	public Type getType(){
		return this.type;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	public Direction getDirection(){
		return this.direction;
	}
	
	public void setSnakeType(SnakeType snakeType){
		this.snakeType = snakeType;
	}
	
	public SnakeType getSnakeType(){
		return this.snakeType;
	}
	
	public void setCoords(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int[] getCoords(){
		int[] coords = {this.x, this.y};
		return coords;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
