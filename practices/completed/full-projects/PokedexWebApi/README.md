### Instrucciones para ejecutar Pokedex Web API

# [ENLACE A DOCUMENTACIÓN](https://www.notion.so/Pokedex-Web-and-API-26bb54dc02164be5bcf3f8461e28a894)
##### Arrancar API:

  > Abrir VS y hacer click en "abrir un proyecto o una solución". Seleccionar el archivo `API/Pokedex.sln`

- Una vez importado el proyecto, sigue estos pasos:

  - Arrancar una base de datos (preferentemente en el puerto 3306).
  - Ejecutar esta query: [SQL](https://gist.github.com/Zackysh/3802383e4ba18c2e16ce1f9ed12c18cc)
  - Si no tienes la BDD en el puerto 3306, dentro del proyecto ve a `appsettings.json` y cambia el puerto al que estés utilizando.
  - Si tienes un usuario `root` sin contraseña, puedes continuar, sino crealo o introduce tus credenciales en `appsettings.json`

##### Arrancar Web:

- Abre la carpeta `FE` con VS-Code (u otro editor) y ejecuta en la terminal los siguientes comandos:
  - `npm install`
  - `ng serve`

#### Cosas a tener en cuenta:

Si haces click en el nombre de los pokemons podrás ver sus detalles en una ventana a parte.
