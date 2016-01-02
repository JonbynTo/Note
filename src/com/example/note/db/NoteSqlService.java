package com.example.note.db;

import java.util.ArrayList;
import java.util.List;

import com.example.note.bean.NoteText;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteSqlService {
	private ManagerDatabase dbManager;
	
	public NoteSqlService(Context context) {
		dbManager = new ManagerDatabase(context);
	}
	
	public void save(NoteText noteText) {
		SQLiteDatabase database = dbManager.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("contentText", noteText.getContentText());
		values.put("date", noteText.getDate());
		values.put("titleText", noteText.getTitleText());
		database.insert("tb_note_text", null, values);
		database.close();
	}
	
	public void update(NoteText noteText) {
		SQLiteDatabase database = dbManager.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("contentText", noteText.getContentText());
		values.put("date", noteText.getDate());
		values.put("titleText", noteText.getTitleText());
		database.update("tb_note_text", values, "_id=?",
				new String[] {String.valueOf(noteText.getId())});
		database.close();
	}
	
	public NoteText find(Integer id) {
		SQLiteDatabase database = dbManager.getWritableDatabase();
		Cursor cursor = database.query("tb_note_text", 
				new String[] {"contentText", "date", "titleText"}, "_id=?", 
				new String[] {String.valueOf(id)}, null, null, null);
		if(cursor != null) {
			if(cursor.moveToNext()) {
				NoteText noteText = new NoteText(cursor.getInt(cursor.getColumnIndex("_id")),
						cursor.getString(cursor.getColumnIndex("contentText")),
						cursor.getString(cursor.getColumnIndex("date")),
						cursor.getString(cursor.getColumnIndex("titleText")));
				cursor.close();
				database.close();
				return noteText;
			}
		}
		cursor.close();
		database.close();
		return null;
	}
	
	public void delete(Integer id) {
		SQLiteDatabase database = dbManager.getWritableDatabase();
		database.delete("tb_note_text", "_id=?", new String[] {String.valueOf(id)});
		database.close();
	}
	
	public List<NoteText> getScrollData(){
		List<NoteText> noteTexts = new ArrayList<NoteText>();
		SQLiteDatabase database = dbManager.getWritableDatabase();
		Cursor cursor = database.query("tb_note_text", new String[] {"_id", "contentText", "date", "titleText"}, "_id>?", new String[] {"0"}, null, null, null);
		if(cursor != null) {
			while(cursor.moveToNext()) {
				noteTexts.add(new NoteText(cursor.getInt(cursor.getColumnIndex("_id")), 
						cursor.getString(cursor.getColumnIndex("contentText")), 
						cursor.getString(cursor.getColumnIndex("date")), 
						cursor.getString(cursor.getColumnIndex("titleText"))));
			}
			cursor.close();
		}
		database.close();
		return noteTexts;
	}
	
	public long getCount() {
		SQLiteDatabase database = dbManager.getWritableDatabase();
		Cursor cursor = database.query("tb_note_text", new String[] {"count(*)"}, null, null, null, null, null);
		if(cursor != null) {
			if(cursor.moveToNext()) {
				Long count = cursor.getLong(0);
				cursor.close();
				database.close();
				return count;
			}
		}
		cursor.close();
		database.close();
		return 0;
	}
}
