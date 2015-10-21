package com.seleniumsimplified.myTests.pages;

import com.seleniumsimplified.myTests.DashboardUser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class DashboardPage extends LoadableComponent<DashboardPage> {
    AdminBar adminBar = new AdminBar();
    NavBar navBar = new NavBar();
    WebDriver driver;
    WebDriverWait wait;
    DashboardUser user;

    private class AdminBar {
        Boolean isItemAvailableInAdminBar(String item) {
            for (WebElement we : driver.findElements(By.cssSelector("#admin_bar>div.right-ctas li>a>span"))) {
                if (we.getText().trim().toLowerCase().equals(item.toLowerCase())) {
                    return true;
                }
            }
            return false;
        }
    }

    private class NavBar {
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

    public ExpectedCondition<Boolean> userIsLoggedIn() {
        return ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body.showLogin")));
    }

    public boolean isRightUserLoggedIn() {
        return navBar.isRightUserLoggedIn();
    }

}


