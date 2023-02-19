package com.zhuweihao.servlets;

import com.zhuweihao.utils.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuweihao
 * @Date 2023/2/17 15:57
 * @Description com.zhuweihao.servlets
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private Map<String, Object> beanMap = new HashMap<>();

    public DispatcherServlet() {
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
                Node item = beanNodeList.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    String id = element.getAttribute("id");
                    String aClass = element.getAttribute("class");
                    Object beanobj = Class.forName(aClass).newInstance();

                    beanMap.put(id, beanobj);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException | ClassNotFoundException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //设置编码
        req.setCharacterEncoding("utf-8");

        /*
        url:http://localhost:8080/javaweb_war_exploded/hello.do
        servletPath:/hello.do
        第一步：/hello.do -> hello
        第二步：hello -> HelloController
         */
        String servletPath = req.getServletPath();

        servletPath = servletPath.substring(1);
        int lastIndexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndexOf);

        Object controller = beanMap.get(servletPath);

        String operate = req.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method[] declaredMethods = controller.getClass().getDeclaredMethods();
            for (Method method :
                    declaredMethods) {
                String name = method.getName();
                if (name.equals(operate)) {
                    //统一获取请求参数
                    Parameter[] parameters = method.getParameters();

                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if ("req".equals(parameterName)) {
                            parameterValues[i] = req;
                        } else if ("resp".equals(parameterName)) {
                            parameterValues[i]=resp;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i]=req.getSession();
                        }else {
                            //从请求中获取参数值
                            String parameterValue = req.getParameter(parameter.getName());
                            String typeName = parameter.getType().getName();
                            Object parameterObj=parameterValue;
                            if(parameterObj!=null){
                                if("java.lang.Integer".equals(typeName)){
                                    parameterObj=Integer.parseInt(parameterValue);
                                }
                                //根据需要进行扩充，如boolean类型
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }
                    //方法调用
                    method.setAccessible(true);
                    Object obj = method.invoke(controller, parameterValues);
                    //视图处理
                    String methodReturnStr = (String) obj;
                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else {
                        super.processTemplate(methodReturnStr, req, resp);
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
