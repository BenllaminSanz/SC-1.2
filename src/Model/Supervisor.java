package Model;

public class Supervisor {

    private int id_supervisor;
    private String nombre_supervisor;
    private int propuesto_trabajadores;
    private int area_idarea;
    private String nombre_area;
    private int turno_idturno;
    private String nombre_turno;

    public int getId_supervisor() {
        return id_supervisor;
    }

    public void setId_supervisor(int id_supervisor) {
        this.id_supervisor = id_supervisor;
    }

    public String getNombre_supervisor() {
        return nombre_supervisor;
    }

    public void setNombre_supervisor(String nombre_supervisor) {
        this.nombre_supervisor = nombre_supervisor;
    }

    public int getPropuesto_trabajadores() {
        return propuesto_trabajadores;
    }

    public void setPropuesto_trabajadores(int propuesto_trabajadores) {
        this.propuesto_trabajadores = propuesto_trabajadores;
    }

    public int getArea_idarea() {
        return area_idarea;
    }

    public void setArea_idarea(int area_idarea) {
        this.area_idarea = area_idarea;
    }

    public String getNombre_area() {
        return nombre_area;
    }

    public void setNombre_area(String nombre_area) {
        this.nombre_area = nombre_area;
    }

    public int getTurno_idturno() {
        return turno_idturno;
    }

    public void setTurno_idturno(int turno_idturno) {
        this.turno_idturno = turno_idturno;
    }

    public String getNombre_turno() {
        return nombre_turno;
    }

    public void setNombre_turno(String nombre_turno) {
        this.nombre_turno = nombre_turno;
    }

    
}
