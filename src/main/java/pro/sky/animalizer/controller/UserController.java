package pro.sky.animalizer.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalizer.model.Shelter_user;
import pro.sky.animalizer.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public List<Shelter_user> getAllUsers() {
       return userService.getAllUsers();

    }

    @GetMapping("/getUserById"+"{id}")
    public Shelter_user findUserById(@PathVariable long id) {
       return userService.findUserById(id);
    }

    @PostMapping
    public Shelter_user createUser(@RequestBody Shelter_user shelterUser) {
        return userService.createUser(shelterUser);
    }

    @PutMapping("{id}")
    public ResponseEntity<Shelter_user> editUser(@PathVariable long id,
            @RequestBody Shelter_user shelterUser) {
        Shelter_user shelterUserCheck = userService.editUser(id,shelterUser);
        if (shelterUserCheck == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelterUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Shelter_user> removeUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }


}
