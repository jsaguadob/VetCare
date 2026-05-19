package vetcare.data;

import vetcare.model.*;
import java.util.*;

public class Veterinaria {
    private static Veterinaria instancia;

    private List<Cliente> clientes;
    private List<Mascota> mascotas;
    private List<Cita> citas;
    private Map<Integer, Cliente> mapaClientes;
    private Map<Integer, Mascota> mapaMascotas;
    private Map<Integer, Cita> mapaCitas;

    private int contadorCliente = 1;
    private int contadorMascota = 1;
    private int contadorCita = 1;

    private Veterinaria() {
        clientes = new ArrayList<>();
        mascotas = new ArrayList<>();
        citas = new ArrayList<>();
        mapaClientes = new HashMap<>();
        mapaMascotas = new HashMap<>();
        mapaCitas = new HashMap<>();
    }

    public static Veterinaria getInstancia() {
        if (instancia == null) {
            instancia = new Veterinaria();
        }
        return instancia;
    }

    public static void reiniciarInstancia() {
        instancia = null;
    }

    // === CLIENTES ===

    public void agregarCliente(Cliente c) {
        clientes.add(c);
        mapaClientes.put(c.getId(), c);
    }

    public Cliente buscarCliente(int id) {
        return mapaClientes.get(id);
    }

    public Cliente buscarClientePorNombre(String nombre) {
        for (Cliente c : clientes) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
    }

    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    public boolean actualizarCliente(int id, String nombre, String telefono, String email, String direccion) {
        Cliente c = mapaClientes.get(id);
        if (c == null) return false;
        c.setNombre(nombre);
        c.setTelefono(telefono);
        c.setEmail(email);
        c.setDireccion(direccion);
        return true;
    }

    public boolean eliminarCliente(int id) {
        Cliente c = mapaClientes.remove(id);
        if (c != null) {
            clientes.remove(c);
            List<Mascota> mascotasDelCliente = getMascotasPorDueno(id);
            for (Mascota m : mascotasDelCliente) {
                mapaMascotas.remove(m.getId());
                mascotas.remove(m);
            }
            List<Cita> citasAEliminar = new ArrayList<>();
            for (Cita ci : citas) {
                Mascota m = mapaMascotas.get(ci.getIdMascota());
                if (m == null) {
                    citasAEliminar.add(ci);
                }
            }
            for (Cita ci : citasAEliminar) {
                mapaCitas.remove(ci.getId());
                citas.remove(ci);
            }
            return true;
        }
        return false;
    }

    // === MASCOTAS ===

    public void agregarMascota(Mascota m) {
        mascotas.add(m);
        mapaMascotas.put(m.getId(), m);
        Cliente dueno = mapaClientes.get(m.getIdDueno());
        if (dueno != null) {
            dueno.agregarMascota(m.getId());
        }
    }

    public Mascota buscarMascota(int id) {
        return mapaMascotas.get(id);
    }

    public List<Mascota> getMascotas() {
        return new ArrayList<>(mascotas);
    }

    public List<Mascota> getMascotasPorDueno(int idDueno) {
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota m : mascotas) {
            if (m.getIdDueno() == idDueno) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public boolean actualizarMascota(int id, String nombre, String especie, String raza, int edad, Sexo sexo, int idDueno) {
        Mascota m = mapaMascotas.get(id);
        if (m == null) return false;
        int idDuenoAnterior = m.getIdDueno();
        m.setNombre(nombre);
        m.setEspecie(especie);
        m.setRaza(raza);
        m.setEdad(edad);
        m.setSexo(sexo);
        if (idDuenoAnterior != idDueno) {
            Cliente duenoAnterior = mapaClientes.get(idDuenoAnterior);
            if (duenoAnterior != null) {
                duenoAnterior.getIdsMascotas().remove(Integer.valueOf(id));
            }
            m.setIdDueno(idDueno);
            Cliente nuevoDueno = mapaClientes.get(idDueno);
            if (nuevoDueno != null) {
                nuevoDueno.agregarMascota(id);
            }
        }
        return true;
    }

    public boolean eliminarMascota(int id) {
        Mascota m = mapaMascotas.remove(id);
        if (m == null) return false;
        mascotas.remove(m);
        Cliente dueno = mapaClientes.get(m.getIdDueno());
        if (dueno != null) {
            dueno.getIdsMascotas().remove(Integer.valueOf(id));
        }
        List<Cita> citasAEliminar = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getIdMascota() == id) {
                citasAEliminar.add(c);
            }
        }
        for (Cita c : citasAEliminar) {
            mapaCitas.remove(c.getId());
            citas.remove(c);
        }
        return true;
    }

    // === CITAS ===

    public boolean existeCitaEnHorario(int idMascota, String fecha, String hora, Integer excluirId) {
        for (Cita c : citas) {
            if (excluirId != null && c.getId() == excluirId) continue;
            if (c.getIdMascota() == idMascota && c.getFecha().equals(fecha) && c.getHora().equals(hora)) {
                return true;
            }
        }
        return false;
    }

    public void agregarCita(Cita c) {
        citas.add(c);
        mapaCitas.put(c.getId(), c);
    }

    public Cita buscarCita(int id) {
        return mapaCitas.get(id);
    }

    public List<Cita> getCitas() {
        return new ArrayList<>(citas);
    }

    public List<Cita> getCitasPorMascota(int idMascota) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getIdMascota() == idMascota) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public List<Cita> getCitasPorEstado(String estado) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getEstado().equals(estado)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public boolean actualizarDiagnostico(int idCita, String diagnostico) {
        Cita c = mapaCitas.get(idCita);
        if (c != null) {
            c.setDiagnostico(diagnostico);
            c.setEstado(Cita.ESTADO_COMPLETADA);
            return true;
        }
        return false;
    }

    public boolean cancelarCita(int idCita) {
        Cita c = mapaCitas.get(idCita);
        if (c != null && c.getEstado().equals(Cita.ESTADO_PROGRAMADA)) {
            c.setEstado(Cita.ESTADO_CANCELADA);
            return true;
        }
        return false;
    }

    // === CONTADORES ===

    public int generarIdCliente() { return contadorCliente++; }
    public int generarIdMascota() { return contadorMascota++; }
    public int generarIdCita() { return contadorCita++; }

    public int getContadorCliente() { return contadorCliente; }
    public int getContadorMascota() { return contadorMascota; }
    public int getContadorCita() { return contadorCita; }

    public void setContadores(int c, int m, int ci) {
        this.contadorCliente = c;
        this.contadorMascota = m;
        this.contadorCita = ci;
    }
}
