package example.controllers.User;

import example.dao.UserDao;
import example.models.User;
import example.utils.EmailSender;
import example.utils.MemoryCodeStorage;
import example.utils.MyBatisUtil;
import example.utils.VerificationCodeGenerator;
import org.apache.ibatis.session.SqlSession;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/FindPassword")
public class UserFindPassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        // 获取注册表单参数
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 Verification_code 和 newPassword 字段值
        String Email = jsonObject.getString("Email");


        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        User user=userDao.getUserByEmail(Email);
        //校验邮箱的有效性
        if(user!=null){
            String recipientEmail = Email;
            String subject = "您的验证码为";
            // 生成临时验证码，并存储到内存中,然后发送
            String code= VerificationCodeGenerator.generateNumericCode(6);
            MemoryCodeStorage.saveCode(Email,code);
            EmailSender.sendEmail(recipientEmail,subject,code);

            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "sentCode success");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(responseJson);
            out.flush();
        }
        else {
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "username error");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(responseJson);
            out.flush();
        }
    }
}
