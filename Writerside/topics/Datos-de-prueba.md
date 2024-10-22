# Flyway migrations

Las migraciones de Flyway son una forma de gestionar y versionar
los cambios en la estructura de una base de datos de manera ordenada y automática. 
Flyway es una herramienta que permite aplicar, registrar y controlar las migraciones 
(cambios) de esquema, garantizando que la base de datos esté siempre en una versión 
coherente con el código de la aplicación.

## ¿Qué son las migraciones de Flyway?

Una migración es un cambio en la base de datos que puede incluir:

- Creación o modificación de tablas.
- Creación de índices.
- Inserciones o actualizaciones de datos.
- Alteraciones en constraints, triggers, etc.

Flyway gestiona estos cambios con archivos SQL o scripts Java que son aplicados secuencialmente para mantener la base de datos en un estado consistente.

**¿Para qué sirven las migraciones de Flyway?**

- Controlar la versión de la base de datos: Cada migración tiene una versión, permitiendo que Flyway aplique los cambios en el orden correcto y mantenga un historial.
- Automatizar actualizaciones: Cada vez que se despliega una nueva versión de la aplicación, Flyway puede automáticamente aplicar las migraciones necesarias para actualizar el esquema de la base de datos.
- Evitar problemas de sincronización: Al versionar los cambios en la base de datos, se garantiza que tanto los entornos de desarrollo, pruebas y producción estén siempre sincronizados.
- Reversibilidad: Flyway permite también "deshacer" migraciones si algo sale mal, aplicando el rollback correspondiente.

## Implementación

### Pasos para configurar flyway

#### 1. Flyway plugin   
    
   Debemos agregar el siguiente plugin a nuestro pom

``` XML
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <version>10.20.0</version>
    <configuration>
        <configFiles>
            <configFile>
                src/main/resources/flyway.conf
            </configFile>
        </configFiles>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.flywaydb</groupId>
            <artifactId>flyway-database-postgresql</artifactId>
            <version>10.20.0</version>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</plugin>
   ``` 
Este puglin nos permitirá realizar las migraciones desde la linea de comandos de maven

#### 2. Flyway.conf  
   Para especificar que los datos necesarios al plugin debemos añadir el archivo `flyway.conf` en la 
   ruta que especificamos en la tag `<configFile>` de nuestro plugin.  
   El contenido del archivo `flyway.conf` debe tener el siguiente contenido
   ``` text
    flyway.user=postgres
    flyway.password=password
    flyway.schemas=public
    flyway.url=jdbc:postgresql://localhost:5432/torre
    flyway.locations=filesystem:src/main/resources/db/migration,classpath:com/torre/backend/db/seeds
  ```
Donde se especifica el usuario, contraseña, esquemas y la url de nuestra base de datos ademas de definir donde 
se encontraran nuestras migraciones

#### 3. Crear nuevas migraciones 

Para crear nuevas migraciones usaremos las herramientas que proporciona IntelliJIdea 

1. Por lo que nos dirigiremos a apartado de base de datos y crearemos una nueva conexión
   ![Conexión con la base de datos](nuevaConexion.png 'Conexión con la base de datos')
2. Ingresaremos los datos de nuestra DB 
   ![Datos de la conexión](datosBd.png)
3. Damos clic en nuestra conexión y seleccionamos `Crear migración de Flyway` 
   ![Menu contextual](3.png)
4. Selecciónamos en el primer apartado model y para el segundo nuestra conexión
   ![Migración](4.png)
5. Esto nos mostrara un archivo sql donde nos aseguraremos que este en la ruta 
   `src/main/resources/db/migration`
   ![Migración sql](5.png)

### Java Base Migration

Flyway permite ejecutar migraciones desde código Java para esto iremos al apartado de project , haremos clic derecho 
y seleccionaremos `Java Flyway migration` esto mostrará una nueva ventana donde 
ingresaremos el nombre de la migración. Esto generará una nuevo archivo `.java` 
donde podremos definir la lógica de nuestra migración

> Las migraciones de **Flyway** tienen una notación estricta 
> Todas deben empezar con `V` seguido con el número de migración, doble `_` y el nombre de la migración  
> Ej. `V1__init_schema.sql` o `V2__users.java`
{style = note}