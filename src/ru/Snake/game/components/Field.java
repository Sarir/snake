package ru.Snake.game.components;

import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ru.Snake.SnakeMain;

@SuppressWarnings("serial")
public class Field extends JPanel {
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
		for(int i = 0; i < SnakeMain.x; i++){
			for(int j = 0; j < SnakeMain.y; j++){
				if(SnakeMain.field[i][j].getType() != Type.Void){
					if(SnakeMain.field[i][j].getType() == Type.Apple){
						try {
							
							g.drawImage(ImageIO.read(getClass().getResource(SnakeMain.field[i][j].getImg())), i * 16 + 4, j * 16 + 4, this);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						try {
							g.drawImage(ImageIO.read(getClass().getResource(SnakeMain.field[i][j].getImg())), i * 16, j * 16, this);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
    }
}    
