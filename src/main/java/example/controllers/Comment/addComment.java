package example.controllers.Comment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import example.dao.CommentDao;
import example.dao.CourseDao;
import example.dao.UserDao;
import example.models.Comment;
import example.models.User;
import example.utils.MyBatisUtil;
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
import java.util.Date;

@WebServlet("/addComment")
public class addComment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        // 使用 UTF-8 编码解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream, "UTF-8"));
        JsonObject jsonObject_req = jsonReader.readObject();
        jsonReader.close();
        System.out.println("test1");
        // 获取 JSON 数据中的 username 和 password 字段值
        String courseId = jsonObject_req.getString("courseId");
        String username = jsonObject_req.getString("userName");
        String rate = jsonObject_req.get("rate").toString();
        String textArea = jsonObject_req.getString("textArea");
        System.out.println(courseId+"-"+username+"-"+rate+"-"+textArea);
        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 CommentDao 和 UserDao 对象
        CommentDao commentDao = new CommentDao(sqlSession);
        UserDao userDao = new UserDao(sqlSession);

        User user = userDao.getUserByUsername(username);
        //为comment对象赋值
        Comment comment=new Comment();
        comment.setCourse_id(Integer.parseInt(courseId));
        comment.setUser_id(user.getId());
        comment.setContent(textArea);
        comment.setRating(Integer.parseInt(rate));
        comment.setCreated_at(new Date());
        commentDao.addComment(comment);


        sqlSession.commit();
        sqlSession.close();

        JSONObject jsonString=new JSONObject();

        jsonString.put("message","addComment success");

        String resJson=jsonString.toJSONString();
        System.out.println(resJson);
        // 设置响应类型为 JSON
        resp.setContentType("application/json");

        // 发送响应
        resp.getWriter().println(resJson);

    }
}
