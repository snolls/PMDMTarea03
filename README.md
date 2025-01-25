# PMDMTarea03

## Descripción General
PMDMTarea03 es una Pokédex interactiva que permite a los usuarios explorar, capturar y gestionar sus Pokémon favoritos. Ofrece una experiencia fluida y sencilla, integrando funcionalidades avanzadas como sincronización con Firebase y cambio dinámico de idioma entre inglés y español.

---

## Requisitos y Configuración

### Requisitos del Proyecto
- **Herramientas y Librerías Utilizadas:**
  - Android Studio
  - Firebase (Authentication, Firestore)
  - Retrofit (para consumir la API de Pokémon)
  - Glide (para cargar imágenes)
- **Configuración del Entorno:**
  - **minSdk:** 24
  - **targetSdk:** 35

### Configuración del Proyecto
1. Clona este repositorio en tu equipo local:
   ```bash
   git clone https://github.com/snolls/PMDMTarea03
   ```
2. Abre el proyecto en **Android Studio**.
3. Asegúrate de tener configurado **Firebase** en tu consola:
   - Descarga el archivo `google-services.json` desde Firebase Console y agrégalo a la carpeta `app` del proyecto.
4. Sincroniza las dependencias en Android Studio.

---

## Funcionalidades

- **Navegación entre fragmentos:** Los usuarios pueden moverse fácilmente entre las diferentes secciones de la aplicación mediante la barra de navegación inferior.
- **Captura y visualización de Pokémon:**
  - Visualiza una lista completa de Pokémon.
  - Captura Pokémon seleccionándolos de la lista.
  - Los Pokémon capturados se marcan en rojo y aparecen en una lista separada.
- **Cambio de idioma:** Cambia dinámicamente entre inglés y español desde la sección de ajustes.
- **Eliminación de Pokémon capturados:**
  - Activa la opción en ajustes para habilitar la eliminación.
  - Desliza un Pokémon capturado hacia la izquierda para eliminarlo.
- **Sincronización con Firebase:**
  - Los datos de los Pokémon capturados se almacenan en la nube mediante Firebase Firestore.

---

## Uso

### Guía para Usuarios

1. **Capturar Pokémon:**
   - Selecciona un Pokémon de la lista disponible.
   - Una vez capturado, el Pokémon aparecerá en la lista de capturados y se marcará en rojo.

2. **Cambiar el idioma:**
   - Ve a la sección de ajustes.
   - Activa el switch para cambiar a inglés o desactívalo para volver al español.

3. **Navegar por la aplicación:**
   - Utiliza los botones de navegación en la parte inferior de la pantalla para moverte entre las diferentes secciones.

4. **Eliminar un Pokémon capturado:**
   - Activa la opción de eliminación desde los ajustes.
   - Desliza un Pokémon capturado hacia la izquierda para eliminarlo.

---

## Tecnologías Utilizadas

- **Firebase:**
  - Authentication para gestionar el inicio de sesión.
  - Firestore para almacenar y sincronizar los datos de los Pokémon capturados.

- **Retrofit:**
  - Consumir la API de Pokémon para obtener la lista de Pokémon y sus detalles.

- **Glide:**
  - Cargar y mostrar imágenes de los Pokémon de manera eficiente.

