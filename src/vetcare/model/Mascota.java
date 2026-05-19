package vetcare.model;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private Sexo sexo;
    private int idDueno;

    public Mascota() {}

    public Mascota(int id, String nombre, String especie, String raza, int edad, Sexo sexo, int idDueno) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.sexo = sexo;
        this.idDueno = idDueno;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }

    public int getIdDueno() { return idDueno; }
    public void setIdDueno(int idDueno) { this.idDueno = idDueno; }

    @Override
    public String toString() {
        return getId() + " - " + getNombre() + " (" + getEspecie() + ")";
    }
}
