//Modelo de la Tabla Puesto
package Model;

public class Puesto {

    private int idPuesto;
    private String Nombre_Puesto;
    private String Nombre_Puesto_Ingles;
    private String Descripcion;
    private String Nivel;
    private int Centro_de_Costo;
    private int Propuesto_Trabajadores;
    private int Propuesto_Turno;

    public int getPropuesto_Turno() {
        return Propuesto_Turno;
    }

    public void setPropuesto_Turno(int Propuesto_Turno) {
        this.Propuesto_Turno = Propuesto_Turno;
    }

    public int getPropuesto_Turnos() {
        return Propuesto_Turnos;
    }

    public void setPropuesto_Turnos(int Propuesto_Turnos) {
        this.Propuesto_Turnos = Propuesto_Turnos;
    }
    private int Propuesto_Turnos;
    private int Diferencia_Trabajadores;

    public int getTurnoLS() {
        return TurnoLS;
    }

    public void setTurnoLS(int TurnoLS) {
        this.TurnoLS = TurnoLS;
    }
    
    private int Plantilla_Trabajadores;
    private int area_idArea;
    private String area_NombreArea;
    private int TurnoA;
    private int TurnoB;
    private int TurnoC;
    private int TurnoD;
    private int TurnoLV;
    private int TurnoLS;

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getNombre_Puesto() {
        return Nombre_Puesto;
    }

    public void setNombre_Puesto(String Nombre_Puesto) {
        this.Nombre_Puesto = Nombre_Puesto;
    }

    public String getNombre_Puesto_Ingles() {
        return Nombre_Puesto_Ingles;
    }

    public void setNombre_Puesto_Ingles(String Nombre_Puesto_Ingles) {
        this.Nombre_Puesto_Ingles = Nombre_Puesto_Ingles;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    
    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String Nivel) {
        this.Nivel = Nivel;
    }

    public int getCentro_de_Costo() {
        return Centro_de_Costo;
    }

    public void setCentro_de_Costo(int Centro_de_Costo) {
        this.Centro_de_Costo = Centro_de_Costo;
    }

    public int getPropuesto_Trabajadores() {
        return Propuesto_Trabajadores;
    }

    public void setPropuesto_Trabajadores(int Propuesto_Trabajadores) {
        this.Propuesto_Trabajadores = Propuesto_Trabajadores;
    }

    public int getDiferencia_Trabajadores() {
        return Diferencia_Trabajadores;
    }

    public void setDiferencia_Trabajadores(int Diferencia_Trabajadores) {
        this.Diferencia_Trabajadores = Diferencia_Trabajadores;
    }

    public int getPlantilla_Trabajadores() {
        return Plantilla_Trabajadores;
    }

    public void setPlantilla_Trabajadores(int Plantilla_Trabajadores) {
        this.Plantilla_Trabajadores = Plantilla_Trabajadores;
    }

    public int getArea_idArea() {
        return area_idArea;
    }

    public void setArea_idArea(int area_idArea) {
        this.area_idArea = area_idArea;
    }

    public String getArea_NombreArea() {
        return area_NombreArea;
    }

    public void setArea_NombreArea(String area_NombreArea) {
        this.area_NombreArea = area_NombreArea;
    }
    
    public int getTurnoA() {
        return TurnoA;
    }

    public void setTurnoA(int TurnoA) {
        this.TurnoA = TurnoA;
    }

    public int getTurnoB() {
        return TurnoB;
    }

    public void setTurnoB(int TurnoB) {
        this.TurnoB = TurnoB;
    }

    public int getTurnoC() {
        return TurnoC;
    }

    public void setTurnoC(int TurnoC) {
        this.TurnoC = TurnoC;
    }

    public int getTurnoD() {
        return TurnoD;
    }

    public void setTurnoD(int TurnoD) {
        this.TurnoD = TurnoD;
    }

    public int getTurnoLV() {
        return TurnoLV;
    }

    public void setTurnoLV(int TurnoLV) {
        this.TurnoLV = TurnoLV;
    }
}
