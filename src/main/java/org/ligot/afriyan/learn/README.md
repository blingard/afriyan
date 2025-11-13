# Module E-Learning - Package Learn

## Vue d'ensemble

Ce module implémente un système complet d'e-learning avec les fonctionnalités suivantes :
- Gestion des formations avec modules
- Quiz de fin de module (optionnels)
- Quiz de fin de formation (optionnel)
- Suivi de progression des utilisateurs
- Possibilité de recommencer une formation
- Système de questions à choix multiples

## Architecture

### Entités principales

#### 1. **Formation**
- Contient des modules ordonnés
- Peut avoir un quiz final
- Statuts : DRAFT, PUBLISHED, ARCHIVED
- Niveaux : Débutant, Intermédiaire, Avancé

#### 2. **Module**
- Appartient à une formation
- Contenu (texte/HTML) et vidéo optionnelle
- Peut avoir un quiz de fin de module
- Ordonné dans la formation

#### 3. **Quiz**
- Types : MODULE_QUIZ (fin de module) ou FINAL_QUIZ (fin de formation)
- Score minimum requis pour réussir (par défaut 50%)
- Durée limite optionnelle
- Nombre de tentatives limité ou illimité

#### 4. **Question**
- Questions à choix multiples
- Points attribués par question
- Explication de la réponse correcte

#### 5. **QuestionOption**
- Options de réponse pour chaque question
- Une ou plusieurs réponses correctes possibles

#### 6. **UserFormationEnrollment**
- Inscription d'un utilisateur à une formation
- Suivi de la progression globale
- Statuts : IN_PROGRESS, COMPLETED, FAILED, ABANDONED
- Possibilité de recommencer (restart)

#### 7. **UserProgress**
- Progression par module
- Statuts : NOT_STARTED, IN_PROGRESS, COMPLETED
- Score du quiz du module si applicable

#### 8. **UserQuizAttempt**
- Tentative de passage d'un quiz
- Score obtenu et validation (passé/échoué)
- Durée de la tentative

#### 9. **UserQuizAnswer**
- Réponse d'un utilisateur à une question
- Validation de la correction

## API Endpoints

### Formations

```
POST   /api/learn/formations                    - Créer une formation
PUT    /api/learn/formations/{id}               - Modifier une formation
GET    /api/learn/formations/{id}               - Récupérer une formation
GET    /api/learn/formations                    - Lister toutes les formations
GET    /api/learn/formations/published          - Lister les formations publiées
GET    /api/learn/formations/my-formations      - Mes formations créées
GET    /api/learn/formations/search?keyword=    - Rechercher des formations
GET    /api/learn/formations/level/{niveau}     - Formations par niveau
PUT    /api/learn/formations/{id}/publish       - Publier une formation
PUT    /api/learn/formations/{id}/archive       - Archiver une formation
DELETE /api/learn/formations/{id}               - Supprimer une formation
```

### Modules

```
POST   /api/learn/modules                              - Créer un module
PUT    /api/learn/modules/{id}                         - Modifier un module
GET    /api/learn/modules/{id}                         - Récupérer un module
GET    /api/learn/modules/formation/{formationId}      - Modules d'une formation
DELETE /api/learn/modules/{id}                         - Supprimer un module
PUT    /api/learn/modules/formation/{formationId}/reorder - Réordonner les modules
```

### Inscriptions et Progression

```
POST   /api/learn/enrollments/enroll                   - S'inscrire à une formation
GET    /api/learn/enrollments/{id}                     - Détails d'une inscription
GET    /api/learn/enrollments/my-enrollments           - Mes inscriptions
GET    /api/learn/enrollments/formation/{formationId}  - Inscriptions d'une formation
GET    /api/learn/enrollments/formation/{formationId}/my-enrollment - Mon inscription à une formation
PUT    /api/learn/enrollments/{enrollmentId}/restart   - Recommencer une formation
PUT    /api/learn/enrollments/{enrollmentId}/abandon   - Abandonner une formation
PUT    /api/learn/enrollments/progress/update          - Mettre à jour la progression
PUT    /api/learn/enrollments/progress/start-module    - Commencer un module
PUT    /api/learn/enrollments/progress/complete-module - Terminer un module
GET    /api/learn/enrollments/{enrollmentId}/progress  - Progression d'une inscription
```

### Quiz

```
POST   /api/learn/quiz                        - Créer un quiz
PUT    /api/learn/quiz/{id}                   - Modifier un quiz
GET    /api/learn/quiz/{id}                   - Récupérer un quiz
GET    /api/learn/quiz/module/{moduleId}      - Quiz d'un module
GET    /api/learn/quiz/formation/{formationId} - Quiz final d'une formation
DELETE /api/learn/quiz/{id}                   - Supprimer un quiz

POST   /api/learn/quiz/questions              - Créer une question
PUT    /api/learn/quiz/questions/{id}         - Modifier une question
DELETE /api/learn/quiz/questions/{id}         - Supprimer une question
GET    /api/learn/quiz/{quizId}/questions     - Questions d'un quiz

POST   /api/learn/quiz/attempts/start         - Démarrer une tentative de quiz
POST   /api/learn/quiz/attempts/submit        - Soumettre les réponses d'un quiz
GET    /api/learn/quiz/attempts/{attemptId}   - Détails d'une tentative
GET    /api/learn/quiz/{quizId}/my-attempts   - Mes tentatives pour un quiz
GET    /api/learn/quiz/{quizId}/can-attempt   - Vérifier si peut tenter le quiz
```

## Flux d'utilisation

### 1. Création d'une formation

1. Créer une formation (statut DRAFT)
2. Ajouter des modules ordonnés
3. Pour chaque module, ajouter du contenu et optionnellement un quiz
4. Si la formation a un quiz final, le créer
5. Publier la formation (statut PUBLISHED)

### 2. Inscription et progression d'un apprenant

1. L'utilisateur s'inscrit à une formation
2. Le système crée automatiquement les entrées de progression pour chaque module
3. L'utilisateur commence un module (status IN_PROGRESS)
4. L'utilisateur met à jour sa progression (0-100%)
5. Si le module a un quiz :
   - L'utilisateur démarre une tentative
   - Répond aux questions
   - Soumet les réponses
   - Le système calcule le score et valide
6. Quand le module est terminé, passer au suivant
7. À la fin de tous les modules, si quiz final requis :
   - Passer le quiz final
   - Si réussi : formation COMPLETED
   - Si échoué : formation FAILED
8. Si pas de quiz final : formation COMPLETED automatiquement

### 3. Recommencer une formation

1. L'utilisateur peut recommencer une formation terminée
2. Le système réinitialise toutes les progressions
3. Toutes les tentatives de quiz précédentes sont conservées pour historique

## Logique métier importante

### Progression automatique

- La progression d'une inscription est calculée comme la moyenne des progressions de tous les modules
- Un module est considéré comme complété à 100% de progression
- Si un module a un quiz obligatoire, il doit être réussi pour compléter le module

### Quiz

- Un quiz peut limiter le nombre de tentatives
- Le score minimum est configurable (par défaut 50%)
- Les réponses sont validées automatiquement
- Le résultat d'un quiz de module met à jour la progression du module
- Le résultat du quiz final met à jour le statut de l'inscription

### Validation

- Une formation doit être PUBLISHED pour permettre les inscriptions
- Un utilisateur ne peut s'inscrire qu'une seule fois à une formation
- Les modules doivent être terminés dans l'ordre (logique à implémenter côté front si nécessaire)

## Base de données

Les tables suivantes seront créées automatiquement par JPA :

- `formations`
- `modules`
- `quiz`
- `questions`
- `question_options`
- `user_formation_enrollments`
- `user_progress`
- `user_quiz_attempts`
- `user_quiz_answers`

## Points d'amélioration futurs

1. Système de certificats générés automatiquement
2. Notifications par email à chaque étape
3. Gamification (badges, points, classements)
4. Forum de discussion par formation
5. Ressources téléchargeables par module
6. Questions de différents types (vrai/faux, réponse courte, etc.)
7. Feedback et évaluations des formations
8. Statistiques détaillées pour les formateurs
9. Export des résultats en PDF/Excel
10. Intégration avec système de paiement pour formations payantes

## Exemple d'utilisation avec curl

### Créer une formation
```bash
curl -X POST http://localhost:8080/api/learn/formations \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Introduction à Java",
    "description": "Formation complète pour débutants",
    "niveau": "Débutant",
    "dureeEstimee": 20,
    "withFinalQuiz": true
  }'
```

### S'inscrire à une formation
```bash
curl -X POST http://localhost:8080/api/learn/enrollments/enroll \
  -H "Content-Type: application/json" \
  -d '{"formationId": 1}'
```

### Démarrer un quiz
```bash
curl -X POST http://localhost:8080/api/learn/quiz/attempts/start \
  -H "Content-Type: application/json" \
  -d '{
    "quizId": 1,
    "enrollmentId": 1
  }'
```

### Soumettre les réponses
```bash
curl -X POST http://localhost:8080/api/learn/quiz/attempts/submit \
  -H "Content-Type: application/json" \
  -d '{
    "attemptId": 1,
    "answers": [
      {"questionId": 1, "selectedOptionId": 2},
      {"questionId": 2, "selectedOptionId": 5}
    ]
  }'
```
