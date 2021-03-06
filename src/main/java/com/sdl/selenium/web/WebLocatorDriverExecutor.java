package com.sdl.selenium.web;

import com.thoughtworks.selenium.SeleniumException;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class WebLocatorDriverExecutor implements WebLocatorExecutor {
    private static final Logger logger = Logger.getLogger(WebLocatorDriverExecutor.class);

    private WebDriver driver;

    public WebLocatorDriverExecutor(WebDriver driver) {
        this.driver = driver;
    }

    private String currentElementPath = "";

    @Override
    public boolean doClick(WebLocator el) {
        boolean clicked = false;
//        if (highlight) {
//            doHighlight();
//        }
        if (el.currentElement != null) {
            try {
                el.currentElement.click();
                clicked = true;
            } catch (StaleElementReferenceException e) {
                logger.error("StaleElementReferenceException: ", e);
                el.currentElement.click(); // not sure it will click now
                clicked = true;
                logger.info("Exception at Click on ");
            } catch (ElementNotVisibleException e) {
                logger.error("ElementNotVisibleException: ", e);
                throw e;
            } catch (Exception e) {
                logger.error(e);
            }
        } else {
            logger.error("currentElement is null");
        }
        return clicked;
    }

    @Override
    public boolean doClickAt(WebLocator el) {
        if (highlight) {
            doHighlight(el);
        }
        focus(el);
        return doClick(el);
    }

    @Override
    public boolean isElementPresent(WebLocator el) {
        findElement(el);
        return el.currentElement != null;
    }

    @Override
    public WebElement findElement(WebLocator el) {
        final String path = el.getPath();
//        if(currentElement != null && (currentElementPath.equals(path))){
//            logger.warn("currentElement already found one time: " + toString());
//            //return;
//        }
        try {
            el.currentElement = driver.findElement(By.xpath(path));
            currentElementPath = path;
        } catch (WebDriverException e) {
            el.currentElement = null;
            //logger.debug("Element not present:" + (path != null ? path : selector));
        }
        return el.currentElement;
    }

    @Override
    public int size(WebLocator el) {
        return driver.findElements(By.xpath(el.getPath())).size();
    }

    @Override
    public void doHighlight(WebLocator el) {
        highlightElementWithDriver(el.currentElement);
    }

    @Override
    public void focus(WebLocator el) {
        doMouseOver(el);
    }

    @Override
    public String getAttribute(WebLocator el, String attribute) {
        String attributeValue = null;
        if (isElementPresent(el)) {
            try {
                attributeValue = el.currentElement.getAttribute(attribute);
            } catch (SeleniumException e) {
                logger.debug("getAttribute '" + attribute + "' SeleniumException: " + e);
            }
        }
        return attributeValue;
    }

    @Override
    public String getHtmlText(WebLocator el) {
        String text = null;
        if (isElementPresent(el)) {
            try {
                text = el.currentElement.getText();
            } catch (WebDriverException e) {
                logger.debug("element has vanished meanwhile: " + el.getPath());
                logger.debug(e);
            }
        }
        return text;
    }

    @Override
    public String getHtmlSource(WebLocator el) {
        String text = null;
        if (isElementPresent(el)) {
            text = driver.getPageSource();
        }
        return text;
    }

    @Override
    public void type(WebLocator el, String text) {
        el.sendKeys(text);
    }

    @Override
    public void typeKeys(WebLocator el, String text) {
        el.currentElement.sendKeys(text);
    }

    @Override
    public void sendKeys(WebLocator el, java.lang.CharSequence... charSequences) {
        if (isElementPresent(el)) {
            try {
                el.currentElement.sendKeys(charSequences);
            } catch (ElementNotVisibleException e) {
                logger.error("sendKeys: ElementNotVisibleException");
                throw e;
            } catch (WebDriverException e){
                //TODO this fix is for Chrome
                Actions builder = new Actions(driver);
                builder.click(el.currentElement);
                builder.sendKeys(charSequences);
            }
        }
    }

    @Override
    public boolean isTextPresent(WebLocator el, String text) {
        return driver.getPageSource().contains(text);
    }

    @Override
    public boolean exists(WebLocator el) {
        return size(el) > 0;
    }

    @Override
    public boolean isSelected(WebLocator el) {
        return el.currentElement.isSelected();
    }

    @Override
    public void blur(WebLocator el) {
        fireEventWithJS(el, "blur");
    }

    @Override
    public boolean clear(WebLocator el) {
        boolean clear = false;
        if (isElementPresent(el)) {
            try {
                el.currentElement.clear();
                clear = true;
            } catch (InvalidElementStateException e) {
                clear = false;
            }
        }
        return clear;
    }

    @Override
    public boolean doubleClickAt(WebLocator el) {
        boolean clicked = false;
        try {
            Actions builder = new Actions(driver);
            builder.doubleClick(el.currentElement).build().perform();
            clicked = true;
        } catch (Exception e) {
            // http://code.google.com/p/selenium/issues/detail?id=244
            logger.debug(e);
            fireEventWithJS(el, "dblclick");
        }
        logger.info("doubleClickAt " + el);
        return clicked;
    }

    @Override
    public boolean doMouseOver(WebLocator el) {
        fireEventWithJS(el, "mouseover");
        return true;
    }

    public String getAttributeId(WebLocator el) {
        String pathId = getAttribute(el, "id");
        if (el.hasId()) {
            final String id = el.getId();
            if (!id.equals(pathId)) {
                logger.warn("id is not same as pathId:" + id + " - " + pathId);
            }
            return id;
        }
        return pathId;
    }

    public void fireEventWithJS(WebLocator el, String eventName) {
        String script;
        String id = getAttributeId(el);
        if (!"".equals(id)) {
            script = "var fireOnThis = document.getElementById('" + id + "');\n" +
                    "if(document.createEvent) {" +
                    "var evObj = document.createEvent('MouseEvents');\n" +
                    "evObj.initEvent( '" + eventName + "', true, true );\n" +
                    "fireOnThis.dispatchEvent(evObj);\n" +
                    "} else if( document.createEventObject ) {" +
                    "fireOnThis.fireEvent('on" + eventName + "');" +
                    "}";
        } else if (!"".equals(getAttribute(el, "class"))){
            script = "var fireOnThis = document.getElementsByClassName('" + getAttribute(el, "class") + "');\n" +
                    "if(document.createEvent) {" +
                    "var evObj = document.createEvent('MouseEvents');\n" +
                    "evObj.initEvent( '" + eventName + "', true, true );\n" +
                    "fireOnThis[0].dispatchEvent(evObj);\n" +
                    "} else if( document.createEventObject ) {" +
                    "fireOnThis[0].fireEvent('on" + eventName + "');" +
                    "}";
        } else {
            script = "var fireOnThis = document.evaluate(\"" + el.getPath() + "\", document, null, XPathResult.ANY_TYPE, null).iterateNext();\n" +
                    "var evObj = document.createEvent('MouseEvents');\n" +
                    "evObj.initEvent( '" + eventName + "', true, true );\n" +
                    "fireOnThis.dispatchEvent(evObj);";
        }
//        logger.debug("fireEventWithJS : " + eventName);
        executeScript(script);
    }

    @Override
    public Object executeScript(String script, Object... objects) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript(script, objects);
    }

    private void highlightElementWithDriver(WebElement el) {

        // TODO more tests for this method

//        String highlightStyle = "background: none yellow !important; color: red !important; border: 1px solid green !important;";

//        String script = "(function(element){" +
//            "var original_style = element.getAttribute('style') || '';" +
//            "element.setAttribute('style', original_style + '; " + highlightStyle + "'); " +
//            "setTimeout(function(){element.setAttribute('style', original_style);}, 200);})(arguments[0]);";

//        executeScript(script, element);

//        for (int i = 0; i < 2; i++) {
//            JavascriptExecutor js = (JavascriptExecutor) driver;
//            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, highlightStyle);
//            js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
//        }
    }
}