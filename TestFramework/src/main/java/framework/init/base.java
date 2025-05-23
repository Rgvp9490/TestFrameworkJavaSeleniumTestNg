package framework.init;

import framework.reader.jsonFileReader;
import framework.runner.TestRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import framework.performAction.autoweb.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.tinylog.Logger;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class base {

    public WebDriver driver;
    public Browser browser;
    jsonFileReader config = new jsonFileReader();

    public String env = "";
    public String platform = "";
    public String browserName = "";

    @BeforeMethod
    public WebDriver init() {
        try {
            if (TestRunner.currentConfig == null || TestRunner.currentConfig.isEmpty()) {
                TestRunner.currentConfig = config.getRunConfig(); // fallback if not set
            }

            String configName = TestRunner.currentConfig;
            platform = config.getPlatform(configName);
            Logger.info("Running on platform: " + platform + ", config: " + configName);

            switch (platform.toLowerCase()) {
                case "web":
                    driver = setupLocalBrowser(configName);
                    env = config.getEnv(configName);
                    browser = new Browser(driver);
                    browser.open_url(env);

                    if (config.isAPITestConfigEnable(configName)) {
                        RestAssured.baseURI = config.getAPIEnv(configName);
                        Logger.info("API baseURI set to: " + RestAssured.baseURI);
                    }
                    break;

                case "api":
                    RestAssured.baseURI = config.getAPIEnvDirect(configName);
                    Logger.info("API baseURI set to (direct): " + RestAssured.baseURI);
                    break;

                case "android":
                    setup_android(configName);
                    break;

                case "ios":
                    setup_iOS(configName);
                    break;

                default:
                    Logger.error("Unsupported platform: " + platform);
            }

        } catch (Exception e) {
            Logger.error("Exception in Base init(): " + e.getMessage());
        }

        return driver;
    }

    private WebDriver setupLocalBrowser(String configName) {
        browserName = config.getBrowser(configName);
        String downloadPath = Paths.get(System.getProperty("user.dir"), "src/test/java/downloadedFile").toString();

        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", downloadPath);
                chromeOptions.setExperimentalOption("prefs", prefs);
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver(chromeOptions);

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();

            default:
                throw new RuntimeException("Unsupported browser: " + browserName);
        }
    }

    private void setup_android(String configName) {
        Logger.info("Android setup not implemented yet.");
    }

    private void setup_iOS(String configName) {
        Logger.info("iOS setup not implemented yet.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            Logger.info("Browser closed successfully.");
        }
    }
}
