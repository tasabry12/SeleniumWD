import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;


public class WDDemo1 {

	public static void main(String[] args) throws Exception {
        
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
		wd.findElement(By.name("ar.rt.leaveSlice.orig.key")).sendKeys("DFW");
		
		// Step6
		wd.findElement(By.name("ar.rt.leaveSlice.dest.key")).clear();
		
		// Step7
		wd.findElement(By.name("ar.rt.leaveSlice.dest.key")).sendKeys("SFO");
		
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
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sortResultsSelect")));
		
		// Step13
		String price = wd.findElement(By.xpath(".//*[@id='matrix']/div[1]/div/div/span")).getText().replace("$", "");
		System.out.println("The lowest price is: " + price);
		
		wd.close();
		
	}

}
