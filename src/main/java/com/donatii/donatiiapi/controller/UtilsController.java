package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.repository.CostumizabilRepository;
import com.donatii.donatiiapi.repository.TagAnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    @GetMapping(value = "/item/{tip}/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> image(@PathVariable("tip") String tip, @PathVariable("url") String url) {
        ByteArrayResource inputStream;
        try {
            inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                    "assets/images/avatars/"+tip+"/"+url+".png"
            )));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentLength(inputStream.contentLength())
                    .body(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nonexistent image!");
    }
}
