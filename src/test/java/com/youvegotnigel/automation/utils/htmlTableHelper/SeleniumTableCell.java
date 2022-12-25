package com.youvegotnigel.automation.utils.htmlTableHelper;

import org.openqa.selenium.WebElement;

public class SeleniumTableCell extends WebElementHelper{

    public SeleniumTableCell(WebElement element) {
        super(element);
    }

    static SeleniumTableCell getInstance(WebElement cellElement) {
        return new SeleniumTableCell(cellElement);
    }

    public String getText() {
        return this.getElement().getText();
    }

    public boolean isHeaderCell() {
        return this.getElement().getTagName().toLowerCase().equals("th");
    }
}
