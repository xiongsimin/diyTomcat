package kim.aries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author aries
 * @Data 2020-10-12
 */
public class StaticResourceUtil {

    public static String getAbsolutePath(String path) {
        String absolutePath = StaticResourceUtil.class.getResource("/").getPath();
        return absolutePath + path;
    }

    /**
     * 读取静态资源文件输入流，通过输出流输出
     *
     * @param inputStream
     * @param outputStream
     * @throws IOException
     */
    public static void outputStaticResource(InputStream inputStream, OutputStream outputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        int resourceSize = count;
        //输出http请求头，然后在输出具体内容
        outputStream.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());
        //读取内容输出
        //已读取的内容长度
        long written = 0;
        //计划每次缓冲的长度
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];
        while (written < resourceSize) {
            if (written + byteSize > resourceSize) {
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            outputStream.flush();
            written += byteSize;
        }
    }

    public static void main(String[] args) {
        System.out.println(getAbsolutePath("temp"));
    }
}
