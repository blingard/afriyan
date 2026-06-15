package org.ligot.afriyan.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ligot.afriyan.Dto.PlanningFamilialDTO;
import org.ligot.afriyan.entities.MethodePlanning;
import org.ligot.afriyan.entities.ObjectifPlanning;
import org.ligot.afriyan.entities.PlanningFamilial;
import org.ligot.afriyan.entities.Utilisateur;
import org.ligot.afriyan.mapper.PlanningFamilialMapper;
import org.ligot.afriyan.mapper.PlanningFamilialLogMapper;
import org.ligot.afriyan.repository.IPlanningFamilialRepository;
import org.ligot.afriyan.repository.IPlanningFamilialLogRepository;
import org.ligot.afriyan.service.ICycleMenstruelService;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanningFamilialServiceTest {

    private PlanningFamilialService planningFamilialService;

    @Mock
    private IPlanningFamilialRepository planningRepository;
    @Mock
    private IPlanningFamilialLogRepository logRepository;
    @Mock
    private PlanningFamilialMapper planningMapper;
    @Mock
    private PlanningFamilialLogMapper logMapper;
    @Mock
    private UtilsService utilsService;
    @Mock
    private ICycleMenstruelService cycleMenstruelService;

    @BeforeEach
    void setUp() {
        planningFamilialService = new PlanningFamilialService(
                planningRepository,
                logRepository,
                planningMapper,
                logMapper,
                utilsService,
                cycleMenstruelService
        );
    }

    @Test
    void testSavePlanning_Success() throws Exception {
        Utilisateur user = new Utilisateur();
        user.setUuid("test-uuid");
        when(utilsService.getUser()).thenReturn(user);

        PlanningFamilialDTO dto = PlanningFamilialDTO.builder()
                .objectif(ObjectifPlanning.CONTRACEPTION)
                .methode(MethodePlanning.PILULE)
                .dateDebut(new Date())
                .rappelActif(false)
                .build();

        PlanningFamilial planning = new PlanningFamilial();
        when(planningMapper.create(any(PlanningFamilialDTO.class))).thenReturn(planning);
        when(planningRepository.save(any(PlanningFamilial.class))).thenReturn(planning);
        when(planningMapper.toDTO(any(PlanningFamilial.class))).thenReturn(dto);

        PlanningFamilialDTO result = planningFamilialService.savePlanning(dto);

        assertNotNull(result);
        assertEquals(ObjectifPlanning.CONTRACEPTION, result.getObjectif());
        verify(planningRepository, times(1)).save(any(PlanningFamilial.class));
    }
}
