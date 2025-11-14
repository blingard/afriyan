package org.ligot.afriyan.learn.service;

import org.ligot.afriyan.learn.dto.*;

import java.util.List;

public interface EnrollmentService {
    
    UserFormationEnrollmentDTO enrollUser(String formationId);
    
    UserFormationEnrollmentDTO getEnrollmentById(String id);
    
    List<UserFormationEnrollmentDTO> getUserEnrollments();
    
    List<UserFormationEnrollmentDTO> getFormationEnrollments(String formationId);
    
    UserFormationEnrollmentDTO getUserEnrollmentForFormation(String formationId);
    
    UserFormationEnrollmentDTO restartFormation(String enrollmentId);
    
    UserFormationEnrollmentDTO abandonFormation(String enrollmentId);
    
    UserProgressDTO updateModuleProgress(UpdateProgressDTO dto);
    
    UserProgressDTO startModule(String enrollmentId, String moduleId);
    
    UserProgressDTO completeModule(String enrollmentId, String moduleId);
    
    List<UserProgressDTO> getEnrollmentProgress(String enrollmentId);
    
    void calculateAndUpdateEnrollmentProgress(String enrollmentId);
    
    // Nouvelles méthodes pour le parcours détaillé
    DetailedProgressDTO getDetailedProgress(String enrollmentId);
    
    void markChapterAsCompleted(MarkChapterCompleteDTO dto);
}
