# Manual de Usuario - VetCare

## 1. Introducción

VetCare es un sistema de escritorio para la gestión de una clínica veterinaria. Permite registrar clientes y mascotas, agendar citas médicas y mantener un historial clínico.

## 2. Requisitos del Sistema

- Java 17 o superior instalado
- Sistema operativo: Windows, macOS o Linux
- Resolución de pantalla mínima: 1024x768

## 3. Inicio del Sistema

### Desde NetBeans
1. Abrir NetBeans
2. Ir a `File > Open Project`
3. Seleccionar la carpeta `VetCare`
4. Click en `Open Project`
5. Click derecho sobre el proyecto > `Run`

### Desde línea de comandos
```bash
cd C:\Source\Proyectos\VetCare
javac -d build\classes -sourcepath src src\vetcare\VetCareApp.java src\vetcare\model\*.java src\vetcare\data\*.java src\vetcare\gui\*.java
java -cp build\classes vetcare.VetCareApp
```

## 4. Pantalla Principal

Al iniciar el programa aparece una ventana con 5 pestañas:

1. **Clientes** - Registrar y listar dueños de mascotas
2. **Mascotas** - Registrar y listar pacientes
3. **Agendar Cita** - Crear y gestionar citas médicas
4. **Listado de Citas** - Visualizar y filtrar citas
5. **Historial Clínico** - Consultar historial por mascota

## 5. Registro de Clientes

1. Ir a la pestaña **Clientes**
2. Completar los campos: Nombre, Teléfono, Email, Dirección
3. Hacer click en **"Registrar Cliente"**
4. El cliente aparecerá en la tabla de la derecha

> **Nota:** El campo "ID" se asigna automáticamente.

## 6. Registro de Mascotas

1. Ir a la pestaña **Mascotas**
2. Completar: Nombre, Especie, Raza, Edad (años), Sexo
3. Seleccionar el Dueño de la lista desplegable
4. Click en **"Registrar Mascota"**

> **Importante:** Debe haber al menos un cliente registrado para registrar una mascota.

## 7. Agendar Cita

1. Ir a la pestaña **Agendar Cita**
2. Seleccionar la mascota de la lista
3. Ingresar fecha (AAAA-MM-DD) y hora (HH:MM)
4. Escribir el motivo de la consulta
5. Click en **"Agendar Cita"**

## 8. Completar o Cancelar Cita

### Completar
1. Seleccionar la cita en la tabla (hacer click sobre la fila)
2. El ID de la cita aparece en el campo "ID Cita seleccionada"
3. Escribir el diagnóstico
4. Click en **"Completar Cita"**

### Cancelar
1. Seleccionar la cita en la tabla
2. Click en **"Cancelar Cita"**
3. Confirmar la cancelación

> **Nota:** Solo se pueden cancelar citas en estado "Programada".

## 9. Listado de Citas

1. Ir a la pestaña **Listado de Citas**
2. Usar el filtro para mostrar: Todas, Programadas, Completadas o Canceladas
3. Click en "Filtrar" para aplicar el filtro
4. Seleccionar una cita para ver su detalle completo abajo

## 10. Historial Clínico

1. Ir a la pestaña **Historial Clínico**
2. Seleccionar la mascota
3. Click en **"Consultar Historial"**
4. La tabla muestra todas las citas de esa mascota
5. El resumen del paciente se muestra en la parte inferior

## 11. Guardado de Datos

Los datos se guardan automáticamente al cerrar el programa en archivos CSV dentro de la carpeta `data/`. Al volver a abrir el programa, los datos se cargan automáticamente.

Archivos generados:
- `clientes.csv` - Datos de los clientes
- `mascotas.csv` - Datos de las mascotas
- `citas.csv` - Datos de las citas
- `contadores.csv` - Contadores de IDs

## 12. Solución de Problemas

| Problema | Posible Causa | Solución |
|----------|--------------|----------|
| "El nombre es obligatorio" | Campo nombre vacío | Completar el nombre |
| "La edad debe ser un número" | Edad con texto no numérico | Ingresar solo números |
| "Fecha con formato inválido" | Fecha no sigue AAAA-MM-DD | Usar formato correcto: 2026-05-18 |
| No aparecen dueños en el combo | No hay clientes registrados | Registrar un cliente primero |
| No aparecen mascotas en el combo | No hay mascotas registradas | Registrar una mascota primero |
