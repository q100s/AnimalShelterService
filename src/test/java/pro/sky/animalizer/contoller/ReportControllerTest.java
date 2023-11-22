package pro.sky.animalizer.contoller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.sky.animalizer.controller.ReportController;
import pro.sky.animalizer.model.Report;
import pro.sky.animalizer.service.ReportService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {
    @Mock
    private ReportService reportService;
    @InjectMocks
    private ReportController reportController;
    private MockMvc mockMvc;


    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();

    }

    @Test
    void getReportById() throws Exception {
        mockMvc.perform(get("/report/{id}", 1L))
                .andExpect(status().isOk());

    }
    @Test
    void getReportByTelegramId() throws Exception {
        Collection<Report> listTest = new ArrayList<>();
        when(reportService.findAllReports()).thenReturn(listTest);
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk());

    }
    @Test
    void getReportLastByTelegramId() throws Exception {
        Collection<Report> listTest = new ArrayList<>();
        when(reportService.findAllReports()).thenReturn(listTest);
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk());

    }
    @Test
    void getReports() throws Exception {
        Collection<Report> listTest = new ArrayList<>();
        when(reportService.findAllReports()).thenReturn(listTest);
        mockMvc.perform(get("/report"))
                .andExpect(status().isOk());

    }
}
