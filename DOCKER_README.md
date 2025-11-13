# Docker Compose - Guide d'utilisation

## Structure des volumes

Le fichier `docker-compose.yml` configure les mappings suivants :

### Volumes de l'application
- **`./config`** → `/app/config` (lecture seule)
  - Contient les fichiers de configuration de l'application
  - `application.properties`, `application-dev.properties`, etc.

- **`./uploads`** → `/app/uploads` (lecture/écriture)
  - Stockage des fichiers téléchargés
  - Articles, images, documents, etc.

- **`./logs`** → `/app/logs` (lecture/écriture)
  - Fichiers de logs de l'application

### Volume de la base de données
- **`postgres_data`** → Volume Docker persistant pour PostgreSQL

## Commandes utiles

### Démarrer l'application
```bash
docker-compose up -d
```

### Voir les logs
```bash
# Tous les services
docker-compose logs -f

# Application uniquement
docker-compose logs -f app

# PostgreSQL uniquement
docker-compose logs -f postgres
```

### Arrêter l'application
```bash
docker-compose down
```

### Arrêter et supprimer les volumes
```bash
docker-compose down -v
```

### Reconstruire l'application
```bash
docker-compose up -d --build
```

### Vérifier l'état des services
```bash
docker-compose ps
```

## Configuration

### Variables d'environnement
Copiez `.env.example` vers `.env` et modifiez selon vos besoins :
```bash
cp .env.example .env
```

### Accès à l'application
- Application : http://localhost:8087
- Swagger UI : http://localhost:8087/swagger-ui-custom.html
- PostgreSQL : localhost:5433

### Accès au conteneur
```bash
# Shell dans le conteneur de l'application
docker-compose exec app sh

# Shell dans PostgreSQL
docker-compose exec postgres psql -U afriyan -d afriyan
```

## Structure réseau
- Réseau Docker : `afriyan-network`
- Les conteneurs peuvent communiquer entre eux par leur nom de service

## Health Checks
- **PostgreSQL** : Vérifie que la base est prête toutes les 10s
- **Application** : Vérifie l'endpoint `/actuator/health` toutes les 30s
