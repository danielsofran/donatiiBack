package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.Costumizabil;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.interfaces.ICauzaService;
import com.donatii.donatiiapi.service.interfaces.ICostumizabilService;
import com.donatii.donatiiapi.service.interfaces.IUserService;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {
    private final IUserService userService;
    private final ICauzaService cauzaService;
    private final ICostumizabilService costumizabilService;

    @Autowired
    public UserController(IUserService userService, ICauzaService cauzaService, ICostumizabilService costumizabilService) {
        this.userService = userService;
        this.cauzaService = cauzaService;
        this.costumizabilService = costumizabilService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User loginRequest) {
        try {
            User user = userService.login(loginRequest.getEmail(), loginRequest.getParola());
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User registerRequest) {
        try {
            User user = userService.register(registerRequest);
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok().body("User updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().body("User deleted!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/like/{user_id}/{cauza_id}")
    public ResponseEntity<Object> like(@PathVariable("user_id") Long user_id, @PathVariable("cauza_id") Long cauza_id) {
        try {
            userService.like(user_id, cauza_id);
            cauzaService.like(cauza_id, user_id);
            return ResponseEntity.ok().body("Like updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/buy/{user_id}/{costumizabil_id}")
    public ResponseEntity<Object> buy(@PathVariable("user_id") Long user_id, @PathVariable("costumizabil_id") Long costumizabil_id) {
        try {
            userService.buy(user_id, costumizabilService.getCostumizabil(costumizabil_id));
            return ResponseEntity.ok().body("Costumizabil Cumparat!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("/equip/{user_id}/{costumizabil_id}")
    public ResponseEntity<Object> equip(@PathVariable("user_id") Long user_id, @PathVariable("costumizabil_id") Long costumizabil_id) {
        try {
            Set<Costumizabil> res = userService.equip(user_id, costumizabilService.getCostumizabil(costumizabil_id));
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/disequip/{user_id}/{costumizabil_id}")
    public ResponseEntity<Object> disequip(@PathVariable("user_id") Long user_id, @PathVariable("costumizabil_id") Long costumizabil_id) {
        try {
            Set<Costumizabil> res = userService.unequip(user_id, costumizabilService.getCostumizabil(costumizabil_id));
            return ResponseEntity.ok().body(res);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/resources/{user_id}/{coins}/{level}")
    public ResponseEntity<Object> updateResources(@PathVariable("user_id") Long user_id,
                                                  @PathVariable("coins") Long coins,
                                                  @PathVariable(value = "level", required = false) Integer level) {
        try {
            userService.updateResources(user_id, coins, level);
            return ResponseEntity.ok().body("Resources updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
