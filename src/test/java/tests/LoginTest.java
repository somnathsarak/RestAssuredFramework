package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void validLogin_shouldSucceed() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open("https://example.com/login");
        loginPage.login("demoUser", "demoPass");

        // Simple assertion placeholder
        Assert.assertTrue(driver.getTitle().contains("Dashboard"),
                "Expected to be on dashboard after login");
    }
}
