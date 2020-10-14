package kim.aries;

/**
 * @Author aries
 * @Data 2020-10-14
 */
public abstract class HttpServlet implements Servlet {

    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request, Response response);

    public void service(Request request, Response response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
