package pro.sky.animalizer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.animalizer.exceptions.ReportNotFondException;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.repositories.ReportRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private ReportRepository reportRepositoryMock;
    @InjectMocks
    private ReportService reportService;


    Report reportTest = new Report(1, "photoPathTest", "testText");

    @Test
    void createReport() {
        when(reportRepositoryMock.save(reportTest)).thenReturn(reportTest);
        assertEquals(reportRepositoryMock.save(reportTest), reportTest);
    }

    @Test
    void findAllReports() {
        List<Report> listTest = new ArrayList<>();
        when(reportRepositoryMock.findAll()).thenReturn(listTest);
        assertEquals(reportService.findAllReport(), listTest);
    }


    @Test
    void findReportByIdWhenReportIsNotFound() {
        assertThrows(ReportNotFondException.class,
                () -> reportService.findReportById(4L));

    }

    @Test
    void findReportByTelegramId() {
        when(reportRepositoryMock.findByTelegramId(2L)).thenReturn((reportTest));
        assertEquals(reportService.findReportByTelegramId(2L), reportTest);

    }

    @Test
    void findReportById() {
        assertThrows(ReportNotFondException.class,
                () -> reportService.findReportById(4L));

    }

}
