# Anotaciones de Api Doc
Para el documentar los endpoints se utilizarán anotaciones  


## Anotaciones de Swagger
### 1. `@Tag`
- **Descripción**: Utilizada para agrupar y clasificar los endpoints del controlador en la documentación de la API.
- **Ejemplo**:
  ```java
  @Tag(name = "Políticas")
  ```
### 2. `@SecurityScheme`

- **Descripción**: Define un esquema de seguridad para la API.
- **Ejemplo**: 
    ```java 
    @SecurityScheme(
  name = "bearerToken",
  type = SecuritySchemeType.HTTP,
  scheme = "bearer",
  bearerFormat = "JWT"
  )
  ```
### 3. `@Operation`

- **Descripción**: Documenta un endpoint específico, proporcionando detalles como descripción y resumen.
- **Ejemplo**: 
    ```
  @Operation(description = "Endpoint para listar todas las políticas")

  ```
- **Propósito**: Proporciona información relevante sobre lo que hace el endpoint, mejorando la comprensión de su funcionalidad. 
#### Parámetros de @Operation
 - **description**: Breve descripción del endpoint.
 - **summary**: Un resumen conciso del propósito del endpoint (opcional).

### 4. `@SecurityRequirement`

- **Descripción**:  Indica que un endpoint requiere autenticación específica.
- **Ejemplo**: 
   ```java
  @SecurityRequirement(name = "bearerToken")
  ```
- **Propósito**:  Informa a los consumidores de la API que deben proporcionar un token válido para acceder a los endpoints protegidos.

## Ejemplo de uso en un endpoint
Aquí tienes un ejemplo que muestra cómo se combinan estas anotaciones en un endpoint del ```PolicyController```:

```java  
@GetMapping()
@Operation(description = "Endpoint para listar todas las políticas")
@SecurityRequirement(name = "bearerToken")
public ResponseEntity<List<CasbinRule>> getAllPolicies() {
    return ResponseEntity.ok(policyService.getPolicies());
}

```

## Ejemplo de uso en un controlador
Aquí hay un ejemplo que muestra como se utilizan otras anotaciones en el controlador del ```PolicyController```

```java
@RestController
@RequestMapping("/policy")
@Tag(name = "Productos", description = "Operaciones relacionada a las políticas de acceso")
@SecurityScheme(
    name = "bearerToken",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
    )
public class PolicyController {
//controladores y demas
```

> **Nota:** Todos los endpoints registrados se pueden ver en Swagger UI, en la ruta 
> ```/api/v1/swagger-ui.html``` o en el siguiente enlace [aquí](http://localhost:8080/api/v1/swagger-ui.html)
