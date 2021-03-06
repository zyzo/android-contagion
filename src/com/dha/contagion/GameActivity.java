package com.dha.contagion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

import com.dha.contagion.GameLogic.Case;
 
public class GameActivity extends Activity {
	private Map<Case, Integer> imageMaps; 			// maps images with cases
	private Integer[] imageIDs; 					// for ImageAdaptor to print playBoard
	private GameLogic mGame;						// the logic
	private GridView mGridView;                     // the look and the feel
	private ImageSwitcher mImageSwitcher;           // the animations
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		/* init */
		mGame = new GameLogic();
		imageMaps = initImageMapsXOTr(); 
		imageIDs = caseToImg();
		mGridView = (GridView) findViewById(R.id.gridView);
		mGridView.setAdapter(new ImageAdapter(this));
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
		mImageSwitcher.setFactory(new ViewFactory() {
			
			@Override
			public View makeView() {
				return new ImageView(GameActivity.this);
			}
		});
		mImageSwitcher.setInAnimation(
		AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		mImageSwitcher.setOutAnimation( AnimationUtils.loadAnimation(this,
		android.R.anim.fade_out));
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.i("onItemClick", Integer.toString(position));
				// animation
				
				// game change
				mGame.handleEvent(position);
				
				// update board
				imageIDs = caseToImg();
				
				// animate
				mImageSwitcher.setX(arg1.getX() + 21 );
				mImageSwitcher.setY(arg1.getY() + 17 );
				mImageSwitcher.setImageResource(imageIDs[position]);
				mImageSwitcher.animate();
				
				mGridView.invalidateViews();
			}
		});
		
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		menu.add(ACTIVITY_SERVICE);
		return true;
	}
	
	// initialize a Map object and return
	// assign this object to imageMaps
	private Map<Case, Integer> initImageMaps() {
		Map<Case, Integer> map = new HashMap<Case, Integer>();
		map.put(GameLogic.Case.RED, R.drawable.pion_rouge_outlined_black);
		map.put(GameLogic.Case.BLUE, R.drawable.pion_bleu_outlined_black);
		map.put(GameLogic.Case.BLANK, R.drawable.pion_blank_outlined_black);
		return map;
	}
	
	private Map<Case, Integer> initImageMapsXO() {
		Map<Case, Integer> map = new HashMap<Case, Integer>();
		map.put(GameLogic.Case.RED, R.drawable.a);
		map.put(GameLogic.Case.BLUE, R.drawable.b);
		map.put(GameLogic.Case.BLANK, R.drawable.c);
		return map;
	}
	
	private Map<Case, Integer> initImageMapsXOTr() {
		Map<Case, Integer> map = new HashMap<Case, Integer>();
		map.put(GameLogic.Case.RED, R.drawable.trans_o);
		map.put(GameLogic.Case.BLUE, R.drawable.trans_x);
		map.put(GameLogic.Case.BLANK, R.drawable.c);
		return map;
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
				drawBoard[cnt++] = imageMaps.get(square);
			}
		}
		return drawBoard;
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
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			// TODO: replace this line with an equivalent fix in xml file
			imageView.setAdjustViewBounds(true);
			imageView.setImageResource(imageIDs[position]);
			return imageView;
		}
		
		public void notifyDataSetChanged (){
			Log.v("ASDAS","ASDSAD");
		}
		
	}
}
