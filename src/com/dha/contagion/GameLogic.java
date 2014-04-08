package com.dha.contagion;

import java.util.Arrays;

import android.util.Log;

public class GameLogic {
	public static final int DIM = 7;
	private static final Player INIT_PLAYER = Player.BLUE_PLAYER;
	
	public static enum Case {BLUE, RED, BLANK};
	public static enum Player {
		BLUE_PLAYER, RED_PLAYER;
		Case toCase(){  
			return this == BLUE_PLAYER ? Case.BLUE : Case.RED;
		}
		public Player ennemyPlayer() { 
			return this == BLUE_PLAYER ? RED_PLAYER : BLUE_PLAYER;
		};
		public String toString() {
			return this == BLUE_PLAYER ? "BLUE PLAYER" : "RED PLAYER";
		}
	};

	private Case[][] mPlayBoard;
	private Player currentPlayer; 
	Pos	chosenCase;
	private boolean havingChosenCase;
	public GameLogic(){
		playBoardInit();
	}
	private void playBoardInit() {
		mPlayBoard = new Case[DIM][DIM];
		for (Case[] row: mPlayBoard) Arrays.fill(row, Case.BLANK);
		mPlayBoard[0][0] = Case.BLUE;
		mPlayBoard[DIM-1][DIM-1] = Case.BLUE;
		mPlayBoard[0][DIM-1] = Case.RED;
		mPlayBoard[DIM-1][0] = Case.RED;
		chosenCase = new Pos(0,0);
		currentPlayer = INIT_PLAYER;
		havingChosenCase = false;
	}
	
	public Case[][] getPlayBoard(){ return mPlayBoard;}
	
	private void insert(Pos p, Case c){ 
		mPlayBoard[p.x][p.y] = c;
	}
	public void copy(Pos position){
		insert(position, currentPlayer == Player.BLUE_PLAYER ? Case.BLUE : Case.RED);		
	}
	void move(Pos nextPos){
		copy(nextPos);
		insert(chosenCase, Case.BLANK);
	}
	void contamineEnnemy(Pos curPos){
		Case playerCase = currentPlayer.toCase();
		Case ennemyCase = currentPlayer.ennemyPlayer().toCase();
		for (int x = curPos.x - 1; x <= curPos.x + 1; x++){
			if (x < 0 || DIM <= x) continue;
			for (int y = curPos.y - 1; y <= curPos.y + 1; y++){
				if (y < 0 || DIM <= y) continue;
				if (mPlayBoard[x][y] == ennemyCase) mPlayBoard[x][y] = playerCase;
			}
		}
	}
	public void handleEvent(int position) {
		int row = position/DIM;
		int col = position%DIM;
		Case casePressed = mPlayBoard[row][col];
		// TODO
		if (havingChosenCase){
			Pos currentCase = new Pos(row,col);
			int distance = chosenCase.donutDistance(currentCase);
			// pressed on 1 case twice
			if (distance == 0){
				havingChosenCase = false;
				return;
			} 
			if (casePressed == Case.BLANK){
			// pressed on eligible case to copy
				if (distance == 1){ 
					copy(currentCase);
			// pressed on eligible case to move
				} else if (distance == 2){
					move(currentCase); 
				}
				contamineEnnemy(currentCase);
				currentPlayer = currentPlayer.ennemyPlayer();
				havingChosenCase = false;
			} else {
			// pressed on other player case
				if (casePressed == currentPlayer.toCase()){
					chosenCase.assign(row, col);
				} else {
			// pressed on ineligible case to do anything
					havingChosenCase = false;
				}
			}
		} else{
		// no memo at all
			if (casePressed == currentPlayer.toCase()){
				chosenCase.assign(row, col);
				havingChosenCase = true;
			}
		}
	}
}
