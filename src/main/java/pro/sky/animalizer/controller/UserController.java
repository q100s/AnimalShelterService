package pro.sky.animalizer.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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
    public User findUserById(@Parameter(description = "Идентификатор для поиска") @PathVariable long id) {
        return userService.findUserById(id);
    }

    @Operation(
            summary = "Поиск пользователя по telegram-идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь, найденный по telegram-идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователя с переданным telegram-id не существует"
                    )
            })
    @GetMapping("/by{telegramId}")
    public ResponseEntity<User> findByTelegramId(@Parameter(description = "Telegram-Идентификатор для поиска")
                                                     @PathVariable long telegramId) {
        User userByTelegramId = userService.findUserByTelegramId(telegramId);
        if (userByTelegramId == null) {
            logger.error("There isn't a user with id = " + telegramId);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(userByTelegramId);
        }
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
    public User createUser(@Parameter(description = "Добавляемый пользователь")@RequestBody User shelterUser) {
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
                                         @Parameter(description = "Отредактированный пользователь") @RequestBody User shelterUser) {
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