package Querys;

import Functions.QueryFunctions;
import Model.Puesto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasCertificadoPuesto extends Conexion {

    public boolean consultar(String idCertificado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT count(*) FROM certificado WHERE idCertificado = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idCertificado));
            rs = ps.executeQuery();

            if (rs.next()) {
                int puestos = rs.getInt("count(*)");
                return puestos > 0;
            }
            return false;
        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public List<Puesto> buscar(String idCertificado) {
        List<Puesto> lbu = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM certificado_puesto WHERE certificado_idCertificado = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(idCertificado));
            rs = ps.executeQuery();

            if (rs.next()) {
                Puesto puesto = new Puesto();
                puesto.setIdPuesto(rs.getInt("puesto_idPuesto"));
                puesto.setNombre_Puesto(QueryFunctions.CapturaCondicionalSimple(
                        "puesto", "nombre_puesto", "idPuesto",
                        String.valueOf(rs.getInt("puesto_idPuesto"))));
                lbu.add(puesto);
            }
        } catch (SQLException e) {
            Logger.getLogger(ConsultasBaja.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return lbu;
    }

    public List<Puesto> buscarPuestos(List<Puesto> rows) {
        List<Puesto> puesto = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM view_puesto WHERE idPuesto = ?";

        try {
            for (Puesto value : rows) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, value.getIdPuesto());
                rs = ps.executeQuery();
                if (rs.next()) {
                    Puesto data = new Puesto();
                    data.setIdPuesto(rs.getInt("idPuesto"));
                    data.setNombre_Puesto(rs.getString("Nombre_Puesto"));
                    data.setNombre_Puesto_Ingles(rs.getString("Nombre_Puesto_Ingles"));
                    data.setArea_idArea(rs.getInt("idArea"));
                    data.setArea_NombreArea(rs.getString("nombre_Area"));
                    puesto.add(data);
                }

            }
            return puesto;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return null;
    }

    public boolean registarPuesto(String idCertificado, List<Puesto> rows) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "INSERT INTO `sistema_capacitacion`.`certificado_puesto`\n"
                + "(`certificado_idCertificado`,\n"
                + "`puesto_idPuesto`)\n"
                + "VALUES\n"
                + "(?,?);";

        try {
            for (Puesto value : rows) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idCertificado));
                ps.setInt(2, value.getIdPuesto());
                ps.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean eliminar(int idCertificado, int idPuesto) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        try {
            String sql = "DELETE FROM `sistema_capacitacion`.`certificado_puesto`\n"
                    + "WHERE certificado_idcertificado= ? AND puesto_idPuesto=?;";

            ps = con.prepareStatement(sql);
            ps.setInt(1, idCertificado);
            ps.setInt(2, idPuesto);
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasAsistentesCurso.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
