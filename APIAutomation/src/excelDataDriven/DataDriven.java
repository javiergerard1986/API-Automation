package excelDataDriven;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	// 1. Identify testCases column by scanning the entire 1st row
	// 2. Once column is identified then scan entire testCase column to identify purchase test case
	// 3. After you grab purchase testCase row = pull all the data of that row and feed into test	
	public ArrayList<String> getData(String tcName) {
		ArrayList<String> exampleArray = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\excelDataDriven\\ExcelBook.xlsx");
			XSSFWorkbook workBook = new XSSFWorkbook(fis);
			
			System.out.println("Number of sheets: " + workBook.getNumberOfSheets());
			
			for(int x = 0; x < workBook.getNumberOfSheets(); x++) {
				
				if(workBook.getSheetName(x).equalsIgnoreCase("testdata")) {
					XSSFSheet sheet = workBook.getSheetAt(x);
					
					// 1. Identify testCases column by scanning the entire 1st row
					Iterator<Row> rowsIterator = sheet.iterator(); // Collection of rows
					Row firstRow = rowsIterator.next(); // Single Row
					Iterator<Cell> cellsIterator = firstRow.cellIterator(); // Collection of cells
		
					int index = 0;
					
					while(cellsIterator.hasNext()) {
						Cell cell = cellsIterator.next(); // Single Cell
						if(cell.getStringCellValue().equalsIgnoreCase("TestCases")) {
							System.out.println(cell.getStringCellValue() + " - Column Index: " + index);
							
							// 2. Once column is identified then scan entire testCase column to identify purchase test case
							while(rowsIterator.hasNext()) {
								Row row = rowsIterator.next();
								Iterator<Cell> cellIterator = row.cellIterator();
								Cell c = cellIterator.next();
								
								// 3. After you grab purchase testCase row = pull all the data of that row and feed into test
								if(c.getStringCellValue().equalsIgnoreCase(tcName)) {
									System.out.println("----------------------");
									System.out.println(c.getStringCellValue());
									System.out.println("----------------------");
									while(cellIterator.hasNext()) {
										c = cellIterator.next();
										if(c.getCellType() == CellType.NUMERIC) {
											exampleArray.add(NumberToTextConverter.toText(c.getNumericCellValue()));
										
										} else {
											exampleArray.add(c.getStringCellValue());
										}
									}
									
								}
							}
							
						}
						index++;
					}
				}
			}
			workBook.close();
		} catch(IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return exampleArray;
	}
}
