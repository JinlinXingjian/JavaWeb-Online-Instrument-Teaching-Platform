package example.controllers.User;

import example.dao.UserDao;
import example.models.User;
import example.utils.MyBatisUtil;
import example.utils.PasswordUtil;
import org.apache.ibatis.session.SqlSession;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/*普通用户的注册*/

@WebServlet("/register")
public class UserRegister extends HttpServlet {
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
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");

        System.out.println(username+"-"+password+"-"+email);

        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 UserDao 对象
        UserDao userDao = new UserDao(sqlSession);

        // 检查用户名是否已经存在
        User existingUser = userDao.getUserByUsername(username);
        if (existingUser != null) {
            // 用户名已存在，返回注册失败
            resp.getWriter().println("用户名已存在，请选择另一个用户名。");
            sqlSession.close();
            return;
        }

        // 使用 PasswordUtil.encryptPassword 对密码进行哈希处理
        String hashedPassword = PasswordUtil.encryptPassword(password);

        // 创建新的用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setEmail(email);
        user.setRole("learner");
        user.setStatus("正常");
        user.setAvatar("default.png");

        // 添加用户到数据库
        userDao.addUser(user);

        // 提交事务
        sqlSession.commit();
        sqlSession.close();

        // 注册成功，返回JSON响应
        JsonObject responseJson = Json.createObjectBuilder()
                .add("message", "register success")
                .build();

        resp.getWriter().println(responseJson.toString());

    }
}
