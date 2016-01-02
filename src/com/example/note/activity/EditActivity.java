package com.example.note.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.note.db.NoteSqlService;
import com.example.note.bean.NoteText;
import com.example.note.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends Activity implements OnClickListener{
	private LinearLayout titleEditDone;
	private LinearLayout titleEditDelete;
	private LinearLayout titleLeftLayout;
	private Calendar cal;
	private	EditText timeEdit;
	private	EditText contentEdit;
	private	EditText titleEdit;
	private SimpleDateFormat df;
	private NoteSqlService noteSqlService;
	private Integer isExistsId = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_page);
		titleLeftLayout = (LinearLayout) findViewById(R.id.aaa);
		titleEditDelete = (LinearLayout) findViewById(R.id.title_edit_delete);
		titleEditDone = (LinearLayout) findViewById(R.id.title_edit_done);
		timeEdit = (EditText) findViewById(R.id.time_edit);
		contentEdit = (EditText) findViewById(R.id.text_content);
		titleEdit = (EditText) findViewById(R.id.title_edit);
		cal = Calendar.getInstance();
		noteSqlService = new NoteSqlService(this);
		titleLeftLayout.setOnClickListener(this);
		titleEditDelete.setOnClickListener(this);
		titleEditDone.setOnClickListener(this);
		timeEdit.setOnClickListener(this);
		
		Intent intent = getIntent();
		if((intent.getStringExtra("activityName")).equals("ViewActivity")){
			((TextView) findViewById(R.id.edit_title_text)).setText(R.string.update_info);
			isExistsId = intent.getIntExtra("id", -1);
			titleEdit.setText(intent.getStringExtra("noteTitle"));
			timeEdit.setText(intent.getStringExtra("noteDate"));
			contentEdit.setText(intent.getStringExtra("noteContent"));
		}else{}
		
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.aaa:
			turnToMain();
			break;
		case R.id.title_edit_done:
			if(isExistsId == null) {
				String title = titleEdit.getText().toString();
				String time = timeEdit.getText().toString();
				if(!(title.equals("") || time.equals(""))) {
					NoteText noteText = new NoteText(null, contentEdit.getText().toString(), 
							timeEdit.getText().toString(), titleEdit.getText().toString());
					noteSqlService.save(noteText);
					Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
					turnToMain();
				}else{
					Toast.makeText(this, "请填写标题和时间", Toast.LENGTH_SHORT).show();
				}
			}else{
				String title = titleEdit.getText().toString();
				String time = timeEdit.getText().toString();
				if(!(title.equals("") || time.equals(""))) {
					NoteText noteText = new NoteText(isExistsId, contentEdit.getText().toString(), 
							timeEdit.getText().toString(), titleEdit.getText().toString());
					noteSqlService.update(noteText);
					Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
					turnToMain();
				}else{
					Toast.makeText(this, "请填写标题和时间", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.title_edit_delete:
			turnToMain();
			break;
		case R.id.time_edit:
			new DatePickerDialog(EditActivity.this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					cal.set(Calendar.YEAR, year);
					cal.set(Calendar.MONTH, monthOfYear);
					cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					updateDate();
				}
			}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
			break;
		}
	}
	
	private void updateDate() {
		// TODO Auto-generated method stub
		df = new SimpleDateFormat("yyyy-MM-dd");
		timeEdit.setText(df.format(cal.getTime()));
	}
	public void turnToMain() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		this.finish();
	}
	
}
