package org.ligot.afriyan.learn.controller;

import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/learn/quiz")
@CrossOrigin("*")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizCreateDTO dto) {
        QuizDTO created = quizService.createQuiz(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(
            @PathVariable String id,
            @RequestBody QuizCreateDTO dto) {
        QuizDTO updated = quizService.updateQuiz(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable String id) {
        QuizDTO quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<QuizDTO> getQuizByModuleId(@PathVariable String moduleId) {
        QuizDTO quiz = quizService.getQuizByModuleId(moduleId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/formation/{formationId}")
    public ResponseEntity<QuizDTO> getQuizByFormationId(@PathVariable String formationId) {
        QuizDTO quiz = quizService.getQuizByFormationId(formationId);
        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/questions")
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionCreateDTO dto) {
        QuestionDTO created = quizService.createQuestion(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable String id,
            @RequestBody QuestionCreateDTO dto) {
        QuestionDTO updated = quizService.updateQuestion(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        quizService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuizId(@PathVariable String quizId) {
        List<QuestionDTO> questions = quizService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/attempts/start")
    public ResponseEntity<UserQuizAttemptDTO> startQuizAttempt(
            @RequestParam String quizId, @RequestParam String enrollmentId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        UserQuizAttemptDTO attempt = quizService.startQuizAttempt(
                userId, quizId, enrollmentId);
        return new ResponseEntity<>(attempt, HttpStatus.CREATED);
    }

    @PostMapping("/attempts/submit")
    public ResponseEntity<UserQuizAttemptDTO> submitQuizAttempt(@RequestBody SubmitQuizDTO dto) {
        UserQuizAttemptDTO attempt = quizService.submitQuizAttempt(dto);
        return ResponseEntity.ok(attempt);
    }

    @GetMapping("/attempts/{attemptId}")
    public ResponseEntity<UserQuizAttemptDTO> getAttemptById(@PathVariable String attemptId) {
        UserQuizAttemptDTO attempt = quizService.getAttemptById(attemptId);
        return ResponseEntity.ok(attempt);
    }

    @GetMapping("/{quizId}/my-attempts")
    public ResponseEntity<List<UserQuizAttemptDTO>> getMyQuizAttempts(
            @PathVariable String quizId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<UserQuizAttemptDTO> attempts = quizService.getUserQuizAttempts(userId, quizId);
        return ResponseEntity.ok(attempts);
    }

    @GetMapping("/{quizId}/can-attempt")
    public ResponseEntity<Boolean> canUserAttemptQuiz(
            @PathVariable String quizId,
            Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean canAttempt = quizService.canUserAttemptQuiz(userId, quizId);
        return ResponseEntity.ok(canAttempt);
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() != null) {
            return 1L; // À adapter selon votre implémentation
        }
        throw new RuntimeException("Utilisateur non authentifié");
    }
}
