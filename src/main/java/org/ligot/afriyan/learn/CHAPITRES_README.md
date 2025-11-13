# Module Chapitres - Documentation

## Vue d'ensemble

L'entité `Modules` a été modifiée pour remplacer le champ `contenu` par une relation avec une nouvelle entité `Chapitres`. Chaque module peut maintenant contenir plusieurs chapitres ordonnés.

## Modifications apportées

### 1. Nouvelle entité `Chapitres`

**Fichier**: `entities/Chapitres.java`

L'entité Chapitres représente un chapitre d'un module avec les champs suivants:
- `id`: Identifiant unique
- `titre`: Titre du chapitre (obligatoire)
- `contenu`: Contenu du chapitre (texte, HTML, etc.)
- `ordre`: Ordre du chapitre dans le module (obligatoire)
- `dureeEstimee`: Durée estimée en minutes
- `videoUrl`: URL d'une vidéo associée
- `documentUrl`: URL d'un document associé
- `module`: Relation ManyToOne vers Modules
- `dateCreation`: Date de création automatique
- `dateModification`: Date de modification automatique

### 2. Modification de l'entité `Modules`

**Fichier**: `entities/Modules.java`

**Champs supprimés**:
- `contenu` (String)
- `videoUrl` (String)

**Champ ajouté**:
- `chapitres`: Liste de Chapitres (OneToMany avec cascade ALL et orphanRemoval)

### 3. Repository

**Fichier**: `repository/ChapitresRepository.java`

Méthodes disponibles:
- `findByModuleIdOrderByOrdre(Long moduleId)`: Récupère tous les chapitres d'un module ordonnés
- `countByModuleId(Long moduleId)`: Compte le nombre de chapitres d'un module

### 4. DTOs

**Fichiers créés**:
- `dto/ChapitresDTO.java`: DTO de réponse
- `dto/ChapitresCreateDTO.java`: DTO de création/mise à jour avec validation

**Fichiers modifiés**:
- `dto/ModuleDTO.java`: Suppression des champs `contenu` et `videoUrl`
- `dto/ModuleCreateDTO.java`: Suppression des champs `contenu` et `videoUrl`

### 5. Service

**Fichier**: `service/ChapitresService.java` et `service/impl/ChapitresServiceImpl.java`

Méthodes disponibles:
- `createChapitre(ChapitresCreateDTO dto)`: Créer un nouveau chapitre
- `updateChapitre(Long id, ChapitresCreateDTO dto)`: Mettre à jour un chapitre
- `getChapitreById(Long id)`: Récupérer un chapitre par ID
- `getChapitresByModuleId(Long moduleId)`: Récupérer tous les chapitres d'un module
- `deleteChapitre(Long id)`: Supprimer un chapitre
- `reorderChapitres(Long moduleId, List<Long> chapitreIds)`: Réorganiser les chapitres

### 6. Controller

**Fichier**: `controller/ChapitresController.java`

Endpoints REST disponibles:

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| POST | `/api/learn/chapitres` | Créer un chapitre |
| PUT | `/api/learn/chapitres/{id}` | Mettre à jour un chapitre |
| GET | `/api/learn/chapitres/{id}` | Récupérer un chapitre |
| GET | `/api/learn/chapitres/module/{moduleId}` | Récupérer tous les chapitres d'un module |
| DELETE | `/api/learn/chapitres/{id}` | Supprimer un chapitre |
| PUT | `/api/learn/chapitres/module/{moduleId}/reorder` | Réorganiser les chapitres |

## Exemple d'utilisation

### Créer un chapitre

```json
POST /api/learn/chapitres
{
  "moduleId": 1,
  "titre": "Introduction aux variables",
  "contenu": "<p>Les variables en Java...</p>",
  "ordre": 1,
  "dureeEstimee": 15,
  "videoUrl": "https://example.com/video.mp4",
  "documentUrl": "https://example.com/doc.pdf"
}
```

### Récupérer les chapitres d'un module

```
GET /api/learn/chapitres/module/1
```

Réponse:
```json
[
  {
    "id": 1,
    "titre": "Introduction aux variables",
    "contenu": "<p>Les variables en Java...</p>",
    "ordre": 1,
    "dureeEstimee": 15,
    "videoUrl": "https://example.com/video.mp4",
    "documentUrl": "https://example.com/doc.pdf",
    "moduleId": 1,
    "dateCreation": "2025-11-10T10:00:00",
    "dateModification": null
  }
]
```

### Réorganiser les chapitres

```json
PUT /api/learn/chapitres/module/1/reorder
[2, 1, 3]
```

## Migration de données

Si vous avez des données existantes avec le champ `contenu` dans la table `modules`, vous devrez migrer ces données vers la nouvelle table `chapitres`. Voici un exemple de script SQL:

```sql
-- Pour chaque module avec du contenu, créer un chapitre
INSERT INTO chapitres (module_id, titre, contenu, ordre, duree_estimee, video_url, date_creation)
SELECT 
    id as module_id,
    'Contenu du module' as titre,
    contenu,
    1 as ordre,
    duree_estimee,
    video_url,
    CURRENT_TIMESTAMP as date_creation
FROM modules
WHERE contenu IS NOT NULL AND contenu != '';

-- Supprimer les anciennes colonnes (après vérification)
-- ALTER TABLE modules DROP COLUMN contenu;
-- ALTER TABLE modules DROP COLUMN video_url;
```

## Fichiers modifiés

### Nouveaux fichiers
- `entities/Chapitres.java`
- `repository/ChapitresRepository.java`
- `dto/ChapitresDTO.java`
- `dto/ChapitresCreateDTO.java`
- `service/ChapitresService.java`
- `service/impl/ChapitresServiceImpl.java`
- `controller/ChapitresController.java`

### Fichiers modifiés
- `entities/Modules.java`
- `dto/ModuleDTO.java`
- `dto/ModuleCreateDTO.java`
- `service/impl/ModuleServiceImpl.java`
- `init/LearnDataInitializer.java`

## Notes importantes

1. La relation `Modules` -> `Chapitres` utilise `cascade = CascadeType.ALL` et `orphanRemoval = true`, ce qui signifie:
   - Quand un module est supprimé, tous ses chapitres sont supprimés automatiquement
   - Quand un chapitre est retiré de la liste, il est supprimé de la base de données

2. Les chapitres sont ordonnés par le champ `ordre` (commençant à 1)

3. Tous les endpoints sont protégés par CORS avec `@CrossOrigin(origins = "*")`

4. La validation des données est assurée par les annotations Jakarta Validation dans les DTOs
