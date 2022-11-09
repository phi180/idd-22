package it.uniroma3.idd.hw4.logic;

import it.uniroma3.idd.hw4.domain.ExtractedData;
import it.uniroma3.idd.hw4.domain.ExtractedLabeledData;
import it.uniroma3.idd.hw4.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ExtractorLogic {

    private static final Logger logger = Logger.getLogger(ExtractorLogic.class.toString());

    public ExtractorLogic() {
        System.setProperty("webdriver.gecko.driver", Utils.getPropertiesFullPath(Utils.getDriversLocation()));
    }

    public ExtractedData extractData(String url, List<String> xpaths) {
        logger.info("ExtractorLogic - extractLabeledData(): url=" + url + ", xpaths=" + xpaths);

        ExtractedData extractedData = new ExtractedData();
        extractedData.setUrl(url);

        WebDriver driver = new FirefoxDriver();
        driver.get(url);

        for(String xpathExpression : xpaths) {
            String result = driver.findElement(By.xpath(xpathExpression)).getText();
            extractedData.getXpath2data().put(xpathExpression, result);
        }

        driver.close();

        logger.info("ExtractorLogic - extractLabeledData(): extractedData=" + extractedData);

        return extractedData;
    }

    public ExtractedLabeledData extractLabeledData(String url, Map<String,List<String>> label2xpath) {
        logger.info("ExtractorLogic - extractLabeledData(): url=" + url + ", label2xpath=" + label2xpath);

        ExtractedLabeledData extractedLabeledData = new ExtractedLabeledData();
        extractedLabeledData.setUrl(url);

        WebDriver driver = new FirefoxDriver();
        driver.get(url);

        for(Map.Entry<String,List<String>> label2xpathEntry : label2xpath.entrySet()) {
            String label = label2xpathEntry.getKey();

            extractedLabeledData.getLabel2xpathData().putIfAbsent(label,new HashMap<>());
            for(String xpath: label2xpathEntry.getValue()) {
                String result = driver.findElement(By.xpath(xpath)).getText();
                extractedLabeledData.getLabel2xpathData().get(label).put(xpath, result);
            }
        }

        driver.close();

        logger.info("ExtractorLogic - extractLabeledData(): extractedLabeledData=" + extractedLabeledData);

        return extractedLabeledData;
    }


}
