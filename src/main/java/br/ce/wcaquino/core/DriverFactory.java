package br.ce.wcaquino.core;

import br.ce.wcaquino.core.Propriedades.TipoExecucao;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
	
//	private static WebDriver driver;
	private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>(){
		@Override
		protected synchronized WebDriver initialValue(){
			return initDriver();
		}
};
	
	private DriverFactory() {}

	public static WebDriver getDriver(){
		return threadDriver.get();
	}

	public static WebDriver initDriver(){
		WebDriver driver = null;
		if (Propriedades.TIPO_EXECUCAO == TipoExecucao.LOCAL) {

			if (driver == null) {
				switch (Propriedades.BROWSER) {
					case FIREFOX:
						System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\geckodriver.exe");
						driver = new FirefoxDriver();
						break;
					case CHROME:
						System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
						driver = new ChromeDriver();
						break;
					case IE:
						System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\IEDriverServer.exe");
						driver = new InternetExplorerDriver();
						break;
				}

			}
		}

		if (Propriedades.TIPO_EXECUCAO == TipoExecucao.GRID){
			DesiredCapabilities cap = null;
			switch (Propriedades.BROWSER){
				case FIREFOX:
					System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\geckodriver.exe");
					cap = DesiredCapabilities.firefox();
					break;
				case CHROME:
					System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
					cap = DesiredCapabilities.chrome();
					break;
				case IE:
					System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\IEDriverServer.exe");
					cap = DesiredCapabilities.internetExplorer();
					break;
			}
			try {
				driver = new RemoteWebDriver(new URL("http://192.168.0.184:4444/wd/hub"),cap);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

		if (Propriedades.TIPO_EXECUCAO == TipoExecucao.NUVEM){
			DesiredCapabilities cap = null;
			switch (Propriedades.BROWSER){
				case FIREFOX:
					System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\geckodriver.exe");
					cap = DesiredCapabilities.firefox();
					break;
				case CHROME:
					System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
					cap = DesiredCapabilities.chrome();
					break;
				case IE:
					System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\IEDriverServer.exe");
					cap = DesiredCapabilities.internetExplorer();
					cap.setCapability("platform", "Windows 7");
					cap.setCapability("version", "11.0");
					break;
			}
			try {
				driver = new RemoteWebDriver(new URL("http://andre.mendonca:3f31b311-c613-4761-b85e-102ed77b4a3f@ondemand.saucelabs.com:80/wd/hub"),cap);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}

		driver.manage().window().setSize(new Dimension(1200, 765));
		return driver;
	}





	public static void killDriver(){
		WebDriver driver = getDriver();
		if(driver != null) {
			driver.quit();
			driver = null;
		}
		if (threadDriver != null){
			threadDriver.remove();
		}
	}
}
