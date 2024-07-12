package com.codingdojo.groupproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.groupproject.models.Tag;
import com.codingdojo.groupproject.repositories.TagRepository;

@Service
public class TagService {
	@Autowired
	TagRepository tagRepository;
	
	public Tag findTagByName(String name) {
		Optional<Tag> potentialTag = tagRepository.findByName(name);
		System.out.println(name);
		if(potentialTag.isPresent()) {
			return potentialTag.get();
		} else {
			return null;
		}
	}
	
	public Tag findTag(Long id) {
		Optional<Tag> potentialTag = tagRepository.findById(id);
		if(potentialTag.isPresent()) {
			return potentialTag.get();
		} else {
			return null;
		}
	}
	
	public List<Tag> findAllTags(){
		return tagRepository.findAll();
	}
	
	public Tag createTag(Tag tag) {
		return tagRepository.save(tag);
	}
	
	public Tag updateTag(Tag tag) {
		return tagRepository.save(tag);
	}
	
	public void deleteTag(Long id) {
		tagRepository.deleteById(id);
	}
	
	public void initialTagLoad() {
		List<Tag> loadList = new ArrayList<Tag>();
		
		//Negative numbers in the conflict id do not conflict, but the positive ones can
		//conflictId: 1
		loadList.add(new Tag("Action", (long) -1));
		loadList.add(new Tag("Adventure", (long) -1));
		loadList.add(new Tag("Platformer", (long) -1));
		loadList.add(new Tag("Hack & Slash", (long) -1));
		loadList.add(new Tag("Driving", (long) -1));
		loadList.add(new Tag("Story", (long) -1));
		loadList.add(new Tag("Sports", (long) -1));
		loadList.add(new Tag("Simulator", (long) -1));
		loadList.add(new Tag("Arcade", (long) -1));
		loadList.add(new Tag("Roguelike", (long) -1));
		loadList.add(new Tag("Survival", (long) -1));
		
		/* You should be able to correctly label the tags by taking the absolute value of its conflictId. 
		 * 1 being type 
		 * 2 being mechanics 
		 * 3 being perspective 
		 * 4 being structure
		 * 5 being graphics
		 * 6 being style
		 * 7 being duration
		 * and 8 being production.*/
		//conflictId: 2
		loadList.add(new Tag("Cards", (long) -2));
		loadList.add(new Tag("Board", (long) -2));
		loadList.add(new Tag("Basic RPG", (long) 2));
		loadList.add(new Tag("Full RPG", (long) 2));
		loadList.add(new Tag("Puzzle", (long) -2));
		loadList.add(new Tag("Campaign", (long) -2));
		loadList.add(new Tag("Multiplayer", (long) -2));
		loadList.add(new Tag("Touch", (long) -2));
		loadList.add(new Tag("Quicktime Actions", (long) -2));
		loadList.add(new Tag("Point & Click", (long) -2));
		
		//The following conflicts with each-other
		//conflictId: 3
		loadList.add(new Tag("First-Person", (long) 3));
		loadList.add(new Tag("Third-Person", (long) 3));
		loadList.add(new Tag("Isometric", (long) 3));
		
		//conflictId: 4
		loadList.add(new Tag("Sequential", (long) -4));
		loadList.add(new Tag("Selectable Stages", (long) -4));
		loadList.add(new Tag("Limited Open World", (long) 4));
		loadList.add(new Tag("Open World", (long) 4));
		
		//conflictId: 5
		loadList.add(new Tag("2D", (long) 5));
		loadList.add(new Tag("2.5D", (long) 5));
		loadList.add(new Tag("3D", (long) 5));
		
		//conflictId: 6
		loadList.add(new Tag("Realistic", (long) -6));
		loadList.add(new Tag("Pixel Art", (long) -6));
		loadList.add(new Tag("Cartoonish/Comic", (long) -6));
		loadList.add(new Tag("Artsy", (long) -6));
		loadList.add(new Tag("Retro", (long) -6));
		
		//conflictId: 7
		loadList.add(new Tag("Short", (long) 7));
		loadList.add(new Tag("Medium", (long) 7));
		loadList.add(new Tag("Long", (long) 7));
		
		//conflictId: 8
		loadList.add(new Tag("Indie", (long) 8));
		loadList.add(new Tag("AAA", (long) 8));
		
		tagRepository.saveAll(loadList);
	}
}


