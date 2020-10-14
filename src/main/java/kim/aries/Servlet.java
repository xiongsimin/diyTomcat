package kim.aries;

/**
 * @Author aries
 * @Data 2020-10-14
 */
public interface Servlet {
    void init() throws Exception;

    void destroy() throws Exception;

    void service(Request request, Response response) throws Exception;
}
