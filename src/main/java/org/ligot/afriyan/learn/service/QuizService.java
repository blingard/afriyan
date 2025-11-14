package org.ligot.afriyan.learn.service;

import org.ligot.afriyan.learn.dto.*;

import java.util.List;

public interface QuizService {
    
    QuizDTO createQuiz(QuizCreateDTO dto);
    
    QuizDTO updateQuiz(String id, QuizCreateDTO dto);
    
    QuizDTO getQuizById(String id);
    
    QuizDTO getQuizByModuleId(String moduleId);
    
    QuizDTO getQuizByFormationId(String formationId);
    
    void deleteQuiz(String id);
    
    QuestionDTO createQuestion(QuestionCreateDTO dto);
    
    QuestionDTO updateQuestion(String id, QuestionCreateDTO dto);
    
    void deleteQuestion(String id);
    
    List<QuestionDTO> getQuestionsByQuizId(String quizId);
    
    UserQuizAttemptDTO startQuizAttempt(String quizId, String enrollmentId);
    
    UserQuizAttemptDTO submitQuizAttempt(SubmitQuizDTO dto);
    
    List<UserQuizAttemptDTO> getUserQuizAttempts(String quizId);
    
    UserQuizAttemptDTO getAttemptById(String attemptId);
    
    boolean canUserAttemptQuiz(String quizId);
}
