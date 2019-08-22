package com.ailogic;

/**
 * 
 * @author Maiko Bergman
 * @since 22-08-2019
 * 
 * Class representing basic Q-learning tables, referred to as grids.
 * Has methods for printing, updating and training these tables.
 *
 */

public abstract class AbstractGrid {

	int columns;
	int rows;
	
	int[][] grid;
	int[][] qGrid;
	
	double gamma = 0.8;
	
	public AbstractGrid() {
		
	}
	
	public AbstractGrid(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
		
		this.grid = new int[rows][columns];
		this.qGrid = new int[rows][columns];
	}
	
	public void setGridValue(int row, int column, int value) {
		this.grid[row][column] = value;
	}
	public int getGridValue(int row, int column) {
		return this.grid[row][column];
	}
	public void fromString(String s) {
		
		String[] rows = s.split("\\n");
		this.columns = rows[0].length();
		this.rows = rows.length;
		
		this.grid = new int[this.rows][this.columns];
		this.qGrid = new int[this.rows][this.columns];
		
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				this.grid[j][i] = 0;
			}
		}
	}
	
	public void printGrid() {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				System.out.print(this.grid[i][j] + " ");
			}
			System.out.println("");
		}
	}
	public void printQGrid() {
		for(int i = 0; i < this.rows; i++) {
			for(int j = 0; j < this.columns; j++) {
				System.out.print(this.qGrid[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * A method that does a training episode for the Q-table. Subclasses should override the
	 * @code(isActionLegal) and @code(getStateActionMap) methods instead of this one. 
	 * This method takes random legal actions from the initialState until it gets to a terminating state
	 * and updates the Q-table accordingly while doing so.
	 * 
	 * @param initialState an integer representing the initialState from which to start
	 * this training episode.
	 */
	public void trainIteration(int initialState) {
		
		if(initialState == -1) {
			initialState = (int)(Math.random() * this.rows);
		}
		
		
		boolean actionChosen = false;
		int action = -1;
		
		// Check if action legal
		
		while(!actionChosen) {
			action = (int)(Math.random() * this.columns);
			if(!isActionLegal(initialState, action)) {
				continue;
			}else {
				actionChosen = true;
			}
			
		}
		
		System.out.println("Training iteration! Chosen initialState: " + initialState + " and action: " + action);
		
		// Obtain next state
		
		int newLocation = getStateActionMap(initialState, action);
		
		System.out.println("New location is: " + newLocation);
		
	//	int nCol = newLocation % (int)Math.sqrt(this.rows);
	//	int nRow = (newLocation - nCol)/(int)Math.sqrt(this.rows);
		
		// Obtain immediate reward
		
		int immediateReward = this.grid[initialState][newLocation];
		
		System.out.println("De immediate reward is: " + immediateReward);
		
		// Update Q-table
		
		int totalReward = (int)(immediateReward + this.gamma * recurseReward(newLocation));
		this.qGrid[initialState][newLocation] = totalReward;
		
		if(this.grid[initialState][newLocation] != 100 && this.grid[initialState][newLocation] != -500) {
			trainIteration(newLocation);
		}
	}
	

	/**
	 * A method which calculates max(Q'(s', \forall a)). This is necessary for the Q update calculations.
	 * It simply iterates over all the possible actions from s' and returns the maximal reward value found by 
	 * taking any of those actions.
	 * @param initialState the state s' for which we should find the optimal future reward
	 * @return the maximum value of a reward obtainable by taking any action from s'
	 */
	public int recurseReward(int initialState) {
		int maxValue = -100000;
		
		for(int action = 0; action < this.columns; action++) {
			if(!isActionLegal(initialState, action)){
				continue;
			}else {
				int potNewState = getStateActionMap(initialState, action);
	//			int pNewColumns = potNewState % (int)Math.sqrt(this.rows);
	//			int pNewRows = (potNewState - pNewColumns)/(int)Math.sqrt(this.rows);
				
				if(this.qGrid[initialState][potNewState] > maxValue) {
					maxValue = this.qGrid[initialState][potNewState];
				}
			}
		}
		System.out.println("De max value is: " + maxValue);
		return maxValue;
	}
	
	/*
	 * These abstract methods have to be implemented by subclasses.
	 */
	
	/**
	 * 
	 * @param initialState an integer representing the current state from which an action is taken
	 * @param initialAction an integer representing the action taken
	 * @return a resulting state, obtained when taking @code(initialAction) from @code(initialState)
	 */
	public abstract int getStateActionMap(int initialState, int initialAction);
	
	/**
	 * 
	 * @param state an integer representing the state from which an action is taken
	 * @param action an integer representing the action taken
	 * @return a boolean representing whether or not the taken action is legal from that state.
	 */
	public abstract boolean isActionLegal(int state, int action);
	
	/**
	 * A method that uses the current Q-table to simulate an episode, taking the optimal actions
	 * until it gets to the terminating state.
	 * @param finalState an integer representing the terminating state of the environment
	 */
	public void doTestRun(int finalState) {
		int initialState = (int) (Math.random() * this.rows);
		int nextState = -1;
		
		if(initialState == finalState) {
			return;
		}
		
		System.out.println("Spawning at: " + initialState);
		
		while(initialState != finalState) {
			int maxReward = -100000;
			nextState = initialState;
			
			for(int j = 0; j < this.columns; j++) {
				if(this.qGrid[initialState][j] > maxReward) {
					maxReward = this.qGrid[initialState][j];
					nextState = getStateActionMap(initialState, j);
				}
			}
			
			int tState = initialState;
			initialState = nextState;
			nextState = tState;
			
			System.out.println("Taking next step from " + nextState + " to " + initialState + "! For reward: " + this.grid[nextState][initialState]);
		}
		

	}
	
}
