package com.seleniumsimplified.myTests;

import com.seleniumsimplified.webdriver.manager.Driver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

/**
 * Created by esalehi on 2015-09-23.
 */
public class SyncTest {

    static WebDriver driver;

    @Before
    public void setup(){
        driver = Driver.get("http://oxgcu.com/hotspur/2/?oz_ce=ggafk&c3=2015-10-8-es-01");
    }

    @Test
    public void syncTest() throws InterruptedException {
        Thread.sleep(200000);
        //driver.findElement(By.cssSelector("input#id")).sendKeys(Keys.chord("superadmin"));
        //driver.findElement(By.cssSelector("input#password")).sendKeys(Keys.chord("changeme"));

        //driver.findElement(By.cssSelector("select#combo2")).sendKeys("Java");
        //driver.findElement(By.cssSelector("div#login_form>form")).submit();
        //new WebDriverWait(driver,10).until(ExpectedConditions.titleIs("Dashboard : Home"));

        //assertEquals("Super",driver.findElement(By.cssSelector("div#welcome>div.expanded>h1>span.users-name")).getText());
    }
}
