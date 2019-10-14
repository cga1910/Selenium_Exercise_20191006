package testSuite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;

public class Test_A extends Base {

  List<WebElement> elementList;
  ArrayList<String> pageTitleList = new ArrayList<String>();

  @BeforeClass
  public void init() {
    openBrowser("firefox");
  }

  // Navigate to the page and assert that the page title is correct
  @Test(priority = 1)
  public void test01() {
    navigate("home_URL");
    String pageTitle = driver.getTitle();
    Assert.assertEquals(pageTitle, prop.getProperty("expected_pageTitle")); // Hard assert
  }

  // Make sure every visible link in the main menu can be clicked, and collect all
  // page titles (to be checked in the next test)
  @Test(priority = 2)
  public void test02() throws InterruptedException {

    elementList = driver.findElements(By.xpath(prop.getProperty("xPath_menuBarLinks")));

    int counter = 0;
    for (WebElement e : elementList) {
      if (e.isDisplayed()) {
        System.out.println(e.getAttribute("href"));
        counter++;
      }
    }

    System.out.println("Number of displayed links: " + counter);
    // Only continue with the test if the number of links is correct (sometimes the
    // page shows an additional "home" link, which seems to happen randomly)
    Assert.assertEquals(counter, 9);

    int clickCounter = 0;
    for (int i = 0; i < elementList.size(); i++) {
      WebElement e = elementList.get(i);
      if (e.isDisplayed()) {
        System.out.println(clickCounter + ". Clicking: " + e.getAttribute("href"));
        click(e);
        clickCounter++;
        pageTitleList.add(driver.getTitle());
        elementList = driver.findElements(By.xpath(prop.getProperty("xPath_menuBarLinks")));
      } else {
        System.out.println("Element is not displayed - will not click: " + e.getAttribute("href"));
      }
    }

    for (String s : pageTitleList) {
      System.out.println(s);
    }
  }

  int index = 0;

  // Assert that all page titles are correct
  // The test will run one time for each row of the provided array
  @Test(priority = 3, dataProvider = "getData", dependsOnMethods = "test02")
  public void test03(Object expectedPageTitle) {
    Assert.assertEquals(pageTitleList.get(index), expectedPageTitle);
    index++;
  }

  @DataProvider
  public Object[] getData() {
    Sheet sheet = excelReader.getSheet(0);
    Object[] data = new Object[9];
    // Fill the data array with strings from the Excel file
    for (int i = 0; i < data.length; i++) {
      data[i] = excelReader.getCellValue_as_String(sheet, i, 0);
    }
    //data[0] = excelReader.getCellValue_as_String(sheet, 0, 0);
    //data[1] = excelReader.getCellValue_as_String(sheet, 1, 0);
    //data[2] = excelReader.getCellValue_as_String(sheet, 2, 0);
    //data[3] = excelReader.getCellValue_as_String(sheet, 3, 0);
    //data[4] = excelReader.getCellValue_as_String(sheet, 4, 0);
    //data[5] = excelReader.getCellValue_as_String(sheet, 5, 0);
    //data[6] = excelReader.getCellValue_as_String(sheet, 6, 0);
    //data[7] = excelReader.getCellValue_as_String(sheet, 7, 0);
    //data[8] = excelReader.getCellValue_as_String(sheet, 8, 0);
    return data;
  }

//@DataProvider
// public Object[] getData() {
//  Object[] data = new Object[9]; // Get these strings from data.xlsx with POI
//  data[0] = "New Golf Clubs | New Golf Shoes, Bags & Clothing | American Golf";
//  data[1] = "Golf Clubs | Golf Clubs For Sale Online | American Golf";
//  data[2] = "Golf Clothing | Golf Clothes | Men's, Women's, Kids Golf Clothing | American Golf";
//  data[3] = "Golf Accessories | Golf Balls, Gloves & Tees | American Golf";
//  data[4] = "Golf Equipment | Golf GPS, Bags & Trolleys | American Golf";
//  data[5] = "Ladies Golf Clubs | Ladies Golf Clothes & Shoes | American Golf";
//  data[6] = "Golf Brands at American Golf";
//  data[7] = "Golf Sale | Golf Clubs, Shoes & Clothing Sale | American Golf";
//  data[8] = "AG Members Club";
//  return data;
// }

  @AfterClass
  public void finish() throws InterruptedException {
    System.out.println("Running AfterClass method: ");
    try {
      System.out.println("Closing the workbook ... ");
      excelReader.workbook.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    closeBrowser();
  }

}
