# APP LOOKIERO
- Lookiero Application

Proyecto Java - Spring boot.

En él se expone una API REST con distintos endpoints donde se pueden realizar pequeños servicios implementada una lógica de negocio establecida.
Base de datos relacional MySQL - 2 Tablas.

Para arrancar el proyecto se puede utilizar el IDE o desde la raíz del proyecto con el comando de maven:
mvn spring-boot:run

- El proyecto está construido y configurado con Swagger, por lo que se puede invocar los servicios desde: http://localhost:8080/swagger-ui/index.html
- En la carpeta postman, se encuentran el Environment y la Colección API, para ser importados y poder usar Postman para la invocación de los servicios.
## MYSQL
### DDL para generar la Tabla USER

CREATE TABLE `USER` (
`USERNAME` varchar(45) NOT NULL,
`PASSWORD` varchar(45) NOT NULL,
`BIRTH_DAY` varchar(10) NOT NULL,
`HEIGHT` varchar(45) NOT NULL,
`WEIGHT` varchar(45) NOT NULL,
PRIMARY KEY (`USERNAME`),
UNIQUE KEY `USERNAME_UNIQUE` (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

### DDL para generar la Tabla USER_STATISTICS

CREATE TABLE `USER_STATISTICS` (
`ID` int NOT NULL AUTO_INCREMENT,
`USERNAME` varchar(45) NOT NULL,
`AGE` int NOT NULL,
`BMI` varchar(6) NOT NULL,
`CATEGORY_BMI` varchar(45) NOT NULL,
PRIMARY KEY (`ID`),
KEY `fk_User_idx` (`USERNAME`),
CONSTRAINT `fk_User` FOREIGN KEY (`USERNAME`) REFERENCES `user` (`USERNAME`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

### QUERIES PARA INSERTAR VALORES EN LA TABLA DE USER

- Es opcional, se puede usar el endpoint create para ello. Si invocamos el endpoint createUser, se generará el usuario y las estadísticas (Tablas User y User-Statistics).
- Si se inserta manualmente, sólo creará el User (Tabla User), por lo que habrá que invocar el endpoint put updateHeightOrWeight para generar las estadísticas.

INSERT INTO USER (USERNAME, PASSWORD, BIRTH_DAY, HEIGHT, WEIGHT)
VALUES("userLookiero", "ieroKloo", "10-07-1985", "1,87", 80);
INSERT INTO USER (USERNAME, PASSWORD, BIRTH_DAY, HEIGHT, WEIGHT)
VALUES("Magda1976", "gdMA76", "13-02-1976", "1,67", 50);
INSERT INTO USER (USERNAME, PASSWORD, BIRTH_DAY, HEIGHT, WEIGHT)
VALUES("Goyo1999", "ogyo9991", "30-11-1999", "1,72", 87);

### CURL's

#### Obtener el usuario XXX
- curl --location --request GET 'http://localhost:8080/lookiero?username=userLookiero'

#### Obtener todos los usuarios
- curl --location --request GET 'http://localhost:8080/lookiero/users'

#### Obtener el listado de Estadísticas de todos los usuarios registrados
- curl --location --request GET 'http://localhost:8080/lookiero/statistics'

#### Crear un usuario
- curl --location --request POST 'http://localhost:8080/lookiero' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "username" : "usuarioPrueba",
  "password" : "prueba00",
  "birthdate" : "10-10-1990",
  "height" : "1,80",
  "weight" : 85
  }'

#### Actualizar los datos de Height y Weight de un Usuario
- curl --location --request PUT 'http://localhost:8080/lookiero' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "username" : "usuarioPrueba",
  "height" : "1,85",
  "weight" : 80
  }'

#### Anotaciones

El proyecto está basado en un principio básico de una arquitectura DDD.
He querido usar una clase de Servicio Utils para implementar aquella lógica de chequeo, modificación, parseo, etc... para abstraer al servicio y generar un código más limpio.
He usado el uso de FunctionalInterface para separar y ver más claro cada servicio.
He usado MapStruct para el mapeo de Modelos-Entidades, tanto en la capa de application como de infrastructure.
