package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.repository.CostumizabilRepository;
import com.donatii.donatiiapi.repository.TagAnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UtilsController {
    private final TagAnimalRepository tagAnimalRepository;
    private final CostumizabilRepository costumizabilRepository;

    @Autowired
    public UtilsController(TagAnimalRepository tagAnimalRepository, CostumizabilRepository costumizabilRepository) {
        this.tagAnimalRepository = tagAnimalRepository;
        this.costumizabilRepository = costumizabilRepository;
    }

    @GetMapping("/tags")
    public Iterable<TagAnimal> getTags() {
        return tagAnimalRepository.findAll();
    }

    @GetMapping("/costumizabile")
    public Iterable<Costumizabil> getCostumizabile() {
        return costumizabilRepository.findAll();
    }
}
