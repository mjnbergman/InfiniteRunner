package com.ailogic;



public class Grid extends AbstractGrid{


	
	public Grid(int columns, int rows) {
		super(columns, rows);
	}

	@Override
	public int getStateActionMap(int initialState, int initialAction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActionLegal(int state, int action) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getReward(int initialState, int initialAction) {
		// TODO Auto-generated method stub
		return 0;
	}

}
