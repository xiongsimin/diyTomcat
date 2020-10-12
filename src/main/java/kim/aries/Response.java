package kim.aries;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author aries
 * @Data 2020-10-11
 * @Description 封装Response对象，依赖于OutputStream  该对象提供核心方法，输出html
 */
public class Response {
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Response() {
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 使用输出流输出指定字符串
     *
     * @param content
     * @throws IOException
     */
    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }

    /**
     * @param path
     */
    public void outputHtml(String path) throws IOException {
        //获取静态资源文件的绝对路径
        String absoluteResourcePath = StaticResourceUtil.getAbsolutePath(path);
        //输入静态资源文件
        File file = new File(absoluteResourcePath);
        if (file.exists() && file.isFile()) {
            //读取静态资源文件，输出静态资源
            StaticResourceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            //输出404
            output(HttpProtocolUtil.getHttpHeader404());
        }
    }
}
