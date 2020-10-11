package kim.aries;

import java.io.IOException;
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
        while (true) {
            Socket socket = serverSocket.accept();
            //返回数据
            String data = "result";
            //获取输出流
            OutputStream outputStream = socket.getOutputStream();
            String responseText = HttpProtocolUtil.getHttpHeader200(data.getBytes().length) + data;
            outputStream.write(responseText.getBytes());
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        BootStrap bootStrap = new BootStrap();
        bootStrap.start();
    }
}
