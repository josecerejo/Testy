package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextFieldTest {
    public static ExtJsComponent container = new ExtJsComponent("container");

    @DataProvider
    public static Object[][] testConstructorPathDataProvider() {
        return new Object[][]{
                {new TextField(),                                       "//input[not (@type='hidden') ]"},
                {new TextField().setId("ID"),                           "//input[@id='ID' and not (@type='hidden') ]"},
                {new TextField("TextFieldClass"),                       "//input[contains(@class, 'TextFieldClass') and not (@type='hidden') ]"},
                {new TextField(container),                              "//*[contains(@class, 'container')]//input[not (@type='hidden') ]"},
                {new TextField(container).setElPath("//*[contains(text(), 'Register')]"), "//*[contains(@class, 'container')]//*[contains(text(), 'Register')]"},
                {new TextField(container, "TextFieldText"),             "//*[contains(@class, 'container')]//label[text()='TextFieldText']//following-sibling::*//input[not (@type='hidden') ]"},
                {new TextField("name", container),                      "//*[contains(@class, 'container')]//input[contains(@name,'name') and not (@type='hidden') ]"},
        };
    }

    @Test(dataProvider = "testConstructorPathDataProvider")
    public void getPathSelectorCorrectlyFromConstructors(TextField textField, String expectedXpath) {
        Assert.assertEquals(textField.getPath(), expectedXpath);
    }

    @Test
    public void getPathSelectorCorrectlySetId() {
        TextField textField = new TextField();
        textField.setId("TextFieldId");
        Assert.assertEquals(textField.getPath(), "//input[@id='TextFieldId' and not (@type='hidden') ]");
    }

    @Test
    public void getPathSelectorCorrectlySetElementPath() {
        TextField textField = new TextField();
        textField.setElPath("//*[contains(text(), 'Register')]");
        Assert.assertEquals(textField.getPath(), "//*[contains(text(), 'Register')]");
    }
}
