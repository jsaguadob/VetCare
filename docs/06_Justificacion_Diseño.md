# Justificación del Diseño - VetCare

## 1. Lenguaje y Plataforma: Java Swing

| Decisión | Justificación |
|---|---|
| Java en lugar de .NET o Python | Java es multiplataforma, tiene un ecosistema maduro para aplicaciones de escritorio y no requiere licencias. Además, su sistema de tipos estático y orientación a objetos facilitan el modelado del dominio veterinario (Cliente, Mascota, Cita). |
| Swing en lugar de JavaFX | Swing es parte de la JDK estándar (Java SE) y está disponible sin dependencias externas. JavaFX se separó del JDK a partir de Java 11, lo que obligaría a incluir librerías adicionales. Swing, aunque más antiguo, es suficiente para una aplicación con formularios, tablas y paneles. Es la opción más portable para un entorno académico. |
| NetBeans Ant en lugar de Maven/Gradle | NetBeans es el IDE institucional y su formato nativo es Ant. No se requieren dependencias externas, por lo que la complejidad de Maven/Gradle no aporta beneficios. |

## 2. Look & Feel: Nimbus

| Decisión | Justificación |
|---|---|
| Nimbus en lugar del L&F predeterminado (Metal) o FlatLaf | Nimbus es un L&F moderno incluido en el JDK (desde Java 6u10). Proporciona bordes redondeados, degradados y una apariencia más limpia sin añadir librerías. FlatLaf requeriría un JAR externo. Se eligió Nimbus sobre Metal porque mejora la experiencia visual sin costo de mantenimiento adicional. |

## 3. Arquitectura General: MVC Ligero

| Decisión | Justificación |
|---|---|
| Separación en paquetes model / data / gui | Sigue el patrón MVC de manera informal: el modelo encapsula los datos y reglas de negocio, data maneja la lógica de almacenamiento, y gui contiene la presentación. Esto permite modificar la interfaz sin alterar el modelo y viceversa. |
| Una sola ventana con CardLayout | Una ventana única evita la proliferación de ventanas emergentes. CardLayout permite alternar entre la pantalla de bienvenida y la aplicación principal manteniendo el mismo JFrame, lo que da sensación de aplicación cohesiva. |

## 4. Patrón Singleton en Veterinaria

| Decisión | Justificación |
|---|---|
| Veterinaria como Singleton | Garantiza que todos los paneles de la GUI compartan la misma instancia de datos. No tiene sentido tener dos instancias independientes de la "clínica". Centraliza el acceso a los datos y simplifica la sincronización entre pestañas. |

## 5. Estructuras de Datos: ArrayList + HashMap

| Decisión | Justificación |
|---|---|
| ArrayList para listas de clientes y mascotas | Preserva el orden de inserción, permite iteración rápida y acceso por índice. Es la estructura más natural para mostrar en tablas (JTable). |
| HashMap<Integer, Mascota> y HashMap<Integer, Cita> | Proporciona acceso O(1) por ID para búsquedas y actualizaciones. Ideal para recuperar una mascota o cita específica sin recorrer toda la lista. |
| Cliente con lista propia de mascotas (ArrayList<Mascota>) | Permite navegar de Cliente → Mascotas sin recorrer el mapa global. Facilita la eliminación en cascada y la validación de pertenencia. |

## 6. Persistencia: Archivos CSV

| Decisión | Justificación |
|---|---|
| CSV en lugar de base de datos (SQLite, MySQL) | CSV es un formato de texto plano legible y editable manualmente. No requiere motor de base de datos, drivers JDBC ni configuración. Para una aplicación académica con datos locales, CSV es la opción más simple y portable. |
| Un archivo por entidad | clientes.csv, mascotas.csv, citas.csv separan responsabilidades y facilitan la depuración. |
| contadores.csv para IDs autoincrementales | Evita colisiones de ID al reiniciar la aplicación. Se persiste el último ID usado de cada tipo. |
| Carga al iniciar, guarda al cerrar | Los datos están disponibles inmediatamente al abrir la aplicación. El guardado al cerrar con gancho de ShutdownHook asegura que no se pierdan cambios incluso si el usuario cierra la ventana directamente. |

## 7. Manejo de Errores

| Decisión | Justificación |
|---|---|
| Validación de campos obligatorios en cada botón de guardar | El usuario recibe retroalimentación inmediata sin esperar a que la operación falle. Se usa JOptionPane.showMessageDialog para mensajes claros. |
| Validación de conflicto de horario en citas | Se verifica que no exista otra cita para la misma mascota en la misma fecha y hora antes de guardar. Esto protege la integridad de la agenda. |
| Confirmación antes de eliminar | Previene eliminaciones accidentales de clientes (que cascadea a mascotas y citas) y de mascotas. |

## 8. Diseño de la GUI

| Decisión | Justificación |
|---|---|
| Header oscuro con logo textual | Crea una identidad visual. El color oscuro (#2C3E50) contrasta con el contenido claro y da un aspecto profesional. |
| Botón "Salir" en el header | Permite volver a la pantalla de bienvenida sin cerrar la aplicación. Intuitivo y accesible desde cualquier pestaña. |
| JTabbedPane para las secciones | Organiza las funcionalidades en pestañas sin abrir nuevas ventanas. El usuario puede alternar rápidamente entre clientes, mascotas, citas e historial. |
| ChangeListener para refrescar combos y tablas | Garantiza que al cambiar de pestaña, JComboBoxes y JTables muestren datos actualizados. Es más confiable que addNotify() porque se dispara incluso cuando el panel ya fue mostrado antes. |
| Barra inferior horizontal en citas | Separa la acción de crear/editar (parte superior) de las acciones de gestionar citas existentes (Completar/Cancelar). Evita confusión entre "Guardar nueva cita" y "Completar cita existente". |
| Botones deshabilitados según estado de la cita | Solo se puede editar/completar/cancelar citas en estado "Programada". Esto evita errores de flujo (ej. cancelar una cita ya completada). |

## 9. Modelado de Clases

| Decisión | Justificación |
|---|---|
| Persona como clase abstracta | Refleja que no existen "personas genéricas" en el sistema, solo clientes. Sirve como clase base para añadir empleados o veterinarios en el futuro. |
| Cliente como clase concreta | Hereda de Persona y añade la lista de mascotas. Representa al dueño que registra a sus animales. |
| Sexo como Enum | Restringe los valores posibles a MACHO y HEMBRA. Un String permitiría valores inválidos. |
| Cita como clase con estado | El campo String estado ("Programada", "Completada", "Cancelada") controla el ciclo de vida de la cita. Se valida en la GUI qué transiciones están permitidas. |
| Encapsulamiento (atributos privados + getters/setters + validaciones) | Protege la integridad de los datos. Por ejemplo, no se puede asignar null a nombre o establecer valores vacíos. |

## 10. Flujo de Pantallas

| Decisión | Justificación |
|---|---|
| Pantalla de bienvenida inicial | Muestra el nombre de la clínica y un botón "Comenzar". Da una presentación profesional y evita que el usuario se enfrente directamente a la aplicación. |
| CardLayout para transición | Permite volver a la bienvenida con el botón "Salir". Es más limpio que cerrar y reabrir ventanas. |
| Sin ventanas modales para formularios | Todos los formularios están integrados en las pestañas. Esto evita la acumulación de ventanas y mantiene el flujo de trabajo dentro de la misma interfaz. |

## 11. Eliminación en Cascada

| Decisión | Justificación |
|---|---|
| Eliminar cliente elimina sus mascotas y citas | Mantiene la consistencia de los datos. Si un dueño se va de la clínica, sus mascotas y las citas de esas mascotas ya no tienen razón de existir. |
| Eliminar mascota elimina sus citas | Similar al punto anterior. Una cita sin mascota no tiene sentido. |
| Confirmación explícita al eliminar | El usuario debe confirmar antes de una eliminación en cascada. Esto evita pérdida accidental de datos. |

## 12. Diseño del Código

| Decisión | Justificación |
|---|---|
| UIConstantes como clase con métodos estáticos | Centraliza colores, fuentes y métodos de estilo (estiloBoton, estiloTabla, bordeTitulado, crearCampoTexto). Esto evita duplicación de código y facilita cambios de tema. |
| Cada panel en su propia clase | Separa responsabilidades. Cada formulario conoce solo los datos que necesita. |
| PersistenciaArchivo independiente del modelo | Los métodos de carga/guardado están aislados de la lógica de negocio. Si se cambia el formato (CSV → JSON, por ejemplo), solo se modifica esta clase. |

---

*Documento de justificación técnica del diseño del sistema VetCare.*
