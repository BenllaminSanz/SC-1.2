//Clase con el metodo para llenar Combo Box segun los datos en BD
package Functions;

import Querys.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

public class QueryFunctions {

    //Metodo para llenar los ComboBox segun una consulta en la bd
    public static void LlenarComboBox(String tabla, String valor, JComboBox<String> combo) {
        String sql = "SELECT * FROM " + tabla;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                combo.addItem(rs.getString(valor));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para llenar los ComboBox que depende de la selccion de otro ComboBox
    public static void LlenarComboBoxAnidado(String tabla1, String tabla2, String ref1,
            String ref2, String valor, String colum, JComboBox<String> combo) {

        String sql = "SELECT * FROM " + tabla1 + " INNER JOIN " + tabla2 + " on "
                + tabla1 + "." + ref1 + " = " + tabla2 + "." + ref2
                + " WHERE " + tabla2 + "." + ref2 + " = '" + valor + "'";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();
//        combo.removeAllItems();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString(colum));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para llenar los ComboBox segun una consulta en la bd
    public static void LlenarComboBoxCondicional(String tabla, String valor, String colum, String ref, JComboBox<String> combo) {
        String sql = "SELECT * FROM " + tabla + " WHERE " + colum + "=" + ref;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                combo.addItem(rs.getString(valor));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Metodo para capturar el item seleccionado en el ComboBox
    public static String CapturaCondicionalSimple(String tabla, String colum, String refe, String valor) {
        String sql = "SELECT " + colum + " FROM " + tabla + " WHERE " + refe + " = ?";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, valor);
            rs = ps.executeQuery();
            while (rs.next()) {
                String a = rs.getString(colum);
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "0";
    }

    //Metodo para capturar el item seleccionado en el ComboBox
    public static String CapturaCondicional(String tabla, String colum, String refe, String valor) {
        String sql = "SELECT " + colum + " FROM " + tabla + " WHERE " + refe + " = " + valor;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String a = rs.getString(colum);
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "0";
    }

    //Metodo para llenar los ComboBox que depende de la selccion de otro ComboBox
    public static String CapturaCondicionalAnidado(String tabla1, String tabla2, String ref1,
            String ref2, String resp, String colum, String valor) {

        String sql = "SELECT " + resp + " FROM " + tabla1 + " INNER JOIN " + tabla2 + " on "
                + tabla1 + "." + ref1 + " = " + tabla2 + "." + ref2
                + " WHERE " + tabla1 + "." + colum + " = '" + valor + "'";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String a = rs.getString(resp);
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    //Metodo para capturar el item seleccionado en el ComboBox
    public static String CapturaSimple(String tabla, String colum) {
        String sql = "SELECT " + colum + " FROM " + tabla;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String resp = rs.getString(colum);
                return resp;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Metodo para capturar el item seleccionado en el ComboBox
    public static String CapturaDirecta(String consulta) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        String mode = "SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));";
        try {
            ps = con.prepareStatement(mode);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement(consulta);
            rs = ps.executeQuery();

            while (rs.next()) {
                String resp = rs.getString(1);
                return resp;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(QueryFunctions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
}
