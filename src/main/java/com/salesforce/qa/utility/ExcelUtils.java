package com.salesforce.qa.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;

    // Initialize the Excel workbook and sheet
    public static void setExcelFile(String filePath, String sheetName) {
        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(excelFile);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get data from a specific cell
    public static String getCellData(int rowNum, int colNum) {
        Cell cell = sheet.getRow(rowNum).getCell(colNum);
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    // Close the Excel workbook
	/*
	 * public static void closeExcelFile() { try { if (workbook != null) {
	 * workbook.close(); } } catch (IOException e) { e.printStackTrace(); } }
	 */
}
