package pro.sky.animalizer.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public void getAllUsers() {
        userService.getAllUsers();

    }

    @PostMapping
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @PutMapping
    public ResponseEntity<User> editUser(@RequestBody User user) {
        User userCheck = userService.editUser(user);
        if (userCheck == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> removeUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }


}
