# Star Wars Entity Management System

Proyecto realizado para la asignatura de Desarrollo de Interfaces (2.º DAM). Se trata de una aplicación Android nativa desarrollada en Kotlin para la gestión integral de especies del universo Star Wars.

## 📋 Descripción General

La aplicación implementa un sistema de gestión completo (CRUD) que permite al usuario interactuar con una base de datos local persistente. El objetivo principal de esta versión es demostrar la integración de **persistencia de datos**, **navegación global** y **reglas de negocio robustas**.

### Funcionalidades Principales:
* **Persistencia de Datos:** Almacenamiento local mediante **Room Database**. Los datos permanecen guardados al cerrar la aplicación.
* **Navegación Global:** Implementación de un **Navigation Drawer** (menú lateral) para el acceso fluido a las distintas secciones (Listado, Ajustes, Sobre Nosotros).
* **Gestión de Especies:**
    * Alta de registros con validación de integridad (control de duplicados y formato de fecha).
    * Edición de información existente.
    * Eliminación con confirmación de seguridad.
* **Notificaciones:** Feedback al usuario mediante notificaciones del sistema al realizar operaciones críticas.
* **Ordenación Inteligente:** Clasificación de listados mediante consultas SQL directas para máxima eficiencia.

## 🛠️ Tecnologías Utilizadas

El desarrollo se ha basado en el stack tecnológico moderno recomendado por Google (Modern Android Development):

* **Lenguaje:** Kotlin.
* **Interfaz de Usuario:** Jetpack Compose (Material Design 3).
* **Arquitectura:** MVVM (Model-View-ViewModel) con Clean Architecture.
* **Inyección de Dependencias:** Dagger Hilt.
* **Persistencia:** Room Database (SQLite abstract layer).
* **Asincronía:** Kotlin Coroutines & Flow.
* **Navegación:** Navigation Compose con animaciones personalizadas.

## 📱 Instalación y Permisos

Para el correcto funcionamiento de la aplicación en dispositivos con Android 13 (Tiramisu) o superior:
1.  Instalar el archivo `.apk` generado.
2.  Al iniciar, aceptar el permiso de **Notificaciones** para recibir confirmaciones de las operaciones de alta.

## 📝 Notas del Desarrollador

En esta versión (Tarea 4) se ha priorizado la robustez del dato y la experiencia de usuario (UX). Se han implementado validaciones en tiempo real en los formularios (feedback visual en rojo) y se ha migrado de un repositorio de datos estático a uno dinámico con Room, asegurando que no se pierda información entre sesiones.

## 🎥 Demo
[Ver vídeo de funcionamiento](assets/video_demostracion)

---
**Desarrollado por:** Antonio Del Rio
**Curso:** 2.º DAM
**Asignatura:** Desarrollo de Interfaces
**Fecha:** Febrero 2026