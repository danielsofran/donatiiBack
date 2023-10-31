package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.repository.ITagAnimalRepository;
import com.donatii.donatiiapi.repository.IUserRepository;
import com.donatii.donatiiapi.service.interfaces.ITagAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagAnimalService implements ITagAnimalService {

    private final ITagAnimalRepository tagAnimalRepository;
    @Autowired
    public TagAnimalService(ITagAnimalRepository tagAnimalRepository) {
        this.tagAnimalRepository = tagAnimalRepository;
    }

    public Iterable<TagAnimal> getTags()
    {
        return tagAnimalRepository.findAll();
    }
}
