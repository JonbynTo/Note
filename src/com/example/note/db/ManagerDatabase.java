package com.example.note.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManagerDatabase extends SQLiteOpenHelper {
	
	private static final String name = "edit_text";
	private static final int version = 1;

	public ManagerDatabase(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE tb_note_text(_id integer primary key autoincrement, contentText text, date text not null, titleText text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS tb_note_text");
		onCreate(db);
	}

}
