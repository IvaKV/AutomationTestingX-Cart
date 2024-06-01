import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest{
    Credentials credentials = new Credentials();

    @Test (priority = 1)
    public void verifyRegistration() throws InterruptedException {

        //Click on Sign in
        driver.findElement(By.className("header_bar-sign_in")).click();

        //Click on "Create"
        driver.findElement(By.partialLinkText("Create")).click();

        //Filling in the form and clicking Submit
        driver.findElement(By.id("login")).sendKeys(credentials.getEmail());
        driver.findElement(By.id("password")).sendKeys(credentials.getPassword());
        driver.findElement(By.id("password-conf")).sendKeys(credentials.getPassword());
        driver.findElement(By.className("model-form-buttons")).click();

        //Closing message
        Thread.sleep(2000);
        driver.findElement(By.className("close")).click();

        //Clicking the drop down and selecting "My Account"
        driver.findElement(By.cssSelector("div.header_bar-my_account")).click();
        driver.findElement(By.cssSelector("ul.account-links li:first-child")).click();

        //Verifying successful registration
        Thread.sleep(3000);
        String expectedEmail = credentials.getEmail();
        String actualEmail = driver.findElement(By.id("login")).getAttribute("value");
        Assert.assertEquals(actualEmail, expectedEmail);

        //Clicking Log Out
        driver.findElement(By.cssSelector("div.header_bar-my_account")).click();
        driver.findElement(By.cssSelector("ul.account-links li:last-child")).click();
    }

    @Test (priority = 2)
    public void verifyDuplicateUserRegistrationFailure() {

        //Click on Sign in
        driver.findElement(By.className("header_bar-sign_in")).click();

        //Click on "Create"
        driver.findElement(By.partialLinkText("Create")).click();

        //Filling in the form and clicking Submit
        driver.findElement(By.id("login")).sendKeys(credentials.getEmail());
        driver.findElement(By.id("password")).sendKeys(credentials.getPassword());
        driver.findElement(By.id("password-conf")).sendKeys(credentials.getPassword());
        driver.findElement(By.className("model-form-buttons")).click();

        //Verifying error message for already registered account
        WebElement errorMessage = driver.findElement(By.className("error"));
        Assert.assertTrue(errorMessage.isDisplayed());

        //Closing registration window
        driver.findElement(By.className("ui-dialog-titlebar-close")).click();
    }
}
