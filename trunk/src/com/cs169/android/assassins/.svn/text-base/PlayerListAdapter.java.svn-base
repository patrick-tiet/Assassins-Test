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

public class PlayerListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Player> players;
	private AssassinsApplication app;

	public PlayerListAdapter(Context context, ArrayList<Player> players) {
		super();
		this.context = context;
		this.players = players;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		PlayerItem playerItem;
		
		if (convertView == null) {
			playerItem = (PlayerItem) View.inflate(context, R.layout.player_item, null);
		}
		else {
			Log.v("ASA", "1");
			playerItem = (PlayerItem) convertView;
			Log.v("ASA", "PlaylistAdapter: getView, convert view is not null, en d");
		}
		
		playerItem.addPlayer(players.get(position));
		Log.v("ASA", "PlaylistAdapter: getView, end");
		return playerItem;
	}

	public void forceReload() {
		Log.v("ASA", "PlaylistAdapter: forceReload, start");
		notifyDataSetChanged();
		Log.v("ASA", "PlaylistAdapter: forceReload, end");
	}

	public int getCount() {
		return players.size();
	}

	public Object getItem(int position) {
		return (null==players)? null : players.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
}