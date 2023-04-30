package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.CauzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cauza")
public class CauzaController {
    private final CauzaService service;

    @Autowired
    public CauzaController(CauzaService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok().body(new User());
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
}
