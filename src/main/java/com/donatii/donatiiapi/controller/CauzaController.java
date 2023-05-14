package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.CauzaAdapost;
import com.donatii.donatiiapi.model.TagAnimal;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.CauzaService;
import com.donatii.donatiiapi.service.UserService;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/cauza")
public class CauzaController {
    private final CauzaService service;
    private final UserService userService;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CauzaController(CauzaService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Object> save(@PathVariable("userId") Long userId,@RequestBody Cauza cauza) {
        try {
            try {
                cauza.setSustinatori(new HashSet<>());
                cauza.setPoze(new HashSet<>());
                if(cauza instanceof CauzaAdapost) {//preventing detached entity error(duplicates)
                    Set<TagAnimal> tags = ((CauzaAdapost) cauza).getTaguri();
                    ((CauzaAdapost) cauza).setTaguri(new HashSet<>());
                    for (TagAnimal tag : tags) {
                        ((CauzaAdapost) cauza).getTaguri().add(entityManager.find(TagAnimal.class, tag.getId()));
                    }
                }
                User owner = userService.findById(userId);
                cauza = service.save(cauza);
                owner.getCauze().add(cauza);
                userService.save(owner);
            }
            catch (NotFoundException e) {
                return ResponseEntity.badRequest().body("User not found!");
            }
            return ResponseEntity.ok().body(cauza);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Cauza cauza = service.findById(id);
            return ResponseEntity.ok().body(cauza);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> findAll() {
        try {
            List<Cauza> cauze = service.findAll();
            return ResponseEntity.ok().body(cauze);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody Cauza cauza) {
        try {
            service.update(id, cauza);
            return ResponseEntity.ok().body("Cauza updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().body("Cauza deleted!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/image/{url}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Object> image(@PathVariable("url") String url) {
        ByteArrayResource inputStream;
        try {
            inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                    "assets/images/cases/" + url
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

    @PostMapping("/saveImages/{id}")
    public ResponseEntity<String> savePictures(@PathVariable("id") Long id, @RequestParam("pictures") MultipartFile[] pictures) {
        for (MultipartFile picture : pictures) {
            try {
                Files.write(Paths.get("assets/images/cases/" + id + picture.getOriginalFilename()), picture.getBytes());
                service.savePicture("http://localhost:8080/cauza/image/" + id + picture.getOriginalFilename(), id);
            } catch (IOException | NotFoundException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Pictures saved successfully");

        //service.savePicture("http://localhost:8080/cauza/image/" + id + picture.getOriginalFilename(), id);
    }

}
