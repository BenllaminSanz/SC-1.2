package Model;

public class Administrativos {

    private String Folio_Trabajador;
    private String Nombre_Trabajador;
    private String CURP_Trabajador;
    private String RFC_Trabajador;
    private String IMSS_Trabajador;
    private String Fecha_Antiguedad;
    private String cia;
    private String area;
    private String puesto;
    private String turno;
    private boolean Brigadista;
    private int Brigada;

    public String getFolio_Trabajador() {
        return Folio_Trabajador;
    }

    public void setFolio_Trabajador(String Folio_Trabajador) {
        this.Folio_Trabajador = Folio_Trabajador;
    }

    public String getNombre_Trabajador() {
        return Nombre_Trabajador;
    }

    public void setNombre_Trabajador(String Nombre_Trabajador) {
        this.Nombre_Trabajador = Nombre_Trabajador;
    }

    public String getCURP_Trabajador() {
        return CURP_Trabajador;
    }

    public void setCURP_Trabajador(String CURP_Trabajador) {
        this.CURP_Trabajador = CURP_Trabajador;
    }

    public String getRFC_Trabajador() {
        return RFC_Trabajador;
    }

    public void setRFC_Trabajador(String RFC_Trabajador) {
        this.RFC_Trabajador = RFC_Trabajador;
    }

    public String getIMSS_Trabajador() {
        return IMSS_Trabajador;
    }

    public void setIMSS_Trabajador(String IMSS_Trabajador) {
        this.IMSS_Trabajador = IMSS_Trabajador;
    }

    public String getFecha_Antiguedad() {
        return Fecha_Antiguedad;
    }

    public void setFecha_Antiguedad(String Fecha_Antiguedad) {
        this.Fecha_Antiguedad = Fecha_Antiguedad;
    }

    public String getCia() {
        return cia;
    }

    public void setCia(String cia) {
        this.cia = cia;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public boolean isBrigadista() {
        return Brigadista;
    }

    public void setBrigadista(boolean Brigadista) {
        this.Brigadista = Brigadista;
    }

    public int getBrigada() {
        return Brigada;
    }

    public void setBrigada(int Brigada) {
        this.Brigada = Brigada;
    }
    
    
}
