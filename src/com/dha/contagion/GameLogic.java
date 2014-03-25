package com.dha.contagion;

import java.util.Arrays;


public class GameLogic {
	public static enum Case {BLUE, RED, BLANK};
	public static final int DIM = 7;
	private Case[][] PlayBoard;
	
	public GameLogic(){
		playBoardInit();
	}

	private void playBoardInit() {
		PlayBoard = new Case[DIM][DIM];
		for (Case[] row: PlayBoard) Arrays.fill(row, Case.BLANK);
		PlayBoard[0][0] = Case.BLUE;
		PlayBoard[DIM-1][DIM-1] = Case.BLUE;
		PlayBoard[0][DIM-1] = Case.RED;
		PlayBoard[DIM-1][0] = Case.RED;
	}
	
	public Case[][] getPlayBoard(){ return PlayBoard;}
}
