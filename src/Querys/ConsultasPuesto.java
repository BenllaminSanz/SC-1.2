package Querys;

import Model.Puesto;
import Functions.QueryFunctions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasPuesto extends Conexion {

    //Funcion de Registro para Area
    public boolean registrar(Puesto tbr) {

        String sql = "INSERT INTO `sistema_capacitacion`.`puesto`\n"
                + "(`Nombre_Puesto`,\n"
                + "`Nombre_Puesto_Ingles`,\n"
                + "`Descripcion`,\n"
                + "`Nivel`,\n"
                + "`Centro_de_Costo`,\n"
                + "`Propuesto_Trabajadores`,\n"
                + "`area_idArea`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, tbr.getNombre_Puesto());
            ps.setString(2, tbr.getNombre_Puesto_Ingles());
            ps.setString(3, tbr.getDescripcion());
            ps.setString(4, tbr.getNivel());
            ps.setInt(5, tbr.getCentro_de_Costo());
            ps.setInt(6, tbr.getPropuesto_Trabajadores());
            ps.setInt(7, tbr.getArea_idArea());
            ps.execute();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql1 = "INSERT INTO sistema_capacitacion.turno_puesto (turno_idTurno, puesto_idPuesto, propuesto) VALUES (?,?,?)";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql1)) {

            int idPuesto = Integer.parseInt(QueryFunctions.CapturaCondicional("puesto", "idPuesto", "Nombre_Puesto", tbr.getNombre_Puesto()));

            String[] sufijosTurno = {"A", "B", "C", "D", "LV", "LS"};

            for (int i = 0; i < sufijosTurno.length; i++) {
                String metodoNombre = "getTurno" + sufijosTurno[i];
                Method metodo = tbr.getClass().getMethod(metodoNombre);
                int valorPropuesto = (Integer) metodo.invoke(tbr);

                ps.setInt(1, i + 1); // suponiendo idTurno es 1 para A, 2 para B, etc.
                ps.setInt(2, idPuesto);
                ps.setInt(3, valorPropuesto);
                ps.addBatch();
            }
            ps.executeBatch();
            return true;

        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificar(Puesto tbr, int folio) {

        // Actualización del puesto
        String sql = "UPDATE `sistema_capacitacion`.`puesto` SET "
                + "`Nombre_Puesto` = ?, "
                + "`Nombre_Puesto_Ingles` = ?, "
                + "`Descripcion` = ?, "
                + "`Nivel` = ?, "
                + "`Centro_de_Costo` = ?, "
                + "`Propuesto_Trabajadores` = ?, "
                + "`area_idArea` = ? "
                + "WHERE `idPuesto` = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tbr.getNombre_Puesto());
            ps.setString(2, tbr.getNombre_Puesto_Ingles());
            ps.setString(3, tbr.getDescripcion());
            ps.setString(4, tbr.getNivel());
            ps.setInt(5, tbr.getCentro_de_Costo());
            ps.setInt(6, tbr.getPropuesto_Trabajadores());
            ps.setInt(7, tbr.getArea_idArea());
            ps.setInt(8, folio);

            int filas = ps.executeUpdate();
            if (filas == 0) {
                return false; // No se modificó nada
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        // Actualización de turnos del puesto
        String sql1 = "UPDATE sistema_capacitacion.turno_puesto SET propuesto = ? WHERE turno_idTurno = ? AND puesto_idPuesto = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql1)) {

            int idPuesto = folio; // Usamos directamente el folio

            String[] sufijosTurno = {"A", "B", "C", "D", "LV", "LS"};

            for (int i = 0; i < sufijosTurno.length; i++) {
                try {
                    String metodoNombre = "getTurno" + sufijosTurno[i];
                    Method metodo = tbr.getClass().getMethod(metodoNombre);
                    int valorPropuesto = (Integer) metodo.invoke(tbr);

                    ps.setInt(1, valorPropuesto);
                    ps.setInt(2, i + 1); // turno_idTurno
                    ps.setInt(3, idPuesto);
                    ps.addBatch();
                } catch (NoSuchMethodException e) {
                    System.err.println("Método no encontrado: getTurno" + sufijosTurno[i]);
                }
            }

            ps.executeBatch();
            return true;

        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //Funcion de Eliminar para Trabajadores
    public boolean eliminar(Puesto tbr) {
        String sql = "DELETE FROM `puesto`\n"
                + "WHERE `idPuesto` = ?;";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, tbr.getIdPuesto());
            ps.execute();
            return true;

        } catch (SQLException e) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, e);

        }
        return false;
    }

    //Funcion de Registro para Trabajadores
    public boolean buscar(Puesto tbr) {
        String sql = "SELECT * FROM puesto WHERE idPuesto = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tbr.getIdPuesto());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tbr.setIdPuesto(rs.getInt("idPuesto"));
                    tbr.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                    tbr.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                    tbr.setDescripcion(rs.getString("Descripcion"));
                    tbr.setNivel(rs.getString("Nivel"));
                    tbr.setCentro_de_Costo(rs.getInt("Centro_de_Costo"));
                    tbr.setPropuesto_Trabajadores(rs.getInt("Propuesto_Trabajadores"));
                    tbr.setArea_idArea(rs.getInt("area_idArea"));
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, "Error en la consulta de puesto", e);
            return false;
        }

        // Mapa de sufijos de métodos por ID de turno
        Map<Integer, String> sufijosTurno = new HashMap<>();
        sufijosTurno.put(1, "A");
        sufijosTurno.put(2, "B");
        sufijosTurno.put(3, "C");
        sufijosTurno.put(4, "D");
        sufijosTurno.put(5, "LV");
        sufijosTurno.put(6, "LS");

        String sql1 = "SELECT tp.turno_idTurno, tp.propuesto "
                + "FROM turno_puesto tp "
                + "JOIN puesto p ON tp.puesto_idPuesto = p.idPuesto "
                + "WHERE p.idPuesto = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql1)) {
            ps.setInt(1, tbr.getIdPuesto());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int turnoId = rs.getInt("turno_idTurno");
                    int propuesto = rs.getInt("propuesto");

                    String sufijo = sufijosTurno.get(turnoId);
                    if (sufijo == null) {
                        System.err.println("Turno no reconocido: " + turnoId);
                        continue; // o manejar el error como desees
                    }

                    String metodoNombre = "setTurno" + sufijo;
                    System.out.println("Invocando método: " + metodoNombre + " con valor: " + propuesto);

                    try {
                        Method metodo = tbr.getClass().getMethod(metodoNombre, int.class);
                        metodo.invoke(tbr, propuesto);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, "Error al invocar método dinámico", ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, "Error en la consulta de turnos", ex);
            return false;
        }

        return true;
    }
}
