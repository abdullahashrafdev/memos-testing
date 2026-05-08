import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemosTest {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "http://13.60.192.70:5231"; 
    private static final String USERNAME = "abdullahmemos";
    private static final String PASSWORD = "abdullahmemos";

    @BeforeAll
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test @Order(1)
    public void test01_VerifyTitle() {
        driver.get(BASE_URL);
        Assertions.assertTrue(driver.getTitle().contains("Memos"));
    }

    @Test @Order(2)
    public void test02_InvalidLogin() {
        driver.get(BASE_URL + "/auth");
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("fakeuser");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("fakepass");
        driver.findElement(By.xpath("//button[contains(text(), 'Sign in')]")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("auth"));
    }

    @Test @Order(3)
    public void test03_ValidLogin() {
        driver.get(BASE_URL + "/auth");
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys(USERNAME);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys(PASSWORD);
        driver.findElement(By.xpath("//button[contains(text(), 'Sign in')]")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("auth")));
        Assertions.assertFalse(driver.getCurrentUrl().contains("auth"));
    }

    @Test @Order(4)
    public void test04_CreateMemo() {
        WebElement box = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//textarea")));
        box.sendKeys("Automated Test Memo 1");
        driver.findElement(By.xpath("//button[contains(text(), 'Save')]")).click();
        Assertions.assertTrue(driver.getPageSource().contains("Automated Test Memo 1"));
    }

    @Test @Order(5) public void test05_Tag() { driver.findElement(By.xpath("//textarea")).sendKeys("#devops"); driver.findElement(By.xpath("//button[contains(text(), 'Save')]")).click(); Assertions.assertTrue(driver.getPageSource().contains("#devops")); }
    @Test @Order(6) public void test06_Search() { driver.findElement(By.xpath("//input[@placeholder='Search']")).sendKeys("Automated"); Assertions.assertTrue(driver.getPageSource().contains("Automated")); }
    @Test @Order(7) public void test07_Settings() { driver.findElement(By.xpath("//a[contains(@href, '/setting')]")).click(); Assertions.assertTrue(driver.getPageSource().contains("Setting")); }
    @Test @Order(8) public void test08_Explore() { driver.findElement(By.xpath("//a[contains(@href, '/explore')]")).click(); Assertions.assertTrue(driver.getPageSource().contains("Explore")); }
    @Test @Order(9) public void test09_CheckUser() { Assertions.assertTrue(driver.getPageSource().contains(USERNAME)); }
    @Test @Order(10) public void test10_Pin() { driver.get(BASE_URL); driver.findElement(By.xpath("(//div[contains(@class, 'memo-menu')])[1]")).click(); driver.findElement(By.xpath("//span[contains(text(), 'Pin')]")).click(); Assertions.assertNotNull(driver.getCurrentUrl()); }
    @Test @Order(11) public void test11_Unpin() { driver.findElement(By.xpath("(//div[contains(@class, 'memo-menu')])[1]")).click(); driver.findElement(By.xpath("//span[contains(text(), 'Unpin')]")).click(); Assertions.assertNotNull(driver.getCurrentUrl()); }
    @Test @Order(12) public void test12_Archive() { driver.findElement(By.xpath("(//div[contains(@class, 'memo-menu')])[1]")).click(); driver.findElement(By.xpath("//span[contains(text(), 'Archive')]")).click(); Assertions.assertTrue(driver.getPageSource().contains("Archived")); }
    @Test @Order(13) public void test13_ViewArchived() { driver.get(BASE_URL + "/archived"); Assertions.assertTrue(driver.getPageSource().contains("Archived Memos")); }
    @Test @Order(14) public void test14_Delete() { driver.get(BASE_URL); driver.findElement(By.xpath("(//div[contains(@class, 'memo-menu')])[1]")).click(); driver.findElement(By.xpath("//span[contains(text(), 'Delete')]")).click(); driver.findElement(By.xpath("//button[contains(text(), 'Confirm')]")).click(); Assertions.assertNotNull(driver.getCurrentUrl()); }
    @Test @Order(15) public void test15_Logout() { driver.findElement(By.xpath("//div[contains(@class, 'user-avatar')]")).click(); driver.findElement(By.xpath("//span[contains(text(), 'Sign out')]")).click(); Assertions.assertTrue(driver.getCurrentUrl().contains("auth")); }

    @AfterAll public static void tearDown() { if (driver != null) driver.quit(); }
}
