package Model;

public class PersonalCurso {

    private int idCurso;
    private String nombre_Curso;
    private int idHistorial_Curso;
    private int idTrabajador;
    private String nombre_Trabajador;
    private String fecha_inicio;
    private String fecha_cierre;
    private String tipo_curso;
    private String estado_curso;

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre_Curso() {
        return nombre_Curso;
    }

    public void setNombre_Curso(String nombre_Curso) {
        this.nombre_Curso = nombre_Curso;
    }

    public int getIdHistorial_Curso() {
        return idHistorial_Curso;
    }

    public void setIdHistorial_Curso(int idHistorial_Curso) {
        this.idHistorial_Curso = idHistorial_Curso;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getNombre_Trabajador() {
        return nombre_Trabajador;
    }

    public void setNombre_Trabajador(String nombre_Trabajador) {
        this.nombre_Trabajador = nombre_Trabajador;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(String fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public String getTipo_curso() {
        return tipo_curso;
    }

    public void setTipo_curso(String tipo_curso) {
        this.tipo_curso = tipo_curso;
    }

    public String getEstado_curso() {
        return estado_curso;
    }

    public void setEstado_curso(String estado_curso) {
        this.estado_curso = estado_curso;
    }

}
