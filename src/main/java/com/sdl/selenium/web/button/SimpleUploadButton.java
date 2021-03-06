package com.sdl.selenium.web.button;

import com.sdl.selenium.web.WebLocator;
import org.apache.log4j.Logger;

public class SimpleUploadButton extends WebLocator {
    private static final Logger logger = Logger.getLogger(SimpleUploadButton.class);

    public SimpleUploadButton() {
        setClassName("SimpleUploadButton");
        setTag("input");
    }

    public SimpleUploadButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public SimpleUploadButton(WebLocator container, String id) {
        this(container);
        setId(id);
    }

    public void uploadFile(String path) {
        type(path);
    }

    public void uploadFileWithJS(String path, String id) {
        executeScript("return (function(id){var d=document.getElementById(id);d.style.visibility='visible';d.style.height='1px'; d.style.width='1px'; d.style.opacity=1;return d.getAttribute('style') != ''}('" + id + "'));");
        type(path);
    }
}
