package ui_automation.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import ui_automation.constants.Constants;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;

public class BrowserFactory {
    private static final Logger LOGGER = LogManager.getLogger(BrowserFactory.class);

    public static WebDriver createInstance() {

        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        LOGGER.info("Operating System: " + osName + ", Version: " + osVersion + ", Architecture: " + osArch);

        LOGGER.info("Creating a new WebDriver instance");

        WebDriver driver = null;
        try {

            String browserType = System.getProperty("browser");
            LOGGER.info("Requested browser type: " + (browserType == null ? "default (Chrome)" : browserType));

            if (System.getProperty("browser") == null) {
                WebDriverManager.chromedriver().setup();
                HashMap<String, Object> chromePrefs = new HashMap<>();
                String basePath = System.getProperty(Constants.USER_DIRECTORY);
                String dirPath = Paths.get(basePath, "src", "test", "resources", "test_data").toString();
                chromePrefs.put("download.default_directory", dirPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--ignore-ssl-errors=yes");
                options.addArguments("--ignore-certificate-errors");
                options.setExperimentalOption("prefs", chromePrefs);
                driver = new ChromeDriver(options);
                LOGGER.info("ChromeDriver setup complete with options: " + options.toString());
            } else {
                switch (System.getProperty("browser")) {
                    case "chrome-headless":
                        WebDriverManager.chromedriver().setup();
                        driver = new ChromeDriver(new ChromeOptions().setHeadless(true));
                        LOGGER.info("Setting up a headless ChromeDriver");
                        break;
                    case "chromeRemote":
                        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                        String basePath = System.getProperty("user.dir");
                        String filePath = Paths.get(basePath, "src", "test", "resources", "test_data").toString();
                        chromePrefs.put("download.default_directory", filePath);
                        ChromeOptions chrOptions = new ChromeOptions();
                        chrOptions.addArguments("--ignore-ssl-errors=yes");
                        chrOptions.addArguments("--ignore-certificate-errors");
                        chrOptions.setExperimentalOption("prefs", chromePrefs);
                        try {
                            driver = new RemoteWebDriver(new URL(ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.GRID_URL)), chrOptions);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        LOGGER.info("Setting up a remote ChromeDriver");
                        break;
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver();
                        break;
                    case "firefox-headless":
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true));
                        LOGGER.info("Setting up a headless FirefoxDriver");
                        break;
                    case "firefoxRemote":
                        FirefoxOptions firOptions = new FirefoxOptions();
                        try {
                            driver = new RemoteWebDriver(new URL(ConfigurationReader.getProperty(Constants.UI_CONFIG, Constants.GRID_URL)), firOptions);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        LOGGER.info("Setting up a remote FirefoxDriver");
                        break;
                    case "ie":
                        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                            throw new WebDriverException("Your operating system does not support the requested browser");
                        }
                        WebDriverManager.iedriver().setup();
                        driver = new InternetExplorerDriver();
                        LOGGER.info("Setting up IE Driver");
                        break;
                    case "edge":
                        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                            throw new WebDriverException("Your operating system does not support the requested browser");
                        }
                        WebDriverManager.edgedriver().setup();
                        driver = new EdgeDriver();
                        LOGGER.info("Setting up EdgeDriver");
                        break;
                    case "safari":
                        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                            throw new WebDriverException("Your operating system does not support the requested browser");
                        }
                        WebDriverManager.getInstance(SafariDriver.class).setup();
                        driver = new SafariDriver();
                        LOGGER.info("Setting up Safari Driver");
                        break;
                    default:
                        WebDriverManager.chromedriver().setup();
                        driver = new ChromeDriver();
                        LOGGER.info("Defaulting to ChromeDriver");
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error creating WebDriver instance: " + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
        LOGGER.info("WebDriver instance created successfully: " + driver);
        return driver;
    }
}