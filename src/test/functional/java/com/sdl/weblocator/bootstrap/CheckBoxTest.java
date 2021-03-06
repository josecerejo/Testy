package com.sdl.weblocator.bootstrap;

import com.sdl.bootstrap.form.CheckBox;
import com.sdl.bootstrap.form.Form;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckBoxTest extends TestBase {
    private static final Logger logger = Logger.getLogger(CheckBoxTest.class);

    Form form = new Form(null, "Form Title");
    CheckBox checkBox = new CheckBox(form);

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void check() {
        assertTrue(checkBox.click());
        assertTrue(checkBox.isSelected());
    }
}
