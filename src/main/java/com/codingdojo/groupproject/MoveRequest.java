package com.codingdojo.groupproject;

public class MoveRequest {
	private String[] mediaTagList;
	private String tagName;
	
	public MoveRequest() {}
	
	public MoveRequest(String[] mediaTagList, String tagName) {
		this.tagName = tagName;
		this.mediaTagList = mediaTagList;
	}


	public String[] getMediaTagList() {
		return mediaTagList;
	}

	public void setMediaTagList(String[] mediaTagList) {
		this.mediaTagList = mediaTagList;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	
	
}
