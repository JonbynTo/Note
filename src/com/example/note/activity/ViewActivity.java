package com.example.note.activity;

import com.example.note.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewActivity extends Activity implements OnClickListener{
	
	private TextView titleView;
	private TextView dateView;
	private TextView contentView;
	private LinearLayout linearBack;
	private LinearLayout editView;
	private int id;
	private String title;
	private String date;
	private String content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view);
		titleView = (TextView) findViewById(R.id.view_text_title);
		dateView = (TextView) findViewById(R.id.view_text_date);
		contentView = (TextView) findViewById(R.id.view_text_content);
		linearBack = (LinearLayout) findViewById(R.id.view_back);
		editView = (LinearLayout) findViewById(R.id.view_edit_layout);
		Intent intent = getIntent();
		title = intent.getStringExtra("noteTitle");
		date = intent.getStringExtra("noteDate");
		content = intent.getStringExtra("noteContent");
		id = intent.getIntExtra("id", -1);
		titleView.setText(title);
		dateView.setText(date);
		contentView.setText(content);
		((TextView) findViewById(R.id.view_title_text)).setText(date);
		linearBack.setOnClickListener(this);
		editView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.view_back:
			turnToMain();
			break;
		case R.id.view_edit_layout:
			Intent intent = new Intent();
			intent.putExtra("id", id);
			intent.putExtra("noteTitle", title);
			intent.putExtra("noteDate", date);
			intent.putExtra("noteContent", content);
			intent.putExtra("activityName", this.getClass().getSimpleName());
			intent.setClass(this, EditActivity.class);
			startActivity(intent);
			this.finish();
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			this.finish();
		}
		return false;
	}
	
	public void turnToMain() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
}
