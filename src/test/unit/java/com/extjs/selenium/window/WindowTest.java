package com.extjs.selenium.window;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WindowTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new Window(),                     "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;')]"},
                {new Window().setCls("Cls"),       "//*[contains(@class, 'x-window') and contains(@class, 'Cls') and contains(@style ,'visibility: visible;')]"},
                {new Window(true),                 "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;') and preceding-sibling::*[contains(@class, 'ext-el-mask') and contains(@style, 'display: block')]]"},
                {new Window(false),                "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;')]"},
                {new Window("WindowTitle"),        "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;') and (count(*[contains(@class,'x-window-header')]//*[text()='WindowTitle']) > 0 or count(*[contains(@class, '-tl')]//*[contains(@class,'x-window-header')]//*[text()='WindowTitle']) > 0)]"},
                {new Window("WindowTitle", true),  "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;') and (count(*[contains(@class,'x-window-header')]//*[text()='WindowTitle']) > 0 or count(*[contains(@class, '-tl')]//*[contains(@class,'x-window-header')]//*[text()='WindowTitle']) > 0) and preceding-sibling::*[contains(@class, 'ext-el-mask') and contains(@style, 'display: block')]]"},
                {new Window("WindowTitle", false), "//*[contains(@class, 'x-window') and contains(@style ,'visibility: visible;') and (count(*[contains(@class,'x-window-header')]//*[text()='WindowTitle']) > 0 or count(*[contains(@class, '-tl')]//*[contains(@class,'x-window-header')]//*[text()='WindowTitle']) > 0)]"},
        };
    }

    @Test (dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(Window window, String expectedXpath) {
        Assert.assertEquals(window.getPath(), expectedXpath);
    }

}
