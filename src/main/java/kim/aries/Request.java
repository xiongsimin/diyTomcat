package kim.aries;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author aries
 * @Data 2020-10-11
 * @Sescription 把请求信息封装成Request对象，依赖于InputStream
 */
public class Request {
    //请求方式，比如GET/POST
    private String method;
    //例如：/ ，/index.html
    private String url;


    //输入流  其他属性从输入流中解析出来
    private InputStream inputStream;

    public Request() {
    }

    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        //从输入流中获取请求信息
        int count = 0;
        //todo 疑问，如果inputStream.available()为0，此处岂不是死循环？
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String inputStr = new String(bytes);
        //获取第一行请求信息  eg: GET / HTTP/1.1
        String firstLineStr = inputStr.split("\n")[0];
        String[] strings = firstLineStr.split(" ");
        method = strings[0];
        url = strings[1];
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
    }
}
