package COM.T56;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class CheckCodeTest {

	public static void main(String[] args) throws Exception {
		System.setProperty("phantomjs.binary.path", "D:/Programs/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		WebDriver driver = new PhantomJSDriver( );
		
		driver.get("http://10.8.9.49/CheckCode.aspx");
		System.out.println(driver.getPageSource());
		WebElement img = driver.findElement(By.tagName("img"));
		
		File yzm = captureElement(img);
		
		System.out.println(yzm.getAbsolutePath());
		driver.navigate().refresh();
		
		Thread.sleep(6000);
		
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

}