package com.sdl.weblocator.extjs;

import com.extjs.selenium.button.Button;
import com.extjs.selenium.window.Window;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ButtonTest extends TestBase {
    private static final Logger logger = Logger.getLogger(ButtonTest.class);
    Window dateFieldWindow = new Window("DateFieldWindow");
    Button submitButton = new Button(dateFieldWindow, "Submit");
    Button closeButton = new Button(dateFieldWindow, "Close");

    @BeforeMethod
    public void startTests() {
        Button dateFieldButton = new Button(null, "DateField");
        dateFieldButton.click();
    }

    @AfterMethod
    public void endTests() {
        dateFieldWindow.close();
    }

    @Test
    public void testEditorType() {
        assertTrue(driver.findElement(By.xpath(closeButton.getPath())).isDisplayed());
    }
}
