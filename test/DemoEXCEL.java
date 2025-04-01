/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jebes
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class DemoEXCEL{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar el archivo Excel
        System.out.print("Ingrese la ruta completa del archivo Excel: ");
        String filePath = scanner.nextLine();

        // Solicitar la fila y columna inicial para la lectura
        System.out.print("Ingrese la fila inicial (0 para la primera fila): ");
        int startRow = scanner.nextInt();

        System.out.print("Ingrese la columna inicial (0 para la primera columna): ");
        int startCol = scanner.nextInt();

        // Leer el archivo Excel
        System.out.println(filePath);
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Leer la primera hoja
            System.out.println("Leyendo datos del archivo Excel...");

            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                // Imprimir valores desde la columna inicial
                for (int colIndex = startCol; colIndex < row.getLastCellNum(); colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        System.out.print(getCellValue(cell) + "\t");
                    } else {
                        System.out.print("NULL\t");
                    }
                }
                System.out.println(); // Salto de línea después de cada fila
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo Excel: " + e.getMessage());
        }
    }

    // Método para obtener el valor de una celda en formato String
    private static String getCellValue(Cell cell) {
        
        int tipoDeCelda = cell.getCellType();
        
        switch (tipoDeCelda) {
            case 1:
                return cell.getStringCellValue();
            case 0:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case 2:
                return String.valueOf(cell.getBooleanCellValue());
            case 3:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}

