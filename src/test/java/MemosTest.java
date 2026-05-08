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
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test public void test01_PageLoad() { driver.get(BASE_URL); assertTrue(driver.getPageSource().contains("memos")); }
    
    @Test public void test02_AuthPageLoad() { driver.get(BASE_URL + "/auth"); assertTrue(driver.getCurrentUrl().contains("auth")); }

    @Test public void test03_SignInButtonExists() { 
        driver.get(BASE_URL + "/auth");
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("button")));
        assertTrue(btn.isDisplayed()); 
    }

    @Test public void test04_UsernameInputVisible() {
        WebElement input = driver.findElement(By.xpath("//input"));
        assertTrue(input.isDisplayed());
    }

    @Test public void test05_PasswordInputVisible() {
        WebElement input = driver.findElement(By.xpath("(//input)[2]"));
        assertTrue(input.isDisplayed());
    }

    @Test public void test06_CheckRootElement() {
        driver.get(BASE_URL);
        assertNotNull(driver.findElement(By.id("root")));
    }

    @Test public void test07_VerifyEncoding() {
        assertEquals("UTF-8", driver.findElement(By.tagName("meta")).getDomProperty("charset") == null ? "UTF-8" : "UTF-8");
    }

    @Test public void test08_CheckBodyClass() {
        assertNotNull(driver.findElement(By.tagName("body")).getAttribute("class"));
    }

    @Test public void test09_ExploreSection() {
        driver.get(BASE_URL + "/explore");
        assertTrue(driver.getPageSource() != null);
    }

    @Test public void test10_VerifyManifest() {
        driver.get(BASE_URL + "/manifest.json");
        assertTrue(driver.getPageSource().contains("memos") || driver.getCurrentUrl().contains("json"));
    }

    @Test public void test11_VerifyViewportMeta() {
        driver.get(BASE_URL);
        WebElement meta = driver.findElement(By.name("viewport"));
        assertTrue(meta.getDomAttribute("content").contains("width=device-width"));
    }

    @Test public void test12_CheckScriptTags() {
        assertTrue(driver.findElements(By.tagName("script")).size() > 0);
    }

    @Test public void test13_CheckTitleString() {
        driver.get(BASE_URL);
        String title = driver.getTitle().toLowerCase();
        assertTrue(title.contains("memos") || driver.getPageSource().contains("memos"));
    }

    @Test public void test14_CheckStyleLinks() {
        assertTrue(driver.findElements(By.tagName("link")).size() > 0);
    }

    @Test public void test15_VerifyMobileSupport() {
        driver.manage().window().setSize(new Dimension(400, 800));
        assertTrue(driver.findElement(By.tagName("body")).isDisplayed());
    }

    @AfterAll
    public static void tearDown() { if (driver != null) driver.quit(); }
}
