package com.seleniumsimplified.myTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class LoginPage extends LoadableComponent<LoginPage> {

    WebDriver driver;
    static WebDriverWait wait;

    public LoginPage(WebDriver driver){this.driver=driver;}

    @Override
    protected void load() {
        driver.get("http://localhost:8080/kepler-186f/");
        if(driver.findElements(By.cssSelector("body.showLogin")).size()==0) {
            driver.findElement(By.cssSelector("#user_info")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i.icon-logout")));
            driver.findElement(By.cssSelector("i.icon-logout")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body.showLogin")));
        }
    }

    @Override
    protected void isLoaded() throws Error {
        try{
            driver.findElement(By.cssSelector("body.showLogin"));
        }catch (NoSuchElementException e){
            throw new Error("Login page not loaded");
        }
    }
    public String waitUntilNotificationPopsUpAndReturnIt(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body>div.alert")));
        return driver.findElement(By.cssSelector("body>div.alert>span.msg")).getText();
    }
    public void goToForgotPasswordAndWait(){
        driver.findElement(By.cssSelector("div.forgot-password>a")).click();
        WebElement emailInput = driver.findElement(By.cssSelector("#email"));
        wait.until(ExpectedConditions.visibilityOf(emailInput));
    }
    public void submitForgetPassword(String email){
        driver.findElement(By.cssSelector("#email")).sendKeys(email);
        driver.findElement(By.cssSelector("div.right-forgot>button.cta[type=submit]")).click();
    }
    public void cancelForgetPasswordAndWait(){
        driver.findElement(By.cssSelector("div.right-forgot>button[type=button]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form[name=userLoginForm]")));
    }

    public void submitLoginForm(String username,String password){
        driver.findElement(By.cssSelector("div#login_panel input#id")).sendKeys(username);
        driver.findElement(By.cssSelector("div#login_panel input#password")).sendKeys(password);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body>div.alert")));
        driver.findElement(By.cssSelector("div#login_panel button")).click();
    }
}
