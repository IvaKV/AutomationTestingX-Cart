import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogInTest extends BaseTest {

    Credentials credentials = new Credentials();

    @Test (priority = 2)
    public void loggingInTest()  {

        //Clicking Sign In
        driver.findElement(By.className("header_bar-sign_in")).click();

        //Filling in the input fields and clicking "sign in"
        driver.findElement(By.id("login-email")).clear();
        driver.findElement(By.id("login-email")).sendKeys(credentials.getEmail());
        driver.findElement(By.id("login-password")).sendKeys(credentials.getPassword());
        driver.findElement(By.cssSelector("table.login-form tr:nth-of-type(3)")).click();
    }

    @Test (priority = 3)
    public void verifyLogIn() throws InterruptedException {
        //Confirming Sign in
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("div.header_bar-my_account")).click();
        driver.findElement(By.cssSelector("ul.account-links li:first-child")).click();

        String expectedEmail = credentials.getEmail();
        String actualEmail = driver.findElement(By.id("login")).getAttribute("value");
        Assert.assertEquals(actualEmail, expectedEmail);

        //Navigating to homepage  ==> ova mi e za end2end testot da se vrati na homepage
        driver.findElement(By.className("company-logo")).click();
    }

    @Test (priority = 1)
    public void verifyUnsuccessfulLogin()  {
        //Clicking Sign In
        driver.findElement(By.className("header_bar-sign_in")).click();

        //Filling in the input fields and clicking "sign in"
        driver.findElement(By.id("login-email")).sendKeys("invalidusername@email.com");
        driver.findElement(By.id("login-password")).sendKeys("passwordss");
        driver.findElement(By.cssSelector("table.login-form tr:nth-of-type(3)")).click();

        //Closing window
        driver.findElement(By.className("ui-dialog-titlebar-close")).click();
        driver.navigate().refresh(); // ova go staviv zoshto mi frlashe error kaj verifyLog in
    }
}
