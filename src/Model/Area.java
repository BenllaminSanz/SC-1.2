//Modelo de la Tabla Area
package Model;

public class Area {
    private int idArea; 
    private String Nombre_Area; 
    private String Descripcion;
    private int tipo_proceso;

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNombre_Area() {
        return Nombre_Area;
    }

    public void setNombre_Area(String Nombre_Area) {
        this.Nombre_Area = Nombre_Area;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getTipo_proceso() {
        return tipo_proceso;
    }

    public void setTipo_proceso(int tipo_proceso) {
        this.tipo_proceso = tipo_proceso;
    }
}
