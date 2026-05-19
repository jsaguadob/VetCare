# Diagramas UML - VetCare

## 1. Diagrama de Casos de Uso

![Diagrama de Casos de Uso](diagrama_casos_uso.png)

```mermaid
graph TD
    Actor[Veterinario/Recepcionista] -->|Registrar Cliente| UC1[Registrar Cliente]
    Actor -->|Registrar Mascota| UC2[Registrar Mascota]
    Actor -->|Agendar Cita| UC3[Agendar Cita]
    Actor -->|Completar Cita| UC4[Completar Cita con Diagnostico]
    Actor -->|Cancelar Cita| UC5[Cancelar Cita]
    Actor -->|Listar Citas| UC6[Listar y Filtrar Citas]
    Actor -->|Consultar Historial| UC7[Consultar Historial Clinico]
    Actor -->|Gestionar Datos| UC8[Persistencia en Archivos]
    UC3 -.->|incluye| UC2
    UC4 -.->|incluye| UC3
    UC7 -.->|incluye| UC2
```

### Descripción de Actores

| Actor | Descripción |
|-------|-------------|
| **Veterinario** | Profesional que atiende a las mascotas, completa citas y consulta historiales |
| **Recepcionista** | Personal administrativo que registra clientes, mascotas y agenda citas |

## 2. Diagrama de Clases

![Diagrama de Clases](diagrama_clases.png)

```mermaid
classDiagram
    class Persona {
        <<Abstract>>
        -int id
        -String nombre
        -String telefono
        -String email
        +getId() int
        +setId(int) void
        +getNombre() String
        +setNombre(String) void
        +getTelefono() String
        +setTelefono(String) void
        +getEmail() String
        +setEmail(String) void
    }
    class Cliente {
        -String direccion
        -List~Integer~ idsMascotas
        +getDireccion() String
        +setDireccion(String) void
        +getIdsMascotas() List
        +agregarMascota(int) void
        +toString() String
    }
    class Mascota {
        -int id
        -String nombre
        -String especie
        -String raza
        -int edad
        -Sexo sexo
        -int idDueno
    }
    class Sexo {
        <<Enumeration>>
        MACHO
        HEMBRA
    }
    class Cita {
        -int id
        -int idMascota
        -String fecha
        -String hora
        -String motivo
        -String diagnostico
        -String estado
        +ESTADO_PROGRAMADA String
        +ESTADO_COMPLETADA String
        +ESTADO_CANCELADA String
    }
    class Veterinaria {
        -List~Cliente~ clientes
        -List~Mascota~ mascotas
        -List~Cita~ citas
        -Map~Integer~Cliente~ mapaClientes
        -Map~Integer~Mascota~ mapaMascotas
        -Map~Integer~Cita~ mapaCitas
        +getInstancia() Veterinaria
        +agregarCliente(Cliente) void
        +buscarCliente(int) Cliente
        +getClientes() List
        +agregarMascota(Mascota) void
        +buscarMascota(int) Mascota
        +getMascotas() List
        +agregarCita(Cita) void
        +buscarCita(int) Cita
        +getCitas() List
        +actualizarDiagnostico(int~String~) boolean
        +cancelarCita(int) boolean
    }
    class PersistenciaArchivo {
        +guardarDatos() void
        +cargarDatos() void
    }
    class VentanaPrincipal {
        -JTabbedPane tabs
        +VentanaPrincipal()
    }
    class PanelRegistroCliente
    class PanelRegistroMascota
    class PanelAgendarCita
    class PanelListarCitas
    class PanelHistorialClinico
    Persona <|-- Cliente
    Cliente o-- Mascota : tiene
    Cita ..> Mascota : referencia
    Cita ..> Cliente : referencia
    Mascota --> Sexo
    Veterinaria --> Cliente : gestiona
    Veterinaria --> Mascota : gestiona
    Veterinaria --> Cita : gestiona
    PersistenciaArchivo ..> Veterinaria : persiste
    VentanaPrincipal --> PanelRegistroCliente
    VentanaPrincipal --> PanelRegistroMascota
    VentanaPrincipal --> PanelAgendarCita
    VentanaPrincipal --> PanelListarCitas
    VentanaPrincipal --> PanelHistorialClinico
    PanelRegistroCliente ..> Veterinaria
    PanelRegistroMascota ..> Veterinaria
    PanelAgendarCita ..> Veterinaria
    PanelListarCitas ..> Veterinaria
    PanelHistorialClinico ..> Veterinaria
```

## 3. Descripción de la Arquitectura

### Capas del Sistema

| Capa | Paquete | Responsabilidad |
|------|---------|-----------------|
| **Modelo** | `vetcare.model` | Clases del dominio: Persona, Cliente, Mascota, Cita, Sexo |
| **Datos** | `vetcare.data` | Lógica de negocio (Veterinaria) y persistencia (PersistenciaArchivo) |
| **Presentación** | `vetcare.gui` | Interfaz gráfica Swing: VentanaPrincipal y paneles |
| **Aplicación** | `vetcare` | Punto de entrada (VetCareApp) |

### Patrones de Diseño Utilizados

| Patrón | Ubicación | Justificación |
|--------|-----------|---------------|
| **Singleton** | `Veterinaria` | Garantiza una única instancia del gestor central de datos accesible desde toda la aplicación |
| **MVC** | Todo el sistema | Modelo (model + data), Vista (gui), Controlador (Veterinaria actúa como controlador) |
| **Template Method** | `Persona` (abstracta) | Define estructura base que `Cliente` extiende |
