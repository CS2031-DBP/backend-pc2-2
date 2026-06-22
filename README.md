# Sparky The Horizon — Backend API

Backend de la plataforma de fans **Sparky The Horizon**, dedicada a la discografía de *Bring Me The Horizon (BMTH)*. Construido con Spring Boot 3.4 + JWT + H2 en memoria.

---

## Cómo ejecutar

```bash
./mvnw spring-boot:run
```

> No requiere variables de entorno ni base de datos externa. La base de datos H2 se crea en memoria al arrancar y se destruye al apagar.

El servidor corre en `http://localhost:8080`.

### Consola H2 (opcional)

Disponible en `http://localhost:8080/h2-console`

| Campo    | Valor               |
|----------|---------------------|
| JDBC URL | `jdbc:h2:mem:labdb` |
| Usuario  | `sa`                |
| Password | *(vacío)*           |

---

## Datos precargados

Al arrancar, el backend carga automáticamente **8 álbumes** con **5 canciones cada uno**:

| ID | Álbum                              | Año  | Canciones (primeras 2)                              |
|----|------------------------------------|------|-----------------------------------------------------|
| 1  | Count Your Blessings               | 2006 | Pray for Plagues, Tell Slater Not to Wash His Dick  |
| 2  | Suicide Season                     | 2008 | The Comedown, Suicide Season                        |
| 3  | There Is a Hell...                 | 2010 | Crucify Me, Anthem                                  |
| 4  | Sempiternal                        | 2013 | Can You Feel My Heart, The House of Wolves          |
| 5  | That's the Spirit                  | 2015 | Doomed, Happy Song                                  |
| 6  | Amo                                | 2019 | I Apologise If You Feel Something, MANTRA           |
| 7  | Post Human: Survival Horror        | 2020 | Dear Diary,, Parasite Eve                           |
| 8  | Post Human: Nex Gen                | 2024 | LosT, DArkSide                                      |

---

## Autenticación

El backend usa **JWT (Bearer Token)**. Los endpoints protegidos requieren el header:

```
Authorization: Bearer <token>
```

El token se obtiene al registrarse o iniciar sesión.

---

## Endpoints

### Autenticación

#### `POST /api/auth/register` — Registrar usuario

**Acceso:** público  
**Content-Type:** `application/json`

**Body:**
```json
{
  "email": "estudiante@utec.edu.pe",
  "password": "miPassword123",
  "name": "Juan Pérez"
}
```

| Campo      | Tipo   | Requerido | Descripción                  |
|------------|--------|-----------|------------------------------|
| `email`    | String | Sí        | Correo electrónico válido    |
| `password` | String | Sí        | Contraseña del usuario       |
| `name`     | String | Sí        | Nombre completo del usuario  |

**Respuesta exitosa (`200 OK`):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

---

#### `POST /api/auth/login` — Iniciar sesión

**Acceso:** público  
**Content-Type:** `application/json`

**Body:**
```json
{
  "email": "estudiante@utec.edu.pe",
  "password": "miPassword123"
}
```

| Campo      | Tipo   | Requerido | Descripción            |
|------------|--------|-----------|------------------------|
| `email`    | String | Sí        | Correo registrado      |
| `password` | String | Sí        | Contraseña del usuario |

**Respuesta exitosa (`200 OK`):**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9..."
}
```

---

### Álbumes y Canciones

#### `GET /api/albums` — Listar todos los álbumes

**Acceso:** público (no requiere token)

**Respuesta exitosa (`200 OK`):**
```json
[
  {
    "id": 1,
    "title": "Count Your Blessings",
    "year": 2006,
    "coverUrl": "https://via.placeholder.com/300"
  },
  ...
]
```

| Campo      | Tipo    | Descripción                        |
|------------|---------|------------------------------------|
| `id`       | Long    | Identificador único del álbum      |
| `title`    | String  | Título del álbum                   |
| `year`     | Integer | Año de lanzamiento                 |
| `coverUrl` | String  | URL de la portada del álbum        |

---

#### `GET /api/albums/{albumId}/songs` — Canciones de un álbum

**Acceso:** público (no requiere token)  
**Path param:** `albumId` — ID del álbum (Long)

**Ejemplo:** `GET /api/albums/4/songs`

**Respuesta exitosa (`200 OK`):**
```json
[
  {
    "id": 16,
    "title": "Can You Feel My Heart",
    "duration": "3:45",
    "albumId": 4
  },
  ...
]
```

| Campo      | Tipo   | Descripción                               |
|------------|--------|-------------------------------------------|
| `id`       | Long   | Identificador único de la canción         |
| `title`    | String | Título de la canción                      |
| `duration` | String | Duración en formato `mm:ss`               |
| `albumId`  | Long   | ID del álbum al que pertenece la canción  |

---

### Favoritos

> Todos los endpoints de favoritos requieren el header `Authorization: Bearer <token>`.

#### `POST /api/user/favorites` — Agregar canción a favoritos

**Acceso:** autenticado  
**Content-Type:** `application/json`

**Body:**
```json
{
  "songId": 16
}
```

| Campo    | Tipo | Requerido | Descripción                          |
|----------|------|-----------|--------------------------------------|
| `songId` | Long | Sí        | ID de la canción a agregar a favoritos |

**Respuesta exitosa (`200 OK`):** sin cuerpo

---

#### `DELETE /api/user/favorites` — Eliminar canción de favoritos

**Acceso:** autenticado  
**Content-Type:** `application/json`

**Body:**
```json
{
  "songId": 16
}
```

| Campo    | Tipo | Requerido | Descripción                            |
|----------|------|-----------|----------------------------------------|
| `songId` | Long | Sí        | ID de la canción a eliminar de favoritos |

**Respuesta exitosa (`200 OK`):** sin cuerpo

---

## Resumen de endpoints

| Método | Ruta                          | Acceso      | Descripción                            |
|--------|-------------------------------|-------------|----------------------------------------|
| POST   | `/api/auth/register`          | Público     | Registrar nuevo usuario                |
| POST   | `/api/auth/login`             | Público     | Iniciar sesión y obtener token         |
| GET    | `/api/albums`                 | Público     | Listar los 8 álbumes de BMTH           |
| GET    | `/api/albums/{albumId}/songs` | Público     | Listar canciones de un álbum           |
| POST   | `/api/user/favorites`         | Autenticado | Agregar canción a favoritos            |
| DELETE | `/api/user/favorites`         | Autenticado | Eliminar canción de favoritos          |

---

## Errores comunes

| Código | Causa                                              |
|--------|----------------------------------------------------|
| `400`  | Campos requeridos faltantes o con formato inválido |
| `403`  | Endpoint protegido sin token o token inválido      |
| `500`  | Error interno (ej. canción o usuario no encontrado)|
