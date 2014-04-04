package com.dha.contagion;

import android.util.Log;

import com.dha.contagion.GameLogic.Case;
public class AI {

	private GameLogic gameLogic=new GameLogic();

	//	public Result result_blank=new Result();
	//public Result result_meilleur=new Result();
	//public Result result_meilleur2=new Result();
	//Result result_choose=new Result();
	//Result result_strategie=new Result();

	@SuppressWarnings("static-access")
	public Result getBlankCaseSurroundedByHighBlank(GameLogic.Case[][] mplayBoard,
			Pos thisPosRED,
			int distance){
		
		Case caseplayer= mplayBoard[thisPosRED.x][thisPosRED.y];
		Case adversaireCase = caseplayer==Case.BLUE? Case.RED: Case.BLUE;
		Log.d("type","advers= "+adversaireCase.toString()+"case="+caseplayer.toString());
		Result result_blank=new Result();
		int somme=0;
		int max=0;
		Pos pos1= new Pos(7,7);
		Pos pos2= new Pos(7,7);


		for (int i=0;i<gameLogic.DIM;i++){
			for(int j=0;j<gameLogic.DIM;j++){
				somme=0;
				pos1.assign(i, j);
				int dist=(pos1.donutDistance(thisPosRED));
				if (dist==distance && (mplayBoard[i][j]==Case.BLANK) ) {   
					for (int k=0;k<gameLogic.DIM;k++){
						for(int m=0;m<gameLogic.DIM;m++){
							pos2.assign(k, m);
							int dist2=pos2.donutDistance(pos1);
							if ((dist2==1) && (mplayBoard[k][m]==Case.BLUE)) {
								somme=somme+1;
								if (somme> max)  {
									result_blank.posBlank.assign(i, j);
									result_blank.posBlue.assign(k, m);
									max=somme;


								}}}}}}}


		result_blank.posRed=thisPosRED;
		if (max==0) {
			result_blank.posBlank.assign(8, 8);

		}
		result_blank.max=max;
		Log.d("casevide", "resultBlank "+ String.valueOf(somme)+result_blank.toString());
		return result_blank;
	}
	///////////////////////////////////////

	@SuppressWarnings("static-access")
	public Result meilleurcasevide(GameLogic.Case[][] mplayBoard,
			Pos thisPos,
			int distance,int max, int min){

		Result result_blank=new Result();
		Result result_meilleur=new Result();
		Pos pos1= new Pos(7,7);
		Pos CloserBluePos=new Pos(7, 7);
		Pos CloserBlankPos=new Pos(7, 7);

		min=GameLogic.DIM;
		max=0;

		for (int i=0;i<gameLogic.DIM;i++){
			for(int j=0;j<gameLogic.DIM;j++){
				pos1.assign(i, j);

				if (((pos1.donutDistance(thisPos)<min) || pos1.donutDistance(thisPos)<=3) && mplayBoard[i][j]==Case.BLUE ){

					if (pos1.donutDistance(thisPos)<min){
						min=pos1.donutDistance(thisPos);
						CloserBluePos.assign(i, j);
					}

					if (min<=3){
						result_blank=getBlankCaseSurroundedByHighBlank(mplayBoard, thisPos, distance);
						//if (result_blank.posBlank.x!=8 && result_blank.posBlank.y!=8){
						CloserBluePos=result_blank.posBlue;
						CloserBlankPos=result_blank.posBlank;
						//}
					}

				}
			}
		}


		result_meilleur.posBlank=CloserBlankPos;
		result_meilleur.posBlue=CloserBluePos;
		result_meilleur.posRed=thisPos;
		result_meilleur.max=result_blank.max;
		result_meilleur.min=min;
		Log.d("meilleurcase vide","meilleurcasevide in mei="+result_meilleur.toString());
		return result_meilleur;

	}
	////////////////////////////////////

	@SuppressWarnings("static-access")
	public Result ChoosePos(GameLogic game,
			Pos ChosenPos,
			int min) {

		Result result_meilleur=new Result();
		Result result_meilleur2=new Result();
		Result result_choose=new Result();

		int minX=GameLogic.DIM;
		int max=0;
		int max1=0;
		int max2=0;

		Pos CloserBluePos= new Pos(7,7);
		Pos CloserBlankPos=new Pos(7,7);
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
					result_meilleur2=meilleurcasevide(mPlayBoard, pos5, 2, max2, minX);
					Log.d("meilleurcase vide","meilleurcasevide=in choo"+result_meilleur2.toString());
					minX=result_meilleur2.min;
					max2=result_meilleur2.max;
					postrouve2=result_meilleur2.posRed;
					posI2=result_meilleur2.posBlank;
					posX2=result_meilleur2.posBlue;
					result_meilleur=meilleurcasevide(mPlayBoard, pos5, 1, max1, minX);
					Log.d("meilleurcase vide","meilleurcasevide=choo"+result_meilleur.toString());
					minX=result_meilleur.min;
					max1=result_meilleur.max;
					postrouve1=result_meilleur.posRed;
					posI1=result_meilleur.posBlank;
					posX1=result_meilleur.posBlue;


					if (minX<=3 || minX<min){

						if (minX<min ){
							min=minX;
							
							//	ChosenPos.assign(i, j);
						}
						//CloserBluePos.assign(result_meilleur.posBlue.x,result_meilleur.posBlue.y);
						//CloserBluePos.assign(posX1.x,posX1.y);

						if (min<=3) {
							if (max1>=max2 && max1>=max) {
								max=max1;
								ChosenPos.assign(postrouve1.x, postrouve1.y);
								CloserBlankPos.assign(posI1.x,posI1.y);
								CloserBluePos.assign(posX1.x,posX1.y);
							}
							else if(max2>max1 && max2>=max) {
								max=max2;
								ChosenPos.assign(postrouve2.x, postrouve2.y);
								CloserBlankPos.assign(posI2.x,posI2.y);
								CloserBluePos.assign(posX2.x,posX2.y);
							}
						}
						else{
							CloserBluePos.assign(posX1.x, posX1.y);
							if (mPlayBoard[i][j]==Case.RED && pos5.donutDistance(CloserBluePos)<=min ){
								ChosenPos.assign(i, j);
							}

						}
					}
				}
			}
		}
		result_choose.posBlank=CloserBlankPos;
		result_choose.posBlue=CloserBluePos;
		result_choose.posRed=ChosenPos;
		result_choose.min=min;
		result_choose.max=max;
		Log.d("meilleurcase vide","Choosecas "+result_choose.toString());

		return result_choose;
	}


	///////////////////////////////////

	Result strategie_min_sup_3 (GameLogic gameLogic,
			Pos ChosePos,
			Pos BluePos,
			int min) {

		Result result_strategie=new Result();
		GameLogic.Case[][] mPlayBoard= gameLogic.getPlayBoard();
		boolean found=false;
		Pos arrivalPos= new Pos(7,7);
		Pos posBlank= new Pos(7,7);
		if ( min>3 ) {

			for (int i=0; i<GameLogic.DIM;i++){
				for(int c=0;(c<GameLogic.DIM) && (!found) ;c++){
					arrivalPos.assign(i, c);
					if (arrivalPos.donutDistance(ChosePos)==1 && mPlayBoard[i][c]==Case.BLANK && ( !found) ){
						posBlank=arrivalPos;
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
							arrivalPos.donutDistance(BluePos)<min && arrivalPos.donutDistance(ChosePos)<min)
					{
						posBlank=arrivalPos;
						gameLogic.move(arrivalPos);
						found=true;
					}

				}
			}
		}
		result_strategie.min=min;
		result_strategie.posBlank=posBlank;
		result_strategie.posBlue=BluePos;
		result_strategie.posRed=ChosePos;
		//result_strategie

		Log.d("meilleurcase vide","strategy"+result_strategie.toString());
		return result_strategie;
	}
	////////////////////////

	Result contremachine(GameLogic gameLogic){
		boolean trouve= false;

		Pos departurePos= new Pos(7,7);

		int min=GameLogic.DIM;
		Pos ennemyPos= new Pos(7,7);
		Pos blankPos= new Pos(7,7);

		Result result_strategie=new Result();
		Result result_choose=new Result();
		result_choose=ChoosePos(gameLogic, ennemyPos, min);

		departurePos=result_choose.posRed;
		blankPos=result_choose.posBlank;
		if ( blankPos.x!=8 && blankPos.y!=8){
			gameLogic.chosenCase.assign(departurePos.x, departurePos.y);
			
			if (departurePos.donutDistance(blankPos)==1) {
				gameLogic.copy(blankPos);
				trouve=true;
			}
			else if (departurePos.donutDistance(blankPos)==2) {
				gameLogic.move(blankPos);
				trouve=true;
			}

		}

		if (!trouve || blankPos.x==8 && blankPos.y==8){
			result_strategie=strategie_min_sup_3(gameLogic, result_choose.posRed,result_choose.posBlue, min);
			departurePos=result_strategie.posRed;
			blankPos=result_strategie.posBlank;
			gameLogic.chosenCase.assign(departurePos.x, departurePos.y);
			if (departurePos.donutDistance(blankPos)==1) {
				gameLogic.copy(blankPos);
				trouve=true;
			}
			else if (departurePos.donutDistance(blankPos)==2) {
				gameLogic.move(blankPos);
				trouve=true;
			}

		}


		gameLogic.contamineEnnemy(blankPos);
		return result_strategie;
	}

	////////////////////



}

