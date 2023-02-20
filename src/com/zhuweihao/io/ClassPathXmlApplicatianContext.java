package com.zhuweihao.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuweihao
 * @Date 2023/2/20 13:30
 * @Description com.zhuweihao.io
 */
public class ClassPathXmlApplicatianContext implements BeanFactory {
    private Map<String, Object> beanMap = new HashMap<>();

    public ClassPathXmlApplicatianContext() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //创建Document对象
            Document document = documentBuilder.parse(inputStream);
            //获取所有bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String id = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class beanClass = Class.forName(className);
                    //创建bean实例
                    Object beanObj = beanClass.newInstance();
                    //将bean实例对象保存到map容器中
                    beanMap.put(id, beanObj);
                }
            }
            //组装bean之间的依赖关系
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String id = beanElement.getAttribute("id");
                    //注意子节点个数，xml相关知识
                    NodeList beanChildNodeList = beanElement.getChildNodes();
                    for (int j = 0; j < beanChildNodeList.getLength(); j++) {
                        Node beanChildNode = beanChildNodeList.item(j);
                        if(beanChildNode.getNodeType()==Node.ELEMENT_NODE&&"property".equals(beanChildNode.getNodeName())){
                            Element propertyElement=(Element) beanChildNode;
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            //找到propertyRef对应的实例
                            Object refObj = beanMap.get(propertyRef);
                            //为当前bean对象的propertyName属性赋值为propertyRef对象,即beanObj.propertyName=refObj
                            Object beanObj = beanMap.get(id);
                            Class<?> beanClazz = beanObj.getClass();
                            Field propertyField = beanClazz.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj,refObj);
                        }
                    }

                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
