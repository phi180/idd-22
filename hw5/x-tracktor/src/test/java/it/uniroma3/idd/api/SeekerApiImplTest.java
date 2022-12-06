package it.uniroma3.idd.api;

import it.uniroma3.idd.utils.Utils;
import org.junit.jupiter.api.Test;


class SeekerApiImplTest {

    @Test
    void getEntityUrlsValueToday() {
        final String entryPageUrl = "https://www.value.today/";
        final String nextButtonXPath = "//ul[@class='pagination js-pager__items']/li[@class='pager__item pager__item--next']/a";
        final String entityPagesUrlXPath = "//h2[@class='text-primary']/a";

        Utils.writeUrlsToFile("valuetoday", new SeekerApiImpl().getEntityUrls(entryPageUrl,nextButtonXPath, 100, entityPagesUrlXPath));
    }

    @Test
    void getEntityUrlsCompaniesMarketCapIndia() {
        final String entryPageUrl = "https://companiesmarketcap.com/india/largest-companies-in-india-by-market-cap";
        final String nextButtonXPath = "//a[text()=' Next 100❯ ']";
        final String entityPagesUrlXPath = "//table[@class='default-table table marketcap-table dataTable']/tbody/tr/td/div/a";

        Utils.writeUrlsToFile("companiesmarketcapindia", new SeekerApiImpl().getEntityUrls(entryPageUrl,nextButtonXPath, 5, entityPagesUrlXPath));
    }

    @Test
    void getEntityUrlsForbes() {
        final String entryPageUrl = "https://www.forbes.com/lists/global2000/";
        final String nextButtonXPath = "//button[@class='next-button arrow-footer']";
        final String entityPagesUrlXPath = "//div[@class='table']/div[@class='table-row-group']/a";

        Utils.writeUrlsToFile("forbes", new SeekerApiImpl().getEntityUrls(entryPageUrl,nextButtonXPath, 1, entityPagesUrlXPath));
    }

    @Test
    void getEntityUrlsForbes2() {
        final String entryPageUrl = "https://www.forbes.com/lists/worlds-best-employers/";
        final String nextButtonXPath = "//button[@class='next-button arrow-footer']";
        final String entityPagesUrlXPath = "//div[@class='table']/div[@class='table-row-group']/a";

        Utils.writeUrlsToFile("forbes2", new SeekerApiImpl().getEntityUrls(entryPageUrl,nextButtonXPath, 16, entityPagesUrlXPath));
    }

    @Test
    void getEntityUrlsDisfold() {
        final String entryPageUrl = "https://disfold.com/world/companies/";
        final String nextButtonXPath = "//div[@class='pagination']/ul[@class='pagination']/li/a[i[text()='chevron_right']]";
        final String entityPagesUrlXPath = "//table[@class='striped responsive-table']/tbody/tr/td[2]/a";

        Utils.writeUrlsToFile("disfold", new SeekerApiImpl().getEntityUrls(entryPageUrl,nextButtonXPath, 19, entityPagesUrlXPath));
    }

}