package com.extjs.selenium.button;

import com.sdl.selenium.web.WebLocator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UploadButtonTest {
    public static WebLocator container = new WebLocator("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new UploadButton(), "//table[contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new UploadButton(container, "Download"), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(.//*[text()='Download']) > 0 and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new UploadButton(container), "//*[contains(@class, 'container')]//table[contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new UploadButton(container).setId("ID"), "//*[contains(@class, 'container')]//table[@id='ID' and contains(@class, 'x-btn') and count(ancestor-or-self::*[contains(@style, 'display: none')]) = 0 and count(ancestor-or-self::*[contains(@class, 'x-hide-display')]) = 0 and not(contains(@class, 'x-item-disabled'))]"},
                {new UploadButton(container).setElPath("//*[text()='Download']"), "//*[contains(@class, 'container')]//*[text()='Download']"},
                {new UploadButton().setElPath("//*[text()='Download']"), "//*[text()='Download']"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(UploadButton uploadButton, String expectedXpath) {
        Assert.assertEquals(uploadButton.getPath(), expectedXpath);
    }
}