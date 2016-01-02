package com.example.note.bean;

public class NoteText {
	private Integer id;
	private String contentText;
	private String date;
	private String titleText;
	
	public NoteText(Integer id, String contentText, String date, String titleText){
		this.id = id;
		this.contentText = contentText;
		this.date = date;
		this.titleText = titleText;
	}
	
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitleText() {
		return titleText;
	}
	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
