package Model;

import java.util.Date;

public class HabilidadEvaluada {
        private int idHabilidad;
    private String nombreHabilidad;
    private int ordenHabilidad;
    private String nivelAlcanzado; // I, L, U, O o null si no evaluado
    private Date fechaEvaluacion;
    private String observaciones;

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public void setIdHabilidad(int idHabilidad) {
        this.idHabilidad = idHabilidad;
    }

    public String getNombreHabilidad() {
        return nombreHabilidad;
    }

    public void setNombreHabilidad(String nombreHabilidad) {
        this.nombreHabilidad = nombreHabilidad;
    }

    public int getOrdenHabilidad() {
        return ordenHabilidad;
    }

    public void setOrdenHabilidad(int ordenHabilidad) {
        this.ordenHabilidad = ordenHabilidad;
    }

    public String getNivelAlcanzado() {
        return nivelAlcanzado;
    }

    public void setNivelAlcanzado(String nivelAlcanzado) {
        this.nivelAlcanzado = nivelAlcanzado;
    }

    public Date getFechaEvaluacion() {
        return fechaEvaluacion;
    }

    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
