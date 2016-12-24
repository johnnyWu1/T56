package COM.T56;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class zuoye {

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// webdriver.gecko.driver
		System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver", "C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");
		System.setProperty("phantomjs.binary.path", "D:/Programs/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		

		Capabilities capabilities = new DesiredCapabilities();
		System.out.println(capabilities.isJavascriptEnabled());
//		WebDriver driver = new PhantomJSDriver(capabilities );
		
		WebDriver driver = new ChromeDriver();

		// WebDriver driver=new FirefoxDriver();

		// driver.manage().window().maximize();

		String xh = "201340922108";
		String pass = "wu950429";

		driver.get("http://10.8.9.49/");
		waitForElementVisible(driver, By.cssSelector("#icode"), 3);
		File yzmFile = captureElement(driver.findElement(By.id("icode")));
		System.out.println(yzmFile.getAbsolutePath());
		String code = OCR.recognizeText(yzmFile);
		System.out.println("\n当前识别的验证码是：" + code);

		boolean isSuccess;
		do {
			driver.findElement(By.cssSelector("#txtUserName")).clear();
			driver.findElement(By.cssSelector("#txtUserName")).sendKeys(xh);
			driver.findElement(By.cssSelector("#TextBox2")).clear();
			driver.findElement(By.cssSelector("#TextBox2")).sendKeys(pass);
			System.out.println(" \n请确认验证码是否正确！ 如果正确请按Enter 如果不正确请输入验证码按Enter：");
			String inputText = input.nextLine();
			if (inputText.trim().equals("")) {
				inputText = code;
			}
			driver.findElement(By.cssSelector("#txtSecretCode")).sendKeys(inputText);
			driver.findElement(By.cssSelector("#Button1")).click();
			isSuccess = true;
			if (waitForAlert(driver, 1)) {
				driver.switchTo().alert().accept();
				isSuccess = false;
				System.out.println("验证码输入有误！");
			}
			if (isSuccess) {
				if (!waitForElementPresent(driver,
						By.cssSelector("li.top:nth-child(1) > a:nth-child(1) > span:nth-child(1)"), 3)) {
					driver.get("http://10.8.9.49/");
					waitForElementVisible(driver, By.cssSelector("#icode"), 3);
					yzmFile = captureElement(driver.findElement(By.id("icode")));
					System.out.println(yzmFile.getAbsolutePath());
					code = OCR.recognizeText(yzmFile);
					System.out.println("\n当前识别的验证码是：" + code);
					isSuccess = false;
				}
			}
		} while (!isSuccess);
		Actions action = new Actions(driver);
		// li.top:nth-child(5) > ul:nth-child(2) > li:nth-child(8) >
		// a:nth-child(1)
		WebElement nav = driver.findElement(By.cssSelector("li.top:nth-child(5) > a:nth-child(1)"));
		action.moveToElement(nav, 2, 2).build().perform();

		nav = driver.findElement(By.xpath("//*[@id='headDiv']/ul/li[5]//a[text()='成绩查询']"));

		action.moveToElement(nav, 2, 2).click().build().perform();

		// driver.findElement(By.cssSelector("li.top:nth-child(5) >
		// ul:nth-child(2) > li:nth-child(8) > a:nth-child(1)")).click();
		waitForElementPresent(driver, By.cssSelector("#iframeautoheight"), 3);
		driver = driver.switchTo().frame("iframeautoheight");
		waitPageRefresh(driver, 3);

		if (!waitForElementPresent(driver, By.cssSelector("#ddlXN"), 20)){
			System.out.println(driver.getPageSource());
			System.exit(0);
		}
		Select xn = new Select(driver.findElement(By.cssSelector("#ddlXN")));

		List<WebElement> options = xn.getOptions();

		for (int i = 1; i < options.size(); i++) {
			xn = new Select(driver.findElement(By.cssSelector("#ddlXN")));
			xn.selectByIndex(i);
			options = xn.getOptions();
			System.out.println(options.get(i).getText());
			driver.findElement(By.xpath("//*[@id='btn_xn']")).click();
			waitPageRefresh(driver, 20);
			Thread.sleep(1000);
			
			List<WebElement> trs = driver.findElements(By.xpath("//*[@id='Datagrid1']/tbody/tr"));
			System.out.println("size:"+trs.size());
			for (WebElement webElement : trs) {
				//*[@id="Datagrid1"]/tbody/tr[2]/td[4]
				
				String keName = webElement.findElement(By.xpath("td[4]")).getText();
				String score = webElement.findElement(By.xpath("td[13]")).getText();
				System.out.println(String.format("%s:\t%s", keName, score));
			}

		}

		// ((JavascriptExecutor)driver).executeScript("document.querySelector('#headDiv
		// > ul > li:nth-child(3) > ul > li:nth-child(1) > a').click();");
		// driver.findElement(By.cssSelector("li.top:nth-child(3) >
		// ul:nth-child(2) > li:nth-child(1) > a:nth-child(1)")).click();
		// driver.manage().window().maximize();

		//
		// waitForElementPresent(driver,"#iframeautoheight",3);
		// driver = driver.switchTo().frame("iframeautoheight");
		//
		// waitForElementPresent(driver,"#pjkc", 5);
		// Thread.sleep(1000);
		// Select pjkc = new
		// Select(driver.findElement(By.cssSelector("#pjkc")));
		// int selectSize = pjkc.getOptions().size();
		// System.out.println(selectSize);
		// for(int j = 0;j<selectSize;j++){
		// pjkc.selectByIndex(j+1);
		// System.out.println(pjkc.getOptions().get(j).getText());
		// Thread.sleep(1000);
		//
		// }

		// .click('#headDiv > ul > li:nth-child(3) > ul > li:nth-child(1) > a')
		// .execute(function() {
		// document.querySelector('#headDiv > ul > li:nth-child(3) > ul >
		// li:nth-child(1) > a').click();
		// })
		// .frame('#iframeautoheight',function (argument) {
		// // body...
		// })
		// .waitForElementVisible('#pjkc', 3000,false);
		//
		//

	}

	// static void waitForElementNotPresent(WebDriver driver,final String
	// cssSeletor,int timeout) {
	// new WebDriverWait(driver, timeout).until(new Predicate<WebDriver>() {
	// public boolean apply(WebDriver input) {
	//// System.out.println(input.findElement(By.cssSelector(cssSeletor)).isDisplayed());
	// return !input.findElement(By.cssSelector(cssSeletor)).isDisplayed();
	// }
	// });
	// }

	static boolean waitForElementVisible(WebDriver driver, final By by, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	static boolean waitForAlert(WebDriver driver, int timeout) {

		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 页面元素截图
	public static File captureElement(WebElement element) throws Exception {
		WrapsDriver wrapsDriver = (WrapsDriver) element;
		// 截图整个页面
		File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
		BufferedImage img = ImageIO.read(screen);
		// 获得元素的高度和宽度
		int width = element.getSize().getWidth();
		int height = element.getSize().getHeight();
		// 创建一个矩形使用上面的高度，和宽度
		// 得到元素的坐标
		Point p = element.getLocation();
		BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);
		// 存为png格式
		ImageIO.write(dest, "png", screen);
		return screen;
	}

	static boolean waitForElementPresent(WebDriver driver, final By by, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static boolean waitPageRefresh(WebDriver driver, int timeout) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.refreshed(null));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
