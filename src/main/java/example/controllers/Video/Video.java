package example.controllers.Video;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


@WebServlet("/video/*")
public class Video extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //获取到服务器上视频存放地址
        String videoFileName = request.getPathInfo();
        File videoFile = new File(getServletContext().getRealPath("/videos" + videoFileName));
        FileInputStream is = new FileInputStream(videoFile);
        //检查是否为请求部分视频
        String range = request.getHeader("Range");
        if (range == null) {
            range = "bytes=0-";
        }

        int start = Integer.parseInt(range.substring(range.indexOf("=") + 1, range.indexOf("-")));
        int end = (int)videoFile.length() - 1;
        if (range.indexOf("-") < range.length() - 1) {
            //说明查询到了end字段
            end = Integer.parseInt(range.substring(range.indexOf("-") + 1));
        }

        int contentLength = end - start + 1;

        response.setHeader("Content-Type", "video/mp4");
        response.setHeader("Content-Length", String.valueOf(contentLength));
        response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + videoFile.length());
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);  // 206

        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        is.skip(start);

        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }

        is.close();
    }
}