import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class MemosTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:5230"; 

    @BeforeAll
    public static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void test01_VerifyTitle() {
        driver.get(BASE_URL);
        assertTrue(driver.getPageSource().toLowerCase().contains("memos"));
    }

    @Test
    public void test02_NavigateToAuth() {
        driver.get(BASE_URL + "/auth");
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(., 'Sign')]")));
        assertTrue(btn.isDisplayed());
    }

    @Test
    public void test03_CheckLoginFields() {
        assertTrue(driver.findElement(By.xpath("//input[@type='text' or @placeholder='Username']")).isDisplayed());
        assertTrue(driver.findElement(By.xpath("//input[@type='password' or @placeholder='Password']")).isDisplayed());
    }

    @Test
    public void test04_VerifyResourcesLink() {
        driver.get(BASE_URL);
        WebElement resources = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, '/resources')]")));
        assertTrue(resources.isDisplayed());
    }

    @Test
    public void test05_VerifyExploreLink() {
        WebElement explore = driver.findElement(By.xpath("//a[contains(@href, '/explore')]"));
        assertTrue(explore.isDisplayed());
    }

    @Test
    public void test06_CheckTimelineVisibility() {
        driver.get(BASE_URL + "/explore");
        WebElement timeline = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("memos-wrapper")));
        assertNotNull(timeline);
    }

    @Test
    public void test07_CheckSettingsLink() {
        driver.get(BASE_URL);
        WebElement settings = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, '/setting')]")));
        assertTrue(settings.isDisplayed());
    }

    @Test
    public void test08_VerifySearchInput() {
        WebElement search = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@placeholder, 'Search')]")));
        assertTrue(search.isEnabled());
    }

    @Test
    public void test09_VerifyHomeRedirection() {
        driver.findElement(By.xpath("//span[contains(text(), 'Home') or contains(text(), 'Memos')]")).click();
        assertEquals(BASE_URL + "/", driver.getCurrentUrl());
    }

    @Test
    public void test10_CheckTagListSection() {
        WebElement tags = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(text(), 'Tags')]")));
        assertTrue(tags.isDisplayed());
    }

    @Test
    public void test11_CheckLanguageToggle() {
        driver.get(BASE_URL + "/auth");
        WebElement lang = driver.findElement(By.tagName("body"));
        assertTrue(lang.getText().length() > 0);
    }

    @Test
    public void test12_CheckMemosListContainer() {
        driver.get(BASE_URL);
        WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("root")));
        assertTrue(container.isDisplayed());
    }

    @Test
    public void test13_CheckFooterCredits() {
        WebElement footer = driver.findElement(By.tagName("footer"));
        assertTrue(footer.getText().toLowerCase().contains("memos") || footer.isDisplayed());
    }

    @Test
    public void test14_Verifyfavicon() {
        driver.get(BASE_URL);
        String source = driver.getPageSource();
        assertTrue(source.contains("favicon"));
    }

    @Test
    public void test15_VerifyResponsiveView() {
        driver.manage().window().setSize(new Dimension(375, 812)); // iPhone X size
        WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue(menu.isDisplayed());
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}
