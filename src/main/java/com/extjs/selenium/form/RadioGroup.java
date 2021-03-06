package com.extjs.selenium.form;

import com.extjs.selenium.ExtJsComponent;
import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class RadioGroup extends ExtJsComponent {
    private static final Logger logger = Logger.getLogger(RadioGroup.class);

    private Radio radio = new Radio(this);

    public RadioGroup() {
        setClassName("RadioGroup");
        setBaseCls("x-form-radio-group");
    }

    public RadioGroup(WebLocator container, String name) {
        this();
        setContainer(container);
        radio.setName(name);
    }

    public boolean selectByLabel(String label) {
        radio.setLabel(label);
        boolean selected = !isDisabled() && radio.click();
        radio.setLabel(null);
        return selected;
    }

    public boolean selectByValue(String value) {
        radio.setText(value);
        boolean selected = !isDisabled() && radio.click();
        radio.setText(null);
        return selected;
    }

    public String getLabelName(String label) {
        return new WebLocator(radio, "/following-sibling::label[contains(text(),'" + label + "')]").getHtmlText();
    }

    public boolean isDisabled() {
        return new WebLocator(null, radio.getPath(true)).exists();
    }
}