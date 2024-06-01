import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;

public class CartTest extends BaseTest{

    LogInTest login = new LogInTest();
    Product product;

    @Test (priority = 1)
    public void logInFirst() {
        login.loggingInTest();
    }

    @Test (priority = 2)
    public void verifyAddItemToCart() throws InterruptedException {
        Thread.sleep(3000);

        //Searching for products and adding them to cart

        //Adding item from homepage
        WebElement itemHover = driver.findElement(By.cssSelector("ul.products-grid li:first-child"));
        Actions hoverElement = new Actions(driver).moveToElement(itemHover);
        hoverElement.perform();

        Thread.sleep(2000);

        //Adding to cart
        driver.findElement(By.cssSelector("button.product-add2cart")).click();
        //Closing window
        Thread.sleep(2000);
        driver.findElement(By.className("ui-dialog-titlebar-close")).click();

        //Vaka napraviv bidejki podole imam for-loop za search poleto a sakav da si vklucham eden item so hover
        driver.navigate().to("https://demostore.x-cart.com/");

        //Searching items
        product = new Product("body oil", "sandals");
        ArrayList<String> productsToCart = product.getProductsList();

        for (String products : productsToCart) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.findElement(By.name("substring")).sendKeys(products);
            driver.findElement(By.className("list-image")).click();
            driver.findElement(By.className("add2cart")).click();
            Thread.sleep(2000); //ova go staviv zatoa shto ne uspevashe da go iskluci prozorcheto shto se pojavuva posle add to cart
            driver.findElement(By.className("ui-dialog-titlebar-close")).click();
        }

        String expectedItemsInCart = "5";
        String actualItemsinCart = driver.findElement(By.className("minicart-items-number")).getText();
        Assert.assertEquals(actualItemsinCart, expectedItemsInCart);

    }

    @Test (priority = 3)
    public void verifyDeleteItemFromCart() throws InterruptedException {
        //Clicking the cart button
        driver.findElement(By.className("lc-minicart")).click();
        //Clicking view cart
        driver.findElement(By.className("cart")).click();
        Thread.sleep(2000);
        //Clicking the delete button
        driver.findElement(By.cssSelector("a.remove")).click();

        Thread.sleep(3000); //za da se pojavi porakata :)
        WebElement expectedMessage = driver.findElement(By.cssSelector("div#status-messages"));
        Assert.assertTrue(expectedMessage.isDisplayed());

        //Navigating back to homepage
        driver.navigate().to("https://demostore.x-cart.com/");
    }

    @Test (priority = 4)
    public void verifyCheckout() throws InterruptedException {
        //Clicking the cart button
        driver.findElement(By.className("lc-minicart")).click();

        //Clicking "Checkout"
        driver.findElement(By.className("checkout")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Filling in the fields
        driver.findElement(By.id("shippingaddress-firstname")).sendKeys("Testing");
        driver.findElement(By.id("shippingaddress-lastname")).sendKeys("Tested");
        driver.findElement(By.id("shippingaddress-address1")).sendKeys("Address12");

        //Selecting the state, zatoa shto Canada i United States imaat SELECT Drop Down
        Thread.sleep(2000);
        WebElement selectedStateDrop = driver.findElement(By.id("shippingaddress-country-code"));
        Select selectedState = new Select(selectedStateDrop);
        String shippingCountry = selectedState.getFirstSelectedOption().getText();

        if (shippingCountry.equals("Canada")) {
            WebElement selectState = driver.findElement(By.id("shippingaddress-state-id"));
            Select dropDownState = new Select(selectState);
            dropDownState.selectByValue("52");
        } else if (shippingCountry.equals("United States")) {
            WebElement selectState = driver.findElement(By.id("shippingaddress-state-id"));
            Select dropDownState = new Select(selectState);
            dropDownState.selectByValue("543");
        } else {
            driver.findElement(By.id("shippingaddress-custom-state")).sendKeys("Spain");
        }
        driver.findElement(By.id("shippingaddress-street")).sendKeys("Street no.2");

        //Clicking "Choose Shipping"
        Thread.sleep(2000); //stavav Threadovi poshto mu trebashe vreme za load i da gi najde
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div[4]/div[1]/div[2]/div[1]/div[2]/div[2]/div/div[2]/button")).click();

        //Choosing type of shipping - radio button
        driver.findElement(By.id("method5")).click();

        //Clicking proceed to payment
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div/div[2]/div[4]/div[1]/div[2]/div[1]/div[2]/div[2]/div/div[2]/button")).click();

        //Accepting terms and conditions checkbox
        Thread.sleep(2000);
        driver.findElement(By.id("tosconsent")).click();

        //Clicking "Place order"
        Thread.sleep(2000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/main/div/div[2]/div/div/div[2]/div[4]/div[1]/div[2]/div[1]/div[2]/div[2]/div/div[1]/form/div[3]/button")).click();

        //Verifying Order
        String expectedOrderMessage = "Thank you for your order";
        String actualOrderMessage = driver.findElement(By.id("page-title")).getText();
        Assert.assertEquals(actualOrderMessage, expectedOrderMessage);
    }
}
