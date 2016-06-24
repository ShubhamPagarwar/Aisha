package com.example.bebobraina;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;

public class FetchSong {

	final String MEDIA_PATH;
	private ArrayList<HashMap<String, String>> songsList;
	private String mp3Pattern ;
	public FetchSong(){
	MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
	 songsList = new ArrayList<HashMap<String, String>>();
		 mp3Pattern = ".mp3";
	}

	public ArrayList<HashMap<String, String>> fetch() {
		System.out.println(MEDIA_PATH);
		if (MEDIA_PATH != null) {
		File home = new File(MEDIA_PATH);
		File[] listFiles = home.listFiles();
		if (listFiles != null && listFiles.length > 0) {
		    for (File file : listFiles) {
		        System.out.println(file.getAbsolutePath());
		        if (file.isDirectory()) {
		            scanDirectory(file);
		        } else {
		            addSongToList(file);
		        }
		    }
		}
		}
		// return songs list array
		return songsList;
		}

		private void scanDirectory(File directory) {
		if (directory != null) {
		File[] listFiles = directory.listFiles();
		if (listFiles != null && listFiles.length > 0) {
		    for (File file : listFiles) {
		        if (file.isDirectory()) {
		            scanDirectory(file);
		        } else {
		            addSongToList(file);
		        }

		    }
		}
		}
		}

		private void addSongToList(File song) {
		if (song.getName().endsWith(mp3Pattern)) {
		HashMap<String, String> songMap = new HashMap<String, String>();
		songMap.put("songTitle",
		        song.getName());
		songMap.put("songPath", song.getPath());

		// Adding each song to SongList
		songsList.add(songMap);
		}
		}
		
}
