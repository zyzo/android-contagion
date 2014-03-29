package com.dha.contagion;

import android.util.Log;

import com.dha.contagion.GameLogic.Case;
public class AI {

	private GameLogic gameLogic=new GameLogic();

	@SuppressWarnings("static-access")
	public void getBlankCaseSurroundedByHighBlank(GameLogic.Case[][] mplayBoard,
			Pos thisPos,
			int distance,
			Pos posOut,
			int maxCaseSurrounded){
		int somme=0;
		int max=0;
		Pos pos1= new Pos(7,7);
		Pos pos2= new Pos(7,7);

		for (int i=0;i<gameLogic.DIM;i++){
			for(int j=0;j<gameLogic.DIM;j++){
				somme=0;
				pos1.assign(i, j);
				int dist=(pos1.donutDistance(thisPos));
				if (dist==distance && (mplayBoard[i][j]==Case.BLANK) ) {   
					for (int k=0;k<gameLogic.DIM;k++){
						for(int m=0;m<gameLogic.DIM;m++){
							pos2.assign(k, m);
							int dist2=pos2.donutDistance(pos1);
							if ((dist2==1) && (mplayBoard[i][j]==Case.BLUE)) {
								somme+=1;
								if (somme> max)  {
									posOut.assign(i, j);
									max=somme;
									maxCaseSurrounded=max;

								}}}}}}}



		if (max==0) {
			posOut.assign(7,7);
			maxCaseSurrounded=0;
		}

	}
	///////////////////////////////////////

	@SuppressWarnings("static-access")
	public void meilleurcasevide(GameLogic.Case[][] mplayBoard,
			Pos thisPos,
			Pos CloserBluePos,
			Pos CloserBlankPos,
			int distance,int max, int min){


		Pos pos1= new Pos(7,7);
		
		CloserBlankPos.assign(7,7);
		CloserBluePos.assign(7,7);

		min=GameLogic.DIM;
		max=0;

		for (int i=0;i<gameLogic.DIM;i++){
			for(int j=0;j<gameLogic.DIM;j++){
				pos1.assign(i, j);

				if (((pos1.donutDistance(thisPos)<min) || pos1.donutDistance(thisPos)<=3) && mplayBoard[i][i]==Case.BLUE ){

					if (pos1.donutDistance(thisPos)<min){
						min=pos1.donutDistance(thisPos);
						CloserBluePos.assign(i, j);
					}

					if (min<=3){
						getBlankCaseSurroundedByHighBlank(mplayBoard, thisPos, distance, CloserBlankPos, max);
					}

				}
			}
		}
		Log.d("meilleurcase vide","meilleurcasevide="+CloserBlankPos.x+" ,"+ CloserBlankPos.y);
	}
	////////////////////////////////////

	@SuppressWarnings("static-access")
	void ChoosePos(GameLogic game,
			Pos CloserBluePos,
			Pos ChosenPos,
			Pos CloserBlankPos,
			int min){

		int minX=GameLogic.DIM;
		int max=0;
		int max1=0;
		int max2=0;

		Pos pos5= new Pos(7,7);
		Pos postrouve1= new Pos(7,7);
		Pos postrouve2= new Pos(7,7);
		Pos posI1= new Pos(7,7);
		Pos posI2= new Pos(7,7);
		Pos posX1= new Pos(7,7);
		Pos posX2= new Pos(7,7);
		GameLogic.Case[][] mPlayBoard=game.getPlayBoard();


		min=GameLogic.DIM;

		for (int i=0;i<gameLogic.DIM;i++){
			for(int j=0;j<gameLogic.DIM;j++){
				if (mPlayBoard[i][j]==Case.RED ){
					pos5.assign(i, j);
					meilleurcasevide(mPlayBoard, pos5, posX1, posI1, 2, max2, minX);
					meilleurcasevide(mPlayBoard, pos5, posX2, posI2, 1, max2, minX);


					if (minX<=3 || minX<min){

						if (minX<min ){
							min=minX;
						}
						CloserBluePos.assign(posX1.x,posX1.y);

						if (min<=3) {
							if (max1>=max && max1>=max2 ) {
								max=max1;
								ChosenPos.assign(postrouve1.x, postrouve1.y);
								CloserBlankPos.assign(posI1.x,posI1.y);
							}
							else if( max2>max || max2>max1) {
								max=max2;
								ChosenPos.assign(postrouve2.x, postrouve2.y);
								CloserBlankPos.assign(posI2.x,posI2.y);
							}
							else{
								CloserBluePos.assign(posX1.x, posX1.y);
								if (mPlayBoard[i][j]==Case.RED && pos5.donutDistance(ChosenPos)<=min ){
									ChosenPos.assign(i, j);
								}
							}
						}
					}
				}
			}
		}

	}


	///////////////////////////////////

	void strategie_min_sup_3 (GameLogic gameLogic,
			Pos ChosePos,
			Pos BlankPos,
			int min) {

		GameLogic.Case[][] mPlayBoard= gameLogic.getPlayBoard();
		boolean found=false;
		Pos arrivalPos= new Pos(7,7);

		if ( min>3 ) {

			for (int i=0; i<GameLogic.DIM;i++){
				for(int c=0;(i<GameLogic.DIM) && (!found) ;c++){
					arrivalPos.assign(i, c);
					if (arrivalPos.donutDistance(ChosePos)==1 && mPlayBoard[i][c]==Case.BLANK && ( !found) ){
						gameLogic.copy(arrivalPos);
						found=true;
					}
				}
			}
		}

		else {
			for (int i=0; i<GameLogic.DIM;i++){
				for(int c=0;(i<GameLogic.DIM) && (!found) ;c++){
					arrivalPos.assign(i, c);
					if (arrivalPos.donutDistance(ChosePos)==1 && mPlayBoard[i][c]==Case.BLANK && ( !found) &&
							arrivalPos.donutDistance(BlankPos)<min && arrivalPos.donutDistance(ChosePos)<min)
					{

						gameLogic.move(arrivalPos);
						found=true;
					}

				}
			}
		}
	}
	////////////////////////

	void contremachine(GameLogic gameLogic){
		boolean trouve= false;
		
		Pos departurePos= new Pos(7,7);

		int min=GameLogic.DIM;
		Pos ennemyPos= new Pos(7,7);
		Pos blankPos= new Pos(7,7);


		ChoosePos(gameLogic, ennemyPos, departurePos, blankPos, min);

		if ( blankPos.x!=7 && blankPos.y!=7){
			if (departurePos.donutDistance(blankPos)==1) {
				gameLogic.copy(blankPos);
				trouve=true;
			}
			else if (departurePos.donutDistance(blankPos)==2) {
				gameLogic.move(blankPos);
				trouve=true;
			}
		}

		if (!trouve || blankPos.x==7 && blankPos.y==7){
			strategie_min_sup_3(gameLogic, departurePos,ennemyPos, min);
		}
	}

	////////////////////
}
