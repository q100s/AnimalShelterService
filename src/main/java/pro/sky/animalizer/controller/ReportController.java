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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.service.ReportService;

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
    public Collection<Report> takeAllReports() {
        return reportService.findAllReport();
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
                            description = "Отчет с переданным id не существует"
                    )
            })
    @GetMapping("/{id}")
    public Report findReportById(@Parameter(description = "Идентификатор для поиска") @PathVariable Long id) {
        return reportService.findReportById(id);
    }

    @Operation(
            summary = "Поиск отчета по telegram-идентификатору",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Отчет, найденный по telegram-идентификатору",
                            content = {@Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class)
                            )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Отчет с переданным telegram-id не существует"
                    )
            })
    @GetMapping("/by{telegramId}")
    public ResponseEntity<Report> findReportByTelegramId(@Parameter(description = "Telegram-Идентификатор для поиска")
                                                         @PathVariable Long telegramId) {
        Report reportByTelegramId = reportService.findReportByTelegramId(telegramId);
        if (reportByTelegramId == null) {
            logger.error("There isn't a report with id = " + telegramId);
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(reportByTelegramId);
        }
    }
}
