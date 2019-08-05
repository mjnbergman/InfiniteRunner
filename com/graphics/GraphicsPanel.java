package com.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.ailogic.Player;

/**
 * 
 * @author Maiko Bergman
 * @since 05-08-2019
 * 
 * Class that represents a JPanel that the game will be drawn to, very simplistic
 * as the game is not the point of this project, let alone the graphics
 *
 */
public class GraphicsPanel extends JPanel{
	
	private Player p1;
	
	public GraphicsPanel() {
		this.p1 = new Player(this.getWidth()/2, this.getHeight()/2, 50, 100);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Paint the background white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Paint the floor
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, this.getHeight()/2, this.getWidth(), this.getHeight()/20);
		
		p1.draw(g2d);
	}
	
	public void resetPlayer() {
		this.p1 = new Player(this.getWidth()/2 - 25, this.getHeight()/2 - 100, 50, 100);
	}
}
