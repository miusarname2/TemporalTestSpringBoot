# TemporalTest: API REST de Películas con Spring Boot y MySQL

API REST desarrollada con **Spring Boot 3** y **MySQL** para gestionar un catálogo de películas mediante operaciones CRUD (crear, leer, actualizar y eliminar). Fue creada como proyecto de práctica previo a una experiencia con Pragma.

## Características

- CRUD completo sobre el recurso `movies`.
- Persistencia con Spring Data JPA (Hibernate) sobre MySQL.
- Respuestas HTTP correctas: `200`, `201`, `204` y `404`.
- CORS habilitado en todos los endpoints, listo para consumir desde un frontend.

## Tecnologías

| Tecnología | Versión |
| --- | --- |
| Java | 22 |
| Spring Boot | 3.3.2 |
| Spring Web | incluido en Boot |
| Spring Data JPA | incluido en Boot |
| MySQL Connector/J | incluido en Boot |
| Maven (wrapper) | 3.9.7 |

## Requisitos previos

- JDK 22
- MySQL 8 (local o remoto)
- No necesitas instalar Maven: el proyecto incluye `mvnw`.

## Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/<tu-usuario>/TemporalTestSpringBoot.git
   cd TemporalTestSpringBoot
   ```

2. Crea la base de datos y la tabla (ver [Modelo de datos](#modelo-de-datos)).

3. Configura la conexión a la base de datos con variables de entorno (**no subas credenciales al repositorio**):

   ```bash
   export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/nombre_de_tu_base"
   export SPRING_DATASOURCE_USERNAME="tu_usuario"
   export SPRING_DATASOURCE_PASSWORD="tu_contraseña"
   ```

   En Windows (PowerShell):

   ```powershell
   $env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/nombre_de_tu_base"
   $env:SPRING_DATASOURCE_USERNAME="tu_usuario"
   $env:SPRING_DATASOURCE_PASSWORD="tu_contraseña"
   ```

## Ejecución

```bash
./mvnw spring-boot:run
```

La API quedará disponible en `http://localhost:8080`.

Para generar el `.jar`:

```bash
./mvnw clean package
java -jar target/TemporalTest-0.0.1-SNAPSHOT.jar
```

## Modelo de datos

Entidad `Movie`, mapeada a la tabla `movies`:

| Campo | Tipo | Descripción |
| --- | --- | --- |
| `id` | `long` | Identificador autogenerado |
| `title` | `String` | Título de la película |
| `year` | `int` | Año de estreno |
| `votes` | `int` | Cantidad de votos |
| `rating` | `double` | Calificación |
| `descripcion` | `String` | Sinopsis o descripción |
| `imageUrl` | `String` | URL de la imagen (columna `image_url`) |

Script SQL para crear la tabla:

```sql
CREATE TABLE movies (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  title       VARCHAR(255),
  year        INT    NOT NULL,
  votes       INT    NOT NULL,
  rating      DOUBLE NOT NULL,
  descripcion VARCHAR(255),
  image_url   VARCHAR(255)
);
```

> Alternativa: agrega `spring.jpa.hibernate.ddl-auto=update` en `application.properties` y Hibernate creará la tabla automáticamente.

## Endpoints

Ruta base: `/api/movies`

| Método | Ruta | Descripción | Respuestas |
| --- | --- | --- | --- |
| `GET` | `/api/movies` | Lista todas las películas | `200` |
| `GET` | `/api/movies/{id}` | Obtiene una película por ID | `200`, `404` |
| `POST` | `/api/movies` | Crea una película | `201` |
| `PUT` | `/api/movies/{id}` | Reemplaza los datos de una película | `200`, `404` |
| `DELETE` | `/api/movies/{id}` | Elimina una película | `204`, `404` |

### Ejemplos

**Crear una película**

```bash
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Inception",
    "year": 2010,
    "votes": 2500000,
    "rating": 8.8,
    "descripcion": "Un ladrón que roba secretos a través de los sueños.",
    "imageUrl": "https://ejemplo.com/inception.jpg"
  }'
```

**Listar todas**

```bash
curl http://localhost:8080/api/movies
```

**Obtener por ID**

```bash
curl http://localhost:8080/api/movies/1
```

**Actualizar**

```bash
curl -X PUT http://localhost:8080/api/movies/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Inception",
    "year": 2010,
    "votes": 2600000,
    "rating": 8.9,
    "descripcion": "Descripción actualizada.",
    "imageUrl": "https://ejemplo.com/inception.jpg"
  }'
```

**Eliminar**

```bash
curl -X DELETE http://localhost:8080/api/movies/1
```

## Estructura del proyecto

```
TemporalTestSpringBoot/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/
    ├── main/
    │   ├── java/com/AgConsultores/
    │   │   ├── Main.java                     # Punto de entrada
    │   │   ├── controllers/
    │   │   │   └── MovieController.java      # Endpoints REST
    │   │   ├── models/
    │   │   │   └── Movie.java                # Entidad JPA
    │   │   └── repositories/
    │   │       └── MovieRepository.java      # Acceso a datos (JpaRepository)
    │   └── resources/
    │       └── application.properties        # Configuración
    └── test/
        └── java/com/AgConsultores/TemporalTest/
            └── MainTests.java                # Test de carga de contexto
```

## Pruebas

```bash
./mvnw test
```

El test actual (`contextLoads`) levanta el contexto completo de Spring, por lo que **necesita una base de datos accesible** con las variables de entorno configuradas.

## Contribuciones

1. Haz un fork del repositorio.
2. Crea una rama: `git checkout -b mi-mejora`.
3. Haz tus cambios y commitea.
4. Envía un pull request.
