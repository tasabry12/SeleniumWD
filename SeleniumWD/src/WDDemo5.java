import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WDDemo5 {

	public static void main(String[] args) {
        // Step 1 Open FF
		WebDriver wd = new FirefoxDriver();
		WebDriverWait wait = new WebDriverWait(wd, 60);
		
		// Step 2 Get the web site
		wd.get("http://www.travelocity.com/");
		
		// Step 3 Click on Flight
		wd.findElement(By.id("tab-flight-tab")).click();
		
		// Step 4 Type Origin
		wd.findElement(By.id("flight-origin")).clear();
		wd.findElement(By.id("flight-origin")).sendKeys("SFO");
		
		// Step 5 Type Destination 
		wd.findElement(By.id("flight-destination")).clear();
		wd.findElement(By.id("flight-destination")).sendKeys("NYC");
		
		// Step 6 Departure date
		wd.findElement(By.id("flight-departing")).clear();
		wd.findElement(By.id("flight-departing")).sendKeys("07/2/2015");
		
		// Step 6 Arrival date
		wd.findElement(By.id("flight-returning")).clear();
		wd.findElement(By.id("flight-returning")).sendKeys("07/20/2015");
		
		// Step 6 Search flight
    	wd.findElement(By.id("search-button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("package-saving-info")));
		
		// Step 6 cheapest fly
		String price = wd.findElement(By.xpath(".//*[@id='flex-card-0']/div[2]/div[2]/div/div/div/span[2]")).getText();
		

		System.out.println("The price is: " + price);
		
		
	

		

	}

}
