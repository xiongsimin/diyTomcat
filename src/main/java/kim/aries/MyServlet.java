package kim.aries;

import java.io.IOException;

/**
 * @Author aries
 * @Data 2020-10-14
 */
public class MyServlet extends HttpServlet {
    public void doGet(Request request, Response response) {
        String content = "<h1>MyServlet GET</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doPost(Request request, Response response) {
        String content = "<h1>MyServlet POST</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws Exception {

    }

    public void destroy() throws Exception {

    }
}
