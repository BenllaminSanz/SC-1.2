package Querys;

import Model.Puesto;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasPuesto extends Conexion {

    //Funcion de Registro para Area
    public boolean registrar(Puesto tbr) {
        String sqlPuesto = "INSERT INTO puesto (Nombre_Puesto, Nombre_Puesto_Ingles, Descripcion, Nivel, Centro_de_Costo, Propuesto_Trabajadores, area_idArea) VALUES (?,?,?,?,?,?,?)";
        String sqlTurno = "INSERT INTO turno_puesto (turno_idTurno, puesto_idPuesto, propuesto) VALUES (?,?,?)";
        String sqlTecno = "INSERT INTO puesto_tecnologia (idpuesto, idtecnologia) VALUES (?,?)";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement ps = con.prepareStatement(sqlPuesto, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, tbr.getNombre_Puesto());
                ps.setString(2, tbr.getNombre_Puesto_Ingles());
                ps.setString(3, tbr.getDescripcion());
                ps.setString(4, tbr.getNivel());
                ps.setInt(5, tbr.getCentro_de_Costo());
                ps.setInt(6, tbr.getPropuesto_Trabajadores());
                ps.setInt(7, tbr.getArea_idArea());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                int idPuesto = 0;
                if (rs.next()) {
                    idPuesto = rs.getInt(1);
                }

                try (PreparedStatement ps2 = con.prepareStatement(sqlTurno)) {
                    String[] sufijosTurno = {"A", "B", "C", "D", "LV", "LS"};
                    for (int i = 0; i < sufijosTurno.length; i++) {
                        String metodoNombre = "getTurno" + sufijosTurno[i];
                        Method metodo = tbr.getClass().getMethod(metodoNombre);
                        int valorPropuesto = (Integer) metodo.invoke(tbr);

                        ps2.setInt(1, i + 1);
                        ps2.setInt(2, idPuesto);
                        ps2.setInt(3, valorPropuesto);
                        ps2.addBatch();
                    }
                    ps2.executeBatch();
                }

                try (PreparedStatement ps3 = con.prepareStatement(sqlTecno)) {
                    // 1 = OE, 2 = RS, 3 = POLYCOTTON
                    String[] sufijosTecno = {"OE", "RS", "POLY"};
                    for (int i = 0; i < sufijosTecno.length; i++) {
                        String metodoNombre = "getTecno" + sufijosTecno[i];
                        Method metodo = tbr.getClass().getMethod(metodoNombre);
                        int valorPropuesto = (Integer) metodo.invoke(tbr);

                        // Solo insertar si el valor es 1 (checkbox seleccionado)
                        if (valorPropuesto == 1) {
                            ps3.setInt(1, idPuesto);   // idpuesto
                            ps3.setInt(2, i + 1);      // idtecnologia (1=OE, 2=RS, 3=POLY)
                            ps3.addBatch();
                        }
                    }
                    ps3.executeBatch();
                }
            }

            con.commit(); // confirmar transacción
            return true;

        } catch (Exception ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    //Funcion de Moficar para Trabajadores
    public boolean modificar(Puesto tbr, int folio) {
        String sqlPuesto = "UPDATE puesto SET "
                + "Nombre_Puesto = ?, "
                + "Nombre_Puesto_Ingles = ?, "
                + "Descripcion = ?, "
                + "Nivel = ?, "
                + "Centro_de_Costo = ?, "
                + "Propuesto_Trabajadores = ?, "
                + "area_idArea = ? "
                + "WHERE idPuesto = ?";

        String sqlTurno = "INSERT INTO turno_puesto (turno_idTurno, puesto_idPuesto, propuesto) "
                + "VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE propuesto = VALUES(propuesto)";

        String sqlTecno = "INSERT INTO puesto_tecnologia (idPuesto, idTecnologia) "
                + "VALUES (?, ?) "
                + "ON DUPLICATE KEY UPDATE idTecnologia = VALUES(idTecnologia)";

        String sqlDeleteTecno = "DELETE FROM puesto_tecnologia WHERE idPuesto = ?";

        try (Connection con = getConnection()) {
            con.setAutoCommit(false);

            // --- Actualizar datos del puesto ---
            try (PreparedStatement ps = con.prepareStatement(sqlPuesto)) {
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
                    con.rollback();
                    return false; // no se modificó nada
                }
            }

            // --- Insertar o actualizar turnos ---
            try (PreparedStatement ps = con.prepareStatement(sqlTurno)) {
                String[] sufijosTurno = {"A", "B", "C", "D", "LV", "LS"};
                for (int i = 0; i < sufijosTurno.length; i++) {
                    String metodoNombre = "getTurno" + sufijosTurno[i];
                    Method metodo = tbr.getClass().getMethod(metodoNombre);
                    int valorPropuesto = (Integer) metodo.invoke(tbr);

                    ps.setInt(1, i + 1);   // turno_idTurno
                    ps.setInt(2, folio);   // puesto_idPuesto
                    ps.setInt(3, valorPropuesto);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            try (PreparedStatement psDel = con.prepareStatement(sqlDeleteTecno)) {
                psDel.setInt(1, folio);
                psDel.execute();
            }

            try (PreparedStatement ps3 = con.prepareStatement(sqlTecno)) {
                String[] sufijosTecno = {"OE", "RS", "POLY"};
                for (int i = 0; i < sufijosTecno.length; i++) {
                    String metodoNombre = "getTecno" + sufijosTecno[i];
                    Method metodo = tbr.getClass().getMethod(metodoNombre);
                    int valorTecno = (Integer) metodo.invoke(tbr);
                    System.out.println(valorTecno);
                    // Si el valor es 1 (seleccionado), lo guardamos
                    if (valorTecno == 1) {
                        ps3.setInt(1, folio);   // puesto_idPuesto
                        ps3.setInt(2, i + 1);   // tecnologia_idTecnologia
                        ps3.addBatch();
                    }
                }
                ps3.executeBatch();
            }

            con.commit();
            return true;

        } catch (Exception ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
                        Logger.getLogger("Turno no reconocido: " + turnoId);
                        continue; // o manejar el error como desees
                    }

                    String metodoNombre = "setTurno" + sufijo;

                    Method metodo = tbr.getClass().getMethod(metodoNombre, int.class);
                    metodo.invoke(tbr, propuesto);
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, "Error en la consulta de turnos", ex);
            return false;
        }

        // --- Consultar tecnologías asociadas ---
        Map<Integer, String> sufijosTecno = new HashMap<>();
        sufijosTecno.put(1, "OE");
        sufijosTecno.put(2, "RS");
        sufijosTecno.put(3, "POLY");

        String sqlTecno = "SELECT idtecnologia FROM puesto_tecnologia WHERE idpuesto = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sqlTecno)) {
            ps.setInt(1, tbr.getIdPuesto());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idTecno = rs.getInt("idtecnologia");
                    String sufijo = sufijosTecno.get(idTecno);

                    if (sufijo == null) {
                        Logger.getLogger("Tecnología no reconocida: " + idTecno);
                        continue;
                    }

                    // Llamar al setter correspondiente: setTecnoOE, setTecnoRS o setTecnoPOLY
                    String metodoNombre = "setTecno" + sufijo;
                    try {
                        Method metodo = tbr.getClass().getMethod(metodoNombre, int.class);
                        metodo.invoke(tbr, 1); // 1 porque está activo en la tabla
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException ex) {
                        Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasPuesto.class.getName()).log(Level.SEVERE, "Error en la consulta de tecnologías", ex);
        }

        return true;
    }
}
