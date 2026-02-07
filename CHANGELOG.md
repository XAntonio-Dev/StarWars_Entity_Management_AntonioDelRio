# Changelog

Registro de cambios realizados en el proyecto Star Wars Entity Management System.

## [2.0.0] - Entrega Final Tarea 4 (Aplicación Multifuncional)

### Añadido (Added)
* **Navegación Global:** Implementación de `ModalNavigationDrawer` para gestión del menú lateral y navegación entre pantallas principales.
* **Persistencia (Room):** Migración del repositorio de datos simulados (Mock) a una base de datos SQLite real usando la librería Room.
* **Validaciones de Negocio:**
    * Control de integridad: Prevención de nombres duplicados consultando la BD antes de insertar.
    * Validación de formato: Reglas estrictas para fechas (Regex) y campos obligatorios.
* **Notificaciones:** Integración de `NotificationChannel` para avisar al usuario tras la creación exitosa de una especie (requiere permiso `POST_NOTIFICATIONS`).
* **SQL Dinámico:** Ordenación de la lista realizada directamente en el DAO mediante consultas SQL (`ORDER BY`) para optimizar el rendimiento.
* **Animaciones:** Transiciones de entrada y salida (`enterTransition`, `exitTransition`) en el `NavHost`.

### Modificado (Changed)
* Actualización de la `TopAppBar` para adaptarse al estado de cada pantalla y soportar el menú lateral.
* Refactorización de los ViewModels para usar `viewModelScope` y operaciones asíncronas con base de datos.

---

## [1.0.0] - Entrega Tarea 3 (Arquitectura MVVM)

### Añadido (Added)
* Implementación de la librería Accompanist para gestionar animaciones de navegación.
* Nueva pantalla "About Us" accesible desde el menú de la barra superior.
* Lógica de borrado de especies directamente desde el listado principal.
* Componente personalizado de diálogo de alerta (`AlertDialog`) para confirmar eliminaciones.
* Funcionalidad de ordenación básica en el ViewModel.

### Modificado (Changed)
* Rediseño completo de las tarjetas del listado (`Card`) para incluir iconos y botones de acción.
* Arquitectura migrada completamente a MVVM con Inyección de Dependencias (Hilt).

### Corregido (Fixed)
* Solución al cierre inesperado al usar el botón "Atrás".
* Corrección en la maquetación de textos del listado para evitar superposiciones.