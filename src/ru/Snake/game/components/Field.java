package ru.Snake.game.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ru.Snake.SnakeMain;
import ru.Snake.game.Pathes;
import ru.Snake.game.References;

@SuppressWarnings("serial")
public class Field extends JPanel {
	
    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
    	//SnakeMain.frame.repaint();
       for(int i = 0; i < References.snake.size(); i++){
			try {		
				g.drawImage(ImageIO.read(getClass().getResource(References.snake.get(i).getImg())), References.snake.get(i).getX() * 16, References.snake.get(i).getY() * 16, this);
				//SnakeMain.imageField.repaint(References.snake.get(i).getX(), References.snake.get(i).getY(), 16, 16);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
       // Apple
       
        for(int i = 0; i < References.apples.size(); i++){
			try {		
				g.drawImage(ImageIO.read(getClass().getResource(References.apples.get(i).getImg())), References.apples.get(i).getX() * 16 + 4, References.apples.get(i).getY() * 16 + 4, this);
				//SnakeMain.imageField.repaint(References.apples.get(i).getX(), References.apples.get(i).getY(), 16, 16);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        // Void 
        
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		for(int k = 0; k < References.tailX; k++) {
		    for(int l = 0; l < References.tailY; l++) {
		        image.setRGB(k, l, Color.BLACK.getRGB());
		    }
		}
        
        try {
        	if(References.started){
        		g.drawImage(ImageIO.read(getClass().getResource(Pathes.blackRect)), References.tailX * 16, References.tailY * 16, SnakeMain.gameFrame);
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}    
    }
}    
