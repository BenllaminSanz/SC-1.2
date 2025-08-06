/*Funciones dentro del programa para modificar segun la necesidad el texto 
de los botones de las ventanas asi como asignar datos dentro de los parametros
obtenidos entre las base de datos y la vista*/
package Functions;

import Model.Administrativos;
import Model.Area;
import Model.Certificado;
import Model.Curso;
import Model.HabilidadesCurso;
import Model.HistorialCurso;
import Model.PersonalCertificado;
import Querys.ConsultasArea;
import Querys.ConsultasPuesto;
import Querys.ConsultasTrabajador;
import Model.Puesto;
import Model.RequerimientosCurso;
import Model.Trabajador;
import Querys.Conexion;
import Querys.ConsultasAdministrativo;
import Querys.ConsultasCertificado;
import Querys.ConsultasCertificadoPuesto;
import Querys.ConsultasCurso;
import Querys.ConsultasHabilidadesCurso;
import Querys.ConsultasHistorialCurso;
import Querys.ConsultasPersonalCertificado;
import Querys.ConsultasRequerimientosCurso;
import Subviews.IFrmEditarAdministrativo;
import Subviews.IFrmEditarArea;
import Subviews.IFrmEditarCertificacion;
import Subviews.IFrmEditarCertificado;
import Subviews.IFrmEditarCurso;
import Subviews.IFrmEditarDocumento;
import Subviews.IFrmEditarHabilidad;
import Subviews.IFrmEditarHistorialCurso;
import Subviews.IFrmEditarPuesto;
import Subviews.IFrmEditarTrabajador;
import Tables.DesignTabla;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

public class ButtonFunctions {

    static List<JCheckBox> puestosCheckboxes = new ArrayList<>(); // Lista para almacenar los checkboxes de puestos relacionados

    /*Metodo creado para asignar los valores dentro de los componentes de la 
    vista editar trabajador respecto a la seleccion echa en la vista trabajador*/
    public static void TxtBtnTrabajador(String texto, String folio, IFrmEditarTrabajador frm, Trabajador mod, ConsultasTrabajador modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar Trabajador");
                frm.btn_guardar.setText(texto);
                frm.txt_area.addItem("Seleccionar Área...");
                frm.txt_puesto.addItem("Seleccionar Puesto...");
                frm.txt_turno.addItem("Seleccionar Turno...");
                llenarComboBoxesTrabajador(frm);
                frm.cb_brigada.setEnabled(false);
                frm.repaint();
                break;
            case "Actualizar":
                frm.setTitle("Modificar Trabajador");
                frm.btn_guardar.setText(texto);
                mod.setFolio_Trabajador(folio);
                if (modC.buscar(mod)) {
                    frm.txt_Folio.setText(mod.getFolio_Trabajador());
                    frm.txt_Nombre.setText(mod.getNombre_Trabajador());
                    frm.txt_Curp.setText(mod.getCURP_Trabajador());
                    frm.txt_RFC.setText(mod.getRFC_Trabajador());
                    frm.txt_IMSS.setText(mod.getIMSS_Trabajador());
                    frm.dt_Ingreso.setDate(DateTools.StringtoDatePDF(mod.getFecha_Antiguedad()));
                    String idx2 = QueryFunctions.CapturaCondicionalSimple("area", "idArea",
                            "Nombre_Area", mod.getNombre_Area());
                    QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.txt_area);
                    frm.txt_area.setSelectedIndex(Integer.parseInt(idx2) - 1);
                    String idx = QueryFunctions.CapturaCondicionalSimple("area", "idArea",
                            "Nombre_Area", frm.txt_area.getSelectedItem().toString());
                    frm.txt_puesto.removeAllItems();
                    QueryFunctions.LlenarComboBoxAnidado("puesto", "area", "area_idArea", "idArea",
                            idx, "Nombre_Puesto", frm.txt_puesto);
                    String puesto = mod.getNombre_Puesto();
                    frm.txt_puesto.setSelectedItem(puesto);
                    String idx3 = QueryFunctions.CapturaCondicionalSimple("turno", "idTurno",
                            "nombre_turno", mod.getNombre_Turno());
                    QueryFunctions.LlenarComboBox("turno", "nombre_turno", frm.txt_turno);
                    frm.txt_turno.setSelectedIndex(Integer.parseInt(idx3) - 1);
                    frm.txt_salario.setText(String.valueOf(mod.getSalarioDiario_Trabajador()));
                    if (mod.getFecha_Cumpleaños() != null) {
                        frm.dt_Cumpleaños.setDate(DateTools.StringtoDatePDF(mod.getFecha_Cumpleaños()));
                    }
                    frm.txt_Email.setText(mod.getEmail_Trabajador());
                    frm.txt_tel.setText(mod.getTel_Trabajador());
                    frm.txtA_Com.setText(mod.getComentario());
                    QueryFunctions.LlenarComboBox("brigadas", "nombre_brigada", frm.cb_brigada);
                    if (mod.isBrigadista()) {
                        frm.cb_brigadista.setSelected(true);
                        String brigada = QueryFunctions.CapturaCondicionalSimple("Brigadas", "nombre_brigada",
                                "idBrigadas", String.valueOf(mod.getBrigada()));
                        frm.cb_brigada.setSelectedItem(brigada);
                    } else {
                        frm.cb_brigadista.setSelected(false);
                        frm.cb_brigada.setEnabled(false);
                    }
                    frm.repaint();
                    break;
                }
        }
    }

    public static void TxtBtnArea(String texto, int folio, IFrmEditarArea frm, Area mod, ConsultasArea modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar Área");
                frm.btn_guardar.setText(texto);
                frm.repaint();
                break;
            case "Actualizar":
                frm.setTitle("Moficar Área");
                frm.btn_guardar.setText(texto);
                mod.setIdArea(folio);
                if (modC.buscar(mod)) {
                    frm.txt_Folio.setText(mod.getNombre_Area());
                    frm.txt_descripcion.setText(mod.getDescripcion());
                    frm.cb_proceso.setSelectedIndex(mod.getTipo_proceso() - 1);
                    frm.repaint();
                    break;
                }
        }
    }

    public static void TxtBtnPuesto(String texto, int folio, IFrmEditarPuesto frm, Puesto mod, ConsultasPuesto modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar Puesto");
                frm.cb_AreaPuesto.addItem("Seleccionar Área...");
                llenarComboBoxesPuesto(frm);
                frm.btn_guardar.setText(texto);
                frm.repaint();
                break;
            case "Actualizar":
                frm.setTitle("Modificar Puesto");
                frm.btn_guardar.setText(texto);
                mod.setIdPuesto(folio);
                if (modC.buscar(mod)) {
                    frm.txt_Puesto.setText(mod.getNombre_Puesto());
                    frm.txt_PuestoIngles.setText(mod.getNombre_Puesto_Ingles());
                    frm.txt_descPuesto.setText(mod.getDescripcion());
                    QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_AreaPuesto);
                    String puesto = QueryFunctions.CapturaCondicionalSimple("area", "Nombre_Area", "idArea", String.valueOf(mod.getArea_idArea()));
                    frm.cb_AreaPuesto.setSelectedItem(puesto);
                    frm.txt_nivel.setText(mod.getNivel());
                    frm.txt_centrocosto.setText(String.valueOf(mod.getCentro_de_Costo()));
                    frm.txt_totaltbr.setText(String.valueOf(mod.getPropuesto_Trabajadores()));
                    frm.txt_totalturno_A.setText(String.valueOf(mod.getTurnoA()));
                    frm.txt_totalturno_B.setText(String.valueOf(mod.getTurnoB()));
                    frm.txt_totalturno_C.setText(String.valueOf(mod.getTurnoC()));
                    frm.txt_totalturno_D.setText(String.valueOf(mod.getTurnoD()));
                    frm.txt_totalturno_LV.setText(String.valueOf(mod.getTurnoLV()));
                    frm.txt_totalturno_LS.setText(String.valueOf(mod.getTurnoLS()));
                    
                    frm.repaint();
                    break;
                }
        }
    }

    public static void TxtBtnCurso(String texto, String folio, IFrmEditarCurso frm, Curso mod, ConsultasCurso modC, ConsultasRequerimientosCurso modDC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar Curso");
                frm.btn_agregarCurso.setText(texto);
                QueryFunctions.LlenarComboBox("tipo_curso", "nombre_tipo", frm.cb_tipoCurso);
                break;
            case "Actualizar":
                frm.setTitle("Modificar Curso");
                frm.btn_agregarCurso.setText(texto);
                QueryFunctions.LlenarComboBox("tipo_curso", "nombre_tipo", frm.cb_tipoCurso);
                int id = Integer.parseInt(QueryFunctions.CapturaCondicionalSimple(
                        "curso", "idCurso", "nombre_curso", folio));
                mod.setIdCurso(id);
                if (modC.buscar(mod)) {
                    frm.txt_nombreCurso.setText(mod.getNombre_Curso());
                    String tipo = QueryFunctions.CapturaCondicionalSimple(
                            "tipo_curso", "nombre_tipo", "idtipo_curso",
                            String.valueOf(mod.getIdTipo_Curso()));
                    frm.cb_tipoCurso.setSelectedItem(tipo);
                    frm.spin_semanas.setValue(mod.getSemanas());
                    if ((frm.cb_tipoCurso.getSelectedItem().toString()).equals(
                            "PROCESOS DE PRODUCCIÓN")) {
                        String certificado = QueryFunctions.CapturaCondicionalSimple(
                                "certificado", "nombre_certificado", "idcertificado",
                                String.valueOf(mod.getIdCertificado()));
                        frm.cb_certificado.setSelectedItem(certificado);
                        frm.jLabel3.setVisible(true);
                        frm.cb_certificado.setVisible(true);
                        frm.btn_certificado.setVisible(true);
                        DesignTabla.designRequerimientosCurso(frm, String.valueOf(mod.getIdCurso()));
                        DesignTabla.designHabilidadesCurso(frm, String.valueOf(mod.getIdCurso()));
                    } else {
                        frm.jLabel3.setVisible(false);
                        frm.cb_certificado.setVisible(false);
                        frm.btn_certificado.setVisible(false);
                    }
                    frm.revalidate();
                    frm.repaint();
                }
                break;
        }
    }

    private static void llenarComboBoxesTrabajador(IFrmEditarTrabajador frm) {
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.txt_area);
        QueryFunctions.LlenarComboBox("puesto", "Nombre_Puesto", frm.txt_puesto);
        QueryFunctions.LlenarComboBox("turno", "Nombre_Turno", frm.txt_turno);
        QueryFunctions.LlenarComboBox("brigadas", "nombre_brigada", frm.cb_brigada);
    }

    private static void llenarComboBoxesPuesto(IFrmEditarPuesto frm) {
        QueryFunctions.LlenarComboBox("area", "Nombre_Area", frm.cb_AreaPuesto);
    }

    public static void TxtBtnHistorialCurso(String texto, String folio, IFrmEditarHistorialCurso frm, HistorialCurso mod, ConsultasHistorialCurso modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar registro del Curso");
                frm.btn_guardar.setText(texto);
                CargarCheckBoxArea(frm);
                CargarCheckBoxPuesto(frm);
                CargarCheckBoxTurno(frm);
                frm.repaint();
                break;

            case "Actualizar":
                frm.setTitle("Modificar registro del Curso");
                frm.btn_guardar.setText(texto);
                mod.setIdHistorialCurso(Integer.parseInt(folio));
                if (modC.buscar(mod)) {
                    frm.txt_instructor.setText(mod.getNombre_instructor());
                    frm.cb_curso.setSelectedItem(mod.getNombre_Curso());
                    frm.dc_inicio.setDate(DateTools.StringtoDatePDF(mod.getFecha_inicio()));
                    frm.dc_estimado.setDate(DateTools.StringtoDatePDF(mod.getFecha_estimada()));
                    frm.txt_asistentes.setText(String.valueOf(mod.getAsistentes_estimados()));
                    frm.txt_duracion.setText(String.valueOf(mod.getTiempo_estimado()));
                    CargarCheckBoxArea(frm);
                    CargarCheckBoxPuesto(frm);
                    CargarCheckBoxTurno(frm);
                    modC.buscarAPT(frm, mod);
                }
                break;
        }
    }

    public static void TxtBtnRequerimiento(String texto, String folio, IFrmEditarDocumento frm, RequerimientosCurso mod, ConsultasRequerimientosCurso modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar requerimiento al Curso");
                frm.btn_guardar.setText(texto);
                frm.repaint();
                break;

            case "Actualizar":
                frm.setTitle("Modificar requerimiento del Curso");
                frm.btn_guardar.setText(texto);
                mod.setIdRequerimiento(Integer.parseInt(folio));
                if (modC.buscar(mod)) {
                    String curso = QueryFunctions.CapturaCondicionalSimple("curso", "nombre_curso", "idCurso",
                            String.valueOf(mod.getIdCurso()));
                    frm.cb_cursos.setSelectedItem(curso);
                    frm.txt_requerimiento.setText(mod.getNombre_requerimiento());
                    frm.txt_descripcion.setText(mod.getDescp_requerimiento());
                    frm.txt_documento.setText(mod.getNombre_archivo());
                }
                break;
        }
    }
    
        public static void TxtBtnHabilidad(String texto, String folio, IFrmEditarHabilidad frm, HabilidadesCurso mod, ConsultasHabilidadesCurso modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar habilidad al Curso");
                frm.btn_guardar.setText(texto);
                frm.repaint();
                break;

            case "Actualizar":
                frm.setTitle("Modificar habilidad del Curso");
                frm.btn_guardar.setText(texto);
                mod.setIdHabilidad(Integer.parseInt(folio));
                if (modC.buscar(mod)) {
                    String curso = QueryFunctions.CapturaCondicionalSimple("curso", "nombre_curso", "idCurso",
                            String.valueOf(mod.getIdCurso()));
                    frm.cb_cursos.setSelectedItem(curso);
                    frm.txt_orden1.setText(String.valueOf(mod.getIdHabilidad()));
                    frm.txt_requerimiento.setText(mod.getNombre_habilidad());
                    frm.txt_orden.setText(String.valueOf(mod.getOrden_habilidad()));
                }
                break;
        }
    }

    public static void TxtBtnCertificado(String texto, String folio, IFrmEditarCertificado frm, Certificado mod, ConsultasCertificado modC) {
        ConsultasCertificadoPuesto modCP = new ConsultasCertificadoPuesto();
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar Certificado");
                frm.btn_guardar.setText(texto);
                frm.repaint();
                break;
            case "Actualizar":
                frm.setTitle("Modificar Certificado");
                frm.btn_guardar.setText(texto);
                mod.setIdCertificado(Integer.parseInt(folio));
                if (modC.buscar(mod)) {
                    frm.txt_certificado.setText(mod.getNombre_Certificado());
                    frm.txt_gerente1.setText(mod.getGerente1());
                    frm.txt_gerente2.setText(mod.getGerente2());
                    frm.txt_gerente3.setText(mod.getGerente3());
                    frm.txt_puesto1.setText(mod.getPuesto1());
                    frm.txt_puesto2.setText(mod.getPuesto2());
                    frm.txt_puesto3.setText(mod.getPuesto3());
                    if (modCP.consultar(folio)) {
                        frm.jTable_Puestos.setEnabled(true);
                        DesignTabla.designCertificadoPuesto(frm, folio);
                    } else {
                        frm.jTable_Puestos.setEnabled(false);
                    }
                }
                break;
        }
    }

    public static void TxtBtnCertificacion(String texto, String folio, IFrmEditarCertificacion frm, PersonalCertificado mod, ConsultasPersonalCertificado modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar Certificación");
                frm.btn_guardar.setText(texto);
                QueryFunctions.LlenarComboBox("certificado", "nombre_Certificado", frm.cb_certificado);
                frm.repaint();
                break;
            case "Actualizar":
                frm.setTitle("Modificar Certificación");
                frm.btn_guardar.setText(texto);
                QueryFunctions.LlenarComboBox("certificado", "nombre_Certificado", frm.cb_certificado);
                mod.setIdCertificacion(Integer.parseInt(folio));
                if (modC.buscar(mod)) {
                    frm.txt_nomina.setText(String.valueOf(mod.getIdTrabajador_Certificado()));
                    frm.txt_nombre.setText(QueryFunctions.CapturaCondicionalSimple(
                            "trabajador", "nombre_trabajador", "Folio_Trabajador", mod.getIdTrabajador_Certificado()));
                    frm.txt_apellidos.setText(mod.getApellidos());
                    frm.txt_nombres.setText(mod.getNombres());
                    String curso = QueryFunctions.CapturaCondicionalSimple("certificado", "nombre_certificado", "idcertificado",
                            String.valueOf(mod.getId_Certificado()));
                    frm.cb_certificado.setSelectedItem(curso);
                    frm.cb_tipo.setSelectedItem(mod.getTipo_certificado());
                    frm.dc_inicio.setDate(DateTools.StringtoDatePDF(mod.getFecha_inicio()));
                    frm.dc_certificacion.setDate(DateTools.StringtoDatePDF(mod.getFecha_certificacion()));
                }
                break;
        }
    }

    private static void CargarCheckBoxArea(IFrmEditarHistorialCurso panel) {
        panel.jPanel2.setLayout(new BoxLayout(panel.jPanel2, BoxLayout.Y_AXIS));
        String sql = "SELECT * FROM area";

        PreparedStatement ps = null;
        ResultSet rs = null;

        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

//        List<JCheckBox> puestosCheckboxes = new ArrayList<>(); // Lista para almacenar los checkboxes de puestos relacionados
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                JCheckBox item = new JCheckBox(rs.getString("nombre_Area"));
                panel.jPanel2.add(item);
                item.addActionListener((ActionEvent e) -> {
                    try {
                        String nombreArea = item.getText();
                        Connection con1 = conn.getConnection();

                        // Realizar la consulta a la base de datos para obtener los puestos relacionados con el área
                        String sqlPuestos = "SELECT * FROM puesto p JOIN area a ON p.area_idarea = a.idArea WHERE a.nombre_Area = ?";
                        try (PreparedStatement ps1 = con1.prepareStatement(sqlPuestos)) {
                            ps1.setString(1, nombreArea);
                            // Crear una lista temporal para almacenar los nombres de los puestos relacionados
                            try (ResultSet rs1 = ps1.executeQuery()) {
                                // Crear una lista temporal para almacenar los nombres de los puestos relacionados
                                List<String> puestosRelacionados = new ArrayList<>();
                                while (rs1.next()) {
                                    String nombrePuestoDB = rs1.getString("nombre_puesto");
                                    puestosRelacionados.add(nombrePuestoDB);
                                }   // Actualizar las selecciones de los JCheckBox en jPanel3
                                Component[] components = panel.jPanel3.getComponents();
                                for (Component component : components) {
                                    if (component instanceof JCheckBox) {
                                        JCheckBox checkBox = (JCheckBox) component;
                                        String nombrePuesto = checkBox.getText();

                                        boolean relacionado = puestosRelacionados.contains(nombrePuesto);
                                        if (puestosRelacionados.contains(nombrePuesto)) {
                                            // Agregar el checkbox de puesto relacionado a la lista
                                            puestosCheckboxes.add(checkBox);
                                        }
                                        boolean seleccionado = checkBox.isSelected() || relacionado;
                                        if (item.isSelected()) {
                                            checkBox.setSelected(seleccionado);
                                        } else {
                                            for (JCheckBox puestoCheckbox : puestosCheckboxes) {
                                                puestoCheckbox.setSelected(false);
                                            }
                                        }
                                    }
                                }
                                // Cerrar el ResultSet y PreparedStatement
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ButtonFunctions.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
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

    private static void CargarCheckBoxPuesto(IFrmEditarHistorialCurso panel) {
        panel.jPanel3.setLayout(new BoxLayout(panel.jPanel3, BoxLayout.Y_AXIS));
        String sql = "SELECT * FROM puesto JOIN area ON puesto.area_idArea=area.idArea";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                JCheckBox item = new JCheckBox(rs.getString("nombre_Puesto"));
                String nombreArea = rs.getString("nombre_Area");
                panel.jPanel3.add(item);
                item.addActionListener((ActionEvent e) -> {
                    if (puestosCheckboxes.contains(item)) {
                        boolean todosSeleccionados = true;
                        for (JCheckBox checkbox : puestosCheckboxes) {
                            if (!checkbox.isSelected()) {
                                todosSeleccionados = false;
                                break;
                            }
                        }
                        Component[] components = panel.jPanel2.getComponents();
                        for (Component component : components) {
                            if (component instanceof JCheckBox) {
                                JCheckBox checkBox = (JCheckBox) component;
                                String area = checkBox.getText();
                                if (nombreArea.equals(area)) {
                                    checkBox.setSelected(todosSeleccionados);
                                }
                            }
                        }
                    }
                });
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

    private static void CargarCheckBoxTurno(IFrmEditarHistorialCurso panel) {
        panel.jPanel4.setLayout(new BoxLayout(panel.jPanel4, BoxLayout.Y_AXIS));
        String sql = "SELECT * FROM turno";

        PreparedStatement ps = null;
        ResultSet rs = null;
        Conexion conn = new Conexion();
        Connection con = conn.getConnection();

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                JCheckBox item = new JCheckBox(rs.getString("nombre_Turno"));
                panel.jPanel4.add(item);
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

    public static void TxtBtnAdministrativo(String texto, String folio, IFrmEditarAdministrativo frm, Administrativos mod, ConsultasAdministrativo modC) {
        switch (texto) {
            case "Guardar":
                frm.setTitle("Agregar");
                frm.btn_guardar.setText(texto);
                QueryFunctions.LlenarComboBox("brigadas", "nombre_Brigada", frm.cb_brigada);
                frm.cb_brigada.setEnabled(false);
                frm.repaint();
                break;
            case "Actualizar":
                frm.setTitle("Modificar");
                frm.btn_guardar.setText(texto);
                mod.setFolio_Trabajador(folio);
                if (modC.buscar(mod)) {
                    frm.txt_Folio.setText(mod.getFolio_Trabajador());
                    frm.txt_Nombre.setText(mod.getNombre_Trabajador());
                    frm.txt_Curp.setText(mod.getCURP_Trabajador());
                    frm.txt_RFC.setText(mod.getRFC_Trabajador());
                    frm.txt_IMSS.setText(mod.getIMSS_Trabajador());
                    frm.dt_Ingreso.setDate(DateTools.StringtoDatePDF(mod.getFecha_Antiguedad()));
                    frm.jTextField1.setText(mod.getCia());
                    frm.jTextField2.setText(mod.getPuesto());
                    frm.jTextField3.setText(mod.getArea());
                    frm.cb_turnos.setSelectedItem(mod.getTurno());
                    QueryFunctions.LlenarComboBox("brigadas", "nombre_brigada", frm.cb_brigada);
                    System.out.println(mod.isBrigadista());
                    if (mod.isBrigadista()) {
                        frm.cb_brigadista.setSelected(true);
                        String brigada = QueryFunctions.CapturaCondicionalSimple("Brigadas", "nombre_brigada",
                                "idBrigadas", String.valueOf(mod.getBrigada()));
                        frm.cb_brigada.setSelectedItem(brigada);
                    } else {
                        frm.cb_brigadista.setSelected(false);
                        frm.cb_brigada.setEnabled(false);
                    }
                    frm.repaint();
                    break;
                }
        }
    }
}
