package pro.sky.animalizer.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.animalizer.exceptions.UserNotFoundException;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.model.User;
import pro.sky.animalizer.model.UserType;
import pro.sky.animalizer.service.ReportService;
import pro.sky.animalizer.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Component
public class Notifier {
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final ReportService reportService;

    public Notifier(TelegramBot telegramBot,
                    UserService userService,
                    ReportService reportService) {
        this.telegramBot = telegramBot;
        this.userService = userService;
        this.reportService = reportService;
    }

    @Scheduled(fixedDelay = 43_200_000L)
    public void sendWarningToAdopter() {
        List<Long> adoptersTelegramIds = getAdoptersId();
        for (Long id : adoptersTelegramIds) {
            Report checkedReport = reportService.findLastReportByTelegramId(id);
            if (checkedReport != null && checkedReport.getReportDate().isBefore(LocalDate.now().minusDays(2))) {
                telegramBot.execute(new SendMessage(id, "Ты давно не отправлял отчет"));
            }
        }
    }

    @Scheduled(fixedDelay = 43_200_000L)
    public void sendWarningToVolunteer() {
        Long volunteerChatId = userService.getAllUsers().stream()
                .filter(user -> user.getUserType().equals(UserType.VOLUNTEER))
                .findAny().orElseThrow(UserNotFoundException::new)
                .getTelegramId();
        List<Long> adoptersTelegramIds = getAdoptersId();
        for (Long id : adoptersTelegramIds) {
            Report checkedReport = reportService.findLastReportByTelegramId(id);
            if (checkedReport != null && checkedReport.getReportDate().isBefore(LocalDate.now().minusDays(3))) {
                telegramBot.execute(new SendMessage
                        (volunteerChatId, "Пользователь с телеграм-id " + id + " 2 дня не отправлял отчет"));
            }
        }
    }

    private List<Long> getAdoptersId() {
        List<Long> adoptersTelegramId = userService.getAllUsers().stream()
                .filter(user -> user.getUserType().equals(UserType.ADOPTER))
                .map(User::getTelegramId)
                .toList();
        return adoptersTelegramId;
    }
}