package base;

import java.io.File;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;

public class ExcelReader {
  
  // POI excel library revolves around four key interfaces:
  
  // Workbook: A workbook is the high-level representation of a Spreadsheet.
  // Sheet: A workbook may contain many sheets.
  // Row: Represents row in the spreadsheet.
  // Cell: Represents column in the spreadsheet.
  
  // POI library consists of two different implementations for the above interfaces:
  
  // HSSF (Horrible SpreadSheet Format): Implementations used to work with the older excel format: .xls
  // XSSF (XML SpreadSheet Format): Implementations used to work with the newer format: .xlsx
  
  // Useful classes (implements the Workbook, Sheet, Row, and Cell interfaces):
  // HSSFWorkbook; HSSFSheet; HSSFRow; HSSFCell
  // XSSFWorkbook; XSSFSheet; XSSFRow; XSSFCell
  
  public Workbook workbook;
  
  public void loadWorkbook() throws InvalidFormatException {
    try {
      String path = System.getProperty("user.dir") + "\\resources\\data.xlsx";
      System.out.println("Loading file: " + path);
      workbook = WorkbookFactory.create(new File(path));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    //printSheetInfo();
  }
  
  public void printSheetInfo() {
    System.out.println("Printing sheet names: ");
    for (Sheet sheet : workbook) {
      System.out.println(sheet.getSheetName());
    }
    int numSheets = workbook.getNumberOfSheets();
    System.out.println("Loaded file contains " + numSheets + " sheets.");
  }
  
  public Sheet getSheet(int index) {
    Sheet sheet = workbook.getSheetAt(index);
    // Sheet sheet = workbook.getSheet("Blad1");
    return sheet;
  }
  
  // Method to return a cell value as a String regardless of the cell data type
  public String getCellValue_as_String(Sheet sheet, int rowIndex, int colIndex) {    
    DataFormatter dataFormatter = new DataFormatter();
    //CellAddress cellAddress = new CellAddress(rowIndex, colIndex);
    //sheet.setActiveCell(cellAddress);  
    Row row = sheet.getRow(rowIndex);
    Cell cell = CellUtil.getCell(row, colIndex);
    String cellValue = dataFormatter.formatCellValue(cell);
    return cellValue;
  }
  
}
