# API REST para Gestión de Proyectos, Desarrolladores y Tecnologías

**Desarrollado por:** Fernando Vaquero Buzón

Este proyecto consiste en una API REST creada para gestionar información relacionada con proyectos, desarrolladores y tecnologías. Fue desarrollado utilizando **Spring Boot** y sigue el patrón **Modelo-Vista-Controlador (MVC)**. Además, el proyecto está gestionado mediante **Git** como sistema de control de versiones.

## Índice
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instrucciones de Instalación y Configuración](#instrucciones-de-instalación-y-configuración)
- [Conclusiones](#conclusiones)
- [Agradecimientos y Contribuciones](#agradecimientos-y-contribuciones)
- [Licencia](#licencia)
- [Contacto](#contacto)

---

## Descripción del Proyecto

La API permite gestionar proyectos, desarrolladores y tecnologías, facilitando la ejecución de operaciones CRUD en las tablas correspondientes. Permite a los usuarios:
- Crear, leer, actualizar y eliminar proyectos, desarrolladores y tecnologías.
- Filtrar proyectos según el nombre o la tecnología asociada.
- Modificar el estado de los proyectos (de **"Desarrollo"** a **"Pruebas"**, y de **"Pruebas"** a **"Producción"**).

Este proyecto ha sido desarrollado como parte de un módulo de servidor, con énfasis en el uso de paginación en las consultas y un manejo adecuado de errores utilizando `ResponseEntity`.

---

## Tecnologías Utilizadas

Para ejecutar este proyecto, se necesitan las siguientes herramientas y tecnologías:

- **Java 17**
- **Spring Boot**
- **Maven** (como gestor de dependencias y construcción)
- **MySQL** (o cualquier otra base de datos compatible con JPA)
- **Thunder Client** (para realizar pruebas de la API)
- **Git** (para la gestión de versiones)

---

## Instrucciones de Instalación y Configuración

1. **Crear el Proyecto en Spring Boot**  
   Utiliza el [Spring Initializr](https://start.spring.io/) para generar un proyecto base con las dependencias necesarias.

2. **Modelado de la Base de Datos**  
   Define las entidades correspondientes para proyectos, desarrolladores y tecnologías utilizando JPA.

3. **Creación de Controladores**  
   Desarrolla los controladores que manejarán las solicitudes HTTP hacia los distintos endpoints de la API.

4. **Implementación de los Servicios**  
   Los servicios deben contener la lógica de negocio para las operaciones CRUD y de filtrado.

5. **Repositorio Git**  
   Crea el repositorio en **GitHub** y vincúlalo a tu proyecto de Spring Boot para el control de versiones.

---

## Conclusiones

Este proyecto demuestra la implementación de una API REST sólida para gestionar información de proyectos, desarrolladores y tecnologías. Utiliza **Spring Boot** con JPA para manejar las entidades y la base de datos, e incluye paginación y manejo de errores para garantizar una experiencia robusta y escalable. Además, el proyecto implementa buenas prácticas como validaciones de datos y documentación con Swagger. La correcta gestión de estados y relaciones entre las entidades garantiza un diseño eficiente.

---

## Agradecimientos y Contribuciones

Agradecimientos especiales a:

- **Joaquín Borrego Fernández**, por su valiosa orientación durante el desarrollo de este proyecto.
- Compañeros de clase, por sus comentarios constructivos y apoyo continuo.

---

## Licencia

Este proyecto se distribuye bajo la **licencia Apache 2.0**, permitiendo a otros usuarios usar, modificar y distribuir el código, siempre y cuando sigan los términos de la licencia, incluyendo la atribución adecuada y el cumplimiento de las restricciones relacionadas con el uso comercial o la redistribución.

---

## Contacto

Para cualquier consulta o más información:

- **Correo Electrónico:** fernandovaquero96@gmail.com  
- **GitHub:** [Perfil en GitHub](https://github.com/FernandoVB96)
