package org.ligot.afriyan.learn.controller;

import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/api/learn/enrollments")
@CrossOrigin("*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enroll")
    public ResponseEntity<UserFormationEnrollmentDTO> enrollUser(
            @RequestParam String id,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        UserFormationEnrollmentDTO enrollment = enrollmentService.enrollUser(userId, id);
        return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFormationEnrollmentDTO> getEnrollmentById(@PathVariable String id) {
        UserFormationEnrollmentDTO enrollment = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/my-enrollments")
    public ResponseEntity<List<UserFormationEnrollmentDTO>> getMyEnrollments(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<UserFormationEnrollmentDTO> enrollments = enrollmentService.getUserEnrollments(userId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/formation/{formationId}")
    public ResponseEntity<List<UserFormationEnrollmentDTO>> getFormationEnrollments(
            @PathVariable String formationId) {
        List<UserFormationEnrollmentDTO> enrollments = enrollmentService.getFormationEnrollments(formationId);
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/formation/{formationId}/my-enrollment")
    public ResponseEntity<UserFormationEnrollmentDTO> getMyEnrollmentForFormation(
            @PathVariable String formationId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        UserFormationEnrollmentDTO enrollment = enrollmentService.getUserEnrollmentForFormation(userId, formationId);
        return ResponseEntity.ok(enrollment);
    }

    @PutMapping("/{enrollmentId}/restart")
    public ResponseEntity<UserFormationEnrollmentDTO> restartFormation(@PathVariable String enrollmentId) {
        UserFormationEnrollmentDTO enrollment = enrollmentService.restartFormation(enrollmentId);
        return ResponseEntity.ok(enrollment);
    }

    @PutMapping("/{enrollmentId}/abandon")
    public ResponseEntity<UserFormationEnrollmentDTO> abandonFormation(@PathVariable String enrollmentId) {
        UserFormationEnrollmentDTO enrollment = enrollmentService.abandonFormation(enrollmentId);
        return ResponseEntity.ok(enrollment);
    }

    @PutMapping("/progress/update")
    public ResponseEntity<UserProgressDTO> updateModuleProgress(@RequestBody UpdateProgressDTO dto) {
        UserProgressDTO progress = enrollmentService.updateModuleProgress(dto);
        return ResponseEntity.ok(progress);
    }

    @PutMapping("/progress/start-module")
    public ResponseEntity<UserProgressDTO> startModule(
            @RequestParam String enrollmentId,
            @RequestParam String moduleId) {
        UserProgressDTO progress = enrollmentService.startModule(enrollmentId, moduleId);
        return ResponseEntity.ok(progress);
    }

    @PutMapping("/progress/complete-module")
    public ResponseEntity<UserProgressDTO> completeModule(
            @RequestParam String enrollmentId,
            @RequestParam String moduleId) {
        UserProgressDTO progress = enrollmentService.completeModule(enrollmentId, moduleId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/{enrollmentId}/progress")
    public ResponseEntity<List<UserProgressDTO>> getEnrollmentProgress(@PathVariable String enrollmentId) {
        List<UserProgressDTO> progress = enrollmentService.getEnrollmentProgress(enrollmentId);
        return ResponseEntity.ok(progress);
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() != null) {
            return 1L; // À adapter selon votre implémentation
        }
        throw new RuntimeException("Utilisateur non authentifié");
    }
}
