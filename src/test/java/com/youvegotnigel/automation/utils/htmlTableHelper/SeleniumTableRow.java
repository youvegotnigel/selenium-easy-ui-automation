package com.youvegotnigel.automation.utils.htmlTableHelper;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SeleniumTableRow extends WebElementHelper{

    public SeleniumTableRow(WebElement element) {
        super(element);
    }

    static SeleniumTableRow getInstance(WebElement rowElement) {
        return new SeleniumTableRow(rowElement);
    }

    public SeleniumTableCell get(int columnIndex) {
        Optional<WebElement> elementOptional = this.findChild("(.//td|.//th)[" + (columnIndex + 1) + "]");
        WebElement element = (WebElement)elementOptional.orElseThrow(() -> {
            return new IndexOutOfBoundsException("Column index " + columnIndex + " too large for row.");
        });
        return SeleniumTableCell.getInstance(element);
    }

    public int cellCount() {
        List<WebElement> cellElements = this.findChildren(".//td|.//th");
        return cellElements.size();
    }

    public List<SeleniumTableCell> cells() {
        List<WebElement> cells = this.findChildren(".//td|.//th");
        return (List)cells.stream().map(SeleniumTableCell::getInstance).collect(Collectors.toList());
    }

    public boolean isHeaderRow() {
        List<WebElement> headerCells = this.findChildren(".//th");
        return headerCells.size() == this.cellCount();
    }
}
