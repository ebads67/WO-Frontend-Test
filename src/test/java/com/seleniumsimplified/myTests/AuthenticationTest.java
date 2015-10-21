package com.seleniumsimplified.myTests;

import com.seleniumsimplified.myTests.pages.HomePage;
import com.seleniumsimplified.myTests.pages.LoginPage;
import com.seleniumsimplified.webdriver.manager.Driver;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationTest {
    private static WebDriver driver;
    private static Driver.BrowserName browserName ;//= Driver.BrowserName.GOOGLECHROME;
    private LoginPage loginPage;

    private static DashboardUser superadmin;
    private static DashboardUser user;

    private static String AuthUrl = "http://localhost:8080/kepler-186f/";

    @BeforeClass
    public static void createDriverAndVisitAuthPage() {
        readCredentialsFromFile();
    }

    private static void readCredentialsFromFile() {
        /*
        Write the credentials in "credentials.txt" in four lines.
        admin username
        admin password
        username
        password
        */
        superadmin = new DashboardUser();
        String fileName = "credentials.txt";
        String line;
        try {
            FileReader fileReader =
                    new FileReader(fileName);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            if ((line = bufferedReader.readLine()) != null) {
                superadmin.setUsername(line);
                superadmin.setEmail(line + "@whiteops.com");
                superadmin.setCompany("White Ops");
                superadmin.setFirstName("Super");
                superadmin.setLastName("Admin");
            }
            if ((line = bufferedReader.readLine()) != null) {
                superadmin.setPassword(line);

            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    @BeforeClass
    public static void setupTestSuite() {
        if (browserName == Driver.BrowserName.GOOGLECHROME) {
            //System.out.println(FirefoxDriver.);
            /*ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("start-maximized");
            chromeOptions.setBinary("/Applications/Google\\ Chrome.app/Contents/MacOS/Google\\ Chrome");*/
            Driver.set(Driver.BrowserName.GOOGLECHROME);
        }
        driver=Driver.get();
    }

    @Before
    public void setupTest() {
        //driver = Driver.get();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        loginPage.get();
    }

    @Test
    public void wrongUsername() {
        loginPage.submitLoginForm("aWrongUsernameForTest!", "aWrongPasswordForTest!");
        String notificationMessage = loginPage.waitUntilNotificationPopsUpAndReturnIt();
        assertTrue("Alert for wrong credentials.", notificationMessage.contains("is incorrect."));
    }

    @Test
    public void cancelForgotPassword() {
        loginPage.goToForgotPasswordAndWait();
        loginPage.cancelForgetPasswordAndWait();
    }

    @Test
    public void forgotPasswordOnWrongEmailAddress() {
        loginPage.goToForgotPasswordAndWait();
        loginPage.waitUntilNotificationMessageDisappears();
        loginPage.submitForgetPassword("aWrongEmailAddress@aWrongDomain.xyz");
        String msg = loginPage.waitUntilNotificationPopsUpAndReturnIt();
        assertTrue("Alert for wrong email address.", msg.contains("Please check that your e-mail address is correct."));
    }

    @Test
    public void forgetPasswordOnCorrectEmailAddress() {
        loginPage.goToForgotPasswordAndWait();
        loginPage.waitUntilNotificationMessageDisappears();
        loginPage.submitForgetPassword(superadmin.getEmail());
        String msg = loginPage.waitUntilNotificationPopsUpAndReturnIt();
        assertTrue("Alert for sending an reset password to user's email.", msg.toLowerCase().contains("thank you.") && msg.toLowerCase().contains(superadmin.getEmail()));
    }

    @Test
    public void superAdminLogin() throws InterruptedException {
        loginPage.submitLoginForm(superadmin.getUsername(), superadmin.getPassword());
        assertFalse("No longer in login page.", loginPage.isLoginPageLoaded());
        HomePage homePage = new HomePage(driver, superadmin);
        assertTrue("The correct user is logged in.", homePage.isRightUserLoggedIn());
    }
}