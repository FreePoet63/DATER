import io.qameta.atlas.webdriver.ElementsCollection;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import org.openqa.selenium.WebElement;

public interface DaterCollection extends WebPage {

    @FindBy("//input[@type=\"tel\"]")
    ElementsCollection<WebElement> phoneCode();

    @FindBy("//div[contains(@class, \"dater-user\")]")
    ElementsCollection<WebElement> elementAccount();

    @FindBy("//a[starts-with(@class, \"icon\")]")
    ElementsCollection<WebElement> closeRedactor();
}
