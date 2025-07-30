package Model;

public class HabilidadesCurso {

    private int idHabilidad;
    private String nombre_habilidad;
    private int orden_habilidad;
    private int idCurso;
    private String nombre_archivo;
    private String ruta_Docuemento;

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public void setIdHabilidad(int idHabilidad) {
        this.idHabilidad = idHabilidad;
    }

    public String getNombre_habilidad() {
        return nombre_habilidad;
    }

    public void setNombre_habilidad(String nombre_habilidad) {
        this.nombre_habilidad = nombre_habilidad;
    }

    public int getOrden_habilidad() {
        return orden_habilidad;
    }

    public void setOrden_habilidad(int orden_habilidad) {
        this.orden_habilidad = orden_habilidad;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getRuta_Docuemento() {
        return ruta_Docuemento;
    }

    public void setRuta_Docuemento(String ruta_Docuemento) {
        this.ruta_Docuemento = ruta_Docuemento;
    }
}
