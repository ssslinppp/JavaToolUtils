package com.ssslinppp.xmlUtils;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import java.util.List;

public class XPathUtils {

    public static String getElementAttributeStringValue(Element element, String AttributeName) {
        try {
            return element.getAttributeValue(AttributeName);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getElementAttributeIntegerValue(Element element, String AttributeName) {
        try {
            return Integer.parseInt(getElementAttributeStringValue(element, AttributeName));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Long getElementAttributeLongValue(Element element, String AttributeName) {
        try {
            return Long.parseLong(getElementAttributeStringValue(element, AttributeName));
        } catch (Exception e) {
            return -1l;
        }
    }

    public static Boolean getElementAttributeBooleanValue(Element element, String AttributeName) {
        try {
            return Boolean.parseBoolean(getElementAttributeStringValue(element, AttributeName));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getElementTextStringValueFromRoot(Element rootEle, String path) {
        try {
            return ((Element) getSingleNode(rootEle, path)).getTextTrim();
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getElementTextIntegerValueFromRoot(Element rootEle, String path) {
        try {
            return Integer.parseInt(getElementTextStringValueFromRoot(rootEle, path));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Long getElementTextLongValueFromRoot(Element rootEle, String path) {
        try {
            return Long.parseLong(getElementTextStringValueFromRoot(rootEle, path));
        } catch (Exception e) {
            return -1l;
        }
    }

    public static Boolean getElementTextBooleanValueFromRoot(Element rootEle, String path) {
        try {
            return Boolean.parseBoolean(getElementTextStringValueFromRoot(rootEle, path));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getElementTextStringValueByElement(Element element, String childName) {
        try {
            return element.getChildText(childName);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer getElementTextIntegerValueByElement(Element element, String childName) {
        try {
            return Integer.parseInt(getElementTextStringValueByElement(element, childName));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Long getElementTextLongValueByElement(Element element, String childName) {
        try {
            return Long.parseLong(getElementTextStringValueByElement(element, childName));
        } catch (Exception e) {
            return -1l;
        }
    }

    public static Boolean getElementTextBooleanValueByElement(Element element, String childName) {
        try {
            return Boolean.parseBoolean(getElementTextStringValueByElement(
                    element, childName));
        } catch (Exception e) {
            return false;
        }
    }

    public static Object getSingleNode(Element rootEle, String path)
            throws JDOMException {
        return XPath.selectSingleNode(rootEle, path);
    }

    @SuppressWarnings("rawtypes")
    public static List getNodes(Element rootEle, String path)
            throws JDOMException {
        return XPath.selectNodes(rootEle, path);
    }
}