package com.youvegotnigel.automation.utils.newtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTMLTableHelper extends WebElementHelper {

    static Map<Integer, String> cachedHeaders = new HashMap<>();


    public WebElement identifyTable(String table_name, int element_index){

        String xpath = "(//h4[contains(text(),'"+table_name+"')]/following-sibling::div/table)["+element_index+"]";
        WebElement table = WebElementHelper.findChild(null, By.xpath(xpath));
        return table;
    }


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


    private static List<WebElement> findColumnHeaders(WebElement table, String attribute, String value){
        String textXpath = XPathHelper.makeTextComparisonXPath(attribute, value, XPathHelper.CompareOptions.EQUALS, false);
        String thTextXpath = String.format("th[%s]", textXpath);
        String tdTextXpath = String.format("td[%s]", textXpath);

        String columnsXpath = String.format(".//tr/%s|//tr/%s|.//tr/%s/preceding-sibling::th|//tr/%s/preceding-sibling::td", thTextXpath, tdTextXpath, thTextXpath, tdTextXpath);
        System.out.printf("Find columns matching to search criteria xpath: %s}", columnsXpath);
        List<WebElement> columns = WebElementHelper.findChildren(table, By.xpath(columnsXpath));
        return columns;
    }

    /**
     * Get position of a column based on it's attribute
     */
    private static int getColumnIndex(WebElement table, String attribute, String value){
        int index;
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

        return !columns.isEmpty() ? columns.size() : - 1;
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


    /**
     * @return Prepare table row xpath
     * @param cell_info values of table cells
     */
    public String prepareRowXpath(WebElement table, Map<String,String> cell_info){

        String rowXpath = "";
        List<String> columnsXpath  = new ArrayList<>();

        for (Map.Entry<String,String> entry : cell_info.entrySet()){
            String xpath_list = XPathHelper.makeTextComparisonXPath(".", entry.getValue(), XPathHelper.CompareOptions.EQUALS, false);
            //System.out.println(xpath_list);
            columnsXpath .add(xpath_list);
        }

        String row_xpath = "(//tbody/tr[" + String.join("and", columnsXpath ) + "])[1]";
        return row_xpath;
    }



}
