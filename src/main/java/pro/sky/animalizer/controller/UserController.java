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
                            description = "Пользователя с переданным идентификатором отсутствует в базе данных"
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
                            description = "Пользователя с переданным telegram-идентификатором отсутствует в базе данных"
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
    public User createUser(@Parameter(description = "Добавляемый пользователь") @RequestBody User shelterUser) {
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
                            description = "Пользователь с переданным идентификатором изменен",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором не существует"
                    )
            })
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@Parameter(description = "Идентификатор для поиска") @PathVariable Long id,
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
                            description = "Пользователь с переданным идентификатором удален",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<User> removeUser(@Parameter(description = "Идентификатор для поиска") @PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Соединение пользователя с переданным идентификатором с питомцем с переданным идентификатором",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Усыновитель с присвоенным питомцем",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователю присвоен статус усыновителя и прикреплен питомец",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Питомец с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @PutMapping("/setPet")
    public ResponseEntity<User> connectUserAndPet(
            @Parameter(description = "Идентификатор для поиска пользователя") @RequestParam Long userId,
            @Parameter(description = "Идентификатор для поиска питомца") @RequestParam Long petId) {
        User adopter = userService.connectUserAndPet(userId, petId);
        if (adopter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adopter);
    }

    @Operation(
            summary = "Соединение пользователя с переданным идентификатором с питомцем с переданным идентификатором",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Усыновитель с присвоенным питомцем",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = User.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Усыновителю продлен испытательный срок на переданное количество дней",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @PutMapping("/extendTrialPeriod")
    public ResponseEntity<User> extendTrialPeriod(
            @Parameter(description = "Идентификатор для поиска пользователя") @RequestParam Long userId,
            @Parameter(description = "Количество дней, на которое необходимо продлить испытательный срок") @RequestParam int additionalDays) {
        User adopter = userService.extendTrialPeriod(userId, additionalDays);
        if (adopter == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adopter);
    }

    @Operation(
            summary = "Подтверждение провала испытательного срока усыновителем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь не прошёл испытательный срок и удален из базы данных, " +
                                    "питомцу в поле усыновителдь присвоен null",
                            content = {@Content(
                                    schema = @Schema(implementation = String.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @DeleteMapping("/reject")
    public String rejectAdopter(@Parameter(description = "Идентификатор для поиска") @RequestParam Long id) {
        return userService.rejectAdopter(id);
    }

    @Operation(
            summary = "Подтверждение прохождения испытательного срока усыновителем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь прошёл испытательный срок и удален из базы данных",
                            content = {@Content(
                                    schema = @Schema(implementation = String.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @DeleteMapping("/approve")
    public String approveAdopter(@Parameter(description = "Идентификатор для поиска") @RequestParam Long id) {
        return userService.approveAdopterAndSendCongratulation(id);
    }

    @Operation(
            summary = "Поиск всех усыновителей, у которых наступил конец испытательного срока",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех усыновителей, у которых наступил конец испытательного срока",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User[].class))
                            )
                            }
                    )
            })
    @GetMapping("/allWithEndOfTrialPeriod")
    public List<User> findAllWithEndOfTrialPeriod() {
        return userService.findAllWithEndOfTrialPeriod();
    }

    @Operation(
            summary = "Отправка пользователю с переданным идентификатором о плохом заполнении отчетов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователю с переданным идентификатором отправлено уведомление о плохом заполнении отчетов",
                            content = {@Content(
                                    schema = @Schema(implementation = String.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь с переданным идентификатором отсутствует в базе данных"
                    )
            })
    @GetMapping("/notification")
    public String sendMessageAboutBadReport(Long userId) {
        return userService.sendMessageAboutBadReport(userId);
    }
}