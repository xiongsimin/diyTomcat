package kim.aries;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author aries
 * @Data 2020-10-11
 */
public class BootStrap {
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void start() throws Exception {


        //加载解析web.xml，初始化Servlet
        loadServlet();

        //定义一个线程池

        int corePoolSize = 10;
        int maximumPoolSize = 50;
        long keepAliveTime = 100L;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);


        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("========>diyTomcat start on port:" + port);
        /**diyTomcat V1.0
         *需求：返回一个固定字符串“Hello World!”到页面
         *
         */
         /*
        while (true) {
            Socket socket = serverSocket.accept();
            //返回数据
            String data = "Hello World!";
            //获取输出流
            OutputStream outputStream = socket.getOutputStream();
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }
        */
        /**
         * diyTomcat V2.0
         * 需求：封装Request和Response对象，返回html静态资源文件
         */
        /*
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            //封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            response.outputHtml(request.getUrl());
            socket.close();
        }
        */
        /**
         * diyTomcat V3.0
         * 需求：可以请求动态资源（Servlet）
         */
        /*
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            //封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            //如果从servlet中取到当做动态资源处理
            if (servletMap.get(request.getUrl()) != null) {
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request, response);
            }
            //否则当做静态资源处理
            else {
                response.outputHtml(request.getUrl());
            }
            socket.close();
        }
        */
        /**
         * V3.0基础上的多线程改造(不使用线程池)
         *
         */

      /*  while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
            requestProcessor.start();
        }*/

        /**
         * V3.0基础上的多线程改造(使用线程池)
         *
         */
        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, servletMap);
//            requestProcessor.start();
            threadPoolExecutor.execute(requestProcessor);
        }

    }

    private Map<String, HttpServlet> servletMap = new HashMap<String, HttpServlet>();

    /**
     * 加载解析web.xml，初始化Servlet
     */
    private void loadServlet() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> servletNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < servletNodes.size(); i++) {
                //servlet节点
                Element element = servletNodes.get(i);
                Element servletNameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletNameElement.getStringValue();
                Element servletClassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassElement.getStringValue();
                //根据servlet-name的值找到url-pattern（servlet-mapping下的子节点）
                Element servletMappingElement = (Element) rootElement.selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMappingElement.selectSingleNode("url-pattern").getStringValue();
                servletMap.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BootStrap bootStrap = new BootStrap();
        try {
            bootStrap.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
