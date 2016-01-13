import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Data drive Framework
 */

public class WDDemo3 {

	public static void main(String[] args) throws Exception {
		String[][] data ;
		data = excelRead();
		double price;
		for ( int i = 1 ; i < data.length ; i++) {
			price = findPrice(data[i][1]);
			System.out.println("Price For Flight From " + data[i][0] + " To " + data[i][1] + " is " + price);
		}

	}
		
		
		public static double findPrice(String dest) throws Exception {
        
		// Step1
		WebDriver wd = new FirefoxDriver();        //we have created an object wd
		WebDriverWait wait = new WebDriverWait(wd, 60);
		
		// Step2
		wd.get("http://www.orbitz.com");
		
		// Step3
		wd.findElement(By.xpath("//input[@name='search.type']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ar.rt.leaveSlice.orig.key")));
		
		/*
		 * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in the 'until' 
		 * condition, and immediately propagate all others.		
		 */


		// Step4
		wd.findElement(By.name("ar.rt.leaveSlice.orig.key")).clear();
		
		// Step5
		wd.findElement(By.name("ar.rt.leaveSlice.orig.key")).sendKeys("SFO");
		
		// Step6
		wd.findElement(By.name("ar.rt.leaveSlice.dest.key")).clear();
		
		// Step7
		wd.findElement(By.name("ar.rt.leaveSlice.dest.key")).sendKeys(dest);
		
		// Step8
		wd.findElement(By.name("ar.rt.leaveSlice.date")).clear();
		
		// Step9
		wd.findElement(By.name("ar.rt.leaveSlice.date")).sendKeys("11/19/15");
		
		// Step10
		wd.findElement(By.name("ar.rt.returnSlice.date")).clear();
		
		// Step11
		wd.findElement(By.name("ar.rt.returnSlice.date")).sendKeys("11/20/15");
		
		// Step12
		wd.findElement(By.name("search")).click();
		Thread.sleep(30000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='buttonLink link']")));
		
		// Step13
		String price = wd.findElement(By.xpath(".//*[@id='matrix']/div[1]/div/div/span")).getText().replace("$", "");
		wd.close();
	
		double dprice = Double.parseDouble(price);
		return dprice;
		
	}

		

		public static String[][] excelRead() throws Exception  {
			File excel = new File("C:\\DEV\\Selenium\\Data\\dataWDDemo3.xls");
			FileInputStream fis = new FileInputStream(excel);
			HSSFWorkbook wb = new HSSFWorkbook(fis);
			HSSFSheet ws = wb.getSheet("Input");
			
			int rowNum = ws.getLastRowNum() + 1 ;
			int colNum = ws.getRow(0).getLastCellNum();
			String[][] data = new String[rowNum][colNum];
			
			for  ( int i = 0 ; i < rowNum ; i++ ) {
				HSSFRow row = ws.getRow(i);
				   for ( int j = 0; j < colNum ; j++ ) {
					   HSSFCell cell = row.getCell(j);
					   String value = cellToString(cell);
					   data[i][j] = value;
				   }
				   
			}
			return data;
		}
			
//			public static String[][] cellToString(HSSFCell cell) {
//				
//				int type;
//				Object result;
//				type = cell.getCellType();
//				
//				switch (type) {
//				
//				case 0 : // numeric value in Excel
//					result = cell.getNumericCellValue();
//					break;
//					
//				case 1 : // string value in Excel
//					result = cell.getStringCellValue();
//					break;
//				default :
//					throw new RuntimeException("There are no support for this type of cell");
//								
//				
//				}
//				return result.toString();
//			}
			
				
		public static String cellToString(HSSFCell cell) {
			
			int type;
			Object result;
			type = cell.getCellType();
			
			switch (type) {
			
			case 0 : // numeric value in Excel
				result = cell.getNumericCellValue();
				break;
				
			case 1 : // string value in Excel
				result = cell.getStringCellValue();
				break;
			default :
				throw new RuntimeException("There are no support for this type of cell");
							
			
			}
			return result.toString();
		}
		

	

}
