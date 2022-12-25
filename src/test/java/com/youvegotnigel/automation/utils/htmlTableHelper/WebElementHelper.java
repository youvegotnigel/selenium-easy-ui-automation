package com.youvegotnigel.automation.utils.htmlTableHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

public class WebElementHelper {

    private WebElement element;

    public WebElementHelper(WebElement element) {
        this.element = element;
    }

    public WebElement getElement() {
        return this.element;
    }

    protected Optional<WebElement> findChild(String xpath) {
        try {
            return Optional.ofNullable(this.getElement().findElement(By.xpath(xpath)));
        } catch (NoSuchElementException var3) {
            return Optional.empty();
        }
    }

    protected List<WebElement> findChildren(String xpath) {
        return this.getElement().findElements(By.xpath(xpath));
    }
}
