package com.codingdojo.groupproject;

public class MoveRequest {
	private Long mediaId;
	private Long tagId;
	
	public MoveRequest() {}
	
	public MoveRequest(Long mediaId, Long tagId) {
		this.tagId = tagId;
		this.mediaId = mediaId;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
}
