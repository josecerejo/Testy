package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TableTest{
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Table(),             "//table"},
                {new Table(container),    "//*[contains(@class, 'container')]//table"},
                {new Table().setId("ID"), "//table[@id='ID']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Table table, String expectedXpath) {
        Assert.assertEquals(table.getPath(), expectedXpath);
    }

    @DataProvider
    public static Object[][] testConstructorPathDataProvider1() {
        return new Object[][]{
                {new Table().getGridRow(1),                                     "//table//tr[1]"},
                {new Table().getGridRow("SearchElement"),                       "//table//tr[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]"},
                {new Table().getGridRow("SearchElement", SearchType.CONTAINS),  "//table//tr[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]"},
                {new Table().getGridRow("SearchElement", SearchType.STARTS_WITH),"//table//tr[starts-with(text(),'SearchElement') or count(.//*[starts-with(text(),'SearchElement')]) > 0]"},

                {new Table().getGridCell(1, 1),                                  "//table//tr[1]//td[1]"},
                {new Table().getGridCell("SearchElement", SearchType.EQUALS),    "//table//tr//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]"},
                {new Table().getGridCell("SearchElement", SearchType.CONTAINS),  "//table//tr//td[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]"},
                {new Table().getGridCell("SearchElement", SearchType.STARTS_WITH),"//table//tr//td[starts-with(text(),'SearchElement') or count(.//*[starts-with(text(),'SearchElement')]) > 0]"},
                {new Table().getGridCell(1, 1, "SearchElement"),                  "//table//tr[1]//td[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0][1]"},
                {new Table().getGridCell("SearchElement", "text", SearchType.EQUALS), "//table//tr[contains(text(),'SearchElement') or count(.//*[contains(text(),'SearchElement')]) > 0]//td[text()='text' or count(.//*[text()='text']) > 0]"},
                {new Table().getGridCell("SearchElement", 2, SearchType.EQUALS),         "//table//tr[text()='SearchElement' or count(.//*[text()='SearchElement']) > 0]//td[2]"},
                {new Table().findRow(new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)),         "//table//tr[count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]"},
                {new Table().getGridCell(1, new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)),         "//table//tr[count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]//td[1]"},
                {new Table().getGridCell(1, "111", new TableCell(3, "1234", SearchType.EQUALS), new TableCell(4, "Eng-Fra", SearchType.EQUALS)),  "//table//tr[count(td[3][text()='1234' or count(.//*[text()='1234']) > 0]) > 0 and count(td[4][text()='Eng-Fra' or count(.//*[text()='Eng-Fra']) > 0]) > 0]//td[1][text()='111' or count(.//*[text()='111']) > 0]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider1")
    public void getPathSelectorCorrectlyFromConstructors1(WebLocator webLocator, String expectedXpath) {
        Assert.assertEquals(webLocator.getPath(), expectedXpath);
    }
}
