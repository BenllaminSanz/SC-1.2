package Querys;

import Functions.DateTools;
import Functions.QueryFunctions;
import Model.AsistenteCurso;
import Model.HistorialCurso;
import Subviews.IFrmEditarHistorialCurso;
import java.awt.Component;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

public class ConsultasHistorialCurso extends Conexion {

    public ConsultasAsistentesCurso modC = new ConsultasAsistentesCurso();

    public boolean buscar(HistorialCurso mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM `sistema_capacitacion`.`view_historialcursos` WHERE idHistorial =?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHistorialCurso());
            rs = ps.executeQuery();

            if (rs.next()) {
                mod.setNombre_Curso(rs.getString("nombre_curso"));
                mod.setNombre_instructor(rs.getString("nombre_instructor"));
                mod.setFecha_inicio(DateTools.MySQLtoString(rs.getDate("fecha_inicio")));
                mod.setFecha_estimada(DateTools.MySQLtoString(rs.getDate("fecha_estimada")));
                mod.setFecha_cierre(DateTools.MySQLtoString(rs.getDate("fecha_cierre")));
                mod.setTiempo_estimado(rs.getDouble("duracion_curso"));
                mod.setAsistentes_estimados(rs.getInt("asistentes_esperados"));
                mod.setCosto_curso(rs.getDouble("costo_curso"));
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error en Query buscar: " + e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean agregar(HistorialCurso mod, IFrmEditarHistorialCurso frm) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`historial_curso`\n"
                + "(`idCurso`,\n"
                + "`fecha_inicio`,\n"
                + "`fecha_estimada`,\n"
                + "`duracion_curso`,\n"
                + "`asistentes_esperados`,"
                + "`nombre_instructor`,"
                + "`costo_curso`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";

        try {
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // Agrega el parámetro Statement.RETURN_GENERATED_KEYS
            ps.setInt(1, mod.getIdCurso());
            ps.setString(2, DateTools.StringtoMySQL(mod.getFecha_inicio()));
            ps.setString(3, DateTools.StringtoMySQL(mod.getFecha_estimada()));
            ps.setDouble(4, mod.getTiempo_estimado());
            ps.setInt(5, mod.getAsistentes_estimados());
            ps.setString(6, mod.getNombre_instructor());
            ps.setDouble(7, mod.getCosto_curso());
            ps.execute();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int idhistorial_curso = 0;
            if (generatedKeys.next()) {
                idhistorial_curso = generatedKeys.getInt(1);
            }

            Component[] componentsArea = frm.jPanel2.getComponents();
            for (Component component : componentsArea) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        String sqlPuestos = "INSERT INTO `sistema_capacitacion`.`historial_curso_area`\n"
                                + "(`idhistorial_curso`,\n"
                                + "`idArea`)\n"
                                + "VALUES\n"
                                + "(?,?);";
                        PreparedStatement ps1 = con.prepareStatement(sqlPuestos);
                        ps1.setInt(1, idhistorial_curso); // Utiliza el valor generado de idhistorial_curso
                        ps1.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple("area", "idArea",
                                        "nombre_Area", checkBox.getText())));
                        ps1.executeUpdate();
                    }
                }
            }

            Component[] componentsPuesto = frm.jPanel3.getComponents();
            for (Component component : componentsPuesto) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        String sqlPuestos = "INSERT INTO `sistema_capacitacion`.`historial_curso_puesto`\n"
                                + "(`idhistorial_curso`,\n"
                                + "`idPuesto`)\n"
                                + "VALUES\n"
                                + "(?,?);";
                        PreparedStatement ps1 = con.prepareStatement(sqlPuestos);
                        ps1.setInt(1, idhistorial_curso); // Utiliza el valor generado de idhistorial_curso
                        ps1.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple("puesto", "idPuesto",
                                        "nombre_Puesto", checkBox.getText())));
                        ps1.executeUpdate();
                    }
                }
            }

            Component[] componentsTurno = frm.jPanel4.getComponents();
            for (Component component : componentsTurno) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        String sqlPuestos = "INSERT INTO `sistema_capacitacion`.`historial_curso_turno`\n"
                                + "(`idhistorial_curso`,\n"
                                + "`idTurno`)\n"
                                + "VALUES\n"
                                + "(?,?);";
                        PreparedStatement ps1 = con.prepareStatement(sqlPuestos);
                        ps1.setInt(1, idhistorial_curso); // Utiliza el valor generado de idhistorial_curso
                        ps1.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple("turno", "idTurno",
                                        "nombre_Turno", checkBox.getText())));
                        ps1.executeUpdate();
                    }
                }
            }

            agregarGrupos(mod, idhistorial_curso);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean actualizar(HistorialCurso mod, IFrmEditarHistorialCurso frm) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`historial_curso`\n"
                + "SET\n"
                + "`idCurso` = ?,\n"
                + "`fecha_inicio` = ?,\n"
                + "`fecha_estimada` = ?,\n"
                + "`fecha_cierre` = ?,\n"
                + "`duracion_curso` = ?,\n"
                + "`asistentes_esperados` = ?,"
                + "`nombre_instructor` = ?,"
                + "`costo_curso` = ?\n"
                + "WHERE `idHistorial_Curso` = ?;";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCurso());
            ps.setString(2, DateTools.StringtoMySQL(mod.getFecha_inicio()));
            ps.setString(3, DateTools.StringtoMySQL(mod.getFecha_estimada()));
            ps.setString(4, DateTools.StringtoMySQL(mod.getFecha_cierre()));
            ps.setDouble(5, mod.getTiempo_estimado());
            ps.setInt(6, mod.getAsistentes_estimados());
            ps.setString(7, mod.getNombre_instructor());
            ps.setDouble(8, mod.getCosto_curso());
            ps.setInt(9, mod.getIdHistorialCurso());
            ps.execute();

            PreparedStatement psArea = null;
            String sqlArea = "DELETE FROM `sistema_capacitacion`.`historial_curso_area`\n"
                    + "WHERE idHistorial_Curso = ?;";
            psArea = con.prepareStatement(sqlArea);
            psArea.setInt(1, mod.getIdHistorialCurso());
            psArea.execute();

            PreparedStatement psPuesto = null;
            String sqlPuesto = "DELETE FROM `sistema_capacitacion`.`historial_curso_puesto`\n"
                    + "WHERE idHistorial_Curso = ?;";
            psPuesto = con.prepareStatement(sqlPuesto);
            psPuesto.setInt(1, mod.getIdHistorialCurso());
            psPuesto.execute();

            PreparedStatement psTurno = null;
            String sqlTurno = "DELETE FROM `sistema_capacitacion`.`historial_curso_turno`\n"
                    + "WHERE idHistorial_Curso = ?;";
            psTurno = con.prepareStatement(sqlTurno);
            psTurno.setInt(1, mod.getIdHistorialCurso());
            psTurno.execute();

            Component[] componentsArea = frm.jPanel2.getComponents();
            for (Component component : componentsArea) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        PreparedStatement psAsistentes = null;
                        String sqlAsistentes = "DELETE FROM `sistema_capacitacion`.`asistentes_curso`\n"
                                + "WHERE idHistorial_Curso = ?";
                        psAsistentes = con.prepareStatement(sqlAsistentes);
                        psAsistentes.setInt(1, mod.getIdHistorialCurso());
                        psAsistentes.execute();

                        String sqlAreas = "INSERT INTO `sistema_capacitacion`.`historial_curso_area`\n"
                                + "(`idhistorial_curso`,\n"
                                + "`idArea`)\n"
                                + "VALUES\n"
                                + "(?,?);";
                        PreparedStatement ps1 = con.prepareStatement(sqlAreas);
                        ps1.setInt(1, mod.getIdHistorialCurso()); // Utiliza el valor generado de idhistorial_curso
                        ps1.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple("area", "idArea",
                                        "nombre_Area", checkBox.getText())));
                        ps1.executeUpdate();
                    }
                }
            }

            Component[] componentsPuesto = frm.jPanel3.getComponents();
            for (Component component : componentsPuesto) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        PreparedStatement psAsistentes = null;
                        String sqlAsistentes = "DELETE FROM `sistema_capacitacion`.`asistentes_curso`\n"
                                + "WHERE idHistorial_Curso = ?";
                        psAsistentes = con.prepareStatement(sqlAsistentes);
                        psAsistentes.setInt(1, mod.getIdHistorialCurso());
                        psAsistentes.execute();

                        String sqlPuestos = "INSERT INTO `sistema_capacitacion`.`historial_curso_puesto`\n"
                                + "(`idhistorial_curso`,\n"
                                + "`idPuesto`)\n"
                                + "VALUES\n"
                                + "(?,?);";
                        PreparedStatement ps1 = con.prepareStatement(sqlPuestos);
                        ps1.setInt(1, mod.getIdHistorialCurso()); // Utiliza el valor generado de idhistorial_curso
                        ps1.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple("puesto", "idPuesto",
                                        "nombre_Puesto", checkBox.getText())));
                        ps1.executeUpdate();
                    }
                }
            }

            Component[] componentsTurno = frm.jPanel4.getComponents();
            for (Component component : componentsTurno) {
                if (component instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) component;
                    if (checkBox.isSelected()) {
                        PreparedStatement psAsistentes = null;
                        String sqlAsistentes = "DELETE FROM `sistema_capacitacion`.`asistentes_curso`\n"
                                + "WHERE idHistorial_Curso = ?";
                        psAsistentes = con.prepareStatement(sqlAsistentes);
                        psAsistentes.setInt(1, mod.getIdHistorialCurso());
                        psAsistentes.execute();

                        String sqlTurnos = "INSERT INTO `sistema_capacitacion`.`historial_curso_turno`\n"
                                + "(`idhistorial_curso`,\n"
                                + "`idTurno`)\n"
                                + "VALUES\n"
                                + "(?,?);";
                        PreparedStatement ps1 = con.prepareStatement(sqlTurnos);
                        ps1.setInt(1, mod.getIdHistorialCurso()); // Utiliza el valor generado de idhistorial_curso
                        ps1.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple("turno", "idTurno",
                                        "nombre_Turno", checkBox.getText())));
                        ps1.executeUpdate();
                    }
                }
            }
            agregarGrupos(mod, mod.getIdHistorialCurso());
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean eliminar(String idHistorial) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`historial_curso`\n"
                + "WHERE idHistorial_Curso = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, idHistorial);
            ps.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en Query registro: " + e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean concluir(HistorialCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`historial_curso`\n"
                + "SET\n"
                + "`fecha_cierre` = ?,\n"
                + "`estado_curso` = ?\n"
                + "WHERE `idHistorial_Curso` = ?;";

        String sql1 = "SET @estado_curso:=?;";
        String sql2 = "SET @id_historial_curso:=?;";
        String sql3 = "SET @id_tipo_curso:=?;";
        String call = "{CALL `sistema_capacitacion`.`concluir_curso`()}";

        // Consulta asistentes aprobados
        String sqlAprobados = "SELECT nombre_asistente "
                + "FROM asistentes_curso "
                + "WHERE idHistorial_curso = ? "
                + "AND (status_entrenamiento = 'En Proceso de Certificación' "
                + "     OR status_entrenamiento = 'Concluido')";

        // Consulta asistentes que no aprobaron
        String sqlNoAprobados = "SELECT nombre_asistente "
                + "FROM asistentes_curso "
                + "WHERE idHistorial_curso = ? "
                + "AND status_entrenamiento = 'En Entrenamiento'";

        try {
            // 1. Actualizar estado en historial_curso
            ps = con.prepareStatement(sql);
            ps.setString(1, DateTools.StringtoMySQL(mod.getFecha_cierre()));
            ps.setString(2, "Concluido");
            ps.setInt(3, mod.getIdHistorialCurso());
            ps.execute();

            // 2. Set variables de sesión
            ps = con.prepareStatement(sql1);
            ps.setString(1, "Concluido");
            ps.execute();

            ps = con.prepareStatement(sql2);
            ps.setInt(1, mod.getIdHistorialCurso());
            ps.execute();

            ps = con.prepareStatement(sql3);
            ps.setInt(1, mod.getIdTipo_Curso());
            ps.execute();

            PreparedStatement psql = con.prepareStatement(
                    "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));"
            );
            psql.execute();

            // 3. Llamar al procedure
            CallableStatement cs = con.prepareCall(call);
            cs.execute();

            // 4. Consultar asistentes
            ps = con.prepareStatement(sqlAprobados);
            ps.setInt(1, mod.getIdHistorialCurso());
            ResultSet rs = ps.executeQuery();
            StringBuilder aprobados = new StringBuilder("Asistentes que cumplieron los requerimientos:\n");
            boolean hayAprobados = false;
            while (rs.next()) {
                hayAprobados = true;
                String nombre = rs.getString("nombre_asistente");
                aprobados.append(" - ").append(nombre).append("\n");
            }
            if (hayAprobados) {
                JOptionPane.showMessageDialog(null, aprobados.toString());
            }

            ps = con.prepareStatement(sqlNoAprobados);
            ps.setInt(1, mod.getIdHistorialCurso());
            rs = ps.executeQuery();
            StringBuilder noAprobados = new StringBuilder("Asistentes que NO alcanzaron los requerimientos o ILUO < 90%:\n");
            boolean hayNoAprobados = false;
            while (rs.next()) {
                hayNoAprobados = true;
                String nombre = rs.getString("nombre_asistente");
                noAprobados.append(" - ").append(nombre).append("\n");
            }

            if (hayNoAprobados) {
                JOptionPane.showMessageDialog(null, noAprobados.toString());
            } else if (hayNoAprobados && !hayAprobados) {
                JOptionPane.showMessageDialog(null, "Ningún asistente cumplió con todos los requerimientos.");
            }

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean restaurar(HistorialCurso mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`historial_curso`\n"
                + "SET\n"
                + "`fecha_cierre` = ?,\n"
                + "`estado_curso` = ?\n"
                + "WHERE `idHistorial_Curso` = ?;";

        String sql1 = "SET @estado_curso:=?;";
        String sql2 = "SET @id_historial_curso:=?;";
        String call = "{CALL `sistema_capacitacion`.`concluir_curso`()}";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, null);
            ps.setString(2, "Activo");
            ps.setInt(3, mod.getIdHistorialCurso());
            ps.execute();

            ps = con.prepareStatement(sql1);
            ps.setString(1, "Activo");
            ps.execute();

            ps = con.prepareStatement(sql2);
            ps.setInt(1, mod.getIdHistorialCurso());
            ps.execute();

            CallableStatement cs = con.prepareCall(call);
            cs.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public void buscarAPT(IFrmEditarHistorialCurso frm, HistorialCurso mod) {
        List<String> areasSeleccionadas = obtenerAreasSeleccionadas(mod);
        Component[] componentsArea = frm.jPanel2.getComponents();
        for (Component component : componentsArea) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                String nombreArea = checkBox.getText();
                if (areasSeleccionadas.contains(nombreArea)) {
                    checkBox.setSelected(true);
                }
            }
        }

        List<String> puestosSeleccionados = obtenerPuestosSeleccionados(mod);
        Component[] componentsPuesto = frm.jPanel3.getComponents();
        for (Component component : componentsPuesto) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                String nombreArea = checkBox.getText();
                if (puestosSeleccionados.contains(nombreArea)) {
                    checkBox.setSelected(true);
                }
            }
        }

        List<String> turnosSeleccionados = obtenerTurnosSeleccionados(mod);
        Component[] componentsTurnos = frm.jPanel4.getComponents();
        for (Component component : componentsTurnos) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                String nombreArea = checkBox.getText();
                if (turnosSeleccionados.contains(nombreArea)) {
                    checkBox.setSelected(true);
                }
            }
        }
    }

    public boolean agregarGrupos(HistorialCurso mod, int idhistorial_curso) {
        mod.setIdHistorialCurso(idhistorial_curso);
        List<String> areasSeleccionadas = obtenerAreasSeleccionadas(mod);
        List<String> puestosSeleccionados = obtenerPuestosSeleccionados(mod);
        List<String> turnosSeleccionadas = obtenerTurnosSeleccionados(mod);
        List<AsistenteCurso> asistentes = new ArrayList<>();

        if (!areasSeleccionadas.isEmpty() && !turnosSeleccionadas.isEmpty()) {
            for (String area : areasSeleccionadas) {
                System.out.println(area);
                for (String turno : turnosSeleccionadas) {
                    System.out.println(turno);
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    Connection con = getConnection();

                    String sql = "SELECT * FROM trabajador "
                            + "WHERE puesto_area_idArea = ? AND turno_idTurno=?;";

                    try {
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple(
                                        "area", "idArea", "nombre_Area", area)));
                        ps.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple(
                                        "turno", "idTurno", "nombre_Turno", turno)));
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            AsistenteCurso tbr = new AsistenteCurso();
                            tbr.setId(rs.getString("Folio_Trabajador"));
                            tbr.setNombre(rs.getString("Nombre_Trabajador"));
                            tbr.setPuesto("Trabajador");
                            asistentes.add(tbr);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            modC.registrarTrabajador(String.valueOf(mod.getIdCurso()),
                    String.valueOf(mod.getIdHistorialCurso()), asistentes);
            return true;
        }

        if (areasSeleccionadas.isEmpty() && !puestosSeleccionados.isEmpty() && !turnosSeleccionadas.isEmpty()) {
            for (String puesto : puestosSeleccionados) {
                for (String turno : turnosSeleccionadas) {
                    PreparedStatement ps = null;
                    ResultSet rs = null;
                    Connection con = getConnection();

                    String sql = "SELECT * FROM trabajador "
                            + "WHERE puesto_idPuesto = ? AND turno_idTurno=?;";

                    try {
                        ps = con.prepareStatement(sql);
                        ps.setInt(1, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple(
                                        "puesto", "idPuesto", "nombre_Puesto", puesto)));
                        ps.setInt(2, Integer.parseInt(
                                QueryFunctions.CapturaCondicionalSimple(
                                        "turno", "idTurno", "nombre_Turno", turno)));
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            AsistenteCurso tbr = new AsistenteCurso();
                            tbr.setId(rs.getString("Folio_Trabajador"));
                            tbr.setNombre(rs.getString("Nombre_Trabajador"));
                            tbr.setPuesto("Trabajador");
                            asistentes.add(tbr);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            modC.registrarTrabajador(String.valueOf(mod.getIdCurso()),
                    String.valueOf(mod.getIdHistorialCurso()), asistentes);
            return true;
        }
        return false;
    }

    private List<String> obtenerAreasSeleccionadas(HistorialCurso mod) {
        List<String> seleccionados = new ArrayList<>();
        String sql = "SELECT * FROM historial_curso_area hc\n"
                + "JOIN area a ON hc.idArea=a.idArea\n"
                + "WHERE idHistorial_Curso = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHistorialCurso());
            rs = ps.executeQuery();
            while (rs.next()) {
                seleccionados.add(rs.getString("Nombre_Area"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seleccionados;
    }

    private List<String> obtenerPuestosSeleccionados(HistorialCurso mod) {
        List<String> seleccionados = new ArrayList<>();
        String sql = "SELECT * FROM historial_curso_puesto hc\n"
                + "JOIN puesto p ON hc.idPuesto=p.idPuesto\n"
                + "WHERE idHistorial_Curso = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHistorialCurso());
            rs = ps.executeQuery();
            while (rs.next()) {
                seleccionados.add(rs.getString("Nombre_Puesto"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seleccionados;
    }

    private List<String> obtenerTurnosSeleccionados(HistorialCurso mod) {
        List<String> seleccionados = new ArrayList<>();
        String sql = "SELECT * FROM historial_curso_turno hc\n"
                + "JOIN turno t ON hc.idTurno=t.idTurno\n"
                + "WHERE idHistorial_Curso = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdHistorialCurso());
            rs = ps.executeQuery();
            while (rs.next()) {
                seleccionados.add(rs.getString("Nombre_Turno"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConsultasHistorialCurso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return seleccionados;
    }
}
