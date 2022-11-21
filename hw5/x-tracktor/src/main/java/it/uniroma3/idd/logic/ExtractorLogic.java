package it.uniroma3.idd.logic;

import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.logic.singleton.WebDriverSingleton;
import it.uniroma3.idd.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorLogic {

    private static final int MAX_WAIT_DURATION = 1;

    public ExtractorLogic() {
        System.setProperty("webdriver.gecko.driver", Utils.getResourceFullPath(Utils.getDriversLocation()));
    }

    public ExtractedLabeledData extractLabeledData(String url, Map<String,List<String>> label2xpath) {
        ExtractedLabeledData extractedLabeledData = new ExtractedLabeledData();
        extractedLabeledData.setUrl(url);

        WebDriver driver = WebDriverSingleton.getInstance().getWebDriver();
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

        return extractedLabeledData;
    }


}
