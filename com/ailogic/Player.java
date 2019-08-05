package com.ailogic;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author Maiko Bergman
 * @since 05-08-2019
 * 
 * A class that represents the player, is manually controlable during testing, otherwise
 * it is the agent.
 *
 */
public class Player {

	private int x;
	private int y;
	
	private int width;
	private int height;
	
	private double velocity;
	
	public Player(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void updateVelocity(double velocity) {
		this.velocity += velocity;
	}
	public void update() {
		this.y += this.velocity;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.fillRect(this.x, this.y, this.width, this.height);
	}
}
