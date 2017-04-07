package com.weixin.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * ClassName: XmlConverUtil
 * date: 2015年9月24日 下午3:16:20
 *
 * @author sid
 */
public class XmlConverUtil {

    private static final Logger logger = Logger.getLogger(XmlConverUtil.class);

    /**
     *
     * xmltoMap
     *
     * @author sidXmlConverUtil
     * @param xml
     * @return
     */
    public static Map<String,String> xmltoMap(String xml) {
        try {
            Map<String,String> map = new HashMap<String,String>();
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List<?> node = nodeElement.elements();
            for (Iterator<?> it = node.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                map.put(elm.getQName().getName(), elm.getStringValue());
                elm = null;
            }
            node = null;
            nodeElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return null;
    }

}
