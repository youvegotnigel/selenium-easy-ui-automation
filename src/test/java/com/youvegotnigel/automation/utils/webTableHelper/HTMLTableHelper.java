package com.youvegotnigel.automation.utils.webTableHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;


public class HTMLTableHelper extends WebElementHelper {

    static final String FIND_TABLE_BY_ROW_XPATH = "//table[*[tr[@{rowCriteria}]]]";
    static final String FIND_CELLS_BY_COLUMN_INDEX = ".//tr[.//td[ position()= 1]|.//th[position()=0]]/*[(local-name()='td' or local-name()='th') and count(./preceding-sibling::*[local-name()='td' or local-name()='th'])=@{columnIndex}]";
    static Map<Integer, String> cachedHeaders = new HashMap<>();

    private static final Logger log = LogManager.getLogger(HTMLTableHelper.class.getName());



    /**
     * @return Identify html table using table header
     * @param table_name value of table header
     * @param element_index value of element index
     */
    public static WebElement identifyTable(String table_name, int element_index){

        String xpath = "(//h4[contains(text(),'"+table_name+"')]/following-sibling::*/table)["+element_index+"]";
        WebElement table = WebElementHelper.findChild(null, By.xpath(xpath));
        return table;
    }

    /**
     * @return Identify html table using table column headers
     * @param cell_info values of table cells
     * @param element_index value of element index
     */
    public static WebElement identifyTable(Map<String,String> cell_info, int element_index){

        List<String> xpathList = new ArrayList<>();

        for (Map.Entry<String,String> entry : cell_info.entrySet()){
            String xpath_list = XPathHelper.makeTextComparisonXPath(".", entry.getKey(), XPathHelper.CompareOptions.EQUALS, false);
            String closure = String.format(".//*[%s]", xpath_list);
            xpathList.add(closure);
        }

        String xpath = "(//thead/tr[" + String.join(" and ", xpathList) + "]/ancestor::table)["+element_index+"]";
        WebElement table = WebElementHelper.findChild(null, By.xpath(xpath));
        return table;
    }


    /**
     * Verify a row displaying in a table using it's cells info
     * @param table the WebElement table used for verifying if a row displayed
     * @param cellsInfo a map of cells info including column's header and cell value
     * @return true/false
     */
    public static boolean verifyRowDisplayed(WebElement table, Map<String, String> cellsInfo){

        log.info(String.format("Verify a row containing the following cells '%s' is displayed in the table '%s'", cellsInfo, table));
        log.info(String.format("table xPath ::: %s", table.getText()));

        try {
            WebElement row = getMatchedRow(table, cellsInfo);
            if(row == null){
                log.debug(String.format("Could not find any rows match to searching criteria %s", cellsInfo));
                return false;
            }else{
                WebElementHelper.moveToElement(row);
                return true;
            }

        }catch (Exception e){
            log.debug(String.format("Could not find any rows match to searching criteria %s", cellsInfo));
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Identity a cell web element using column header and cell value
     * @param table the WebElement table which the cell belonging to
     * @param columnHeader the column header that the cell display under
     * @param cellValue the value displays on the cell
     * @return WebElement
     */
    public static WebElement identifyCellByValueAndColHeader(WebElement table, String columnHeader, String cellValue){

        log.info(String.format("identity a cell with value '%s' under column header '%s'", cellValue, columnHeader));

        try{
            int columnIndex = getColumnIndex(table, ".", columnHeader);
            if(columnIndex > 0){
                String cellCriteria = XPathHelper.makeTextComparisonXPath(".", cellValue, XPathHelper.CompareOptions.EQUALS, false);
                String cellXpath = String.format(".//tr/td[%s and position() = %d]|.//tr/th[%s and position() = %d]", cellCriteria, columnIndex, cellCriteria, columnIndex);
                WebElement cell = WebElementHelper.findChild(table, By.xpath(cellXpath));
                return cell;

            }else{
                log.debug(String.format("Could not find any columns with header %s", columnHeader));
                return null;
            }


        }catch (Exception e){
            log.debug(String.format("Could not find any columns with header %s", columnHeader));
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Get column index by it's header
     * @param table the WebElement table which the row belonging to
     * @param columnHeader the column header
     * @return integer, starts from 1
     */
    public static int getColumnIndexByHeader(WebElement table, String columnHeader){

        log.info(String.format("Get index of column '%s'", columnHeader));

        try{
            int index = getColumnIndex(table, ".", columnHeader);
            if(index>0){
                return index;
            }else {
                log.debug(String.format("Could not find column with header '%s'", columnHeader));
                return -1;
            }

        }catch (Exception e){
            log.debug(String.format("Could not find column with header '%s'", columnHeader));
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Get column index by it's attribute. It's used in case of missing column header
     * @param table the WebElement table which the row belonging to
     * @param attribute the attribute name used for detect position of the column
     * @param value the attribute value used for detect position of the column
     * @return integer, starts from 1
     */
    public static int getColumnIndexByAttribute(WebElement table, String attribute, String value){

        log.info(String.format("Get index of column using attribute %s = '%s'", attribute, value));

        try{
            int index = getColumnIndex(table, attribute, value);
            if(index>0){
                return index;
            }else {
                log.debug(String.format("Could not find column with attribute %s = '%s'", attribute, value));
                return -1;
            }

        }catch (Exception e){
            log.debug(String.format("Could not find column with attribute %s = '%s'", attribute, value));
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @return Prepare table row xpath
     * @param table the WebElement table which the row belonging to
     * @param cell_info values of table cells
     */
    public static String prepareRowXpath(WebElement table, Map<String,String> cell_info){

        List<String> columnsXpath  = new ArrayList<>();

        for (Map.Entry<String,String> entry : cell_info.entrySet()){

            int columnIndex = getColumnIndex(table, ".", entry.getKey());

            String cellCriteria = XPathHelper.makeTextComparisonXPath(".", entry.getValue(), XPathHelper.CompareOptions.EQUALS, false);
            //System.out.println(cellCriteria);
            cellCriteria = String.format(".//*[%s and position()=%d and (local-name()='td' or local-name()='th')]", cellCriteria, columnIndex);
            columnsXpath.add(cellCriteria);
        }
        String rowXpath = "(//tr[" + String.join(" and ", columnsXpath ) + "])[1]";
        return rowXpath;
    }


    /**
     * @return List of Table column headers
     * @param table the WebElement table which the row belonging to
     * @param attribute value of attribute
     * @param value value of column
     */
    private static List<WebElement> findColumnHeaders(WebElement table, String attribute, String value){
        String textXpath = XPathHelper.makeTextComparisonXPath(attribute, value, XPathHelper.CompareOptions.EQUALS, false);
        String thTextXpath = String.format("th[%s]", textXpath);
        String tdTextXpath = String.format("td[%s]", textXpath);

        String columnsXpath = String.format(".//tr/%s|//tr/%s|.//tr/%s/preceding-sibling::th|//tr/%s/preceding-sibling::td", thTextXpath, tdTextXpath, thTextXpath, tdTextXpath);
        log.info(String.format("Find columns matching to search criteria xpath: '%s'}", columnsXpath));
        List<WebElement> columns = WebElementHelper.findChildren(table, By.xpath(columnsXpath));
        return columns;
    }

    /**
     * Get row index using it's cells info
     * @param table the WebElement table which the row belonging to
     * @param cell_info map of cells info including their column headers and cell values
     * @return integer, starts from 1
     */
    public static int getRowIndexByCellsInfo(WebElement table, Map<String,String> cell_info){

        log.info(String.format("Get index of the row containing the following cells '%s'", cell_info));
        try{

            List<WebElement> rows = getMatchedAndPrecedingRows(table, cell_info);
            return isHeaderSeparated(table) ? rows.size(): rows.size()-1;

        }catch (Exception e){
            log.debug(String.format("Could not find any rows match to searching criteria '%s'", cell_info));
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Identify a web element cell of the specific table based on column header and another cells info
     * @param table the table passed in to identify the cell
     * @param columnHeader the column header which that cell belonging to
     * @param cell_info map of another cells info including their column headers and cell values to identify the row that cell belongings to
     * @return WebElement
     */
    public static WebElement identifyCellByHeaderAndCellsInfo(WebElement table, String columnHeader, Map<String, String> cell_info){

        log.info(String.format("identify a cell under column header '%s' belonging to the row of another cells '%s'", columnHeader, cell_info));
        try{

            int columnIndex = getColumnIndex(table, ".", columnHeader);
            if(columnIndex<0){
                log.debug(String.format("Could not find any columns with header '%s'", columnHeader));
                return null;
            }

            WebElement row = getMatchedRow(table, cell_info);
            if(row == null){
                log.debug(String.format("Could not find any rows match to searching criteria '%s'", cell_info));
                return null;
            }

            String cellXpath = String.format(".//*[position()=%d and (local-name()='td' or local-name()='th')]", columnIndex);
            WebElement cell = WebElementHelper.findChild(row, By.xpath(cellXpath));
            if(cell == null){
                log.debug(String.format("Could not find any cell under column header '%s' belonging to the row of another cells '%s'", columnHeader, cell_info));
                return null;
            }
            return cell;

        }catch (Exception e){
            log.debug(String.format("Could not find any columns with header '%s'", columnHeader));
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Identity a cell web element using row and column indexes
     * @param table the WebElement table which the row belonging to
     * @param columnIndex position of the column in the table
     * @param rowIndex position of the row in the table
     * @return WebElement
     */
    public static WebElement identifyCellByIndexes(WebElement table, int columnIndex, int rowIndex){

        log.info(String.format("Identify a cell in table with row index:%d, column index:%d", rowIndex, columnIndex));
        try{

            List<WebElement> rows = WebElementHelper.findChildren(table, By.tagName("tr"));
            int rowNumber = rows.size();

            if(rowIndex <= 0 || rowIndex > rowNumber){
                log.debug(String.format("Invalid row index: %d. It should start from 1", rowIndex));
                return null;
            }

            WebElement row = rows.get(rowIndex);
            List<WebElement> cols = row.findElements(By.xpath(".//td|.//th"));

            //Scan all columns to see if any columns use colspan attribute
            int originalValue = columnIndex;
            int spanSum = 0;
            int currentIndex = 0;
            for(WebElement e:cols){
                currentIndex++;
                String colspan = e.getAttribute("colspan");
                if(colspan != null){
                    int increasement= Integer.parseInt(colspan)-1;
                    spanSum += increasement;
                    //If any colspan used, the colspan value subtracted from the index
                    columnIndex = columnIndex - increasement;
                }
                if (currentIndex + spanSum >= originalValue){
                    break;
                }
            }

            WebElement cell = cols.get(columnIndex-1);
            return cell;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get values of cells in a column
     * @param table the WebElement table which the row belonging to
     * @param columnHeader: name of column
     * @return list of values
     */
    public static List<String> getCellsValueByColumnHeader(WebElement table, String columnHeader){

        log.info(String.format("Get values of cells in column '%s'", columnHeader));
        try{

            int columnIndex = getColumnIndexByHeader(table, columnHeader)-1;
            if(columnIndex<0){
                log.debug(String.format("Could not find any columns with header '%s'", columnHeader));
                return null;
            }

            String cellsXpath = FIND_CELLS_BY_COLUMN_INDEX.replace("@{columnIndex}", String.valueOf(columnIndex));
            List<WebElement> listCells = WebElementHelper.findChildren(table, By.xpath(cellsXpath));
            List<String> listValues = new ArrayList<>();
            int rowNum = listCells.size();

            if(rowNum>0){
                log.debug(String.format("Found %d row(s)", rowNum));
                for(WebElement cell: listCells){
                    String cellValue = WebElementHelper.getTextContent(cell);
                    listValues.add(cellValue);
                }

            }else{
                log.debug(String.format("Could not find any row\n"));
            }

            return listValues;

        }catch (Exception e){
            log.debug(String.format("Could not find any row\n"));
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Get position of a column based on it's attribute
     * @param table values of table element
     * @param attribute value of attribute
     * @param value value of column
     */
    private static int getColumnIndex(WebElement table, String attribute, String value){
        //int index;
        if(attribute != "." && attribute != "text()"){
            attribute = String.format("@%s",attribute);
        }
        List<WebElement> columns = findColumnHeaders(table, attribute, value);
        if(columns.isEmpty()){
            Map<Integer, String> headerIndexes = getHeaderIndexes(table);
            int cahedIndex = getCachedHeaderIndex(value);
            String newHeader = headerIndexes.get(cahedIndex);
            if(newHeader.toLowerCase().contains(value.toLowerCase())){
                columns = findColumnHeaders(table, attribute, newHeader);
            }
        }

        return !columns.isEmpty() ? columns.size() : -1;
    }

    /**
     * @param table
     * @return Total row count of the table
     */
    public static int getTotalRowCount(WebElement table){
        String trXpath = "//tbody/tr";
        log.debug(String.format("Find no of rows xpath: '%s'", trXpath));
        List<WebElement> rows = WebElementHelper.findChildren(table, By.xpath(trXpath));

        return !rows.isEmpty() ? rows.size() : -1;
    }

    /**
     * Click on a column based on it' header
     * @param table the table that the column belongings to
     * @param columnHeader the column header
     * @return
     */
    public static void clickOnColumn(WebElement table, String columnHeader){

        log.info(String.format("Click on column with header: '%s'", columnHeader));
        try{

            WebElement column = findColumn(table, columnHeader);
            column.click();

        }catch (Exception e){
            log.debug(String.format("Could not find any columns with header '%s'", columnHeader));
            e.printStackTrace();
        }
    }

    /**
     * Click on a link with specific text inside a cell of table
     * @param cell the cell that the link belonging to
     * @param linkText the text display on the link
     */
    public static void clickOnLinkInCell(WebElement cell, String linkText){
        log.info(String.format("Click on the link by text '%s' inside the cell '%s'", linkText, cell));
        try {
            String textCriteria = XPathHelper.makeTextComparisonXPath(".", linkText, XPathHelper.CompareOptions.CONTAINS, false);
            String linkXpath = String.format(".//*[%s]", textCriteria);
            WebElement link = WebElementHelper.findChild(cell, By.xpath(linkXpath));
            WebElementHelper.clickInView(link);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Set text for input control inside a cell
     * @param cell the cell object need to set text
     * @param text the value used for typing into cell
     */
    public static void setTextForCell(WebElement cell, String text){
        log.info(String.format("Set '%s' into a input control inside the cell '%s'", text, cell));
        try{
            WebElement textControl = WebElementHelper.findChild(cell, By.xpath(".//input[not(@type = 'hidden')]|.//textarea"));
            textControl.clear();
            textControl.sendKeys(text);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Select option of a select box in a cell
     * @param cell: the cell which the select box belongs to
     * @param optionText: the label of option in select box
     * @param isRegex : true if label is regular expression, false if not
     */
    public static void selectOptionInCell(WebElement cell, String optionText, boolean isRegex){
        log.info(String.format("Select an item with label '%s' in a cell '%s'. Using regular expression: %s", optionText, cell.getText(), isRegex));
        try{
            WebElement weSelect = WebElementHelper.findChild(cell, By.xpath(".//select"));
            if(weSelect == null){
                log.error(String.format("Could not find any select box inside of the cell '%s'", cell));
                return;
            }
            WebElementHelper.selectOptionByLabel(weSelect, optionText, isRegex);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static WebElement findColumn(WebElement table, String columnHeader){
        String textXpath = XPathHelper.makeTextComparisonXPath(".", columnHeader, XPathHelper.CompareOptions.EQUALS,false);
        String columnXpath = String.format(".//tr/td[%s]|.//tr/th[%s]", textXpath, textXpath);
        return WebElementHelper.findChild(table, By.xpath(columnXpath));
    }

    private static WebElement getMatchedRow(WebElement table, Map<String,String> cellsInfo){
        String rowXpath = prepareRowXpath(table, cellsInfo);
        log.info(String.format("getMatchedRow - %s", rowXpath));
        try{
            return table.findElement(By.xpath(rowXpath));
        }catch (NoSuchElementException e){
            log.debug(String.format("Element in table '%s' was NOT found with cell_info: '%s'", table, cellsInfo));
            e.printStackTrace();
            return null;
        }
    }

    private static List<WebElement>getMatchedAndPrecedingRows(WebElement table, Map<String,String> cell_info){
        String rowsXpath = prepareRowXpath(table, cell_info);
        rowsXpath = String.format("%s|%s/preceding-sibling::tr", rowsXpath, rowsXpath);
        return WebElementHelper.findChildren(table, By.xpath(rowsXpath));
    }

    private static boolean isHeaderSeparated(WebElement table){
        String headerXpath = ".//thead/tr";
        WebElement headerRow = WebElementHelper.findChild(table, By.xpath(headerXpath));
        return headerRow != null;
    }

    private static Map<Integer, String> getHeaderIndexes(WebElement table){
        try{
            Map<Integer, String> headerIndexes = new HashMap<>();
            String locator = ".//tr[1]/child::*";
            List<WebElement> elements = WebElementHelper.findChildren(table, By.xpath(locator));
            int index = 1;

            for(WebElement e: elements){
                String header = WebElementHelper.getTextContent(e);
                if(!header.isEmpty()){
                    headerIndexes.putIfAbsent(index, header);
                }
                index++;
            }

            return headerIndexes;

        }catch(Exception e){
            log.debug(String.format("Fail to backup column header info since an error: %s", e.getMessage()));
            return null;
        }
    }

    private static int getCachedHeaderIndex(String header){

        for (Map.Entry<Integer, String> entry:cachedHeaders.entrySet()){
            if(entry.getValue().equalsIgnoreCase(header)){
                return entry.getKey();
            }
        }

        for (Map.Entry<Integer, String> entry:cachedHeaders.entrySet()){
            if(entry.getValue().toLowerCase().contains(header.toLowerCase())){
                return entry.getKey();
            }
        }

        return -1;
    }


}
