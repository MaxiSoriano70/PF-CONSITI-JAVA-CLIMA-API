# 🌦️ Clima API – Proyecto de Consulta de Clima con Spring Boot y OpenWeather

Este proyecto es una aplicación backend desarrollada con **Java**, **Spring Boot**, **MySQL** y **JWT**, que permite a los usuarios autenticarse, consultar el clima actual de diferentes ciudades a través de la API de **OpenWeather**, y almacenar esas consultas en una base de datos para su posterior seguimiento.

---

## 📌 Características principales

- 🔐 Registro y autenticación de usuarios con **JWT**.
- ☁️ Consulta de clima actual utilizando la API de **OpenWeather**.
- 🧾 Almacenamiento de cada consulta realizada, incluyendo:
  - Ciudad consultada
  - Fecha y hora
  - Tipo de consulta (`CURRENT`, etc.)
  - Usuario que realizó la consulta
- 📊 Límite configurable de 100 consultas por usuario, restaurables cada cierto tiempo.
- 📁 Arquitectura limpia con entidades, servicios, controladores y repositorios separados.

---

## 🧱 Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3**
- **Spring Security + JWT**
- **MySQL**
- **Lombok**
- **OpenWeather API**
- **JPA (Hibernate)**

---

## 📐 Modelo de Datos

### `Usuario`
| Campo                   | Tipo           | Descripción                             |
|------------------------|----------------|-----------------------------------------|
| idUsuario              | Integer        | ID autogenerado                         |
| nombre / apellido      | String         | Nombre completo del usuario             |
| email / password       | String         | Credenciales únicas                     |
| role                   | Enum (`ERol`)  | Rol del usuario (`ADMIN`, `USER`)       |
| cantidadConsultas      | Integer        | Consultas restantes                     |
| fechaRestauracionConsultas | LocalDate | Fecha de restauración del contador      |

### `Consulta`
| Campo             | Tipo              | Descripción                             |
|------------------|-------------------|-----------------------------------------|
| id               | Integer           | ID autogenerado                         |
| ciudad           | String            | Ciudad consultada                       |
| tipoConsulta     | Enum (`ETipoConsulta`) | Tipo de consulta (ej: `CURRENT`)    |
| fechaHoraConsulta| LocalDateTime     | Fecha y hora de la consulta             |
| usuario          | Usuario           | Usuario que realizó la consulta         |

---

## 🔐 Seguridad

La seguridad está basada en **JWT**:

- Al registrarse o iniciar sesión, se genera un **token JWT** con la información del usuario.
- El token se incluye en cada request a endpoints protegidos como `Bearer Token` en la cabecera.
- Solo los usuarios autenticados pueden realizar consultas y ver sus datos.

---

## ⚙️ Configuración del proyecto

1. Clona el repositorio:

```bash
git clone https://github.com/tuusuario/clima-api-springboot.git
cd clima-api-springboot

spring.datasource.url=jdbc:mysql://localhost:3306/tu_base_datos
spring.datasource.username=root
spring.datasource.password=tu_contraseña

openweather.api.key=TU_API_KEY
jwt.secret=d85fdfad667f0fed4487e3cbb8a67ed5d37db75bacf1981def8a572399a9cda0
./mvnw spring-boot:run
```

🙋‍♂️ Autor
Maximiliano Soriano
💼 Desarrollador Full Stack
📧 maxi.soriano.70.23@gmail.com
