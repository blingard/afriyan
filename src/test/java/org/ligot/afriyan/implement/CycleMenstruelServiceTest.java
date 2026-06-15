package org.ligot.afriyan.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ligot.afriyan.Dto.CycleProjectionDTO;
import org.ligot.afriyan.Dto.SimulationRequestDTO;
import org.ligot.afriyan.mapper.CycleMenstruelMapper;
import org.ligot.afriyan.mapper.PeriodLogMapper;
import org.ligot.afriyan.repository.ICycleMenstruelSettingsRepository;
import org.ligot.afriyan.repository.IPeriodLogRepository;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CycleMenstruelServiceTest {

    private CycleMenstruelService cycleMenstruelService;

    @Mock
    private ICycleMenstruelSettingsRepository settingsRepository;
    @Mock
    private IPeriodLogRepository periodLogRepository;
    @Mock
    private CycleMenstruelMapper settingsMapper;
    @Mock
    private PeriodLogMapper periodLogMapper;
    @Mock
    private UtilsService utilsService;
    @Mock
    private TwilioService twilioService;

    @BeforeEach
    void setUp() {
        cycleMenstruelService = new CycleMenstruelService(
                settingsRepository,
                periodLogRepository,
                settingsMapper,
                periodLogMapper,
                utilsService,
                twilioService
        );
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void testSimulateCycle_Success() {
        LocalDate startDate = LocalDate.of(2026, 6, 1);
        SimulationRequestDTO dto = SimulationRequestDTO.builder()
                .dateDernieresRegles(toDate(startDate))
                .dureeCycleMoyenne(28)
                .dureeReglesMoyenne(5)
                .nbCycles(3)
                .build();

        List<CycleProjectionDTO> projections = cycleMenstruelService.simulateCycle(dto);

        assertNotNull(projections);
        assertEquals(3, projections.size());

        // Cycle 1:
        // Start date: 2026-06-01 + 28 days = 2026-06-29
        // End date: 2026-06-29 + 5 - 1 = 2026-07-03
        // Ovulation: 2026-06-29 + 28 - 14 = 2026-07-13
        // Fertile start: 2026-07-13 - 5 days = 2026-07-08
        // Fertile end: 2026-07-13 + 1 day = 2026-07-14
        CycleProjectionDTO p1 = projections.get(0);
        
        LocalDate expectedStart = LocalDate.of(2026, 6, 29);
        LocalDate expectedEnd = LocalDate.of(2026, 7, 3);
        LocalDate expectedOvulation = LocalDate.of(2026, 7, 13);
        LocalDate expectedFertileStart = LocalDate.of(2026, 7, 8);
        LocalDate expectedFertileEnd = LocalDate.of(2026, 7, 14);

        assertEquals(toDate(expectedStart), p1.getDateDebutRegles());
        assertEquals(toDate(expectedEnd), p1.getDateFinRegles());
        assertEquals(toDate(expectedOvulation), p1.getDateOvulation());
        assertEquals(toDate(expectedFertileStart), p1.getDebutPeriodeFertile());
        assertEquals(toDate(expectedFertileEnd), p1.getFinPeriodeFertile());
    }
}
