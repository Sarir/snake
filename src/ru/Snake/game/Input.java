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
	        		if(ke.getKeyCode() == ke.VK_UP && References.snake.get(References.snake.size() - 1).getDirection() != Direction.Down){
	        			MainLoop.changeDirection(References.snake.get(References.snake.size() - 1), Direction.Up);
	        		} else if(ke.getKeyCode() == ke.VK_DOWN && References.snake.get(References.snake.size() - 1).getDirection() != Direction.Up){
	        			MainLoop.changeDirection(References.snake.get(References.snake.size() - 1), Direction.Down);
	        		} else if(ke.getKeyCode() == ke.VK_RIGHT && References.snake.get(References.snake.size() - 1).getDirection() != Direction.Left){
	        			MainLoop.changeDirection(References.snake.get(References.snake.size() - 1), Direction.Right);
	        		} else if(ke.getKeyCode() == ke.VK_LEFT && References.snake.get(References.snake.size() - 1).getDirection() != Direction.Right){
	        			MainLoop.changeDirection(References.snake.get(References.snake.size() - 1), Direction.Left);
	        		}
	        	}
	        	return false;
	        }
	    });
	}
}
