# Documento de Arquitectura - VetCare

## 1. Justificación de la Arquitectura

VetCare está construido siguiendo una arquitectura en capas con el patrón MVC (Modelo-Vista-Controlador), utilizando Java SE con Swing para la interfaz gráfica. A continuación se justifican las decisiones técnicas.

## 2. Pilares de POO Implementados

### 2.1 Encapsulamiento

Todas las clases del paquete `model` utilizan atributos privados (`private`) con métodos getters y setters públicos. Esto protege los datos y permite controlar cómo se accede y modifica cada atributo.

**Ejemplo en `Persona.java`:**
```java
private int id;
private String nombre;
public int getId() { return id; }
public void setId(int id) { this.id = id; }
```

### 2.2 Herencia

La clase abstracta `Persona` define la estructura base, y `Cliente` extiende sus atributos y comportamientos. Esto evita duplicación de código y establece una jerarquía clara.

```
Persona (abstracta)
  └── Cliente
```

### 2.3 Abstracción

`Persona` es una clase abstracta que define el contrato mínimo para cualquier persona en el sistema (id, nombre, teléfono, email). No se puede instanciar directamente, obligando a usar sus subclases.

### 2.4 Polimorfismo

Aunque en esta versión solo existe una subclase de `Persona`, la estructura está preparada para añadir más tipos (ej: `Veterinario`) que hereden de `Persona` y puedan ser tratados polimórficamente.

## 3. Estructuras de Datos (Colecciones Java)

| Colección | Ubicación | Propósito |
|-----------|-----------|-----------|
| `ArrayList<Cliente>` | `Veterinaria` | Almacenar clientes en orden de inserción |
| `ArrayList<Mascota>` | `Veterinaria` | Almacenar mascotas |
| `ArrayList<Cita>` | `Veterinaria` | Almacenar citas |
| `HashMap<Integer, Cliente>` | `Veterinaria` | Búsqueda rápida de clientes por ID (O(1)) |
| `HashMap<Integer, Mascota>` | `Veterinaria` | Búsqueda rápida de mascotas por ID |
| `HashMap<Integer, Cita>` | `Veterinaria` | Búsqueda rápida de citas por ID |

Se eligió `ArrayList` para preservar el orden de inserción y `HashMap` para búsquedas eficientes por ID.

## 4. Patrón Singleton

La clase `Veterinaria` implementa el patrón Singleton:

```java
private static Veterinaria instancia;
private Veterinaria() { ... }
public static Veterinaria getInstancia() { ... }
```

**Justificación:**
- Garantiza una única fuente de verdad para todos los datos
- Evita pasar referencias entre ventanas
- Fácil acceso desde cualquier punto del programa

## 5. Patrón MVC

| Componente | Clases | Rol |
|------------|--------|-----|
| **Modelo** | `Persona`, `Cliente`, `Mascota`, `Cita`, `Sexo` | Representan los datos del dominio |
| **Vista** | `VentanaPrincipal`, `Panel*` | Interfaz gráfica que muestra datos al usuario |
| **Controlador** | `Veterinaria` | Contiene la lógica de negocio y orquesta las operaciones |

Los paneles GUI (`Panel*`) se comunican únicamente con `Veterinaria` (el controlador), nunca directamente con el modelo, lo que sigue el principio de separación de responsabilidades.

## 6. Manejo de Errores

Todas las operaciones críticas están envueltas en bloques `try-catch`:

```java
try {
    int edad = Integer.parseInt(txtEdad.getText());
    // procesar...
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(this, "La edad debe ser un numero entero valido.");
}
```

**Tipos de errores manejados:**
- `NumberFormatException` - Entrada de texto donde se espera un número
- `DateTimeParseException` - Formato de fecha inválido
- `IOException` - Errores de lectura/escritura de archivos
- `Exception` - Captura genérica para evitar cierres inesperados

## 7. Persistencia Básica

Se utiliza el formato CSV (Comma-Separated Values) por su simplicidad y portabilidad:

| Archivo | Contenido |
|---------|-----------|
| `clientes.csv` | id, nombre, teléfono, email, dirección |
| `mascotas.csv` | id, nombre, especie, raza, edad, sexo, idDueño |
| `citas.csv` | id, idMascota, fecha, hora, motivo, diagnóstico, estado |
| `contadores.csv` | contadorCliente, contadorMascota, contadorCita |

**Ventajas del formato CSV:**
- Legible por humanos (se puede abrir con bloc de notas o Excel)
- Fácil de implementar sin librerías externas
- Suficiente para el alcance del proyecto (persistencia básica)

## 8. Estructura del Proyecto

```
VetCare/
├── build.xml                    # Script de compilación Ant
├── manifest.mf                  # Archivo manifiesto para JAR
├── nbproject/                   # Configuración del proyecto NetBeans
│   ├── build-impl.xml
│   ├── project.properties
│   └── project.xml
├── src/vetcare/                 # Código fuente
│   ├── VetCareApp.java          # Punto de entrada
│   ├── model/                   # Clases del modelo
│   ├── data/                    # Lógica de negocio y persistencia
│   └── gui/                     # Interfaz gráfica Swing
├── docs/                        # Documentación del proyecto
│   ├── 01_Requisitos.md
│   ├── 02_UML.md
│   ├── 03_Pruebas.md
│   ├── 04_Manual_Usuario.md
│   └── 05_Arquitectura.md
└── data/                        # Archivos CSV en tiempo de ejecución
```

## 9. Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|-----------|---------|-----------|
| Java SE | 25.0.2 | Lenguaje de programación |
| Java Swing | (incluido) | Interfaz gráfica de usuario |
| Apache Ant | 1.10.13 | Sistema de compilación |
| NetBeans | 24 | Entorno de desarrollo integrado (IDE) |
| CSV | - | Formato de persistencia de datos |
