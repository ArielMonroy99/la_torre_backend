# Datos de prueba

Para añadir datos de prueba se añadió la configuración al application.yml
    
   ``` yaml
   jpa:
      hibernate:
         ddl-auto: create
   ```

Esta configuración indica a Hibernate que debe crear el esquema de la base de datos 
cada vez que se inicia la aplicación. 
Esto asegura que siempre tengas un entorno limpio y actualizado, 
lo que permite cargar automáticamente el archivo import.sql (si existe) 
con datos iniciales. Es ideal para entornos de desarrollo y pruebas, 
ya que elimina cualquier dato anterior y facilita la prueba de nuevas 
funcionalidades con datos frescos.

## Añadir datos

Para añadir datos se deben agregar los inserts al archivo `import.sql` que se encuentra
en el directorio `src/main/resources` 

### Casbin 
En el caso de casbin se debe seguir el modelo que se encuentra en el directorio `src/main/resources/casbin/model.conf`

**Ejemplo de política de Casbin**: 

```sql
    insert into casbin_rule (id, ptype,v0,v1,v2) values (1,'p','ADMINISTRATOR','/api/v1/policy','GET|POST|PUT');
```
Donde v0 representa el sujeto, v1 el objeto y v2 las acciones que se pueden realizar

> No olvides reiniciar las secuencias después de añadir registros a una nueva tabla  
> **Ejemplo de reinicio de secuencia**
>   ```sql
>    SELECT setval('casbin_sequence',(SELECT MAX(id) FROM casbin_rule)); 
>    ```
{style="warning"}