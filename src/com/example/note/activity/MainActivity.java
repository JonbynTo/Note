package com.example.note.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.note.R;
import com.example.note.bean.NoteText;
import com.example.note.db.NoteSqlService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	private ImageView titleLeft;
	private LinearLayout titleAdd;
	private ListView mainListView;
	private List<Map<String, String>> list;
	private SimpleAdapter mainListAdapter;
	private boolean isExit = false;
	private NoteSqlService noteSqlService;
	private List<NoteText> noteTexts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		titleLeft = (ImageView) findViewById(R.id.title_left_menu);
		titleAdd = (LinearLayout) findViewById(R.id.title_add);
		mainListView = (ListView) findViewById(R.id.mainList);
		noteSqlService = new NoteSqlService(this);
		noteTexts = new ArrayList<NoteText>();
		noteTexts = noteSqlService.getScrollData();
		list = new ArrayList<Map<String,String>>();
		Log.e("INFO", this.getClass().getSimpleName());
		if(noteTexts.size() > 0) {
			mainListAdapter = new SimpleAdapter(this, getData(), R.layout.mian_list, 
					new String[] {"note_title", "note_date"}, new int[] {R.id.note_title, R.id.note_date});
			mainListView.setAdapter(mainListAdapter);
		}
		titleAdd.setOnClickListener(this);
		titleLeft.setOnClickListener(this);
		mainListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.add(0, 0, 0, "删除");
			}
		});
		mainListView.setOnItemClickListener(this);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(0 == item.getItemId()) {
			AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
			list.remove(menuInfo.position);
			NoteText noteText = noteTexts.get(menuInfo.position);
			mainListAdapter.notifyDataSetChanged();
			noteSqlService.delete(noteText.getId());
			noteTexts = noteSqlService.getScrollData();
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			OnKeySecondDown();
		}
		return false;
	}
	
	public void OnKeySecondDown(){
		Timer timer;
		if(isExit == false) {
			isExit = true;
			timer = new Timer();
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					isExit = false;
				}
			}, 2000);
			
		}else{
			finish();
			System.exit(0);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.title_left_menu:
			break;
		case R.id.title_add:
			Intent intent = new Intent();
			intent.putExtra("activityName", this.getClass().getSimpleName());
			intent.setClass(this, EditActivity.class);
			startActivity(intent);
			this.finish();
			break;
		}
	}
	
	public List<Map<String, String>> getData(){
			for(int i = 0; i < noteTexts.size(); i++){
				NoteText noteText = noteTexts.get(i);
				HashMap<String, String> hashM = new HashMap<String, String>();
				if(noteText.getTitleText().length() >= 10) {
					hashM.put("note_title", noteText.getTitleText().substring(0, 10) + " . . .");
				}else{
					hashM.put("note_title", noteText.getTitleText());
				}
				hashM.put("note_date", noteText.getDate());
				list.add(hashM);
			}
			return list;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		NoteText noteText = noteTexts.get(arg2);
		Intent intent = new Intent();
		intent.putExtra("id", noteText.getId());
		intent.putExtra("noteTitle", noteText.getTitleText());
		intent.putExtra("noteDate", noteText.getDate());
		intent.putExtra("noteContent", noteText.getContentText());
		intent.setClass(this, ViewActivity.class);
		startActivity(intent);
		this.finish();
	}

}
