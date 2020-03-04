package com.ssslinppp.xmlUtils;

import com.ssslinppp.model.Book;
import com.ssslinppp.model.Computer;
import com.ssslinppp.model.MyConfig;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XmlToConfigV {
    private static Logger logger = Logger.getLogger(XmlToConfigV.class);

    public static MyConfig getLogSystemConfigVO(String fileName) {
        try {
            Element rootEle = new SAXBuilder().build(new File(fileName))
                    .getRootElement();
            MyConfig myConfig = new MyConfig();

            myConfig.setStringValue(XPathUtils
                    .getElementTextStringValueFromRoot(rootEle,
                            "/config/base-config/stringValue"));
            myConfig.setIntegerValue(XPathUtils
                    .getElementTextIntegerValueFromRoot(rootEle,
                            "/config/base-config/integerValue"));
            myConfig.setLongValue(XPathUtils
                    .getElementTextLongValueFromRoot(rootEle,
                            "/config/base-config/longValue"));

            HashMap<Integer, Book> booksMap = new HashMap<Integer, Book>();
            for (Object element : XPath.selectNodes(rootEle, "/config/books/book")) {
                Book book = new Book();
                book.setId(XPathUtils.getElementAttributeIntegerValue((Element) element, "id"));
                book.setName(XPathUtils.getElementTextStringValueByElement((Element) element, "name"));
                book.setPrice(XPathUtils.getElementTextLongValueByElement((Element) element, "price"));
                booksMap.put(book.getId(), book);
            }
            myConfig.setBooksMap(booksMap);

            List<Computer> computerList = new ArrayList<Computer>();
            for (Object element : XPath.selectNodes(rootEle, "/config/computers/computer")) {
                Computer computer = new Computer();
                computer.setType(XPathUtils.getElementAttributeStringValue((Element) element, "type"));
                computer.setSize(XPathUtils.getElementAttributeStringValue((Element) element, "size"));
                computerList.add(computer);
            }
            myConfig.setComputerList(computerList);
            return myConfig;
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

}