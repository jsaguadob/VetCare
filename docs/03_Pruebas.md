# Plan de Pruebas QA - VetCare

## 1. Introducción

Este documento describe los casos de prueba para verificar el correcto funcionamiento del sistema VetCare. Cada caso de prueba incluye: ID, descripción, precondiciones, pasos, resultado esperado y resultado obtenido.

## 2. Casos de Prueba

### CP-01: Registrar Cliente - Caso Feliz

| Campo | Valor |
|-------|-------|
| **ID** | CP-01 |
| **Descripción** | Registrar un cliente con datos válidos |
| **Precondiciones** | Sistema iniciado, pestaña "Clientes" activa |
| **Pasos** | 1. Ingresar nombre: "Juan Pérez"<br>2. Ingresar teléfono: "555-1234"<br>3. Ingresar email: "juan@mail.com"<br>4. Ingresar dirección: "Calle 123"<br>5. Click en "Registrar Cliente" |
| **Resultado Esperado** | Mensaje de éxito "Cliente registrado exitosamente con ID: 1". Cliente visible en la tabla. |
| **Resultado Obtenido** | |

### CP-02: Registrar Cliente - Nombre Vacío

| Campo | Valor |
|-------|-------|
| **ID** | CP-02 |
| **Descripción** | Intentar registrar cliente sin nombre |
| **Precondiciones** | Sistema iniciado |
| **Pasos** | 1. Dejar campo nombre vacío<br>2. Completar los demás campos<br>3. Click en "Registrar Cliente" |
| **Resultado Esperado** | Mensaje de advertencia: "El nombre del cliente es obligatorio". No se registra el cliente. |
| **Resultado Obtenido** | |

### CP-03: Registrar Mascota - Caso Feliz

| Campo | Valor |
|-------|-------|
| **ID** | CP-03 |
| **Descripción** | Registrar una mascota con datos válidos |
| **Precondiciones** | Existe al menos un cliente registrado |
| **Pasos** | 1. Ingresar nombre: "Firulais"<br>2. Seleccionar especie: "Perro"<br>3. Ingresar raza: "Labrador"<br>4. Ingresar edad: "3"<br>5. Seleccionar sexo: "Macho"<br>6. Seleccionar dueño de la lista<br>7. Click en "Registrar Mascota" |
| **Resultado Esperado** | Mensaje de éxito. Mascota visible en la tabla con el nombre del dueño. |
| **Resultado Obtenido** | |

### CP-04: Registrar Mascota - Edad Inválida

| Campo | Valor |
|-------|-------|
| **ID** | CP-04 |
| **Descripción** | Intentar registrar mascota con edad no numérica |
| **Precondiciones** | Existe al menos un cliente |
| **Pasos** | 1. Ingresar nombre: "Pelusa"<br>2. Ingresar edad: "tres" (texto)<br>3. Completar demás campos<br>4. Click en "Registrar Mascota" |
| **Resultado Esperado** | Mensaje de error: "La edad debe ser un numero entero valido". No se registra. |
| **Resultado Obtenido** | |

### CP-05: Registrar Mascota - Edad Fuera de Rango

| Campo | Valor |
|-------|-------|
| **ID** | CP-05 |
| **Descripción** | Intentar registrar mascota con edad negativa |
| **Precondiciones** | Existe al menos un cliente |
| **Pasos** | 1. Ingresar nombre: "Tortuga"<br>2. Ingresar edad: "-5"<br>3. Click en "Registrar Mascota" |
| **Resultado Esperado** | Mensaje de advertencia: "La edad debe ser un numero entre 0 y 50". |
| **Resultado Obtenido** | |

### CP-06: Agendar Cita - Caso Feliz

| Campo | Valor |
|-------|-------|
| **ID** | CP-06 |
| **Descripción** | Agendar una cita con datos válidos |
| **Precondiciones** | Existe al menos una mascota registrada |
| **Pasos** | 1. Seleccionar mascota de la lista<br>2. Fecha: "2026-05-20" (auto-completada)<br>3. Hora: "10:30"<br>4. Motivo: "Revisión general"<br>5. Click en "Agendar Cita" |
| **Resultado Esperado** | Mensaje de éxito. Cita visible en la tabla con estado "Programada". |
| **Resultado Obtenido** | |

### CP-07: Agendar Cita - Fecha Inválida

| Campo | Valor |
|-------|-------|
| **ID** | CP-07 |
| **Descripción** | Agendar cita con formato de fecha incorrecto |
| **Precondiciones** | Existe al menos una mascota |
| **Pasos** | 1. Ingresar fecha: "20-05-2026" (formato DD-MM-AAAA)<br>2. Completar demás campos<br>3. Click en "Agendar Cita" |
| **Resultado Esperado** | Mensaje de error: "La fecha debe tener el formato AAAA-MM-DD". |
| **Resultado Obtenido** | |

### CP-08: Completar Cita

| Campo | Valor |
|-------|-------|
| **ID** | CP-08 |
| **Descripción** | Completar una cita con diagnóstico |
| **Precondiciones** | Existe al menos una cita en estado "Programada" |
| **Pasos** | 1. Seleccionar la cita en la tabla<br>2. El ID aparece en "ID Cita seleccionada"<br>3. Ingresar diagnóstico: "Paciente sano, vacunación al día"<br>4. Click en "Completar Cita" |
| **Resultado Esperado** | Mensaje de éxito. Cita cambia a estado "Completada" con diagnóstico visible. |
| **Resultado Obtenido** | |

### CP-09: Cancelar Cita

| Campo | Valor |
|-------|-------|
| **ID** | CP-09 |
| **Descripción** | Cancelar una cita programada |
| **Precondiciones** | Existe al menos una cita en estado "Programada" |
| **Pasos** | 1. Seleccionar la cita en la tabla<br>2. Click en "Cancelar Cita"<br>3. Confirmar en el diálogo de confirmación |
| **Resultado Esperado** | Cita cambia a estado "Cancelada". |
| **Resultado Obtenido** | |

### CP-10: Filtrar Citas por Estado

| Campo | Valor |
|-------|-------|
| **ID** | CP-10 |
| **Descripción** | Filtrar citas por estado en el listado |
| **Precondiciones** | Existen citas en varios estados |
| **Pasos** | 1. Ir a pestaña "Listado de Citas"<br>2. Seleccionar filtro "Solo Completadas"<br>3. Click en "Filtrar" |
| **Resultado Esperado** | Solo aparecen citas con estado "Completada". |
| **Resultado Obtenido** | |

### CP-11: Historial Clínico

| Campo | Valor |
|-------|-------|
| **ID** | CP-11 |
| **Descripción** | Consultar historial clínico de una mascota |
| **Precondiciones** | La mascota tiene al menos una cita registrada |
| **Pasos** | 1. Ir a pestaña "Historial Clínico"<br>2. Seleccionar mascota<br>3. Click en "Consultar Historial" |
| **Resultado Esperado** | Tabla muestra todas las citas de la mascota. Resumen del paciente visible. |
| **Resultado Obtenido** | |

### CP-12: Persistencia de Datos

| Campo | Valor |
|-------|-------|
| **ID** | CP-12 |
| **Descripción** | Verificar que los datos se guardan y cargan correctamente |
| **Precondiciones** | Existen datos registrados en el sistema |
| **Pasos** | 1. Registrar cliente, mascota y cita<br>2. Cerrar el programa (los datos se guardan automáticamente)<br>3. Volver a abrir el programa<br>4. Verificar que los datos están presentes |
| **Resultado Esperado** | Todos los datos registrados están disponibles después de reiniciar. |
| **Resultado Obtenido** | |

### CP-13: Inicio Sin Datos Previos

| Campo | Valor |
|-------|-------|
| **ID** | CP-13 |
| **Descripción** | Iniciar el sistema sin archivos de datos |
| **Precondiciones** | No existen archivos CSV en la carpeta data/ |
| **Pasos** | 1. Eliminar carpeta data/<br>2. Iniciar VetCare |
| **Resultado Esperado** | El sistema inicia sin errores, con todas las listas vacías. Mensaje informativo opcional. |
| **Resultado Obtenido** | |

## 3. Resumen de Pruebas

| ID | Prioridad | Estado |
|----|-----------|--------|
| CP-01 | Alta | ⬜ Pendiente |
| CP-02 | Alta | ⬜ Pendiente |
| CP-03 | Alta | ⬜ Pendiente |
| CP-04 | Alta | ⬜ Pendiente |
| CP-05 | Media | ⬜ Pendiente |
| CP-06 | Alta | ⬜ Pendiente |
| CP-07 | Alta | ⬜ Pendiente |
| CP-08 | Alta | ⬜ Pendiente |
| CP-09 | Alta | ⬜ Pendiente |
| CP-10 | Media | ⬜ Pendiente |
| CP-11 | Alta | ⬜ Pendiente |
| CP-12 | Alta | ⬜ Pendiente |
| CP-13 | Alta | ⬜ Pendiente |
