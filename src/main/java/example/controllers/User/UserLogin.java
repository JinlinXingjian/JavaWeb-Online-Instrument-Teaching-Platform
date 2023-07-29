package example.controllers.User;

import com.alibaba.fastjson.JSONObject;
import example.dao.UserDao;
import example.models.User;
import example.utils.JwtUtils;
import example.utils.MyBatisUtil;
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

@WebServlet(value = "/login")
public class UserLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 password 字段值
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        try {
            // 调用 UserMapper 的方法进行验证
            boolean isValidLogin = userDao.validateLogin(username, password);
            // 构建响应 JSON
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

            if (isValidLogin) {
                // 登录成功
                User userObject = userDao.getUserByUsername(username);
                JSONObject userJson = new JSONObject();
                userJson.put("username", userObject.getUsername());
                userJson.put("Email", userObject.getEmail());
                userJson.put("avatar", userObject.getAvatar());
                userJson.put("status", userObject.getStatus());
                userJson.put("role", userObject.getRole());
                if (userObject.getUsername() == null || userObject.getUsername() == "") {
                    return;
                }
                // 生成 JWT Token
                String token = JwtUtils.generateJwt(userObject.getUsername(), 3600000L);
                System.out.println("login-g-token:" + userObject.getUsername() + "---" + token);
                //设置响应json的内容
                responseBuilder.add("message", "login success");
                responseBuilder.add("token", token);
                responseBuilder.add("user", userJson.toJSONString());
                JsonObject responseJson = responseBuilder.build();

                // 设置响应内容为 JSON
                resp.setContentType("application/json;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(responseJson);
                out.flush();
            } else {
                // 登录失败
                //设置响应json的内容
                responseBuilder.add("message", "login fail");
                JsonObject responseJson = responseBuilder.build();

                // 设置响应内容为 JSON
                resp.setContentType("application/json;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.print(responseJson);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
