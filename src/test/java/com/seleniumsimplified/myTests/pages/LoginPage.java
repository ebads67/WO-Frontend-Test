package com.seleniumsimplified.myTests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.InterruptedException;


public class LoginPage extends LoadableComponent<LoginPage> {

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    @Override
    protected void load() {
        driver.get("http://localhost:8080/kepler-186f/");
        if (driver.findElements(By.cssSelector("body.showLogin")).size() == 0) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.findElement(By.cssSelector("#user_info")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("i.icon-logout")));
            driver.findElement(By.cssSelector("i.icon-logout")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body.showLogin")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#login_panel input#id")));
        }
    }

    @Override
    protected void isLoaded() {
        try {
            WebElement loginForm = driver.findElement(By.cssSelector("body.showLogin"));
            WebElement usernameInputField = driver.findElement(By.cssSelector("div#login_panel input#id"));
            if (!(loginForm.isDisplayed() && usernameInputField.isDisplayed())) {
                throw new NoSuchElementException("Login page not loaded");
            }
        } catch (NoSuchElementException e) {
            throw new Error("Login page not loaded");
        }
    }

    public Boolean isLoginPageLoaded() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body.showLogin")));
            return false;
        } catch (TimeoutException e) {
            WebElement usernameInputField = driver.findElement(By.cssSelector("div#login_panel input#id"));
            if (usernameInputField.isDisplayed()) {
                return true;
            } else {// In forgot password.
                return false;
            }
        }
    }

    public void waitUntilNotificationMessageDisappears() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body>div.alert")));
    }

    public String waitUntilNotificationPopsUpAndReturnIt() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body>div.alert")));
        return driver.findElement(By.cssSelector("body>div.alert>span.msg")).getText();
    }

    public void goToForgotPasswordAndWait() {
        driver.findElement(By.cssSelector("div.forgot-password>a")).click();
        WebElement emailInput = driver.findElement(By.cssSelector("#email"));
        wait.until(ExpectedConditions.visibilityOf(emailInput));
    }

    public void submitForgetPassword(String email) {
        WebElement emailInput = driver.findElement(By.cssSelector("#email"));
        emailInput.clear();
        emailInput.sendKeys(email);
        driver.findElement(By.cssSelector("div.right-forgot>button.cta[type=submit]")).click();
    }

    public void cancelForgetPasswordAndWait() {
        driver.findElement(By.cssSelector("div.right-forgot>button[type=button]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("form[name=userLoginForm]")));
    }

    public void submitLoginForm(String username, String password) {
        driver.findElement(By.cssSelector("div#login_panel input#id")).sendKeys(username);
        driver.findElement(By.cssSelector("div#login_panel input#password")).sendKeys(password);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body>div.alert")));
        driver.findElement(By.cssSelector("div#login_panel button")).click();
    }
}