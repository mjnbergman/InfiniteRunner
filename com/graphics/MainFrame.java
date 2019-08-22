package com.graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * 
 * @author Maiko Bergman
 * @since 05-08-2019
 * 
 * The class containing the main GUI
 *
 */
public class MainFrame {

	private GraphicsPanel gp;
	private boolean pressing = false;
	
	public MainFrame() {
		buildGUI();
	}
	
	private void buildGUI() {
		
		JFrame mFrame = new JFrame("Infinite Runner");
		
		
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setSize(800, 600);
		this.gp = new GraphicsPanel();
		
		this.gp.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println("KEy pressed!");
				if(arg0.getKeyCode() == arg0.VK_UP) {
					
					if(!pressing) {
						gp.playerUpEvent();
					}
					pressing = true;
					
					System.out.println("Up pressed!");
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == arg0.VK_UP) {
					pressing = false;
				}
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mFrame.add(this.gp);
		
		
		mFrame.setVisible(true);
		
		this.gp.resetPlayer();
		this.gp.requestFocus();
		
		this.gameLoop();
	}
	
	// A bad practice, but very simple game loop
	private void gameLoop() {
		while(true) {
			this.gp.repaint();
			this.gp.requestFocus();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
