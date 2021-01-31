import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;
import io.qameta.atlas.webdriver.extension.Param;

public interface DaterElement extends WebPage {

    @SuppressWarnings("rawtypes")
    @FindBy("//span[contains(text(), '{{ text }}')]")
    AtlasWebElement Button(@Param("text") String text);

    @SuppressWarnings("rawtypes")
    @FindBy("//a[contains(text(), '{{ text }}')]")
    AtlasWebElement buttonContinuation(@Param("text") String text);

    @SuppressWarnings("rawtypes")
    @FindBy("//input[starts-with(@id, '{{ text }}')]")
    AtlasWebElement fieldPhone(@Param("text") String text);

    @SuppressWarnings("rawtypes")
    @FindBy("//img[@alt= '{{ text }}']")
    AtlasWebElement elePageAccount(@Param("text") String text);

    @FindBy("//textarea[contains(@class, '{{ text }}')]")
    AtlasWebElement eleRedactor (@Param("text") String text);

    @FindBy("//div[contains(@class, \"user-name\")]")
    AtlasWebElement userName();

    @FindBy("//p[contains(@class, \"small-text\")]")
    AtlasWebElement errorText();

}
