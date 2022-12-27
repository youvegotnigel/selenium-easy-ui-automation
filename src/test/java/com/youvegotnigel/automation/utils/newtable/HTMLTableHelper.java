package com.youvegotnigel.automation.utils.newtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLTableHelper extends WebElementHelper {

    static Map<Integer, String> cachedHeaders = new HashMap<>();


    /**
     * @return Identify html table using table header
     * @param table_name value of table header
     * @param element_index value of element index
     */
    public WebElement identifyTable(String table_name, int element_index){

        String xpath = "(//h4[contains(text(),'"+table_name+"')]/following-sibling::div/table)["+element_index+"]";
        WebElement table = WebElementHelper.findChild(null, By.xpath(xpath));
        return table;
    }

    /**
     * @return Identify html table using table column headers
     * @param cell_info values of table cells
     * @param element_index value of element index
     */
    public WebElement identifyTable(Map<String,String> cell_info, int element_index){

        List<String> xpathList = new ArrayList<>();

        for (Map.Entry<String,String> entry : cell_info.entrySet()){
            String xpath_list = XPathHelper.makeTextComparisonXPath(".", entry.getKey(), XPathHelper.CompareOptions.EQUALS, false);
            //System.out.println(xpath_list);
            xpathList.add(xpath_list);
        }

        String xpath = "(//thead/tr[" + String.join("and", xpathList) + "]/ancestor::table)["+element_index+"]";
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

        System.out.printf("Verify a row containing the following cells '%s' is displayed in the table '%s'", cellsInfo, table);
        System.out.printf("table xPath ::: %s", table.getText());

        try {
            WebElement row = getMatchedRow(table, cellsInfo);
            return row != null;

        }catch (Exception e){
            System.out.printf("Could not find any rows match to searching criteria %s", cellsInfo);
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

        System.out.printf("identity a cell with value '%s' under column header '%s'", cellValue, columnHeader);

        try{
            int columnIndex = getColumnIndex(table, ".", columnHeader);
            if(columnIndex > 0){
                String cellCriteria = XPathHelper.makeTextComparisonXPath(".", cellValue, XPathHelper.CompareOptions.EQUALS, false);
                String cellXpath = String.format(".//tr/td[%s and position() = %d]|.//tr/th[%s and position() = %d]", cellCriteria, columnIndex, cellCriteria, columnIndex);
                WebElement cell = WebElementHelper.findChild(table, By.xpath(cellXpath));
                return cell;

            }else{
                System.out.printf("Could not find any columns with header %s", columnHeader);
                return null;
            }


        }catch (Exception e){
            System.out.printf("Could not find any columns with header %s", columnHeader);
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

        System.out.printf("Get index of column '%s'", columnHeader);

        try{
            int index = getColumnIndex(table, ".", columnHeader);
            if(index>0){
                return index;
            }else {
                System.out.printf("Could not find column with header '%s'", columnHeader);
                return -1;
            }

        }catch (Exception e){
            System.out.printf("Could not find column with header '%s'", columnHeader);
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

        System.out.printf("Get index of column using attribute %s = '%s'", attribute, value);

        try{
            int index = getColumnIndex(table, attribute, value);
            if(index>0){
                return index;
            }else {
                System.out.printf("Could not find column with attribute %s = '%s'", attribute, value);
                return -1;
            }

        }catch (Exception e){
            System.out.printf("Could not find column with attribute %s = '%s'", attribute, value);
            e.printStackTrace();
            return -1;
        }
    }

    private static WebElement getMatchedRow(WebElement table, Map<String,String> cellsInfo){
        String rowXpath = prepareRowXpath(table, cellsInfo);
        System.out.printf("getMatchedRow - %s", rowXpath);

        return table.findElement(By.xpath(rowXpath));
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


    /**
     * @return Prepare table row xpath
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
        String rowXpath = "(//tr[" + String.join("and", columnsXpath ) + "])[1]";
        return rowXpath;
    }



    private static List<WebElement> findColumnHeaders(WebElement table, String attribute, String value){
        String textXpath = XPathHelper.makeTextComparisonXPath(attribute, value, XPathHelper.CompareOptions.EQUALS, false);
        String thTextXpath = String.format("th[%s]", textXpath);
        String tdTextXpath = String.format("td[%s]", textXpath);

        String columnsXpath = String.format(".//tr/%s|//tr/%s|.//tr/%s/preceding-sibling::th|//tr/%s/preceding-sibling::td", thTextXpath, tdTextXpath, thTextXpath, tdTextXpath);
        System.out.printf("Find columns matching to search criteria xpath: '%s'}", columnsXpath);
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

        System.out.printf("Get index of the row containing the following cells '%s'", cell_info);
        try{

            List<WebElement> rows = getMatchedAndPrecedingRows(table, cell_info);
            return isHeaderSeparated(table) ? rows.size(): rows.size()-1;

        }catch (Exception e){
            System.out.printf("Could not find any rows match to searching criteria '%s'", cell_info);
            e.printStackTrace();
            return -1;
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
        if(!columns.isEmpty()){
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
        System.out.printf("Find no of rows xpath: '%s'", trXpath);
        List<WebElement> rows = WebElementHelper.findChildren(table, By.xpath(trXpath));

        return !rows.isEmpty() ? rows.size() : -1;
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
            System.out.printf("Fail to backup column header info since an error: %s", e.getMessage());
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
