package vetcare.model;

public class Cita {
    private int id;
    private int idMascota;
    private String fecha;
    private String hora;
    private String motivo;
    private String diagnostico;
    private String estado;

    public static final String ESTADO_PROGRAMADA = "Programada";
    public static final String ESTADO_COMPLETADA = "Completada";
    public static final String ESTADO_CANCELADA = "Cancelada";

    public Cita() {
        this.estado = ESTADO_PROGRAMADA;
        this.diagnostico = "";
    }

    public Cita(int id, int idMascota, String fecha, String hora, String motivo) {
        this.id = id;
        this.idMascota = idMascota;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.diagnostico = "";
        this.estado = ESTADO_PROGRAMADA;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdMascota() { return idMascota; }
    public void setIdMascota(int idMascota) { this.idMascota = idMascota; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
