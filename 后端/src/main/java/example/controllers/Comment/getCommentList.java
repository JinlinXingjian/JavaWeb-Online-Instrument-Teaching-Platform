package example.controllers.Comment;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/getCourseComments")
public class getCommentList extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");
        // 从请求体中获取输入流
        InputStream inputStream = req.getInputStream();

        // 使用 JsonReader 解析输入流中的 JSON 数据
        JsonReader jsonReader = Json.createReader(new InputStreamReader(inputStream));
        JsonObject jsonObject_req = jsonReader.readObject();
        jsonReader.close();

        // 获取 JSON 数据中的 username 和 password 字段值
        String courseId = jsonObject_req.getString("courseId");
        System.out.println("courseId: " + courseId);
        // 获取 SqlSession 实例
        SqlSession sqlSession = MyBatisUtil.getSqlSession();

        // 创建 CommentDao 和 UserDao 对象
        CommentDao commentDao = new CommentDao(sqlSession);
        UserDao userDao = new UserDao(sqlSession);

        // 获取所有评论
        List<Comment> commentList = commentDao.getAllComments();

        // 创建一个用于存储 JSON 对象的列表
        List<JSONObject> jsonList = new ArrayList<>();

        // 遍历评论列表，封装为 JSON 对象
        for (int i = 0; i < commentList.size(); i++) {
            Comment comment = commentList.get(i);
            if(comment.getCourse_id()!=Integer.parseInt(courseId)){
                continue;
            }
            // 获取评论对应的用户信息
            User user = userDao.getUserById(comment.getUser_id());

            // 创建 JSON 对象
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", i + 1);
            jsonObject.put("userName", user.getUsername());
            jsonObject.put("avatar", user.getAvatar());
            jsonObject.put("textBody", comment.getContent());
            jsonObject.put("time", comment.getCreated_at().toString());
            jsonObject.put("rating", comment.getRating());

            // 将 JSON 对象添加到列表中
            jsonList.add(jsonObject);
        }

        // 将 JSON 对象列表转换为 JSON 数组
        JSONArray jsonArray = new JSONArray(Collections.singletonList(jsonList));

        // 输出 JSON 字符串
        String jsonString = jsonArray.toJSONString();

        // 设置响应类型为 JSON
        resp.setContentType("application/json");

        // 发送响应
        resp.getWriter().println(jsonString);

    }
}
