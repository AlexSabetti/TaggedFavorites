package com.codingdojo.groupproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.groupproject.models.Media;
import com.codingdojo.groupproject.models.Tag;
import com.codingdojo.groupproject.models.User;
import com.codingdojo.groupproject.repositories.MediaRepository;

@Service
public class MediaService {
	@Autowired
	private MediaRepository mediaRepository;
	@Autowired
	
	public List<Media> findAllMedia(){
		return mediaRepository.findAll();
	}
	
	public Media createMedia(Media media) {
		return mediaRepository.save(media);
	}
	
	public Media updateMedia(Media media) {
		return mediaRepository.save(media);
	}
	
	public void deleteMedia(Media media) {
		mediaRepository.delete(media);
	}
	
	public Media findMedia(Long id) {
		Optional<Media> potentialMedia = mediaRepository.findById(id);
		if(potentialMedia.isPresent()) {
			return potentialMedia.get();
		} else {
			return null;
		}
	}
	
	public List<Media> getRecommendedMedia(Media media){
		List<Media> potentialMedias = mediaRepository.findAll();
		List<Media> toReturn = new ArrayList<Media>();
		//The following is a terrible, terrible way of doing this
		for(Media potentialMedia : potentialMedias) {
			for(Tag tag : potentialMedia.getTags()) {
				if(media.getTags().contains(tag)) {
					if(media.getId() != potentialMedia.getId())
					{
						toReturn.add(potentialMedia);
						break;
					}
				}
			}
		}
		
		return toReturn;
	}
	
	public Media findMediaByName(String name) {
		Optional<Media> potentialMedia = mediaRepository.findByName(name);
		if(potentialMedia.isPresent()) {
			return potentialMedia.get();
		}else {
			return null;
		}
	}
	
	public Media addTag(Media media, Tag tag) {
		for(Tag checkTag : media.getTags()) {
			if(checkTag.getConflictId() == tag.getConflictId() && tag.getConflictId() > 0) {
				return media;
			}
		}
		
		media.getTags().add(tag);
		return mediaRepository.save(media);
	}
	
	public Media removeTag(Media media, Tag tag) {
		media.getTags().remove(tag);
		return mediaRepository.save(media);
	}
	
	public List<Media> findAllByTag(Tag tag){
		List<Media> medias = mediaRepository.findAll();
		List<Media> toReturn = new ArrayList<Media>();
		for(Media potentialMedia : medias) {
			if(potentialMedia.getTags().contains(tag)) {
				toReturn.add(potentialMedia);
			}
		}
		
		return toReturn;
	}
}
