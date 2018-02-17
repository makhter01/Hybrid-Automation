package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CommonAPI {
    public WebDriver driver = null;
    public String browserStack_username = null;
    public String browserStack_accessKey = null;
    public String saucelabs_userName = null;
    public String saucelabs_accessKey = null;

       @Parameters({"usecloudEnv","cloudEnvName","OS","OS_version","browserName","browser-version","url"})
       @BeforeMethod
    public void setUp(@Optional("false") boolean usecloudEnv,@Optional("false") String cloudEnvName,@Optional("OS X") String OS,@Optional("10") String OS_version,@Optional("firefox") String browserName,@Optional("34") String browser_version,@Optional("www.amazon.com") String url)throws IOException{
        if(usecloudEnv==true){
            if(cloudEnvName.equalsIgnoreCase("browserstack")){
                getCloudDriver(cloudEnvName,browserStack_username,browserStack_accessKey,OS,OS_version,browserName,browser_version);
                }
            else if (cloudEnvName.equalsIgnoreCase("saucelabs")){
                getCloudDriver(cloudEnvName,saucelabs_userName, saucelabs_accessKey,OS,OS_version, browserName, browser_version);
            }
        } else {
            getLocalDriver(OS, browserName);
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(35, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();
    }

    public WebDriver getCloudDriver(String envName, String envUsername, String env_AccessKey,String os, String os_version, String browserName,String browser_version) throws IOException {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("os", os);
        cap.setCapability("os_version", os_version);
        cap.setCapability("browserName", browserName);
        cap.setCapability("browser_version", browser_version);
        if (envName.equalsIgnoreCase("saucelabs")) {
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + env_AccessKey +
                    "@ondemand.saucelabs.com:80/wd/hub"), cap);
        } else if (envName.equalsIgnoreCase("Browserstack")) {
            cap.setCapability("resolution", "1024x768");
            driver = new RemoteWebDriver(new URL("http://" + envUsername + ":" + env_AccessKey +
                    "@hub-cloud.browserstack.com/wd/hub"), cap);
        }
        return driver;

    }

    public WebDriver getLocalDriver(@Optional("mac") String os, String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.chrome.driver", "../Generic/driver/chromedriver");
            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.chrome.driver", "../driver/chromedriver.exe");
            }
            driver = new ChromeDriver();
        }
        if (browserName.equalsIgnoreCase("firefox")) {
            if (os.equalsIgnoreCase("mac")) {
                System.setProperty("webdriver.gecko.driver", "../driver/geckodriver");
            } else if (os.equalsIgnoreCase("windows")) {
                System.setProperty("webdriver.gecko.driver", "../driver.geckodriver.exe");
            }
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")) {
            System.setProperty("webdriver.ie.driver", "driver/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        }
        return driver;

    }

    @AfterMethod

    public void cleanUp(){
           driver.quit();
    }



    }



