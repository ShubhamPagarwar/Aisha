package com.example.bebobraina;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class OpenVideo extends Activity{
	ListView listSongs;
	LinearLayout songsView;
	boolean flag;
	ArrayList<HashMap<String, String>> search;
	static int i;
	@Override
	public void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.listvideo);
		// get the list of files and place them in ListView
		listSongs = (ListView) this.findViewById(R.id.listSongs);
		songsView = (LinearLayout) this.findViewById(R.id.songsView);
		songsView.setVisibility(View.VISIBLE);
		search= new ArrayList<HashMap<String, String>>();
		search=(ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("videolistoriginal");
		
	
		SimpleAdapter adapter = new SimpleAdapter(this,search,R.layout.song, new String[] { "videoTitle","videoPath"},
			     new int[] {  R.id.textTitle, R.id.textPath} );
		
	listSongs.setAdapter(adapter); 	
	/*if ( search.size() > 0 )
		songsView.setVisibility(View.VISIBLE);
	else
		songsView.setVisibility(View.INVISIBLE);*/

	listSongs.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> view, View item,
				int position, long id) {
			
			// Play song using  built-in audio player
			
			String path = search.get(position).get("videoPath");
			
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
			intent.setDataAndType(Uri.parse(path), "video/mp4");
			startActivity(intent);
			}
	});
	}
		
	
	
}