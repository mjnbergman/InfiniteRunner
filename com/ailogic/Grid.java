package com.ailogic;



public class Grid extends AbstractGrid{

	private boolean flying = false;
	private boolean dead = false;
	
	public Grid(int columns, int rows) {
		super(columns, rows);
	}
	
	public void updateScore(int score) {
		this.explorationRate = 1.0 - score/10f;
	}
	
	public void update(boolean flying, boolean dead) {
		this.flying = flying;
		this.dead = dead;
	}

	@Override
	public int getStateActionMap(int initialState, int initialAction) {
		if(initialState + 1 >= this.rows) {
			return 0;
		}else {
			return initialState++;
		}
	}

	@Override
	public boolean isActionLegal(int state, int action) {
		if(action == 1 && flying) {
			return false;
		}
		return true;
	}

	@Override
	public int getReward(int initialState, int initialAction) {
		if(!dead && initialAction == 1) {
			return 1;
		}else if(!dead && initialAction == 0 && !flying){
			return 5;
		}else if(!dead && initialAction == 0 && flying){
			return 0;
		}else {
			return -1000;
		}
	}

}
