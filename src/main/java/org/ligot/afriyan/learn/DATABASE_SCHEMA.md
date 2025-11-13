# Schéma de la base de données - Module E-Learning

## Diagramme des relations

```
┌─────────────────────┐
│   FORMATIONS        │
├─────────────────────┤
│ id (PK)            │
│ titre              │
│ description        │
│ image_couverture   │
│ niveau             │
│ duree_estimee      │
│ status             │
│ with_final_quiz    │
│ date_creation      │
│ date_modification  │
│ created_by         │
└─────────────────────┘
         │
         │ 1:N
         ▼
┌─────────────────────┐
│    MODULES          │
├─────────────────────┤
│ id (PK)            │
│ titre              │
│ description        │
│ ordre              │
│ duree_estimee      │
│ contenu            │
│ video_url          │
│ with_quiz          │
│ formation_id (FK)  │
│ date_creation      │
│ date_modification  │
└─────────────────────┘
         │
         │ 1:1
         ▼
┌─────────────────────┐
│      QUIZ           │
├─────────────────────┤
│ id (PK)            │
│ titre              │
│ description        │
│ type               │ (MODULE_QUIZ / FINAL_QUIZ)
│ score_minimum      │
│ duree_limite       │
│ nombre_tentatives_max│
│ module_id (FK)     │ (nullable)
│ formation_id (FK)  │ (nullable)
│ date_creation      │
│ date_modification  │
└─────────────────────┘
         │
         │ 1:N
         ▼
┌─────────────────────┐
│   QUESTIONS         │
├─────────────────────┤
│ id (PK)            │
│ intitule           │
│ explication        │
│ points             │
│ ordre              │
│ image_url          │
│ quiz_id (FK)       │
│ date_creation      │
│ date_modification  │
└─────────────────────┘
         │
         │ 1:N
         ▼
┌─────────────────────┐
│ QUESTION_OPTIONS    │
├─────────────────────┤
│ id (PK)            │
│ texte              │
│ is_correct         │
│ ordre              │
│ question_id (FK)   │
└─────────────────────┘


┌─────────────────────┐         ┌─────────────────────┐
│   UTILISATEURS      │         │   FORMATIONS        │
│   (users table)     │         │                     │
└─────────────────────┘         └─────────────────────┘
         │                               │
         │                               │
         │           N:N                 │
         └───────────────────────────────┘
                     │
                     ▼
         ┌─────────────────────────┐
         │ USER_FORMATION_         │
         │   ENROLLMENTS           │
         ├─────────────────────────┤
         │ id (PK)                │
         │ user_id (FK)           │
         │ formation_id (FK)      │
         │ status                 │
         │ progression_pourcent   │
         │ date_debut             │
         │ date_fin               │
         │ score_final            │
         │ certificat_url         │
         │ date_inscription       │
         │ date_modification      │
         └─────────────────────────┘
                     │
                     │ 1:N
                     ▼
         ┌─────────────────────────┐
         │   USER_PROGRESS         │
         ├─────────────────────────┤
         │ id (PK)                │
         │ enrollment_id (FK)     │
         │ module_id (FK)         │
         │ status                 │
         │ progression_pourcent   │
         │ date_debut             │
         │ date_completion        │
         │ score_quiz             │
         │ quiz_passed            │
         │ date_creation          │
         │ date_modification      │
         └─────────────────────────┘


┌─────────────────────┐         ┌─────────────────────┐
│   UTILISATEURS      │         │      QUIZ           │
└─────────────────────┘         └─────────────────────┘
         │                               │
         │           N:N                 │
         └───────────────────────────────┘
                     │
                     ▼
         ┌─────────────────────────┐
         │  USER_QUIZ_ATTEMPTS     │
         ├─────────────────────────┤
         │ id (PK)                │
         │ user_id (FK)           │
         │ quiz_id (FK)           │
         │ enrollment_id (FK)     │
         │ numero_tentative       │
         │ score_obtenu           │
         │ points_obtenus         │
         │ points_totaux          │
         │ is_passed              │
         │ is_completed           │
         │ date_debut             │
         │ date_fin               │
         │ duree_secondes         │
         └─────────────────────────┘
                     │
                     │ 1:N
                     ▼
         ┌─────────────────────────┐
         │  USER_QUIZ_ANSWERS      │
         ├─────────────────────────┤
         │ id (PK)                │
         │ attempt_id (FK)        │
         │ question_id (FK)       │
         │ selected_option_id(FK) │
         │ is_correct             │
         │ points_obtenus         │
         │ date_reponse           │
         └─────────────────────────┘
```

## Tables principales

### 1. formations
Stocke les formations disponibles.

**Clés étrangères :**
- Aucune

**Relations :**
- 1:N avec modules
- 1:1 avec quiz (quiz final optionnel)
- 1:N avec user_formation_enrollments

### 2. modules
Contient les modules de chaque formation.

**Clés étrangères :**
- formation_id → formations(id)

**Relations :**
- N:1 avec formations
- 1:1 avec quiz (quiz de module optionnel)
- 1:N avec user_progress

### 3. quiz
Définit les quiz (de module ou final).

**Clés étrangères :**
- module_id → modules(id) [nullable]
- formation_id → formations(id) [nullable]

**Relations :**
- 1:1 avec modules (si quiz de module)
- 1:1 avec formations (si quiz final)
- 1:N avec questions
- 1:N avec user_quiz_attempts

### 4. questions
Questions des quiz.

**Clés étrangères :**
- quiz_id → quiz(id)

**Relations :**
- N:1 avec quiz
- 1:N avec question_options
- 1:N avec user_quiz_answers

### 5. question_options
Options de réponse pour chaque question.

**Clés étrangères :**
- question_id → questions(id)

**Relations :**
- N:1 avec questions
- 1:N avec user_quiz_answers (via selected_option_id)

### 6. user_formation_enrollments
Inscriptions des utilisateurs aux formations.

**Clés étrangères :**
- user_id → users(id)
- formation_id → formations(id)

**Contraintes :**
- UNIQUE(user_id, formation_id) - Un utilisateur ne peut s'inscrire qu'une fois

**Relations :**
- N:1 avec users
- N:1 avec formations
- 1:N avec user_progress
- 1:N avec user_quiz_attempts

### 7. user_progress
Progression des utilisateurs par module.

**Clés étrangères :**
- enrollment_id → user_formation_enrollments(id)
- module_id → modules(id)

**Contraintes :**
- UNIQUE(enrollment_id, module_id) - Une seule progression par module et inscription

**Relations :**
- N:1 avec user_formation_enrollments
- N:1 avec modules

### 8. user_quiz_attempts
Tentatives de quiz par les utilisateurs.

**Clés étrangères :**
- user_id → users(id)
- quiz_id → quiz(id)
- enrollment_id → user_formation_enrollments(id) [nullable]

**Relations :**
- N:1 avec users
- N:1 avec quiz
- N:1 avec user_formation_enrollments
- 1:N avec user_quiz_answers

### 9. user_quiz_answers
Réponses des utilisateurs aux questions.

**Clés étrangères :**
- attempt_id → user_quiz_attempts(id)
- question_id → questions(id)
- selected_option_id → question_options(id)

**Relations :**
- N:1 avec user_quiz_attempts
- N:1 avec questions
- N:1 avec question_options

## Index recommandés

Pour optimiser les performances, les index suivants sont recommandés :

```sql
-- Formations
CREATE INDEX idx_formations_status ON formations(status);
CREATE INDEX idx_formations_created_by ON formations(created_by);

-- Modules
CREATE INDEX idx_modules_formation_id ON modules(formation_id);
CREATE INDEX idx_modules_formation_ordre ON modules(formation_id, ordre);

-- Quiz
CREATE INDEX idx_quiz_module_id ON quiz(module_id);
CREATE INDEX idx_quiz_formation_id ON quiz(formation_id);

-- Questions
CREATE INDEX idx_questions_quiz_id ON questions(quiz_id);
CREATE INDEX idx_questions_quiz_ordre ON questions(quiz_id, ordre);

-- Question Options
CREATE INDEX idx_question_options_question_id ON question_options(question_id);

-- User Formation Enrollments
CREATE UNIQUE INDEX idx_enrollments_user_formation ON user_formation_enrollments(user_id, formation_id);
CREATE INDEX idx_enrollments_user_id ON user_formation_enrollments(user_id);
CREATE INDEX idx_enrollments_formation_id ON user_formation_enrollments(formation_id);
CREATE INDEX idx_enrollments_status ON user_formation_enrollments(status);

-- User Progress
CREATE UNIQUE INDEX idx_progress_enrollment_module ON user_progress(enrollment_id, module_id);
CREATE INDEX idx_progress_enrollment_id ON user_progress(enrollment_id);

-- User Quiz Attempts
CREATE INDEX idx_attempts_user_quiz ON user_quiz_attempts(user_id, quiz_id);
CREATE INDEX idx_attempts_enrollment_id ON user_quiz_attempts(enrollment_id);

-- User Quiz Answers
CREATE INDEX idx_answers_attempt_id ON user_quiz_answers(attempt_id);
```

## Volumétrie estimée

Pour 1000 utilisateurs et 100 formations :

| Table | Nombre d'enregistrements estimé |
|-------|--------------------------------|
| formations | 100 |
| modules | 500 (5 modules par formation) |
| quiz | 600 (5 quiz modules + 1 final par formation) |
| questions | 6000 (10 questions par quiz) |
| question_options | 24000 (4 options par question) |
| user_formation_enrollments | 5000 (5 formations par utilisateur) |
| user_progress | 25000 (5 modules × 5 formations × 1000 users) |
| user_quiz_attempts | 15000 (3 tentatives moyennes) |
| user_quiz_answers | 150000 (10 réponses par tentative) |

**Total estimé : ~225 600 enregistrements**
