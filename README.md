# superheros W2M Application
Challenge tecnico para MinData/WorldToMeet

> **Desarrollado por [Marcelo Adrian Lemma](https://ar.linkedin.com/in/marcelo-lemma-123b04122).**

> Backend API desarrollada para el challenge técnico de **[MinData](https://www.mindata.es/)** para proyecto de **[WorldToMeet](https://www.w2m.travel/es-es/)**

## Descripción
Esta aplicación consta de seis endpointes dedicados a la administracion de la base de datos de superheroes. 

Poseé un método [POST]() para la carga de datos (requiere una basic authorization con permisos de "USER"), un método [PUT]() para la actualizacion de datos (requiere una basic authorization con permisos de "USER"),
un método [DELETE]() para la eliminacion de datos (requiere una basic authorization con permisos de "ADMIN") y tres métodos [GET]() para buscar superheroes por id, listar todos y buscar por parte del nombre (que no requieren la basic authorization)

## EndPoints

- **Ingresar nuevos superheroes**: 
> - Metodo: **POST**
> - URL: [https://{servidor}:{puerto}/w2m/superhero/save]()
> - Basic Auth (rol **USER**): 
>   - User: "User"
>   - Password: "User"
> - Request:
>>     {
>>         "name": "Superman",
>>         "secretIdentity": "Clark",
>>         "superPowers": [
>>             "Volar",
>>             "Super fuerza",
>>             "Super velocidad",
>>             "Vision laser"
>>         ]
>>     }
> - Response
>>     {
>>         "superheroId": 1,
>>         "name": "Superman",
>>         "secretIdentity": "Clark",
>>         "superPowers": [
>>             "Volar",
>>             "Super fuerza",
>>             "Super velocidad",
>>             "Vision laser"
>>         ]
>>     }

- **Actualizar superheroes guardados**:
> - Metodo: **PUT**
> - URL: [https://{servidor}:{puerto}/w2m/superhero/update/\[superhero-id\]]()
> - Basic Auth (rol **USER**):
>   - User: "User"
>   - Password: "User"
> - Request:
>>     {
>>         "name": "Superman",
>>         "secretIdentity": "Clark Kent",
>>         "superPowers": [
>>             "Volar",
>>             "Super fuerza",
>>             "Super velocidad",
>>             "Vision laser",
>>             "Soplido de hielo",
>>             "Super resistencia"
>>         ]
>>     }
> - Response
>>     {
>>         "superheroId": 1,
>>         "name": "Superman",
>>         "secretIdentity": "Clark Kent",
>>         "superPowers": [
>>             "Volar",
>>             "Super fuerza",
>>             "Super velocidad",
>>             "Vision laser",
>>             "Soplido de hielo",
>>             "Super resistencia"
>>         ]
>>     }

- **Eliminar superheroes guardados**:
> - Metodo: **DELETE**
> - URL: [https://{servidor}:{puerto}/w2m/superhero/delete/\[superhero-id\]]()
> - Basic Auth (rol **ADMIN**):
>   - User: "Admin"
>   - Password: "Admin"
> - Response: HttpStatus

- **Buscar superheroe por ID**:
> - Metodo: **GET**
> - URL: [https://{servidor}:{puerto}/w2m/superhero/\[searchId\]]()
> - Basic Auth: **N/A**
> - Response
>>     {
>>         "superheroId": 1,
>>         "name": "Superman",
>>         "secretIdentity": "Clark Kent",
>>         "superPowers": [
>>             "Volar",
>>             "Super fuerza",
>>             "Super velocidad",
>>             "Vision laser",
>>             "Soplido de hielo",
>>             "Super resistencia"
>>         ]
>>     }

- **Listar todos los superheroes**:
> - Metodo: **GET**
> - URL: [https://{servidor}:{puerto}/w2m/superhero/list]()
> - Basic Auth: **N/A**
> - Response
>>     [
>>         {
>>             "superheroId": 1,
>>             "name": "Superman",
>>             "secretIdentity": "Clark Kent",
>>             "superPowers": [
>>                 "Volar",
>>                 "Super fuerza",
>>                 "Super velocidad",
>>                 "Vision laser",
>>                 "Soplido de hielo",
>>                 "Super resistencia"
>>             ]
>>         },
>>         {
>>             "superheroId": 2,
>>             "name": "Hulk",
>>             "secretIdentity": "Bruce Banner",
>>             "superPowers": [
>>                 "Super fuerza",
>>                 "Super resistencia",
>>                 "Regeneracion acelerada"
>>             ]
>>         },
>>         {
>>             "superheroId": 3,
>>             "name": "Batman",
>>             "secretIdentity": "Bruce Wayne",
>>             "superPowers": [
>>                 "Ser rico"
>>             ]
>>         }
>>     ]

- **Listar los superheroes que contengan un string en el nombre**:
> - Metodo: **GET**
> - URL: [https://{servidor}:{puerto}/w2m/superhero/name-part/\[name-part\]]()
>   - name-part (String): example "MAN" 
> - Basic Auth: **N/A**
> - Response
>>     [
>>         {
>>             "superheroId": 1,
>>             "name": "Superman",
>>             "secretIdentity": "Clark Kent",
>>             "superPowers": [
>>                 "Volar",
>>                 "Super fuerza",
>>                 "Super velocidad",
>>                 "Vision laser",
>>                 "Soplido de hielo",
>>                 "Super resistencia"
>>             ]
>>         },
>>         {
>>             "superheroId": 3,
>>             "name": "Batman",
>>             "secretIdentity": "Bruce Wayne",
>>             "superPowers": [
>>                 "Ser rico"
>>             ]
>>         }
>>     ]

## Security

### Users and Roles

Los usuarios y sus roles son de ejemplo y se encuentran definidos en memoria en el código

- **Usuario** [User]()
  - UserName: **User**
  - Password: **User**
  - Roles: **USER**


- **Usuario** [Admin]()
  - UserName: **Admin**
  - Password: **Admin**
  - Roles: **USER, ADMIN**

## Validaciones

Los datos utilizados para el request al registrar nuevas busquedas, deben cumplir los siguientes requisitos:

- **name**: Se validará que no sea nulo, no sea una cadena vacía o que no solo contenga espacios en blanco y/o tabulaciones.


- **secretIdentity**: Se validará que no sea nulo, que no sea una cadena vacía o que no solo contenga espacion en blanco y/o tabulaciones.


- **superPowers**: Se que no sea nulo, pero si puede ser una lista vacía.

## Custom Annotation

Se implementa una Annotation **@TimeTracker** que se utiliza en todos los métodos del Controller. 
Esta Annotation medirá el tiempo de ejecución del método en milisegundos y lo almacenará en un archivo de log. 

Por defecto el archivo se almacena en el archivo "logs/time_tracker.log", además, cada archivo de log no podrá exceder los 10MB de tamaño.

Estos parámetros son modificables desde el archivo de configuracion **/resources/log4j.properties**

## Caché

Las APIs cuentan con una Cache, para agilizar las búsquedas en la base de datos, la cual se completa con las peticiones de tipo **GET** y se vacía con las peticiones de tipo **POST**, **PUT** y **DELETE** para mantener un correcto funcionamiento.

- Cache name: **"w2m-superhero"**

## Documentation

Esta aplicación cuenta con la documentación generada por **Swagger2**, desde la cual se pueden ver los métodos disponibles para el User con el que se ingrese.

- **Swagger-UI**
  - Access: [http://{servidor}:{puerto}/swagger-ui.html]()
  - Users: **User**, **Admin**

## Tecnologías

#### El proyecto fue desarrollado con:

- Java
- Spring Boot
- Spring Security
- JPA
- H2 Database
- JUnit
- Mockito
- Swagger
- Caché
- AOP (custom annotation)
- Log4J
