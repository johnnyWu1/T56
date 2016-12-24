package COM.T56;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class you {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrsome.exe");
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		
        WebDriver driver=new ChromeDriver();
//        driver.manage().window().maximize();
        driver.get("http://www.baidu.com");
        
        WebElement bu=driver.findElement(By.cssSelector("input#kw"));
        Actions action=new Actions(driver);
        action.contextClick(bu).perform();
	}

}
