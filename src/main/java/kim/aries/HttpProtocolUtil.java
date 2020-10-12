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

    /**
     * 为响应码404提供请求头信息
     *
     * @return
     */
    public static String getHttpHeader404() {
        String str404 = "<h1>404 not found</h1>";
        String header = "HTTP/1.1 404 NOT FOUND" + "\n"
                + "Content-Type: text/html;charset=utf-8" + "\n"
                + "Content-Length: " + str404.length() + "\n"
                + "\r\n" + str404;
        return header;
    }
}
