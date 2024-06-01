import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class ChangeDetailsTest extends BaseTest{

    LogInTest login = new LogInTest();

    @Test
    public void logInFirst() {
        //Logging in first
        login.loggingInTest();
    }

    @Test
    public void verifyChangeCountry() throws InterruptedException {

        //Clicking the dropdown, selecting Canada, clicking "save"
        Thread.sleep(3000); //ova go staviv zatoa shto vo xml dokumentot ne uspevashe da go klikne dropdown kopcheto a inaku raboti bez ova vo ovaa klasa
        driver.findElement(By.className("header_bar-locale")).click();
        WebElement dropDownButton = driver.findElement(By.id("country_code_1"));
        Select dropDown = new Select(dropDownButton);
        dropDown.selectByValue("CA");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //Morav so full xPath, nikako ne mi go naogjashe
        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/header/div[1]/div/div[2]/div[2]/div/ul/li/form/button")).click();

        //Verify change of country
        String actualCountryResult = driver.findElement(By.className("country-indicator")).getText();
        String expectedCountryResult = "Canada";
        Assert.assertEquals(actualCountryResult, expectedCountryResult);
    }
}
