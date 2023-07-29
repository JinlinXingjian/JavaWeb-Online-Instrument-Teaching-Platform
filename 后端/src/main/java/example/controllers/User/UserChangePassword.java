package example.controllers.User;

import example.dao.UserDao;
import example.models.User;
import example.utils.MyBatisUtil;
import example.utils.PasswordUtil;
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

@WebServlet("/changePassword")
public class UserChangePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

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

        // 获取 JSON 数据中的 username 和 password 字段值
        String username = jsonObject.getString("username");
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");

        System.out.println(username+"-"+oldPassword+"-"+newPassword);

        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        User user = userDao.getUserByUsername(username);

        if(userDao.validateLogin(username,oldPassword)){
            System.out.println("test1");
            // 使用 PasswordUtil.encryptPassword 对密码进行哈希处理
            newPassword = PasswordUtil.encryptPassword(newPassword);
            user.setPassword(newPassword);
            userDao.updateUser(user);
            // 提交事务
            sqlSession.commit();
            sqlSession.close();

            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "changePassword success");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(responseJson);
            out.flush();
        }
        else{
            System.out.println("test2");
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
            responseBuilder.add("message", "password error");
            JsonObject responseJson = responseBuilder.build();

            // 设置响应内容为 JSON
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(responseJson);
            out.flush();
        }
    }
}
