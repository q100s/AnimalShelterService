package pro.sky.animalizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.animalizer.model.Pet;
import pro.sky.animalizer.service.PetService;

import java.util.Collection;

public class PetContoller { 
    private final PetService service;

    public PetContoller(PetService service) {
        this.service = service;
    }

    @Operation(
            summary = "Поиск всех питомецов, находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех питомецов, существующих в базе данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet[].class))
                            )
                            }
                    )
            })
    @GetMapping
    public Collection<Pet> getAllPet() {
        return service.getAllPet();
    }

    @Operation(
            summary = "Поиск питомца по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "питомец, найденный по идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "питомеца с переданным id не существует"
                    )
            })
    @GetMapping("/{id}")
    public Pet getPetById(@Parameter(description = "Идентификатор для поиска") @PathVariable("id") Long id) {
        return service.getPetById(id);
    }

    @Operation(
            summary = "Добавление питомеца в базу данных",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавляемый питомец",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "питомец добавлен в базу данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    )
            })
    @PostMapping
    public Pet createPet(@RequestBody Pet Pet) {
        return service.createPet(Pet);
    }

    @Operation(
            summary = "Изменение питомеца в базе данных по искомому идентификатору",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отредактированный питомец",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Pet.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "питомец с переданным илентификатором изменен",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "питомеца с переданным id не существует"
                    )
            })
    @PutMapping("/{id}")
    public Pet editPet(@Parameter(description = "Идентификатор для поиска") @PathVariable("id") Long id, @RequestBody Pet Pet) {
        return service.editPet(id, Pet);
    }

    @Operation(
            summary = "Удаление питомеца по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "питомец с переданным илентификатором удален",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Pet.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "питомеца с переданным id не существует"
                    )
            })
    @DeleteMapping("/{id}")
    public void deletePet(@Parameter(description = "Идентификатор для поиска") @PathVariable("id") Long id) {
        service.deletePet(id);
    }
}
