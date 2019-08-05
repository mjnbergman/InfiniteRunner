package com.graphics;

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
	
	public MainFrame() {
		buildGUI();
	}
	
	private void buildGUI() {
		
		JFrame mFrame = new JFrame("Infinite Runner");
		
		
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setSize(800, 600);
		this.gp = new GraphicsPanel();
		mFrame.add(this.gp);
		
		
		mFrame.setVisible(true);
		
		this.gp.resetPlayer();
	}
}
