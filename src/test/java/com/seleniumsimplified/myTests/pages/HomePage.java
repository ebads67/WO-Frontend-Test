package com.seleniumsimplified.myTests.pages;

import com.seleniumsimplified.myTests.DashboardUser;
import org.openqa.selenium.support.ui.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.NoSuchElementException;

public class HomePage extends DashboardPage {

    public HomePage(WebDriver driver, DashboardUser user) {
        this.driver = driver;
        this.user = user;
        this.wait = new WebDriverWait(driver, 10);
    }

    @Override
    protected void load() {
        driver.get("http://localhost:8080/kepler-186f/");
        if (driver.findElements(By.cssSelector("body.showLogin")).size() > 0) {
            LoginPage loginPage = new LoginPage(driver);
            loginPage.submitLoginForm(user.getUsername(), user.getPassword());
            wait.until(userIsLoggedIn());
        }
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (!driver.findElement(By.cssSelector("div.homeView")).isDisplayed()) {
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            throw new Error("Login page not loaded");
        }
    }


}