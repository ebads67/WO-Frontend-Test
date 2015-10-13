package com.seleniumsimplified.myTests;

import com.seleniumsimplified.webdriver.manager.Driver;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by esalehi on 2015-09-22.
 */
public class InteractionExercisesTest {
    private static WebDriver driver;
    String defaultPageTitle;
    @BeforeClass
    public static void beforeClass(){
        driver = Driver.get();
    }
    @Before
    public void beforeTest(){
        driver.navigate().to("http://compendiumdev.co.uk/selenium/basic_html_form.html");
        defaultPageTitle=driver.getTitle();
    }
    @Test
    public void test1(){
        driver.findElement(By.cssSelector("input[value='submit']")).click();
        new WebDriverWait(driver,10).until(ExpectedConditions.not(ExpectedConditions.titleIs(defaultPageTitle)));
        assertThat(defaultPageTitle, is(not(driver.getTitle())));
    }

    @Test
    public void test2(){
        WebElement commTextArea = driver.findElement(By.cssSelector("textarea[name='comments']"));
        commTextArea.clear();
        String comment="This is a comment about the test!";
        commTextArea.sendKeys(comment);
        driver.findElement(By.cssSelector("input[value='submit']")).click();
        new WebDriverWait(driver,10).until(ExpectedConditions.not(ExpectedConditions.titleIs(defaultPageTitle)));
        assertThat(comment, is(driver.findElement(By.cssSelector("#_valuecomments")).getText()));

    }
    @Test
    public void test3(){
        driver.findElement(By.cssSelector("input[value='rd1']")).click();
        driver.findElement(By.cssSelector("input[value='submit']")).submit();
    }
    @Test
    public void testLast(){
        //driver.findElement(By.cssSelector("input[type='file']")).;

    }
}
