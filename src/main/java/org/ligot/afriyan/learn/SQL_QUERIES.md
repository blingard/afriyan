# Requêtes SQL utiles - Module E-Learning

## Statistiques globales

### Nombre total de formations par statut
```sql
SELECT status, COUNT(*) as total
FROM formations
GROUP BY status;
```

### Formations les plus populaires
```sql
SELECT f.id, f.titre, COUNT(e.id) as nombre_inscrits
FROM formations f
LEFT JOIN user_formation_enrollments e ON f.id = e.formation_id
GROUP BY f.id, f.titre
ORDER BY nombre_inscrits DESC
LIMIT 10;
```

### Taux de complétion des formations
```sql
SELECT 
    f.titre,
    COUNT(e.id) as total_inscriptions,
    SUM(CASE WHEN e.status = 'COMPLETED' THEN 1 ELSE 0 END) as completees,
    ROUND(SUM(CASE WHEN e.status = 'COMPLETED' THEN 1 ELSE 0 END) * 100.0 / COUNT(e.id), 2) as taux_completion
FROM formations f
INNER JOIN user_formation_enrollments e ON f.id = e.formation_id
GROUP BY f.id, f.titre
ORDER BY taux_completion DESC;
```

## Statistiques utilisateur

### Progression d'un utilisateur dans toutes ses formations
```sql
SELECT 
    f.titre as formation,
    e.status,
    e.progression_pourcent,
    e.date_debut,
    e.date_fin
FROM user_formation_enrollments e
INNER JOIN formations f ON e.formation_id = f.id
WHERE e.user_id = :userId
ORDER BY e.date_inscription DESC;
```

### Détail de progression par module pour une inscription
```sql
SELECT 
    m.titre as module,
    m.ordre,
    p.status,
    p.progression_pourcent,
    p.score_quiz,
    p.quiz_passed
FROM user_progress p
INNER JOIN modules m ON p.module_id = m.id
WHERE p.enrollment_id = :enrollmentId
ORDER BY m.ordre;
```

### Historique des tentatives de quiz d'un utilisateur
```sql
SELECT 
    q.titre as quiz,
    a.numero_tentative,
    a.score_obtenu,
    a.points_obtenus,
    a.points_totaux,
    a.is_passed,
    a.date_debut,
    a.date_fin,
    a.duree_secondes
FROM user_quiz_attempts a
INNER JOIN quiz q ON a.quiz_id = q.id
WHERE a.user_id = :userId
ORDER BY a.date_debut DESC;
```

### Utilisateurs les plus actifs
```sql
SELECT 
    u.id,
    u.nom,
    u.prenom,
    COUNT(DISTINCT e.id) as formations_inscrites,
    COUNT(DISTINCT a.id) as quiz_tentes,
    SUM(CASE WHEN e.status = 'COMPLETED' THEN 1 ELSE 0 END) as formations_completees
FROM users u
LEFT JOIN user_formation_enrollments e ON u.id = e.user_id
LEFT JOIN user_quiz_attempts a ON u.id = a.user_id
GROUP BY u.id, u.nom, u.prenom
ORDER BY formations_completees DESC, quiz_tentes DESC
LIMIT 10;
```

## Statistiques sur les quiz

### Questions les plus difficiles (taux d'échec élevé)
```sql
SELECT 
    q.intitule,
    COUNT(a.id) as nombre_reponses,
    SUM(CASE WHEN a.is_correct = true THEN 1 ELSE 0 END) as reponses_correctes,
    ROUND(SUM(CASE WHEN a.is_correct = true THEN 1 ELSE 0 END) * 100.0 / COUNT(a.id), 2) as taux_reussite
FROM questions q
INNER JOIN user_quiz_answers a ON q.id = a.question_id
GROUP BY q.id, q.intitule
HAVING COUNT(a.id) > 10
ORDER BY taux_reussite ASC
LIMIT 10;
```

### Score moyen par quiz
```sql
SELECT 
    q.titre,
    COUNT(a.id) as nombre_tentatives,
    ROUND(AVG(a.score_obtenu), 2) as score_moyen,
    ROUND(AVG(a.duree_secondes), 0) as duree_moyenne_secondes,
    SUM(CASE WHEN a.is_passed = true THEN 1 ELSE 0 END) as reussites,
    ROUND(SUM(CASE WHEN a.is_passed = true THEN 1 ELSE 0 END) * 100.0 / COUNT(a.id), 2) as taux_reussite
FROM quiz q
LEFT JOIN user_quiz_attempts a ON q.id = a.quiz_id
WHERE a.is_completed = true
GROUP BY q.id, q.titre
ORDER BY taux_reussite DESC;
```

### Distribution des scores pour un quiz
```sql
SELECT 
    CASE 
        WHEN score_obtenu < 50 THEN '0-49%'
        WHEN score_obtenu < 70 THEN '50-69%'
        WHEN score_obtenu < 85 THEN '70-84%'
        ELSE '85-100%'
    END as tranche_score,
    COUNT(*) as nombre_tentatives
FROM user_quiz_attempts
WHERE quiz_id = :quizId AND is_completed = true
GROUP BY tranche_score
ORDER BY tranche_score;
```

## Analyse des modules

### Modules avec le plus faible taux de complétion
```sql
SELECT 
    m.titre,
    f.titre as formation,
    COUNT(p.id) as total_progressions,
    SUM(CASE WHEN p.status = 'COMPLETED' THEN 1 ELSE 0 END) as completes,
    ROUND(SUM(CASE WHEN p.status = 'COMPLETED' THEN 1 ELSE 0 END) * 100.0 / COUNT(p.id), 2) as taux_completion
FROM modules m
INNER JOIN formations f ON m.formation_id = f.id
LEFT JOIN user_progress p ON m.id = p.module_id
GROUP BY m.id, m.titre, f.titre
HAVING COUNT(p.id) > 0
ORDER BY taux_completion ASC
LIMIT 10;
```

### Temps moyen de complétion par module
```sql
SELECT 
    m.titre,
    m.duree_estimee as duree_estimee_minutes,
    COUNT(p.id) as nombre_completions,
    ROUND(AVG(EXTRACT(EPOCH FROM (p.date_completion - p.date_debut)) / 60), 2) as duree_moyenne_minutes
FROM modules m
INNER JOIN user_progress p ON m.id = p.module_id
WHERE p.status = 'COMPLETED' AND p.date_debut IS NOT NULL AND p.date_completion IS NOT NULL
GROUP BY m.id, m.titre, m.duree_estimee
ORDER BY duree_moyenne_minutes DESC;
```

## Requêtes pour les formateurs

### Mes formations et leur performance
```sql
SELECT 
    f.titre,
    f.status,
    COUNT(DISTINCT e.id) as total_inscrits,
    COUNT(DISTINCT CASE WHEN e.status = 'IN_PROGRESS' THEN e.id END) as en_cours,
    COUNT(DISTINCT CASE WHEN e.status = 'COMPLETED' THEN e.id END) as completees,
    ROUND(AVG(e.progression_pourcent), 2) as progression_moyenne
FROM formations f
LEFT JOIN user_formation_enrollments e ON f.id = e.formation_id
WHERE f.created_by = :userId
GROUP BY f.id, f.titre, f.status
ORDER BY f.date_creation DESC;
```

### Feedback détaillé sur un quiz (réponses par question)
```sql
SELECT 
    q.ordre,
    q.intitule,
    opt.texte as option,
    opt.is_correct as est_correcte,
    COUNT(a.id) as nombre_selections,
    ROUND(COUNT(a.id) * 100.0 / 
        (SELECT COUNT(*) FROM user_quiz_answers WHERE question_id = q.id), 2) as pourcentage
FROM questions q
INNER JOIN question_options opt ON q.id = opt.question_id
LEFT JOIN user_quiz_answers a ON opt.id = a.selected_option_id
WHERE q.quiz_id = :quizId
GROUP BY q.id, q.ordre, q.intitule, opt.id, opt.texte, opt.is_correct
ORDER BY q.ordre, opt.ordre;
```

## Nettoyage et maintenance

### Supprimer les tentatives de quiz non terminées de plus de 7 jours
```sql
DELETE FROM user_quiz_attempts
WHERE is_completed = false 
  AND date_debut < NOW() - INTERVAL '7 days';
```

### Identifier les inscriptions abandonnées (pas de progression depuis 30 jours)
```sql
SELECT 
    e.id,
    u.nom,
    u.prenom,
    f.titre,
    e.progression_pourcent,
    e.date_modification
FROM user_formation_enrollments e
INNER JOIN users u ON e.user_id = u.id
INNER JOIN formations f ON e.formation_id = f.id
WHERE e.status = 'IN_PROGRESS'
  AND e.date_modification < NOW() - INTERVAL '30 days'
ORDER BY e.date_modification;
```

### Archiver automatiquement les formations sans inscription depuis 6 mois
```sql
UPDATE formations
SET status = 'ARCHIVED'
WHERE status = 'PUBLISHED'
  AND id NOT IN (
    SELECT DISTINCT formation_id 
    FROM user_formation_enrollments 
    WHERE date_inscription > NOW() - INTERVAL '6 months'
  );
```

## Rapports avancés

### Évolution des inscriptions par mois
```sql
SELECT 
    DATE_TRUNC('month', date_inscription) as mois,
    COUNT(*) as nouvelles_inscriptions,
    COUNT(DISTINCT user_id) as utilisateurs_uniques
FROM user_formation_enrollments
WHERE date_inscription >= NOW() - INTERVAL '12 months'
GROUP BY DATE_TRUNC('month', date_inscription)
ORDER BY mois;
```

### Taux de réussite des quiz par tentative
```sql
SELECT 
    numero_tentative,
    COUNT(*) as total_tentatives,
    SUM(CASE WHEN is_passed = true THEN 1 ELSE 0 END) as reussites,
    ROUND(SUM(CASE WHEN is_passed = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) as taux_reussite
FROM user_quiz_attempts
WHERE is_completed = true
GROUP BY numero_tentative
ORDER BY numero_tentative;
```

### Utilisateurs à risque d'abandon (progression stagnante)
```sql
WITH user_last_activity AS (
    SELECT 
        e.id as enrollment_id,
        e.user_id,
        e.formation_id,
        e.progression_pourcent,
        GREATEST(
            e.date_modification,
            COALESCE((SELECT MAX(p.date_modification) FROM user_progress p WHERE p.enrollment_id = e.id), e.date_modification),
            COALESCE((SELECT MAX(a.date_fin) FROM user_quiz_attempts a WHERE a.enrollment_id = e.id), e.date_modification)
        ) as derniere_activite
    FROM user_formation_enrollments e
    WHERE e.status = 'IN_PROGRESS'
)
SELECT 
    u.nom,
    u.prenom,
    u.email,
    f.titre as formation,
    ula.progression_pourcent,
    ula.derniere_activite,
    NOW() - ula.derniere_activite as inactivite_duree
FROM user_last_activity ula
INNER JOIN users u ON ula.user_id = u.id
INNER JOIN formations f ON ula.formation_id = f.id
WHERE ula.derniere_activite < NOW() - INTERVAL '14 days'
  AND ula.progression_pourcent > 0
  AND ula.progression_pourcent < 100
ORDER BY inactivite_duree DESC;
```
