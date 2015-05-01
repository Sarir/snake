package ru.Snake.game;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import ru.Snake.SnakeMain;
import ru.Snake.game.components.Direction;

public class Input extends Thread{
	
	public Input(){
		this.start();
	}
	
	@Override
	public void run() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

	        @SuppressWarnings("static-access")
			@Override
	        public boolean dispatchKeyEvent(KeyEvent ke) {
	        	if(ke.getID() == ke.KEY_PRESSED){
	        		if(ke.getKeyCode() == ke.VK_UP && References.headDirection != Direction.Down){
	        			MainLoop.changeDirection(SnakeMain.field[References.headX][References.headY], Direction.Up);
	        		} else if(ke.getKeyCode() == ke.VK_DOWN && References.headDirection != Direction.Up){
	        			MainLoop.changeDirection(SnakeMain.field[References.headX][References.headY], Direction.Down);
	        		} else if(ke.getKeyCode() == ke.VK_RIGHT && References.headDirection != Direction.Left){
	        			MainLoop.changeDirection(SnakeMain.field[References.headX][References.headY], Direction.Right);
	        		} else if(ke.getKeyCode() == ke.VK_LEFT && References.headDirection != Direction.Right){
	        			MainLoop.changeDirection(SnakeMain.field[References.headX][References.headY], Direction.Left);
	        		}
	        	}
	        	return false;
	        }
	    });
	}
}
