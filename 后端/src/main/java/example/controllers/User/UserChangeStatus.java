package example.controllers.User;

import example.dao.UserDao;
import example.models.User;
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
import java.nio.charset.StandardCharsets;

@WebServlet("/changenStatus")
public class UserChangeStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain;charset=UTF-8");
        // 获取注册表单参数
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 password 字段值
        String username = jsonObject.getString("username");
        String newStatus = jsonObject.getString("newStatus");
        String changeUsername=jsonObject.getString("changeUsername");

        System.out.println(username+"----"+newStatus);

        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        //创建响应response
        JsonObjectBuilder responseBuilder = Json.createObjectBuilder();

        User user = userDao.getUserByUsername(username);
        if(user==null){
            responseBuilder.add("message", "username error");
            return ;
        }
        else if(user.getRole().equals("admin")){

            User changeUser=userDao.getUserByUsername(changeUsername);
            changeUser.setStatus(newStatus);
            userDao.updateUser(changeUser);
            // 提交事务
            sqlSession.commit();
            sqlSession.close();

            responseBuilder.add("message", "changeStatus success");
        }
        else{
            responseBuilder.add("message", "changeStatus error");
        }
        JsonObject responseJson = responseBuilder.build();
        // 设置响应内容为 JSON
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(responseJson);

    }
}
