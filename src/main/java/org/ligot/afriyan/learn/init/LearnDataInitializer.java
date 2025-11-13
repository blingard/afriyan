package org.ligot.afriyan.learn.init;

import lombok.extern.slf4j.Slf4j;
import org.ligot.afriyan.learn.entities.*;
import org.ligot.afriyan.learn.enumerations.FormationLevel;
import org.ligot.afriyan.learn.repository.*;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.boot.CommandLineRunner;

import java.util.Date;

/**
 * Classe pour initialiser des données de test pour le module e-learning.
 * Décommentez l'annotation @Component pour activer l'initialisation au démarrage.
 */
//@Component
//@Slf4j
public class LearnDataInitializer implements CommandLineRunner {

    private final FormationRepository formationRepository;
    private final ModuleRepository moduleRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository optionRepository;
    private final IUtilisateurRepository utilisateurRepo;

    public LearnDataInitializer(FormationRepository formationRepository, ModuleRepository moduleRepository, QuizRepository quizRepository, QuestionRepository questionRepository, QuestionOptionRepository optionRepository, IUtilisateurRepository utilisateurRepo) {
        this.formationRepository = formationRepository;
        this.moduleRepository = moduleRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
        this.utilisateurRepo = utilisateurRepo;
    }

    @Override
    public void run(String... args) throws Exception {
//        ////log.info("Initialisation des données de test pour le module e-learning...");

        // Vérifier s'il y a déjà des formations
        if (formationRepository.count() > 0) {
            //log.info("Des formations existent déjà. Initialisation annulée.");
            return;
        }

        // Créer une formation exemple
        Formation formation = new Formation();
        formation.setTitre("Introduction à Spring Boot");
        formation.setDescription("Apprenez les bases de Spring Boot, le framework Java le plus populaire pour créer des applications d'entreprise.");
        formation.setNiveau(FormationLevel.BEGINNER);
        formation.setDureeEstimee(15);
        formation.setStatus(Formation.FormationStatus.PUBLISHED);
        formation.setWithFinalQuiz(true);
        formation.setDateCreation(new Date());
        formation.setCreatedBy(1L);
        formation = formationRepository.save(formation);
        //log.info("Formation créée : {}", formation.getTitre());

        // Créer les modules
        Modules module1 = createModule(formation, 1, 
            "Introduction et Installation",
            "Découvrez Spring Boot et installez votre environnement de développement.",
            "Dans ce module, vous allez découvrir ce qu'est Spring Boot, pourquoi l'utiliser, et comment installer tous les outils nécessaires.",
            60,
            true);

        Modules module2 = createModule(formation, 2,
            "Votre première application",
            "Créez votre première application Spring Boot de A à Z.",
            "Vous allez créer une application web simple avec Spring Boot, comprendre la structure du projet et les annotations de base.",
            90,
            true);

        Modules module3 = createModule(formation, 3,
            "REST API avec Spring Boot",
            "Créez des API REST professionnelles.",
            "Apprenez à créer des endpoints REST, gérer les requêtes HTTP, et retourner des données JSON.",
            120,
            true);

        //log.info("3 modules créés pour la formation");

        // Créer un quiz pour le module 1
        Quiz quiz1 = createQuiz(module1, null, "Quiz - Introduction et Installation", Quiz.QuizType.MODULE_QUIZ);
        createQuestion(quiz1, 1, "Qu'est-ce que Spring Boot ?",
            "Spring Boot est un framework qui simplifie le développement d'applications Spring.",
            new String[]{
                "Un framework Java pour créer des applications rapidement",
                "Un langage de programmation",
                "Un système d'exploitation",
                "Une base de données"
            },
            0);

        createQuestion(quiz1, 2, "Quel outil utilise-t-on pour gérer les dépendances dans Spring Boot ?",
            "Maven et Gradle sont les deux outils principaux pour gérer les dépendances.",
            new String[]{
                "NPM",
                "Maven ou Gradle",
                "Composer",
                "Pip"
            },
            1);

        //log.info("Quiz créé pour le module 1 avec 2 questions");

        // Créer un quiz final
        Quiz quizFinal = createQuiz(null, formation, "Quiz Final - Introduction à Spring Boot", Quiz.QuizType.FINAL_QUIZ);
        quizFinal.setScoreMinimum(70);
        quizFinal.setNombreTentativesMax(3);
        quizRepository.save(quizFinal);

        createQuestion(quizFinal, 1, "Quelle annotation transforme une classe en contrôleur REST ?",
            "@RestController combine @Controller et @ResponseBody.",
            new String[]{
                "@Controller",
                "@RestController",
                "@Service",
                "@Component"
            },
            1);

        createQuestion(quizFinal, 2, "Comment démarrer une application Spring Boot ?",
            "La méthode main avec SpringApplication.run() démarre l'application.",
            new String[]{
                "new SpringApplication().start()",
                "SpringApplication.run()",
                "Application.start()",
                "Spring.boot()"
            },
            1);

        createQuestion(quizFinal, 3, "Quel port par défaut utilise Spring Boot ?",
            "Spring Boot démarre par défaut sur le port 8080.",
            new String[]{
                "80",
                "3000",
                "8080",
                "8888"
            },
            2);

        //log.info("Quiz final créé avec 3 questions");
        //log.info("Initialisation des données de test terminée avec succès !");
    }

    private Modules createModule(Formation formation, int ordre, String titre, 
                                String description, String contenu, 
                                int duree, boolean withQuiz) {
        Modules module = new Modules();
        module.setFormation(formation);
        module.setOrdre(ordre);
        module.setTitre(titre);
        module.setDescription(description);
        module.setDureeEstimee(duree);
        module.setWithQuiz(withQuiz);
        module.setDateCreation(new Date());
        return moduleRepository.save(module);
    }

    private Quiz createQuiz(Modules module, Formation formation, String titre, Quiz.QuizType type) {
        Quiz quiz = new Quiz();
        quiz.setModule(module);
        quiz.setFormation(formation);
        quiz.setTitre(titre);
        quiz.setDescription("Testez vos connaissances sur ce qui a été appris.");
        quiz.setType(type);
        quiz.setScoreMinimum(50);
        quiz.setDureeLimite(30);
        quiz.setDateCreation(new Date());
        return quizRepository.save(quiz);
    }

    private void createQuestion(Quiz quiz, int ordre, String intitule, 
                                String explication, String[] options, int correctIndex) {
        Question question = new Question();
        question.setQuiz(quiz);
        question.setOrdre(ordre);
        question.setIntitule(intitule);
        question.setExplication(explication);
        question.setPoints(1);
        question.setDateCreation(new Date());
        question = questionRepository.save(question);

        for (int i = 0; i < options.length; i++) {
            QuestionOption option = new QuestionOption();
            option.setQuestion(question);
            option.setTexte(options[i]);
            option.setIsCorrect(i == correctIndex);
            option.setOrdre(i + 1);
            optionRepository.save(option);
        }
    }
}
