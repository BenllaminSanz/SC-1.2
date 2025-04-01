package Tables;

import Model.AsistenteCurso;
import Model.LBU;
import Model.PersonalCertificado;
import Model.Puesto;
import Model.Trabajador;
import Querys.ConsultasCertificadoPuesto;
import Subviews.IFrmAgregarInstructor;
import Subviews.IFrmAgregarAsistente;
import Subviews.IFrmEditarCertificado;
import Subviews.IFrmEditarCurso;
import Subviews.IFrmAgregarPuestoCertificado;
import Subviews.SubMenuItem;
import static Tables.CargarTabla.cargarTablaAllAsistentesCertificados;
import static Tables.CargarTabla.cargarTablaAsistentesCertificados;
import static Tables.CargarTabla.cargarTablaEstadoCertificacion;
import View.IFrmAreasPuestos;
import View.IFrmCapacitacion;
import View.IFrmLBU;
import View.IFrmTrabajador;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

public class DesignTabla {

    public static void designTrabajador(IFrmTrabajador frm) {
        TableTrabajador tableModel = new TableTrabajador(CargarTabla.cargarTablaTrabajador());
        JTable tablaTrabajador = frm.jTable_Trabajadores;
        tablaTrabajador.setModel(tableModel);

        // Personalizar el ancho de las columnas
        TableColumnModel colum = tablaTrabajador.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(170);
        colum.getColumn(3).setPreferredWidth(170);
        colum.getColumn(4).setPreferredWidth(100);
        colum.getColumn(5).setPreferredWidth(200);
        colum.getColumn(6).setPreferredWidth(100);
        colum.getColumn(7).setPreferredWidth(70);
        colum.getColumn(8).setPreferredWidth(200);
        colum.getColumn(9).setPreferredWidth(60);
        colum.getColumn(10).setPreferredWidth(60);
        colum.getColumn(11).setPreferredWidth(60);

        // Personalizar el color de los encabezados de columna
        JTableHeader header = tablaTrabajador.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Personalizar el color de fondo y texto de los encabezados
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);

                return c;
            }
        });

        TableCellRenderer customRenderer = tableModel.getStatusRenderer();

        // Establecer el TableCellRenderer personalizado para las celdas de datos
        tablaTrabajador.setDefaultRenderer(Object.class, customRenderer::getTableCellRendererComponent);

        tableModel.setFechaSorter(tablaTrabajador, 4);
        tablaTrabajador.setComponentPopupMenu(frm.PopMenu_Trabajador);
    }

    public static void designTrabajadorB(IFrmTrabajador frm, String texto) {
//      Modelo de la tabla personaliza de trabajador
        List<Trabajador> trabajadores = CargarTabla.buscarTablaTrabajador(texto);
        TableTrabajador Ttbr = new TableTrabajador(trabajadores);
        JTable table = frm.jTable_Trabajadores;
        table.setModel(Ttbr);

        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        //Asignar el renderer personalizado a todas las columnas
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(Ttbr.getStatusRenderer());
        }

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(170);
        colum.getColumn(3).setPreferredWidth(170);
        colum.getColumn(4).setPreferredWidth(100);
        colum.getColumn(5).setPreferredWidth(200);
        colum.getColumn(6).setPreferredWidth(100);
        colum.getColumn(7).setPreferredWidth(70);
        colum.getColumn(8).setPreferredWidth(200);
        colum.getColumn(9).setPreferredWidth(60);
        colum.getColumn(10).setPreferredWidth(60);
        colum.getColumn(11).setPreferredWidth(60);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Ttbr);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        Asignamos un MenuPop en la tabla
        table.setComponentPopupMenu(frm.PopMenu_Trabajador);
    }

    public static void designTrabajadorBStatus(IFrmTrabajador frm, String texto) {
//        Modelo de la tabla personaliza de trabajador
        List<Trabajador> trabajadores = CargarTabla.buscarTablaTrabajadorStatus(texto);
        TableTrabajador Ttbr = new TableTrabajador(trabajadores);
        JTable table = frm.jTable_Trabajadores;
        table.setModel(Ttbr);

        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

//         Asignar el renderer personalizado a todas las columnas
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setCellRenderer(Ttbr.getStatusRenderer());
        }

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(170);
        colum.getColumn(3).setPreferredWidth(170);
        colum.getColumn(4).setPreferredWidth(100);
        colum.getColumn(5).setPreferredWidth(200);
        colum.getColumn(6).setPreferredWidth(100);
        colum.getColumn(7).setPreferredWidth(70);
        colum.getColumn(8).setPreferredWidth(200);
        colum.getColumn(9).setPreferredWidth(60);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Ttbr);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        Asignamos un MenuPop en la tabla
        table.setComponentPopupMenu(frm.PopMenu_Trabajador);
    }

    public static void designBajas(IFrmTrabajador frm) {
        //Modelo de la tabla abstracta de bajas, llenado por una lista generada 
        //por el metodo cargarTablaBajas
        TableBajas Ttbr = new TableBajas(CargarTabla.cargarTablaBajas());
        //Definimos el modelo a jTable1 en la vista
        JTable table = frm.jTable_Bajas;
        table.setModel(Ttbr);

        //Defino un diseño para el encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        //Difino diseño de la columnas 
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(300);
        colum.getColumn(2).setPreferredWidth(100);
        colum.getColumn(3).setCellRenderer(createTooltipRenderer());
        colum.getColumn(3).setPreferredWidth(200);
        colum.getColumn(4).setPreferredWidth(100);
        colum.getColumn(5).setPreferredWidth(200);
        colum.getColumn(6).setPreferredWidth(200);
        colum.getColumn(7).setPreferredWidth(100);
        colum.getColumn(8).setPreferredWidth(60);
        colum.getColumn(9).setPreferredWidth(60);

        ToolTipManager.sharedInstance().registerComponent(table);
        Ttbr.setFechaSorter(table, 2);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_Baja);
    }

    public static void designBajasB(IFrmTrabajador frm, String texto) {
        //Modelo de la tabla abstracta de bajas, llenado por una lista generada 
        //por el metodo cargarTablaBajas
        TableBajas Ttbr = new TableBajas(CargarTabla.buscarTablaBajas(texto));
        //Definimos el modelo a jTable1 en la vista
        JTable table = frm.jTable_Bajas;
        table.setModel(Ttbr);

        //Defino un diseño para el encabezado de la tabla
        //Defino un diseño para el encabezado de la tabla
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        //Difino diseño de la columnas 
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(300);
        colum.getColumn(2).setPreferredWidth(100);
        colum.getColumn(3).setCellRenderer(createTooltipRenderer());
        colum.getColumn(3).setPreferredWidth(200);
        colum.getColumn(4).setPreferredWidth(100);
        colum.getColumn(5).setPreferredWidth(200);
        colum.getColumn(6).setPreferredWidth(200);
        colum.getColumn(7).setPreferredWidth(100);
        colum.getColumn(8).setPreferredWidth(60);
        colum.getColumn(9).setPreferredWidth(60);

        ToolTipManager.sharedInstance().registerComponent(table);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Ttbr);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Asignamos un MenuPop en la tabla
        table.setComponentPopupMenu(frm.PopMenu_Baja);
    }

    public static void designLBU(IFrmLBU frm, LBU mod) {
        //Modelo de la tabla personaliza de trabajador
        TableLBU Tlbu = new TableLBU(CargarTabla.cargarTablaLBU());
        JTable table = frm.jTable_LBU;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(100);
        colum.getColumn(1).setPreferredWidth(100);
        colum.getColumn(2).setPreferredWidth(200);
        colum.getColumn(3).setPreferredWidth(100);
        colum.getColumn(4).setPreferredWidth(80);
        colum.getColumn(5).setPreferredWidth(80);
        colum.getColumn(6).setPreferredWidth(80);
        colum.getColumn(7).setPreferredWidth(300);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);
    }

    public static void designLBUB(IFrmLBU frm, LBU mod, int i) {
        //Modelo de la tabla personaliza de trabajador
        TableLBU Tlbu = new TableLBU(CargarTabla.buscarTablaLBU(mod, i));
        JTable table = frm.jTable_LBU;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(100);
        colum.getColumn(1).setPreferredWidth(100);
        colum.getColumn(2).setPreferredWidth(200);
        colum.getColumn(3).setPreferredWidth(100);
        colum.getColumn(4).setPreferredWidth(80);
        colum.getColumn(5).setPreferredWidth(80);
        colum.getColumn(6).setPreferredWidth(80);
        colum.getColumn(7).setPreferredWidth(300);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);
    }

    public static void designLBUTrabajadorArea(SubMenuItem frm, int a, int b) {
        //Modelo de la tabla personaliza de trabajador
        TableLBUTrabajador Tlbu = new TableLBUTrabajador(CargarTabla.cargarTablaLBUResumenTrabajador(a, b));
        JTable table = frm.jTable_Trabajadores_Supervisor;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(50);
        colum.getColumn(1).setPreferredWidth(50);
        colum.getColumn(2).setPreferredWidth(300);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSorter(sorter);
        table.repaint();
    }

    public static void designSupervisor(IFrmLBU frm) {
        //Modelo de la tabla abstracta de bajas, llenado por una lista generada 
        //por el metodo cargarTablaBajas
        TableSupervisor Ttbr = new TableSupervisor(CargarTabla.cargarTablaSupervisor());
        JTable table = frm.jTable1;
        table.setModel(Ttbr);

        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        //Difino diseño de la columnas 
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(200);
        colum.getColumn(1).setPreferredWidth(60);
        colum.getColumn(2).setPreferredWidth(300);
        colum.getColumn(3).setPreferredWidth(60);

        ToolTipManager.sharedInstance().registerComponent(table);

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Ttbr);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designSupervisorEdit(IFrmTrabajador frm) {
        //Modelo de la tabla abstracta de bajas, llenado por una lista generada 
        //por el metodo cargarTablaBajas
        TableSupervisorEdit Ttbr = new TableSupervisorEdit(CargarTabla.cargarTablaSupervisorEdit());
        //Definimos el modelo a jTable1 en la vista
        JTable table = frm.jTable_Supervisores;
        table.setModel(Ttbr);

        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        //Difino diseño de la columnas 
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(10);
        colum.getColumn(1).setPreferredWidth(250);
        ToolTipManager.sharedInstance().registerComponent(table);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_Supervisor);
    }

    public static void designAreas(IFrmAreasPuestos frm) {
        // Cargar los datos de la tabla de áreas
        TableAreas tableModel = new TableAreas(CargarTabla.cargarTablaArea());
        JTable table = frm.jTable_Areas;
        table.setModel(tableModel);

        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        // Establecer la selección de una sola fila y el menú emergente
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_Area);
    }

    public static void designAllHistorialCursos(IFrmCapacitacion frm) {
        TableHistorialCursos tableModel = new TableHistorialCursos(CargarTabla.cargarTablaHistorialCursos());
        JTable table = frm.JTable_HistorialCurso;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(1).setCellRenderer(createTooltipRenderer());

        TableRowSorter<TableHistorialCursos> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_HistorialCurso);
    }

    public static void designAllHistorialCursosB(String fecha_inicio, IFrmCapacitacion frm) {
        TableHistorialCursos tableModel = new TableHistorialCursos(CargarTabla.buscarTablaHistorialCursos(fecha_inicio));
        JTable table = frm.JTable_HistorialCurso;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);

        TableRowSorter<TableHistorialCursos> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_HistorialCurso);
    }

    public static void designHistorialCursosTipo(IFrmCapacitacion frm, int idTipoCurso) {
        TableHistorialCursos tableModel = new TableHistorialCursos(CargarTabla.cargarTablaHistorialCursosTipo(idTipoCurso));
        JTable table = frm.JTable_HistorialCurso;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(1).setCellRenderer(createTooltipRenderer());

        TableRowSorter<TableHistorialCursos> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_HistorialCurso);
    }

    public static void designHistorialCurso(IFrmCapacitacion frm, int idCurso) {
        TableHistorialCursos tableModel = new TableHistorialCursos(CargarTabla.cargarTablaHistorialCurso(idCurso));
        JTable table = frm.JTable_HistorialCurso;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(1).setCellRenderer(createTooltipRenderer());

        TableRowSorter<TableHistorialCursos> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_HistorialCurso);
    }

    public static void designHistorialCursoNombres(IFrmCapacitacion frm, int idCurso) {
        TableHistorialCursoNombres tableModel = new TableHistorialCursoNombres(CargarTabla.cargarTablaHistorialCursoNombres(idCurso));
        JTable table = frm.JTable_HistorialCurso;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(1).setCellRenderer(createTooltipRenderer());

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_HistorialCurso);
    }

    public static void designAsistentesCurso(IFrmCapacitacion frm, int idCurso) {
        TableAsistentesCurso tableModel = new TableAsistentesCurso(CargarTabla.cargarTablaAsistentesCurso(idCurso));
        JTable table = frm.jTable_Asistentes_Curso;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(2).setPreferredWidth(50);
        colum.getColumn(3).setPreferredWidth(20);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_AsistenteCurso);
    }

    public static void designPuestos(IFrmAreasPuestos frm) {
        TablePuestoArea Ttbr = new TablePuestoArea(CargarTabla.cargarTablaPuestos());
        JTable table = frm.jTable_Puestos;
        table.setModel(Ttbr);
        //Defino un diseño para el encabezado de la tabla
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        //Difino diseño de la columnas
        TableColumnModel colum = frm.jTable_Puestos.getColumnModel();
        colum.getColumn(0).setPreferredWidth(200);
        colum.getColumn(1).setPreferredWidth(200);
        frm.jTable_Puestos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frm.jTable_Puestos.setComponentPopupMenu(frm.PopMenu_Puesto);
    }

    public static void designPuestosArea(IFrmAreasPuestos frm, int idArea) {
        TablePuestoArea Ttbr = new TablePuestoArea(CargarTabla.cargarTablaPuestoArea(idArea));
        JTable table = frm.jTable_Puestos;
        table.setModel(Ttbr);
        //Defino un diseño para el encabezado de la tabla
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        //Difino diseño de la columnas
        TableColumnModel colum = frm.jTable_Puestos.getColumnModel();
        colum.getColumn(0).setPreferredWidth(200);
        colum.getColumn(1).setPreferredWidth(200);
        frm.jTable_Puestos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frm.jTable_Puestos.setComponentPopupMenu(frm.PopMenu_Puesto);
    }

    public static void designAsistentesTrabajadores(IFrmAgregarAsistente frm, String idHistorial) {
        TableAsistentesTrabajador tableModel = new TableAsistentesTrabajador(
                CargarTabla.cargarTablaAsistentesCursoTrabajadores(idHistorial));
        JTable table = frm.jTable_Trabajadores;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        table.setComponentPopupMenu(frm.PopMenu_TrabajadorCapacitacion);
    }

    public static void designAsistentesSupervisores(IFrmAgregarAsistente frm, String idHistorial) {
        TableAsistentesSupervisor tableModel = new TableAsistentesSupervisor(
                CargarTabla.cargarTablaAsistentesCursoSupervisores(idHistorial));
        JTable table = frm.jTable_Trabajadores1;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        table.setComponentPopupMenu(frm.PopMenu_SupervisorCapacitacion);
    }

    public static void designAsistentesAdministrativos(IFrmAgregarAsistente frm, String idHistorial) {
        TableAsistentesAdministrativos tableModel = new TableAsistentesAdministrativos(
                CargarTabla.cargarTablaAsistentesCursoAdministrativos(idHistorial));
        JTable table = frm.jTable_Administrativos;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = frm.jTable_Administrativos.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);

        table.setComponentPopupMenu(frm.PopMenu_AdministrativoCapacitacion);
    }

    public static void designAsistentesTrabajadoresB(IFrmAgregarAsistente frm, String idHistorial, String texto) {
        TableAsistentesTrabajador tableModel = new TableAsistentesTrabajador(
                CargarTabla.buscarTablaAsistentesCursoTrabajadores(idHistorial, texto));
        JTable table = frm.jTable_Trabajadores;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
    }

    public static void designAsistentesSupervisoresB(IFrmAgregarAsistente frm, String idHistorial, String texto) {
        TableAsistentesSupervisor tableModel = new TableAsistentesSupervisor(
                CargarTabla.buscarTablaAsistentesCursoSupervisores(idHistorial, texto));
        JTable table = frm.jTable_Trabajadores1;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
    }

    public static void designAsistentesTrabajadoresF(IFrmAgregarAsistente frm, String idHistorial, AsistenteCurso mod, int i) {
        TableAsistentesTrabajador tableModel = new TableAsistentesTrabajador(
                CargarTabla.filtrarTablaAsistentesCursoTrabajadores(idHistorial, mod, i));
        JTable table = frm.jTable_Trabajadores;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
    }

    public static void designRequerimientosCurso(IFrmEditarCurso frm, String idCurso) {
        TableRequerimientosCurso tableModel = new TableRequerimientosCurso(
                CargarTabla.cargarTablaRequerimientosCurso(idCurso));
        JTable table = frm.jTable_Requerimientos;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        //Difino diseño de la columnas
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(10);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(200);

        table.setComponentPopupMenu(frm.PopMenu_Requerimientos);
    }

    public static void designCertificados(IFrmCapacitacion frm) {
        TableCertificado tableModel = new TableCertificado(
                CargarTabla.cargarTablaCertificados());
        JTable table = frm.jtable_certificados;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        table.setComponentPopupMenu(frm.PopMenu_Certificado);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designInstructores(IFrmAgregarInstructor frm) {
        TableInstructoresCurso tableModel = new TableInstructoresCurso(
                CargarTabla.cargarTablaInstructoresCurso());
        JTable table = frm.jTable_InstructuresLBU;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
    }

    public static void designAsistentesCertificados(IFrmCapacitacion frm, int idCertificado) {
        TableAsistentesCertificado tableModel = new TableAsistentesCertificado(
                cargarTablaAsistentesCertificados(idCertificado));
        JTable table = frm.jTable_AsistentesCertificados;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(30);
        colum.getColumn(2).setPreferredWidth(200);

        TableRowSorter<TableAsistentesCertificado> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setComponentPopupMenu(frm.PopMenu_Certificados);
    }

    public static void designAllAsistentesCertificados(IFrmCapacitacion frm) {
        TableAsistentesCertificados tableModel = new TableAsistentesCertificados(
                cargarTablaAllAsistentesCertificados());
        JTable table = frm.jTable_AsistentesCertificados;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(200);
        colum.getColumn(1).setPreferredWidth(10);
        colum.getColumn(2).setPreferredWidth(10);
        colum.getColumn(3).setPreferredWidth(200);

        TableRowSorter<TableAsistentesCertificados> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        tableModel.setFechaSorter(table, 4);
        table.setComponentPopupMenu(frm.PopMenu_Certificados);
    }

    public static void designAllEstadoCertificacion(IFrmCapacitacion frm) {
        TableReporteEntrenamientos tableModel = new TableReporteEntrenamientos(cargarTablaEstadoCertificacion());
        JTable tablaCertificados = frm.jTable_entrenamientos;
        tablaCertificados.setModel(tableModel);

        // Personalizar el ancho de las columnas
        TableColumnModel colum = tablaCertificados.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(80);
        colum.getColumn(3).setPreferredWidth(170);
        colum.getColumn(4).setPreferredWidth(100);
        colum.getColumn(5).setPreferredWidth(60);
        colum.getColumn(6).setPreferredWidth(100);

        // Personalizar el color de los encabezados de columna
        JTableHeader header = tablaCertificados.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Personalizar el color de fondo y texto de los encabezados
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);

                return c;
            }
        });

        TableCellRenderer customRenderer = tableModel.getStatusRenderer();

        tablaCertificados.setModel(tableModel); // Configurar el modelo de la tabla antes de actualizar los datos

        // Establecer el TableCellRenderer personalizado para las celdas de datos
        tablaCertificados.setDefaultRenderer(Object.class, customRenderer::getTableCellRendererComponent);

        TableRowSorter<TableReporteEntrenamientos> sorter = new TableRowSorter<>(tableModel);
        tablaCertificados.setRowSorter(sorter);
        tablaCertificados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designEstadoCertificacionB(IFrmCapacitacion frm, PersonalCertificado mod, int i) {
        //Modelo de la tabla personaliza de trabajador
        TableReporteEntrenamientos tableModel = new TableReporteEntrenamientos(
                CargarTabla.buscarTablaAsistentesCertificados(mod, i));
        JTable table = frm.jTable_entrenamientos;
        table.setModel(tableModel);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        for (int a = 0; a < table.getColumnModel().getColumnCount(); a++) {
            TableColumn column = table.getColumnModel().getColumn(a);
            column.setCellRenderer(tableModel.getStatusRenderer());
        }

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(tableModel);
        table.setRowSorter(sorter);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designCertificadosTrabajador(IFrmCapacitacion frm, String idAsistente) {
        //Modelo de la tabla personaliza de trabajador
        TableCertificadosTrabajador Tlbu = new TableCertificadosTrabajador(
                CargarTabla.cargarTablaCertificadosTrabajador(idAsistente));
        JTable table = frm.jTable_certificadosTrabajador;
        table.setModel(Tlbu);

        // Personalizar el ancho de las columnas
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(7);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);

        table.setComponentPopupMenu(frm.PopMenu_Certificados);
    }

    public static void designCursosTrabajador(IFrmCapacitacion frm, String idAsistente) {
        //Modelo de la tabla personaliza de trabajador
        TableCursosTrabajador Tlbu = new TableCursosTrabajador(
                CargarTabla.cargarTablaCursosTrabajador(idAsistente));
        JTable table = frm.jTable_cursosTrabajador;
        table.setModel(Tlbu);

        // Personalizar el ancho de las columnas
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(7);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);

        table.setComponentPopupMenu(frm.PopMenu_Cursos);
    }

    public static void designCertificadoPuesto(IFrmEditarCertificado frm, String folio) {
        //Modelo de la tabla personaliza de trabajador
        TablePuestosCertificado Tlbu = new TablePuestosCertificado(
                CargarTabla.cargarTablaPuestoCertificado(Integer.parseInt(folio)));
        JTable table = frm.jTable_Puestos;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);

        table.setComponentPopupMenu(frm.PopMenu_PuestosCertificado);
    }

    public static void designNuevoCertificadoPuesto(IFrmEditarCertificado frmA, ConsultasCertificadoPuesto modC, List<Puesto> rows) {
        //Modelo de la tabla personaliza de trabajador
        TablePuestosCertificado Tlbu = new TablePuestosCertificado(modC.buscarPuestos(rows));
        JTable table = frmA.jTable_Puestos;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);

        table.setComponentPopupMenu(frmA.PopMenu_PuestosCertificado);
    }

    public static void designAdministrativos(IFrmTrabajador frm) {
        //Modelo de la tabla personaliza de trabajador
        TableAdministrativos Tlbu = new TableAdministrativos(
                CargarTabla.cargarTablaAdministrativos());
        JTable table = frm.jTable_Administrativos;
        table.setModel(Tlbu);

        // Personalizar el ancho de las columnas
        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);
        colum.getColumn(2).setPreferredWidth(120);
        colum.getColumn(3).setPreferredWidth(90);
        colum.getColumn(4).setPreferredWidth(80);
        colum.getColumn(5).setPreferredWidth(80);
        colum.getColumn(6).setPreferredWidth(150);
        colum.getColumn(7).setPreferredWidth(150);
        colum.getColumn(8).setPreferredWidth(150);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);

        frm.jTable_Administrativos.setComponentPopupMenu(frm.PopMenu_Administrativo);
    }

    public static void designAsistentesAdministrativosB(IFrmAgregarAsistente frm, String idHistorial, String texto) {
        //Modelo de la tabla personaliza de trabajador
        TableAsistentesAdministrativos Tlbu = new TableAsistentesAdministrativos(
                CargarTabla.buscarTablaAsistentesCursoAdministrativos(idHistorial, texto));
        JTable table = frm.jTable_Administrativos;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);
    }

    public static void designPuestosCertificado(IFrmAgregarPuestoCertificado frm, String idCertificado) {
        TablePuestosCertificado tableModel = new TablePuestosCertificado(
                CargarTabla.cargarTablaPuestoCertificadoAgregar(idCertificado));
        JTable table = frm.jTable_Puestos;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        table.setComponentPopupMenu(frm.PopMenu_TrabajadorCapacitacion);
    }

    public static void designPuestosCertificadoF(IFrmAgregarPuestoCertificado frm, int idCertificado, int idArea) {
        TablePuestosCertificado tableModel = new TablePuestosCertificado(
                CargarTabla.cargarTablaPuestoCertificadoAgregarFiltrar(idCertificado, idArea));
        JTable table = frm.jTable_Puestos;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        table.setComponentPopupMenu(frm.PopMenu_TrabajadorCapacitacion);
    }

    public static void designAsistentesBrigadistas(IFrmAgregarAsistente frm, String idHistorial) {
        TableAsistentesBrigadistas tableModel = new TableAsistentesBrigadistas(
                CargarTabla.cargarTablaAsistentesCursoBrigadistas(idHistorial));
        JTable table = frm.jTable_Administrativos1;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = frm.jTable_Administrativos1.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);

        table.setComponentPopupMenu(frm.PopMenu_BrigadistaCapacitacion);
    }

    public static void designAsistentesBrigadistasF(IFrmAgregarAsistente frm, String idHistorial, AsistenteCurso mod, int i) {
        TableAsistentesBrigadistas tableModel = new TableAsistentesBrigadistas(
                CargarTabla.cargarTablaAsistentesCursoBrigadistasF(idHistorial, mod, i));
        JTable table = frm.jTable_Administrativos1;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = frm.jTable_Administrativos1.getColumnModel();
        colum.getColumn(0).setPreferredWidth(60);
        colum.getColumn(1).setPreferredWidth(200);

        table.setComponentPopupMenu(frm.PopMenu_BrigadistaCapacitacion);
    }

    public static void designAsistentesBrigadistasB(IFrmAgregarAsistente frm, String idHistorial, String texto) {
        //Modelo de la tabla personaliza de trabajador
        TableAsistentesBrigadistas Tlbu = new TableAsistentesBrigadistas(
                CargarTabla.buscarTablaAsistentesCursoBrigadistas(idHistorial, texto));
        JTable table = frm.jTable_Administrativos1;
        table.setModel(Tlbu);

        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableRowSorter<AbstractTableModel> sorter = new TableRowSorter<AbstractTableModel>(Tlbu);
        table.setRowSorter(sorter);
    }

    public static void designPuestosCertificadoB(IFrmAgregarPuestoCertificado frm, String idCertificado, String texto) {
        TablePuestosCertificado tableModel = new TablePuestosCertificado(
                CargarTabla.cargarTablaPuestoCertificadoAgregarBuscar(idCertificado, texto));
        JTable table = frm.jTable_Puestos;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
        table.setComponentPopupMenu(frm.PopMenu_TrabajadorCapacitacion);
    }

    public static void designTrabajadorCapacitacion(IFrmCapacitacion frm, String Area, String Puesto, String Turno, String Estado, int par) {
        TableTrabajadorCapacitacion tableModel = new TableTrabajadorCapacitacion(
                CargarTabla.cargarTablaTrabajadorCapacitacion(Area, Puesto, Turno, Estado, par));
        JTable table = frm.Tabla_Listado;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = frm.Tabla_Listado.getColumnModel();
        colum.getColumn(0).setPreferredWidth(40);
        colum.getColumn(1).setPreferredWidth(200);

    }

    public static void designTrabajadorCertificados(IFrmCapacitacion frm, String Area, String Puesto, String Turno, String Estado, int par) {
        TableTrabajadorCapacitacion tableModel = new TableTrabajadorCapacitacion(
                CargarTabla.cargarTablaTrabajadorCertificados(Area, Puesto, Turno, Estado, par));
        JTable table = frm.Tabla_Listado;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = frm.Tabla_Listado.getColumnModel();
        colum.getColumn(0).setPreferredWidth(40);
        colum.getColumn(1).setPreferredWidth(200);
    }

    public static DefaultTableCellRenderer createTooltipRenderer() {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JComponent && value != null && !value.toString().isEmpty()) {
                    JComponent jc = (JComponent) c;
                    jc.setToolTipText(value.toString());
                }
                return c;
            }
        };
    }

    public static void designPersonalCertificado(IFrmCapacitacion frm, int año, int mes, String area, String puesto, String turno, int par) {
        TablePersonalCertificado tableModel = new TablePersonalCertificado(
                CargarTabla.cargarTablaPersonalCertificado(año, mes, area, puesto, turno, par));
        JTable table = frm.Tabla_Listado;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
    }

    public static void designPersonalCertificadoProceso(IFrmCapacitacion frm, int año, int mes, String proceso) {
        TablePersonalCertificado tableModel = new TablePersonalCertificado(
                CargarTabla.cargarTablaPersonalCertificadoProceso(año, mes, proceso));
        JTable table = frm.Tabla_Listado;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });
    }

    public static void designAsistentesCursos(IFrmCapacitacion frm) {
        TableAsistentesCurso tableModel = new TableAsistentesCurso(CargarTabla.cargarTablaAsistentesCursos());
        JTable table = frm.Tabla_ListadoCursos1;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(2).setPreferredWidth(50);
        colum.getColumn(3).setPreferredWidth(20);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designAllCursosActivos(IFrmCapacitacion frm) {
        TableHistorialCursos tableModel = new TableHistorialCursos(CargarTabla.cargarTablaHistorialCursosActivos());
        JTable table = frm.Tabla_ListadoCursos2;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(1).setCellRenderer(createTooltipRenderer());

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designAsistentesCursosProceso(IFrmCapacitacion frm) {
        TableAsistentesCurso tableModel = new TableAsistentesCurso(CargarTabla.cargarTablaAsistentesProceso());
        JTable table = frm.Tabla_ListadoCursos1;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(20);
        colum.getColumn(1).setPreferredWidth(150);
        colum.getColumn(2).setPreferredWidth(50);
        colum.getColumn(3).setPreferredWidth(20);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public static void designBrigadas(IFrmTrabajador frm) {
        TableBrigadas tableModel = new TableBrigadas(CargarTabla.cargarTablaBrigadas());
        JTable table = frm.jTable_brigadas;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(5);
        colum.getColumn(1).setPreferredWidth(200);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_Brigadas);
    }

    public static void designBrigadistas(IFrmTrabajador frm) {
        TableBrigadistas tableModel = new TableBrigadistas(CargarTabla.cargarTablaBrigadistas());
        JTable table = frm.jTable_brigadistas;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(1).setPreferredWidth(15);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_Brigadistas);
    }
    
     public static void designBrigadistasBrigada(IFrmTrabajador frm, int idBrigada) {
        TableBrigadistasBrigada tableModel = new TableBrigadistasBrigada(CargarTabla.cargarTablaBrigadistasBrigada(idBrigada));
        JTable table = frm.jTable_brigadistas;
        table.setModel(tableModel);
        // Alinear el texto del encabezado al centro
        JTableHeader header = table.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            {
                setBackground(new Color(175, 196, 174));
                setHorizontalAlignment(JLabel.CENTER);
            }
        });

        TableColumnModel colum = table.getColumnModel();
        colum.getColumn(0).setPreferredWidth(15);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setComponentPopupMenu(frm.PopMenu_Brigadistas);
    }

}
