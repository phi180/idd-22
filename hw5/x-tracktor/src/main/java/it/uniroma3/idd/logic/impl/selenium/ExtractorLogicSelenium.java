package it.uniroma3.idd.logic.impl.selenium;

import it.uniroma3.idd.domain.ExtractedLabeledData;
import it.uniroma3.idd.domain.ExtractedRow;
import it.uniroma3.idd.domain.ExtractedTable;
import it.uniroma3.idd.logic.Extractor;
import it.uniroma3.idd.logic.singleton.WebDriverSingleton;
import it.uniroma3.idd.utils.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorLogicSelenium implements Extractor {

    private static final int MAX_WAIT_DURATION = 1;
    private final List<String> clickXPaths;

    public ExtractorLogicSelenium(List<String> clickXPaths) {
        System.setProperty("webdriver.gecko.driver", Utils.getResourceFullPath(Utils.getDriversLocation()));
        this.clickXPaths = clickXPaths;
    }

    public ExtractedLabeledData extractLabeledData(String url, Map<String,List<String>> label2xpath) {
        ExtractedLabeledData extractedLabeledData = new ExtractedLabeledData();
        extractedLabeledData.setUrl(url);

        WebDriver driver = WebDriverSingleton.getInstance().getWebDriver();
        driver.get(url);

        this.clickXpaths(driver, clickXPaths);

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

    public ExtractedTable getTableFromPage(String url, String tableXPath) {
        ExtractedTable extractedTable = new ExtractedTable();

        WebDriver driver = WebDriverSingleton.getInstance().getWebDriver();
        driver.get(url);

        this.clickXpaths(driver, clickXPaths);

        final String THEAD = "thead";
        final String TBODY = "tbody";
        final String TR = "/tr";
        final String TH = "/th";
        final String TD = "td";

        WebElement tableHeadElement = new WebDriverWait(driver, Duration.ofSeconds(MAX_WAIT_DURATION))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(tableXPath)));

        ExtractedRow headerRow = new ExtractedRow();
        List<WebElement> tableHeaderCells = tableHeadElement.findElements(By.xpath(THEAD + TR + TH));
        for(WebElement tableheaderCell : tableHeaderCells) {
            headerRow.getCells().add(tableheaderCell.getText());
        }
        extractedTable.setHeaderRow(headerRow);

        List<WebElement> tableRows = tableHeadElement.findElements(By.xpath(TBODY + TR));
        for(WebElement tableRow : tableRows) {
            ExtractedRow extractedRow = new ExtractedRow();
            List<WebElement> rowCells = tableRow.findElements(By.xpath(TD));
            for(WebElement tableCell : rowCells) {
                extractedRow.getCells().add(tableCell.getText());
            }

            extractedTable.getRows().add(extractedRow);
        }


        return extractedTable;
    }

    // private methods

    private void clickXpaths(WebDriver webDriver, List<String> clickXPaths) {
        synchronized (webDriver) {
            try {
                webDriver.wait(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(String clickXPath : clickXPaths) {
            webDriver.findElement(By.xpath(clickXPath)).click();
        }
    }


}
