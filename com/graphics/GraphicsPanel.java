package com.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.ailogic.*;

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
	private ArrayList<DeathPole> poles;
	private int score = 0;
	
	public GraphicsPanel() {
		this.p1 = new Player(this.getWidth()/2, this.getHeight()/2, 50, 100);
		this.poles = new ArrayList<>();
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Paint the background white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Paint the floor
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, this.getHeight()/2, this.getWidth(), this.getHeight()/20);
		
		// Hit checking
		
		boolean killIt = false;
		
		for(DeathPole dp : this.poles) {
			
			if(touchesPlayer(dp)) {
				killIt = true;
			}
			
			dp.update();
			dp.draw(g2d);
		}
		
		p1.update(this.getHeight()/2 - 100);
		p1.draw(g2d);
		
		if(killIt) {
			resetPlayer();
		}
	}
	
	public boolean touchesPlayer(DeathPole dp) {
		Rectangle r1 = new Rectangle(dp.getX(), dp.getY(), dp.getWidth(), dp.getHeight());
		Rectangle r2 = new Rectangle(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
		
		return r1.intersects(r2);
	}
	
	public void upEvent() {
		this.p1.updateVelocity(-5.0, this.getHeight()/2 - 100);
	}
	public void resetPlayer() {
		
		this.score = 0;
		this.p1 = new Player(this.getWidth()/2 - 25, this.getHeight()/2 - 100, 50, 100);
		this.poles = new ArrayList<>();
		
		
		// We want there to be an end to this game instead of it being a truly infinite runner,
		// as we want to be able to leave it and know when it has completed the challenge.
		// Therefore we do not generate an infinite amount of death poles but only 1000.
		
		int initialPoleX = 1000;
		
		for(int i = 0; i < 1000; i++) {
			int poleDistance = (int)(150 + (Math.random() * 800));
			initialPoleX += poleDistance;
			DeathPole dp = new DeathPole(initialPoleX, this.getHeight()/2 - 40, 25, 40);
			this.poles.add(dp);
		}
	}
}
