package com.codingdojo.groupproject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.groupproject.models.Media;
import com.codingdojo.groupproject.models.Tag;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long>{
	List<Media> findAll();
	
	@Query(value ="SELECT * FROM medias ORDER BY name", nativeQuery=true)
	List<Media> findAllSortByName();
	
	Optional<Media> findByName(String name);
	
	List<Media> findByTags(List<Tag> tags);
}
