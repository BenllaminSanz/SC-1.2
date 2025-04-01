package Model;

public class Curso {
    private int idCurso;
    private String nombre_Curso;
    private int idTipo_Curso;
    private String nombre_Tipo_Curso;
    private int idCertificado;
    private String objetivo_curso;
    private int semanas;

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

    public int getIdTipo_Curso() {
        return idTipo_Curso;
    }

    public void setIdTipo_Curso(int idTipo_Curso) {
        this.idTipo_Curso = idTipo_Curso;
    }

    public String getNombre_Tipo_Curso() {
        return nombre_Tipo_Curso;
    }

    public void setNombre_Tipo_Curso(String nombre_Tipo_Curso) {
        this.nombre_Tipo_Curso = nombre_Tipo_Curso;
    }

    public int getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(int idCertificado) {
        this.idCertificado = idCertificado;
    }

    public String getObjetivo_curso() {
        return objetivo_curso;
    }

    public void setObjetivo_curso(String objetivo_curso) {
        this.objetivo_curso = objetivo_curso;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }
}
