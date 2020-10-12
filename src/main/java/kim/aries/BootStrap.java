package kim.aries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

    private void start() throws IOException {
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
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            //封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());
            response.outputHtml(request.getUrl());
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        BootStrap bootStrap = new BootStrap();
        bootStrap.start();
    }
}
