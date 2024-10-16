# Reglas de Casbin
Casbin es un framework de control de acceso que utiliza un modelo de autorización basado en políticas. 
Las reglas de Casbin son definiciones que determinan quién puede hacer qué en un sistema.
Estas reglas se almacenan en una base de datos o archivo y se aplican para gestionar el acceso a recursos.
## Estructura de una regla

Una regla típica en Casbin tiene la siguiente estructura:
```p, sub, obj, act```

- **p**: Representa una regla de política (policy).
- **sub**: Es el sujeto (subject) que está intentando acceder al recurso. Puede ser un usuario, un rol, etc.
- **obj**: Es el objeto (object) al que se intenta acceder. Puede ser un recurso, una ruta, etc.
- **act**: Es la acción (action) que se está intentando realizar sobre el objeto, como "read", "write", "delete", etc.
## Tipos de reglas

1. **Políticas (p)**: Estas son las reglas que definen los permisos que tienen los sujetos sobre los objetos. Por ejemplo:
    ```
    p, alice, data1, read
    p, bob, data2, write
    ```

2. **Reglas de Denegación (d)**: Estas son reglas que indican explícitamente que un sujeto no tiene acceso a un objeto. Pueden ser útiles para crear excepciones. Por ejemplo:
    ```
   d, eve, data1, read
    ```
3. **Reglas de Supervisión (e)**: Estas reglas permiten definir políticas de control que se aplican en casos especiales o para auditar accesos. Por ejemplo:
   ```
   e, some, policy
   ```
   
## Evaluación de Acceso
Cuando se evalúa una solicitud de acceso, Casbin comprueba las reglas definidas para determinar si el acceso debe ser concedido o denegado, siguiendo estos pasos:

1. **Identificación del Sujeto**: Se identifica quién está haciendo la solicitud.
2. **Identificación del Objeto y Acción**: Se identifica el recurso al que se intenta acceder y la acción que se desea realizar.
3. **Evaluación de Reglas**: Casbin evalúa las reglas de política, denegación y grupo para determinar el resultado.

## Implementación dentro del proyecto 

### Anotación @CasbinFilter
La anotación @CasbinFilter se utiliza para aplicar reglas de autorización basadas en Casbin a métodos específicos 
dentro de un controlador o servicio en Spring Boot. Esta anotación permite asegurar que solo los usuarios o roles 
con los permisos adecuados puedan acceder a ciertos recursos o ejecutar ciertas acciones.

#### Comportamiento
Cuando un método está marcado con @CasbinFilter, se realiza lo siguiente:

1. **Interceptación de la solicitud**: Se captura la URI solicitada y el método HTTP (GET, POST, etc.).
2. **Extracción de roles**: Se recuperan los roles asociados al usuario que realiza la solicitud.
3. **Evaluación de permisos**: A través del enforcer de Casbin, se verifica si alguno de los roles tiene permiso para ejecutar la acción solicitada sobre el recurso específico.
4. **Control de acceso**: Si el usuario tiene los permisos adecuados, se permite la ejecución del método; de lo contrario, se lanza una excepción de acceso denegado.

#### Ejemplo de uso 

``` java
@CasbinFilter
@GetMapping()
public ResponseEntity<List<CasbinRule>> getAllPolicies() {
    // Lógica del método...
}
```
En este ejemplo, el método getAllPolicies solo se ejecutará si el usuario tiene permisos suficientes, según las políticas de Casbin configuradas.
