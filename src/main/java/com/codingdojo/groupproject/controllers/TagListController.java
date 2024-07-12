package com.codingdojo.groupproject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codingdojo.groupproject.MoveRequest;
import com.codingdojo.groupproject.models.Media;
import com.codingdojo.groupproject.models.Tag;
import com.codingdojo.groupproject.services.MediaService;
import com.codingdojo.groupproject.services.TagService;
import com.codingdojo.groupproject.services.UserService;

@RestController
public class TagListController {
	@Autowired
	UserService userService;
	@Autowired
	MediaService mediaService;
	@Autowired
	TagService tagService;
	
	@GetMapping("/api/startup")
	public ResponseEntity<List<String>> initialize(){
		List<Tag> tags = tagService.findAllTags();
		List<String> tagNames = new ArrayList<String>();
		for(Tag tag : tags) {
			tagNames.add(tag.getName());
		}
		
		return ResponseEntity.ok(tagNames);
	}
	
	@PostMapping("/api/add")
	public ResponseEntity<Integer> add(@RequestBody MoveRequest moveRequest)
	{
		Tag tag = tagService.findTagByName(moveRequest.getTagName());
		for(String string : moveRequest.getMediaTagList()) {
			Tag toTag = tagService.findTagByName(string);
			if(toTag.getConflictId() == tag.getConflictId() && toTag.getConflictId() > 0) {
				return ResponseEntity.ok((Integer) 0);
			}
		}
		
		return ResponseEntity.ok((Integer) 1);
		
	}
	
	@PostMapping("/api/startup/edit")
	public ResponseEntity<List<List<String>>> initializeEdit(@RequestBody Integer mediaId){
		Media media = mediaService.findMedia((long) mediaId);
		List<String> toModel = new ArrayList<String>();
		for(Tag tag : media.getTags()) {
			for(Tag freshTag : tagService.findAllTags()) {
				if(tag.getConflictId() == freshTag.getConflictId() && tag.getConflictId() > 0) {
					//Do nothing
				} else if(tag.getId() == freshTag.getId()) {
					//Do nothing
				} else {
					toModel.add(freshTag.getName());
				}
				
			}
		}
		System.out.println(toModel);
		List<Tag> tags = media.getTags();
		List<String> tagNames = new ArrayList<String>();
		for(Tag tag : tags) {
			tagNames.add(tag.getName());
		}
		List<List<String>> toList = new ArrayList<List<String>>();
		toList.add(0, tagNames); toList.add(1, toModel);
		
		return ResponseEntity.ok(toList);
	}
}
