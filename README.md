# 📋 Ayni Postulaciones Service

Microservicio transaccional de la plataforma Somos Ayni. Une a los talentos con los retos publicados por las empresas y gestiona el ciclo de vida completo de una candidatura — desde que el talento envía su solución hasta que la empresa la aprueba o rechaza. Existe como dominio separado porque las reglas de evaluación y el historial de candidaturas son una responsabilidad propia que no debe mezclarse con la identidad del usuario ni con los logros de gamificación.

## Responsabilidad del Bounded Context

**Maneja:**
- Creación de postulaciones (talento → reto)
- Consulta del historial de postulaciones de un talento
- Listado de soluciones enviadas para un reto
- Evaluación de candidatos (APROBADO / RECHAZADO) con feedback

**No maneja:**
- Otorgamiento de insignias — eso lo hace `ayni-habilidades-service` de forma independiente tras la aprobación
- Publicación ni creación de retos (responsabilidad de `ayni-retos-service`)
- Autenticación ni gestión de usuarios

---

## Endpoints REST

| Método | Ruta | Descripción | Auth |
|--------|------|-------------|------|
| POST | `/api/v1/postulaciones` | Postular a un reto (body: `talentoId`, `retoId`, `urlSolucion`) | JWT requerido (TALENTO) |
| GET | `/api/v1/postulaciones/talento/{talentoId}` | Historial completo de postulaciones de un talento | JWT requerido |
| GET | `/api/v1/postulaciones/reto/{retoId}` | Listar todas las soluciones enviadas para un reto | JWT requerido (EMPRESA / ADMIN) |
| POST | `/api/v1/postulaciones/{id}/evaluar` | Evaluar un candidato (body: `resultado` APROBADO/RECHAZADO, `feedback`) | JWT requerido (EMPRESA / ADMIN) |

---

## Arquitectura (Hexagonal)

```
src/main/java/com/somosayni/postulaciones/
├── domain/           # Núcleo del negocio — sin dependencias externas
│   ├── model/        # Entidades y enums (Postulacion, Evaluacion, EstadoPostulacion)
│   └── port/         # Interfaces de repositorio y servicios de salida
├── application/      # Casos de uso (postular, listar, evaluar)
│   └── service/      # Orquestación de la lógica de negocio
└── infrastructure/   # Adaptadores técnicos
    ├── persistence/  # JPA repositories, entidades de BD
    ├── security/     # Validación de JWT (solo verifica, no firma)
    └── web/          # Controladores REST, DTOs de entrada/salida
```

| Capa | Responsabilidad |
|------|----------------|
| **domain** | Reglas transaccionales: una postulación solo puede evaluarse si está en estado `EN_REVISION`, no se puede postular dos veces al mismo reto, transiciones de estado válidas |
| **application** | Orquesta los puertos: persiste la postulación, aplica la evaluación, valida precondiciones de negocio |
| **infrastructure** | Todo lo técnico: Spring Boot, JPA/PostgreSQL, validación del token JWT, serialización JSON |

---

## Modelos de dominio principales

### Postulacion

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | UUID | Identificador único |
| `talentoId` | UUID | ID del talento que postula |
| `retoId` | UUID | ID del reto al que postula |
| `urlSolucion` | String | URL del entregable (repositorio, drive, etc.) |
| `estado` | Enum | Estado actual de la candidatura |
| `fechaPostulacion` | LocalDateTime | Momento en que se registró la postulación |

### Ciclo de vida (EstadoPostulacion)

```
EN_REVISION  ──────► APROBADO
     │
     └──────────────► RECHAZADO
```

| Estado | Significado |
|--------|-------------|
| `EN_REVISION` | La solución fue recibida y está pendiente de evaluación |
| `APROBADO` | La empresa aprobó la solución del talento |
| `RECHAZADO` | La empresa rechazó la solución con feedback |

### Evaluacion

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | UUID | Identificador único |
| `postulacionId` | UUID | Postulación que fue evaluada |
| `resultado` | Enum | `APROBADO` / `RECHAZADO` |
| `feedback` | String | Comentario de la empresa para el talento |
| `fechaEvaluacion` | LocalDateTime | Momento en que se registró la evaluación |

---

## Cómo ejecutar

### Local

```bash
# Requisitos: Java 21, Maven, PostgreSQL corriendo en localhost:5432
mvn clean package -DskipTests
java -jar target/*.jar
```

### Docker

```bash
cp .env.example .env
# Editar .env con tus valores reales
docker-compose up --build
```

---

## Variables de entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SERVER_PORT` | Puerto en que levanta el servicio | `8084` |
| `DB_HOST` | Host de PostgreSQL | `localhost` |
| `DB_PORT` | Puerto de PostgreSQL | `5432` |
| `DB_NAME` | Nombre de la base de datos | `somosayni` |
| `DB_USERNAME` | Usuario de PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña de PostgreSQL | *(requerido)* |
| `JWT_SECRET` | Clave secreta para validar tokens JWT (firmados por identidad-service) | *(requerido — compartida con todos los servicios)* |

---

## Swagger / OpenAPI

| | Link |
|---|---|
| **Swagger UI (local)** | [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html) |
| **OpenAPI JSON (local)** | [http://localhost:8084/api-docs](http://localhost:8084/api-docs) |
| **swagger.json (repo)** | [ver en GitHub](https://github.com/ayni-01/ayni-postulaciones-service/blob/main/swagger.json) |
| **Swagger Editor (online)** | [abrir en Swagger Editor](https://editor.swagger.io/?url=https://raw.githubusercontent.com/ayni-01/ayni-postulaciones-service/main/swagger.json) |

> Para probar los endpoints protegidos: copia el JWT del login → clic en **Authorize** → pega `Bearer <tu-token>`.

---

## Dependencias

Este servicio forma parte del ecosistema Somos Ayni junto con `ayni-identidad-service`, `ayni-habilidades-service`, `ayni-perfiles-service` y `ayni-retos-service`. Los servicios comparten las siguientes convenciones:

- **Base de datos:** Todos usan la misma instancia de PostgreSQL, base de datos `somosayni`, pero cada servicio opera sobre sus propias tablas sin hacer JOINs entre contextos.
- **JWT:** Los tokens son **firmados exclusivamente por `ayni-identidad-service`**. Este servicio solo valida la firma usando la misma variable `JWT_SECRET`.
- **Comunicación:** Los servicios son independientes entre sí. Se referencian únicamente por ID (p. ej. `talentoId`, `retoId`) y no realizan llamadas HTTP entre ellos en tiempo real. Cuando una postulación es aprobada, el otorgamiento de insignias en `ayni-habilidades-service` se realiza de forma manual o mediante un proceso separado — no hay llamada directa entre estos dos servicios.
