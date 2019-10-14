package testSuite;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellUtil;
import org.testng.annotations.Test;

public class Test_Excel {
  
  @Test
  public void test01() throws InvalidFormatException, EncryptedDocumentException, IOException {
    //ExcelReader excelReader = new ExcelReader();
    
    Workbook workbook;
    
    String path = System.getProperty("user.dir") + "\\resources\\data.xlsx";
    System.out.println("Loading file: " + path);
    workbook = WorkbookFactory.create(new File(path));
    
    int activeIndex = workbook.getActiveSheetIndex();
    System.out.println("Active sheet index: " + activeIndex);
    
    Sheet sheet = workbook.getSheetAt(0);
    
    CellAddress cellAddress = new CellAddress(0, 0);
    sheet.setActiveCell(cellAddress);
    //Cell cell = (Cell) sheet.getActiveCell();
    
    Row row = sheet.getRow(0);
    int columnIndex = 0;
    Cell cell = CellUtil.getCell(row, columnIndex);
    
    DataFormatter dataFormatter = new DataFormatter();
    String cellValue = dataFormatter.formatCellValue(cell);
    System.out.println("Cell value: " + cellValue);
  }
  
}
