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
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.ReportService;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
  
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @Operation(
            summary = "Получение всех отчетов, находящихся в базе данных",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция всех отчетов, существующих в базе данных",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report[].class))
                            )
                            }
                    )
            })
    @GetMapping
    public Collection<Report> getAllReports() {
        return reportService.findAllReports();
    }

    @Operation(
            summary = "Поиск отчета по идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет, найденный по идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Отчета с переданным id не существует"
                    )
            })
    @GetMapping("/{id}")
    public Report findUserById(@Parameter(description = "Идентификатор для поиска") @PathVariable long id) {
        return reportService.findReportById(id);
    }

    @Operation(
            summary = "Поиск отчетов по telegram-идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Коллекция отчетов, найденных по telegram-идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report[].class))
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Отчетов с переданным telegram-id не существует"
                    )
            })
    @GetMapping("/by{telegramId}")
    public ResponseEntity<Collection<Report>> findAllByTelegramId(@Parameter(description = "Telegram-Идентификатор для поиска")
                                                 @PathVariable long telegramId) {
        Collection<Report> reportsByTelegramId = reportService.findReportsByTelegramId(telegramId);
        if (reportsByTelegramId == null) {
            logger.error("There isn't a user with id = " + telegramId);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(reportsByTelegramId);
        }
    }
    @Operation(
            summary = "Поиск последнего отправленного отчета пользователем с переданным telegram-идентификатором",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Последний отчет, найденный по telegram-идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Отчетов с переданным telegram-id не существует"
                    )
            })
    @GetMapping("/lastBy{telegramId}")
    public ResponseEntity<Report> findLastByTelegramId(@Parameter(description = "Telegram-Идентификатор для поиска")
                                                               @PathVariable long telegramId) {
        Report lastReportByTelegramId = reportService.findLastReportByTelegramId(telegramId);
        if (lastReportByTelegramId == null) {
            logger.error("У пользователя с телеграм-id " + telegramId + " нет отчетов.");
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(lastReportByTelegramId);
        }
    }


}