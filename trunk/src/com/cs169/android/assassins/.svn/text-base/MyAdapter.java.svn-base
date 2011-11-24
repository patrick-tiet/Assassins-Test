package com.cs169.android.assassins;

import java.util.ArrayList;



import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Game> games;
	private AssassinsApplication app;

	public MyAdapter(Context context, ArrayList<Game> games) {
		super();
		this.context = context;
		this.games = games;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.v("ASA", "PlaylistAdapter: getView, start");
		GameItem gameItem;
		if (convertView == null) {
			Log.v("ASA", "PlaylistAdapter: getView, convert view is null, start");
			
			gameItem = (GameItem) View.inflate(context, R.layout.game_item, null);
			Log.v("ASA", "PlaylistAdapter: getView, convert view is null, end");
		}
		else {
			Log.v("ASA", "1");
			gameItem = (GameItem) convertView;
			Log.v("ASA", "PlaylistAdapter: getView, convert view is not null, en d");
		}
		Log.v("ASA", "PlaylistAdapter: getView, tli add Song");
		Log.v("ASA", "PlaylistAdapter: games.get(position) = "+ games.toString());
		Log.v("ASA", "PlaylistAdapter: games.get(position) = "+ games.get(position).toString());
		gameItem.addGame(games.get(position));
		Log.v("ASA", "PlaylistAdapter: getView, end");
		return gameItem;
	}

	public void forceReload() {
		Log.v("ASA", "PlaylistAdapter: forceReload, start");
		notifyDataSetChanged();
		Log.v("ASA", "PlaylistAdapter: forceReload, end");
	}

	public int getCount() {
		return games.size();
	}

	public Object getItem(int position) {
		return (null==games)? null : games.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
}