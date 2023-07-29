package example.controllers.User;

import com.alibaba.fastjson.JSONArray;
import example.dao.UserDao;
import example.models.User;
import example.utils.JwtUtils;
import example.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@WebServlet("/getAllUsers")
public class getAllUsers extends HttpServlet {
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

        User user=userDao.getUserByUsername(username);
        System.out.println(user.getRole());
        System.out.println(user.getRole()=="admin");
        //判断是否是管理员
        if(user.getRole().equals("admin")){
            // 构建 JSON 数组
            JsonArrayBuilder userArrayBuilder = Json.createArrayBuilder();
            for (User item : userDao.getAllUsers()) {
                if(item.getRole().equals("admin")){
                    continue;
                }
                JsonObjectBuilder jsonObjectBuilder=Json.createObjectBuilder();
                jsonObjectBuilder.add("id",item.getId());
                jsonObjectBuilder.add("username",item.getUsername());
                jsonObjectBuilder.add("email",item.getEmail());
                jsonObjectBuilder.add("status",item.getStatus());
                jsonObjectBuilder.add("avatar",item.getAvatar());

                JsonObject jsonObjectTemp=jsonObjectBuilder.build();
                userArrayBuilder.add(jsonObjectTemp);
            }
            JsonArray array=userArrayBuilder.build();

            // 构建最终的 JSON 响应
            JsonObject responseJson = Json.createObjectBuilder()
                    .add("UserList", array).add("message","getAllUsers success")
                    .build();

            // 设置响应类型为 JSON
            resp.setContentType("application/json");

            // 发送响应
            resp.getWriter().println(responseJson.toString());

        }
        else {
            // 构建最终的 JSON 响应
            JsonObject responseJson = Json.createObjectBuilder()
                    .add("message", "role error")
                    .build();

            // 设置响应类型为 JSON
            resp.setContentType("application/json");

            // 发送响应
            resp.getWriter().println(responseJson.toString());
        }
    }
}
