package vetcare.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private String direccion;
    private List<Integer> idsMascotas;

    public Cliente() {
        this.idsMascotas = new ArrayList<>();
    }

    public Cliente(int id, String nombre, String telefono, String email, String direccion) {
        super(id, nombre, telefono, email);
        this.direccion = direccion;
        this.idsMascotas = new ArrayList<>();
    }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Integer> getIdsMascotas() { return idsMascotas; }
    public void setIdsMascotas(List<Integer> idsMascotas) { this.idsMascotas = idsMascotas; }

    public void agregarMascota(int idMascota) {
        if (!idsMascotas.contains(idMascota)) {
            idsMascotas.add(idMascota);
        }
    }

    @Override
    public String toString() {
        return getId() + " - " + getNombre();
    }
}
