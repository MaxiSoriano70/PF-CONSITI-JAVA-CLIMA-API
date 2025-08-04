# 🌦️ Clima API – Consulta de Clima con Spring Boot y OpenWeather

Este proyecto es una aplicación backend desarrollada con **Java**, **Spring Boot**, **MySQL** y **JWT**, que permite a los usuarios registrarse, autenticarse y consultar el clima actual de distintas ciudades mediante la API de **OpenWeather**. Además, cada consulta se guarda en una base de datos para su posterior análisis o seguimiento.

---

## 🚀 Funcionalidades Principales

- 🔐 Autenticación y registro de usuarios utilizando **JWT**.
- ☁️ Consulta de clima actual a través de **OpenWeather API**.
- 🧾 Almacenamiento de cada consulta realizada, incluyendo:
  - Ciudad consultada
  - Fecha y hora
  - Tipo de consulta (`CURRENT`, etc.)
  - Usuario que realizó la consulta
- 📊 Límite configurable de hasta 100 consultas por usuario, que se reinicia automáticamente tras una fecha determinada.
- 🧩 Estructura limpia siguiendo buenas prácticas (Controladores, Servicios, Repositorios, DTOs y Entidades).

---

## 🛠️ Tecnologías Utilizadas

- 💻 **Java 21**
- ⚙️ **Spring Boot 3**
- 🛡️ **Spring Security + JWT**
- 🗄️ **MySQL**
- 🧰 **Lombok**
- 🌐 **OpenWeather API**
- 🔍 **JPA / Hibernate**
- 📚 **Springdoc OpenAPI (Swagger UI)**
- 🚀 **DevTools para autorecarga en desarrollo**

---

## 🧱 Modelo de Datos

### 👤 `Usuario`

| Campo                      | Tipo         | Descripción                                    |
|---------------------------|--------------|------------------------------------------------|
| idUsuario                 | Integer      | ID autogenerado                                |
| nombre / apellido         | String       | Nombre completo del usuario                    |
| email / password          | String       | Credenciales del usuario                       |
| role                      | Enum (`ERol`)| Rol del usuario: `ADMIN`, `USER`               |
| cantidadConsultas         | Integer      | Cantidad de consultas restantes                |
| fechaRestauracionConsultas| LocalDate    | Fecha de reinicio del contador de consultas    |

### 🌍 `Consulta`

| Campo              | Tipo                 | Descripción                                   |
|-------------------|----------------------|-----------------------------------------------|
| id                | Integer              | ID autogenerado                               |
| ciudad            | String               | Nombre de la ciudad consultada                |
| tipoConsulta      | Enum (`ETipoConsulta`)| Tipo de consulta (ej. `CURRENT`)             |
| fechaHoraConsulta | LocalDateTime        | Fecha y hora exacta de la consulta            |
| usuario           | Usuario              | Usuario que realizó la consulta               |

---

## 🔐 Seguridad con JWT

- Al registrarse o iniciar sesión, se genera un **token JWT**.
- El token debe incluirse en la cabecera `Authorization` como **Bearer Token** en las peticiones a endpoints protegidos.
- Solo los usuarios autenticados pueden acceder a las funcionalidades principales.

---

## ⚙️ Configuración del Proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/tuusuario/clima-api-springboot.git
cd clima-api-springboot

spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_datos
spring.datasource.username=root
spring.datasource.password=tu_contraseña

openweather.api.key=TU_API_KEY
jwt.secret=TU_SECRETO_JWT

./mvnw spring-boot:run

http://localhost:8080/swagger-ui/index.html

## 🙋‍♂️ Autor

**Maximiliano Soriano**

💼 Desarrollador Java Full Stack  
📧 maxi.soriano.70.23@gmail.com
