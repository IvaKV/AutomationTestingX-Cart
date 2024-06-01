import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ContactUsTest extends BaseTest{
    String subject = "Product Information";
    String message = "I have a shipping problem with the product.";

    LogInTest loginFirst = new LogInTest();

    @Test
    public void logInFirst() {
        //Logging in first
        loginFirst.loggingInTest();
    }

    @Test
    public void verifyContactUsMessage() throws InterruptedException {
        //Clicking on "Contact Us"
        Thread.sleep(3000); //bez ova ne mi go naogjashe
        driver.findElement(By.partialLinkText("Contact")).click();

        //Typing in Subject of message
        driver.findElement(By.id("subject")).sendKeys(subject);

        //Typing in message
        driver.findElement(By.id("message")).sendKeys(message);

        //Clicking "send"
        Thread.sleep(3000);
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[3]/main/div/div[3]/div/div/div[3]/div[2]/div[1]/div/form/div[2]/div[2]/div/button")).click();

        //Verifying sent message
        Thread.sleep(2000);
        WebElement confirmationMessage = driver.findElement(By.id("status-messages"));

        Assert.assertTrue(confirmationMessage.isDisplayed());

    }
}
