# Git hooks

Los Git hooks son scripts personalizados que se ejecutan automáticamente en respuesta a determinados eventos en un repositorio de Git. Estos eventos pueden ocurrir tanto en el lado del cliente (cuando realizas un commit, por ejemplo) como en el lado del servidor (cuando alguien empuja cambios a un repositorio remoto).

Existen dos tipos principales de hooks:

- Hooks del lado del cliente: Se ejecutan en eventos como `commit`, `merge`, `checkout` y más. Estos se utilizan comúnmente para validar mensajes de commit, ejecutar pruebas antes de permitir un commit, o asegurarse de que el código cumpla con ciertos estándares de estilo.

- Hooks del lado del servidor: Se activan en eventos como `push` y `receive`, y suelen utilizarse para verificar o rechazar cambios que se suben al repositorio, por ejemplo, asegurándose de que los commits cumplan ciertas reglas antes de integrarlos.

Los hooks son potentes herramientas para automatizar flujos de trabajo y garantizar la calidad del código en un proyecto colaborativo.

