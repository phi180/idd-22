package it.uniroma3.idd.logic;

import it.uniroma3.idd.domain.ExtractedData;
import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorLogic {

    private static final int MAX_WAIT_DURATION = 5;
    private static final String FIREFOX_PROFILE_NAME = "selenium_idd";

    private WebDriver driver;

    public ExtractorLogic() {
        System.setProperty("webdriver.gecko.driver", Utils.getResourceFullPath(Utils.getDriversLocation()));
        this.initDriver();
    }

    public ExtractedLabeledData extractLabeledData(String url, Map<String,List<String>> label2xpath) {
        ExtractedLabeledData extractedLabeledData = new ExtractedLabeledData();
        extractedLabeledData.setUrl(url);

        driver.get(url);

        for(Map.Entry<String,List<String>> label2xpathEntry : label2xpath.entrySet()) {
            String label = label2xpathEntry.getKey();

            extractedLabeledData.getLabel2xpathData().putIfAbsent(label,new HashMap<>());
            for(String xpath: label2xpathEntry.getValue()) {
                try {
                    String result = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT_DURATION))
                            .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))).getText();
                    extractedLabeledData.getLabel2xpathData().get(label).put(xpath, result);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //driver.close();

        return extractedLabeledData;
    }

    // private methods

    private void initDriver() {
        FirefoxOptions options = new FirefoxOptions();
        //options.setHeadless(true);

        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile firefoxProfile = profile.getProfile(FIREFOX_PROFILE_NAME);
        options.setProfile(firefoxProfile);

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }


}
