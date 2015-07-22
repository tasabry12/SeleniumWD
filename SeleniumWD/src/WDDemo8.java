import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Data drive Framework
 */

public class WDDemo8 {

	public static void main(String[] args) throws Exception {
        String[][] steps ;
        String[][] data ;
		HashMap map = new HashMap();
		String itin;
		String stepFile = "C:\\Dev\\Tool\\Selenium\\Data\\Keyword.xls";
		String dataFile = "C:\\Dev\\Tool\\Selenium\\Data\\SearchCriteria.xls";
		steps = excelRead(stepFile);
		data = excelRead(dataFile);
		double price;
		WebDriver wd = null;
		
		for (int k = 1 ; k < data.length ; k++) {
		for (int i = 1; i < steps.length; i++) {
			String param = null ;
		
			if (steps[i][0].equalsIgnoreCase("Y")) {
				switch (steps[i][4]) {
				case "open_browser" :
					wd = open_browser(steps[i][7]);
					break;
				case "navigate_to" :
					navigate_to(wd,steps[i][7]);
					break;
				case "click_element" :
					click_element(wd,steps[i][5], steps[i][6]);
					break;
				case "send_keys" :
					if (steps[i][3].contains("From")) param = data[k][0];
					if (steps[i][3].contains("To")) param = data[k][1];
					if (steps[i][3].contains("Leave")) param = data[k][2];
					if (steps[i][3].contains("Return")) param = data[k][3];
					send_keys(wd,steps[i][5], steps[i][6], param);		//	send_keys(wd,steps[i][5], steps[i][6], steps[i][7]);					
					break;
				case "verify_element" :
					verify_element(wd,steps[i][5], steps[i][6]);
					break;					
				case "store_text" :
					String sprice = store_text(wd,steps[i][5], steps[i][6]);
					price = Double.parseDouble(sprice.replaceAll("total", "").replaceAll("\\n", "").replace("per person", "").replace("$", ""));
					itin = "Using " + steps[i][1] + " From " + data[k][0] + " To " + data[k][1] + " Leave Date " + data[k][2] + " Departure Date " + data[k][3];
					System.out.println("For "+ itin + " The price is " + price);
					map.put(price, itin);
					break;
				case "close_browser" :
					System.out.println("\nSto chiudendo");
					close_browser(wd);
					break;

				} // end of switch
			}  // end of if
		} //  end of step for loop
		
		} // end of data for loop
		
//		for ( int i = 1 ; i < data.length ; i++) {
//			System.out.println("Itin : From " + data[i][0] + " To " + data[i][1] + " ");
//			price = findPriceBytraveLoCity(data[i][0], data[i][1]);
//			itin = " Using Travelocity From : " + data[i][0] + " To " + data[i][1];
//			System.out.println("Travelocity Price is: " + price);
//			map.put(price, itin);
//			
//			price = findPriceByOrbitz(data[i][0], data[i][1]);
//			itin = " Using Orbitz From : " + data[i][0] + " To " + data[i][1];
//			System.out.println("Orbitz Price is: " + price);
//			map.put(price, itin);		
//			
//		}
		ArrayList list = new ArrayList(map.keySet());
		
		java.util.Collections.sort(list);
		
		System.out.println("The minimum fare is " + list.get(0) + " and the itin is " + map.get(list.get(0)));

	} //  main
		public static WebDriver open_browser (String browserType){
			WebDriver wd1 = null;
			if (browserType.equalsIgnoreCase("FireFox")){
				wd1 = new FirefoxDriver();
			}
			if (browserType.equalsIgnoreCase("IE")){
				wd1 = new InternetExplorerDriver();
			}
			if (browserType.equalsIgnoreCase("chrome")){
				wd1 = new ChromeDriver();
			}
			return wd1;
			
		}
		
		public static void navigate_to (WebDriver wd, String url){
			wd.get(url);
		}
		
		public static void send_keys(WebDriver wd, String locator, String locString, String data){
			switch (locator){
			case "xpath" : wd.findElement(By.xpath(locString)).clear(); wd.findElement(By.xpath(locString)).sendKeys(data);
			case "name" : wd.findElement(By.name(locString)).clear(); wd.findElement(By.name(locString)).sendKeys(data);
			case "id" : wd.findElement(By.id(locString)).clear(); wd.findElement(By.id(locString)).sendKeys(data);
			}
		}
		
		public static void click_element(WebDriver wd, String locator, String locString){
			switch(locator){
			case "xpath" : wd.findElement(By.xpath(locString)).click(); break;
			case "name" : wd.findElement(By.name(locString)).click(); break;
			case "id" : wd.findElement(By.id(locString)).click(); break;
			}
			
		}
		
		public static void verify_element(WebDriver wd, String locator, String locString) {
			WebDriverWait wait = null;
			switch (locator){
			case "xpath" : wait = new WebDriverWait(wd, 60); wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locString)));
			case "name" :  wait = new WebDriverWait(wd, 60); wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locString)));
			case "id" :    wait = new WebDriverWait(wd, 60); wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locString)));
				
			}
		}
		
		public static String store_text(WebDriver wd, String locator, String locString){
			String v_txt = null;
			switch(locator){
			case "xpath" : v_txt = (wd.findElement(By.xpath(locString)).getText()) ; break;
			case "name" :  v_txt = (wd.findElement(By.name(locString)).getText()) ; break;
			case "id" :    v_txt = (wd.findElement(By.id(locString)).getText()) ; break; 
			}
			return v_txt;
		}
		
		public static void close_browser(WebDriver wd) {
			wd.close();
			
		}
		

//		
//		public static double findPriceBytraveLoCity(String dep, String dest) throws Exception {
//        
//	        // Step 1 Open FF
//			WebDriver wd = new FirefoxDriver();
//			WebDriverWait wait = new WebDriverWait(wd, 60);
//			
//			// Step 2 Get the web site
//			wd.get("http://www.travelocity.com/");
//			
//			// Step 3 Click on Flight
//			wd.findElement(By.id("tab-flight-tab")).click();
//			
//			// Step 4 Type Origin
//			wd.findElement(By.id("flight-origin")).clear();
//			wd.findElement(By.id("flight-origin")).sendKeys(dep);
//			
//			// Step 5 Type Destination 
//			wd.findElement(By.id("flight-destination")).clear();
//			wd.findElement(By.id("flight-destination")).sendKeys(dest);
//			
//			// Step 6 Departure date
//			wd.findElement(By.id("flight-departing")).clear();
//			wd.findElement(By.id("flight-departing")).sendKeys("07/19/2015");
//			
//			// Step 6 Arrival date
//			wd.findElement(By.id("flight-returning")).clear();
//			wd.findElement(By.id("flight-returning")).sendKeys("07/20/2015");
//			
//			// Step 6 Search flight
//			wd.findElement(By.id("search-button")).click();
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("package-saving-info")));
//			
//			// Step 6 cheapest fly
//			String price = wd.findElement(By.xpath(".//*[@id='flex-card-0']/div[2]/div[2]/div/div/div/span[2]")).getText().replace("$", "");
//
//		
//	    	wd.close();
//	
//		   double dprice = Double.parseDouble(price);
//		   return dprice;
//		
//	}
		
//		public static double findPriceByOrbitz(String dep, String dest) throws Exception {
//	        
//		// Step1
//		WebDriver wd = new FirefoxDriver();        //we have created an object wd
//		WebDriverWait wait = new WebDriverWait(wd, 60);
//		
//		// Step2
//		wd.get("http://www.orbitz.com");
//		
//		// Step3
//		wd.findElement(By.xpath("//input[@name='search.type']")).click();
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ar.rt.leaveSlice.orig.key")));
//		
//		/*
//		 * Wait will ignore instances of NotFoundException that are encountered (thrown) by default in the 'until' 
//		 * condition, and immediately propagate all others.		
//		 */
//
//
//		// Step4
//		wd.findElement(By.name("ar.rt.leaveSlice.orig.key")).clear();
//		
//		// Step5
//		wd.findElement(By.name("ar.rt.leaveSlice.orig.key")).sendKeys(dep);
//		
//		// Step6
//		wd.findElement(By.name("ar.rt.leaveSlice.dest.key")).clear();
//		
//		// Step7
//		wd.findElement(By.name("ar.rt.leaveSlice.dest.key")).sendKeys(dest);
//		
//		// Step8
//		wd.findElement(By.name("ar.rt.leaveSlice.date")).clear();
//		
//		// Step9
//		wd.findElement(By.name("ar.rt.leaveSlice.date")).sendKeys("07/19/15");
//		
//		// Step10
//		wd.findElement(By.name("ar.rt.returnSlice.date")).clear();
//		
//		// Step11
//		wd.findElement(By.name("ar.rt.returnSlice.date")).sendKeys("07/20/15");
//		
//		// Step12
//		wd.findElement(By.name("search")).click();
//		Thread.sleep(30000);
//		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='buttonLink link']")));
//		
//		// Step13
//		String price = wd.findElement(By.xpath("//span[@class='price']")).getText().replace("$", "");
//		wd.close();
//	
//		double dprice = Double.parseDouble(price);
//		return dprice;
//		
//	}

		

		public static String[][] excelRead(String fileName) throws Exception  {
			File excel = new File(fileName);
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
			

			
				
		public static String cellToString(HSSFCell cell) {
			
			int type;
			Object result;
			type = cell.getCellType();
			
			switch (type) {
			
			case 0 : // numeric value in Excel
				
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					//result = cell.getDateCellValue();
					
					DataFormatter df = new DataFormatter();
					String CellValue = df.formatCellValue(cell);
					//result = CellValue;
					System.out.println("La data inserita è: "+CellValue);
					
			        StringBuffer date = new StringBuffer((String) CellValue );
			        System.out.println( "before: " + date );
			        result = date.insert( 6, "20" );
			        System.out.println( "after: " + date );

				}
				else {
					result = cell.getNumericCellValue();
				}
				break;
				
			case 1 : // string value in Excel
				result = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK :
				result = cell.CELL_TYPE_BLANK;
				break;

			default :
				throw new RuntimeException("There are no support for this type of cell");
							
			
			}
			return result.toString();
		}
		

	

}
