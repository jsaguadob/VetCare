# Documento de Levantamiento de Requisitos - VetCare

## 1. Información General

| Campo | Detalle |
|-------|---------|
| **Proyecto** | VetCare |
| **Cliente** | Clínica Veterinaria Huellitas |
| **Equipo** | Programación II |
| **Versión** | 1.0 |
| **Fecha** | Mayo 2026 |

## 2. Alcance del Sistema

VetCare es un sistema de escritorio desarrollado en Java que automatiza la gestión de una clínica veterinaria. El sistema permite registrar clientes (dueños de mascotas), registrar pacientes (mascotas), agendar citas médicas y mantener un historial clínico básico por mascota.

### Funcionalidades incluidas

- Registro, consulta y listado de clientes
- Registro, consulta y listado de mascotas
- Agendamiento de citas médicas
- Completar citas con diagnóstico
- Cancelar citas
- Visualización de historial clínico por mascota
- Persistencia de datos en archivos CSV
- Interfaz gráfica de usuario (Java Swing)

### Funcionalidades excluidas (fuera de alcance)

- Facturación y cobros
- Gestión de inventario de medicamentos
- Módulo de usuarios con autenticación y roles
- Envío de recordatorios por correo electrónico o SMS
- Módulo de reportes estadísticos avanzados
- Conexión a base de datos relacional (solo persistencia en archivos)

## 3. Historias de Usuario

### HU-01: Registrar Cliente

> **Como** recepcionista de la clínica,
> **quiero** registrar un nuevo cliente con sus datos básicos,
> **para** poder asociarlo a sus mascotas y agendar citas a su nombre.

**Criterios de aceptación:**
- El formulario debe solicitar: nombre, teléfono, email y dirección
- El nombre es obligatorio
- El sistema debe asignar un ID único automáticamente
- El cliente registrado debe aparecer en la lista de clientes

### HU-02: Registrar Mascota

> **Como** recepcionista,
> **quiero** registrar una mascota asociada a un dueño existente,
> **para** mantener el expediente del paciente.

**Criterios de aceptación:**
- El formulario debe solicitar: nombre, especie, raza, edad, sexo y dueño
- Nombre, edad y dueño son obligatorios
- La edad debe ser un número entero válido entre 0 y 50
- El dueño debe seleccionarse de una lista de clientes registrados
- La mascota registrada debe aparecer en la lista de mascotas

### HU-03: Agendar Cita

> **Como** recepcionista,
> **quiero** agendar una cita médica para una mascota,
> **para** reservar un espacio en la agenda del veterinario.

**Criterios de aceptación:**
- Debe seleccionarse la mascota, fecha, hora y motivo
- La fecha debe tener formato AAAA-MM-DD y ser una fecha válida
- La hora debe tener formato HH:MM
- Motivo, fecha y hora son obligatorios
- La cita se crea con estado "Programada"

### HU-04: Completar Cita

> **Como** veterinario,
> **quiero** registrar el diagnóstico de una cita,
> **para** dejar constancia de la atención realizada.

**Criterios de aceptación:**
- Se debe seleccionar una cita de la lista
- Se debe ingresar el diagnóstico
- Al completar, la cita cambia a estado "Completada"

### HU-05: Cancelar Cita

> **Como** recepcionista,
> **quiero** cancelar una cita programada,
> **para** liberar el espacio en la agenda.

**Criterios de aceptación:**
- Solo se pueden cancelar citas en estado "Programada"
- El sistema debe solicitar confirmación antes de cancelar

### HU-06: Ver Listado de Citas

> **Como** recepcionista o veterinario,
> **quiero** ver todas las citas con opción de filtrarlas por estado,
> **para** tener una visión general de la agenda.

**Criterios de aceptación:**
- Mostrar todas las citas en una tabla
- Poder filtrar por: Todas, Solo Programadas, Solo Completadas, Solo Canceladas
- Al seleccionar una cita, mostrar el detalle completo

### HU-07: Consultar Historial Clínico

> **Como** veterinario,
> **quiero** consultar el historial clínico de una mascota específica,
> **para** conocer sus atenciones previas y diagnósticos.

**Criterios de aceptación:**
- Seleccionar una mascota de la lista
- Mostrar todas sus citas con fechas, motivos, diagnósticos y estados
- Mostrar un resumen del paciente (nombre, especie, edad, etc.)

### HU-08: Persistencia de Datos

> **Como** usuario del sistema,
> **quiero** que los datos se guarden automáticamente al cerrar el programa,
> **para** no perder la información entre sesiones de trabajo.

**Criterios de aceptación:**
- Al cerrar la ventana, los datos se guardan en archivos CSV
- Al iniciar el programa, los datos se cargan automáticamente
- Si no hay archivos de datos, el sistema inicia vacío sin errores
