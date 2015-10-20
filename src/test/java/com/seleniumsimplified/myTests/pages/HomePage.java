package com.seleniumsimplified.myTests.pages;

import com.seleniumsimplified.myTests.DashboardUser;
import org.openqa.selenium.support.ui.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.NoSuchElementException;

public class HomePage extends LoadableComponent<HomePage> {

    WebDriver driver;
    WebDriverWait wait;
    DashboardUser user;

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

    public ExpectedCondition<Boolean> userIsLoggedIn() {
        return ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body.showLogin")));
    }

    public boolean isRightUserLoggedIn() {
        driver.findElement(By.cssSelector("a#user_info")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#user_nav ul.dropdown-menu")));
        System.out.println(driver.findElement(By.cssSelector("div#user_nav span.left span.name")).getText().trim());
        return (driver.findElement(By.cssSelector("div#user_nav span.left span.name")).getText().trim().toLowerCase().equals(user.getFirstName().toLowerCase()) &&
                driver.findElement(By.cssSelector("div#user_nav span.left span.company")).getText().trim().toLowerCase().equals(user.getCompany().toLowerCase()) &&
                driver.findElement(By.cssSelector("div#user_nav ul.dropdown-menu li:first-child div:first-child")).getText().trim().toLowerCase().equals((user.getFirstName() + " " + user.getLastName()).toLowerCase()) &&
                driver.findElement(By.cssSelector("div#user_nav ul.dropdown-menu li:first-child div:nth-child(2)")).getText().trim().toLowerCase().equals(user.getEmail().toLowerCase())
        );
    }
}