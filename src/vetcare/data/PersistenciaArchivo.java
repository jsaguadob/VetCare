package vetcare.data;

import vetcare.model.*;
import java.io.*;
import java.nio.file.*;

public class PersistenciaArchivo {

    private static final String DIR = "data";
    private static final String CLI_PATH = DIR + "/clientes.csv";
    private static final String MAS_PATH = DIR + "/mascotas.csv";
    private static final String CIT_PATH = DIR + "/citas.csv";
    private static final String CTR_PATH = DIR + "/contadores.csv";

    public static void guardarDatos() throws IOException {
        Files.createDirectories(Path.of(DIR));

        Veterinaria vet = Veterinaria.getInstancia();
        StringBuilder sb;

        sb = new StringBuilder("id,nombre,telefono,email,direccion\n");
        for (Cliente c : vet.getClientes()) {
            sb.append(c.getId()).append(",");
            sb.append(escaparCSV(c.getNombre())).append(",");
            sb.append(escaparCSV(c.getTelefono())).append(",");
            sb.append(escaparCSV(c.getEmail())).append(",");
            sb.append(escaparCSV(c.getDireccion())).append("\n");
        }
        Files.writeString(Path.of(CLI_PATH), sb.toString());

        sb = new StringBuilder("id,nombre,especie,raza,edad,sexo,idDueno\n");
        for (Mascota m : vet.getMascotas()) {
            sb.append(m.getId()).append(",");
            sb.append(escaparCSV(m.getNombre())).append(",");
            sb.append(escaparCSV(m.getEspecie())).append(",");
            sb.append(escaparCSV(m.getRaza())).append(",");
            sb.append(m.getEdad()).append(",");
            sb.append(m.getSexo().name()).append(",");
            sb.append(m.getIdDueno()).append("\n");
        }
        Files.writeString(Path.of(MAS_PATH), sb.toString());

        sb = new StringBuilder("id,idMascota,fecha,hora,motivo,diagnostico,estado\n");
        for (Cita c : vet.getCitas()) {
            sb.append(c.getId()).append(",");
            sb.append(c.getIdMascota()).append(",");
            sb.append(c.getFecha()).append(",");
            sb.append(c.getHora()).append(",");
            sb.append(escaparCSV(c.getMotivo())).append(",");
            sb.append(escaparCSV(c.getDiagnostico())).append(",");
            sb.append(c.getEstado()).append("\n");
        }
        Files.writeString(Path.of(CIT_PATH), sb.toString());

        sb = new StringBuilder("contadorCliente,contadorMascota,contadorCita\n");
        sb.append(vet.getContadorCliente()).append(",");
        sb.append(vet.getContadorMascota()).append(",");
        sb.append(vet.getContadorCita()).append("\n");
        Files.writeString(Path.of(CTR_PATH), sb.toString());
    }

    public static void cargarDatos() throws IOException {
        Veterinaria vet = Veterinaria.getInstancia();

        if (Files.exists(Path.of(CTR_PATH))) {
            String[] partes = Files.readAllLines(Path.of(CTR_PATH)).get(1).split(",");
            if (partes.length == 3) {
                try {
                    vet.setContadores(
                        Integer.parseInt(partes[0].trim()),
                        Integer.parseInt(partes[1].trim()),
                        Integer.parseInt(partes[2].trim())
                    );
                } catch (NumberFormatException e) {
                    // usar valores por defecto
                }
            }
        }

        if (Files.exists(Path.of(CLI_PATH))) {
            for (String linea : Files.readAllLines(Path.of(CLI_PATH))) {
                if (linea.startsWith("id") || linea.isBlank()) continue;
                String[] p = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (p.length >= 5) {
                    try {
                        Cliente c = new Cliente(
                            Integer.parseInt(p[0].trim()),
                            limpiarCSV(p[1]),
                            limpiarCSV(p[2]),
                            limpiarCSV(p[3]),
                            limpiarCSV(p[4])
                        );
                        vet.agregarCliente(c);
                    } catch (NumberFormatException e) {
                        // saltar linea invalida
                    }
                }
            }
        }

        if (Files.exists(Path.of(MAS_PATH))) {
            for (String linea : Files.readAllLines(Path.of(MAS_PATH))) {
                if (linea.startsWith("id") || linea.isBlank()) continue;
                String[] p = linea.split(",");
                if (p.length >= 7) {
                    try {
                        Sexo sexo = p[5].trim().equalsIgnoreCase("HEMBRA") ? Sexo.HEMBRA : Sexo.MACHO;
                        Mascota m = new Mascota(
                            Integer.parseInt(p[0].trim()),
                            limpiarCSV(p[1]),
                            limpiarCSV(p[2]),
                            limpiarCSV(p[3]),
                            Integer.parseInt(p[4].trim()),
                            sexo,
                            Integer.parseInt(p[6].trim())
                        );
                        vet.agregarMascota(m);
                    } catch (NumberFormatException e) {
                        // saltar
                    }
                }
            }
        }

        if (Files.exists(Path.of(CIT_PATH))) {
            for (String linea : Files.readAllLines(Path.of(CIT_PATH))) {
                if (linea.startsWith("id") || linea.isBlank()) continue;
                String[] p = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                if (p.length >= 7) {
                    try {
                        Cita c = new Cita(
                            Integer.parseInt(p[0].trim()),
                            Integer.parseInt(p[1].trim()),
                            p[2].trim(),
                            p[3].trim(),
                            limpiarCSV(p[4])
                        );
                        c.setDiagnostico(limpiarCSV(p[5]));
                        c.setEstado(p[6].trim());
                        vet.agregarCita(c);
                    } catch (NumberFormatException e) {
                        // saltar
                    }
                }
            }
        }
    }

    private static String escaparCSV(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }

    private static String limpiarCSV(String valor) {
        if (valor == null) return "";
        valor = valor.trim();
        if (valor.startsWith("\"") && valor.endsWith("\"")) {
            valor = valor.substring(1, valor.length() - 1);
            valor = valor.replace("\"\"", "\"");
        }
        return valor;
    }
}
