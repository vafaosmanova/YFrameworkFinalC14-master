package ui_automation.utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    private static XSSFSheet workSheet;
    private static XSSFWorkbook workBook;
    private static XSSFCell cell;
    private static XSSFRow row;
    private static MissingCellPolicy xRow;


    /**
     * Opens an Excel file and a specific sheet within it.
     *
     * @param path      The file path of the Excel file.
     * @param sheetName The name of the sheet to be accessed.
     * @throws Exception If the file cannot be accessed or the sheet doesn't exist.
     */
    public static void setExcelFile(String path, String sheetName) {
        try {
            FileInputStream ExcelFile = new FileInputStream(path);
            workBook = new XSSFWorkbook(ExcelFile);
            workSheet = workBook.getSheet(sheetName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Issie in setting the file.");
        }
    }

    /**
     * Retrieves the data from a specific cell in the sheet.
     *
     * @param rowNum The row number of the cell (0-based).
     * @param colNum The column number of the cell (0-based).
     * @return The data from the cell as an Object. It could be a String, a Double, or a Date.
     */
    // Improved version of getCellData
    public static Object getCellData(int rowNum, int colNum) {
        try {
            cell = workSheet.getRow(rowNum).getCell(colNum);
            if (cell == null) {
                // Return a default value or indicate that the cell is empty/blank
                return ""; // or you can return null;
            }

            switch (cell.getCellType()) { // NUMERIC
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    } else {
                        return cell.getNumericCellValue();
                    }
                case BOOLEAN:
                    return cell.getBooleanCellValue();
                case FORMULA:
                    // Handle formula cells if necessary
                    return cell.getCellFormula();
                case BLANK:
                    return "";
                default:
                    return "Unsupported cell type: " + cell.getCellType();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "Error reading cell data";
        }
    }

    /**
     * Writes data to a specific cell in the sheet and saves the changes to the file.
     *
     * @param path   The file path where the Excel file is saved.
     * @param value  The data to be written to the cell.
     * @param rowNum The row number of the cell (0-based).
     * @param colNum The column number of the cell (0-based).
     * @throws Exception If there's an error writing data to the cell or saving the file.
     */
    public static void setCellData(String path, String value, int rowNum, int colNum) throws Exception {
        try {
            row = workSheet.getRow(rowNum);
            cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workBook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            throw (e);
        }

    }

    /**
     * Creates a new Excel file with a single sheet and writes data to the first cell.
     *
     * @param fileName The name (including path) for the new Excel file.
     * @param value    The data to be written to the first cell.
     */
    public static void createExcelAndWrite(String fileName, String value) {
        workBook = new XSSFWorkbook();
        workSheet = workBook.createSheet("FIRST SHEET");
        row = workSheet.createRow(0);
        cell = row.createCell(0);
        cell.setCellValue(value);
        // try with resources
        try (FileOutputStream fos = new FileOutputStream(new File(fileName))) {
            workBook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a new Excel file with a single sheet.
     *
     * @param filePath  The file path where the new Excel file will be created.
     * @param sheetName The name of the new sheet in the Excel file.
     */
    public static void createNewExcelFile(String filePath, String sheetName) {
        // Create a workbook object. XSSFWorkbook is for .xlsx files.
        Workbook workbook = new XSSFWorkbook();
        // Create a sheet in the workbook
        Sheet sheet = workbook.createSheet(sheetName);

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            // Write the workbook with the new sheet to the file
            workbook.write(fileOut);
            System.out.println("New Excel file with a sheet named '" + sheetName + "' created at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the workbook
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes data to a specific cell in an existing or new Excel file and sheet.
     *
     * @param filePath  The file path of the Excel file.
     * @param sheetName The name of the sheet.
     * @param rowNum    The row number of the cell (0-based).
     * @param colNum    The column number of the cell (0-based).
     * @param data      The data to be written to the cell.
     */
    public static void writeToCell(String filePath, String sheetName, int rowNum, int colNum, String data) {
        FileInputStream fileIn = null;
        FileOutputStream fileOut = null;
        Workbook workbook = null;

        try {
            fileIn = new FileInputStream(new File(filePath));
            workbook = new XSSFWorkbook(fileIn);
            Sheet sheet = workbook.getSheet(sheetName);

            // If the sheet doesn't exist, create it.
            if (sheet == null) {
                sheet = workbook.createSheet(sheetName);
            }

            // Get the row or create it if it doesn't exist
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            // Get the cell or create it if it doesn't exist
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }

            // Set the cell value
            cell.setCellValue(data);

            // Write the changes back to the file
            fileOut = new FileOutputStream(new File(filePath));
            workbook.write(fileOut);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fileIn != null) {
                    fileIn.close();
                }
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printAllDataFromExcelFile(String filePath, String sheetName) {
        // try with resources
        try (FileInputStream contentOfTheFile = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(contentOfTheFile);
            Sheet sheetByName = workbook.getSheet(sheetName);
            for (Row row : sheetByName) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + " | ");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + " | ");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + " | ");
                            break;
                    }
                }
                System.out.println();
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("There was a problem reading the file at " + filePath);
        }

    }


}