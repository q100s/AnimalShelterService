package pro.sky.animalizer.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
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

    @Operation(
            summary = "Поиск всех пользователей, находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех юзеров, существующих в базе данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User[].class))
                            )
                            }
                    )
            })
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();

    }

    @Operation(
            summary = "Поиск пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, найденный по идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователя с переданным id не существует"
                    )
            })
    @GetMapping("/{id}")
    public User findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/{telegramId}")
    public User findByTelegramId(@PathVariable long telegramId) {
        return userService.findUserByTelegramId(telegramId);
    }

    @Operation(
            summary = "Добавление пользователя в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавляемый пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь добавлен в базу данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    )
            })
    @PostMapping
    public User createUser(@RequestBody User shelterUser) {
        return userService.createUser(shelterUser);
    }

    @Operation(
            summary = "Изменение пользователя в базе данных по искомому идентификатору",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отредактированный пользователь",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь с переданным илентификатором изменен",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным id не существует"
                    )
            })
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@Parameter(description = "Идентификатор для поиска") @PathVariable long id,
                                         @RequestBody User shelterUser) {
        User shelterUserCheck = userService.editUser(id, shelterUser);
        if (shelterUserCheck == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shelterUser);
    }

    @Operation(
            summary = "Удаление пользователя по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь с переданным илентификатором удален",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным id не существует"
                    )
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@Parameter(description = "Идентификатор для поиска") @PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}