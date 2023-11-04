package pro.sky.animalizer.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalizer.model.User;
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
    public List<User> getAllUsers() {
       return userService.getAllUsers();

    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
       return userService.findUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User shelterUser) {
        return userService.createUser(shelterUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable long id,
                                         @RequestBody User shelterUser) {
        User shelterUserCheck = userService.editUser(id,shelterUser);
        if (shelterUserCheck == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelterUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }


}
