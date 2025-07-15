package Documents;

import static Documents.DesingPDF_Certificados.createHeaderCell;
import Functions.QueryFunctions;
import Querys.Conexion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GeneratorPDF_Certificados extends Conexion {

    private static final Conexion conn = new Conexion();

    public static boolean Certificados_General() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos");
        fileChooser.setSelectedFile(new File("MATRIZ CERTIFICADOS OPERATIVOS " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(15, 15, 20, 15);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM area");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idArea = rs.getString("idArea");

                // NUEVO BLOQUE DE VALIDACIÓN
                PreparedStatement validarAreaCursos = con.prepareStatement(
                        "SELECT 1 FROM sistema_capacitacion.view_asistentes_cursos vac "
                        + "JOIN view_trabajador vt ON vac.idAsistentes_Curso = vt.Folio_Trabajador "
                        + "JOIN certificado_puesto cp ON vac.id_Certificado = cp.certificado_idCertificado "
                        + "JOIN puesto p ON cp.puesto_idPuesto = p.idPuesto "
                        + "WHERE p.area_idArea = ? AND vac.id_tipocurso = 2 AND vac.status_entrenamiento != 'Concluido' LIMIT 1"
                );
                validarAreaCursos.setString(1, idArea);
                ResultSet rsValidaArea = validarAreaCursos.executeQuery();
                if (!rsValidaArea.next()) {
                    continue; // Saltar esta área si no tiene cursos activos
                }

                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("Reporte de Certificados", font1));
                doc.add(new Paragraph("Área: " + rs.getString("nombre_Area")));
                doc.add(new Paragraph("\n"));

                String columns = QueryFunctions.CapturaDirecta("SELECT count(*) FROM turno;");
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                PdfPTable table = new PdfPTable(Integer.parseInt(columns) + 1);
                table.setWidthPercentage(100);
                table.setSplitLate(false);

                // Calcular el ancho relativo de la primera columna
                float firstColumnWidth = 1f / 8f;

                // Calcular el ancho relativo de las columnas restantes
                float otherColumnsWidth = (1f - firstColumnWidth) / Integer.parseInt(columns);
                float[] columnWidths = new float[Integer.parseInt(columns) + 1];

                // Establecer el ancho de la primera columna
                columnWidths[0] = firstColumnWidth;

                // Establecer el ancho de las columnas restantes
                Arrays.fill(columnWidths, 1, columnWidths.length, otherColumnsWidth);

                table.setWidths(columnWidths);

                table.addCell("");
                while (rs1.next()) {
                    String turno = rs1.getString("nombre_turno");
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(turno, font1, color, 1));
                }

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps2.setString(1, idArea);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String idPuesto = rs2.getString("idPuesto");

                    PreparedStatement validarEntrenamiento = con.prepareStatement(
                            "SELECT 1 FROM sistema_capacitacion.view_asistentes_cursos vac "
                            + "JOIN view_trabajador vt ON vac.idAsistentes_Curso = vt.Folio_Trabajador "
                            + "WHERE vac.id_Certificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp WHERE cp.puesto_idPuesto = ?) "
                            + "AND vt.idArea = ? AND vac.id_tipocurso = 2 AND vac.status_entrenamiento != 'Concluido' LIMIT 1");
                    validarEntrenamiento.setString(1, idPuesto);
                    validarEntrenamiento.setString(2, idArea);
                    ResultSet validarRs = validarEntrenamiento.executeQuery();

                    if (!validarRs.next()) {
                        System.out.println(idPuesto + " Esta vacio");
                        continue; // No hay asistentes en entrenamiento, saltar este puesto
                    }

                    String puesto = QueryFunctions.CapturaCondicional("puesto", "nombre_puesto", "idPuesto", idPuesto);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(puesto, font, color, 4));

                    PdfPCell headerCell1 = createHeaderCell("Trabajadores Certificados", font, color, 1);
                    headerCell1.setFixedHeight(20f);
                    headerCell1.setColspan(6);
                    table.addCell(headerCell1);

                    PreparedStatement ps3 = con.prepareStatement("SELECT * FROM turno");
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_todo_personal_certificado vtc \n"
                                + "JOIN view_trabajador vt ON vtc.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                                + "WHERE vtc.idCertificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp\n"
                                + "WHERE cp.puesto_idPuesto = ?) AND vt.idArea = ? AND vt.idTurno = ? AND vtc.tipo_certificacion != 'Obsoleto'");
                        ps4.setInt(1, rs2.getInt("idPuesto"));
                        ps4.setString(2, idArea);
                        ps4.setInt(3, rs3.getInt("idTurno"));
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs4.getString("asistentes_curso_idAsistente") + " " + rs4.getString("Nombre_Trabajador");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs4.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }

                    PdfPCell headerCell2 = createHeaderCell("Trabajadores en Entrenamiento", font, color, 1);
                    headerCell2.setFixedHeight(20f);
                    headerCell2.setColspan(6);
                    table.addCell(headerCell2);

                    PreparedStatement ps5 = con.prepareStatement("SELECT * FROM turno");
                    ResultSet rs5 = ps5.executeQuery();

                    while (rs5.next()) {
                        PreparedStatement ps6 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_cursos vac \n"
                                + "JOIN view_trabajador vt ON vac.idAsistentes_Curso=vt.Folio_Trabajador\n"
                                + "WHERE vac.id_Certificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp WHERE cp.puesto_idPuesto = ?)\n"
                                + "AND vt.idArea = ? AND vt.idTurno = ?\n"
                                + "AND vac.id_tipocurso = 2 AND vac.status_entrenamiento = 'En Entrenamiento'");
                        ps6.setInt(1, rs2.getInt("idPuesto"));
                        ps6.setString(2, idArea);
                        ps6.setInt(3, rs5.getInt("idTurno"));
                        ResultSet rs6 = ps6.executeQuery();

                        if (rs6.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs6.getString("idAsistentes_Curso") + " " + rs6.getString("Nombre_Asistente");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs6.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }
                }
                doc.add(table);
                doc.newPage();
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_Certificados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Certificados_Supervisores() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos Supervisores");
        fileChooser.setSelectedFile(new File("MATRIZ CERTIFICADOS OPERATIVOS SUPERVISORES " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER);
            doc.setMargins(15, 15, 20, 15);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM supervisor");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("Reporte de Certificados", font1));
                doc.add(new Paragraph("Supervisor: " + rs.getString("nombre_Supervisor")));
                doc.add(new Paragraph("Área: "
                        + QueryFunctions.CapturaCondicional("area", "nombre_area", "idArea", rs.getString("area_idArea"))));
                doc.add(new Paragraph("\n"));

                String idSupervisor = rs.getString("idSupervisor");
                String idArea = rs.getString("area_idArea");
                String columns = QueryFunctions.CapturaDirecta("SELECT count(*) FROM supervisor_turno\n"
                        + "WHERE supervisor_idSupervisor = " + idSupervisor + " AND supervisor_area_idArea = " + idArea + ";");
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM supervisor_turno\n"
                        + "WHERE supervisor_idSupervisor = ? AND supervisor_area_idArea = ?");
                ps1.setString(1, idSupervisor);
                ps1.setString(2, idArea);
                ResultSet rs1 = ps1.executeQuery();

                PdfPTable table = new PdfPTable(Integer.parseInt(columns) + 1);
                table.setWidthPercentage(100);

                // Calcular el ancho relativo de la primera columna
                float firstColumnWidth = 1f / 6f;

                // Calcular el ancho relativo de las columnas restantes
                float otherColumnsWidth = (1f - firstColumnWidth) / Integer.parseInt(columns);
                float[] columnWidths = new float[Integer.parseInt(columns) + 1];

                // Establecer el ancho de la primera columna
                columnWidths[0] = firstColumnWidth;

                // Establecer el ancho de las columnas restantes
                Arrays.fill(columnWidths, 1, columnWidths.length, otherColumnsWidth);

                table.setWidths(columnWidths);

                table.addCell("");
                while (rs1.next()) {
                    String idTurno = rs1.getString("turno_idTurno");
                    String turno = QueryFunctions.CapturaCondicional("turno", "nombre_turno", "idTurno", idTurno);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(turno, font1, color, 1));
                }

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps2.setString(1, idArea);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String idPuesto = rs2.getString("idPuesto");
                    String puesto = QueryFunctions.CapturaCondicional("puesto", "nombre_puesto", "idPuesto", idPuesto);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(puesto, font, color, 4));

                    PdfPCell headerCell1 = createHeaderCell("Trabajadores Certificados", font, color, 1);
                    headerCell1.setFixedHeight(20f);
                    headerCell1.setColspan(6);
                    table.addCell(headerCell1);

                    PreparedStatement ps3 = con.prepareStatement("SELECT * FROM supervisor_turno\n"
                            + "WHERE supervisor_idSupervisor = ? AND supervisor_area_idArea = ?");
                    ps3.setString(1, idSupervisor);
                    ps3.setString(2, idArea);
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_todo_personal_certificado vtc \n"
                                + "JOIN view_trabajador vt ON vtc.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                                + "WHERE vtc.idCertificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp\n"
                                + "WHERE cp.puesto_idPuesto = ?) AND vt.idArea = ? AND vt.idTurno = ? AND vtc.tipo_certificacion != 'Obsoleto'");
                        ps4.setInt(1, rs2.getInt("idPuesto"));
                        ps4.setString(2, idArea);
                        ps4.setInt(3, rs3.getInt("turno_idTurno"));
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs4.getString("asistentes_curso_idAsistente") + " " + rs4.getString("Nombre_Trabajador");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs4.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }

                    PdfPCell headerCell2 = createHeaderCell("Trabajadores en Entrenamiento", font, color, 1);
                    headerCell2.setFixedHeight(20f);
                    headerCell2.setColspan(6);
                    table.addCell(headerCell2);

                    PreparedStatement ps5 = con.prepareStatement("SELECT * FROM supervisor_turno\n"
                            + "WHERE supervisor_idSupervisor = ? AND supervisor_area_idArea = ?");
                    ps5.setString(1, idSupervisor);
                    ps5.setString(2, idArea);
                    ResultSet rs5 = ps5.executeQuery();

                    while (rs5.next()) {
                        PreparedStatement ps6 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_cursos vac \n"
                                + "JOIN view_trabajador vt ON vac.idAsistentes_Curso=vt.Folio_Trabajador\n"
                                + "WHERE vac.id_Certificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp WHERE cp.puesto_idPuesto = ?)\n"
                                + "AND vt.idArea = ? AND vt.idTurno = ?\n"
                                + "AND vac.id_tipocurso = 2 AND vac.status_entrenamiento = 'En Entrenamiento'");
                        ps6.setInt(1, rs2.getInt("idPuesto"));
                        ps6.setString(2, idArea);
                        ps6.setInt(3, rs5.getInt("turno_idTurno"));
                        ResultSet rs6 = ps6.executeQuery();

                        if (rs6.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs6.getString("idAsistentes_Curso") + " " + rs6.getString("Nombre_Asistente");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs6.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }
                }
                doc.add(table);
                doc.newPage();
            }
            doc.close();

            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_Certificados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Certificados_EspecificoArea(String areaSeleccionada) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos Supervisores");
        fileChooser.setSelectedFile(new File("MATRIZ CERTIFICADOS OPERATIVOS " + areaSeleccionada + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(15, 15, 20, 15);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM area WHERE nombre_Area = ?");
            ps.setString(1, areaSeleccionada);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("Reporte de Certificados", font1));
                doc.add(new Paragraph("Área: " + rs.getString("nombre_Area")));
                doc.add(new Paragraph("\n"));

                String idArea = rs.getString("idArea");
                String columns = QueryFunctions.CapturaDirecta("SELECT count(*) FROM turno;");
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno");
                ResultSet rs1 = ps1.executeQuery();

                PdfPTable table = new PdfPTable(Integer.parseInt(columns) + 1);
                table.setWidthPercentage(100);
                table.setSplitLate(false);

                // Calcular el ancho relativo de la primera columna
                float firstColumnWidth = 1f / 8f;

                // Calcular el ancho relativo de las columnas restantes
                float otherColumnsWidth = (1f - firstColumnWidth) / Integer.parseInt(columns);
                float[] columnWidths = new float[Integer.parseInt(columns) + 1];

                // Establecer el ancho de la primera columna
                columnWidths[0] = firstColumnWidth;

                // Establecer el ancho de las columnas restantes
                Arrays.fill(columnWidths, 1, columnWidths.length, otherColumnsWidth);

                table.setWidths(columnWidths);

                table.addCell("");
                while (rs1.next()) {
                    String turno = rs1.getString("nombre_turno");
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(turno, font1, color, 1));
                }

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps2.setString(1, idArea);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String idPuesto = rs2.getString("idPuesto");
                    String puesto = QueryFunctions.CapturaCondicional("puesto", "nombre_puesto", "idPuesto", idPuesto);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(puesto, font, color, 4));

                    PdfPCell headerCell1 = createHeaderCell("Trabajadores Certificados", font, color, 1);
                    headerCell1.setFixedHeight(20f);
                    headerCell1.setColspan(6);
                    table.addCell(headerCell1);

                    PreparedStatement ps3 = con.prepareStatement("SELECT * FROM turno");
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_todo_personal_certificado vtc \n"
                                + "JOIN view_trabajador vt ON vtc.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                                + "WHERE vtc.idCertificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp\n"
                                + "WHERE cp.puesto_idPuesto = ?) AND vt.idArea = ? AND vt.idTurno = ? AND vtc.tipo_certificacion != 'Obsoleto'");
                        ps4.setInt(1, rs2.getInt("idPuesto"));
                        ps4.setString(2, idArea);
                        ps4.setInt(3, rs3.getInt("idTurno"));
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs4.getString("asistentes_curso_idAsistente") + " " + rs4.getString("Nombre_Trabajador");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs4.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }

                    PdfPCell headerCell2 = createHeaderCell("Trabajadores en Entrenamiento", font, color, 1);
                    headerCell2.setFixedHeight(20f);
                    headerCell2.setColspan(6);
                    table.addCell(headerCell2);

                    PreparedStatement ps5 = con.prepareStatement("SELECT * FROM turno");
                    ResultSet rs5 = ps5.executeQuery();

                    while (rs5.next()) {
                        PreparedStatement ps6 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_cursos vac \n"
                                + "JOIN view_trabajador vt ON vac.idAsistentes_Curso=vt.Folio_Trabajador\n"
                                + "WHERE vac.id_Certificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp WHERE cp.puesto_idPuesto = ?)\n"
                                + "AND vt.idArea = ? AND vt.idTurno = ?\n"
                                + "AND vac.id_tipocurso = 2 AND vac.status_entrenamiento = 'En Entrenamiento'");
                        ps6.setInt(1, rs2.getInt("idPuesto"));
                        ps6.setString(2, idArea);
                        ps6.setInt(3, rs5.getInt("idTurno"));
                        ResultSet rs6 = ps6.executeQuery();

                        if (rs6.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs6.getString("idAsistentes_Curso") + " " + rs6.getString("Nombre_Asistente");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs6.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }
                }
                doc.add(table);
                doc.newPage();
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_Certificados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Certificados_Especifico(String areaSeleccionada, String turnoSeleccionado) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos Especifico");
        fileChooser.setSelectedFile(new File("MATRIZ CERTIFICADOS OPERATIVOS " + areaSeleccionada + turnoSeleccionado
                + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(15, 15, 20, 15);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM area WHERE nombre_Area = ?");
            ps.setString(1, areaSeleccionada);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("Reporte de Certificados", font1));
                doc.add(new Paragraph("Área: " + rs.getString("nombre_Area")));
                doc.add(new Paragraph("\n"));

                String idArea = rs.getString("idArea");
                String columns = QueryFunctions.CapturaDirecta("SELECT count(*) FROM turno WHERE nombre_turno = '" + turnoSeleccionado + "'");
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM turno WHERE nombre_turno = ?");
                ps1.setString(1, turnoSeleccionado);
                ResultSet rs1 = ps1.executeQuery();

                PdfPTable table = new PdfPTable(Integer.parseInt(columns) + 1);
                table.setWidthPercentage(100);
                table.setSplitLate(false);

                // Calcular el ancho relativo de la primera columna
                float firstColumnWidth = 1f / 8f;

                // Calcular el ancho relativo de las columnas restantes
                float otherColumnsWidth = (1f - firstColumnWidth) / Integer.parseInt(columns);
                float[] columnWidths = new float[Integer.parseInt(columns) + 1];

                // Establecer el ancho de la primera columna
                columnWidths[0] = firstColumnWidth;

                // Establecer el ancho de las columnas restantes
                Arrays.fill(columnWidths, 1, columnWidths.length, otherColumnsWidth);

                table.setWidths(columnWidths);

                table.addCell("");
                while (rs1.next()) {
                    String turno = rs1.getString("nombre_turno");
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(turno, font1, color, 1));
                }

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps2.setString(1, idArea);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String idPuesto = rs2.getString("idPuesto");
                    String puesto = QueryFunctions.CapturaCondicional("puesto", "nombre_puesto", "idPuesto", idPuesto);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(puesto, font, color, 4));

                    PdfPCell headerCell1 = createHeaderCell("Trabajadores Certificados", font, color, 1);
                    headerCell1.setFixedHeight(20f);
                    table.addCell(headerCell1);

                    PreparedStatement ps3 = con.prepareStatement("SELECT * FROM turno WHERE nombre_turno = ?");
                    ps3.setString(1, turnoSeleccionado);
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_todo_personal_certificado vtc \n"
                                + "JOIN view_trabajador vt ON vtc.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                                + "WHERE vtc.idCertificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp\n"
                                + "WHERE cp.puesto_idPuesto = ?) AND vt.idArea = ? AND vt.idTurno = ? AND vtc.tipo_certificacion != 'Obsoleto'");
                        ps4.setInt(1, rs2.getInt("idPuesto"));
                        ps4.setString(2, idArea);
                        ps4.setInt(3, rs3.getInt("idTurno"));
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs4.getString("asistentes_curso_idAsistente") + " " + rs4.getString("Nombre_Trabajador");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs4.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }

                    PdfPCell headerCell2 = createHeaderCell("Trabajadores en Entrenamiento", font, color, 1);
                    headerCell2.setFixedHeight(20f);
                    table.addCell(headerCell2);

                    PreparedStatement ps5 = con.prepareStatement("SELECT * FROM turno WHERE nombre_turno = ?");
                    ps5.setString(1, turnoSeleccionado);
                    ResultSet rs5 = ps5.executeQuery();

                    while (rs5.next()) {
                        PreparedStatement ps6 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_asistentes_cursos vac \n"
                                + "JOIN view_trabajador vt ON vac.idAsistentes_Curso=vt.Folio_Trabajador\n"
                                + "WHERE vac.id_Certificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp WHERE cp.puesto_idPuesto = ?)\n"
                                + "AND vt.idArea = ? AND vt.idTurno = ?\n"
                                + "AND vac.id_tipocurso = 2 AND vac.status_entrenamiento = 'En Entrenamiento'");
                        ps6.setInt(1, rs2.getInt("idPuesto"));
                        ps6.setString(2, idArea);
                        ps6.setInt(3, rs5.getInt("idTurno"));
                        ResultSet rs6 = ps6.executeQuery();

                        if (rs6.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs6.getString("idAsistentes_Curso") + " " + rs6.getString("Nombre_Asistente");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs6.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }
                    }
                }
                doc.add(table);
                doc.newPage();
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_Certificados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean Certificados_GeneralesA() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos Área");
        fileChooser.setSelectedFile(new File("MATRIZ CERTIFICADOS OPERATIVOS GENERAL" + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try (Connection con = conn.getConnection()) {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(8);
            Font font1 = new Font();
            font1.setSize(14);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM supervisor");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
                logo.scalePercent(40F);
                logo.setAlignment(0);
                doc.add(logo);

                doc.add(new Paragraph("Reporte de Certificados", font1));
                doc.add(new Paragraph("Supervisor: " + rs.getString("nombre_Supervisor")));
                doc.add(new Paragraph("Área: "
                        + QueryFunctions.CapturaCondicional("area", "nombre_area", "idArea", rs.getString("area_idArea"))));
                doc.add(new Paragraph("\n"));

                String idArea = rs.getString("area_idArea");
                String columns = QueryFunctions.CapturaCondicional("puesto", "count(*)", "area_idArea", idArea);
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                ps1.setString(1, idArea);
                ResultSet rs1 = ps1.executeQuery();

                PdfPTable table = new PdfPTable(Integer.parseInt(columns) + 1);
                table.setWidthPercentage(100);
                table.addCell("");
                while (rs1.next()) {
                    String idPuesto = rs1.getString("idPuesto");
                    String encabezados = QueryFunctions.CapturaCondicional("puesto", "nombre_puesto", "idPuesto", idPuesto);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(encabezados, font, color, 1));
                }

                PreparedStatement ps2 = con.prepareStatement("SELECT * FROM supervisor_turno\n"
                        + "WHERE supervisor_idSupervisor = ? AND supervisor_area_idArea = ?");
                ps2.setInt(1, rs.getInt("idSupervisor"));
                ps2.setInt(2, rs.getInt("area_idArea"));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    String idTurno = rs2.getString("turno_idTurno");
                    String turno = QueryFunctions.CapturaCondicional("turno", "nombre_turno", "idTurno", idTurno);
                    BaseColor color = new BaseColor(175, 196, 174);
                    table.addCell(createHeaderCell(turno, font1, color, 1));

                    PreparedStatement ps3 = con.prepareStatement("SELECT * FROM puesto WHERE area_idArea = ?");
                    ps3.setString(1, idArea);
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        PreparedStatement ps4 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_todo_personal_certificado vtc \n"
                                + "JOIN view_trabajador vt ON vtc.asistentes_curso_idAsistente=vt.Folio_Trabajador\n"
                                + "WHERE vtc.idCertificado IN (SELECT certificado_idCertificado FROM certificado_puesto cp\n"
                                + "WHERE cp.puesto_idPuesto = ?) AND vt.idTurno = ? AND vtc.tipo_certificacion != 'Obsoleto'");
                        ps4.setInt(1, rs3.getInt("idPuesto"));
                        ps4.setInt(2, rs2.getInt("turno_idTurno"));
                        ResultSet rs4 = ps4.executeQuery();

                        if (rs4.next()) {
                            PdfPCell cell = new PdfPCell();
                            // Agregar los resultados de rs4 a la celda
                            do {
                                String resultado = rs4.getString("asistentes_curso_idAsistente") + " " + rs4.getString("Nombre_Trabajador");
                                cell.addElement(new Phrase(resultado, font));
                            } while (rs4.next());

                            table.addCell(cell);
                        } else {
                            table.addCell("");
                        }

                    }
                }
                doc.add(table);
                doc.newPage();
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException e) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_Certificados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean CertificadosResumenSupervisor() {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos Supervisores");
        fileChooser.setSelectedFile(new File("Resumen de Flexibilidad de Supervisores " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));");
            psql.execute();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_certificados_supervisores");
            ResultSet rs = ps.executeQuery();

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("Resúmen de Flexibilidad de los Supervisores"));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            PdfPTable tablaE = DesingPDF_LBU.encabezadoSupervisorFlexi();
            doc.add(tablaE);
            while (rs.next()) {
                float[] relativeWidths = new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F};
                PdfPTable tabla = new PdfPTable(relativeWidths);
                tabla.setWidthPercentage(100);
                tabla.addCell(rs.getString("nombre_supervisor"));
                tabla.addCell(rs.getString("plantilla"));
                tabla.addCell(rs.getString("certificados"));
                tabla.addCell(rs.getString("flexibilidad") + "%");
                tabla.addCell(rs.getString("En Primer Puesto"));
                tabla.addCell(rs.getString("En Segundo Puesto"));
                doc.add(tabla);
            }
            doc.add(new Phrase("Resúmen de todas las Áreas:"));

            PdfPTable tablaSt = DesingPDF_LBU.encabezadoSupervisorTotalFlexi();
            doc.add(tablaSt);

            PreparedStatement ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_certificados_total");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                PdfPTable tablaTotal = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
                tablaTotal.setWidthPercentage(67);
                tablaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

                tablaTotal.addCell(rs2.getString("plantilla"));
                tablaTotal.addCell(rs2.getString("certificados"));
                tablaTotal.addCell(rs2.getString("flexibilidad") + "%");
                tablaTotal.addCell(rs2.getString("En Primer Puesto"));
                tablaTotal.addCell(rs2.getString("En Segundo Puesto"));
                doc.add(tablaTotal);
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean CertificadosResumenEspecifico(String areaSeleccionada, String turnoSeleccionado) {
        Document doc = new Document();
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy");
        String FechaS = formatFecha.format(fecha);

        // Crear un JFileChooser para que el usuario elija la ubicación del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Matriz de Certificados Operativos Supervisores");
        fileChooser.setSelectedFile(new File("Resumen de Flexibilidad de Supervisores Especifico " + FechaS + ".pdf")); // Nombre predeterminado del archivo

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            // El usuario canceló la operación
            JOptionPane.showMessageDialog(null, "Operación cancelada por el usuario.");
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String rutaDoc = fileToSave.getAbsolutePath();

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(rutaDoc));
            doc.setPageSize(PageSize.LETTER.rotate());
            doc.setMargins(20, 20, 20, 20);
            doc.open();

            Font font = new Font();
            font.setSize(9);
            Font font1 = new Font();
            font1.setSize(8);

            Connection con = conn.getConnection();
            PreparedStatement psql = con.prepareStatement("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));");
            psql.execute();

            PreparedStatement ps = null;
            PreparedStatement ps2 = null;

            if (turnoSeleccionado == "Todos...") {
                ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_certificados_area\n"
                        + "WHERE nombre_Area = ? GROUP BY `Puesto`");
                ps.setString(1, areaSeleccionada);
                
                PreparedStatement psV = con.prepareStatement("SET @nombre_Area:=?");
                psV.setString(1, areaSeleccionada);
                psV.executeUpdate();
                ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_certificados_area_total");
            } else {
                ps = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_certificados_area_turno\n"
                        + "WHERE nombre_Area = ? AND nombre_turno = ? GROUP BY `Puesto`");
                ps.setString(1, areaSeleccionada);
                ps.setString(2, turnoSeleccionado);
                
                PreparedStatement psA = con.prepareStatement("SET @nombre_Area:=?");
                psA.setString(1, areaSeleccionada);
                psA.executeUpdate();
                
                PreparedStatement psT = con.prepareStatement("SET @nombre_Turno:=?");
                psT.setString(1, turnoSeleccionado);
                psT.executeUpdate();
                
                ps2 = con.prepareStatement("SELECT * FROM sistema_capacitacion.view_certificados_area_turno_total");
            }

            ResultSet rs = ps.executeQuery();

            Image logo = Image.getInstance(ClassLoader.getSystemResource("Images/LogoParkdale.png"));
            logo.scalePercent(40F);
            logo.setAlignment(0);
            doc.add(logo);

            doc.add(new Paragraph("Resúmen de Flexibilidad Especifico"));
            doc.add(new Paragraph("Area: " + areaSeleccionada));
            doc.add(new Paragraph("Turno: " + turnoSeleccionado));
            doc.add(new Paragraph("Fecha: " + FechaS));
            doc.add(new Paragraph("\n"));

            PdfPTable tablaE = DesingPDF_LBU.encabezadoSupervisorFlexi();
            doc.add(tablaE);
            while (rs.next()) {
                float[] relativeWidths = new float[]{2F, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F};
                PdfPTable tabla = new PdfPTable(relativeWidths);
                tabla.setWidthPercentage(100);
                tabla.addCell(rs.getString("Puesto"));
                tabla.addCell(rs.getString("plantilla"));
                tabla.addCell(rs.getString("certificados"));
                tabla.addCell(rs.getString("flexibilidad") + "%");
                tabla.addCell(rs.getString("En Primer Puesto"));
                tabla.addCell(rs.getString("En Segundo Puesto"));
                doc.add(tabla);
            }
            doc.add(new Phrase("Resúmen: "));

            PdfPTable tablaSt = DesingPDF_LBU.encabezadoSupervisorTotalFlexi();
            doc.add(tablaSt);

            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                PdfPTable tablaTotal = new PdfPTable(new float[]{0.5F, 0.5F, 0.5F, 0.5F, 0.5F});
                tablaTotal.setWidthPercentage(67);
                tablaTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);

                tablaTotal.addCell(rs2.getString("plantilla"));
                tablaTotal.addCell(rs2.getString("certificados"));
                tablaTotal.addCell(rs2.getString("flexibilidad") + "%");
                tablaTotal.addCell(rs2.getString("En Primer Puesto"));
                tablaTotal.addCell(rs2.getString("En Segundo Puesto"));
                doc.add(tablaTotal);
            }
            doc.close();
            JOptionPane.showMessageDialog(null, "Archivo Creado en " + rutaDoc);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    // Abrir el documento
                    desktop.open(new File(rutaDoc));
                }
            }
            return true;
        } catch (DocumentException | FileNotFoundException | SQLException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showConfirmDialog(null, "Error al generar archivo: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorPDF_LBU.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
