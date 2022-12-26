package com.youvegotnigel.automation.utils.htmlTableHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

public class SeleniumTable extends WebElementHelper{



    public SeleniumTable(WebElement element) {
        super(element);

        String tagName = this.getElement().getTagName();
        String[] allowedTags = new String[]{"table", "tbody", "thead", "tfoot"};
        if (!Arrays.asList(allowedTags).contains(tagName.toLowerCase())) {
            throw new IllegalArgumentException("Invalid element of type \"" + tagName + "\" provided. Should be \"table\"");
        }
    }

    /**
     * @return Get total table row count
     */
    public int rowCount() {
        List<WebElement> elements = this.findChildren(".//" + (this.hasTBody() ? "tbody/tr" : "tr"));
        return elements.size();
    }

    /**
     * @return Table has a header ? true|false
     */
    public boolean hasTHead() {
        return this.findChild(".//thead").isPresent();
    }

    /**
     * @return Table has a body ? true|false
     */
    public boolean hasTBody() {
        return this.findChild(".//tbody").isPresent();
    }

    /**
     * @return In table header ? true|false
     */
    public boolean isHeaderRow() {
        List<WebElement> headerCells = this.findChildren(".//th");
        return headerCells.size() == this.cellCount();
    }

    /**
     * @return int cell count
     */
    public int cellCount() {
        List<WebElement> cellElements = this.findChildren(".//td|.//th");
        return cellElements.size();
    }

    /**
     * @return WebElements table rows
     */
    public List<SeleniumTableRow> rows() {
        List<WebElement> elements = this.findChildren(".//" + (this.hasTBody() ? "tbody/tr" : "tr"));
        return (List)elements.stream().map(SeleniumTableRow::getInstance).collect(Collectors.toList());
    }

    /**
     * @return Table row index
     * @param rowIndex int index of the selected row
     */
    public SeleniumTableRow get(int rowIndex) {
        Optional<WebElement> elementOptional = this.findChild(".//" + (this.hasTBody() ? "tbody/tr" : "tr") + "[" + (rowIndex + 1) + "]");
        WebElement webElement = (WebElement)elementOptional.orElseThrow(() -> {
            return new IndexOutOfBoundsException("Row index " + rowIndex + " out of bounds for table.");
        });
        return SeleniumTableRow.getInstance(webElement);
    }

    public SeleniumTable head() {
        Optional<WebElement> thead = this.findChild(".//thead");
        WebElement theadElement = (WebElement)thead.orElseThrow(() -> {
            return new NoSuchElementException("No element of type \"thead\" found on table.");
        });
        return new SeleniumTable(theadElement);
    }

    /**
     * Get header row element
     */
    public SeleniumTableRow headerRow() {
        if (this.hasTHead()) {
            SeleniumTable thead = this.head();
            if (thead.rowCount() > 0) {
                SeleniumTableRow firstRow = thead.get(0);
                if (firstRow.isHeaderRow()) {
                    return firstRow;
                }
            }
        }

        List<SeleniumTableRow> rows = this.rows();
        return (SeleniumTableRow)rows.parallelStream().filter(SeleniumTableRow::isHeaderRow).findFirst().orElse((SeleniumTableRow) null);
    }

    /**
     * Get column values for a given column name
     * @param columnName name of the column
     */
    public List<SeleniumTableCell> getColumn(String columnName) {
        return this.getColumn(columnName, false);
    }

    /**
     * Get column values for a given column name
     * @param columnName name of the column
     * @param includeNulls true or false, include null values
     */
    public List<SeleniumTableCell> getColumn(String columnName, boolean includeNulls) {
        int foundIndex = this.getColumnIndex(columnName);
        if (foundIndex == -1) {
            throw new IllegalArgumentException("Unable to find column named \"" + columnName + "\" in table.");
        } else {
            List<SeleniumTableCell> columnCells = new ArrayList();
            Iterator var5 = this.rows().iterator();

            while(true) {
                SeleniumTableRow row;
                do {
                    if (!var5.hasNext()) {
                        return columnCells;
                    }

                    row = (SeleniumTableRow)var5.next();
                } while(row.isHeaderRow());

                try {
                    SeleniumTableCell cell = row.get(foundIndex);
                    columnCells.add(cell);
                } catch (IndexOutOfBoundsException var8) {
                    if (includeNulls) {
                        columnCells.add((SeleniumTableCell)null);
                    }
                }
            }
        }
    }



//    /**
//     * Get row index by cell info
//     * @param cell_info table cell values as a map
//     */
//    public int getRowIndexByCellsInfo(Map<String,String> cell_info){
//
//        String xpath = prepareRowXpath(cell_info, "1") + "/preceding-sibling::tr";
//        return this.findChildren(xpath).size();
//    }
//
//    /**
//     * Get column index by cell info
//     * @param columnName table header name as a String
//     */
//    public int getColumnIndexByCellsInfo(String columnName){
//
//        String xpath = prepareTableHeaderXpath(columnName) + "/preceding-sibling::th";
//        return this.findChildren(xpath).size();
//    }

    /**
     * Get column index by cell info
     * @param columnName table header name as a String
     */
    public int getColumnIndex(String columnName) {
        SeleniumTableRow headerRow = this.headerRow();
        if (headerRow == null) {
            throw new UnsupportedOperationException("Cannot get cells for column \"" + columnName + "\" on table without header row.");
        } else {
            int foundIndex = -1;

            for(int i = 0; i < headerRow.cellCount(); ++i) {
                if (headerRow.get(i).getText().equalsIgnoreCase(columnName)) {
                    foundIndex = i;
                    break;
                }
            }
            return foundIndex;
        }
    }


    /**
     * Identify table cell by row index and column index
     * @param rowIndex index of table row
     * @param columnIndex index of table column
     */
    public WebElement identifyCellByIndexes(int rowIndex, int columnIndex){

        List<SeleniumTableRow> rows = rows();
        int rowNumber = rows.size();

        if(rowIndex < 0 || rowIndex > rowNumber){
            System.out.println("Invalid row index: " + rowIndex +". It should start from 1");
            return null;
        }

        SeleniumTableRow row = rows.get(rowIndex);
        List<WebElement> cols = row.findChildren(".//td|.//th");
        WebElement cell = cols.get(columnIndex);

        return cell;
    }

    /**
     * Click on table cell
     * @param cell get form identifyCellByIndexes(rowIndex, columnIndex)
     */
    public void clickOnCell(WebElement cell){
        try{
            cell.click();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Could not click on cell " + cell.getText());
        }
    }


}
