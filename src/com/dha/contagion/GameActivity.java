package com.dha.contagion;

import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 
public class GameActivity extends Activity {

	private Integer[] imageIDs; // for ImageAdaptor
	GameLogic mGame;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		/* init */
		mGame = new GameLogic();
		imageIDs = caseToImg();
		
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAdapter(this));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
	
	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;
		public ImageAdapter(Context c){
			context = c;
		}
		@Override
		public int getCount() {
			return imageIDs.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(imageIDs[position]);
			return imageView;
		}
		
	}
	
	// Convert the 2-dim array in GameLogic (containing enum type Case)
	// to 1-dim array containing Image IDs
	private Integer[] caseToImg(){
		GameLogic.Case[][] playBoard = mGame.getPlayBoard(); 
		Log.i("caseToImg", Arrays.deepToString(playBoard));
		Integer[] drawBoard = new Integer[GameLogic.DIM*GameLogic.DIM];
		int cnt = 0;
		for (GameLogic.Case[] cRow : playBoard){
			for (GameLogic.Case square: cRow){
				switch (square){
					case BLUE : 
						drawBoard[cnt++] = R.drawable.pion_rouge_outlined_black;
						continue;
					case RED:
						drawBoard[cnt++] = R.drawable.pion_bleu_outlined_black;
						continue;
					case BLANK:
						drawBoard[cnt++] = R.drawable.pion_blank_outlined_black;
						continue;
				}
			}
		}
		return drawBoard;
	}
}
