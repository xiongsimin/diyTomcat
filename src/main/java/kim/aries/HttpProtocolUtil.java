package kim.aries;

/**
 * @Author aries
 * @Data 2020-10-11
 */

public class HttpProtocolUtil {
    /**
     * 为响应码200提供请求头信息
     *
     * @param length
     * @return
     */
    public static String getHttpHeader200(long length) {
        String header = "HTTP/1.1 200 OK" + "\n"
                + "Content-Type: text/html;charset=utf-8" + "\n"
                + "Content-Length: " + length + "\n"
                + "\r\n";
        return header;
    }
}
