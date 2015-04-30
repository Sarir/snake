package ru.Snake.game.components;

import java.io.File;

public class Rect {
	private String img = null;
	private int speed = 0;
	private Type type = Type.Void;
	private Direction direction = Direction.Null;
	private SnakeType snakeType = SnakeType.Null;
	
	public Rect(String img, int speed, Type type, Direction direction, SnakeType snakeType){
		this.img = img;
		this.speed = speed;
		this.type = type;
		this.direction = direction;
		this.snakeType = snakeType;
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
}
