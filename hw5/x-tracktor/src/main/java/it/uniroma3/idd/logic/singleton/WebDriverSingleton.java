package it.uniroma3.idd.logic.singleton;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

public class WebDriverSingleton {
    private static final String FIREFOX_PROFILE_NAME = "selenium_idd";

    private static WebDriverSingleton instance = null;

    private WebDriverSingleton() {

    }

    public static WebDriverSingleton getInstance() {
        if(instance == null) {
            instance = new WebDriverSingleton();
            instance.init();
        }

        return instance;
    }

    public void close() {
        webDriver.close();
    }

    private void init() {
        FirefoxOptions options = new FirefoxOptions();
        //options.setHeadless(true);

        ProfilesIni profile = new ProfilesIni();
        FirefoxProfile firefoxProfile = profile.getProfile(FIREFOX_PROFILE_NAME);
        options.setProfile(firefoxProfile);

        webDriver = new FirefoxDriver(options);
        webDriver.manage().window().maximize();
    }

    @Getter
    private WebDriver webDriver;

}
