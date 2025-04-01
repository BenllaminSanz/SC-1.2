package Model;

public class HistorialCurso {

    private int idHistorialCurso;
    private int idCurso;
    private String nombre_Curso;
    private int idTipo_Curso;
    private String nombre_instructor;
    private int area_idArea;
    private int puesto_idPuesto;
    private int turno_idTurno;
    private String area;
    private String fecha_inicio;
    private String fecha_estimada;
    private String fecha_cierre;
    private double tiempo_estimado;
    private int asistentes_estimados;
    private int num_asistentes;
    private double horas_asistente;
    private double costo_curso;
    private String status_curso;
    private String nombres;

    public int getIdHistorialCurso() {
        return idHistorialCurso;
    }

    public void setIdHistorialCurso(int idHistorialCurso) {
        this.idHistorialCurso = idHistorialCurso;
    }

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

    public String getNombre_instructor() {
        return nombre_instructor;
    }

    public void setNombre_instructor(String nombre_instructor) {
        this.nombre_instructor = nombre_instructor;
    }

    public int getArea_idArea() {
        return area_idArea;
    }

    public void setArea_idArea(int area_idArea) {
        this.area_idArea = area_idArea;
    }

    public int getPuesto_idPuesto() {
        return puesto_idPuesto;
    }

    public void setPuesto_idPuesto(int puesto_idPuesto) {
        this.puesto_idPuesto = puesto_idPuesto;
    }

    public int getTurno_idTurno() {
        return turno_idTurno;
    }

    public void setTurno_idTurno(int turno_idTurno) {
        this.turno_idTurno = turno_idTurno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_estimada() {
        return fecha_estimada;
    }

    public void setFecha_estimada(String fecha_estimada) {
        this.fecha_estimada = fecha_estimada;
    }

    public String getFecha_cierre() {
        return fecha_cierre;
    }

    public void setFecha_cierre(String fecha_cierre) {
        this.fecha_cierre = fecha_cierre;
    }

    public double getTiempo_estimado() {
        return tiempo_estimado;
    }

    public void setTiempo_estimado(double tiempo_estimado) {
        this.tiempo_estimado = tiempo_estimado;
    }

    public int getAsistentes_estimados() {
        return asistentes_estimados;
    }

    public void setAsistentes_estimados(int asistentes_estimados) {
        this.asistentes_estimados = asistentes_estimados;
    }

    public int getNum_asistentes() {
        return num_asistentes;
    }

    public void setNum_asistentes(int num_asistentes) {
        this.num_asistentes = num_asistentes;
    }

    public double getHoras_asistente() {
        return horas_asistente;
    }

    public void setHoras_asistente(double horas_asistente) {
        this.horas_asistente = horas_asistente;
    }

    public double getCosto_curso() {
        return costo_curso;
    }

    public void setCosto_curso(double costo_curso) {
        this.costo_curso = costo_curso;
    }

    public String getStatus_curso() {
        return status_curso;
    }

    public void setStatus_curso(String status_curso) {
        this.status_curso = status_curso;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
