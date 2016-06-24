package com.example.bebobraina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLStorage{

	public static final String keyfirst_name="firstname";
	public static final String keymiddle_name="middlename";
	public static final String keylast_name="lastname";
	public static final String key_gender="gender";
	public static final String key_age="age";
	public static final String key_rowid="row";

	private static final String database_name="Aishadatabase";
	private static final String database_table="Aishauser";
	private static final int database_version=2;
	
	DbHelper ourHelper;
	Context ourContext;
	static SQLiteDatabase ourDatabase;
	private static class DbHelper extends SQLiteOpenHelper{

		public DbHelper(Context context) {
			super(context, database_name, null, database_version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL( "create table "+database_table+"("+key_rowid+" integer primary key autoincrement,"+keyfirst_name+","+keymiddle_name+","+keylast_name+","+key_gender+","+key_age+" )");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub
			db.execSQL("drop table if exists "+database_table);
			onCreate(db);
		}
     
		
	}
	public SQLStorage(Context c){
		ourContext=c;
		
	}
	public SQLStorage open()
	{
		 ourHelper=new DbHelper(ourContext);
		 ourDatabase= ourHelper.getWritableDatabase();
	     return this;
	}
	public void close(){
		ourHelper.close();
	}
    public void insertdata(){
		ContentValues cv= new ContentValues();
		cv.put(keyfirst_name, " ");
		cv.put(keymiddle_name, " ");
		cv.put(keylast_name, " ");
		cv.put(key_gender, " ");
		cv.put(key_age, " ");
	    ourDatabase.insert(database_table, null, cv);
    }
	public void updateEntry(String name, String middle,String last,String gender,String age)throws Exception {
		// TODO Auto-generated method stub
		ContentValues cv= new ContentValues();
		cv.put(keyfirst_name, name);
		cv.put(keymiddle_name, middle);
		cv.put(keylast_name, last);
		cv.put(key_gender, gender);
		cv.put(key_age, age);
		ourDatabase.update(database_table,cv, key_rowid+"=1", null);
		
	}
	
	public String getFirstName()throws Exception {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,keyfirst_name,keymiddle_name,keylast_name,key_gender,key_age};
		Cursor c=ourDatabase.query(database_table, columns, key_rowid+"=1",null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String result=c.getString(c.getColumnIndex(keyfirst_name));
			return result;
		}
		else
		return null;
}
	public String getMiddleName()throws Exception {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,keyfirst_name,keymiddle_name,keylast_name,key_gender,key_age};
		Cursor c=ourDatabase.query(database_table, columns, key_rowid+"=1",null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String result=c.getString(c.getColumnIndex(keymiddle_name));
			return result;
		}
		else
		return null;
	}
	public String getLastName()throws Exception {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,keyfirst_name,keymiddle_name,keylast_name,key_gender,key_age};
		Cursor c=ourDatabase.query(database_table, columns, key_rowid+"=1",null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String result=c.getString(c.getColumnIndex(keylast_name));
			return result;
		}
		else
		return null;

	}
	public String getGender()throws Exception {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,keyfirst_name,keymiddle_name,keylast_name,key_gender,key_age};
		Cursor c=ourDatabase.query(database_table, columns, key_rowid+"=1",null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String result=c.getString(c.getColumnIndex(key_gender));
			return result;
		}
		else
		return null;
}	
	public String getAge()throws Exception {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,keyfirst_name,keymiddle_name,keylast_name,key_gender,key_age};
		Cursor c=ourDatabase.query(database_table, columns, key_rowid+"=1",null, null, null, null);
		if(c!=null)
		{
			c.moveToFirst();
			String result=c.getString(c.getColumnIndex(key_age));
			return result;
		}
		else
		return null;
}
	
}
	
