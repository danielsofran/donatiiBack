package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.repository.TagAnimalRepository;
import com.donatii.donatiiapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class UtilsController {
    private final TagAnimalRepository tagAnimalRepository;

    @Autowired
    public UtilsController(TagAnimalRepository tagAnimalRepository) {
        this.tagAnimalRepository = tagAnimalRepository;
    }

    @GetMapping("/tags")
    public Iterable<TagAnimal> getTags() {
        return tagAnimalRepository.findAll();
    }
}
