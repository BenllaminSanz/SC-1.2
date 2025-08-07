package Functions;

// Archivo auxiliar: ConsultaHelper.java

public class ConsultaHelper {
    public static String obtenerNombreTrabajador(int idAsistente) {
        return QueryFunctions.CapturaCondicionalSimple("trabajador", "nombre_Trabajador",
                "Folio_Trabajador", String.valueOf(idAsistente));
    }

    public static String obtenerCurso(int idHistorial) {
        return QueryFunctions.CapturaCondicionalAnidado("historial_curso",
                "curso", "idCurso", "idCurso", "nombre_Curso", "idHistorial_Curso",
                String.valueOf(idHistorial));
    }

    public static String obtenerFechaCurso(int idHistorial) {
        String fechaSQL = QueryFunctions.CapturaCondicionalSimple("historial_Curso", "fecha_inicio",
                "idHistorial_Curso", String.valueOf(idHistorial));
        return DateTools.DateLocaltoString(fechaSQL);
    }
}
