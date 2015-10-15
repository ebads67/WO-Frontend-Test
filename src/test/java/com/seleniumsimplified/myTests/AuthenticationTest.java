package com.seleniumsimplified.myTests;

import com.seleniumsimplified.webdriver.manager.Driver;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AuthenticationTest {
    static WebDriver driver;
    static WebDriverWait wait;

    private static String adminUsername;
    private static String adminPassword;
    private static String username;
    private static String password;
    private static String AuthUrl="http://localhost:8080/kepler-186f/#/company/837492/home";


    @BeforeClass
    public static void createDriverAndVisitAuthPage(){
        driver=Driver.get(AuthUrl);
        readCredentialsFromFile();
        wait = new WebDriverWait(driver,10);
    }

    private static void readCredentialsFromFile(){
        //Write the credentials in "credentials.txt" in four lines.
        //admin username
        //admin password
        //username
        //password
        String fileName = "credentials.txt";
        String line;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if((line = bufferedReader.readLine()) != null) {
                adminUsername=line;
            }
            if((line = bufferedReader.readLine()) != null) {
                adminPassword=line;
            }
            if((line = bufferedReader.readLine()) != null) {
                username=line;
            }
            if((line = bufferedReader.readLine()) != null) {
                password=line;
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

/*    @Test
    public void isLoginPageLoaded(){

    }*/
    @Before
    public void beforeTests(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body.showLogin")));
    }
    @Test
    public void wrongUsername(){
        WebElement usernameInput=driver.findElement(By.cssSelector("div#login_panel input#id"));
        WebElement passwordInput=driver.findElement(By.cssSelector("div#login_panel input#password"));
        usernameInput.sendKeys("aWrongUsernameForTest!");
        passwordInput.sendKeys("aWrongPasswordForTest!");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body>div.alert")));
        driver.findElement(By.cssSelector("div#login_panel button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body>div.alert")));
        WebElement alertSpan = driver.findElement(By.cssSelector("body>div.alert>span.msg"));
        System.out.print(alertSpan.getText());
        assertTrue("Alert for wrong credentials.", alertSpan.getText().contains("is incorrect."));
    }
    @Test
    public void forgotPasswordProcedure(){
        driver.findElement(By.cssSelector("div.forgot-password>a")).click();
        WebElement emailInput = driver.findElement(By.cssSelector("#email"));
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys("aWrongEmail@aWrongDomain.xyz");
        WebElement submitButton = driver.findElement(By.cssSelector("div.right-forgot>button.cta[type=submit]"));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body>div.alert")));
        submitButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body>div.alert")));
        WebElement alertSpans = driver.findElement(By.cssSelector("body>div.alert>span.msg"));
        assertTrue("Alert for wrong email address.", alertSpans.getText().contains("Please check that your e-mail address is correct."));
        emailInput.clear();
        emailInput.sendKeys(adminUsername + "@whiteops.com");
        submitButton.click();
        //Check if the email is sent.
    }
    @Test
    public void unauthorizedAccess(){
        WebElement userFullName = driver.findElement(By.cssSelector("div#user_nav ul.dropdown-menu>li.section:first-child>div:first-child"));
        assertTrue("",userFullName.getText().trim().equals(""));
    }

    @After
    public void afterTests(){
        if(driver.findElements(By.cssSelector("div#login_panel input#id")).size()==0) {
            WebElement logoutButton = driver.findElement(By.cssSelector("i.icon-logout"));
            logoutButton.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body.showLogin")));
        }
        driver.navigate().refresh();

    }
}
