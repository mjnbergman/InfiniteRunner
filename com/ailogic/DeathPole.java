package com.ailogic;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * @author Maiko Bergman
 * @since 05-08-2019
 * 
 * The DeathPole class. When the player touches a death pole it dies. 'Dirty'
 * (not entirely logical) inheritance at play for simplicity,
 *
 */
public class DeathPole extends Player{

	public DeathPole(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.velocity = -2.5;
		this.c = Color.GRAY;
		// TODO Auto-generated constructor stub
	} 
	
	public void update() {
		this.x += this.velocity;
	}
}
