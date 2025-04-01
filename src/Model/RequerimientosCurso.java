package Model;

public class RequerimientosCurso {

    private int idRequerimiento;
    private int idCurso;
    private String nombre_requerimiento;
    private String descp_requerimiento;
    private String nombre_archivo;
    private String ruta_Docuemento;

    public int getIdRequerimiento() {
        return idRequerimiento;
    }

    public void setIdRequerimiento(int idRequerimiento) {
        this.idRequerimiento = idRequerimiento;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre_requerimiento() {
        return nombre_requerimiento;
    }

    public void setNombre_requerimiento(String nombre_requerimiento) {
        this.nombre_requerimiento = nombre_requerimiento;
    }

    public String getDescp_requerimiento() {
        return descp_requerimiento;
    }

    public void setDescp_requerimiento(String descp_requerimiento) {
        this.descp_requerimiento = descp_requerimiento;
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
