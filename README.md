# 🚀 ForoHub - API REST para Gestión de Tópicos

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?style=flat&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=flat&logo=apache-maven)

**ForoHub** es una API REST robusta desarrollada con Spring Boot que permite gestionar tópicos de discusión en un foro. Implementa autenticación JWT, CRUD completo de tópicos y gestión de usuarios con roles.

---

## 📋 Características

### ✨ Funcionalidades Principales
- **CRUD completo de tópicos** (Crear, Leer, Actualizar, Eliminar)
- **Sistema de autenticación** con JWT
- **Validación de datos** de entrada
- **Prevención de tópicos duplicados**
- **Paginación y ordenamiento** de resultados
- **Filtrado por curso y año**
- **Gestión de usuarios y perfiles**

### 🔒 Seguridad
- Autenticación mediante **JSON Web Tokens (JWT)**
- Autorización basada en roles
- Validación de entrada con **Bean Validation**
- Manejo de errores centralizado
- Contraseñas encriptadas con **BCrypt**

---

## 🛠️ Tecnologías Utilizadas

| Componente | Tecnología | Versión |
|------------|------------|---------|
| **Java** | OpenJDK | 17+ |
| **Framework** | Spring Boot | 3.5.4 |
| **Base de Datos** | MySQL | 8+ |
| **ORM** | Spring Data JPA | - |
| **Migraciones** | Flyway | - |
| **Seguridad** | Spring Security + JWT | - |
| **Build Tool** | Maven | 4+ |
| **IDE** | IntelliJ IDEA | - |

---

## 🚀 Instalación y Configuración

### Prerrequisitos
```bash
# Verificar versiones
java -version    # Java 17+
mvn -version     # Maven 4+
mysql --version  # MySQL 8+
```

### 1. Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/forohub-api.git
cd forohub-api
```

### 2. Configurar MySQL
```sql
-- Crear la base de datos
CREATE DATABASE foro_hub;
```

### 3. Configurar Variables de Entorno
Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foro_hub
spring.datasource.username=root
spring.datasource.password=tu_password
jwt.secret=tu-secreto-jwt-super-seguro
```

### 4. Ejecutar la Aplicación
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 📚 API Endpoints

### 🔐 Autenticación

#### POST `/login`
Autenticar usuario y obtener token JWT.

**Request:**
```json
{
    "login": "admin",
    "clave": "123456"
}
```

**Response:**
```json
{
    "jwTtoken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 📝 Gestión de Tópicos

#### POST `/topicos`
Crear un nuevo tópico.

**Headers:** `Authorization: Bearer <token>`

**Request:**
```json
{
    "titulo": "¿Cómo usar Spring Security?",
    "mensaje": "Tengo dudas sobre la configuración de Spring Security...",
    "autorId": 1,
    "cursoId": 1
}
```

#### GET `/topicos`
Listar todos los tópicos (con paginación).

**Headers:** `Authorization: Bearer <token>`

**Parámetros opcionales:**
- `page`: Número de página (default: 0)
- `size`: Tamaño de página (default: 10)
- `curso`: Filtrar por nombre de curso
- `anio`: Filtrar por año

#### GET `/topicos/{id}`
Obtener detalles de un tópico específico.

#### PUT `/topicos/{id}`
Actualizar un tópico existente.

#### DELETE `/topicos/{id}`
Eliminar un tópico.

---

## 🧪 Pruebas

### Con Postman/Insomnia

#### 1. Autenticación
```http
POST http://localhost:8080/login
Content-Type: application/json

{
    "login": "admin",
    "clave": "123456"
}
```

#### 2. Crear Tópico
```http
POST http://localhost:8080/topicos
Authorization: Bearer {TOKEN}
Content-Type: application/json

{
    "titulo": "Problema con JPA",
    "mensaje": "No puedo hacer funcionar las relaciones Many-to-One",
    "autorId": 1,
    "cursoId": 1
}
```

---

## 📁 Estructura del Proyecto

```
src/main/java/com/alura/foroHub/
├── controller/
│   ├── AutenticacionController.java
│   └── TopicoController.java
├── domain/
│   ├── curso/
│   ├── topico/
│   └── usuario/
├── infra/
│   ├── exceptions/
│   └── security/
└── ForoHubApplication.java

src/main/resources/
├── db/migration/
│   ├── V1__create_table_usuarios.sql
│   ├── V2__create_table_cursos.sql
│   └── V3__create_table_topicos.sql
└── application.properties
```

---

## 🔧 Configuración para Desarrollo

### IntelliJ IDEA
1. **Importar proyecto**: File → Open → Seleccionar carpeta del proyecto
2. **Configurar JDK**: File → Project Structure → Project → SDK (Java 17+)
3. **Ejecutar aplicación**: Run → ForoHubApplication

### Variables de Entorno para Desarrollo
```properties
# application-dev.properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.com.alura.foroHub=DEBUG
```

---

## 📊 Base de Datos

### Modelo de Datos
- **Usuario**: Información del usuario y autenticación
- **Curso**: Categorías de tópicos
- **Tópico**: Tema principal de discusión

### Relaciones
- Tópico ← N:1 → Usuario (autor)
- Tópico ← N:1 → Curso

---

## 🛡️ Reglas de Negocio

### Validaciones para Tópicos:
1. **Campos obligatorios**: título, mensaje, autor y curso
2. **No duplicados**: No se permiten tópicos con el mismo título y mensaje
3. **Autenticación requerida**: Todas las operaciones requieren token JWT válido
4. **Integridad referencial**: Autor y curso deben existir en la base de datos

### Estados de Tópicos:
- `NO_RESPONDIDO`: Tópico recién creado sin respuestas
- `NO_SOLUCIONADO`: Tópico con respuestas pero sin solución
- `SOLUCIONADO`: Tópico con una respuesta marcada como solución
- `CERRADO`: Tópico cerrado por moderador

---

## 🚀 Despliegue

### Crear JAR para Producción
```bash
# Compilar para producción
mvn clean package

# Ejecutar JAR
java -jar target/foroHub-0.0.1-SNAPSHOT.jar
```

### Variables de Entorno para Producción
```bash
export MYSQL_URL="jdbc:mysql://prod-server:3306/foro_hub"
export MYSQL_USERNAME="forohub_user"
export MYSQL_PASSWORD="secure_password"
export JWT_SECRET="super-secure-secret-key-for-production"
```

---

## 🤝 Contribución

### Proceso de Desarrollo:
1. Fork del repositorio
2. Crear branch feature: `git checkout -b feature/nueva-funcionalidad`
3. Commit cambios: `git commit -am 'Agregar nueva funcionalidad'`
4. Push al branch: `git push origin feature/nueva-funcionalidad`
5. Crear Pull Request

---

## 📝 Licencia

Este proyecto fue desarrollado como parte del **Desafío ForoHub de Alura Latam**. Es un proyecto educativo que demuestra el dominio de tecnologías backend modernas.

---

## 👨‍💻 Autor

Desarrollado con ❤️ siguiendo las mejores prácticas de Spring Boot y los requerimientos del desafío **Alura Latam**.

**Tecnologías dominadas:**
- ✅ Spring Boot 3 con Java 17
- ✅ Spring Security con JWT
- ✅ Spring Data JPA
- ✅ MySQL y Flyway
- ✅ API REST con mejores prácticas
- ✅ Validaciones y manejo de errores
- ✅ Arquitectura limpia y modular

---

## 🎯 Funcionalidades Adicionales

- 📊 Paginación y ordenamiento
- 🔍 Filtros avanzados por curso y fecha
- 🛡️ Seguridad robusta con JWT
- ✅ Validaciones completas de datos
- 🚀 Listo para despliegue en producción

¡**ForoHub** - Una solución completa para gestión de foros de discusión! 🎉
