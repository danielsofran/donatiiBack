package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.CauzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cauza")
public class CauzaController {
    private final CauzaService service;

    @Autowired
    public CauzaController(CauzaService service) {
        this.service = service;
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id) {
        try {
            Cauza cauza = service.findById(id);
            return ResponseEntity.ok().body(cauza);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        try {
            List<Cauza> cauze = service.findAll();
            return ResponseEntity.ok().body(cauze);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
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
        ByteArrayResource inputStream = null;
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

}
