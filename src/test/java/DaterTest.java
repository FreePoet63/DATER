import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import io.qameta.atlas.webdriver.WebPage;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItems;

public class DaterTest {

    FirefoxDriver driver;
    Atlas atlas;

    @BeforeMethod
    public void startFirefoxWebDriver() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        atlas = new Atlas(new WebDriverConfiguration(driver));
        onPage(DaterElement.class).open("https://dater3-staging--dater-tester-ttonhmdt.web.app/");
        Thread.sleep(15000);
        onPage(DaterElement.class).Button("Начать").click();
        onPage(DaterElement.class).buttonContinuation("Пропустить").click();
    }

    private <T extends WebPage> T onPage(Class<T> page) {
        return atlas.create(driver, page);
    }

    @Attachment(value = "screenshot", type = "image/PNG")
    public byte[] screensotPNG() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @Epic(value = "Веб - сайт DATER")
    @Feature(value = "Проверка логики работы и функциональности")
    @Story(value = "Aвторизация")
    @Test(priority = 1)
    @Description(value = "Ввод корректных данных в поля авторизации")
    public void validLogIn() throws InterruptedException, IOException {
        WebElement element = onPage(DaterElement.class).fieldPhone("dater");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=''", element);
        onPage(DaterElement.class).fieldPhone("dater").sendKeys("+79261009001");
        onPage(DaterElement.class).Button("Продолжить").click();
        List<WebElement> phoneElement = onPage(DaterCollection.class).phoneCode();
        for (WebElement ele : phoneElement) {
            ele.sendKeys("1");
        }
        Thread.sleep(15000);
        List<WebElement> elementList = onPage(DaterCollection.class).elementAccount();
        List<String> accountElement = elementList.stream().map(webElement -> webElement.getText()).collect(Collectors.toList());
        assertThat(accountElement, hasItems(containsString("Yury"), containsString("Монет"), containsString(
                "Рейтинг"), containsString("Встреч")));
        screensotPNG();
    }

    @Epic(value = "Веб - сайт DATER")
    @Feature(value = "Проверка логики работы и функциональности")
    @Story(value = "Aвторизация")
    @Test(priority = 2)
    @Description(value = "Ввод некорректных данных в поля авторизации")
    public void invalidLogin() throws IOException {
        WebElement element = onPage(DaterElement.class).fieldPhone("dater");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=''",element);
        onPage(DaterElement.class).fieldPhone("dater").sendKeys("+79261009001");
        onPage(DaterElement.class).Button("Продолжить").click();
        List<WebElement> phoneElement = onPage(DaterCollection.class).phoneCode();
        for (WebElement ele : phoneElement) {
            ele.sendKeys("2");
        }
        WebElement eleError = onPage(DaterElement.class).errorText();
        String textError = eleError.getText();
        Assert.assertEquals(textError, "Код не подходит. Проверь, нет ли ошибок в цифрах" +
                " и номере телефона или запроси новый код.");
        screensotPNG();
    }

    @Epic(value = "Веб - сайт DATER")
    @Feature(value = "Проверка логики работы и функциональности")
    @Story(value = "Редактирование профиля")
    @Test(priority = 3)
    @Description(value = "Редактирование имени на главной странице аккаунта")
    public void editingProfile() throws InterruptedException, IOException {
        WebElement element = onPage(DaterElement.class).fieldPhone("dater");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value=''",element);
        onPage(DaterElement.class).fieldPhone("dater").sendKeys("+79261009001");
        onPage(DaterElement.class).Button("Продолжить").click();
        List<WebElement> phoneElement = onPage(DaterCollection.class).phoneCode();
        for (WebElement ele : phoneElement) {
            ele.sendKeys("1");
        }
        Thread.sleep(15000);
        WebElement user = onPage(DaterElement.class).userName();
        String nameUser = user.getText();
        Assert.assertEquals(nameUser,"Yury");
        onPage(DaterElement.class).elePageAccount("Edit").click();
        Thread.sleep(3000);
        WebElement elem = onPage(DaterElement.class).eleRedactor("resizable");
        elem.sendKeys(Keys.CONTROL,"a");
        elem.sendKeys(Keys.ENTER);
        elem.sendKeys(Keys.BACK_SPACE);
        onPage(DaterElement.class).eleRedactor("resizable").sendKeys("Venya");
        onPage(DaterElement.class).Button("Сохранить").click();
        onPage(DaterCollection.class).closeRedactor().get(1).click();
        WebElement newUser = onPage(DaterElement.class).userName();
        String newNameUser =  newUser.getText();
        Assert.assertEquals(newNameUser,"Venya");
        screensotPNG();
    }

    @AfterMethod
    public void closedBrowser() {
        driver.quit();
    }


}
