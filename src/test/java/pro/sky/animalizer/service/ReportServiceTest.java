package pro.sky.animalizer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalizer.exceptions.ReportNotFondException;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.repositories.ReportRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private ReportRepository reportRepositoryMock;
    @InjectMocks
    private ReportService reportService;
    Report reportTest = new Report(LocalDate.now(),"photoPathTest", "testText", 1L);

    List<Report> reportsByTelegramId = new ArrayList<>();
    List<Report> emptyList = new ArrayList<>();


    @Test
    void createReport() {
        when(reportRepositoryMock.save(reportTest)).thenReturn(reportTest);
        assertEquals(reportRepositoryMock.save(reportTest), reportTest);
    }

    @Test
    void findAllReports() {
        List<Report> listTest = new ArrayList<>();
        when(reportRepositoryMock.findAll()).thenReturn(listTest);
        assertEquals(reportService.findAllReports(), listTest);
    }


    @Test
    void findReportByIdWhenReportIsNotFound() {
        assertThrows(ReportNotFondException.class,
                () -> reportService.findReportById(4L));
    }

    @Test
    void findReportByTelegramId() {
        reportsByTelegramId.add(reportTest);
        when(reportRepositoryMock.findAllByTelegramId(1L)).thenReturn(reportsByTelegramId);
        assertEquals(reportService.findReportsByTelegramId(1L), reportsByTelegramId);
        assertEquals(reportService.findReportsByTelegramId(2L), emptyList);
    }

    @Test
    void findReportById() {
        assertThrows(ReportNotFondException.class,
                () -> reportService.findReportById(4L));
    }
}