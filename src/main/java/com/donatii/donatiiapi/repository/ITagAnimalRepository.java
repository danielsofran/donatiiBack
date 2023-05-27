package com.donatii.donatiiapi.repository;

import com.donatii.donatiiapi.model.TagAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagAnimalRepository extends JpaRepository<TagAnimal, Long> {
}
