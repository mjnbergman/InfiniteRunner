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
	
	protected Color c = Color.RED;

	protected int x;
	protected int y;
	
	protected int width;
	protected int height;
	
	protected double velocity;
	
	public Player(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public boolean isFlying(double groundHeight) {
		return (this.y < groundHeight);
	}
	
	public void updateVelocity(double velocity, double groundHeight) {
		
		if(this.y < groundHeight) {
			return;
		}
		
		this.velocity += velocity;
	}
	public void update(double groundHeight) {
		
		if(this.velocity > 0 && this.y + velocity > groundHeight) {
			this.velocity = 0;
			this.y = (int)groundHeight;
		}
		
		this.y += this.velocity;
		this.velocity += (this.y < groundHeight ? 0.2 : 0);
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(this.c);
		g2d.fillRect(this.x, this.y, this.width, this.height);
	}
}
