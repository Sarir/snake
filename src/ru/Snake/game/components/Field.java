package ru.Snake.game.components;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ru.Snake.game.References;

@SuppressWarnings("serial")
public class Field extends JPanel {
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        for(int i = 0; i < References.snake.size(); i++){
			try {		
				g.drawImage(ImageIO.read(getClass().getResource(References.snake.get(i).getImg())), References.snake.get(i).getX() * 16, References.snake.get(i).getY() * 16, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        for(int i = 0; i < References.apples.size(); i++){
			try {		
				g.drawImage(ImageIO.read(getClass().getResource(References.apples.get(i).getImg())), References.apples.get(i).getX() * 16 + 4, References.apples.get(i).getY() * 16 + 4, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}    
