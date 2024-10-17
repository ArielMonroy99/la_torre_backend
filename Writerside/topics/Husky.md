# JHusky

jHusky es una herramienta que permite automatizar la ejecución de Git hooks en proyectos Java,
específicamente aquellos que utilizan Maven. Esta herramienta facilita la configuración de 
scripts que se ejecutan en eventos clave, como pre-commit o pre-push, para garantizar que el 
código pase por validaciones automáticas antes de que sea enviado al repositorio.

## Instalación y configuración 
    
1. Añade JHusky al pom.xml  
    ```xml
       <build>
        <plugins>
            <!--  ... Otros plugins   -->
            <plugin>
                <groupId>io.github.dathin</groupId>
                <artifactId>jhusky</artifactId>
                <version>1.0.9</version>
            </plugin>
            <!--  ... Otros plugins  -->
        </plugins>
        <!--  ... Otras configuraciones  -->
    </build>
    ```
2. Añadir el goal 
   ```
   mvn jhusky:install -Ddirectory=.husky
   ```
3. Añadir un hook
   ```
   mvn jhusky:add -DhookPath=.husky/pre-commit -Dcommand="mvn clean test"
   git add .husky/pre-commit
   ```
4. Hacer un commit
   ```
   git commit -m "Keep calm and commit"
   # `mvn clean test` will run
   ```
> [Lista de hooks](https://git-scm.com/docs/githooks#_hooks)

> Para el proyecto se tiene configurado un hook de commit-msg por lo que solo es necesario el comando del paso 2 
{style="note"}
