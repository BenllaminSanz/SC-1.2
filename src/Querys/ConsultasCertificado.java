package Querys;

import Model.Certificado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultasCertificado extends Conexion {

    public boolean buscar(Certificado mod) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConnection();

        String sql = "SELECT * FROM certificado WHERE idCertificado = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, mod.getIdCertificado());
            rs = ps.executeQuery();

            if (rs.next()) {
                mod.setNombre_Certificado(rs.getString("nombre_certificado"));
                mod.setGerente1(rs.getString("gerente1"));
                mod.setGerente2(rs.getString("gerente2"));
                mod.setGerente3(rs.getString("gerente3"));
                mod.setPuesto1(rs.getString("puesto1"));
                mod.setPuesto2(rs.getString("puesto2"));
                mod.setPuesto3(rs.getString("puesto3"));

                return true;
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

    public boolean agregarCertificado(Certificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        String sql = "INSERT INTO `sistema_capacitacion`.`certificado`\n"
                + "(`nombre_certificado`,`gerente1`,`gerente2`,`gerente3`,`puesto1`,`puesto2`,`puesto3`)\n"
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";

        String sql1 = "SELECT * FROM `sistema_capacitacion`.`certificado`\n"
                + "WHERE nombre_certificado = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Certificado());
            ps.setString(2, mod.getGerente1());
            ps.setString(3, mod.getGerente2());
            ps.setString(4, mod.getGerente3());
            ps.setString(5, mod.getPuesto1());
            ps.setString(6, mod.getPuesto2());
            ps.setString(7, mod.getPuesto3());
            ps.execute();

            ps = con.prepareStatement(sql1);

            return true;

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

    public boolean agregarSinCurso(Certificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();
        String sql = "INSERT INTO `sistema_capacitacion`.`certificado`\n"
                + "(`nombre_certificado`,`gerente1`,`gerente2`,`gerente3`, `puesto1`,`puesto2`,`puesto3`) "
                + "VALUES\n"
                + "(?,?,?,?,?,?,?);";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Certificado());
            ps.setString(2, mod.getGerente1());
            ps.setString(3, mod.getGerente2());
            ps.setString(4, mod.getGerente3());
            ps.setString(5, mod.getPuesto1());
            ps.setString(6, mod.getPuesto2());
            ps.setString(7, mod.getPuesto3());
            ps.execute();
            return true;

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

    public boolean actualizarCurso(Certificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`certificado`\n"
                + "SET\n"
                + "`nombre_certificado` = ?,\n"
                + "`gerente1` = ?,"
                + "`gerente2` = ?,"
                + "`gerente3` = ?,"
                + "`puesto1` = ?,"
                + "`puesto2` = ?,"
                + "`puesto3` = ?\n"
                + "WHERE `idcertificado` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Certificado());
            ps.setString(2, mod.getGerente1());
            ps.setString(3, mod.getGerente2());
            ps.setString(4, mod.getGerente3());
            ps.setString(5, mod.getPuesto1());
            ps.setString(6, mod.getPuesto2());
            ps.setString(7, mod.getPuesto3());
            ps.setInt(8, mod.getIdCertificado());
            ps.execute();
            return true;

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

    public boolean actualizarSinCurso(Certificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "UPDATE `sistema_capacitacion`.`certificado`\n"
                + "SET\n"
                + "`nombre_certificado` = ?,\n"
                + "`gerente1` = ?,"
                + "`gerente2` = ?,"
                + "`gerente3` = ?,"
                + "`puesto1` = ?,"
                + "`puesto2` = ?,"
                + "`puesto3` = ?\n"
                + "WHERE `idcertificado` = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Certificado());
            ps.setString(2, mod.getGerente1());
            ps.setString(3, mod.getGerente2());
            ps.setString(4, mod.getGerente3());
            ps.setString(5, mod.getPuesto1());
            ps.setString(6, mod.getPuesto2());
            ps.setString(7, mod.getPuesto3());
            ps.setInt(8, mod.getIdCertificado());
            ps.execute();
            return true;

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

    public boolean eliminar(Certificado mod) {
        PreparedStatement ps = null;
        Connection con = getConnection();

        String sql = "DELETE FROM `sistema_capacitacion`.`certificado`\n"
                + "WHERE nombre_certificado = ?;";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mod.getNombre_Certificado());
            ps.execute();
            return true;

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
}
