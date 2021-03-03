# quasar
# Operación fuego de quasar

## Arquitectura de Software

A continuaciòn se exponen diferentes vistas de la arquitectura de software.

### Vista de Paquetes

A continuación, se definen los paquetes que tiene el componente de software, cada paquete es una capa de software en nuestro componente y agrupa un conjunto de clases con responsabilidades comunes:

![Screenshot](/src/main/resources/img/architecture-PackageView.png)

* controller: Todos las clases que reciben peticiones HTTP a los end-point defindos.
* services: Todas las clases con lógica de negocio para Localizar nave y construir mensaje.
* entidades: Todas las clases que representan el negocio.
* exceptions: Todas las excepciones para el manejo de errores controlados.
* libraries: Dependencias con librerías externas.

La comunicación entre paquetes es unidireccional y solo con la capa siguiente inferior.

### Vista de clases

El diagrama de clases expone las clases necesarias para representar el negocio, los servicios con lógica y reglas de negocio y Controladores para atender la petición HTTP a los end-points.

![Screenshot](/src/main/resources/img/architecture-ClassView.png)

Seguimos los principios de [SOLID](https://en.wikipedia.org/wiki/SOLID) de programación orientada a objectos para: 

* Separar las responsabilidades de localización de nave y construir mensajes en diferentes servicios. 
* Usar características de herencia y polimorfismo para definir una nave espacial y extender su comportamiento en satélite y carguero 
* Invertir dependencias entre clases usando interfaces para la comunicación entre capa controller y services.

Tambien se usan buenas practicas de [arquitecturas limpias](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) para separar las responsabilidades en capas, controlar la comunicación entre capas, restringir el uso de clases de capas superiores desde una capa inferior e invertir el flujo de comunicación entre capas por medio de interfaces y el principio de inversión de dependencias.

Se utilizara Java y el framework Spring Boot para construir el componente Software.

### Vista de despliegue

El despliegue del componente se realizará en un entorno de Elastic Beanstalk, el cual nos proporciona un entorno de servidor web con las siguientes características:

Beanstalk Environment : el medio ambiente es el corazón de la aplicación. Cuando inicia un entorno, Beanstalk asigna varios recursos necesarios para ejecutar la aplicación correctamente.

Elastic Load Balancer : cuando la aplicación recibe varias solicitudes de un cliente, Amazon Route53 reenvía estas solicitudes al Elastic Load Balancer. El equilibrador de carga distribuye las solicitudes entre las instancias EC2 de Auto Scaling Group.

Auto Scaling Group : Auto Scaling Group inicia automáticamente instancias de Amazon EC2 adicionales para adaptarse a la carga creciente de su aplicación. Si la carga en su aplicación disminuye, Amazon EC2 Auto Scaling detiene las instancias, pero siempre deja al menos una instancia en ejecución.

Administrador de host : es un componente de software que se ejecuta en cada instancia EC2 que se ha asignado a su aplicación. El administrador de host es responsable de varias cosas como:

* Generar y monitorear archivos de registro de aplicaciones
* Generando eventos a nivel de instancia
* Monitoreo del servidor de aplicaciones

Grupos de seguridad: el grupo de seguridad es como un firewall para su instancia. Elastic Beanstalk tiene un grupo de seguridad predeterminado, que permite al cliente acceder a la aplicación mediante el puerto HTTP 80. También le brinda una opción en la que puede definir grupos de seguridad para el servidor de la base de datos. La siguiente imagen resume lo que hemos aprendido sobre el entorno de servidor web.

Arquitectura Amazon Elastic Beanstalk

![Screenshot](/src/main/resources/img/ElasticBeanstalkArchitecture.png)

## Ejecución

En la siguiente URL podrán acceder al servicio publicado en AWS con Elastic Beanstalk:

http://quasarfiremelitest-env.eba-ygihpkhc.us-east-1.elasticbeanstalk.com/api/v1/

Las pruebas se realizaron con insomnia rest.

POST TOPSECRET
![Screenshot](/src/main/resources/img/TestApi01.png)

POST TOPSECRET SPLIT
![Screenshot](/src/main/resources/img/TestApi02.png)

GET TOPSECRET SPLIT
![Screenshot](/src/main/resources/img/TestApi03.png)
