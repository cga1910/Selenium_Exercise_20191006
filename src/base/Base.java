package base;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;

public class Base {

  public WebDriver driver;
  public Properties prop;
  public ExcelReader excelReader = new ExcelReader();

  @BeforeClass
  public void propertiesFileSetup() {
    FileReader fileReader;
    prop = new Properties();
    try {
      String path = System.getProperty("user.dir") + "\\resources\\test.properties";
      fileReader = new FileReader(path);
      prop.load(fileReader);
      System.out.println("The following file was loaded as a properties file:");
      System.out.println(path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @BeforeClass
  public void excelFileSetup() throws InvalidFormatException {
    excelReader.loadWorkbook();
  }

  public void openBrowser(String browserName) {
    driver = null;
    if (browserName.equalsIgnoreCase("firefox")) {
      System.setProperty("webdriver.gecko.driver", "c:\\libs\\Selenium\\geckodriver.exe");
      //String path = System.getProperty(FirefoxDriver.SystemProperty.BROWSER_BINARY);
      //System.out.println("Using browser binary: " + path);
      driver = new FirefoxDriver();
    } else {
      System.out.println("The specified browser is not supported by this test suite.");
      System.out.println("Please modify Base.java to support more browsers.");
    }
    System.out.println("Maximizing the window...");
    driver.manage().window().maximize();
    System.out.println("Configuring wait settings for the webdriver...");
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
  }

  // Methods below are for simplifying and making the testsuite code more readable

  public void navigate(String key) {
    String url = prop.getProperty(key); 
    System.out.println("Navigating to: " + url);
    driver.get(url);
  }

  public void click(WebElement element) {
    element.click();
  }

  public void closeBrowser() {
    System.out.println("Closing browser ... ");
    driver.quit();
  }

  public static long getTime() {
    return System.currentTimeMillis();
  }
}
