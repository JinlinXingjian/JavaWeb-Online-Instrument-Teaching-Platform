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

@WebServlet("/getUser")
public class getUser extends HttpServlet {
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

        // 从请求头中获取 Token
        String token = req.getHeader("Authorization");
        System.out.println("getUserToken:"+token);

        // 如果 Token 为空或无效，则返回未登录的错误响应
        if (token == null || !JwtUtils.validateJwt(token)) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        //从token的获取username
        String username=JwtUtils.getUsernameFromJwt(token);
        System.out.println("getUserUserName:"+username);
        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        User user = userDao.getUserByUsername(username);

        User userObject = userDao.getUserByUsername(username);
        JSONObject userJson = new JSONObject();
        userJson.put("username",userObject.getUsername());
        userJson.put("Email",userObject.getEmail());
        userJson.put("avatar",userObject.getAvatar());
        userJson.put("status",userObject.getStatus());

        // 构建响应 JSON
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();
        responseBuilder.add("message", "getUser success");
        responseBuilder.add("user", userJson.toJSONString());
        JsonObject responseJson = responseBuilder.build();

        // 设置响应内容为 JSON
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print(responseJson);
        out.flush();
    }
}
