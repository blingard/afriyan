package org.ligot.afriyan.learn.controller;

import jakarta.annotation.security.RolesAllowed;
import org.ligot.afriyan.learn.dto.*;
import org.ligot.afriyan.learn.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("api/learn/quiz")
    @RolesAllowed(value = {"CREATE_QUIZ"})
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizCreateDTO dto) {
        QuizDTO created = quizService.createQuiz(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("api/learn/quiz/{id}")
    @RolesAllowed(value = {"UPDATE_QUIZ"})
    public ResponseEntity<QuizDTO> updateQuiz(
            @PathVariable String id,
            @RequestBody QuizCreateDTO dto) {
        QuizDTO updated = quizService.updateQuiz(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("api/learn/quiz/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable String id) {
        QuizDTO quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("api/learn/quiz/module/{moduleId}")
    public ResponseEntity<QuizDTO> getQuizByModuleId(@PathVariable String moduleId) {
        QuizDTO quiz = quizService.getQuizByModuleId(moduleId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("api/quiz/module/{moduleId}")
    public ResponseEntity<QuizUSerDTO> getQuizUserByModuleId(@PathVariable String moduleId) {
        QuizUSerDTO quiz = quizService.getQuizUserByModuleId(moduleId);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("api/learn/quiz/formation/{formationId}")
    @RolesAllowed(value = {"GET_QUIZ"})
    public ResponseEntity<QuizDTO> getQuizByFormationId(@PathVariable String formationId) {
        QuizDTO quiz = quizService.getQuizByFormationId(formationId);
        return ResponseEntity.ok(quiz);
    }

    @DeleteMapping("api/learn/quiz/{id}")
    @RolesAllowed(value = {"UPDATE_QUIZ"})
    public ResponseEntity<Void> deleteQuiz(@PathVariable String id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("api/learn/quiz/questions")
    @RolesAllowed(value = {"CREATE_QUESTION"})
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionCreateDTO dto) {
        QuestionDTO created = quizService.createQuestion(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("api/learn/quiz/questions/{id}")
    @RolesAllowed(value = {"UPDATE_QUESTION"})
    public ResponseEntity<QuestionDTO> updateQuestion(
            @PathVariable String id,
            @RequestBody QuestionCreateDTO dto) {
        QuestionDTO updated = quizService.updateQuestion(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("api/learn/quiz/questions/{id}")
    @RolesAllowed(value = {"UPDATE_QUESTION"})
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        quizService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("api/learn/quiz/{quizId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestionsByQuizId(@PathVariable String quizId) {
        List<QuestionDTO> questions = quizService.getQuestionsByQuizId(quizId);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("api/learn/quiz/attempts/start")
    public ResponseEntity<UserQuizAttemptDTO> startQuizAttempt(
            @RequestParam String quizId, @RequestParam String enrollmentId) {
        UserQuizAttemptDTO attempt = quizService.startQuizAttempt(quizId, enrollmentId);
        return new ResponseEntity<>(attempt, HttpStatus.CREATED);
    }

    @PostMapping("api/learn/quiz/attempts/submit")
    public ResponseEntity<UserQuizAttemptDTO> submitQuizAttempt(@RequestBody SubmitQuizDTO dto) {
        UserQuizAttemptDTO attempt = quizService.submitQuizAttempt(dto);
        return ResponseEntity.ok(attempt);
    }

    @GetMapping("api/learn/quiz/attempts/{attemptId}")
    public ResponseEntity<UserQuizAttemptDTO> getAttemptById(@PathVariable String attemptId) {
        UserQuizAttemptDTO attempt = quizService.getAttemptById(attemptId);
        return ResponseEntity.ok(attempt);
    }

    @GetMapping("api/learn/quiz/{quizId}/my-attempts")
    public ResponseEntity<List<UserQuizAttemptDTO>> getMyQuizAttempts(
            @PathVariable String quizId) {
        List<UserQuizAttemptDTO> attempts = quizService.getUserQuizAttempts(quizId);
        return ResponseEntity.ok(attempts);
    }

    @GetMapping("api/learn/quiz/{quizId}/can-attempt")
    public ResponseEntity<Boolean> canUserAttemptQuiz(@PathVariable String quizId) {
        boolean canAttempt = quizService.canUserAttemptQuiz(quizId);
        return ResponseEntity.ok(canAttempt);
    }
}
