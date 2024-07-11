package com.codingdojo.groupproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.codingdojo.groupproject.MoveRequest;
import com.codingdojo.groupproject.models.Media;
import com.codingdojo.groupproject.models.Tag;
import com.codingdojo.groupproject.services.MediaService;
import com.codingdojo.groupproject.services.TagService;
import com.codingdojo.groupproject.services.UserService;

public class TagListController {
	@Autowired
	UserService userService;
	@Autowired
	MediaService mediaService;
	@Autowired
	TagService tagService;
	
	@PostMapping("/taggingfavorites-tag-list-add")
	public ResponseEntity<Integer> relayAddTagInfo(@RequestBody MoveRequest request){
		Media media = mediaService.findMedia(request.getMediaId());
		Tag tag = tagService.findTag(request.getTagId());
		if(media.getTags().contains(tag)) {
			return ResponseEntity.ok(0);
		} else {
			mediaService.addTag(media, tag);
			return ResponseEntity.ok(1);
		}
	}
	
	@PostMapping("/taggingfavorites-tag-list-remove")
	public ResponseEntity<Integer> relayRemoveTagInfo(@RequestBody MoveRequest request){
		Media media = mediaService.findMedia(request.getMediaId());
		Tag tag = tagService.findTag(request.getTagId());
		if(!media.getTags().contains(tag)) {
			return ResponseEntity.ok(0);
		} else {
			mediaService.removeTag(media, tag);
			return ResponseEntity.ok(1);
		}
	}
}
