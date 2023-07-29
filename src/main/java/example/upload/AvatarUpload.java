package example.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/upload/avatar/*")
@MultipartConfig
public class AvatarUpload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        // 检查请求是否为多部分表单数据

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                // 创建文件项目工厂
                DiskFileItemFactory factory = new DiskFileItemFactory();

                // 设置内存临界值，大于这个值将使用临时文件存储
                factory.setSizeThreshold(1024 * 1024); // 1MB

                // 设置临时存储目录
                File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
                factory.setRepository(tempDir);

                // 创建文件上传处理器
                ServletFileUpload upload = new ServletFileUpload(factory);

                // 解析请求
                List<FileItem> items = upload.parseRequest(request);

                // 处理上传文件
                for (FileItem item : items) {
                    // 检查当前项目是否为文件
                    if (!item.isFormField()) {
                        // 获取文件名
//                        String fileName = extractFileName(item);
                        String fileName = request.getPathInfo().substring(1);
                        // 拼接保存路径
                        String saveDirectory = getServletContext().getRealPath("/static/avatar/");
                        String targetDirectory = saveDirectory.substring(0, saveDirectory.indexOf("target"));
                        String filePath = targetDirectory + "src\\main\\webapp\\static\\avatar\\" + fileName;


                        // 保存文件到指定路径
                        File uploadedFile = new File(filePath);
                        item.write(uploadedFile);

                        // 执行其他操作，例如保存文件路径到数据库等
                    }
                }

                // 返回上传成功信息
                response.getWriter().print("文件上传成功");
            } catch (Exception e) {
                // 处理异常
                response.getWriter().print("文件上传失败：" + e.getMessage());
            }
        } else {
            response.getWriter().print("无效的文件上传请求");
        }
    }

    private String extractFileName(FileItem item) {
        String fileName = item.getName();
        if (fileName.contains("\\")) {
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        return fileName;
    }

}
