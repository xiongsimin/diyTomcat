package kim.aries;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author xiongsm
 */
public class RequestProcessor extends Thread {
    private Socket socket;
    Map<String, HttpServlet> servletMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
