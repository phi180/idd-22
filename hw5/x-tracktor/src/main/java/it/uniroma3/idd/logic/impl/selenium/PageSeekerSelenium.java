package it.uniroma3.idd.logic.impl.selenium;

import it.uniroma3.idd.logic.Seeker;
import it.uniroma3.idd.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PageSeekerSelenium implements Seeker {

    private static final int MAX_WAIT_DURATION = 5;
    private static final String HREF_ATTRIBUTE = "href";
    private static final String FIREFOX_PROFILE_NAME = "selenium_idd";

    private static final boolean JAVASCRIPT_ENABLED = false;

    public PageSeekerSelenium() {
        System.setProperty("webdriver.gecko.driver", Utils.getResourceFullPath(Utils.getDriversLocation()));
    }

    public List<String> getEntityUrls(String rootUrl, String nextPageButtonXPath, int nSteps, String entityLinksXPath) {
        List<String> urls = new ArrayList<>();

        FirefoxOptions options = new FirefoxOptions();
        //options.setHeadless(true);

        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile firefoxProfile = profile.getProfile(FIREFOX_PROFILE_NAME);
        options.setProfile(firefoxProfile);

        WebDriver driver = new FirefoxDriver(options);

        driver.manage().window().maximize();
        driver.get(rootUrl);

        for(int i = 0; i < nSteps; i++) {
            new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT_DURATION))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(entityLinksXPath)));

            List<WebElement> entityPagesUrls = driver.findElements(By.xpath(entityLinksXPath));

            entityPagesUrls.forEach(webElement -> urls.add(webElement.getAttribute(HREF_ATTRIBUTE)));

            this.clickNextButton(driver, nextPageButtonXPath);
        }

        driver.close();

        return urls;
    }

    /** private methods */

    private void clickNextButton(WebDriver driver, String nextPageButtonXPath) {
        new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT_DURATION))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(nextPageButtonXPath)));

        WebElement nextButtonElement = driver.findElement(By.xpath(nextPageButtonXPath));

        if(JAVASCRIPT_ENABLED) {
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", nextButtonElement);
        } else {
            nextButtonElement.click();
        }
    }

}
