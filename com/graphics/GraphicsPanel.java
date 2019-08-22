package com.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

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
	private Grid gMan;
	private int quantileCount = 50;
	private int score = 0;
	private int maxScore = 0;
	private int episodes = 1;
	private int totalScore = 0;
	private boolean training = true;
	
	public GraphicsPanel() {
		this.p1 = new Player(this.getWidth()/2, this.getHeight()/2, 50, 100);
		this.poles = new ArrayList<>();
		this.gMan = new Grid(2, quantileCount);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Paint the background white
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Paint the floor
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, this.getHeight()/2, this.getWidth(), this.getHeight()/20);
		
		g2d.drawString("Score: " + this.score, 100, 100);
		g2d.drawString("Max/High score: " + this.maxScore, 100, 125);
		g2d.drawString("Training: " + this.training, 100, 150);
		g2d.drawString("Average score: " + (double)((double)totalScore/episodes), 100, 175);
		
		// Hit checking
		
		boolean killIt = false;
		
		int action = 0;
		
		gMan.update(p1.isFlying(this.getHeight()/2 - 100), killIt);
		
		if(training) {
			action = gMan.trainIteration(getNearestNeighbourDistance());
		}else {
			action = gMan.getOptimalAction(getNearestNeighbourDistance());
		}
		
		if(action == 1) {
			upEvent();
		}
		
		Iterator<DeathPole> it = this.poles.iterator();
		
		while(it.hasNext()) {
			
			DeathPole dp = it.next();
			
			if(touchesPlayer(dp)) {
				killIt = true;
				gMan.update(false, true);
			}
			
			dp.update();
			dp.draw(g2d);
			
			// Score updaten als je een pole passeert
			if(dp.getX() + dp.getWidth() < p1.getX() && !dp.passed) {
				score++;
				dp.passed = true;
			}
			
			if(dp.getX() + dp.getWidth() < 0) {
				it.remove();
			}
		}
		
		p1.update(this.getHeight()/2 - 100);
		gMan.update(p1.isFlying(this.getHeight()/2 - 100), killIt);
		p1.draw(g2d);
		
		if(killIt) {
			if(score > maxScore) {
				maxScore = score;
				gMan.updateScore(score);
			}
			totalScore += score;
			resetPlayer();
			episodes++;
		}
	}
	
	public int getNearestNeighbourDistance() {
		int distance = 2000;
		for(DeathPole dp : this.poles) {
			if(dp.getX() - p1.getX() < distance && dp.getX() - p1.getX() >= 0) {
				distance = dp.getX() - p1.getX();
			}
		}
		return Math.round(distance/quantileCount);
	}
	
	public boolean touchesPlayer(DeathPole dp) {
		Rectangle r1 = new Rectangle(dp.getX(), dp.getY(), dp.getWidth(), dp.getHeight());
		Rectangle r2 = new Rectangle(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
		
		return r1.intersects(r2);
	}
	
	public void upEvent() {
		this.p1.updateVelocity(-5.0, this.getHeight()/2 - 100);
	}public void playerUpEvent() {
		this.resetPlayer();
		this.training = !this.training;
	}
	public void resetPlayer() {
		
		this.score = 0;
		this.p1 = new Player(this.getWidth()/2 - 25, this.getHeight()/2 - 100, 50, 100);
		this.poles = new ArrayList<>();
		
		
		// We want there to be an end to this game instead of it being a truly infinite runner,
		// as we want to be able to leave it and know when it has completed the challenge.
		// Therefore we do not generate an infinite amount of death poles but only 1000.
		
		int initialPoleX = 500;
		
		for(int i = 0; i < 1000; i++) {
			int poleDistance = (int)(150 + (Math.random() * 500));
			initialPoleX += poleDistance;
			DeathPole dp = new DeathPole(initialPoleX, this.getHeight()/2 - 40, 25, 40);
			this.poles.add(dp);
		}
	}
}
